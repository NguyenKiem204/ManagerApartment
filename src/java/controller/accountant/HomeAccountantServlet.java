/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.FundDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FundManagement;
import model.TransactionFund;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "HomeAccountantServlet", urlPatterns = {"/accountant/home"})
public class HomeAccountantServlet extends HttpServlet {

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
            out.println("<title>Servlet HomeAccountantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeAccountantServlet at " + request.getContextPath() + "</h1>");
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
        FundDAO fdao = new FundDAO();
        String filter = request.getParameter("filter");
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        String time = "";

        if (filter == null) {
            filter = "Month";
            time = "This Month";
        }

        LocalDate filterDate = null;
        Integer filterMonth = null;
        Integer filterYear = null;

        switch (filter) {
            case "Day":
                filterDate = currentDate;
                time = "Today";
                break;
            case "Month":
                filterMonth = currentMonth;
                time = "This Month";
                break;
            case "Year":
                filterYear = currentYear;
                time = "This Year";
                break;
        }

        // Lấy danh sách quỹ và giao dịch
        List<FundManagement> funds = fdao.getAllFunds(filterDate, filterMonth, filterYear);
//        List<TransactionFund> transactions = fdao.getTransactions(filterDate, filterMonth, filterYear);

        // Tính toán tổng số tiền
        double total = 0;
        for (FundManagement t : funds) {
            total += t.getCurrentBalance();
        }
        DecimalFormat df = new DecimalFormat("#,###.00");

//        double income = fdao.income(transactions, "Income");
//        double spending = fdao.income(transactions, "Expense");
//        double balance = income - spending;

        // Lấy dữ liệu thu nhập và chi tiêu hàng tháng
        Map<String, double[]> monthlyData = new HashMap<>();
        try {
            monthlyData = fdao.getMonthlyIncomeAndSpending(currentYear);
        } catch (SQLException e) {
            e.printStackTrace();
            for (int i = 1; i <= 12; i++) {
                monthlyData.put(String.valueOf(i), new double[]{0, 0});
            }
        }

        double[] monthlyIncome = new double[12];
        double[] monthlySpending = new double[12];
        double[] monthlyBalance = new double[12];

        for (int i = 1; i <= 12; i++) {
            double[] data = monthlyData.getOrDefault(String.valueOf(i), new double[]{0, 0});
            monthlyIncome[i - 1] = data[0];
            monthlySpending[i - 1] = data[1];
            monthlyBalance[i - 1] = data[0] - data[1];
        }
        List<TransactionFund> rencent = fdao.getAllTransactions();
        request.setAttribute("rencent", rencent);

        // Chuyển mảng thành chuỗi để gửi sang JSP
        request.setAttribute("total", df.format(total));
//        request.setAttribute("income", df.format(income));
//        request.setAttribute("spending", df.format(spending));
//        request.setAttribute("balance", df.format(balance));
        request.setAttribute("funds", funds);
        request.setAttribute("time", time);
        request.setAttribute("filter", filter);

        request.setAttribute("monthlyIncome", arrayToString(monthlyIncome));
        request.setAttribute("monthlySpending", arrayToString(monthlySpending));
        request.setAttribute("monthlyBalance", arrayToString(monthlyBalance));

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(array[i]);
        }
        return sb.toString();
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
