/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import config.SepayConfig;
import dao.InvoiceDAO;
import dao.PaymentTransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import model.Invoices;
import model.TypeBill;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import model.Resident;

/**
 *
 * @author nguye
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/owner/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

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
            out.println("<title>Servlet PaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");

        // Kiểm tra nếu không có cư dân đăng nhập
        if (resident == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String invoiceID = request.getParameter("invoiceID");
        if (invoiceID == null || invoiceID.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu invoiceID");
            return;
        }

        try {
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            PaymentTransactionDAO transactionDAO = new PaymentTransactionDAO();
            Invoices invoice = invoiceDAO.selectById(Integer.parseInt(invoiceID));

            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn");
                return;
            }

            // Kiểm tra quyền truy cập: Chỉ cư dân sở hữu hóa đơn mới được thanh toán
            if (invoice.getResident().getResidentId() != resident.getResidentId()) {
                request.setAttribute("errorCode", "403");
                request.setAttribute("errorMessage", "Bạn không có quyền thanh toán hóa đơn này!");
                request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
                return;
            }

            double totalAmount = invoice.getTotalAmount() + invoice.getMuon();
            String transactionId = RandomStringGenerator.generateRandomString(); 
            String encodedTransactionId = URLEncoder.encode(transactionId, StandardCharsets.UTF_8.toString());

            // URL thanh toán SePay (transactionId là mô tả giao dịch)
            String paymentUrl = String.format(
                    "https://qr.sepay.vn/img?bank=MBBank&acc=686868922004&template=compact&amount=%.2f&des=%s",
                    totalAmount, encodedTransactionId
            );

            // Lưu transaction vào database
            transactionDAO.createTransaction(invoiceID, transactionId, totalAmount, "Card");
            transactionDAO.deleteExpiredTransactions();

            // Chuyển hướng đến trang thanh toán
            request.setAttribute("paymentUrl", paymentUrl);
            request.setAttribute("transactionId", encodedTransactionId);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("invoice", invoice);
            request.getRequestDispatcher("OnlinePayment.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý thanh toán");
        }
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
