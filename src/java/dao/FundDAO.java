/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Expense;
import model.FundManagement;
import model.TransactionFund;
import model.TypeFund;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 *
 * @author nguye
 */
public class FundDAO implements DAOInterface<FundManagement, Integer> {

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

    public List<FundManagement> getAllFunds() {
        List<FundManagement> funds = new ArrayList<>();
        String sql = "SELECT fm.FundID, fm.FundName, fm.TotalAmount, fm.CurrentBalance, fm.CreatedAt, fm.Status, "
                + "fm.TypeFundID, fm.CreatedBy, tf.TypeFundID, tf.TypeName, tf.DefaultAmount "
                + "FROM FundManagement fm "
                + "INNER JOIN TypeFund tf ON fm.TypeFundID = tf.TypeFundID";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int fundID = rs.getInt("FundID");
                String fundName = rs.getString("FundName");
                double totalAmount = rs.getDouble("TotalAmount");
                double currentBalance = rs.getDouble("CurrentBalance");
                LocalDate createdAt = rs.getDate("CreatedAt").toLocalDate();
                String status = rs.getString("Status");
                int createdBy = rs.getInt("CreatedBy");

                // Lấy thông tin TypeFund
                int typeFundID = rs.getInt("TypeFundID");
                String typeName = rs.getString("TypeName");
                double defaultAmount = rs.getDouble("DefaultAmount");
                TypeFund typeFund = new TypeFund(typeFundID, typeName, defaultAmount);

                // Lấy danh sách các giao dịch liên quan đến quỹ này
                List<TransactionFund> transactions = getTransactionsByFundID(fundID);

                // Tạo đối tượng FundManagement
                FundManagement fund = new FundManagement(fundID, fundName, totalAmount, currentBalance, createdAt, status, typeFund, createdBy, transactions);
                funds.add(fund);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funds;
    }

