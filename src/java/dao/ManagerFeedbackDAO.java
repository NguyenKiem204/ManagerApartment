/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ManagerFeedback;

/**
 *
 * @author admin
 */
public class ManagerFeedbackDAO implements DAOInterface<ManagerFeedback, Integer> {

    private StaffDAO staff = new StaffDAO();

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
            ps.setDouble(3, t.getAvgRating());
            ps.setInt(4, t.getPositivePercentage());
            ps.setInt(5, t.getNegativePercentage());
            ps.setString(6, t.getStrengths());
            ps.setString(7, t.getWeaknesses());
            ps.setString(8, t.getStaffResponse());
            ps.setString(9, t.getActionPlan());
            ps.setDate(10, Date.valueOf(t.getDeadline()));
            ps.setTimestamp(11, Timestamp.valueOf(t.getCreatedAt()));
            ps.setInt(12, t.getStaff().getStaffId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(ManagerFeedback t) {
        int row = 0;
        String sql = """
                     UPDATE [dbo].[ManagerFeedback]
                        SET [MonthYear] = ?
                           ,[TotalFeedback] = ?
                           ,[AvgRating] = ?
                           ,[PositivePercentage] = ?
                           ,[NegativePercentage] = ?
                           ,[Strengths] = ?
                           ,[Weaknesses] = ?
                           ,[StaffResponse] = ?
                           ,[ActionPlan] = ?
                           ,[Deadline] = ?
                           ,[CreatedAt] = ?
                           ,[StaffID] = ?
                      WHERE ManagerFeedbackID = ?""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(t.getMonthYear()));
            ps.setInt(2, t.getTotalFeedback());
            ps.setDouble(3, t.getAvgRating());
            ps.setInt(4, t.getPositivePercentage());
            ps.setInt(5, t.getNegativePercentage());
            ps.setString(6, t.getStrengths());
            ps.setString(7, t.getWeaknesses());
            ps.setString(8, t.getStaffResponse());
            ps.setString(9, t.getActionPlan());
            ps.setDate(10, Date.valueOf(t.getDeadline()));
            ps.setTimestamp(11, Timestamp.valueOf(t.getCreatedAt()));
            ps.setInt(12, t.getStaff().getStaffId());
            ps.setInt(13, t.getManagerFeedbackId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
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
        String sql = "SELECT * FROM [ManagerFeedback] WHERE ManagerFeedbackID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ManagerFeedback mf = new ManagerFeedback(
                              rs.getInt("ManagerFeedbackID"),
                              rs.getDate("monthYear").toLocalDate(),
                              rs.getInt("totalFeedback"),
                              rs.getDouble("avgRating"),
                              rs.getInt("positivePercentage"),
                              rs.getInt("negativePercentage"),
                              rs.getString("strengths"),
                              rs.getString("weaknesses"),
                              rs.getString("staffResponse"),
                              rs.getString("actionPlan"),
                              rs.getDate("deadline") != null ? rs.getDate("deadline").toLocalDate() : null, 
                              rs.getTimestamp("createdAt").toLocalDateTime(),
                              staff.selectById(rs.getInt("StaffID")
                              ));
                    return mf;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int selectLastId() {
        String sql = "SELECT MAX([ManagerFeedbackID]) FROM [ApartmentManagement].[dbo].[ManagerFeedback];";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị MAX(NotificationID)
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Trả về 0 nếu không có dữ liệu
    }

    public List<ManagerFeedback> selectFirstPage(int staffId) {
        List<ManagerFeedback> list = new ArrayList<>();
        String sql = "SELECT * FROM ManagerFeedback WHERE StaffID = ? ORDER BY [ManagerFeedbackID] DESC OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ManagerFeedback mf = new ManagerFeedback(
                          rs.getInt("ManagerFeedbackID"),
                          rs.getDate("monthYear").toLocalDate(),
                          rs.getInt("totalFeedback"),
                          rs.getDouble("avgRating"),
                          rs.getInt("positivePercentage"),
                          rs.getInt("negativePercentage"),
                          rs.getString("strengths"),
                          rs.getString("weaknesses"),
                          rs.getString("staffResponse"),
                          rs.getString("actionPlan"),
                          rs.getDate("deadline") != null ? rs.getDate("deadline").toLocalDate() : null, 
                          rs.getTimestamp("createdAt").toLocalDateTime(),
                          staff.selectById(rs.getInt("StaffID")
                          ));
                list.add(mf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int selectAllToCount(int staffId) {
        String sql = "SELECT count(*) FROM ManagerFeedback WHERE StaffID = ?";
        int num = 0;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public List<ManagerFeedback> getAllFeedbacksBySort(int keySort, int page, int pageSize, int staffId) {
        List<ManagerFeedback> list = new ArrayList<>();
        String sql = "SELECT * FROM ManagerFeedback WHERE StaffID = ? ";
        List<Object> params = new ArrayList<>();
        params.add(staffId);
        try {
//Xu ly search
            
            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY MonthYear DESC, ManagerFeedbackID DESC";
                    case 2 ->
                        sql += " ORDER BY MonthYear ASC, ManagerFeedbackID ASC";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY MonthYear DESC, ManagerFeedbackID DESC";
            }

//xu ly phan trang
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            int offset = (page - 1) * pageSize;
            params.add(offset);
            params.add(pageSize);

        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManagerFeedback mf = new ManagerFeedback(
                          rs.getInt("ManagerFeedbackID"),
                          rs.getDate("monthYear").toLocalDate(),
                          rs.getInt("totalFeedback"),
                          rs.getDouble("avgRating"),
                          rs.getInt("positivePercentage"),
                          rs.getInt("negativePercentage"),
                          rs.getString("strengths"),
                          rs.getString("weaknesses"),
                          rs.getString("staffResponse"),
                          rs.getString("actionPlan"),
                          rs.getDate("deadline") != null ? rs.getDate("deadline").toLocalDate() : null, 
                          rs.getTimestamp("createdAt").toLocalDateTime(),
                          staff.selectById(rs.getInt("StaffID")
                          ));
                list.add(mf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
