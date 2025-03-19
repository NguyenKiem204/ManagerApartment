/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.FundDAO;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import model.EmailUtil;
import model.InvoiceDetail;
import model.Invoices;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nguye
 */
@WebServlet(name = "paymentSuccess", urlPatterns = {"/owner/paymentSuccess"})
public class paymentSuccess extends HttpServlet {

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
            out.println("<title>Servlet paymentSuccess</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet paymentSuccess at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String invoiceId = request.getParameter("invoiceID");
            if (invoiceId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invoice ID is missing.");
                return;
            }

            int id = Integer.parseInt(invoiceId);
            System.out.println("Received request for invoice ID: " + id);

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            FundDAO fundDAO = new FundDAO();
            invoiceDAO.updateStatusInvoice(id);

            Invoices invoice = invoiceDAO.selectById(id);
            if (invoice == null) {
                throw new IOException("Invoice not found for ID: " + id);
            }

            if (invoice.getDetails() == null || invoice.getDetails().isEmpty()) {
                throw new SQLException("Invoice ID: " + id + " does not have any details.");
            }

            Map<Integer, Double> fundUpdates = new HashMap<>();

            for (InvoiceDetail detail : invoice.getDetails()) {
                double amount = detail.getAmount();
                int fundID = fundDAO.getFundIDByTypeFund(detail.getTypeFundID());

                if (fundID == -1) {
                    throw new SQLException("Không tìm thấy FundID tương ứng với TypeFundID: " + detail.getTypeFundID());
                }

                // Cộng dồn số tiền vào quỹ tương ứng
                fundUpdates.put(fundID, fundUpdates.getOrDefault(fundID, 0.0) + amount);
            }

            // Cập nhật số dư quỹ bằng updateFundBalance
            for (Map.Entry<Integer, Double> entry : fundUpdates.entrySet()) {
                int fundID = entry.getKey();
                double totalAmount = entry.getValue();

                // Gọi updateFundBalance nhưng KHÔNG gọi insertTransaction nữa
                boolean fundUpdated = fundDAO.updateFundBalance(fundID, totalAmount, "Income");
                if (!fundUpdated) {
                    throw new SQLException("Không thể cập nhật số dư quỹ cho FundID: " + fundID);
                }

                System.out.println("Updated FundID: " + fundID + " with amount: " + totalAmount);
            }

            EmailUtil.sendEmailSuccessPayment("cuongnmhe182472@fpt.edu.vn", invoice);
            request.getRequestDispatcher("PaymentSuccess.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid invoice ID: " + e.getMessage());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database error: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error: " + e.getMessage());
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
