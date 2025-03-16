/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dao.MeterReadingDAO;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

// Lớp tiện ích xử lý Excel
public class ExcelUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

   public static ByteArrayOutputStream createMeterReadingReport(List<MeterReading> meterReadings) throws IOException {
    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Meter Readings Report");
        // Tạo style cho tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        
        // Tạo style cho dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        
        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Apartment", "Meter Type", "Meter Number", "Previous Meter Reading", "New Meter Reading", "Consumption", "Reading Date", "Staff", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Ghi dữ liệu vào file
        int rowNum = 1;
        for (MeterReading reading : meterReadings) {
            Row row = sheet.createRow(rowNum++);
            
            // Create cells and apply data style to each cell
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(reading.getApartmentName());
            cell0.setCellStyle(dataStyle);
            
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(reading.getMeterType().equals("Electricity") ? "Điện" : "Nước");
            cell1.setCellStyle(dataStyle);
            
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(reading.getMeterNumber());
            cell2.setCellStyle(dataStyle);
            
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(reading.getPreviousReading() != null ? reading.getPreviousReading().doubleValue() : 0.0);
            cell3.setCellStyle(dataStyle);
            
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(reading.getCurrentReading() != null ? reading.getCurrentReading().doubleValue() : 0.0);
            cell4.setCellStyle(dataStyle);
            
            Cell cell5 = row.createCell(5);
            cell5.setCellValue(reading.getConsumption() != null ? reading.getConsumption().doubleValue() : 0.0);
            cell5.setCellStyle(dataStyle);
            
            Cell cell6 = row.createCell(6);
            cell6.setCellValue(reading.getFormattedDate());
            cell6.setCellStyle(dataStyle);
            
            Cell cell7 = row.createCell(7);
            cell7.setCellValue(reading.getStaffName());
            cell7.setCellStyle(dataStyle);
            
            Cell cell8 = row.createCell(8);
            cell8.setCellValue(reading.getStatus().equals("Active") ? "Hoạt động" : "Đã xóa");
            cell8.setCellStyle(dataStyle);
        }
        
        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Xuất workbook thành ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }
}
    // Tạo template Excel để nhập chỉ số đồng hồ điện nước
//    public static ByteArrayOutputStream createMeterReadingTemplate(List<Meter> meters) throws IOException {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            // Tạo style cho tiêu đề
//            CellStyle headerStyle = workbook.createCellStyle();
//            Font headerFont = workbook.createFont();
//            headerFont.setBold(true);
//            headerFont.setFontHeightInPoints((short) 12);
//            headerStyle.setFont(headerFont);
//            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            headerStyle.setBorderTop(BorderStyle.THIN);
//            headerStyle.setBorderBottom(BorderStyle.THIN);
//            headerStyle.setBorderLeft(BorderStyle.THIN);
//            headerStyle.setBorderRight(BorderStyle.THIN);
//
//            // Tạo style cho dữ liệu
//            CellStyle dataStyle = workbook.createCellStyle();
//            dataStyle.setBorderTop(BorderStyle.THIN);
//            dataStyle.setBorderBottom(BorderStyle.THIN);
//            dataStyle.setBorderLeft(BorderStyle.THIN);
//            dataStyle.setBorderRight(BorderStyle.THIN);
//
//            // Tạo style cho các ô cần nhập liệu
//            CellStyle inputStyle = workbook.createCellStyle();
//            inputStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//            inputStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            inputStyle.setBorderTop(BorderStyle.THIN);
//            inputStyle.setBorderBottom(BorderStyle.THIN);
//            inputStyle.setBorderLeft(BorderStyle.THIN);
//            inputStyle.setBorderRight(BorderStyle.THIN);
//
//            // Tạo các sheet cho điện và nước
//            Sheet electricitySheet = workbook.createSheet("Điện");
//            Sheet waterSheet = workbook.createSheet("Nước");
//
//            // Tạo sheet hướng dẫn
//            Sheet instructionSheet = workbook.createSheet("Hướng dẫn");
//            createInstructionSheet(instructionSheet, headerStyle, dataStyle);
//
//            // Tạo tiêu đề và dữ liệu cho sheet điện
//            createMeterSheet(electricitySheet, "Electricity", meters, headerStyle, dataStyle, inputStyle);
//
//            // Tạo tiêu đề và dữ liệu cho sheet nước
//            createMeterSheet(waterSheet, "Water", meters, headerStyle, dataStyle, inputStyle);
//
//            // Điều chỉnh chiều rộng cột
//            for (int i = 0; i < 7; i++) {
//                electricitySheet.autoSizeColumn(i);
//                waterSheet.autoSizeColumn(i);
//            }
//
//            // Xuất workbook thành ByteArrayOutputStream
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            workbook.write(outputStream);
//            return outputStream;
//        }
//    }

