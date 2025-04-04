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

    public int insert1(Apartment apartment) {
        int row = 0;
        String sqlInsert = "INSERT INTO Apartment (ApartmentName, Block, Status, Type, OwnerID) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {

            ps.setString(1, apartment.getApartmentName());
            ps.setString(2, apartment.getBlock());
            ps.setString(3, apartment.getStatus());
            ps.setString(4, apartment.getType());

            // Nếu ownerId = 0, đặt giá trị NULL
            if (apartment.getOwnerId() == 0) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, apartment.getOwnerId());
            }

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            ex.printStackTrace(); // In lỗi SQL ra console để dễ debug
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

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        String sql = """
        SELECT a.ApartmentID, a.ApartmentName, a.Block, a.Status, a.Type, 
               r.ResidentID, r.FullName, r.PhoneNumber, r.Email
        FROM Apartment a
        LEFT JOIN Resident r ON a.OwnerID = r.ResidentID
    """;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Resident owner = (rs.getInt("ResidentID") != 0) ? new Resident(
                        rs.getInt("ResidentID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email")
                ) : null;

                Apartment apartment = new Apartment(
                        rs.getInt("ApartmentID"),
                        rs.getString("ApartmentName"),
                        rs.getString("Block"),
                        rs.getString("Status"),
                        rs.getString("Type"),
                        owner
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

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
                return rs.getInt(1) > 0;
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

    public List<Apartment> searchApartments(String name, int ownerId, String type, String status, String block, int page, int pageSize) {
        List<Apartment> apartments = new ArrayList<>();
        String sql = """
        SELECT a.ApartmentID, a.ApartmentName, a.Block, a.Status, a.Type, a.OwnerID, 
               r.ResidentID, r.FullName, r.PhoneNumber, r.CCCD, r.Email, r.DOB, r.Sex, r.Status as ResidentStatus
        FROM Apartment a
        LEFT JOIN Resident r ON a.OwnerID = r.ResidentID
        WHERE 1=1
    """;

        if (name != null && !name.isEmpty()) {
            sql += " AND a.ApartmentName LIKE ?";
        }
        if (ownerId != 0) {
            sql += " AND a.OwnerID = ?";
        }
        if (type != null && !type.isEmpty()) {
            sql += " AND a.Type = ?";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND a.Status = ?";
        }
        if (block != null && !block.isEmpty()) {
            sql += " AND a.Block = ?";
        }

        sql += " ORDER BY a.ApartmentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (ownerId != 0) {
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
                    // Tạo đối tượng Resident từ ResultSet
                    Resident owner = new Resident();
                    owner.setResidentId(rs.getInt("ResidentID"));
                    owner.setFullName(rs.getString("FullName"));
                    owner.setPhoneNumber(rs.getString("PhoneNumber"));
                    owner.setCccd(rs.getString("CCCD"));
                    owner.setEmail(rs.getString("Email"));
                    //owner.setDob(rs.getDate("DOB").toLocalDate());
                    owner.setSex(rs.getString("Sex"));
                    owner.setStatus(rs.getString("Status"));

                    // Tạo đối tượng Apartment và gán Owner
                    Apartment apt = new Apartment(
                            rs.getInt("ApartmentID"),
                            rs.getString("ApartmentName"),
                            rs.getString("Block"),
                            rs.getString("Status"),
                            rs.getString("Type"),
                            owner
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
        if (ownerId != 0) {
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

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (ownerId != 0) {
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

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "SELECT TOP 5 ApartmentName FROM Apartment WHERE ApartmentName LIKE ?";

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

    public boolean isDuplicateNameInBlock(String apartmentName, String block, int apartmentId) {
        String sql = "SELECT COUNT(*) FROM Apartment WHERE ApartmentName = ? AND Block = ? AND ApartmentID != ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, apartmentName);
            ps.setString(2, block);
            ps.setInt(3, apartmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isResidentAnOwner(int residentId) {
        String sql = "SELECT COUNT(*) FROM Apartment WHERE OwnerID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, residentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất 1 kết quả, tức là resident này là Owner
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public int selectLastId() {
        String sql = "SELECT MAX(ApartmentID) FROM Apartment;";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
//    public boolean resetOwner(int apartmentId) {
//        String sqlUpdate = "UPDATE Apartment SET OwnerID = NULL, Status = 'Available' WHERE ApartmentID = ?";
//
//        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
//
//            ps.setInt(1, apartmentId);
//            int rowsAffected = ps.executeUpdate();
//
//            return rowsAffected > 0;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
public boolean resetOwner(int apartmentId) {
        String checkContractSQL = "SELECT COUNT(*) FROM Contract WHERE ApartmentID = ? AND LeaseEndDate >= CAST(GETDATE() AS DATE);";
        String updateSQL = "UPDATE Apartment SET OwnerID = NULL, Status = 'Available' WHERE ApartmentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement checkStmt = connection.prepareStatement(checkContractSQL)) {

            checkStmt.setInt(1, apartmentId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Nếu có contract chưa hết hạn, không cho phép reset
                return false;
            }

            // Nếu không có hợp đồng nào còn hiệu lực, tiếp tục reset Owner
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                updateStmt.setInt(1, apartmentId);
                int rowsAffected = updateStmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
