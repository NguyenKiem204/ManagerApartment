package controller.accountant;

import dao.ExpenseDAO;
import dao.FundDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import model.Expense;
import model.Staff;
import model.TypeExpense;

@WebServlet(name = "ImportExpense", urlPatterns = {"/accountant/ImportExpense"})
public class ImportExpense extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExpenseDAO expenseDAO = new ExpenseDAO();
        LocalDate today = LocalDate.now();
        Expense todayExpense = expenseDAO.getExpenseByDate(today);
        List<TypeExpense> typeExpenses = expenseDAO.getAllTypeExpenses();
        request.setAttribute("today", today);
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

        // Kiểm tra dữ liệu đầu vào
        String errorMessage = validateInput(description, amountStr, typeExpenseIDStr);
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            doGet(request, response);
            return;
        }

        description = description.trim().replaceAll("\\s+", " ");
        description = capitalizeFirstLetter(description);

        double amount = Double.parseDouble(amountStr);
        int typeExpenseID = Integer.parseInt(typeExpenseIDStr);

        ExpenseDAO expenseDAO = new ExpenseDAO();
        FundDAO fundDAO = new FundDAO();
        LocalDate today = LocalDate.now();
        Expense todayExpense = expenseDAO.getExpenseByDate(today);
        HttpSession session = request.getSession();
        Staff st= (Staff) session.getAttribute("Staff");

        try {
            
            if (todayExpense == null) {
                expenseDAO.insertExpense(st.getStaffId(), 0);
                todayExpense = expenseDAO.getExpenseByDate(today);
                if (todayExpense == null) {
                    request.setAttribute("errorMessage", "Lỗi khi tạo bản ghi chi tiêu.");
                    doGet(request, response);
                    return;
                }
            }

            // Lấy thông tin loại chi tiêu
            TypeExpense typeExpense = expenseDAO.getTypeExpenseById(typeExpenseID);
            if (typeExpense == null) {
                request.setAttribute("errorMessage", "ID loại chi tiêu không hợp lệ.");
                doGet(request, response);
                return;
            }

            // Xác định trạng thái giao dịch
            String status = typeExpense.isFixed() ? "Pending" : "Approved";

            // Nếu giao dịch được duyệt ngay, kiểm tra số dư quỹ
            if ("Approved".equals(status)) {
                int fundID = fundDAO.getFundIDByTypeFund(typeExpense.getTypeFundID());
                if (fundID == -1) {
                    request.setAttribute("errorMessage", "Không tìm thấy quỹ tương ứng.");
                    doGet(request, response);
                    return;
                }

                double currentBalance = fundDAO.getCurrentBalance(fundID);
                if (currentBalance < amount) {
                    request.setAttribute("errorMessage", "Insufficient fund balance");
                    doGet(request, response);
                    return;
                }
                if (currentBalance < 0) {
                    request.setAttribute("errorMessage", "Amount must >0.");
                    doGet(request, response);
                    return;
                }

                // Ghi nhận giao dịch quỹ
                boolean transactionInserted = fundDAO.insertTransaction(fundID, amount, "Expense", description, 3);
                if (!transactionInserted) {
                    request.setAttribute("errorMessage", "Không thể ghi nhận giao dịch.");
                    doGet(request, response);
                    return;
                }
            }

            // Ghi nhận chi tiêu chi tiết
            expenseDAO.insertExpenseDetail(todayExpense.getExpenseID(), typeExpenseID, amount, status, description);

            // Cập nhật tổng chi tiêu nếu giao dịch được duyệt ngay
            if ("Approved".equals(status)) {
                expenseDAO.updateTotalAmount(todayExpense.getExpenseID(), amount);
            }

            request.setAttribute("successMessage", "Giao dịch đã được thêm thành công!");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu, vui lòng thử lại.");
        }
        doGet(request, response);
    }

    /**
     * Kiểm tra đầu vào của người dùng.
     */
    private String validateInput(String description, String amountStr, String typeExpenseIDStr) {
        if (description == null || description.trim().isEmpty()) {
            return "Mô tả không được để trống.";
        }
        if (!isValidDescription(description)) {
            return "Mô tả chứa ký tự không hợp lệ hoặc quá dài.";
        }
        if (containsProhibitedWords(description)) {
            return "Mô tả chứa từ không phù hợp.";
        }
        if (exceedsWordLimit(description, 50)) {
            return "Mô tả không được quá 50 từ.";
        }
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                return "Số tiền phải lớn hơn 0.";
            }
        } catch (NumberFormatException e) {
            return "Định dạng số tiền không hợp lệ.";
        }
        try {
            Integer.parseInt(typeExpenseIDStr);
        } catch (NumberFormatException e) {
            return "ID loại chi tiêu không hợp lệ.";
        }
        return null;
    }

    /**
     * Viết hoa chữ cái đầu tiên của mô tả.
     */
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Kiểm tra mô tả có chứa ký tự không hợp lệ hoặc quá dài không.
     */
    private boolean isValidDescription(String input) {
        return input.matches("^[\\p{L}0-9\\s.,!?'\"()\\-_/]{1,500}$");
    }

    /**
     * Kiểm tra mô tả có chứa từ cấm không.
     */
    private boolean containsProhibitedWords(String text) {
        String[] prohibitedWords = {"badword1", "badword2", "example"}; // Danh sách từ cấm
        for (String word : prohibitedWords) {
            if (text.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiểm tra số lượng từ trong mô tả có vượt quá giới hạn không.
     */
    private boolean exceedsWordLimit(String text, int maxWords) {
        String[] words = text.trim().split("\\s+");
        return words.length > maxWords;
    }

    @Override
    public String getServletInfo() {
        return "Import Expense Servlet";
    }
}