// Tạo nội dung cho một sheet đồng hồ (điện hoặc nước)
private static void createMeterSheet(Sheet sheet, String meterType, List<Meter> meters,
        CellStyle headerStyle, CellStyle dataStyle, CellStyle inputStyle) {
    // Tạo tiêu đề
    Row titleRow = sheet.createRow(0);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue("METER READING TABLE " + (meterType.equals("Electricity") ? "ELECTRICITY" : "Water"));
    titleCell.setCellStyle(headerStyle);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

    // Tạo hàng tiêu đề cột
    Row headerRow = sheet.createRow(1);
    String[] headers = {"Apartment ID", "Apartment Name", "Owner", "Meter ID", "Previous Reading", "Current Reading", "Reading Date"};
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
    }

    // Tạo một định dạng ngày giờ tùy chỉnh
    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
    dateStyle.cloneStyleFrom(inputStyle);
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));

    // Lọc đồng hồ theo loại và điền dữ liệu
    int rowNum = 2;
    for (Meter meter : meters) {
        if (meter.getMeterType().equals(meterType)) {
            Row row = sheet.createRow(rowNum++);

            // Điền thông tin cố định
            row.createCell(0).setCellValue(meter.getApartmentId());
            row.getCell(0).setCellStyle(dataStyle);

            row.createCell(1).setCellValue(meter.getApartmentName());
            row.getCell(1).setCellStyle(dataStyle);

            row.createCell(2).setCellValue(meter.getOwnerName());
            row.getCell(2).setCellStyle(dataStyle);

            row.createCell(3).setCellValue(meter.getMeterNumber());
            row.getCell(3).setCellStyle(dataStyle);

            // Các ô cần nhập liệu
            Cell previousReadingCell = row.createCell(4);
            previousReadingCell.setCellStyle(dataStyle);

            Cell currentReadingCell = row.createCell(5);
            currentReadingCell.setCellStyle(inputStyle);

            // Tạo ô ngày giờ tự động
            Cell readingDateCell = row.createCell(6);
            // Tạo công thức để theo dõi thay đổi ở ô chỉ số mới
            // Khi ô chỉ số mới (cột F) thay đổi, ô ngày (cột G) sẽ tự động cập nhật thành thời gian hiện tại
            readingDateCell.setCellFormula("IF(F" + (row.getRowNum() + 1) + "<>\"\",NOW(),\"\")");
            readingDateCell.setCellStyle(dateStyle);
        }
    }

    // Thiết lập tính toán công thức tự động cho workbook
    sheet.getWorkbook().setForceFormulaRecalculation(true);
}
    // Tạo sheet hướng dẫn
    private static void createInstructionSheet(Sheet sheet, CellStyle headerStyle, CellStyle dataStyle) {
        // Tạo tiêu đề
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("DATA ENTRY INSTRUCTIONS");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        // Tạo các hướng dẫn
        String[][] instructions = {
            {"1.", "Enter data only in the yellow-highlighted cells."},
            {"2.", "The current reading must be greater than or equal to the previous reading."},
            {"3.", "Enter the reading date in the format DD/MM/YYYY (e.g., 15/03/2025)."},
            {"4.", "Do not change the file structure; do not add or remove columns."},
            {"5.", "Ensure all apartments have accurate and complete information."},
            {"6.", "After completing, save the file and upload it to the system."}
        };

        for (int i = 0; i < instructions.length; i++) {
            Row row = sheet.createRow(i + 2);
            Cell numCell = row.createCell(0);
            numCell.setCellValue(instructions[i][0]);
            numCell.setCellStyle(dataStyle);

            Cell instructionCell = row.createCell(1);
            instructionCell.setCellValue(instructions[i][1]);
            instructionCell.setCellStyle(dataStyle);
        }

        // Điều chỉnh chiều rộng cột
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(1, 15000);
    }

