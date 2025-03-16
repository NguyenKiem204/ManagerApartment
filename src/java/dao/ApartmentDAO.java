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
import model.Resident;

/**
 *
 * @author nkiem
 */
public class ApartmentDAO implements DAOInterface<Apartment, Integer> {
    ResidentDAO residentDAO = new ResidentDAO();
    @Override
    public int insert(Apartment apartment) {
        int row = 0;
        String sqlInsert = "INSERT INTO Apartment (ApartmentName, Block, Status, Type, OwnerID) VALUES (?, ?, ?, ?, ?)";
        System.out.println(sqlInsert);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, apartment.getApartmentName());
            ps.setString(2, apartment.getBlock());
            ps.setString(3, apartment.getStatus());
            ps.setString(4, apartment.getType());
            ps.setInt(5, apartment.getOwnerId());
            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

//    @Override
//    public int update(Apartment apartment) {
//        int row = 0;
//        String sql = "UPDATE Apartment SET ApartmentName = ?, Block = ?, Status = ?, Type = ?, OwnerID = ? WHERE ApartmentID = ?";
//        System.out.println(sql);
//
//        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, apartment.getApartmentName());
//            ps.setString(2, apartment.getBlock());
//            ps.setString(3, apartment.getStatus());
//            ps.setString(4, apartment.getType());
//            
//            ps.setInt(6, apartment.getOwnerId());
//            ps.setInt(5, apartment.getApartmentId());
//            row = ps.executeUpdate();
//            System.out.println("(" + row + " row(s) affected)");
//        } catch (SQLException ex) {
//            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return row;
//    }
    @Override
    public int update(Apartment apartment) {
    int rowUpdated = 0;
    String sql = "UPDATE Apartment SET ApartmentName = ?, Block = ?, Status = ?, Type = ?, OwnerID = ? WHERE ApartmentID = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, apartment.getApartmentName());
        ps.setString(2, apartment.getBlock());
        ps.setString(3, apartment.getStatus());
        ps.setString(4, apartment.getType());
        ps.setInt(5, apartment.getOwnerId());
        ps.setInt(6, apartment.getApartmentId());

        rowUpdated = ps.executeUpdate();
        System.out.println("Rows updated: " + rowUpdated);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return rowUpdated;
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
                        rs.getString("Type"),
                        rs.getInt("OwnerID")
                );
                list.add(apartment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int numberApartment() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Apartment";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    public int numberApartmentOccupied() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Apartment Where Status = 'Occupied'";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
        public int numberApartmentAvailable() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Apartment Where Status = 'Available'";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
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
                            rs.getString("Type"),
                            rs.getInt("OwnerID")
                            
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apartment;
    }

