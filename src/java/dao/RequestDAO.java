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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Request;

/**
 *
 * @author admin
 */
public class RequestDAO implements DAOInterface<Request, Integer> {

    @Override
    public int insert(Request t) {
        int row = 0;
        String sqlInsert = "INSERT INTO [dbo].[Request]\n"
                  + "           ([Description]\n"
                  + "           ,[Title]\n"
                  + "           ,[Status]\n"
                  + "           ,[Date]\n"
                  + "           ,[StaffID]\n"
                  + "           ,[ResidentID]\n"
                  + "           ,[TypeRqID])\n"
                  + "     VALUES\n"
                  + "           (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, t.getDescription());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getStatus());
            ps.setDate(4, Date.valueOf(t.getDate()));
            ps.setInt(5, t.getStaffID());
            ps.setInt(6, t.getResidentID());
            ps.setInt(7, t.getTypeID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Request t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Request t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Request> selectAll() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request rq = new Request(
                        rs.getInt("requestID"),
                        rs.getString("description"),
                        rs.getString("title"),
                          rs.getString("status"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("StaffID"),
                        rs.getInt("ResidentID"),
                          rs.getInt("TypeRqID")
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Request selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
