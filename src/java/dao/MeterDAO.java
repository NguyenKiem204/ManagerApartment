/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

<<<<<<< HEAD
import com.itextpdf.text.log.Logger;
import java.lang.System.Logger.Level;
=======

import com.itextpdf.text.log.Logger;
import java.lang.System.Logger.Level;

>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Meter;

/**
 *
 * @author nkiem
 */
public class MeterDAO {

    public int addMeter(Meter meter) throws SQLException {
        String sql = "INSERT INTO Meter (ApartmentID, MeterType, MeterNumber, InstallationDate, Status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, meter.getApartmentId());
            stmt.setString(2, meter.getMeterType());
            stmt.setString(3, meter.getMeterNumber());

<<<<<<< HEAD
//            stmt.setDate(4, Date.valueOf(meter.getInstallationDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(meter.getInstallationDate()));

=======

            stmt.setTimestamp(4, Timestamp.valueOf(meter.getInstallationDate()));
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
            stmt.setString(5, meter.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public boolean updateMeterInfo(int meterId, String meterNumber, String status) {
        String sql = "UPDATE Meter SET MeterNumber = ?, Status = ? WHERE MeterID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
<<<<<<< HEAD
            stmt.setString(1, meterNumber);
            stmt.setString(2, status);
            stmt.setInt(3, meterId);
=======

            stmt.setInt(1, meter.getApartmentId());
            stmt.setString(2, meter.getMeterType());
            stmt.setString(3, meter.getMeterNumber());
            stmt.setTimestamp(4, Timestamp.valueOf(meter.getInstallationDate()));
            stmt.setString(5, meter.getStatus());
            stmt.setInt(6, meter.getMeterId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateMeterStatus(int meterId, String status) {
        String sql = "UPDATE Meter SET Status = ? WHERE MeterID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, meterId);
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
<<<<<<< HEAD
=======
    
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043

    public boolean deleteMeter(int meterId) throws SQLException {
        String sql = "UPDATE Meter SET Status = 'Inactive' WHERE MeterID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, meterId);

            return stmt.executeUpdate() > 0;
        }
    }

    public Meter getMeterById(int meterId) throws SQLException {
        String sql = "SELECT m.*, a.ApartmentName, r.FullName AS OwnerName "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "WHERE m.MeterID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, meterId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapMeter(rs);
                }
            }
        }

        return null;
    }

    public List<Meter> getMetersByApartment(int apartmentId) throws SQLException {
        String sql = "SELECT m.*, a.ApartmentName, r.FullName AS OwnerName "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "WHERE m.ApartmentID = ? AND m.Status = 'Active'";

        List<Meter> meters = new ArrayList<>();

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, apartmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meters.add(mapMeter(rs));
                }
            }
        }

        return meters;
    }

    public List<Meter> getAllMeters() throws SQLException {
        String sql = "SELECT m.*, a.ApartmentName, r.FullName AS OwnerName "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
                + "WHERE m.Status = 'Active' "
                + "ORDER BY a.ApartmentName, m.MeterType";

        List<Meter> meters = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                meters.add(mapMeter(rs));
            }
        }
        return meters;
    }

    public List<Meter> getMetersByType(String meterType) throws SQLException {
        String sql = "SELECT m.*, a.ApartmentName, r.FullName AS OwnerName "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.ResidentID = r.ResidentID "
                + "WHERE m.MeterType = ? AND m.Status = 'Active' "
                + "ORDER BY a.ApartmentName";

        List<Meter> meters = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meterType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meters.add(mapMeter(rs));
                }
            }
        }
        return meters;
    }
