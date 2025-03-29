/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ExportLog;
import model.ImportLog;

/**
 *
 * @author nkiem
 */
public class ExportLogDAO {

    public int createExportLog(ExportLog exportLog) throws SQLException {
        String sql = "INSERT INTO ExportLog (StaffID, FileName, ExportType, RecordsCount) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public ExportLog getExportLogById(int exportId) throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName "
                + "FROM ExportLog l "
                + "JOIN Staff s ON l.StaffID = s.StaffID "
                + "WHERE l.ExportID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exportId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapExportLog(rs);
                }
            }
        }
        return null;
    }

    public List<ExportLog> getAllExportLogs() throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName "
                + "FROM ExportLog l "
                + "JOIN Staff s ON l.StaffID = s.StaffID "
                + "ORDER BY l.ExportDate DESC";
        List<ExportLog> logs = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

    public int getTotalExportLogs(String status) {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM ExportLog WHERE 1=1";

        if (status != null && !status.isEmpty()) {
            sql += " AND ExportType = ?";
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            if (status != null && !status.isEmpty()) {
                ps.setString(1, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRecords = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRecords;
    }

    public List<ExportLog> searchExportLogs(String fileName, int staffId, String exportType, String startDate, String endDate, int page, int pageSize) {
        List<ExportLog> exportLogs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT e.exportId, e.staffId, e.exportDate, e.fileName, e.recordsCount, e.exportType, 
               s.FullName AS staffName
        FROM ExportLog e
        LEFT JOIN Staff s ON e.staffId = s.staffId
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (fileName != null && !fileName.isEmpty()) {
            sql.append(" AND e.fileName LIKE ?");
            params.add("%" + fileName + "%");
        }
        if (staffId > 0) {
            sql.append(" AND e.staffId = ?");
            params.add(staffId);
        }
        if (exportType != null && !exportType.isEmpty()) {
            sql.append(" AND e.exportType = ?");
            params.add(exportType);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND e.exportDate >= ?");
            params.add(Timestamp.valueOf(startDate + " 00:00:00"));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND e.exportDate <= ?");
            params.add(Timestamp.valueOf(endDate + " 23:59:59"));
        }

        // Phân trang (SQL Server)
        sql.append(" ORDER BY e.exportDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExportLog log = ExportLog.builder()
                            .exportId(rs.getInt("exportId"))
                            .staffId(rs.getInt("staffId"))
                            .exportDate(rs.getTimestamp("exportDate").toLocalDateTime())
                            .fileName(rs.getString("fileName"))
                            .recordsCount(rs.getInt("recordsCount"))
                            .exportType(rs.getString("exportType"))
                            .staffName(rs.getString("staffName"))
                            .build();
                    exportLogs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exportLogs;
    }

}
