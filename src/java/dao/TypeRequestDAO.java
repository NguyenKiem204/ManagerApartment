/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TypeRequest;

/**
 *
 * @author admin
 */
public class TypeRequestDAO implements DAOInterface<TypeRequest, Integer> {

    @Override
    public int insert(TypeRequest t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(TypeRequest t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(TypeRequest t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    StaffDAO staff = new StaffDAO();

    @Override
    public List<TypeRequest> selectAll() {
        List<TypeRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM TypeRequest";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeRequest tr = new TypeRequest(
                          rs.getInt("TypeRqID"),
                          rs.getString("TypeName"),
                          staff.selectById(rs.getInt("StaffID"))
                );

                list.add(tr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public TypeRequest selectById(Integer id) {
        TypeRequest typeRequest = null;
        String sql = "SELECT * FROM [TypeRequest] WHERE [TypeRqID] = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    typeRequest = new TypeRequest(rs.getInt("TypeRqID"),
                              rs.getString("TypeName"),
                              staff.selectById(rs.getInt("StaffID"))
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeRequest;
    }

}
