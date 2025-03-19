/*
 * Manager Meter Reading Servlet - Handles electricity and water meter readings
 */
package controller.accountant;

import dao.ApartmentDAO;
import dao.ExportLogDAO;
import dao.MeterDAO;
import dao.MeterReadingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.ExcelUtils;
import model.ExportLog;
import model.Meter;
import model.MeterReading;
import model.UtilityBill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Servlet for managing meter readings including viewing, exporting, and importing data
 */
@WebServlet(name = "ManagerMeterReadingServlet", urlPatterns = {"/accountant/manager-meter-reading"})
@MultipartConfig
public class ManagerMeterReadingServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ManagerMeterReadingServlet.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private MeterReadingDAO meterReadingDAO;
    private MeterDAO meterDAO;
    private ExportLogDAO exportLogDAO;
    private ApartmentDAO apartmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        meterReadingDAO = new MeterReadingDAO();
        meterDAO = new MeterDAO();
        exportLogDAO = new ExportLogDAO();
        apartmentDAO = new ApartmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action != null ? action : "") {
                case "export":
                    exportMeterReadings(request, response);
                    break;
                case "template":
                    downloadTemplate(request, response);
                    break;
                default:
                    displayMeterReadings(request, response);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doGet", e);
            handleError(request, response, "Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("import".equals(action)) {
                importMeterReadings(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/accountant/manager-meter-reading");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doPost", e);
            handleError(request, response, "Error processing request", e);
        }
    }

    private void displayMeterReadings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Calendar cal = Calendar.getInstance();
        int currentMonth = getValidatedMonth(request.getParameter("month"), cal.get(Calendar.MONTH) + 1);
        int currentYear = getValidatedYear(request.getParameter("year"), cal.get(Calendar.YEAR));
        String apartmentFilter = request.getParameter("apartment");

        List<MeterReading> meterReadings;
        if (apartmentFilter != null && !apartmentFilter.isEmpty()) {
            try {
                int apartmentId = Integer.parseInt(apartmentFilter);
                meterReadings = meterReadingDAO.getMeterReadingsByApartment(apartmentId, currentMonth, currentYear);
            } catch (NumberFormatException e) {
                meterReadings = meterReadingDAO.getMeterReadingsByMonth(currentMonth, currentYear);
            }
        } else {
            meterReadings = meterReadingDAO.getMeterReadingsByMonth(currentMonth, currentYear);
        }

        List<Apartment> apartments = apartmentDAO.selectAll();
        request.setAttribute("meterReadings", meterReadings);
        request.setAttribute("currentMonth", currentMonth);
        request.setAttribute("currentYear", currentYear);
        request.setAttribute("apartments", apartments);
        Map<String, Object> stats = calculateMeterReadingStats(meterReadings);
        request.setAttribute("stats", stats);
        request.getRequestDispatcher("managermeterreading.jsp").forward(request, response);
    }

    private void exportMeterReadings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        LocalDate now = LocalDate.now();
        int month = getValidatedMonth(request.getParameter("month"), now.getMonthValue());
        int year = getValidatedYear(request.getParameter("year"), now.getYear());
        String apartmentFilter = request.getParameter("apartment");
        
        List<MeterReading> readings;
        if (apartmentFilter != null && !apartmentFilter.isEmpty()) {
            try {
                int apartmentId = Integer.parseInt(apartmentFilter);
                readings = meterReadingDAO.getMeterReadingsByApartment(apartmentId, month, year);
            } catch (NumberFormatException e) {
                readings = meterReadingDAO.getMeterReadingsByMonth(month, year);
            }
        } else {
            readings = meterReadingDAO.getMeterReadingsByMonth(month, year);
        }
        Integer staffId = getUserIdFromSession(request);
        ByteArrayOutputStream excelStream = ExcelUtils.createMeterReadingReport(readings);

        ExportLog log = new ExportLog();
        log.setExportType("Meter Reading");
        log.setExportDate(LocalDateTime.now());
        log.setStaffId(staffId);
        log.setFileName("meter_readings_" + month + "_" + year + ".xlsx");
        log.setRecordsCount(excelStream.size());
        exportLogDAO.createExportLog(log);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "meter_readings_" + month + "_" + year + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(excelStream.toByteArray());
            outputStream.flush();
        }
    }

    private void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Meter> meters = meterDAO.getAllMeters();
        
        ByteArrayOutputStream templateStream = ExcelUtils.createMeterReadingTemplate(meters);
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "meter_reading_template.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(templateStream.toByteArray());
            outputStream.flush();
        }
    }
    private void importMeterReadings(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
    try {
        Part filePart = request.getPart("excelFile");
        if (filePart == null) {
            request.setAttribute("importError", "No file selected");
            displayMeterReadings(request, response);
            return;
        }
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.toLowerCase().endsWith(".xlsx")) {
            request.setAttribute("importError", "Only .xlsx files are supported");
            displayMeterReadings(request, response);
            return;
        }
        Calendar cal = Calendar.getInstance();
        int month = getValidatedMonth(request.getParameter("importMonth"), cal.get(Calendar.MONTH) + 1);
        int year = getValidatedYear(request.getParameter("importYear"), cal.get(Calendar.YEAR));
        Integer staffId = getUserIdFromSession(request);
        Map<String, Integer> meterMap = getMeterIdMap();
        
        List<MeterReading> readings;
        try (InputStream inputStream = filePart.getInputStream()) {
            readings = ExcelUtils.readMeterReadingsFromExcel(inputStream, meterMap, staffId, month, year);
        } catch (InvalidFormatException e) {
            request.setAttribute("importError", "Invalid Excel format: " + e.getMessage());
            displayMeterReadings(request, response);
            return;
        }
        
        List<String> errors = validateReadings(readings);
        if (!errors.isEmpty()) {
            request.setAttribute("importErrors", errors);
            displayMeterReadings(request, response);
            return;
        }
        
        // Modify this section to check for existing readings
        int createdCount = 0;
        int updatedCount = 0;
        
        for (MeterReading reading : readings) {
            // Check if a reading already exists for this meter in this month/year
            MeterReading existingReading = meterReadingDAO.getMeterReadingByMeterAndMonthYear(
                reading.getMeterId(), month, year);
            
            if (existingReading != null) {
                // Update the existing reading
                reading.setReadingId(existingReading.getReadingId()); // Set the ID of the existing reading
                if (meterReadingDAO.updateMeterReading(reading) > 0) {
                    updatedCount++;
                }
            } else {
                // Create a new reading
                if (meterReadingDAO.addMeterReading(reading) > 0) {
                    createdCount++;
                }
            }
        }
        
        request.setAttribute("importSuccess", "Import complete: " + createdCount + 
            " new readings created, " + updatedCount + " existing readings updated");
        displayMeterReadings(request, response);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error importing meter readings", e);
        request.setAttribute("importError", "Error importing meter readings: " + e.getMessage());
        displayMeterReadings(request, response);
    }
}
    
    /**
     * Get meter ID mapping (meter number -> meter ID)
     */
    private Map<String, Integer> getMeterIdMap() throws SQLException {
        List<Meter> meters = meterDAO.getAllMeters();
        Map<String, Integer> meterMap = new HashMap<>();
        
        for (Meter meter : meters) {
            meterMap.put(meter.getMeterNumber(), meter.getMeterId());
        }
        
        return meterMap;
    }
    
    /**
     * Validate meter readings before saving
     */
    private List<String> validateReadings(List<MeterReading> readings) {
        List<String> errors = new ArrayList<>();
        
        for (int i = 0; i < readings.size(); i++) {
            MeterReading reading = readings.get(i);
            if (reading.getConsumption().compareTo(BigDecimal.ZERO) < 0) {
                errors.add("Row " + (i + 1) + ": Current reading is less than previous reading");
            }
            if (reading.getConsumption().compareTo(new BigDecimal("1000")) > 0) {
                errors.add("Row " + (i + 1) + ": Unusually high consumption detected. Please verify.");
            }
            if (reading.getReadingDate().isAfter(LocalDateTime.now())) {
                errors.add("Row " + (i + 1) + ": Reading date cannot be in the future");
            }
        }
        
        return errors;
    }
    private Map<String, Object> calculateMeterReadingStats(List<MeterReading> readings) {
        Map<String, Object> stats = new HashMap<>();
        
        BigDecimal totalElectricityConsumption = BigDecimal.ZERO;
        BigDecimal totalWaterConsumption = BigDecimal.ZERO;
        int electricityCount = 0;
        int waterCount = 0;
        
        for (MeterReading reading : readings) {
            if ("Electricity".equals(reading.getMeterType())) {
                totalElectricityConsumption = totalElectricityConsumption.add(reading.getConsumption());
                electricityCount++;
            } else if ("Water".equals(reading.getMeterType())) {
                totalWaterConsumption = totalWaterConsumption.add(reading.getConsumption());
                waterCount++;
            }
        }
        
        stats.put("totalElectricityConsumption", totalElectricityConsumption);
        stats.put("totalWaterConsumption", totalWaterConsumption);
        stats.put("electricityCount", electricityCount);
        stats.put("waterCount", waterCount);
        stats.put("totalReadings", readings.size());
        
        BigDecimal avgElectricityConsumption = electricityCount > 0 
                ? totalElectricityConsumption.divide(new BigDecimal(electricityCount), 2, BigDecimal.ROUND_HALF_UP) 
                : BigDecimal.ZERO;
        
        BigDecimal avgWaterConsumption = waterCount > 0 
                ? totalWaterConsumption.divide(new BigDecimal(waterCount), 2, BigDecimal.ROUND_HALF_UP) 
                : BigDecimal.ZERO;
        
        stats.put("avgElectricityConsumption", avgElectricityConsumption);
        stats.put("avgWaterConsumption", avgWaterConsumption);
        
        return stats;
    }
    
    
    private Integer getUserIdFromSession(HttpServletRequest request) {
        // In a real application, this would come from the user's session
        // For now, using a default value
        return 1; // Default admin user
    }
    
    /**
     * Validate month parameter
     */
    private int getValidatedMonth(String monthParam, int defaultMonth) {
        try {
            int month = Integer.parseInt(monthParam);
            return (month >= 1 && month <= 12) ? month : defaultMonth;
        } catch (NumberFormatException e) {
            return defaultMonth;
        }
    }
    
    /**
     * Validate year parameter
     */
    private int getValidatedYear(String yearParam, int defaultYear) {
        try {
            int year = Integer.parseInt(yearParam);
            int currentYear = LocalDate.now().getYear();
            return (year >= 2000 && year <= currentYear + 1) ? year : defaultYear;
        } catch (NumberFormatException e) {
            return defaultYear;
        }
    }

    /**
     * Handle and display errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response,
            String message, Exception e) throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, message, e);
        request.setAttribute("errorCode", "500");
        request.setAttribute("errorMessage", message + ": " + e.getMessage());
        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
    }
}