// Tạo template Excel để nhập chỉ số đồng hồ điện nước
public static ByteArrayOutputStream createMeterReadingTemplate(List<Meter> meters) throws IOException {
    try (Workbook workbook = new XSSFWorkbook()) {
        // Tạo style cho tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Tạo style cho dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Tạo style cho các ô cần nhập liệu
        CellStyle inputStyle = workbook.createCellStyle();
        inputStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        inputStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        inputStyle.setBorderTop(BorderStyle.THIN);
        inputStyle.setBorderBottom(BorderStyle.THIN);
        inputStyle.setBorderLeft(BorderStyle.THIN);
        inputStyle.setBorderRight(BorderStyle.THIN);

        // Tạo style cho ngày tháng
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(inputStyle);
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));

        // Tạo các sheet cho điện và nước
        Sheet electricitySheet = workbook.createSheet("Electricity");
        Sheet waterSheet = workbook.createSheet("Water");

        // Tạo sheet hướng dẫn
        Sheet instructionSheet = workbook.createSheet("Instructions");
        createInstructionSheet(instructionSheet, headerStyle, dataStyle);

        // Tạo tiêu đề và dữ liệu cho sheet điện
        createMeterSheet(electricitySheet, "Electricity", meters, headerStyle, dataStyle, inputStyle, dateStyle);

        // Tạo tiêu đề và dữ liệu cho sheet nước
        createMeterSheet(waterSheet, "Water", meters, headerStyle, dataStyle, inputStyle, dateStyle);

        // Điều chỉnh chiều rộng cột
        for (int i = 0; i < 7; i++) {
            electricitySheet.autoSizeColumn(i);
            waterSheet.autoSizeColumn(i);
        }

        // Xuất workbook thành ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }
}

