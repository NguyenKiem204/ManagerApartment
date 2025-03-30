/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.Image;
import model.Resident;
import model.UtilityBill;

/**
 *
 * @author nkiem
 */
public class UtilityBillDAO {

    ResidentDAO residentDAO = new ResidentDAO();
    ApartmentDAO apartmentDAO = new ApartmentDAO();

    public int selectLastId() {
        String sql = "SELECT MAX(BillID) FROM UtilityBill;";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilityBillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int addUtilityBill(UtilityBill bill) throws SQLException {
        String sql = "INSERT INTO UtilityBill (ApartmentID, BillingPeriodStart, BillingPeriodEnd, "
                + "ElectricityConsumption, ElectricityCost, WaterConsumption, WaterCost, "
                + "TotalAmount, GeneratedDate, DueDate, Status, InvoiceID, BillingMonth, BillingYear) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getApartmentId());
            stmt.setTimestamp(2, Timestamp.valueOf(bill.getBillingPeriodStart()));
            stmt.setTimestamp(3, Timestamp.valueOf(bill.getBillingPeriodEnd()));

            stmt.setBigDecimal(4, bill.getElectricityConsumption());
            stmt.setBigDecimal(5, bill.getElectricityCost());
            stmt.setBigDecimal(6, bill.getWaterConsumption());
            stmt.setBigDecimal(7, bill.getWaterCost());
            stmt.setBigDecimal(8, bill.getTotalAmount());
            stmt.setTimestamp(9, Timestamp.valueOf(bill.getGeneratedDate()));
            stmt.setTimestamp(10, Timestamp.valueOf(bill.getDueDate()));
            stmt.setString(11, bill.getStatus());

            if (bill.getInvoiceId() != null) {
                stmt.setInt(12, bill.getInvoiceId());
            } else {
                stmt.setNull(12, java.sql.Types.INTEGER);
            }

            stmt.setInt(13, bill.getBillingMonth());
            stmt.setInt(14, bill.getBillingYear());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public boolean updateUtilityBill(UtilityBill bill) throws SQLException {
        String sql = "UPDATE UtilityBill SET ApartmentID = ?, BillingPeriodStart = ?, "
                + "BillingPeriodEnd = ?, ElectricityConsumption = ?, ElectricityCost = ?, "
                + "WaterConsumption = ?, WaterCost = ?, TotalAmount = ?, GeneratedDate = ?, "
                + "DueDate = ?, Status = ?, InvoiceID = ?, BillingMonth = ?, BillingYear = ? "
                + "WHERE BillID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getApartmentId());
            stmt.setTimestamp(2, Timestamp.valueOf(bill.getBillingPeriodStart()));
            stmt.setTimestamp(3, Timestamp.valueOf(bill.getBillingPeriodEnd()));

            stmt.setBigDecimal(4, bill.getElectricityConsumption());
            stmt.setBigDecimal(5, bill.getElectricityCost());
            stmt.setBigDecimal(6, bill.getWaterConsumption());
            stmt.setBigDecimal(7, bill.getWaterCost());
            stmt.setBigDecimal(8, bill.getTotalAmount());
            stmt.setTimestamp(9, Timestamp.valueOf(bill.getGeneratedDate()));
            stmt.setTimestamp(10, Timestamp.valueOf(bill.getDueDate()));
            stmt.setString(11, bill.getStatus());

            if (bill.getInvoiceId() != null) {
                stmt.setInt(12, bill.getInvoiceId());
            } else {
                stmt.setNull(12, java.sql.Types.INTEGER);
            }

            stmt.setInt(13, bill.getBillingMonth());
            stmt.setInt(14, bill.getBillingYear());
            stmt.setInt(15, bill.getBillId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUtilityBillStatus(int billId, String status) throws SQLException {
        String sql = "UPDATE UtilityBill SET Status = ? WHERE BillID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, billId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isInvoicePaid(int invoiceId) {
        String sql = "SELECT i.Status "
                + "FROM Invoice i "
                + "JOIN UtilityBill ub ON i.InvoiceID = ub.InvoiceID "
                + "WHERE i.InvoiceID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "Paid".equalsIgnoreCase(rs.getString("Status"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra trạng thái hóa đơn: " + e.getMessage());
            
        }
        return false;
    }

    public boolean deleteUtilityBill(int billId) throws SQLException {
        String sql = "DELETE FROM UtilityBill WHERE BillID = ? AND Status = 'Unpaid'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;
        }
    }

    public UtilityBill getUtilityBillById(int billId) {
        String sql = "SELECT ub.*, a.ApartmentName, r.FullName AS OwnerName, b.BuildingName "
                + "FROM UtilityBill ub "
                + "JOIN Apartment a ON ub.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "JOIN Building b ON a.BuildingID = b.BuildingID "
                + "WHERE ub.BillID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUtilityBill(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilityBillDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public UtilityBill getBillDetails(int billId) {
        String sql = """
        SELECT 
            ub.BillID, ub.BillingPeriodStart, ub.BillingPeriodEnd, 
            ub.ElectricityConsumption, ub.ElectricityCost, 
            ub.WaterConsumption, ub.WaterCost, ub.TotalAmount, 
            ub.GeneratedDate, ub.DueDate, ub.Status AS BillStatus, 
            ub.BillingMonth, ub.BillingYear, ub.InvoiceID,
            a.ApartmentID, a.ApartmentName, a.[Block], 
            a.[Status] AS ApartmentStatus, a.[Type] AS ApartmentType, 
            r.ResidentID, r.FullName, r.PhoneNumber, r.CCCD, 
            r.Email, r.DOB, r.Sex, r.[Status] AS ResidentStatus, 
            r.RoleID, img.ImageID, img.ImageURL 
        FROM UtilityBill ub
        JOIN Apartment a ON ub.ApartmentID = a.ApartmentID
        JOIN Resident r ON a.OwnerID = r.ResidentID
        LEFT JOIN [Image] img ON r.ImageID = img.ImageID
        WHERE ub.BillID = ?
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            RoleDAO roleDAO = new RoleDAO();
            if (rs.next()) {
                UtilityBill bill = new UtilityBill();
                bill.setBillId(rs.getInt("BillID"));
                bill.setBillingPeriodStart(rs.getTimestamp("BillingPeriodStart").toLocalDateTime());
                bill.setBillingPeriodEnd(rs.getTimestamp("BillingPeriodEnd").toLocalDateTime());
                bill.setElectricityConsumption(rs.getBigDecimal("ElectricityConsumption"));
                bill.setElectricityCost(rs.getBigDecimal("ElectricityCost"));
                bill.setWaterConsumption(rs.getBigDecimal("WaterConsumption"));
                bill.setWaterCost(rs.getBigDecimal("WaterCost"));
                bill.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                bill.setGeneratedDate(rs.getTimestamp("GeneratedDate").toLocalDateTime());
                bill.setDueDate(rs.getTimestamp("DueDate").toLocalDateTime());
                bill.setStatus(rs.getString("BillStatus"));
                bill.setBillingMonth(rs.getInt("BillingMonth"));
                bill.setBillingYear(rs.getInt("BillingYear"));
                bill.setInvoiceId(rs.getInt("InvoiceID"));

                // Thông tin Apartment
                Apartment apt = new Apartment();
                apt.setApartmentId(rs.getInt("ApartmentID"));
                apt.setApartmentName(rs.getString("ApartmentName"));
                apt.setBlock(rs.getString("Block"));
                apt.setStatus(rs.getString("ApartmentStatus"));
                apt.setType(rs.getString("ApartmentType"));
                bill.setApartment(apt);

                // Thông tin Resident
                Resident owner = new Resident();
                owner.setResidentId(rs.getInt("ResidentID"));
                owner.setFullName(rs.getString("FullName"));
                owner.setPhoneNumber(rs.getString("PhoneNumber"));
                owner.setCccd(rs.getString("CCCD"));
                owner.setEmail(rs.getString("Email"));
                owner.setDob(rs.getDate("DOB").toLocalDate());
                owner.setSex(rs.getString("Sex"));
                owner.setStatus(rs.getString("ResidentStatus"));
                owner.setRole(roleDAO.selectById(rs.getInt("RoleID")));

                // Thông tin Image (nếu có)
                Image img = new Image();
                if (rs.getObject("ImageID") != null) {
                    img.setImageID(rs.getInt("ImageID"));
                    img.setImageURL(rs.getString("ImageURL"));
                    owner.setImage(img);
                }
                bill.setOwner(owner);
                apt.setOwnerName(owner.getFullName());
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy hóa đơn theo tháng và năm
    public List<UtilityBill> getUtilityBillsByMonth(int month, int year) throws SQLException {
        String sql = "SELECT ub.*, a.ApartmentName, r.FullName AS OwnerName, b.BuildingName "
                + "FROM UtilityBill ub "
                + "JOIN Apartment a ON ub.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "JOIN Building b ON a.BuildingID = b.BuildingID "
                + "WHERE ub.BillingMonth = ? AND ub.BillingYear = ? "
                + "ORDER BY b.BuildingName, a.ApartmentName";

        List<UtilityBill> bills = new ArrayList<>();

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapUtilityBill(rs));
                }
            }
        }

        return bills;
    }

    // Lấy hóa đơn theo căn hộ
    public List<UtilityBill> getUtilityBillsByApartment(int apartmentId) throws SQLException {
        String sql = "SELECT ub.*, a.ApartmentName, r.FullName AS OwnerName, b.BuildingName "
                + "FROM UtilityBill ub "
                + "JOIN Apartment a ON ub.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerID = r.ResidentID "
                + "WHERE ub.ApartmentID = ? "
                + "ORDER BY ub.BillingYear DESC, ub.BillingMonth DESC";

        List<UtilityBill> bills = new ArrayList<>();

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, apartmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapUtilityBill(rs));
                }
            }
        }

        return bills;
    }

    // Lấy hóa đơn theo trạng thái
    public List<UtilityBill> getUtilityBillsByStatus(String status) throws SQLException {
        String sql = "SELECT ub.*, a.ApartmentName, r.FullName AS OwnerName, b.BuildingName "
                + "FROM UtilityBill ub "
                + "JOIN Apartment a ON ub.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "JOIN Building b ON a.BuildingID = b.BuildingID "
                + "WHERE ub.Status = ? "
                + "ORDER BY b.BuildingName, a.ApartmentName";

        List<UtilityBill> bills = new ArrayList<>();

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapUtilityBill(rs));
                }
            }
        }

        return bills;
    }

    // Lấy hóa đơn theo khoảng thời gian
    public List<UtilityBill> getUtilityBillsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String sql = "SELECT ub.*, a.ApartmentName, r.FullName AS OwnerName, b.BuildingName "
                + "FROM UtilityBill ub "
                + "JOIN Apartment a ON ub.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "JOIN Building b ON a.BuildingID = b.BuildingID "
                + "WHERE ub.GeneratedDate BETWEEN ? AND ? "
                + "ORDER BY b.BuildingName, a.ApartmentName";

        List<UtilityBill> bills = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapUtilityBill(rs));
                }
            }
        }
        return bills;
    }

    public boolean checkBillExists(int apartmentId, int month, int year) throws SQLException {
        String sql = "SELECT COUNT(*) FROM UtilityBill WHERE ApartmentID = ? AND BillingMonth = ? AND BillingYear = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, apartmentId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    private UtilityBill mapUtilityBill(ResultSet rs) throws SQLException {
        UtilityBill bill = new UtilityBill();
        bill.setBillId(rs.getInt("BillID"));
        bill.setApartmentId(rs.getInt("ApartmentID"));
        bill.setBillingPeriodStart(rs.getTimestamp("BillingPeriodStart").toLocalDateTime());
        bill.setBillingPeriodEnd(rs.getTimestamp("BillingPeriodEnd").toLocalDateTime());
        bill.setElectricityConsumption(rs.getBigDecimal("ElectricityConsumption"));
        bill.setElectricityCost(rs.getBigDecimal("ElectricityCost"));
        bill.setWaterConsumption(rs.getBigDecimal("WaterConsumption"));
        bill.setWaterCost(rs.getBigDecimal("WaterCost"));
        bill.setTotalAmount(rs.getBigDecimal("TotalAmount"));
        bill.setGeneratedDate(rs.getTimestamp("GeneratedDate").toLocalDateTime());
        bill.setDueDate(rs.getTimestamp("DueDate").toLocalDateTime());
        bill.setStatus(rs.getString("Status"));
        bill.setInvoiceId(rs.getInt("InvoiceID"));
        bill.setBillingMonth(rs.getInt("BillingMonth"));
        bill.setBillingYear(rs.getInt("BillingYear"));

        // Thông tin bổ sung
        bill.setApartment(apartmentDAO.selectById(rs.getInt("ApartmentID")));
        bill.setOwnerName(rs.getString("OwnerName"));
        bill.setBuildingName(rs.getString("BuildingName"));

        return bill;
    }
}
