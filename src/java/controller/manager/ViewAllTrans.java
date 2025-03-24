/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ApartmentDAO;
import dao.ExpenseDAO;
import dao.FundDAO;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import model.Invoices;
import model.TransactionFund;

/**
 *
 * @author nguye
 */
@WebServlet(name = "ViewAllTrans", urlPatterns = {"/manager/ViewAllTrans"})
public class ViewAllTrans extends HttpServlet {

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
            out.println("<title>Servlet ViewAllTrans</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewAllTrans at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        // Lấy các tham số lọc từ request
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String transactionType = request.getParameter("transactionType");
        String amountRange = request.getParameter("amountRange");
        String rowsPerPageStr = request.getParameter("rowsPerPage");
        String pageStr = request.getParameter("page");

        // Chuyển đổi các tham số lọc
        LocalDate fromDate = (fromDateStr != null && !fromDateStr.isEmpty()) ? LocalDate.parse(fromDateStr) : null;
        LocalDate toDate = (toDateStr != null && !toDateStr.isEmpty()) ? LocalDate.parse(toDateStr) : null;
        int rowsPerPage = (rowsPerPageStr != null && !rowsPerPageStr.isEmpty()) ? Integer.parseInt(rowsPerPageStr) : 10;
        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;

        // Xử lý khoảng giá trị amount
        Double minAmount = null;
        Double maxAmount = null;
        if (amountRange != null && !amountRange.isEmpty()) {
            String[] range = amountRange.split("-");
            if (range.length == 2) {
                minAmount = range[0].isEmpty() ? null : Double.parseDouble(range[0]);
                maxAmount = range[1].isEmpty() ? null : Double.parseDouble(range[1]);
            }
        }

        // Gọi phương thức getAllTransactions
        FundDAO fdao = new FundDAO();
        List<TransactionFund> allTransactions = fdao.getAllTransactions(fromDate, toDate, transactionType, minAmount, maxAmount);

        // Tính toán phân trang
        int totalTransactions = allTransactions.size();
        int totalPages = (int) Math.ceil((double) totalTransactions / rowsPerPage);
        int start = (page - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalTransactions);
        List<TransactionFund> transactions = allTransactions.subList(start, end);

        // Đặt các thuộc tính vào session và request
        session.setAttribute("allTransactions", transactions);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("rowsPerPage", rowsPerPage);
        request.setAttribute("selectedAmountRange", amountRange);
        request.setAttribute("selectedFromDate", fromDateStr);
        request.setAttribute("selectedToDate", toDateStr);
        request.setAttribute("selectedTransactionType", transactionType);

        // Chuyển hướng đến JSP
        request.getRequestDispatcher("ViewAllTrans.jsp").forward(request, response);
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
