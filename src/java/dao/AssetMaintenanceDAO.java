/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AssetMaintenance;

/**
 *
 * @author admin
 */
public class AssetMaintenanceDAO implements DAOInterface<AssetMaintenance, Integer> {

    @Override
    public int insert(AssetMaintenance t) {
        int row = 0;
        String sqlInsert = """
                           INSERT INTO [dbo].[AssetMaintenance]
                                      ([AssetID]
                                      ,[MaintenanceDate]
                                      ,[StaffID]
                                      ,[Description]
                                      ,[Cost]
                                      ,[NextMaintenanceDate])
                                VALUES
                                      (?, ?, ?, ?, ?, ?)""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setInt(1, t.getAsset().getAssetId());
            ps.setDate(2, (Date)t.getMaintenanceDate());
            ps.setInt(3, t.getStaff().getStaffId());
            ps.setString(4, t.getDescription());
            ps.setBigDecimal(5, t.getCost());
            ps.setDate(6, (Date)t.getNextMaintenanceDate());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssetMaintenanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(AssetMaintenance t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(AssetMaintenance t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AssetMaintenance> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AssetMaintenance selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
