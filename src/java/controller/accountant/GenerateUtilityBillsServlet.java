package controller.accountant;

import dao.MeterReadingDAO;
import dao.UtilityBillDAO;
import dao.UtilityRateDAO;
import dao.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MeterReading;
import model.UtilityBill;
import model.UtilityRate;
import model.Resident;
import model.Apartment;
import model.Invoices;
import model.InvoiceDetail;
import dao.DBContext;

@WebServlet(name = "GenerateUtilityBillsServlet", urlPatterns = {"/accountant/generate-utility-bills"})
public class GenerateUtilityBillsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GenerateUtilityBillsServlet.class.getName());
    
    private MeterReadingDAO meterReadingDAO;
    private UtilityBillDAO utilityBillDAO;
    private UtilityRateDAO utilityRateDAO;
    private InvoiceDAO invoiceDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        meterReadingDAO = new MeterReadingDAO();
        utilityBillDAO = new UtilityBillDAO();
        utilityRateDAO = new UtilityRateDAO();
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));
            String paymentDueDateStr = request.getParameter("paymentDueDate");
            String billScope = request.getParameter("billScope");
            String selectedApartmentId = request.getParameter("apartment");
            
            LocalDate paymentDueDate = LocalDate.parse(paymentDueDateStr);
            LocalDateTime dueDateTime = paymentDueDate.atTime(23, 59, 59);
            
            List<MeterReading> readings;
            if ("selected".equals(billScope) && selectedApartmentId != null && !selectedApartmentId.isEmpty()) {
                int apartmentId = Integer.parseInt(selectedApartmentId);
                readings = meterReadingDAO.getMeterReadingsByApartment(apartmentId, month, year);
            } else {
                readings = meterReadingDAO.getMeterReadingsByMonth(month, year);
            }
            
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDateTime billingStart = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime billingEnd = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            
            List<UtilityBill> bills = convertToUtilityBills(readings);
            int successCount = 0;
            int skippedCount = 0;
            
            for (UtilityBill bill : bills) {
                boolean exists = checkBillExists(bill.getApartmentId(), month, year);
                
                if (!exists) {
                    bill.setBillingPeriodStart(billingStart);
                    bill.setBillingPeriodEnd(billingEnd);
                    bill.setGeneratedDate(LocalDateTime.now());
                    bill.setDueDate(dueDateTime);
                    bill.setStatus("Unpaid");
                    bill.setBillingMonth(month);
                    bill.setBillingYear(year);
                    
                    int residentId = getResidentIdForApartment(bill.getApartmentId());
                    
                    Resident resident = new Resident(residentId, bill.getOwnerName(), "", "");
                    Apartment apartment = new Apartment(bill.getApartmentId(), bill.getApartmentName(), "", "", "");
                    
                    Invoices invoice = new Invoices(
                        0,
                        bill.getTotalAmount().doubleValue(),
                        LocalDate.now(),
                        "Unpaid",
                        "Utility Bill for " + YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        paymentDueDate,
                        resident,
                        apartment,
                        0.0
                    );
                    
                    invoiceDAO.insertInvoice(invoice);
                    int invoiceId = invoiceDAO.getLatestInvoiceID();
                    
                    if (bill.getElectricityCost().compareTo(BigDecimal.ZERO) > 0) {
                        InvoiceDetail electricityDetail = new InvoiceDetail(
                            0,
                            bill.getElectricityCost().doubleValue(),
                            "Electricity consumption: " + bill.getElectricityConsumption() + " kWh",
                            1
                        );
                        invoiceDAO.insertInvoiceDetail(invoiceId, electricityDetail);
                    }
                    
                    if (bill.getWaterCost().compareTo(BigDecimal.ZERO) > 0) {
                        InvoiceDetail waterDetail = new InvoiceDetail(
                            0,
                            bill.getWaterCost().doubleValue(),
                            "Water consumption: " + bill.getWaterConsumption() + " m³",
                            2
                        );
                        invoiceDAO.insertInvoiceDetail(invoiceId, waterDetail);
                    }
                    
                    bill.setInvoiceId(invoiceId);
                    utilityBillDAO.addUtilityBill(bill);
                    successCount++;
                } else {
                    skippedCount++;
                }
            }
            String message = String.format("Đã tạo %d hóa đơn mới (bỏ qua %d hóa đơn đã tồn tại)", 
                    successCount, skippedCount);
            response.sendRedirect(request.getContextPath() + 
                    "/accountant/manager-meter-reading?month=" + month + 
                    "&year=" + year + 
                    (selectedApartmentId != null && !selectedApartmentId.isEmpty() ? "&apartment=" + selectedApartmentId : "") + 
                    "&success=" + java.net.URLEncoder.encode(message, "UTF-8"));
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating utility bills", e);
            String errorMessage = "Lỗi khi tạo hóa đơn: " + e.getMessage();
            response.sendRedirect(request.getContextPath() + 
                    "/accountant/manager-meter-reading?error=" + 
                    java.net.URLEncoder.encode(errorMessage, "UTF-8"));
        }
    }
    
    private boolean checkBillExists(int apartmentId, int month, int year) throws SQLException {
        return utilityBillDAO.checkBillExists(apartmentId, month, year);
    }
    
    private List<UtilityBill> convertToUtilityBills(List<MeterReading> readings) throws SQLException {
        Map<Integer, UtilityBill> billMap = new HashMap<>();
        
        LocalDateTime now = LocalDateTime.now();
        UtilityRate electricityRate = utilityRateDAO.getCurrentUtilityRate("Electricity", now);
        UtilityRate waterRate = utilityRateDAO.getCurrentUtilityRate("Water", now);
        BigDecimal electricityUnitPrice = (electricityRate != null) ? 
                                           electricityRate.getUnitPrice() : 
                                           new BigDecimal("3500");
        
        BigDecimal waterUnitPrice = (waterRate != null) ? 
                                     waterRate.getUnitPrice() : 
                                     new BigDecimal("15000");

        Map<Integer, List<MeterReading>> readingsByApartment = new HashMap<>();
        for (MeterReading reading : readings) {
            readingsByApartment.computeIfAbsent(reading.getApartmentId(), k -> new ArrayList<>()).add(reading);
        }
        
        for (Map.Entry<Integer, List<MeterReading>> entry : readingsByApartment.entrySet()) {
            int apartmentId = entry.getKey();
            List<MeterReading> apartmentReadings = entry.getValue();
            
            UtilityBill bill = new UtilityBill();
            bill.setApartmentId(apartmentId);
            bill.setElectricityConsumption(BigDecimal.ZERO);
            bill.setWaterConsumption(BigDecimal.ZERO);
            bill.setElectricityCost(BigDecimal.ZERO);
            bill.setWaterCost(BigDecimal.ZERO);
            bill.setTotalAmount(BigDecimal.ZERO);
            
            for (MeterReading reading : apartmentReadings) {
                bill.setApartmentName(reading.getApartmentName());
                bill.setOwnerName(reading.getOwnerName());
                bill.setBuildingName("Building 1");
                
                if ("Electricity".equals(reading.getMeterType())) {
                    bill.setElectricityConsumption(reading.getConsumption());
                    BigDecimal cost = reading.getConsumption().multiply(electricityUnitPrice);
                    bill.setElectricityCost(cost);
                } else if ("Water".equals(reading.getMeterType())) {
                    bill.setWaterConsumption(reading.getConsumption());
                    BigDecimal cost = reading.getConsumption().multiply(waterUnitPrice);
                    bill.setWaterCost(cost);
                }
            }
            
            bill.setTotalAmount(bill.getElectricityCost().add(bill.getWaterCost()));
            billMap.put(apartmentId, bill);
        }

        return new ArrayList<>(billMap.values());
    }
    
    private int getResidentIdForApartment(int apartmentId) throws SQLException {
        String sql = "SELECT ResidentID FROM Contract WHERE ApartmentID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, apartmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ResidentID");
                }
            }
        }
        throw new SQLException("No active resident found for apartment ID: " + apartmentId);
    }
}