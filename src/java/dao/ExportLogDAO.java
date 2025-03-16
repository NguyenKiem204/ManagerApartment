/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ExportLog;
/**
 *
 * @author nkiem
 */
public class ExportLogDAO {
    // Tạo bản ghi xuất dữ liệu mới
    public int createExportLog(ExportLog exportLog) throws SQLException {
        String sql = "INSERT INTO ExportLog (StaffID, FileName, ExportType, RecordsCount) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, exportLog.getStaffId());
            stmt.setString(2, exportLog.getFileName());
            stmt.setString(3, exportLog.getExportType());
            stmt.setInt(4, exportLog.getRecordsCount());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    // Lấy lịch sử xuất dữ liệu theo ID
    public ExportLog getExportLogById(int exportId) throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName " +
                     "FROM ExportLog l " +
                     "JOIN Staff s ON l.StaffID = s.StaffID " +
                     "WHERE l.ExportID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exportId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapExportLog(rs);
                }
            }
        }
        return null;
    }
    
    // Lấy danh sách lịch sử xuất dữ liệu
    public List<ExportLog> getAllExportLogs() throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName " +
                     "FROM ExportLog l " +
                     "JOIN Staff s ON l.StaffID = s.StaffID " +
                     "ORDER BY l.ExportDate DESC";       
        List<ExportLog> logs = new ArrayList<>();        
        try (Connection conn = DBContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logs.add(mapExportLog(rs));
            }
        }
        return logs;
    }
    
    private ExportLog mapExportLog(ResultSet rs) throws SQLException {
        ExportLog log = new ExportLog();
        log.setExportId(rs.getInt("ExportID"));
        log.setStaffId(rs.getInt("StaffID"));
        log.setExportDate(rs.getTimestamp("ExportDate").toLocalDateTime());
        log.setFileName(rs.getString("FileName"));
        log.setExportType(rs.getString("ExportType"));
        log.setRecordsCount(rs.getInt("RecordsCount"));
        log.setStaffName(rs.getString("StaffName"));
        return log;
    }
}
