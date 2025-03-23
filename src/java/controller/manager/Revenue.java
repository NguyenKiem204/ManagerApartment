package controller.manager;

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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
import model.ExpenseDetail;
import model.FundManagement;
import model.TransactionFund;

@WebServlet(name = "Revenue", urlPatterns = {"/manager/Revenue"})
public class Revenue extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Revenue</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Revenue at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        double income = 0;
        double spending = 0;
//        for (TransactionFund f : transactions) {
//            if (f.getTransactionType().equals("Income")) {
//                income += f.getAmount();
//            } else {
//                spending += f.getAmount();
//            }
//        }
        double balance =income-spending;
        DecimalFormat df = new DecimalFormat("#,###.00");

//        double income = fdao.getTotalAmountByTransactionType("Income", filterDate, filterMonth, filterYear);
//        double spending = fdao.getTotalAmountByTransactionType("Expense", filterDate, filterMonth, filterYear);
//        double balance = income - spending;
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
        InvoiceDAO idao = new InvoiceDAO();
        int tcome = idao.countTotalInvoices(filterYear, filterMonth, filterYear);
        int pcome = idao.countPaidInvoices(filterYear, filterMonth, filterYear);
        request.setAttribute("tcome", tcome);
        request.setAttribute("pcome", pcome);
        ExpenseDAO edao = new ExpenseDAO();
        int tout = edao.countTotalExpenses(filterYear, filterMonth, filterYear);
        int aout = edao.countPaidExpenses(filterYear, filterMonth, filterYear);

        request.setAttribute("tout", tout);
        request.setAttribute("aout", aout);

        request.setAttribute("rencent", rencent);
        request.setAttribute("total", df.format(total));
        request.setAttribute("income", df.format(income));
        request.setAttribute("spending", df.format(spending));
        request.setAttribute("balance", df.format(balance));
        request.setAttribute("funds", funds);
        request.setAttribute("time", time);
        request.setAttribute("filter", filter);

        request.setAttribute("monthlyIncome", arrayToString(monthlyIncome));
        request.setAttribute("monthlySpending", arrayToString(monthlySpending));
        request.setAttribute("monthlyBalance", arrayToString(monthlyBalance));
        request.getRequestDispatcher("Revenue.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
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
}
