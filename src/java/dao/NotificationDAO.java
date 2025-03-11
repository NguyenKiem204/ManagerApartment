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
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Notification;
import model.Staff;

/**
 *
 * @author admin
 */
public class NotificationDAO implements DAOInterface<Notification, Integer> {

    StaffDAO staffdao = new StaffDAO();
    ResidentDAO residentdao = new ResidentDAO();

    @Override
    public int insert(Notification t) {
        int row = 0;
        String sqlInsert = """
                           INSERT INTO [dbo].[Notification]
                                      ([SenderID]
                                      ,[SenderTable]
                                      ,[StaffID]
                                      ,[ResidentID]
                                      ,[Message]
                                      ,[Type]
                                      ,[CreatedAt]
                                      ,[IsRead]
                                      ,[ReferenceID]
                                      ,[ReferenceTable])
                                VALUES
                                      (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setInt(1, t.getSenderId());
            ps.setString(2, t.getSenderTable());
            if (t.getStaff() != null) {
                ps.setInt(3, t.getStaff().getStaffId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (t.getResident() != null) {
                ps.setInt(4, t.getResident().getResidentId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setString(5, t.getMessage());
            ps.setString(6, t.getType());
            ps.setTimestamp(7, Timestamp.valueOf(t.getCreatedAt()));
            ps.setBoolean(8, t.isRead());
            ps.setInt(9, t.getReferenceId());
            ps.setString(10, t.getReferenceTable());
            
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Notification t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int updateIsRead(int notificationId) {
        int row = 0;
        String sql = """
                     UPDATE [dbo].[Notification]
                        SET [IsRead] = 1
                     WHERE NotificationID = ?""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Notification t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Notification> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Notification> selectAllByStaffID(int roleID) {
        List<Notification> list = new ArrayList<>();
        int staffId = staffdao.getStaffByRoleIDAndStatus(roleID, "Active").getStaffId();
        String sql = "SELECT * FROM Notification WHERE StaffID = ? ORDER BY NotificationID DESC;";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                          rs.getInt("NotificationID"),
                          rs.getInt("senderId"),
                          rs.getString("SenderTable"),
                          rs.getString("Message"),
                          rs.getString("type"),
                          rs.getTimestamp("CreatedAt").toLocalDateTime(),
                          rs.getBoolean("IsRead"),
                          rs.getInt("ReferenceID"),
                          rs.getString("ReferenceTable"),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID"))
                );
                list.add(notification);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Notification> selectAllByResidentId(int residentId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE ResidentId = ? ORDER BY NotificationID DESC;";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, residentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                          rs.getInt("NotificationID"),
                          rs.getInt("senderId"),
                          rs.getString("SenderTable"),
                          rs.getString("Message"),
                          rs.getString("type"),
                          rs.getTimestamp("CreatedAt").toLocalDateTime(),
                          rs.getBoolean("IsRead"),
                          rs.getInt("ReferenceID"),
                          rs.getString("ReferenceTable"),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID"))
                );
                list.add(notification);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Notification selectById(Integer id) {
        String sql = """
                     SELECT [NotificationID]
                           ,[SenderID]
                           ,[SenderTable]
                           ,[StaffID]
                           ,[ResidentID]
                           ,[Message]
                           ,[Type]
                           ,[CreatedAt]
                           ,[IsRead]
                           ,[ReferenceID]
                           ,[ReferenceTable]
                       FROM [dbo].[Notification]
                       WHERE NotificationID = ?""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Notification notification = new Notification(
                          rs.getInt("NotificationID"),
                          rs.getInt("senderId"),
                          rs.getString("SenderTable"),
                          rs.getString("Message"),
                          rs.getString("type"),
                          rs.getTimestamp("CreatedAt").toLocalDateTime(),
                          rs.getBoolean("IsRead"),
                          rs.getInt("ReferenceID"),
                          rs.getString("ReferenceTable"),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID"))
                );
                    return notification;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
