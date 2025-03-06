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
                                                 ([StaffID]
                                                 ,[ResidentID]
                                                 ,[Message]
                                                 ,[Type]
                                                 ,[ReferenceID]
                                                 ,[ReferenceTable]
                                                 ,[CreatedAt]
                                                 ,[IsRead])
                                           VALUES
                                                 (?, ?, ?, ?, ?, ?, ?, ?)""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            if (t.getStaff() != null) {
                ps.setInt(1, t.getStaff().getStaffId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            if (t.getResident() != null) {
                ps.setInt(2, t.getResident().getResidentId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, t.getMessage());
            ps.setString(4, t.getType());
            ps.setInt(5, t.getReferenceId());
            ps.setString(6, t.getReferenceTable());
            ps.setDate(7, Date.valueOf(t.getCreatedAt()));
            ps.setBoolean(8, t.isRead());
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
        String sql = "SELECT * FROM Notification WHERE StaffID = ?;";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                          rs.getInt("NotificationID"),
                          rs.getString("Message"),
                          rs.getString("type"),
                          rs.getDate("CreatedAt").toLocalDate(),
                          rs.getBoolean("IsRead"),
                          rs.getInt("ReferenceID"),
                          rs.getString("ReferenceTable"),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID")));
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
                           ,[StaffID]
                           ,[ResidentID]
                           ,[Message]
                           ,[Type]
                           ,[CreatedAt]
                           ,[IsRead]
                       FROM [ApartmentManagement].[dbo].[Notification]
                       where NotificationID = ?""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Notification n = new Notification(
                              rs.getInt("NotificationID"),
                              rs.getString("Message"),
                              rs.getString("type"),
                              rs.getDate("CreatedAt").toLocalDate(),
                              rs.getBoolean("IsRead"),
                              rs.getInt("ReferenceID"),
                              rs.getString("ReferenceTable"),
                              staffdao.selectById(rs.getInt("StaffID")),
                              residentdao.selectById(rs.getInt("ResidentID")));
                    return n;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
