/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ImportLog;

/**
 *
 * @author nkiem
 */
public class ImportLogDAO {
    public int createImportLog(ImportLog importLog) throws SQLException {
        String sql = "INSERT INTO ImportLog (StaffID, FileName, RecordsCount, Status, ErrorLog) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, importLog.getStaffId());
            stmt.setString(2, importLog.getFileName());
            stmt.setInt(3, importLog.getRecordsCount());
            stmt.setString(4, importLog.getStatus());
            stmt.setString(5, importLog.getErrorLog());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    public boolean updateImportLogStatus(int importId, String status, int recordsCount, String errorLog) throws SQLException {
        String sql = "UPDATE ImportLog SET Status = ?, RecordsCount = ?, ErrorLog = ? WHERE ImportID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, recordsCount);
            stmt.setString(3, errorLog);
            stmt.setInt(4, importId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public ImportLog getImportLogById(int importId) throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName " +
                     "FROM ImportLog l " +
                     "JOIN Staff s ON l.StaffID = s.StaffID " +
                     "WHERE l.ImportID = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, importId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapImportLog(rs);
                }
            }
        }
        return null;
    }
    
    public List<ImportLog> getAllImportLogs() throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName " +
                     "FROM ImportLog l " +
                     "JOIN Staff s ON l.StaffID = s.StaffID " +
                     "ORDER BY l.ImportDate DESC";
        List<ImportLog> logs = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logs.add(mapImportLog(rs));
            }
        }
        return logs;
    }
    
    private ImportLog mapImportLog(ResultSet rs) throws SQLException {
        ImportLog log = new ImportLog();
        log.setImportId(rs.getInt("ImportID"));
        log.setStaffId(rs.getInt("StaffID"));
        log.setImportDate(rs.getTimestamp("ImportDate").toLocalDateTime());
        log.setFileName(rs.getString("FileName"));
        log.setRecordsCount(rs.getInt("RecordsCount"));
        log.setStatus(rs.getString("Status"));
        log.setErrorLog(rs.getString("ErrorLog"));
        log.setStaffName(rs.getString("StaffName"));
        return log;
    }
}


