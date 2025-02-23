/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Apartment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nkiem
 */
public class ApartmentDAO implements DAOInterface<Apartment, Integer> {

    @Override
    public int insert(Apartment apartment) {
        int row = 0;
        String sqlInsert = "INSERT INTO Apartment (ApartmentName, Block, Status, Type) VALUES (?, ?, ?, ?)";
        System.out.println(sqlInsert);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, apartment.getApartmentName());
            ps.setString(2, apartment.getBlock());
            ps.setString(3, apartment.getStatus());
            ps.setString(4, apartment.getType());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Apartment apartment) {
        int row = 0;
        String sql = "UPDATE Apartment SET ApartmentName = ?, Block = ?, Status = ?, Type = ? WHERE ApartmentID = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, apartment.getApartmentName());
            ps.setString(2, apartment.getBlock());
            ps.setString(3, apartment.getStatus());
            ps.setString(4, apartment.getType());
            ps.setInt(5, apartment.getApartmentId());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Apartment apartment) {
        int row = 0;
        String sql = "DELETE FROM Apartment WHERE ApartmentID = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, apartment.getApartmentId());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Apartment> selectAll() {
        List<Apartment> list = new ArrayList<>();
        String sql = "SELECT * FROM Apartment";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment(
                          rs.getInt("ApartmentID"),
                          rs.getString("ApartmentName"),
                          rs.getString("Block"),
                          rs.getString("Status"),
                          rs.getString("Type")
                );
                list.add(apartment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Apartment selectById(Integer id) {
        Apartment apartment = null;
        String sql = "SELECT * FROM Apartment WHERE ApartmentID = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    apartment = new Apartment(
                              rs.getInt("ApartmentID"),
                              rs.getString("ApartmentName"),
                              rs.getString("Block"),
                              rs.getString("Status"),
                              rs.getString("Type")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apartment;
    }

    public Apartment getApartmentByName(String apartmentName) {
        Apartment apartment = null;
        String sql = "SELECT * FROM Apartment WHERE ApartmentName = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, apartmentName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    apartment = new Apartment(
                              rs.getInt("ApartmentID"),
                              rs.getString("ApartmentName"),
                              rs.getString("Block"),
                              rs.getString("Status"),
                              rs.getString("Type")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apartment;
    }

    public List<Apartment> selectAllOcc() {
        List<Apartment> list = new ArrayList<>();
        String sql = "SELECT * FROM Apartment a where a.Status='Occupied'";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment(
                          rs.getInt("ApartmentID"),
                          rs.getString("ApartmentName"),
                          rs.getString("Block"),
                          rs.getString("Status"),
                          rs.getString("Type")
                );
                list.add(apartment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<String> getApartmentNames(String searchQuery) {
        List<String> apartments = new ArrayList<>();
        String sql = "SELECT TOP 5 ApartmentName FROM Apartment WHERE ApartmentName LIKE ?"; // Giới hạn kết quả

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, searchQuery + "%"); // Tìm kiếm theo tiền tố
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                apartments.add(rs.getString("ApartmentName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apartments;
    }
}
