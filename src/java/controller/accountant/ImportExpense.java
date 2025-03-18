package controller.accountant;

import dao.ExpenseDAO;
import dao.FundDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Expense;
import model.TypeExpense;

@WebServlet(name = "ImportExpense", urlPatterns = {"/accountant/ImportExpense"})
public class ImportExpense extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ImportExpense.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExpenseDAO expenseDAO = new ExpenseDAO();
        LocalDate today = LocalDate.now();
        Expense todayExpense = expenseDAO.getExpenseByDate(today);
        List<TypeExpense> typeExpenses = expenseDAO.getAllTypeExpenses();

        request.setAttribute("lte", typeExpenses);
        request.setAttribute("exptoday", todayExpense);
        request.getRequestDispatcher("ImportExpense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String description = request.getParameter("description");
        String amountStr = request.getParameter("amount");
        String typeExpenseIDStr = request.getParameter("typeid");

        // Validate input
        String errorMessage = validateInput(description, amountStr, typeExpenseIDStr);
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            doGet(request, response);
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int typeExpenseID = Integer.parseInt(typeExpenseIDStr);

        ExpenseDAO expenseDAO = new ExpenseDAO();
        FundDAO fundDAO = new FundDAO();

        LocalDate today = LocalDate.now();
        Expense todayExpense = expenseDAO.getExpenseByDate(today);

        try {
            if (todayExpense == null) {
                expenseDAO.insertExpense(3, 0);
                todayExpense = expenseDAO.getExpenseByDate(today);
                if (todayExpense == null) {
                    throw new SQLException("Failed to insert and retrieve expense.");
                }
            }

            TypeExpense typeExpense = expenseDAO.getTypeExpenseById(typeExpenseID);
            if (typeExpense == null) {
                throw new IllegalArgumentException("Invalid TypeExpense ID.");
            }

            String status = typeExpense.isFixed() ? "Pending" : "Approved";

            if ("Approved".equals(status)) {
                int fundID = fundDAO.getFundIDByTypeFund(typeExpense.getTypeFundID());
                if (fundID == -1) {
                    throw new SQLException("Không tìm thấy FundID tương ứng với TypeFundID.");
                }

                double currentBalance = fundDAO.getCurrentBalance(fundID);
                if (currentBalance < amount) {
                    request.setAttribute("errorMessage", "Không đủ số dư trong quỹ để thực hiện giao dịch.");
                    doGet(request, response);
                    return;
                }

                boolean transactionInserted = fundDAO.insertTransaction(fundID, amount, "Expense", description, 3);
                if (!transactionInserted) {
                    throw new SQLException("Không thể thêm giao dịch vào bảng TransactionFund.");
                }
            }

            expenseDAO.insertExpenseDetail(todayExpense.getExpenseID(), typeExpenseID, amount, status, description);

            if ("Approved".equals(status)) {
                expenseDAO.updateTotalAmount(todayExpense.getExpenseID(), amount);
            }

            request.setAttribute("successMessage", "Giao dịch đã được thêm thành công!");
            doGet(request, response);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: ", e);
            request.setAttribute("errorMessage", "Database error occurred. Please try again.");
            doGet(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input: ", e);
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
        }
    }

    private String validateInput(String description, String amountStr, String typeExpenseIDStr) {
        if (description == null || description.trim().isEmpty()) {
            return "Description cannot be empty.";
        }
        if (!isValidDescription(description)) {
            return "Description contains invalid characters or exceeds the limit.";
        }
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                return "Amount must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Invalid amount format.";
        }
        try {
            Integer.parseInt(typeExpenseIDStr);
        } catch (NumberFormatException e) {
            return "Invalid TypeExpense ID.";
        }
        return null;
    }

    public boolean isValidDescription(String input) {
        return input.matches("^[\\p{L}0-9\\s.,!?'\"()\\-_/]{1,500}$");
    }

    @Override
    public String getServletInfo() {
        return "Import Expense Servlet";
    }
}
