import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import dao.ApartmentDAO;
import model.Apartment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ExportApartmentExcelServlet", urlPatterns = {"/accountant/export-apartment-excel"})
public class ExportApartmentExcelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=apartments.xlsx");

        try {
            exportDataApartments(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("Error exporting data: " + e.getMessage());
        }
    }

    private void exportDataApartments(HttpServletResponse response) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            ApartmentDAO apartmentDAO = new ApartmentDAO();
            List<Apartment> apartments = apartmentDAO.selectAll();

            Sheet sheet = workbook.createSheet("Apartments");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ApartmentID", "ApartmentName", "Block", "Status", "Type", "OwnerID", "OldElectric", "NewElectric", "OldWater", "NewWater"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            int rowNum = 1;
            for (Apartment apartment : apartments) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(apartment.getApartmentId());
                row.createCell(1).setCellValue(apartment.getApartmentName());
                row.createCell(2).setCellValue(apartment.getBlock());
                row.createCell(3).setCellValue(apartment.getStatus());
                row.createCell(4).setCellValue(apartment.getType());
                row.createCell(5).setCellValue(apartment.getOwnerId());

                for (int i = 0; i < headers.length; i++) {
                    if (row.getCell(i) == null) {
                        row.createCell(i);
                    }
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (OutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
                outputStream.flush();
            }
        }
    }
}