// Get all active meters with their last reading

    public List<Meter> getAllActiveMeters() throws SQLException {
        List<Meter> meters = new ArrayList<>();

        String sql = "SELECT m.MeterID, m.MeterNumber, m.MeterType, a.ApartmentName, "
                + "COALESCE((SELECT TOP 1 CurrentReading FROM MeterReading WHERE MeterID = m.MeterID "
                + "ORDER BY ReadingDate DESC), 0) AS LastReading "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "WHERE m.Status = 'Active'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Meter meter = new Meter();
                meter.setMeterId(rs.getInt("MeterID"));
                meter.setMeterNumber(rs.getString("MeterNumber"));
                meter.setMeterType(rs.getString("MeterType"));
                meter.setApartmentName(rs.getString("ApartmentName"));
                meter.setLastReading(rs.getBigDecimal("LastReading"));
                meters.add(meter);
            }
        }

        return meters;
    }

    public boolean isMeterNumberExists(String meterNumber, Integer excludeMeterId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Meter WHERE MeterNumber = ? AND Status = 'Active'";
        if (excludeMeterId != null) {
            sql += " AND MeterID != ?";
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meterNumber);
            if (excludeMeterId != null) {
                stmt.setInt(2, excludeMeterId);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    private Meter mapMeter(ResultSet rs) throws SQLException {
        Meter meter = new Meter();
        meter.setMeterId(rs.getInt("MeterID"));
        meter.setApartmentId(rs.getInt("ApartmentID"));
        meter.setMeterType(rs.getString("MeterType"));
        meter.setMeterNumber(rs.getString("MeterNumber"));
<<<<<<< HEAD
=======

>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043
        meter.setInstallationDate(rs.getTimestamp("InstallationDate").toLocalDateTime());
        meter.setStatus(rs.getString("Status"));
        meter.setApartmentName(rs.getString("ApartmentName"));
        meter.setOwnerName(rs.getString("OwnerName"));
        return meter;
    }

    public List<Meter> paegingMeter(int index) {
        String sql = "SELECT m.*, a.ApartmentName, r.FullName AS OwnerName "
                + "FROM Meter m "
                + "JOIN Apartment a ON m.ApartmentID = a.ApartmentID "
                + "LEFT JOIN Resident r ON a.OwnerId = r.ResidentID "
//                + "WHERE m.Status = 'Active' "
                + "ORDER BY a.ApartmentName, m.MeterType "
                + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY;";
        List<Meter> meters = new ArrayList<>();
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (index - 1) * 3);  // Đặt giá trị trước khi executeQuery()
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                 meters.add(mapMeter(rs));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(MeterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meters;
    }

      public int getTotalRecords() {

<<<<<<< HEAD
        String sql = "SELECT COUNT(*) AS total FROM [Meter] ";
=======
        String sql = "SELECT COUNT(*) AS total FROM [Meter] Where Status = 'Inactive'";
>>>>>>> a1fcf3c0204f8e528f5c144d1d8dbb0e569a6043

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(MeterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
//    public List<Meter> searchMeters(String meterType, int page, int pageSize) {
//    List<Meter> meters = new ArrayList<>();
//    StringBuilder sql = new StringBuilder("""
//        SELECT m.meterId, m.apartmentId, m.meterType, m.meterNumber, 
//               m.installationDate, m.status, a.name AS apartmentName
//        FROM Meter m
//        LEFT JOIN Apartment a ON m.apartmentId = a.apartmentId
//        WHERE 1=1
//    """);
//
//    List<Object> params = new ArrayList<>();
//
//    if (meterType != null && !meterType.isEmpty()) {
//        sql.append(" AND m.meterType = ?");
//        params.add(meterType);
//    }
//
//    sql.append(" ORDER BY m.installationDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
//    params.add((page - 1) * pageSize);
//    params.add(pageSize);
//
//    try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
//        for (int i = 0; i < params.size(); i++) {
//            ps.setObject(i + 1, params.get(i));
//        }
//
//        try (ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Meter meter = Meter.builder()
//                        .meterId(rs.getInt("meterId"))
//                        .apartmentId(rs.getInt("apartmentId"))
//                        .meterType(rs.getString("meterType"))
//                        .meterNumber(rs.getString("meterNumber"))
//                        .installationDate(rs.getTimestamp("installationDate").toLocalDateTime())
//                        .status(rs.getString("status"))
//                        .apartmentName(rs.getString("apartmentName"))
//                        .build();
//                meters.add(meter);
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return meters;
//}
//    public int getTotalMeters(String meterType) {
//    int totalRecords = 0;
//    String sql = "SELECT COUNT(*) FROM Meter WHERE 1=1";
//
//    if (meterType != null && !meterType.isEmpty()) {
//        sql += " AND meterType = ?";
//    }
//
//    try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//        if (meterType != null && !meterType.isEmpty()) {
//            ps.setString(1, meterType);
//        }
//
//        try (ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                totalRecords = rs.getInt(1);
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }

//    return totalRecords;
}