// Tạo nội dung cho một sheet đồng hồ (điện hoặc nước)
private static void createMeterSheet(Sheet sheet, String meterType, List<Meter> meters,
        CellStyle headerStyle, CellStyle dataStyle, CellStyle inputStyle, CellStyle dateStyle) {
    // Tạo tiêu đề
    Row titleRow = sheet.createRow(0);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue("BẢNG GHI CHỈ SỐ " + (meterType.equals("Electricity") ? "Electricity" : "Water"));
    titleCell.setCellStyle(headerStyle);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
    
    // Tạo hàng tiêu đề cột
    Row headerRow = sheet.createRow(1);
    String[] headers = {"Apartment ID", "Apartment Name", "Owner", "Meter ID", "Previous Reading", "Current Reading", "Reading Date"};
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
    }
    
    // Lọc đồng hồ theo loại và điền dữ liệu
    int rowNum = 2;
    for (Meter meter : meters) {
        if (meter.getMeterType().equals(meterType)) {
            Row row = sheet.createRow(rowNum++);
            
            // Điền thông tin cố định
            row.createCell(0).setCellValue(meter.getApartmentId());
            row.getCell(0).setCellStyle(dataStyle);
            
            row.createCell(1).setCellValue(meter.getApartmentName());
            row.getCell(1).setCellStyle(dataStyle);
            
            row.createCell(2).setCellValue(meter.getOwnerName());
            row.getCell(2).setCellStyle(dataStyle);
            
            row.createCell(3).setCellValue(meter.getMeterNumber());
            row.getCell(3).setCellStyle(dataStyle);
            
            // Các ô cần nhập liệu - Lấy chỉ số cũ (nếu có)
            Cell previousReadingCell = row.createCell(4);
            // Thay thế meter.getPreviousReading() với giá trị thích hợp
            // Có thể sử dụng một giá trị mặc định hoặc lấy từ chỉ số đọc gần nhất
            try {
                // Lấy chỉ số đọc gần nhất cho đồng hồ này
                MeterReadingDAO meterReadingDAO = new MeterReadingDAO();
                MeterReading latestReading = meterReadingDAO.getLatestMeterReading(meter.getMeterId());
                if (latestReading != null) {
                    previousReadingCell.setCellValue(latestReading.getCurrentReading().doubleValue());
                } else {
                    previousReadingCell.setCellValue(0.0); // Giá trị mặc định nếu không có chỉ số trước đó
                }
            } catch (SQLException e) {
                // Xử lý ngoại lệ, đặt giá trị mặc định
                previousReadingCell.setCellValue(0.0);
                e.printStackTrace();
            }
            previousReadingCell.setCellStyle(dataStyle);
            
            // Đặt DataValidation để tự động cập nhật ngày khi nhập chỉ số mới
            Cell currentReadingCell = row.createCell(5);
            currentReadingCell.setCellStyle(inputStyle);
            
            // Ô ngày giờ - trống ban đầu
            Cell readingDateCell = row.createCell(6);
            readingDateCell.setCellStyle(dateStyle);
            
            // Thêm một Worksheet Event để tự động cập nhật ngày khi chỉ số mới thay đổi
            // Sử dụng SheetConditionalFormatting để tạo một quy tắc điều kiện
            SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
            
            // Tạo quy tắc: Khi ô F thay đổi (không còn trống), tự động cập nhật ô G
            ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule("ISBLANK(F" + (rowNum) + ")=FALSE");
            
            // Đặt công thức NOW() cho ô ngày giờ
            readingDateCell.setCellFormula("IF(F" + (rowNum) + "<>\"\",NOW(),\"\")");
        }
    }
    
    // Thiết lập tính toán công thức tự động cho workbook
    sheet.getWorkbook().setForceFormulaRecalculation(true);
}
// Đọc dữ liệu từ file Excel đã nhập
public static List<MeterReading> readMeterReadingsFromExcel(InputStream excelFile,
        Map<String, Integer> meterMap,
        int staffId,
        int month,
        int year) throws IOException, InvalidFormatException {
    List<MeterReading> readings = new ArrayList<>();

    try (Workbook workbook = WorkbookFactory.create(excelFile)) {
        // Đọc sheet điện
        Sheet electricitySheet = workbook.getSheet("Electricity");
        if (electricitySheet != null) {
            readings.addAll(readMeterReadingsFromSheet(electricitySheet, meterMap, staffId, month, year));
        }

        // Đọc sheet nước
        Sheet waterSheet = workbook.getSheet("Water");
        if (waterSheet != null) {
            readings.addAll(readMeterReadingsFromSheet(waterSheet, meterMap, staffId, month, year));
        }
    }

    return readings;
}

// Đọc dữ liệu từ một sheet
private static List<MeterReading> readMeterReadingsFromSheet(Sheet sheet,
        Map<String, Integer> meterMap,
        int staffId,
        int month,
        int year) {
    List<MeterReading> readings = new ArrayList<>();

    // Bỏ qua 2 hàng đầu (tiêu đề)
    for (int i = 2; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null) {
            continue;
        }

        // Đọc dữ liệu từ các cột
        String meterNumber = getStringCellValue(row.getCell(3));
        if (meterNumber == null || meterNumber.isEmpty()) {
            continue;
        }

        Integer meterId = meterMap.get(meterNumber);
        if (meterId == null) {
            continue;
        }

        BigDecimal previousReading = getBigDecimalCellValue(row.getCell(4));
        BigDecimal currentReading = getBigDecimalCellValue(row.getCell(5));
        
        // Kiểm tra nếu chỉ số mới không được nhập thì bỏ qua
        if (previousReading == null || currentReading == null) {
            continue;
        }
        
        // Đọc ngày ghi chỉ số hoặc sử dụng thời gian hiện tại nếu không có
        LocalDateTime readingDate = getDateTimeCellValue(row.getCell(6));
        if (readingDate == null) {
            // Nếu không có ngày giờ, sử dụng thời điểm hiện tại
            readingDate = LocalDateTime.now();
        }

        // Tạo đối tượng MeterReading
        MeterReading reading = new MeterReading();
        reading.setMeterId(meterId);
        reading.setReadingDate(readingDate);
        reading.setPreviousReading(previousReading);
        reading.setCurrentReading(currentReading);
        reading.setConsumption(currentReading.subtract(previousReading));
        reading.setStaffId(staffId);
        reading.setReadingMonth(month);
        reading.setReadingYear(year);
        reading.setStatus("Active");

        readings.add(reading);
    }
    return readings;
}

