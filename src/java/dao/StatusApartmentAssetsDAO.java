package dao;


import dao.DAOInterface;
import dao.DBContext;
import dao.NewsDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AssetCategory;
import model.StatusApartmentAssets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class StatusApartmentAssetsDAO implements DAOInterface<StatusApartmentAssets, Integer>{

    @Override
    public int insert(StatusApartmentAssets t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(StatusApartmentAssets t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(StatusApartmentAssets t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<StatusApartmentAssets> selectAll() {
        List<StatusApartmentAssets> list = new ArrayList<>();
        String sql = "SELECT * FROM StatusApartmentAssets";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatusApartmentAssets saa = new StatusApartmentAssets(rs.getInt("statusID"),
                          rs.getString("StatusName"));
                list.add(saa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatusApartmentAssetsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public StatusApartmentAssets selectById(Integer id) {
        String sql = "SELECT * FROM StatusApartmentAssets WHERE StatusID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StatusApartmentAssets saa = new StatusApartmentAssets(rs.getInt("StatusID"), 
                              rs.getString("statusName"));
                    return saa;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
