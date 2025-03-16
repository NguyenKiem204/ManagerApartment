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
                + "te.TypeExpenseID AS teTypeExpenseID, te.TypeName, te.IsFixed "
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
            // Thiết lập các tham số cho truy vấn
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            // Thực thi truy vấn và xử lý kết quả
            ResultSet rs = ps.executeQuery();
            Expense currentExpense = null; // Lưu trữ Expense hiện tại

            while (rs.next()) {
                int expenseID = rs.getInt("ExpenseID");

                // Nếu Expense hiện tại là null hoặc không trùng với ExpenseID hiện tại
                if (currentExpense == null || currentExpense.getExpenseID() != expenseID) {
                    // Tạo mới Expense nếu chưa tồn tại
                    currentExpense = new Expense(
                            expenseID,
                            rs.getDate("ExpenseDate").toLocalDate(),
                            rs.getInt("StaffID"),
                            rs.getDouble("TotalAmount"),
                            new ArrayList<>() // Khởi tạo danh sách ExpenseDetail rỗng
                    );
                    expenses.add(currentExpense); // Thêm vào danh sách expenses
                }

                // Tạo TypeExpense từ kết quả truy vấn
                TypeExpense typeExpense = new TypeExpense(
                        rs.getInt("teTypeExpenseID"),
                        rs.getString("TypeName"),
                        rs.getBoolean("IsFixed")
                );

                // Tạo ExpenseDetail và thêm vào danh sách của Expense hiện tại
                ExpenseDetail expenseDetail = new ExpenseDetail(
                        rs.getInt("ExpenseDetailID"),
                        expenseID,
                        rs.getInt("TypeExpenseID"),
                        rs.getDouble("Amount"),
                        rs.getString("Status"),
                        rs.getString("Description"),
                        typeExpense
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
        String query = "SELECT TypeExpenseID, TypeName, IsFixed FROM TypeExpense";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TypeExpense typeExpense = new TypeExpense(
                        rs.getInt("TypeExpenseID"),
                        rs.getString("TypeName"),
                        rs.getBoolean("IsFixed")
                );
                list.add(typeExpense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
