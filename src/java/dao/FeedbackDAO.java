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

    StaffDAO st = new StaffDAO();

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
            ps.setInt(5, fb.getStaff().getStaffId());
            ps.setInt(6, fb.getResident().getResidentId());
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

    StaffDAO staff = new StaffDAO();
    ResidentDAO resident = new ResidentDAO();

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
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
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

    public List<Feedback> getAllFeedbacksBySearchOrFilterOrSort(String keySearch, int roleID, int rating, LocalDate date, int keySort) {
        List<Feedback> list = new ArrayList<>();
        String sql = """
                     SELECT [FeedbackID]
                           ,[Title]
                           ,f.Description
                           ,[Date]
                           ,[Rate]
                           ,f.StaffID
                           ,[ResidentID]
                           ,s.FullName
                     ,s.RoleID
                           ,r.RoleName
                       FROM [dbo].[Feedback] f 
                     JOIN Staff s on f.StaffID = s.StaffID
                                join Role r on s.RoleID = r.RoleID
                       WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (r.RoleName LIKE ? OR Title LIKE ? OR s.FullName LIKE ?)";
                params.add("%"+ keySearch +"%");
                params.add("%"+ keySearch +"%");
                params.add("%"+ keySearch +"%");
            }

//Xu ly filter
            //check roleName is null or not
            if (roleID != 0) {
                sql += " AND r.RoleID = ?";
                params.add(roleID);
            }

            //check rating is null or not
            if (rating != 0) {
                sql += " AND Rate = ?";
                params.add(rating);
            }

            //check date is null or not
            if (date != null) {
                sql += " AND CAST([Date] AS DATE) = ?";
                params.add(Date.valueOf(date));
            }
//------------------------------------------

    //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 2 ->
                        sql += " ORDER BY Rate";
                    case 1 ->
                        sql += " ORDER BY StaffID";
                    case 3 ->
                        sql += " ORDER BY Date DESC";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY Date DESC";
            }
        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Feedback> getListByPage(List<Feedback> list, int start, int end) {
        List<Feedback> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
    
    public int getLatestFeedbackID(){
        int latestFeedbackID = 1; // Giá trị mặc định nếu không có feedback nào
        String sql = "SELECT MAX(FeedbackID) FROM Feedback"; 
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                latestFeedbackID = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return latestFeedbackID;
    }
}
