package controller.accountant;

import dao.InvoiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.InvoiceDetail;
import model.Invoices;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

@WebServlet(name = "makepaid", urlPatterns = {"/accountant/makepaid"})
public class makepaid extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("invoiceID"));
            System.out.println("Received request for invoice ID: " + id);
            InvoiceDAO idao = new InvoiceDAO();
            idao.updateStatusInvoice(id);
            generateInvoicePDF(id);
            System.out.println("Invoice PDF generated successfully!");

            response.sendRedirect("UpdateStatusInvoice");
        } catch (NumberFormatException e) {
            System.err.println("Invalid invoice ID: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid invoice ID");
        }
    }

   private void generateInvoicePDF(int id) throws IOException {
    String invoicesDir = "C:/Invoices";
    Path path = Paths.get(invoicesDir);
    if (!Files.exists(path)) {
        Files.createDirectories(path);
    }

    String filePath = invoicesDir + File.separator + "invoice_" + id + ".pdf";

    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);
        File fontFile = new File("C:/Windows/Fonts/arial.ttf");
        PDType0Font unicodeFont = PDType0Font.load(document, fontFile);

        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoices invoice = invoiceDAO.selectById(id);
        if (invoice == null) {
            throw new IOException("Invoice not found for ID: " + id);
        }

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(unicodeFont, 16);

            // Kích thước trang
            float pageWidth = page.getMediaBox().getWidth();
            float centerX = pageWidth / 2;
            float startY = 750;
            float lineSpacing = 30;

            // Tiêu đề hóa đơn
            contentStream.beginText();
            contentStream.newLineAtOffset(centerX - 200, startY);
            contentStream.showText("CÔNG TY CP QUẢN LÝ VẬN HÀNH & KHAI THÁC");
            contentStream.newLineAtOffset(50, -lineSpacing);
            contentStream.showText("Ban quản lý tòa nhà Apartment");
            contentStream.newLineAtOffset(-50, -lineSpacing);
            contentStream.setFont(unicodeFont, 18);
            contentStream.showText("PHIẾU THANH TOÁN HÓA ĐƠN");
            contentStream.endText();

            // Thông tin hóa đơn
            float infoStartY = startY - 120;
            contentStream.setFont(unicodeFont, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, infoStartY);
            contentStream.showText("Mã hóa đơn: " + invoice.getInvoiceID());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Tên chủ hộ: " + invoice.getResident().getFullName());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Căn hộ: " + invoice.getApartment().getApartmentName());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Ngày xuất: " + invoice.getPublicDateft());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Hạn thanh toán: " + invoice.getDueDateft());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Trạng thái: " + invoice.getStatus());
            contentStream.endText();

            // Khoảng cách giữa phần thông tin hóa đơn và bảng
            float tableStartY = infoStartY - (6 * lineSpacing) - 50; // Giảm trừ khoảng cách an toàn
            float rowHeight = 25;
            float tableWidth = 500;
            float col1Width = 50, col2Width = 250, col3Width = 100, col4Width = 100;
            float startX = 50;

            // Tiêu đề bảng
            contentStream.setLineWidth(1);
            contentStream.addRect(startX, tableStartY - rowHeight, tableWidth, rowHeight);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.setFont(unicodeFont, 14);
            contentStream.newLineAtOffset(startX + 5, tableStartY - 18);
            contentStream.showText("STT");
            contentStream.newLineAtOffset(col1Width, 0);
            contentStream.showText("Mô tả");
            contentStream.newLineAtOffset(col2Width, 0);
            contentStream.showText("Số tiền");
            contentStream.newLineAtOffset(col3Width, 0);
            contentStream.showText("Loại");
            contentStream.endText();

            // Dữ liệu bảng
            int row = 1;
            float tableEndY = tableStartY - rowHeight; // Vị trí kết thúc bảng

            for (InvoiceDetail detail : invoice.getDetails()) {
                float rowY = tableEndY - (row * rowHeight);
                contentStream.addRect(startX, rowY, tableWidth, rowHeight);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(unicodeFont, 12);
                contentStream.newLineAtOffset(startX + 20, rowY + 7);
                contentStream.showText(String.valueOf(row));
                contentStream.newLineAtOffset(col1Width, 0);
                contentStream.showText(detail.getDescription());
                contentStream.newLineAtOffset(col2Width, 0);
                contentStream.showText(String.format("%,.2f", detail.getAmount()));
                contentStream.newLineAtOffset(col3Width, 0);
                contentStream.showText(detail.getBillType());
                contentStream.endText();

                row++;
            }

            // Điều chỉnh vị trí các nội dung sau bảng
            float totalY = tableEndY - (row * rowHeight) - 50; // Cách bảng 50px
            contentStream.beginText();
            contentStream.setFont(unicodeFont, 14);
            contentStream.newLineAtOffset(350, totalY);
            contentStream.showText("Tổng tiền: " + String.format("%,.2f", invoice.getTotalAmount()));
            contentStream.endText();

            float dateY = totalY - 40;
            contentStream.beginText();
            contentStream.setFont(unicodeFont, 14);
            contentStream.newLineAtOffset(350, dateY);
            contentStream.showText("Hòa Lạc, " + invoice.getPaydateft());
            contentStream.endText();

            float signY = dateY - 50;
            contentStream.beginText();
            contentStream.setFont(unicodeFont, 14);
            contentStream.newLineAtOffset(350, signY);
            contentStream.showText("Kế toán trưởng");
            contentStream.endText();
        }

        document.save(filePath);
        System.out.println("PDF generated successfully at: " + filePath);
    }
}


}
