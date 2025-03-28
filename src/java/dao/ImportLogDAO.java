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
        String sql = "INSERT INTO ImportLog (StaffID, FileName, RecordsCount, Status, ErrorLog) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, recordsCount);
            stmt.setString(3, errorLog);
            stmt.setInt(4, importId);
            return stmt.executeUpdate() > 0;
        }
    }

    public ImportLog getImportLogById(int importId) throws SQLException {
        String sql = "SELECT l.*, s.FullName AS StaffName "
                + "FROM ImportLog l "
                + "JOIN Staff s ON l.StaffID = s.StaffID "
                + "WHERE l.ImportID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT l.*, s.FullName AS StaffName "
                + "FROM ImportLog l "
                + "JOIN Staff s ON l.StaffID = s.StaffID "
                + "ORDER BY l.ImportDate DESC";
        List<ImportLog> logs = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

    public int getTotalImportLogs(String status) {
    int totalRecords = 0;
    String sql = "SELECT COUNT(*) FROM ImportLog WHERE 1=1";

    if (status != null && !status.isEmpty()) {
        sql += " AND status = ?";
    }

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public List<ImportLog> searchImportLogs(String fileName, int staffId, String status, String startDate, String endDate, int page, int pageSize) {
        List<ImportLog> importLogs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT i.importId, i.staffId, i.importDate, i.fileName, i.recordsCount, i.status, i.errorLog,
               s.FullName AS staffName
        FROM ImportLog i
        LEFT JOIN Staff s ON i.staffId = s.staffId
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (fileName != null && !fileName.isEmpty()) {
            sql.append(" AND i.fileName LIKE ?");
            params.add("%" + fileName + "%");
        }
        if (staffId > 0) {
            sql.append(" AND i.staffId = ?");
            params.add(staffId);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND i.status = ?");
            params.add(status);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND i.importDate >= ?");
            params.add(Timestamp.valueOf(startDate + " 00:00:00"));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND i.importDate <= ?");
            params.add(Timestamp.valueOf(endDate + " 23:59:59"));
        }

        // Phân trang (dành cho SQL Server)
        sql.append(" ORDER BY i.importDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportLog log = ImportLog.builder()
                            .importId(rs.getInt("importId"))
                            .staffId(rs.getInt("staffId"))
                            .importDate(rs.getTimestamp("importDate").toLocalDateTime())
                            .fileName(rs.getString("fileName"))
                            .recordsCount(rs.getInt("recordsCount"))
                            .status(rs.getString("status"))
                            .errorLog(rs.getString("errorLog"))
                            .staffName(rs.getString("staffName"))
                            .build();
                    importLogs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return importLogs;
    }

}