    public static void main(String[] args) {
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        int testApartmentId = 7; // Thay thế bằng ID thực tế trong DB
        
        Apartment apartment = apartmentDAO.selectById(testApartmentId);
        
        if (apartment != null) {
            System.out.println("Apartment Found:");
         
            System.out.println("Name: " + apartment.getApartmentName());
            System.out.println("Block: " + apartment.getBlock());
            System.out.println("Status: " + apartment.getStatus());
            System.out.println("Type: " + apartment.getType());
    
        } else {
            System.out.println("No apartment found with ID: " + testApartmentId);
        }
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
                            rs.getString("Type"),
                            rs.getInt("OwnerID")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apartment;
    }
    public boolean isApartmentExists(String apartmentName, String block) {
        String sql = "SELECT COUNT(*) FROM Apartment WHERE ApartmentName = ? AND Block = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, apartmentName);
            ps.setString(2, block);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 nghĩa là đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Apartment getApartmentById(int apartmentId) {
        Apartment apartment = null;
        String sql = "SELECT * FROM Apartment WHERE ApartmentID = ? ";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, apartmentId);
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
                        rs.getString("Type"),
                       rs.getInt("OwnerID")
                );
                list.add(apartment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    
//    public List<Apartment> searchApartments(String name, int ownerId, String type, String status, String block, int page, int pageSize) {
//    List<Apartment> apartments = new ArrayList<>();
//    String sql = "SELECT ApartmentID, ApartmentName, Block, Status, Type, OwnerID FROM Apartment WHERE 1=1";
//
//    // Xây dựng SQL động
//    if (name != null && !name.isEmpty()) {
//        sql += " AND ApartmentName LIKE ?";
//    }
////    if (ownerId != null && !ownerId.isEmpty()) {
//        sql += " AND OwnerID = ?";
////    }
//    if (type != null && !type.isEmpty()) {
//        sql += " AND Type = ?";
//    }
//    if (status != null && !status.isEmpty()) {
//        sql += " AND Status = ?";
//    }
//    if (block != null && !block.isEmpty()) {
//        sql += " AND Block = ?";
//    }
//    // Thêm ORDER BY và phân trang
//    sql += " ORDER BY ApartmentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//    try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
//        int paramIndex = 1;
//        if (name != null && !name.isEmpty()) {
//            ps.setString(paramIndex++, "%" + name + "%"); // Tìm kiếm gần đúng
//        }
////        if (ownerId != null && !ownerId.isEmpty()) {
//            ps.setInt(paramIndex++, ownerId);
////        }
//        if (type != null && !type.isEmpty()) {
//            ps.setString(paramIndex++, type);
//        }
//        if (status != null && !status.isEmpty()) {
//            ps.setString(paramIndex++, status);
//        }
//        if (block != null && !block.isEmpty()) {
//            ps.setString(paramIndex++, block);
//        }
//        // Thiết lập OFFSET và FETCH NEXT cho phân trang
//        int offset = (page - 1) * pageSize;
//        ps.setInt(paramIndex++, offset);
//        ps.setInt(paramIndex++, pageSize);
//        try (ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Apartment apt = new Apartment(
//                    rs.getInt("ApartmentID"),
//                    rs.getString("ApartmentName"),
//                    rs.getString("Block"),
//                    rs.getString("Status"),
//                    rs.getString("Type"),
//                    rs.getInt("OwnerID")
//                );
//                apartments.add(apt);
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return apartments;
//}

//public int getTotalApartments(String name, int ownerId, String type, String status, String block) {
//    int total = 0;
//    String sql = "SELECT COUNT(*) FROM Apartment a " +
//                 "WHERE (a.ApartmentName LIKE ? OR ? IS NULL) " +
//                 "AND (a.OwnerID = ? OR ? IS NULL) " +
//                 "AND (a.Type = ? OR ? IS NULL) " +
//                 "AND (a.Status = ? OR ? IS NULL) " +
//                 "AND (a.Block = ? OR ? IS NULL)";
//
//    try (Connection conn = DBContext.getConnection();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//        
//        ps.setString(1, name != null ? "%" + name + "%" : null);
//        ps.setString(2, name);
//        ps.setInt(3, ownerId);
//        ps.setInt(4, ownerId);
//        ps.setString(5, type);
//        ps.setString(6, type);
//        ps.setString(7, status);
//        ps.setString(8, status);
//        ps.setString(9, block);
//        ps.setString(10, block);
//
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            total = rs.getInt(1);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return total;
//}
public List<Apartment> searchApartments(String name, int ownerId, String type, String status, String block, int page, int pageSize) {
    List<Apartment> apartments = new ArrayList<>();
    String sql = "SELECT ApartmentID, ApartmentName, Block, Status, Type, OwnerID FROM Apartment WHERE 1=1";

    if (name != null && !name.isEmpty()) {
        sql += " AND ApartmentName LIKE ?";
    }
    if (ownerId != -1) {
        sql += " AND OwnerID = ?";
    }
    if (type != null && !type.isEmpty()) {
        sql += " AND Type = ?";
    }
    if (status != null && !status.isEmpty()) {
        sql += " AND Status = ?";
    }
    if (block != null && !block.isEmpty()) {
        sql += " AND Block = ?";
    }

    
    sql += " ORDER BY ApartmentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {

        int paramIndex = 1;
        if (name != null && !name.isEmpty()) {
            ps.setString(paramIndex++, "%" + name + "%");
        }
        if (ownerId != -1) {
            ps.setInt(paramIndex++, ownerId);
        }
        if (type != null && !type.isEmpty()) {
            ps.setString(paramIndex++, type);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }
        if (block != null && !block.isEmpty()) {
            ps.setString(paramIndex++, block);
        }

        // Phân trang
        int offset = (page - 1) * pageSize;
        ps.setInt(paramIndex++, offset);
        ps.setInt(paramIndex++, pageSize);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Apartment apt = new Apartment(
                    rs.getInt("ApartmentID"),
                    rs.getString("ApartmentName"),
                    rs.getString("Block"),
                    rs.getString("Status"),
                    rs.getString("Type"),
                    rs.getInt("OwnerID")
                );
                apartments.add(apt);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return apartments;
}
public int getTotalApartments(String name, int ownerId, String type, String status, String block) {
    int total = 0;
    String sql = "SELECT COUNT(*) FROM Apartment WHERE 1=1";

    if (name != null && !name.isEmpty()) {
        sql += " AND ApartmentName LIKE ?";
    }
    if (ownerId != -1) {
        sql += " AND OwnerID = ?";
    }
    if (type != null && !type.isEmpty()) {
        sql += " AND Type = ?";
    }
    if (status != null && !status.isEmpty()) {
        sql += " AND Status = ?";
    }
    if (block != null && !block.isEmpty()) {
        sql += " AND Block = ?";
    }

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        int paramIndex = 1;
        if (name != null && !name.isEmpty()) {
            ps.setString(paramIndex++, "%" + name + "%");
        }
        if (ownerId != -1) {
            ps.setInt(paramIndex++, ownerId);
        }
        if (type != null && !type.isEmpty()) {
            ps.setString(paramIndex++, type);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }
        if (block != null && !block.isEmpty()) {
            ps.setString(paramIndex++, block);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return total;
}
public int updateOwner(int apartmentId, int residentId) {
    String sql = "UPDATE Apartment SET OwnerID = ?, Status = 'Occupied' WHERE ApartmentID = ?";
    try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, residentId);
        ps.setInt(2, apartmentId);
        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }
}
public List<Apartment> getNotNullOwnerApartments() {
    List<Apartment> apartments = new ArrayList<>();
    String sql = "SELECT * FROM Apartment WHERE OwnerID IS NULL";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            apartments.add(new Apartment(
                rs.getInt("ApartmentID"),
                rs.getString("ApartmentName"),
                rs.getString("Block"),
                rs.getString("Status"),
                rs.getString("Type"),
                rs.getObject("OwnerID") != null ? rs.getInt("OwnerID") : -1
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return apartments;
}
public List<Apartment> getApartmentsByOwner(int ownerId) {
    List<Apartment> apartments = new ArrayList<>();
    String sql = "SELECT * FROM Apartment WHERE OwnerID = ?";
    
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ownerId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Apartment apartment = new Apartment();
            apartment.setApartmentId(rs.getInt("ApartmentID"));
            apartment.setApartmentName(rs.getString("ApartmentName"));
            apartment.setBlock(rs.getString("Block"));
            apartment.setStatus(rs.getString("Status"));
            apartment.setType(rs.getString("Type"));
            apartment.setOwnerId(rs.getInt("ownerId"));
            apartments.add(apartment);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return apartments;
}

    public List<String> getApartmentNames(String searchQuery) {
        List<String> apartments = new ArrayList<>();
        String sql = "SELECT TOP 5 ApartmentName FROM Apartment WHERE ApartmentName LIKE ?"; // Giới hạn kết quả

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchQuery + "%");
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