// Lấy giá trị chuỗi từ một ô
private static String getStringCellValue(Cell cell) {
    if (cell == null) {
        return null;
    }
    DataFormatter formatter = new DataFormatter();
    return formatter.formatCellValue(cell).trim();
}

// Lấy giá trị số từ một ô
private static BigDecimal getBigDecimalCellValue(Cell cell) {
    if (cell == null) {
        return null;
    }

    switch (cell.getCellType()) {
        case NUMERIC:
            return BigDecimal.valueOf(cell.getNumericCellValue());

        case STRING:
        try {
            String value = cell.getStringCellValue().trim();
            if (value.isEmpty()) {
                return null;
            }
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }

        case FORMULA:
        try {
            return BigDecimal.valueOf(cell.getNumericCellValue()); // Công thức trả về số
        } catch (IllegalStateException e) {
            return null;
        }

        default:
            return null;
    }
}

// Lấy giá trị ngày giờ từ một ô
private static LocalDateTime getDateTimeCellValue(Cell cell) {
    if (cell == null) {
        return null;
    }
    
    try {
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
                break;
                
            case STRING:
                String dateStr = cell.getStringCellValue().trim();
                if (!dateStr.isEmpty()) {
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        return LocalDateTime.parse(dateStr, dtf);
                    } catch (Exception e) {
                        // Nếu không phân tích được, trả về null
                    }
                }
                break;
                
            case FORMULA:
                if (cell.getCachedFormulaResultType() == CellType.NUMERIC && 
                    DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
                break;
                
            default:
                break;
        }
    } catch (Exception e) {
        // Xử lý ngoại lệ
        System.err.println("Error parsing date: " + e.getMessage());
    }
    
    return null;
}

    // Lấy giá trị ngày từ một ô
    private static Date getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return null;
            case STRING:
                try {
                return DATE_FORMAT.parse(cell.getStringCellValue().trim());
            } catch (Exception e) {
                return null;
            }
            default:
                return null;
        }
    }

    // Tạo file Excel báo cáo tiêu thụ điện nước
    public static ByteArrayOutputStream createUtilityConsumptionReport(List<UtilityBill> bills,
            int month,
            int year) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo style cho tiêu đề
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Tạo style cho dữ liệu
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            // Tạo style cho số tiền
            CellStyle moneyStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            moneyStyle.setDataFormat(format.getFormat("#,##0"));
            moneyStyle.setBorderTop(BorderStyle.THIN);
            moneyStyle.setBorderBottom(BorderStyle.THIN);
            moneyStyle.setBorderLeft(BorderStyle.THIN);
            moneyStyle.setBorderRight(BorderStyle.THIN);

            // Tạo sheet báo cáo
            Sheet sheet = workbook.createSheet("Consumption Report");

            // Tạo tiêu đề báo cáo
            Row titleRow1 = sheet.createRow(0);
            Cell titleCell1 = titleRow1.createCell(0);
            titleCell1.setCellValue("ELECTRICITY & WATER CONSUMPTION REPORT");
            titleCell1.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

            Row titleRow2 = sheet.createRow(1);
            Cell titleCell2 = titleRow2.createCell(0);
            titleCell2.setCellValue("Month " + month + "/" + year);
            titleCell2.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

            // Tạo hàng tiêu đề cột
            Row headerRow = sheet.createRow(3);
            String[] headers = {
                "No.", "Apartment ID", "Apartment Name", "Owner", "Electricity Consumption (kWh)",
                "Electricity Cost (VND)", "Water Consumption (m³)", "Water Cost (VND)", "Total Cost (VND)"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Điền dữ liệu
            int rowNum = 4;
            int stt = 1;
            BigDecimal totalElectricityConsumption = BigDecimal.ZERO;
            BigDecimal totalElectricityCost = BigDecimal.ZERO;
            BigDecimal totalWaterConsumption = BigDecimal.ZERO;
            BigDecimal totalWaterCost = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (UtilityBill bill : bills) {
                Row row = sheet.createRow(rowNum++);

                // STT
                Cell sttCell = row.createCell(0);
                sttCell.setCellValue(stt++);
                sttCell.setCellStyle(dataStyle);

                // Mã căn hộ
                Cell apartmentIdCell = row.createCell(1);
                apartmentIdCell.setCellValue(bill.getApartmentId());
                apartmentIdCell.setCellStyle(dataStyle);

                // Tên căn hộ
                Cell apartmentNameCell = row.createCell(2);
                apartmentNameCell.setCellValue(bill.getApartmentName());
                apartmentNameCell.setCellStyle(dataStyle);

                // Chủ hộ
                Cell ownerNameCell = row.createCell(3);
                ownerNameCell.setCellValue(bill.getOwnerName());
                ownerNameCell.setCellStyle(dataStyle);

                // Tiêu thụ điện
                Cell elecConsumptionCell = row.createCell(4);
                elecConsumptionCell.setCellValue(bill.getElectricityConsumption().doubleValue());
                elecConsumptionCell.setCellStyle(dataStyle);
                totalElectricityConsumption = totalElectricityConsumption.add(bill.getElectricityConsumption());

                // Tiền điện
                Cell elecCostCell = row.createCell(5);
                elecCostCell.setCellValue(bill.getElectricityCost().doubleValue());
                elecCostCell.setCellStyle(moneyStyle);
                totalElectricityCost = totalElectricityCost.add(bill.getElectricityCost());

                // Tiêu thụ nước
                Cell waterConsumptionCell = row.createCell(6);
                waterConsumptionCell.setCellValue(bill.getWaterConsumption().doubleValue());
                waterConsumptionCell.setCellStyle(dataStyle);
                totalWaterConsumption = totalWaterConsumption.add(bill.getWaterConsumption());

                // Tiền nước
                Cell waterCostCell = row.createCell(7);
                waterCostCell.setCellValue(bill.getWaterCost().doubleValue());
                waterCostCell.setCellStyle(moneyStyle);
                totalWaterCost = totalWaterCost.add(bill.getWaterCost());

                // Tổng tiền
                Cell totalCell = row.createCell(8);
                totalCell.setCellValue(bill.getTotalAmount().doubleValue());
                totalCell.setCellStyle(moneyStyle);
                totalAmount = totalAmount.add(bill.getTotalAmount());
            }

            // Tạo hàng tổng cộng
            Row totalRow = sheet.createRow(rowNum);

            Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("TOTAL");
            totalLabelCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 3));

            Cell totalElecConsumptionCell = totalRow.createCell(4);
            totalElecConsumptionCell.setCellValue(totalElectricityConsumption.doubleValue());
            totalElecConsumptionCell.setCellStyle(headerStyle);

            Cell totalElecCostCell = totalRow.createCell(5);
            totalElecCostCell.setCellValue(totalElectricityCost.doubleValue());
            totalElecCostCell.setCellStyle(headerStyle);

            Cell totalWaterConsumptionCell = totalRow.createCell(6);
            totalWaterConsumptionCell.setCellValue(totalWaterConsumption.doubleValue());
            totalWaterConsumptionCell.setCellStyle(headerStyle);

            Cell totalWaterCostCell = totalRow.createCell(7);
            totalWaterCostCell.setCellValue(totalWaterCost.doubleValue());
            totalWaterCostCell.setCellStyle(headerStyle);

            Cell grandTotalCell = totalRow.createCell(8);
            grandTotalCell.setCellValue(totalAmount.doubleValue());
            grandTotalCell.setCellStyle(headerStyle);

            // Điều chỉnh chiều rộng cột
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }

            // Xuất workbook thành ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        }
    }
}
