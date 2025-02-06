/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleDAO implements DAOInterface<Role, Integer> {
    
    @Override
    public int insert(Role role) {
        int row = 0;
        String sqlInsert = "INSERT INTO [Role] (RoleName, Description) VALUES (?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Role role) {
        int row = 0;
        String sql = "UPDATE [Role] SET RoleName = ?, Description = ? WHERE RoleID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());
            ps.setInt(3, role.getRoleID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Role role) {
        int row = 0;
        String sql = "DELETE FROM [Role] WHERE RoleID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, role.getRoleID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Role> selectAll() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM [Role]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("RoleID"),
                        rs.getString("RoleName"),
                        rs.getString("Description")
                );
                list.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Role selectById(Integer id) {
        Role role = null;
        String sql = "SELECT * FROM [Role] WHERE RoleID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role = new Role(
                            rs.getInt("RoleID"),
                            rs.getString("RoleName"),
                            rs.getString("Description")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }
}
