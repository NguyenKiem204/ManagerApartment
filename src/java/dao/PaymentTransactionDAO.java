/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Invoices;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author nguye
 */
public class PaymentTransactionDAO implements DAOInterface<Invoices, Integer> {

    @Override
    public int insert(Invoices t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(Invoices t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Invoices t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Invoices> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Invoices selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(PaymentTransactionDAO.class.getName());

    public boolean isValidTransaction(String transactionId) {
        String query = "SELECT payment_date, status FROM PaymentTransaction WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, transactionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp paymentDate = rs.getTimestamp("payment_date");
                String status = rs.getString("status");

                // Kiểm tra trạng thái giao dịch
                if (!"PENDING".equalsIgnoreCase(status)) {
                    return false;
                }

                // Kiểm tra thời gian giao dịch (chỉ hợp lệ trong 10 phút)
                Instant createdTime = paymentDate.toInstant();
                Instant now = Instant.now();
                return ChronoUnit.MINUTES.between(createdTime, now) <= 10;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tạo giao dịch thanh toán
    public void createTransaction(String invoiceID, String transactionId, double amount, String method) {
        String sql = "INSERT INTO PaymentTransaction (transaction_id, invoice_id, amount, status, method) VALUES (?, ?, ?, 'Pending', ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            stmt.setInt(2, Integer.parseInt(invoiceID));
            stmt.setDouble(3, amount);
            stmt.setString(4, method);
            stmt.executeUpdate();
            System.out.println("Transaction created successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tạo giao dịch thanh toán", e);
        }
    }

    // Lấy invoice_id từ transaction_id (mã giao dịch)
    public int getInvoiceIdByTransactionId(String transactionId) {
        String sql = "SELECT invoice_id FROM PaymentTransaction WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("invoice_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy invoice ID từ transaction ID", e);
        }
        return -1;
    }

    public int deleteExpiredTransactions() {
        String sql = "DELETE FROM PaymentTransaction WHERE status = 'Pending' AND DATEDIFF(MINUTE, payment_date, GETDATE()) > 5";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            return stmt.executeUpdate(); // Returns the number of deleted transactions
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa giao dịch quá hạn", e);
        }
        return 0;
    }

    public void updateTransactionStatus(String transactionId, String status) {
        String sql = "UPDATE PaymentTransaction SET status = ? WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTransactionStatus(String transactionId) {
        String status = "pending"; // Mặc định nếu không tìm thấy giao dịch
        String sql = "SELECT status FROM PaymentTransaction WHERE transaction_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy trạng thái giao dịch", e);
        }

        return status;
    }

    public boolean doesTransactionExist(String transactionId) {
        String sql = "SELECT COUNT(*) FROM PaymentTransaction WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking if transaction exists", e);
        }
        return false;
    }

    public List<String> getPendingTransaction(int invoiceID) throws SQLException {
        List<String> pendingTransactions = new ArrayList<>();
        String sql = "SELECT transaction_id FROM PaymentTransaction WHERE invoice_id = ? AND status = 'Pending'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoiceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pendingTransactions.add(rs.getString("transaction_id"));
            }
        }
        return pendingTransactions;
    }

    public int deleteTransactionsByInvoiceID(int invoiceID) {
        String sql = "DELETE FROM PaymentTransaction WHERE invoiceID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
