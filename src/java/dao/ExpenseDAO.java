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

            String sql = "SELECT e.ExpenseID, e.Amount, e.ExpenseDate, e.Status, e.Description, e.StaffID, e.TypeExpenseID "
                    + "FROM Expense e WHERE 1=1";

            if (status != null && !status.isEmpty()) {
                sql += " AND e.Status = ?";
                parameters.add(status);
            }
            if (typeExpenseID >0) {
                sql += " AND e.TypeExpenseID = ?";
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
            sql += " ORDER BY e.ExpenseID"; 

            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < parameters.size(); i++) {
                    ps.setObject(i + 1, parameters.get(i));
                }

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Expense expense = new Expense(
                            rs.getInt("ExpenseID"),
                            rs.getDouble("Amount"),
                            rs.getTimestamp("ExpenseDate").toLocalDateTime(),
                            rs.getInt("TypeExpenseID"),
                            rs.getString("Description"),
                            rs.getString("Status")
                    );
                    expenses.add(expense);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex); // ✅ Sửa lỗi Logger
            }
            return expenses;
        }
        
        public List<TypeExpense> getAllTypeExpenses() {
        List<TypeExpense> list = new ArrayList<>();
        String query = "SELECT TypeExpenseID, TypeName FROM TypeExpense";

        try (Connection conn = DBContext.getConnection();PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TypeExpense typeExpense = new TypeExpense(
                        rs.getInt("TypeExpenseID"),
                        rs.getString("TypeName")
                );
                list.add(typeExpense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
      
    }


