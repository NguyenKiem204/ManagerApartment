/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.PasswordUtil;
import model.Staff;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nkiem
 */

public class StaffDAO implements DAOInterface<Staff, Integer> {

    private final PasswordUtil passwordEncode = new PasswordUtil();

    @Override
    public int insert(Staff staff) {
        int row = 0;
        String sqlInsert = "INSERT INTO Staff (FullName, Password, PhoneNumber, CCCD, Email, DOB, Sex, ImageID, Status, RoleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, staff.getFullName());
            ps.setString(2, passwordEncode.hashPassword(staff.getPassword()));
            ps.setString(3, staff.getPhoneNumber());
            ps.setString(4, staff.getCccd());
            ps.setString(5, staff.getEmail());
            ps.setDate(6, Date.valueOf(staff.getDob()));
            ps.setString(7, staff.getSex());
            ps.setInt(8, staff.getImageId());
            ps.setString(9, "Active");
            ps.setInt(10, staff.getRoleId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Staff staff) {
        int row = 0;
        String sql = "UPDATE Staff SET FullName = ?, Password = ?, PhoneNumber = ?, CCCD = ?, Email = ?, DOB = ?, Sex = ?, ImageID = ?, Status = ?, RoleID = ? WHERE StaffID = ?";
        
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, staff.getFullName());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getPhoneNumber());
            ps.setString(4, staff.getCccd());
            ps.setString(5, staff.getEmail());
            ps.setDate(6, Date.valueOf(staff.getDob()));
            ps.setString(7, staff.getSex());
            ps.setInt(8, staff.getImageId());
            ps.setString(9, staff.getStatus());
            ps.setInt(10, staff.getRoleId());
            ps.setInt(11, staff.getStaffId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Staff staff) {
        int row = 0;
        String sql = "DELETE FROM Staff WHERE StaffID = ?";
        
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staff.getStaffId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Staff> selectAll() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("StaffID"),
                    rs.getString("FullName"),
                    rs.getString("Password"),
                    rs.getString("PhoneNumber"),
                    rs.getString("CCCD"),
                    rs.getString("Email"),
                    rs.getDate("DOB").toLocalDate(),
                    rs.getString("Sex"),
                    rs.getString("Status"),
                        rs.getInt("ImageID"),
                    rs.getInt("RoleID")
                );
                list.add(staff);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Staff selectById(Integer id) {
        Staff staff = null;
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff(
                    rs.getInt("StaffID"),
                    rs.getString("FullName"),
                    rs.getString("Password"),
                    rs.getString("PhoneNumber"),
                    rs.getString("CCCD"),
                    rs.getString("Email"),
                    rs.getDate("DOB").toLocalDate(),
                    rs.getString("Sex"),
                    rs.getString("Status"),
                        rs.getInt("ImageID"),
                    rs.getInt("RoleID")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }
    public List<Staff> getAllStaffs(String sex, String status) {
    List<Staff> staffs = new ArrayList<>();
    try {
        String query = "SELECT * FROM Staff WHERE 1=1";
        
        if (sex != null && !sex.isEmpty()) {
            query += " AND Sex = ?";
        }
        if (status != null && !status.isEmpty()) {
            query += " AND Status = ?";
        }
        Connection conn = DBContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        int paramIndex = 1;
        
        if (sex != null && !sex.isEmpty()) {
            ps.setString(paramIndex++, sex);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(
                 rs.getInt("StaffID"),
                    rs.getString("FullName"),
                    rs.getString("Password"),
                    rs.getString("PhoneNumber"),
                    rs.getString("CCCD"),
                    rs.getString("Email"),
                    rs.getDate("DOB").toLocalDate(),
                    rs.getString("Sex"),
                    rs.getString("Status"),
                        rs.getInt("ImageID"),
                    rs.getInt("RoleID")
            );
            staffs.add(staff);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return staffs;
}
    public List<Staff> searchStaffs(String keyword, String sex, String status) {
    List<Staff> staffs = new ArrayList<>();
    String query = "SELECT * FROM Staff WHERE (FullName LIKE ? OR Email LIKE ?)";

    if (sex != null && !sex.isEmpty()) {
        query += " AND Sex = ?";
    }
    if (status != null && !status.isEmpty()) {
        query += " AND Status = ?";
    }

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");

        int paramIndex = 3;
        if (sex != null && !sex.isEmpty()) {
            ps.setString(paramIndex++, sex);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff(
                rs.getInt("StaffID"),
                        rs.getString("FullName"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber"),
                        rs.getString("CCCD"),
                        rs.getString("Email"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Sex"),
                        rs.getString("Status"),
                        rs.getInt("ImageID"),
                        rs.getInt("RoleID")
            );
            staffs.add(staff);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return staffs;
}
public boolean isStaffExists(String phoneNumber, String cccd, String email) {
    String sql = "SELECT COUNT(*) FROM Staff WHERE PhoneNumber = ? OR Cccd = ? OR Email = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, phoneNumber);
        ps.setString(2, cccd);
        ps.setString(3, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Nếu COUNT > 0 nghĩa là đã tồn tại
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean updateStatus(int staffId, String newStatus) {
    String query = "UPDATE Staff SET Status = ? WHERE StaffID = ?";
    try {
        Connection conn = DBContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, newStatus);
        ps.setInt(2, staffId);

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean existEmail(String email) {
        String sql = "SELECT * FROM Staff WHERE Email = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Staff checkLogin(String email, String password) {
        Staff staff = null;
        String sql = "SELECT * FROM Staff WHERE Email = ?";
        System.out.println(sql);
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int staffId = rs.getInt("StaffID");
                    String storedEmail = rs.getString("Email");
                    String storedPasswordHash = rs.getString("Password");
                    String status = rs.getString("Status");
                    String sex = rs.getString("Sex");
                    String cccd = rs.getString("CCCD");
                    String fullName = rs.getString("FullName");
                    String phoneNumber = rs.getString("PhoneNumber");
                    LocalDate dob = rs.getDate("DOB").toLocalDate();
                    int roleId = rs.getInt("RoleID");
                    int imageId = rs.getInt("ImageID");
                    
                    if (passwordEncode.checkPassword(password, storedPasswordHash) && "ACTIVE".equals(status.toUpperCase())) {
                        staff = new Staff(staffId, fullName, password, phoneNumber, cccd, storedEmail, dob, sex, status, imageId, roleId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean updatePasswordInDatabase(String email, String hashedPassword) {
        try {
            Connection connection = DBContext.getConnection();
            String sql = "UPDATE Staff SET Password = ? WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, hashedPassword);
            statement.setString(2, email);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

