/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.UtilityBill;

/**
 *
 * @author nkiem
 */
public class UtilityBillDAO {

    // Thêm hóa đơn mới
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

    // Cập nhật hóa đơn
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

    // Cập nhật trạng thái hóa đơn
    public boolean updateUtilityBillStatus(int billId, String status) throws SQLException {
        String sql = "UPDATE UtilityBill SET Status = ? WHERE BillID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, billId);

            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa hóa đơn
    public boolean deleteUtilityBill(int billId) throws SQLException {
        String sql = "DELETE FROM UtilityBill WHERE BillID = ? AND Status = 'Unpaid'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy hóa đơn theo ID
    public UtilityBill getUtilityBillById(int billId) throws SQLException {
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
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "JOIN Building b ON a.BuildingID = b.BuildingID "
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
        bill.setApartmentName(rs.getString("ApartmentName"));
        bill.setOwnerName(rs.getString("OwnerName"));
        bill.setBuildingName(rs.getString("BuildingName"));

        return bill;
    }
}
