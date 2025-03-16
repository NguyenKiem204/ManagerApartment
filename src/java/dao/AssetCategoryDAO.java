/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AssetCategory;

/**
 *
 * @author admin
 */
public class AssetCategoryDAO implements DAOInterface<AssetCategory, Integer> {

    @Override
    public int insert(AssetCategory t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(AssetCategory t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(AssetCategory t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AssetCategory> selectAll() {
        List<AssetCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM AssetCategory";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AssetCategory ac = new AssetCategory(rs.getInt("categoryId"),rs.getString("CategoryName"));
                list.add(ac);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssetCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public AssetCategory selectById(Integer id) {
        String sql = "SELECT * FROM [AssetCategory] WHERE CategoryID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AssetCategory ac = new AssetCategory(rs.getInt("CategoryID"),
                              rs.getString("CategoryName"));
                    return ac;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
