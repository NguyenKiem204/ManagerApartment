package controller.accountant;

import dao.ExpenseDAO;
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

        String errorMessage = validateInput(description, amountStr, typeExpenseIDStr);
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("ImportExpense.jsp").forward(request, response);
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int typeExpenseID = Integer.parseInt(typeExpenseIDStr);

        ExpenseDAO expenseDAO = new ExpenseDAO();
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
            expenseDAO.insertExpenseDetail(todayExpense.getExpenseID(), typeExpenseID, amount, status, description);
            expenseDAO.updateTotalAmount(todayExpense.getExpenseID(), amount);
            response.sendRedirect("ImportExpense");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: ", e);
            request.setAttribute("errorMessage", "Database error occurred. Please try again.");
            request.getRequestDispatcher("ImportExpense.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input: ", e);
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("ImportExpense.jsp").forward(request, response);
        }
    }

    private String validateInput(String description, String amountStr, String typeExpenseIDStr) {
        if (description == null || description.trim().isEmpty()) {
            return "Description cannot be empty.";
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

    @Override
    public String getServletInfo() {
        return "Import Expense Servlet";
    }
}
