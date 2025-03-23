/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.PaymentTransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckPaymentStatusServlet", urlPatterns = {"/owner/CheckPaymentStatusServlet"})
public class CheckPaymentStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckPaymentStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckPaymentStatusServlet at " + request.getContextPath() + "</h1>");
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
    private final PaymentTransactionDAO transactionDAO = new PaymentTransactionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionId = request.getParameter("transactionId");

        if (transactionId == null || transactionId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu transactionId");
            return;
        }

        String status = transactionDAO.getTransactionStatus(transactionId);

        // Kiểm tra nếu mã giao dịch không tồn tại
        if (status == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"not_found\"}");
            return;
        }

        // Nếu giao dịch thành công, xóa các giao dịch có cùng mã hóa đơn
        if ("completed".equals(status)) {
            int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
            int deletedRows = transactionDAO.deleteTransactionsByInvoiceID(invoiceID);
            System.out.println("Đã xóa " + deletedRows + " giao dịch có cùng mã hóa đơn: " + invoiceID);
        }

        // Trả về JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"" + status + "\"}");
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
