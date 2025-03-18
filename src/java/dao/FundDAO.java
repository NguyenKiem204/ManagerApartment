/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Expense;

/**
 *
 * @author nguye
 */
public class FundDAO implements DAOInterface<Expense, Integer> {

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

    public boolean updateFundBalance(int fundID, double amount, String transactionType) throws SQLException {
        String sql = "UPDATE FundManagement "
                + "SET CurrentBalance = CurrentBalance + ? "
                + "WHERE FundID = ?";

        // Nếu là Expense (Khoản Chi) thì trừ đi số tiền
        if ("Expense".equalsIgnoreCase(transactionType)) {
            amount = -amount;
        }

        // Kiểm tra số dư trước khi trừ tiền
        if (amount < 0 && getCurrentBalance(fundID) + amount < 0) {
            System.out.println(" Không đủ số dư để thực hiện giao dịch!");
            return false; // Giao dịch thất bại do không đủ tiền
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, fundID);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getFundIDByTypeFund(int typeFundID) {
        String sql = "SELECT FundID FROM FundManagement WHERE TypeFundID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, typeFundID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("FundID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return -1; 
    }

    // Lấy số dư hiện tại của quỹ
    public double getCurrentBalance(int fundID) {
        String sql = "SELECT CurrentBalance FROM FundManagement WHERE FundID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fundID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("CurrentBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean insertTransaction(int fundID, double amount, String transactionType, String description, int staffID) throws SQLException {
        String insertSQL = "INSERT INTO TransactionFund (FundID, Amount, TransactionType, Description, StaffID) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction


            if (!updateFundBalance(fundID, amount, transactionType)) {
                conn.rollback(); 
                System.out.println("Không đủ số dư để thực hiện giao dịch!");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                ps.setInt(1, fundID);
                ps.setDouble(2, amount);
                ps.setString(3, transactionType);
                ps.setString(4, description);
                ps.setInt(5, staffID);
                ps.executeUpdate();
            }

            conn.commit();
            System.out.println(" Giao dịch đã được thêm vào bảng TransactionFund.");
            return true;

        } catch (SQLException e) {
            System.err.println(" Lỗi khi thêm giao dịch vào bảng TransactionFund:");
            e.printStackTrace(); 
            throw e;
        }
    }

    

}
