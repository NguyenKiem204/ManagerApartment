/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MeterReading;

/**
 *
 * @author nkiem
 */
public class MeterReadingDAO {

    // Thêm chỉ số đồng hồ mới
    public int addMeterReading(MeterReading reading) throws SQLException {
        String sql = "INSERT INTO MeterReading (MeterID, ReadingDate, PreviousReading, CurrentReading, "
                + "StaffID, ReadingMonth, ReadingYear, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reading.getMeterId());
            stmt.setTimestamp(2, Timestamp.valueOf(reading.getReadingDate()));
            stmt.setBigDecimal(3, reading.getPreviousReading());
            stmt.setBigDecimal(4, reading.getCurrentReading());
            stmt.setInt(5, reading.getStaffId());
            stmt.setInt(6, reading.getReadingMonth());
            stmt.setInt(7, reading.getReadingYear());
            stmt.setString(8, reading.getStatus());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public int updateMeterReading(MeterReading reading) throws SQLException {
        String sql = "UPDATE MeterReading SET MeterID = ?, ReadingDate = ?, "
                + "PreviousReading = ?, CurrentReading = ?, StaffID = ?, "
                + "ReadingMonth = ?, ReadingYear = ?, Status = ? "
                + "WHERE ReadingID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reading.getMeterId());
            stmt.setTimestamp(2, Timestamp.valueOf(reading.getReadingDate()));
            stmt.setBigDecimal(3, reading.getPreviousReading());
            stmt.setBigDecimal(4, reading.getCurrentReading());
            stmt.setInt(5, reading.getStaffId());
            stmt.setInt(6, reading.getReadingMonth());
            stmt.setInt(7, reading.getReadingYear());
            stmt.setString(8, reading.getStatus());
            stmt.setInt(9, reading.getReadingId());
            return stmt.executeUpdate();
        }
    }

    // Xóa chỉ số đồng hồ (hoặc đánh dấu không hợp lệ)
    public boolean deleteMeterReading(int readingId) throws SQLException {
        String sql = "UPDATE MeterReading SET Status = 'Invalid' WHERE ReadingID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, readingId);
            return stmt.executeUpdate() > 0;
        }
    }
    // Xóa hoàn toàn chỉ số đồng hồ
public boolean deleteMeterReadingPermanently(int readingId) throws SQLException {
    String sql = "DELETE FROM MeterReading WHERE ReadingID = ?";
    try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, readingId);
        return stmt.executeUpdate() > 0;
    }
}


    // Lấy chỉ số đồng hồ theo ID
    public MeterReading getMeterReadingById(int readingId) throws SQLException {
        String sql = "SELECT mr.*, m.MeterNumber, m.MeterType, m.ApartmentID, "
                + "a.ApartmentName, r.FullName AS OwnerName, s.FullName AS StaffName "
                + "FROM MeterReading mr "
                + "JOIN Meter m ON mr.MeterID = m.MeterID "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "JOIN Staff s ON mr.StaffID = s.StaffID "
                + "WHERE mr.ReadingID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, readingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapMeterReading(rs);
                }
            }
        }
        return null;
    }

    // Lấy chỉ số đồng hồ theo tháng và năm
    public List<MeterReading> getMeterReadingsByMonth(int month, int year) throws SQLException {
        String sql = "SELECT mr.*, m.MeterNumber, m.MeterType, m.ApartmentID, "
                + "a.ApartmentName, r.FullName AS OwnerName, s.FullName AS StaffName "
                + "FROM MeterReading mr "
                + "JOIN Meter m ON mr.MeterID = m.MeterID "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "JOIN Staff s ON mr.StaffID = s.StaffID "
                + "WHERE mr.ReadingMonth = ? AND mr.ReadingYear = ? "
                + "ORDER BY a.ApartmentName, m.MeterType";

        List<MeterReading> readings = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    readings.add(mapMeterReading(rs));
                }
            }
        }

        return readings;
    }

    // Lấy chỉ số đồng hồ theo căn hộ, tháng và năm
    public List<MeterReading> getMeterReadingsByApartment(int apartmentId, int month, int year) throws SQLException {
        String sql = "SELECT mr.*, m.MeterNumber, m.MeterType, m.ApartmentID, "
                + "a.ApartmentName, r.FullName AS OwnerName, s.FullName AS StaffName "
                + "FROM MeterReading mr "
                + "JOIN Meter m ON mr.MeterID = m.MeterID "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "JOIN Staff s ON mr.StaffID = s.StaffID "
                + "WHERE m.ApartmentID = ? AND mr.ReadingMonth = ? AND mr.ReadingYear = ? "
                + "ORDER BY m.MeterType";

        List<MeterReading> readings = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, apartmentId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    readings.add(mapMeterReading(rs));
                }
            }
        }
        return readings;
    }

    public MeterReading getMeterReadingByMeterAndMonthYear(int meterId, int month, int year) throws SQLException {
        String sql = "SELECT mr.*, m.MeterNumber, m.MeterType, m.ApartmentID, "
                + "a.ApartmentName, r.FullName AS OwnerName, s.FullName AS StaffName "
                + "FROM MeterReading mr "
                + "JOIN Meter m ON mr.MeterID = m.MeterID "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "JOIN Staff s ON mr.StaffID = s.StaffID "
                + "WHERE mr.MeterID = ? AND mr.ReadingMonth = ? AND mr.ReadingYear = ? "
                + "AND mr.Status = 'Active'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, meterId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapMeterReading(rs);
                }
            }
        }

        return null; // No reading found
    }

    // Lấy chỉ số đồng hồ gần nhất của một đồng hồ
    public MeterReading getLatestMeterReading(int meterId) throws SQLException {
        String sql = "SELECT TOP 1 mr.*, m.MeterNumber, m.MeterType, m.ApartmentID, "
                + "a.ApartmentName, r.FullName AS OwnerName, s.FullName AS StaffName "
                + "FROM MeterReading mr "
                + "JOIN Meter m ON mr.MeterID = m.MeterID "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "JOIN Staff s ON mr.StaffID = s.StaffID "
                + "WHERE mr.MeterID = ? AND mr.Status = 'Active' "
                + "ORDER BY mr.ReadingYear DESC, mr.ReadingMonth DESC, mr.ReadingDate DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, meterId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapMeterReading(rs);
                }
            }
        }
        return null;
    }

    private MeterReading mapMeterReading(ResultSet rs) throws SQLException {
        MeterReading reading = new MeterReading();
        reading.setReadingId(rs.getInt("ReadingID"));
        reading.setMeterId(rs.getInt("MeterID"));
        reading.setReadingDate(rs.getTimestamp("ReadingDate").toLocalDateTime());
        reading.setPreviousReading(rs.getBigDecimal("PreviousReading"));
        reading.setCurrentReading(rs.getBigDecimal("CurrentReading"));
        reading.setConsumption(rs.getBigDecimal("Consumption"));
        reading.setStaffId(rs.getInt("StaffID"));
        reading.setReadingMonth(rs.getInt("ReadingMonth"));
        reading.setReadingYear(rs.getInt("ReadingYear"));
        reading.setStatus(rs.getString("Status"));

        reading.setMeterNumber(rs.getString("MeterNumber"));
        reading.setMeterType(rs.getString("MeterType"));
        reading.setApartmentId(rs.getInt("ApartmentID"));
        reading.setApartmentName(rs.getString("ApartmentName"));
        reading.setOwnerName(rs.getString("OwnerName"));
        reading.setStaffName(rs.getString("StaffName"));
        return reading;
    }
}
