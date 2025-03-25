//package controller.accountant;
//
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import model.ExcelUtils;
//import model.ExportLog;
//import model.MeterReading;
//import model.UtilityBill;
//import java.io.*;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//
//import dao.MeterReadingDAO;
//import dao.MeterDAO;
//import dao.ImportLogDAO;
//import dao.ExportLogDAO;
//import model.Meter;
//import model.MeterReading;
//import model.ImportLog;
//import model.ExportLog;
//import model.UtilityBill;
//import model.ExcelUtils;
//
///**
// *
// * @author nkiem
// */
//@WebServlet("/accountant/meter-reading/*")
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
//    maxFileSize = 1024 * 1024 * 10,      // 10 MB
//    maxRequestSize = 1024 * 1024 * 100    // 100 MB
//)
//public class MeterReadingServlet extends HttpServlet {
//   private MeterReadingDAO meterReadingDAO;
//    private MeterDAO meterDAO;
//    private ImportLogDAO importLogDAO;
//    private ExportLogDAO exportLogDAO;
//    
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        meterReadingDAO = new MeterReadingDAO();
//        meterDAO = new MeterDAO();
//        importLogDAO = new ImportLogDAO();
//        exportLogDAO = new ExportLogDAO();
//    }
//    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//        
//        if (pathInfo == null || pathInfo.equals("/")) {
//            listMeterReadings(request, response);
//        } else if (pathInfo.equals("/edit")) {
//            showEditForm(request, response);
//        } else if (pathInfo.equals("/delete")) {
//            deleteMeterReading(request, response);
//        } else if (pathInfo.equals("/import")) {
//            showImportForm(request, response);
//        } else if (pathInfo.equals("/export")) {
//            exportMeterReadings(request, response);
//        } else if (pathInfo.equals("/template")) {
//            generateExcelTemplate(request, response);
//        } else if (pathInfo.equals("/details")) {
//            showMeterReadingDetails(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//    
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//        
//        if (pathInfo == null || pathInfo.equals("/")) {
//            addMeterReading(request, response);
//        } else if (pathInfo.equals("/update")) {
//            updateMeterReading(request, response);
//        } else if (pathInfo.equals("/import")) {
//            importMeterReadings(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//    
//    private void listMeterReadings(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            Calendar cal = Calendar.getInstance();
//            int month = request.getParameter("month") != null 
//                    ? Integer.parseInt(request.getParameter("month")) 
//                    : cal.get(Calendar.MONTH) + 1;
//            int year = request.getParameter("year") != null 
//                    ? Integer.parseInt(request.getParameter("year")) 
//                    : cal.get(Calendar.YEAR);
//            
//            String apartmentFilter = request.getParameter("apartment");
//            
//            List<MeterReading> meterReadings;
//            if (apartmentFilter != null && !apartmentFilter.isEmpty()) {
//                meterReadings = meterReadingDAO.getMeterReadingsByApartment(
//                        Integer.parseInt(apartmentFilter), month, year);
//            } else {
//                meterReadings = meterReadingDAO.getMeterReadingsByMonth(month, year);
//            }
//            
//            request.setAttribute("meterReadings", meterReadings);
//            request.setAttribute("currentMonth", month);
//            request.setAttribute("currentYear", year);
//            request.getRequestDispatcher("list.jsp").forward(request, response);
//            
//        } catch (SQLException e) {
//            handleError(request, response, "Error retrieving meter readings", e);
//        }
//    }
//    
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            String readingId = request.getParameter("id");
//            
//            if (readingId != null) {
//                MeterReading meterReading = meterReadingDAO.getMeterReadingById(Integer.parseInt(readingId));
//                request.setAttribute("meterReading", meterReading);
//            } else {
//                request.setAttribute("meters", meterDAO.getAllMeters());
//            }
//            
//            request.getRequestDispatcher("/accountant/meter-reading/edit.jsp").forward(request, response);
//            
//        } catch (SQLException e) {
//            handleError(request, response, "Error preparing meter reading form", e);
//        }
//    }
//    
//    private void addMeterReading(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int meterId = Integer.parseInt(request.getParameter("meterId"));
//            int staffId = ((Integer) request.getSession().getAttribute("staffId")); // From session
//            BigDecimal previousReading = new BigDecimal(request.getParameter("previousReading"));
//            BigDecimal currentReading = new BigDecimal(request.getParameter("currentReading"));
//            
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime readingDate = LocalDateTime.parse(request.getParameter("readingDate"), formatter);
//            
//            BigDecimal consumption = currentReading.subtract(previousReading);
//            
//            int month = readingDate.getMonthValue();
//            int year = readingDate.getYear();
//            
//            MeterReading meterReading = new MeterReading();
//            meterReading.setMeterId(meterId);
//            meterReading.setStaffId(staffId);
//            meterReading.setPreviousReading(previousReading);
//            meterReading.setCurrentReading(currentReading);
//            meterReading.setConsumption(consumption);
//            meterReading.setReadingDate(readingDate);
//            meterReading.setReadingMonth(month);
//            meterReading.setReadingYear(year);
//            meterReading.setStatus("Active");
//            
//            meterReadingDAO.addMeterReading(meterReading);
//            
//            response.sendRedirect(request.getContextPath() + "/meter-reading?month=" + month + "&year=" + year);
//            
//        } catch (SQLException | NumberFormatException e) {
//            handleError(request, response, "Error adding meter reading", e);
//        }
//    }
//    
//    private void updateMeterReading(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int readingId = Integer.parseInt(request.getParameter("readingId"));
//            int meterId = Integer.parseInt(request.getParameter("meterId"));
//            int staffId = ((Integer) request.getSession().getAttribute("staffId")); // From session
//            BigDecimal previousReading = new BigDecimal(request.getParameter("previousReading"));
//            BigDecimal currentReading = new BigDecimal(request.getParameter("currentReading"));
//            
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime readingDate = LocalDateTime.parse(request.getParameter("readingDate"), formatter);
//            
//            BigDecimal consumption = currentReading.subtract(previousReading);
//            
//            int month = readingDate.getMonthValue();
//            int year = readingDate.getYear();
//            
//            MeterReading meterReading = new MeterReading();
//            meterReading.setReadingId(readingId);
//            meterReading.setMeterId(meterId);
//            meterReading.setStaffId(staffId);
//            meterReading.setPreviousReading(previousReading);
//            meterReading.setCurrentReading(currentReading);
//            meterReading.setConsumption(consumption);
//            meterReading.setReadingDate(readingDate);
//            meterReading.setReadingMonth(month);
//            meterReading.setReadingYear(year);
//            meterReading.setStatus("Active");
//            
//            meterReadingDAO.updateMeterReading(meterReading);
//            
//            response.sendRedirect(request.getContextPath() + "/accountant/meter-reading?month=" + month + "&year=" + year);
//            
//        } catch (SQLException | NumberFormatException e) {
//            handleError(request, response, "Error updating meter reading", e);
//        }
//    }
//    
//    private void deleteMeterReading(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int readingId = Integer.parseInt(request.getParameter("id"));
//            
//            MeterReading reading = meterReadingDAO.getMeterReadingById(readingId);
//            int month = reading.getReadingMonth();
//            int year = reading.getReadingYear();
//            
//            meterReadingDAO.deleteMeterReading(readingId);
//            
//            response.sendRedirect(request.getContextPath() + "/accountant/meter-reading?month=" + month + "&year=" + year);
//            
//        } catch (SQLException | NumberFormatException e) {
//            handleError(request, response, "Error deleting meter reading", e);
//        }
//    }
//    
//    private void showMeterReadingDetails(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int readingId = Integer.parseInt(request.getParameter("id"));
//            MeterReading meterReading = meterReadingDAO.getMeterReadingById(readingId);
//            
//            request.setAttribute("meterReading", meterReading);
//            request.getRequestDispatcher("/accountant/meter-reading/details.jsp").forward(request, response);
//            
//        } catch (SQLException | NumberFormatException e) {
//            handleError(request, response, "Error retrieving meter reading details", e);
//        }
//    }
//    
//    private void showImportForm(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        request.getRequestDispatcher("/accountant/meter-reading/import.jsp").forward(request, response);
//    }
//    
//    private void generateExcelTemplate(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            List<Meter> meters = meterDAO.getAllMeters();
//            ByteArrayOutputStream excelStream = ExcelUtils.createMeterReadingTemplate(meters);
//            
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader("Content-Disposition", "attachment; filename=meter_reading_template.xlsx");
//            
//            response.getOutputStream().write(excelStream.toByteArray());
//            
//            int staffId = (Integer) request.getSession().getAttribute("staffId");
//            ExportLog log = new ExportLog();
//            log.setStaffId(staffId);
//            log.setFileName("meter_reading_template.xlsx");
//            log.setExportType("Template");
//            log.setRecordsCount(meters.size());
//            exportLogDAO.createExportLog(log);
//            
//        } catch (SQLException | IOException e) {
//            handleError(request, response, "Error generating Excel template", e);
//        }
//    }
//    
//    private void importMeterReadings(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int staffId = (Integer) request.getSession().getAttribute("staffId");
//            Part filePart = request.getPart("excelFile");
//            String fileName = getSubmittedFileName(filePart);
//            
//            ImportLog importLog = new ImportLog();
//            importLog.setStaffId(staffId);
//            importLog.setFileName(fileName);
//            importLog.setRecordsCount(0);
//            importLog.setStatus("Processing");
//            importLog.setErrorLog("");
//            int importId = importLogDAO.createImportLog(importLog);
//            
//            InputStream inputStream = filePart.getInputStream();
//            
//            int month = Integer.parseInt(request.getParameter("month"));
//            int year = Integer.parseInt(request.getParameter("year"));
//            
//            Map<String, Integer> meterMap = new HashMap<>();
//            List<Meter> meters = meterDAO.getAllMeters();
//            for (Meter meter : meters) {
//                meterMap.put(meter.getMeterNumber(), meter.getMeterId());
//            }
//            List<MeterReading> readings;
//            StringBuilder errorLog = new StringBuilder();
//            int successCount = 0;
//            
//            try {
//                readings = ExcelUtils.readMeterReadingsFromExcel(inputStream, meterMap, staffId, month, year);
//                
//                for (MeterReading reading : readings) {
//                    try {
//                        if (reading.getCurrentReading().compareTo(reading.getPreviousReading()) < 0) {
//                            errorLog.append("Error: Current reading less than previous reading for meter ")
//                                   .append(reading.getMeterNumber()).append("\n");
//                            continue;
//                        }
//                        
//                        meterReadingDAO.addMeterReading(reading);
//                        successCount++;
//                    } catch (Exception e) {
//                        errorLog.append("Error processing meter ").append(reading.getMeterId())
//                               .append(": ").append(e.getMessage()).append("\n");
//                    }
//                }
//                String status = successCount > 0 ? (errorLog.length() > 0 ? "Partial" : "Success") : "Failed";
//                importLogDAO.updateImportLogStatus(importId, status, successCount, errorLog.toString());
//                
//                request.setAttribute("importSuccess", true);
//                request.setAttribute("successCount", successCount);
//                request.setAttribute("totalCount", readings.size());
//                request.setAttribute("errorLog", errorLog.toString());
//                
//            } catch (InvalidFormatException e) {
//                importLogDAO.updateImportLogStatus(importId, "Failed", 0, "Invalid Excel format: " + e.getMessage());
//                request.setAttribute("importSuccess", false);
//                request.setAttribute("errorMessage", "Invalid Excel format. Please use the correct template.");
//            }
//            
//            request.getRequestDispatcher("/accountant/meter-reading/import-result.jsp").forward(request, response);
//            
//        } catch (SQLException | NumberFormatException e) {
//            handleError(request, response, "Error importing meter readings", e);
//        }
//    }
//    
//    private void exportMeterReadings(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            int month = Integer.parseInt(request.getParameter("month"));
//            int year = Integer.parseInt(request.getParameter("year"));
//            int staffId = (Integer) request.getSession().getAttribute("staffId");
//            
//            List<MeterReading> readings = meterReadingDAO.getMeterReadingsByMonth(month, year);
//            
//            String type = request.getParameter("type");
//            if ("report".equals(type)) {
//                List<UtilityBill> bills = convertToUtilityBills(readings);
//                ByteArrayOutputStream excelStream = ExcelUtils.createUtilityConsumptionReport(bills, month, year);
//                
//                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                String fileName = "utility_consumption_" + month + "_" + year + ".xlsx";
//                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//                
//                response.getOutputStream().write(excelStream.toByteArray());
//                
//                ExportLog log = new ExportLog();
//                log.setStaffId(staffId);
//                log.setFileName(fileName);
//                log.setExportType("Consumption Report");
//                log.setRecordsCount(bills.size());
//                exportLogDAO.createExportLog(log);
//            } else {
//                List<Meter> meters = meterDAO.getAllMeters();
//                ByteArrayOutputStream excelStream = ExcelUtils.createMeterReadingTemplate(meters);
//                
//                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                String fileName = "meter_readings_" + month + "_" + year + ".xlsx";
//                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//                
//                response.getOutputStream().write(excelStream.toByteArray());
//                
//                ExportLog log = new ExportLog();
//                log.setStaffId(staffId);
//                log.setFileName(fileName);
//                log.setExportType("Readings Export");
//                log.setRecordsCount(readings.size());
//                exportLogDAO.createExportLog(log);
//            }
//            
//        } catch (SQLException | NumberFormatException | IOException e) {
//            handleError(request, response, "Error exporting meter readings", e);
//        }
//    }
//    
//    private List<UtilityBill> convertToUtilityBills(List<MeterReading> readings) {
//        Map<Integer, UtilityBill> billMap = new HashMap<>();
//        
//        for (MeterReading reading : readings) {
//            int apartmentId = reading.getApartmentId();
//            
//            UtilityBill bill = billMap.get(apartmentId);
//            if (bill == null) {
//                bill = new UtilityBill();
//                bill.setApartmentId(apartmentId);
//                bill.setApartmentName(reading.getApartmentName());
//                bill.setOwnerName(reading.getOwnerName());
//                bill.setElectricityConsumption(BigDecimal.ZERO);
//                bill.setWaterConsumption(BigDecimal.ZERO);
//                bill.setElectricityCost(BigDecimal.ZERO);
//                bill.setWaterCost(BigDecimal.ZERO);
//                bill.setTotalAmount(BigDecimal.ZERO);
//                billMap.put(apartmentId, bill);
//            }
//            
//            if ("Electricity".equals(reading.getMeterType())) {
//                bill.setElectricityConsumption(reading.getConsumption());
//                BigDecimal cost = reading.getConsumption().multiply(new BigDecimal("3500"));
//                bill.setElectricityCost(cost);
//            } else if ("Water".equals(reading.getMeterType())) {
//                bill.setWaterConsumption(reading.getConsumption());
//                BigDecimal cost = reading.getConsumption().multiply(new BigDecimal("15000"));
//                bill.setWaterCost(cost);
//            }
//            bill.setTotalAmount(bill.getElectricityCost().add(bill.getWaterCost()));
//        }
//        
//        return new ArrayList<>(billMap.values());
//    }
//    
//    private String getSubmittedFileName(Part part) {
//        String contentDisp = part.getHeader("content-disposition");
//        String[] items = contentDisp.split(";");
//        for (String item : items) {
//            if (item.trim().startsWith("filename")) {
//                return item.substring(item.indexOf("=") + 2, item.length() - 1);
//            }
//        }
//        return "";
//    }
//    
//    private void handleError(HttpServletRequest request, HttpServletResponse response, 
//            String message, Exception e) throws ServletException, IOException {
//        e.printStackTrace();
//        request.setAttribute("errorCode", "500");
//        request.setAttribute("errorMessage", message + ": " + e.getMessage());
//        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
//    }
//
//}
