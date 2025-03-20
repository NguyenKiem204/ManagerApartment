/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.Expense;
import model.ExpenseDetail;
import model.InvoiceDetail;
import model.Invoices;
import model.Resident;
import model.Role;
import model.TypeExpense;

/**
 *
 * @author nguye
 */
public class ExpenseDAO implements DAOInterface<Expense, Integer> {

    @Override
    public int insert(Expense t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(Expense t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Expense t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Expense> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Expense selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Expense> filterExpenses(String status, Integer typeExpenseID, LocalDate fromDate, LocalDate toDate) {
        List<Expense> expenses = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        String sql = "SELECT e.ExpenseID, e.ExpenseDate, e.StaffID, e.TotalAmount, "
                + "ed.ExpenseDetailID, ed.TypeExpenseID, ed.Amount, ed.Status, ed.Description, "
                + "te.TypeExpenseID AS teTypeExpenseID, te.TypeName, te.IsFixed, te.TypeFundID " // Thêm TypeFundID
                + "FROM Expense e "
                + "INNER JOIN ExpenseDetail ed ON e.ExpenseID = ed.ExpenseID "
                + "INNER JOIN TypeExpense te ON ed.TypeExpenseID = te.TypeExpenseID "
                + "WHERE 1=1";

        // Thêm điều kiện lọc
        if (status != null && !status.isEmpty()) {
            sql += " AND ed.Status = ?";
            parameters.add(status);
        }
        if (typeExpenseID != null && typeExpenseID > 0) {
            sql += " AND ed.TypeExpenseID = ?";
            parameters.add(typeExpenseID);
        }
        if (fromDate != null) {
            sql += " AND e.ExpenseDate >= ?";
            parameters.add(java.sql.Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql += " AND e.ExpenseDate <= ?";
            parameters.add(java.sql.Date.valueOf(toDate));
        }
        sql += " ORDER BY e.ExpenseID, ed.ExpenseDetailID";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            Expense currentExpense = null; // Lưu trữ Expense hiện tại

            while (rs.next()) {
                int expenseID = rs.getInt("ExpenseID");

                if (currentExpense == null || currentExpense.getExpenseID() != expenseID) {

                    currentExpense = new Expense(
                            expenseID,
                            rs.getDate("ExpenseDate").toLocalDate(),
                            rs.getInt("StaffID"),
                            rs.getDouble("TotalAmount"),
                            new ArrayList<>() // Khởi tạo danh sách ExpenseDetail rỗng
                    );
                    expenses.add(currentExpense);
                }

                // Tạo TypeExpense từ kết quả truy vấn
                TypeExpense typeExpense = new TypeExpense(
                        rs.getInt("teTypeExpenseID"),
                        rs.getString("TypeName"),
                        rs.getBoolean("IsFixed"),
                        rs.getInt("TypeFundID") // Thêm TypeFundID
                );

                // Tạo ExpenseDetail và thêm vào danh sách của Expense hiện tại
                ExpenseDetail expenseDetail = new ExpenseDetail(
                        rs.getInt("ExpenseDetailID"),
                        expenseID,
                        typeExpense,
                        rs.getDouble("Amount"),
                        rs.getString("Status"),
                        rs.getString("Description")
                );
                currentExpense.getExpenseDetails().add(expenseDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expenses;
    }

    public List<TypeExpense> getAllTypeExpenses() {
        List<TypeExpense> list = new ArrayList<>();
        String query = "SELECT TypeExpenseID, TypeName, IsFixed, TypeFundID FROM TypeExpense"; // Thêm TypeFundID

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TypeExpense typeExpense = new TypeExpense(
                        rs.getInt("TypeExpenseID"),
                        rs.getString("TypeName"),
                        rs.getBoolean("IsFixed"),
                        rs.getInt("TypeFundID") // Thêm TypeFundID
                );
                list.add(typeExpense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ExpenseDetail> getAllExpenseDetails() {
        List<ExpenseDetail> expenseDetails = new ArrayList<>();
        String query = "SELECT ed.ExpenseDetailID, ed.ExpenseID, ed.TypeExpenseID, te.TypeName, ed.Amount, ed.Status, ed.Description "
                + "FROM ExpenseDetail ed "
                + "JOIN TypeExpense te ON ed.TypeExpenseID = te.TypeExpenseID where ed.Status='Pending'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int expenseDetailID = rs.getInt("ExpenseDetailID");
                int expenseID = rs.getInt("ExpenseID");
                int typeExpenseID = rs.getInt("TypeExpenseID");
                String typeName = rs.getString("TypeName");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("Status");
                String description = rs.getString("Description");

                TypeExpense typeExpense = new TypeExpense(typeExpenseID, typeName, false, 0);

                ExpenseDetail expenseDetail = new ExpenseDetail(expenseDetailID, expenseID, typeExpense, amount, status, description);

                // Thêm vào danh sách
                expenseDetails.add(expenseDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenseDetails;
    }

    public void insertExpense(int staffId, double totalAmount) throws SQLException {
        String sql = "INSERT INTO Expense (StaffID, TotalAmount) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, staffId);
            stmt.setBigDecimal(2, new java.math.BigDecimal(totalAmount));
            stmt.executeUpdate();
        }
    }

    public void insertExpenseDetail(int expenseId, int typeExpenseId, double amount, String status, String description) throws SQLException {
        String sql = "INSERT INTO ExpenseDetail (ExpenseID, TypeExpenseID, Amount, Status, Description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, expenseId);
            stmt.setInt(2, typeExpenseId);
            stmt.setBigDecimal(3, new java.math.BigDecimal(amount));
            stmt.setString(4, status);
            stmt.setString(5, description);
            stmt.executeUpdate();
        }
    }

    public Expense getExpenseByDate(LocalDate date) {
        Expense todayExpense = null;
        String sql = "SELECT "
                + "    e.ExpenseID, "
                + "    e.ExpenseDate, "
                + "    e.StaffID, "
                + "    e.TotalAmount, "
                + "    ed.ExpenseDetailID, "
                + "    ed.TypeExpenseID, "
                + "    ed.Amount, "
                + "    ed.Status, "
                + "    ed.Description, "
                + "    te.TypeExpenseID AS teTypeExpenseID, "
                + "    te.TypeName, "
                + "    te.IsFixed, "
                + "    te.TypeFundID " // Thêm TypeFundID
                + "FROM Expense e "
                + "LEFT JOIN ExpenseDetail ed ON e.ExpenseID = ed.ExpenseID "
                + "LEFT JOIN TypeExpense te ON ed.TypeExpenseID = te.TypeExpenseID "
                + "WHERE CONVERT(DATE, e.ExpenseDate) = ? "
                + "ORDER BY e.ExpenseID, ed.ExpenseDetailID;";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(date)); // Truyền tham số ngày vào SQL

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    todayExpense = new Expense(
                            rs.getInt("ExpenseID"),
                            rs.getDate("ExpenseDate").toLocalDate(),
                            rs.getInt("StaffID"),
                            rs.getDouble("TotalAmount"),
                            new ArrayList<>()
                    );

                    do {
                        if (rs.getInt("ExpenseDetailID") != 0) { // Kiểm tra nếu có ExpenseDetail
                            TypeExpense typeExpense = new TypeExpense(
                                    rs.getInt("teTypeExpenseID"),
                                    rs.getString("TypeName"),
                                    rs.getBoolean("IsFixed"),
                                    rs.getInt("TypeFundID") // Thêm TypeFundID
                            );
                            ExpenseDetail expenseDetail = new ExpenseDetail(
                                    rs.getInt("ExpenseDetailID"),
                                    todayExpense.getExpenseID(),
                                    typeExpense,
                                    rs.getDouble("Amount"),
                                    rs.getString("Status"),
                                    rs.getString("Description")
                            );
                            todayExpense.getExpenseDetails().add(expenseDetail);
                        }
                    } while (rs.next());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todayExpense;
    }

    public TypeExpense getTypeExpenseById(int typeExpenseID) {
        TypeExpense typeExpense = null;
        String sql = "SELECT TypeExpenseID, TypeName, IsFixed, TypeFundID FROM TypeExpense WHERE TypeExpenseID = ?"; // Thêm TypeFundID

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, typeExpenseID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                typeExpense = new TypeExpense(
                        rs.getInt("TypeExpenseID"),
                        rs.getString("TypeName"),
                        rs.getBoolean("IsFixed"),
                        rs.getInt("TypeFundID") // Thêm TypeFundID
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return typeExpense;
    }

    public void updateTotalAmount(int expenseID, double additionalAmount) throws SQLException {
        String sql = "UPDATE Expense SET TotalAmount = TotalAmount + ? WHERE ExpenseID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, additionalAmount);
            ps.setInt(2, expenseID);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update total amount. Expense ID not found: " + expenseID);
            }
        }
    }

    public void updateStatusExpense(int ID, String status) throws SQLException {
        String sql = "UPDATE ExpenseDetail SET Status = ? WHERE ExpenseDetailID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, ID);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update total amount. Expense ID not found: " + ID);
            }
        }
    }
    

        public void updateTotalAmount(int expenseDetailID) {
            String getExpenseIDQuery = "SELECT ExpenseID FROM ExpenseDetail WHERE ExpenseDetailID = ?";
            String sumQuery = "SELECT SUM(Amount) FROM ExpenseDetail WHERE ExpenseID = ?";
            String updateQuery = "UPDATE Expense SET TotalAmount = ? WHERE ExpenseID = ?";

            try (Connection conn = DBContext.getConnection(); PreparedStatement getExpenseStmt = conn.prepareStatement(getExpenseIDQuery); PreparedStatement sumStmt = conn.prepareStatement(sumQuery); PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

                // Lấy ExpenseID từ ExpenseDetailID
                getExpenseStmt.setInt(1, expenseDetailID);
                try (ResultSet rs1 = getExpenseStmt.executeQuery()) {
                    if (!rs1.next()) {
                        System.out.println("Không tìm thấy ExpenseID cho ExpenseDetailID = " + expenseDetailID);
                        return;
                    }
                    int expenseID = rs1.getInt("ExpenseID");

                    // Tính tổng số tiền của ExpenseDetail theo ExpenseID
                    sumStmt.setInt(1, expenseID);
                    try (ResultSet rs2 = sumStmt.executeQuery()) {
                        double totalAmount = 0;
                        if (rs2.next()) {
                            totalAmount = rs2.getDouble(1);
                        }

                        // Cập nhật totalAmount trong bảng Expense
                        updateStmt.setDouble(1, totalAmount);
                        updateStmt.setInt(2, expenseID);
                        updateStmt.executeUpdate();
                        System.out.println("Cập nhật TotalAmount thành công cho ExpenseID = " + expenseID);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    

}
