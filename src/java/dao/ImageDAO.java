/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Image;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageDAO implements DAOInterface<Image, Integer> {
    
    @Override
    public int insert(Image image) {
        int row = 0;
        String sqlInsert = "INSERT INTO [Image] (ImageURL) VALUES (?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, image.getImageURL());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Image image) {
        int row = 0;
        String sql = "UPDATE [Image] SET ImageURL = ? WHERE ImageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, image.getImageURL());
            ps.setInt(2, image.getImageID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Image image) {
        int row = 0;
        String sql = "DELETE FROM [Image] WHERE ImageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, image.getImageID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Image> selectAll() {
        List<Image> list = new ArrayList<>();
        String sql = "SELECT * FROM [Image]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Image image = new Image(
                        rs.getInt("ImageID"),
                        rs.getString("ImageURL")
                );
                list.add(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Image selectById(Integer id) {
        Image image = null;
        String sql = "SELECT * FROM [Image] WHERE ImageID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    image = new Image(
                            rs.getInt("ImageID"),
                            rs.getString("ImageURL")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
}

