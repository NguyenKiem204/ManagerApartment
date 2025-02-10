/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;
import java.sql.*;

/**
 *
 * @author admin
 */
public class FeedbackDAO implements DAOInterface<Feedback, Integer> {

    @Override
    public int insert(Feedback fb) {
        int row = 0;
        String sqlInsert = "INSERT INTO [dbo].[Feedback]\n"
                  + "           ([Title]\n"
                  + "           ,[Description]\n"
                  + "           ,[Date]\n"
                  + "           ,[Rate]\n"
                  + "           ,[StaffID]\n"
                  + "           ,[ResidentID])\n"
                  + "     VALUES\n"
                  + "           (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, fb.getTitle());
            ps.setString(2, fb.getDescription());
            ps.setDate(3, Date.valueOf(fb.getDate()));
            ps.setInt(4, fb.getRate());
            ps.setInt(5, fb.getStaffID());
            ps.setInt(6, fb.getResidentID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Feedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Feedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Feedback> selectAll() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("Rate"),
                        rs.getInt("StaffID"),
                        rs.getInt("ResidentID")
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Feedback selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<Feedback> getAllFeedbacksSortedByStaff(){
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY StaffID";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("Rate"),
                        rs.getInt("StaffID"),
                        rs.getInt("ResidentID")
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Feedback> getAllFeedbacksSortedByRating(){
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY Rate";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("Rate"),
                        rs.getInt("StaffID"),
                        rs.getInt("ResidentID")
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Feedback> getAllFeedbacksSortedByDate(){
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY Date DESC";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("Rate"),
                        rs.getInt("StaffID"),
                        rs.getInt("ResidentID")
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
