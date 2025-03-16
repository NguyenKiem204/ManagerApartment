/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Invoices;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.InvoiceDetail;

/**
 *
 * @author nguye
 */
@WebServlet(name = "makepaid", urlPatterns = {"/accountant/makepaid"})
public class makepaid extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet makepaid</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet makepaid at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        try {
//            int id = Integer.parseInt(request.getParameter("invoiceID"));
//            InvoiceDAO idao = new InvoiceDAO();
//            idao.updateStatusInvoice(id); // Cập nhật trạng thái hóa đơn
//
////            generateInvoicePDF(id);
//
//            response.sendRedirect("/accountant/UpdateStatusInvoice");
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
    }

//   private void generateInvoicePDF(int id) throws IOException {
//    // Lấy thư mục lưu file PDF trong webapps (nếu chạy trên Tomcat)
//    String invoicesDir = getServletContext().getRealPath("/invoices");
//    
//    if (invoicesDir == null) {
//        // Nếu chạy trên môi trường không có realPath, lưu vào thư mục gốc project
//        invoicesDir = System.getProperty("user.dir") + File.separator + "invoices";
//    }
//
//    // Đảm bảo thư mục tồn tại
//    File directory = new File(invoicesDir);
//    if (!directory.exists()) {
//        directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
//    }
//
//    // Đường dẫn file PDF
//    String filePath = invoicesDir + File.separator + "invoice_" + id + ".pdf";
//
//    com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//    try {
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        document.open();
//
//        // Lấy dữ liệu hóa đơn
//        InvoiceDAO invoiceDAO = new InvoiceDAO();
//        Invoices invoice = invoiceDAO.selectById(id);
//
//        if (invoice == null) {
//            document.add(new Paragraph("Invoice not found!"));
//        } else {
//            document.add(new Paragraph("CÔNG TY CỔ PHẦN QUẢN LÝ VẬN HÀNH & KHAI THÁC"));
//            document.add(new Paragraph("Ban quản lý tòa nhà Apartment"));
//            document.add(new Paragraph("Phiếu thanh toán hóa đơn"));
//            document.add(new Paragraph("Ngày TB: " + invoice.getPublicDate()));
//            document.add(new Paragraph("Tên chủ hộ: " + invoice.getResident().getFullName()));
//            document.add(new Paragraph("Địa chỉ: " + invoice.getApartment().getApartmentName()));
//
//            // Tạo bảng thông tin chi tiết hóa đơn
//            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(3);
//            table.addCell("Dịch vụ");
//            table.addCell("Thời gian");
//            table.addCell("Thành tiền");
//
//            for (InvoiceDetail detail : invoice.getDetails()) {
//                table.addCell(detail.getDescription());
//                table.addCell(detail.getBillType());
//                table.addCell(String.valueOf(detail.getAmount()));
//            }
//
//            document.add(table);
//            document.add(new Paragraph("Tổng tiền: " + invoice.getTotalAmount()));
//
//            document.close();
//            System.out.println("✅ PDF generated successfully at: " + filePath);
//        }
//    } catch (DocumentException e) {
//        throw new IOException("❌ Error while generating PDF", e);
//    }
//}


    private String convertNumberToWords(double number) {
        // Hàm chuyển đổi số thành chữ (có thể sử dụng thư viện hoặc tự viết)
        // Ví dụ đơn giản:
        if (number == 1000000) {
            return "Một triệu đồng";
        }
        return "Số tiền bằng chữ";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
