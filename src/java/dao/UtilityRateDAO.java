package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UtilityRate;

public class UtilityRateDAO {

    public int addUtilityRate(UtilityRate rate) throws SQLException {
        String sql = "INSERT INTO UtilityRate (UtilityType, RateDescription, UnitPrice, "
                + "EffectiveFrom, EffectiveTo, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rate.getUtilityType());
            stmt.setString(2, rate.getRateDescription());
            stmt.setBigDecimal(3, rate.getUnitPrice());
            stmt.setTimestamp(4, Timestamp.valueOf(rate.getEffectiveFrom()));

            if (rate.getEffectiveTo() != null) {
                stmt.setTimestamp(5, Timestamp.valueOf(rate.getEffectiveTo()));
            } else {
                stmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

            stmt.setString(6, rate.getStatus());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    public boolean updateUtilityRate(UtilityRate rate) throws SQLException {
        String sql = "UPDATE UtilityRate SET UtilityType = ?, RateDescription = ?, UnitPrice = ?, "
                + "EffectiveFrom = ?, EffectiveTo = ?, Status = ? "
                + "WHERE RateID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rate.getUtilityType());
            stmt.setString(2, rate.getRateDescription());
            stmt.setBigDecimal(3, rate.getUnitPrice());
            stmt.setTimestamp(4, Timestamp.valueOf(rate.getEffectiveFrom()));
            
            if (rate.getEffectiveTo() != null) {
                stmt.setTimestamp(5, Timestamp.valueOf(rate.getEffectiveTo()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }
            
            stmt.setString(6, rate.getStatus());
            stmt.setInt(7, rate.getRateId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUtilityRate(int rateId) throws SQLException {
        String sql = "UPDATE UtilityRate SET Status = 'Inactive' WHERE RateID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rateId);
            return stmt.executeUpdate() > 0;
        }
    }

    public UtilityRate getUtilityRateById(int rateId) throws SQLException {
        String sql = "SELECT * FROM UtilityRate WHERE RateID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rateId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUtilityRate(rs);
                }
            }
        }
        return null;
    }

    public UtilityRate getCurrentUtilityRate(String utilityType, LocalDateTime dateTime) {
        String sql = "SELECT TOP 1 * FROM UtilityRate "
                + "WHERE UtilityType = ? AND Status = 'Active' "
                + "AND EffectiveFrom <= ? AND (EffectiveTo IS NULL OR EffectiveTo >= ?) "
                + "ORDER BY EffectiveFrom DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilityType);
            stmt.setTimestamp(2, Timestamp.valueOf(dateTime));
            stmt.setTimestamp(3, Timestamp.valueOf(dateTime));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUtilityRate(rs);
                }
            }
        }catch(SQLException ex){
            Logger.getLogger(UtilityRateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<UtilityRate> getAllUtilityRates() throws SQLException {
        String sql = "SELECT * FROM UtilityRate ORDER BY UtilityType, EffectiveFrom DESC";
        List<UtilityRate> rates = new ArrayList<>();
        
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rates.add(mapUtilityRate(rs));
            }
        }
        return rates;
    }

    public List<UtilityRate> getUtilityRatesByType(String utilityType) throws SQLException {
        String sql = "SELECT * FROM UtilityRate WHERE UtilityType = ? ORDER BY EffectiveFrom DESC";
        List<UtilityRate> rates = new ArrayList<>();
        
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilityType);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rates.add(mapUtilityRate(rs));
                }
            }
        }
        return rates;
    }
    
    private UtilityRate mapUtilityRate(ResultSet rs) throws SQLException {
        UtilityRate rate = new UtilityRate();
        rate.setRateId(rs.getInt("RateID"));
        rate.setUtilityType(rs.getString("UtilityType"));
        rate.setRateDescription(rs.getString("RateDescription"));
        rate.setUnitPrice(rs.getBigDecimal("UnitPrice"));
        rate.setEffectiveFrom(rs.getTimestamp("EffectiveFrom").toLocalDateTime());
        
        Timestamp effectiveTo = rs.getTimestamp("EffectiveTo");
        rate.setEffectiveTo(effectiveTo != null ? effectiveTo.toLocalDateTime() : null);
        
        rate.setStatus(rs.getString("Status"));
        return rate;
    }
}
