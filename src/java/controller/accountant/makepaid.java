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
import java.io.FileOutputStream;

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
        try {
            int id = Integer.parseInt(request.getParameter("invoiceID"));
            InvoiceDAO idao = new InvoiceDAO();
            idao.updateStatusInvoice(id);
            generateInvoicePDF(id);// Chuyển hướng về trang UpdateStatusInvoice
            response.sendRedirect("UpdateStatusInvoice");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void generateInvoicePDF(int id) throws IOException {
        String filePath = "path/to/save/invoice_" + id + ".pdf"; // Đường dẫn lưu file PDF
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Lấy thông tin hóa đơn từ database
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            Invoices invoice = invoiceDAO.selectById(id);

            if (invoice == null) {
                document.add(new Paragraph("Invoice not found!"));
            } else {
                document.add(new Paragraph("Invoice ID: " + invoice.getInvoiceID()));
                document.add(new Paragraph("Description: " + invoice.getDescription()));
                document.add(new Paragraph("Total Amount: " + invoice.getTotalAmount()));
                document.add(new Paragraph("Apartment: " + invoice.getApartment().getApartmentName()));
                document.add(new Paragraph("Status: " + invoice.getStatus()));
                document.add(new Paragraph("Due Date: " + invoice.getDueDate().toString()));
                document.add(new Paragraph("Public Date: " + invoice.getPublicDate().toString()));
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error while generating PDF", e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
