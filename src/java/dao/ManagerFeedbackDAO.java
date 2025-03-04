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
import model.ManagerFeedback;

/**
 *
 * @author admin
 */
public class ManagerFeedbackDAO implements DAOInterface<ManagerFeedback, Integer>  {

    @Override
    public int insert(ManagerFeedback t) {
        int row = 0;
        String sqlInsert = """
                           INSERT INTO [dbo].[ManagerFeedback]
                                      ([MonthYear]
                                      ,[TotalFeedback]
                                      ,[AvgRating]
                                      ,[PositivePercentage]
                                      ,[NegativePercentage]
                                      ,[Strengths]
                                      ,[Weaknesses]
                                      ,[StaffResponse]
                                      ,[ActionPlan]
                                      ,[Deadline]
                                      ,[CreatedAt]
                                      ,[StaffID])
                                VALUES
                                      (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setDate(1, Date.valueOf(t.getMonthYear()));
            ps.setInt(2, t.getTotalFeedback());
            ps.setDouble(3,t.getAvgRating());
            ps.setInt(4,t.getPositivePercentage());
            ps.setInt(5,t.getNegativePercentage());
            ps.setString(6, t.getStrengths());
            ps.setString(7, t.getWeaknesses());
            ps.setString(8, t.getStaffResponse());
            ps.setString(9, t.getActionPlan());
            ps.setDate(10, Date.valueOf(t.getDeadline()));
            ps.setDate(11, Date.valueOf(t.getCreatedAt()));
            ps.setInt(12, t.getStaff().getStaffId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(ManagerFeedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(ManagerFeedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ManagerFeedback> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ManagerFeedback selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