    private List<TransactionFund> getTransactionsByFundID(int fundID) {
        List<TransactionFund> transactions = new ArrayList<>();
        String sql = "SELECT TransactionID, FundID, Amount, TransactionType, Description, TransactionDate, StaffID "
                + "FROM TransactionFund WHERE FundID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fundID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int transactionID = rs.getInt("TransactionID");
                    double amount = rs.getDouble("Amount");
                    String transactionType = rs.getString("TransactionType");
                    String description = rs.getString("Description");
                    LocalDate transactionDate = rs.getDate("TransactionDate").toLocalDate();
                    int staffID = rs.getInt("StaffID");

                    TransactionFund transaction = new TransactionFund(transactionID, fundID, amount, transactionType, description, transactionDate, staffID);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<FundManagement> getAllFunds(LocalDate filterDate, Integer filterMonth, Integer filterYear) {
        List<FundManagement> funds = new ArrayList<>();
        String sql = "SELECT fm.FundID, fm.FundName, fm.TotalAmount, fm.CurrentBalance, fm.CreatedAt, fm.Status, "
                + "fm.TypeFundID, fm.CreatedBy, tf.TypeFundID, tf.TypeName, tf.DefaultAmount "
                + "FROM FundManagement fm "
                + "INNER JOIN TypeFund tf ON fm.TypeFundID = tf.TypeFundID";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int fundID = rs.getInt("FundID");
                String fundName = rs.getString("FundName");
                double totalAmount = rs.getDouble("TotalAmount");
                double currentBalance = rs.getDouble("CurrentBalance");
                LocalDate createdAt = rs.getDate("CreatedAt").toLocalDate();
                String status = rs.getString("Status");
                int createdBy = rs.getInt("CreatedBy");

                // Lấy thông tin TypeFund
                int typeFundID = rs.getInt("TypeFundID");
                String typeName = rs.getString("TypeName");
                double defaultAmount = rs.getDouble("DefaultAmount");
                TypeFund typeFund = new TypeFund(typeFundID, typeName, defaultAmount);

                // Lấy danh sách các giao dịch liên quan đến quỹ này với bộ lọc
                List<TransactionFund> transactions = getTransactionsByFundID(fundID, filterDate, filterMonth, filterYear);

                // Tạo đối tượng FundManagement
                FundManagement fund = new FundManagement(fundID, fundName, totalAmount, currentBalance, createdAt, status, typeFund, createdBy, transactions);
                funds.add(fund);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funds;
    }

    public List<TransactionFund> getTransactionsByFundID(int fundID, LocalDate filterDate, Integer filterMonth, Integer filterYear) {
        List<TransactionFund> transactions = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT TransactionID, FundID, Amount, TransactionType, Description, TransactionDate, StaffID "
                + "FROM TransactionFund WHERE FundID = ?"
        );

        // Thêm điều kiện lọc theo ngày, tháng, năm nếu có
        if (filterDate != null) {
            sql.append(" AND CAST(TransactionDate AS DATE) = ?");
        } else if (filterMonth != null && filterYear != null) {
            sql.append(" AND MONTH(TransactionDate) = ? AND YEAR(TransactionDate) = ?");
        } else if (filterYear != null) {
            sql.append(" AND YEAR(TransactionDate) = ?");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, fundID);

            // Thiết lập các tham số lọc
            int paramIndex = 2;
            if (filterDate != null) {
                ps.setDate(paramIndex++, Date.valueOf(filterDate));
            } else if (filterMonth != null && filterYear != null) {
                ps.setInt(paramIndex++, filterMonth);
                ps.setInt(paramIndex++, filterYear);
            } else if (filterYear != null) {
                ps.setInt(paramIndex++, filterYear);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int transactionID = rs.getInt("TransactionID");
                    double amount = rs.getDouble("Amount");
                    String transactionType = rs.getString("TransactionType");
                    String description = rs.getString("Description");
                    LocalDate transactionDate = rs.getDate("TransactionDate").toLocalDate();
                    int staffID = rs.getInt("StaffID");

                    TransactionFund transaction = new TransactionFund(transactionID, fundID, amount, transactionType, description, transactionDate, staffID);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<TransactionFund> getAllTransactions() {
        List<TransactionFund> transactions = new ArrayList<>();
        String sql = "SELECT TransactionID, FundID, Amount, TransactionType, Description, TransactionDate, StaffID FROM TransactionFund order by TransactionID desc ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int transactionID = rs.getInt("TransactionID");
                int fundID = rs.getInt("FundID");
                double amount = rs.getDouble("Amount");
                String transactionType = rs.getString("TransactionType");
                String description = rs.getString("Description");
                LocalDate transactionDate = rs.getDate("TransactionDate").toLocalDate();
                int staffID = rs.getInt("StaffID");

                TransactionFund transaction = new TransactionFund(transactionID, fundID, amount, transactionType, description, transactionDate, staffID);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public Map<String, double[]> getMonthlyIncomeAndSpending(int year) throws SQLException {
        Map<String, double[]> monthlyData = new HashMap<>();
        String sql = "SELECT MONTH(TransactionDate) AS month, "
                + "SUM(CASE WHEN TransactionType = 'Income' THEN Amount ELSE 0 END) AS income, "
                + "SUM(CASE WHEN TransactionType = 'Expense' THEN Amount ELSE 0 END) AS spending "
                + "FROM TransactionFund "
                + "WHERE YEAR(TransactionDate) = ? "
                + "GROUP BY MONTH(TransactionDate)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("month");
                    double income = rs.getDouble("income");
                    double spending = rs.getDouble("spending");
                    double balance = income - spending;
                    // Lưu dữ liệu vào map
                    monthlyData.put(String.valueOf(month), new double[]{income, spending, balance});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return monthlyData;
    }

    public List<TransactionFund> getAllTran(LocalDate day, int month, int year) {
        List<TransactionFund> list = new ArrayList<>();
        List<Object> pra = new ArrayList<>();
        String sql = " select * from TransactionFund";
        if (day != null ) {
            sql += "Where TransactionDate= ?";
            pra.add(java.sql.Date.valueOf(day));
        }
        if (month >0) {
            sql += " Where Month(TransactionDate) =?";
            pra.add(month);
        }
        if (year > 0) {
            sql += " Where Month(year) =?";
            pra.add(year);
        }
        sql += " ORDER BY inv.InvoiceID Desc";
         try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < pra.size(); i++) {
                ps.setObject(i + 1, pra.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                TransactionFund
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return list;
    }
        
    

    public List<TransactionFund> getAllTransactions(LocalDate fromDate, LocalDate toDate, String transactionType, Double minAmount, Double maxAmount) {
        List<TransactionFund> transactions = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT TransactionID, FundID, Amount, TransactionType, Description, TransactionDate, StaffID FROM TransactionFund WHERE 1=1");

        // Thêm điều kiện lọc nếu có
        if (fromDate != null) {
            sql.append(" AND TransactionDate >= ?");
        }
        if (toDate != null) {
            sql.append(" AND TransactionDate <= ?");
        }
        if (transactionType != null && !transactionType.isEmpty()) {
            sql.append(" AND TransactionType = ?");
        }
        if (minAmount != null) {
            sql.append(" AND Amount >= ?");
        }
        if (maxAmount != null) {
            sql.append(" AND Amount <= ?");
        }

        sql.append(" ORDER BY TransactionID DESC");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (fromDate != null) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(fromDate));
            }
            if (toDate != null) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(toDate));
            }
            if (transactionType != null && !transactionType.isEmpty()) {
                ps.setString(paramIndex++, transactionType);
            }
            if (minAmount != null) {
                ps.setDouble(paramIndex++, minAmount);
            }
            if (maxAmount != null) {
                ps.setDouble(paramIndex++, maxAmount);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int transactionID = rs.getInt("TransactionID");
                    int fundID = rs.getInt("FundID");
                    double amount = rs.getDouble("Amount");
                    String transType = rs.getString("TransactionType");
                    String description = rs.getString("Description");
                    LocalDate transactionDate = rs.getDate("TransactionDate").toLocalDate();
                    int staffID = rs.getInt("StaffID");

                    TransactionFund transaction = new TransactionFund(transactionID, fundID, amount, transType, description, transactionDate, staffID);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public int insert(FundManagement t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(FundManagement t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(FundManagement t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FundManagement> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FundManagement selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
