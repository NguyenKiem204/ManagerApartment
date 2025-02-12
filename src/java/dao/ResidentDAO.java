package dao;

import config.PasswordUtil;
import model.Resident;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Image;
import model.Role;

public class ResidentDAO implements DAOInterface<Resident, Integer> {

    private final PasswordUtil passwordEncode = new PasswordUtil();
    ImageDAO imageDAO = new ImageDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    public int insert(Resident resident) {
        int row = 0;
        String sqlInsert = "INSERT INTO Resident (FullName, Password, PhoneNumber, CCCD, Email, DOB, Sex, ImageID, Status, RoleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, resident.getFullName());
            ps.setString(2, passwordEncode.hashPassword(resident.getPassword()));
            ps.setString(3, resident.getPhoneNumber());
            ps.setString(4, resident.getCccd());
            ps.setString(5, resident.getEmail());
            ps.setDate(6, Date.valueOf(resident.getDob()));
            ps.setString(7, resident.getSex());
            ps.setInt(8, resident.getImage().getImageID());
            ps.setString(9, resident.getStatus());
            ps.setInt(10, resident.getRole().getRoleID());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Resident resident) {
        int row = 0;
        String sql = "UPDATE Resident SET FullName = ?, Password = ?, PhoneNumber = ?, CCCD = ?, Email = ?, DOB = ?, Sex = ?, ImageID = ?, Status = ?, RoleID = ? WHERE ResidentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, resident.getFullName());
            ps.setString(2, resident.getPassword());
            ps.setString(3, resident.getPhoneNumber());
            ps.setString(4, resident.getCccd());
            ps.setString(5, resident.getEmail());
            ps.setDate(6, Date.valueOf(resident.getDob()));
            ps.setString(7, resident.getSex());
            ps.setInt(8, resident.getImage().getImageID());
            ps.setString(9, resident.getStatus());
            ps.setInt(10, resident.getRole().getRoleID());
            ps.setInt(11, resident.getResidentId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public int updateProfileResident(Resident resident) {
        int row = 0;
        String updateRessidentSQL = "UPDATE Resident SET FullName = ?,PhoneNumber = ?, Email = ?, DOB = ?, Sex = ?, ImageID = ? WHERE ResidentID = ?";
        String updateImageSQL = "UPDATE Image SET ImageURL = ? WHERE ImageID = ?";

        try (Connection connection = DBContext.getConnection()) {
            connection.setAutoCommit(false);

            if (resident.getImage().getImageID() > 0 && resident.getImage().getImageURL() != null) {
                try (PreparedStatement psUpdateImage = connection.prepareStatement(updateImageSQL)) {
                    psUpdateImage.setString(1, resident.getImage().getImageURL());
                    psUpdateImage.setInt(2, resident.getImage().getImageID());
                    psUpdateImage.executeUpdate();
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(updateRessidentSQL)) {
                ps.setString(1, resident.getFullName());
                ps.setString(2, resident.getPhoneNumber());
                ps.setString(3, resident.getEmail());
                ps.setDate(4, Date.valueOf(resident.getDob()));
                ps.setString(5, resident.getSex());
                ps.setInt(6, resident.getImage().getImageID());
                ps.setInt(7, resident.getResidentId());

                row = ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
    
    public String getImageURL(int residentId) {
        String imageURL = null;
        String query = "SELECT i.ImageURL FROM Image i "
                + "JOIN Resident s ON i.ImageID = s.ImageID "
                + "WHERE s.ResidentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, residentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    imageURL = rs.getString("ImageURL");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageURL;
    }

    @Override
    public int delete(Resident resident) {
        int row = 0;
        String sql = "DELETE FROM Resident WHERE ResidentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, resident.getResidentId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }


    @Override
    public List<Resident> selectAll() {
        List<Resident> list = new ArrayList<>();
        String sql = "SELECT * FROM Resident";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resident resident = new Resident(
                        rs.getInt("ResidentID"),
                        rs.getString("FullName"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber"),
                        rs.getString("CCCD"),
                        rs.getString("Email"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Sex"),
                        rs.getString("Status"),
                        imageDAO.selectById(rs.getInt("ImageID")),
                       roleDAO.selectById(rs.getInt("RoleID"))
                );
                list.add(resident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Resident selectById(Integer id) {
        Resident resident = null;
        String sql = "SELECT * FROM Resident WHERE ResidentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resident = new Resident(
                        rs.getInt("ResidentID"),
                        rs.getString("FullName"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber"),
                        rs.getString("CCCD"),
                        rs.getString("Email"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Sex"),
                        rs.getString("Status"),
                        imageDAO.selectById(rs.getInt("ImageID")),
                        roleDAO.selectById(rs.getInt("RoleID"))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resident;
    }

    public boolean existEmail(String email) {
        String sql = "SELECT * FROM Resident WHERE Email = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Resident checkLogin(String mail, String password) {
        Resident resident = null;
        String sql = "SELECT * FROM Resident WHERE Email = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int residentId = rs.getInt("ResidentID");
                    String storedPasswordHash = rs.getString("Password");
                    String status = rs.getString("Status");
                    if (passwordEncode.checkPassword(password, storedPasswordHash) && "ACTIVE".equalsIgnoreCase(status)) {
                        String fullName = rs.getString("FullName");
                        String phoneNumber = rs.getString("PhoneNumber");
                        String cccd = rs.getString("CCCD");
                        LocalDate dob = rs.getDate("DOB").toLocalDate();
                        String sex = rs.getString("Sex");
                        Image imageId = imageDAO.selectById(rs.getInt("ImageID"));
                        Role roleId = roleDAO.selectById(rs.getInt("RoleID"));
                        resident = new Resident(residentId, fullName, storedPasswordHash, phoneNumber, cccd, mail, dob, sex, status, imageId, roleId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resident;
    }

    public boolean updatePasswordInDatabase(String email, String hashedPassword) {
        String sql = "UPDATE Resident SET Password = ? WHERE Email = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hashedPassword);
            statement.setString(2, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public Resident getResidentByApartmentID(int ApartmentID){
//        Resident resident = null;
//        String sql = "SELECT * FROM Resident WHERE ApartmentID = ?";
//
//        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, ApartmentID);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                resident = new Resident(
//                        rs.getInt("ResidentID"),
//                        rs.getString("FullName"),
//                        rs.getString("Password"),
//                        rs.getString("PhoneNumber"),
//                        rs.getString("CCCD"),
//                        rs.getString("Email"),
//                        rs.getDate("DOB").toLocalDate(),
//                        rs.getString("Sex"),
//                        rs.getString("Status"),
//                        rs.getInt("ImageID"),
//                        rs.getInt("RoleID")
//                );
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return resident;
//    }
//    ====================Dung====================
    public List<Resident> getAllResidents(String sex, String status) {
        List<Resident> residents = new ArrayList<>();
        try {
            String query = "SELECT * FROM Resident WHERE 1=1";

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
                Resident resident = new Resident(
                        rs.getInt("ResidentID"),
                        rs.getString("FullName"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber"),
                        rs.getString("CCCD"),
                        rs.getString("Email"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Sex"),
                        rs.getString("Status"),
                        imageDAO.selectById(rs.getInt("ImageID")),
                        roleDAO.selectById(rs.getInt("RoleID"))
                );
                residents.add(resident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return residents;
    }

    public boolean updateStatus(int residentId, String newStatus) {
        String query = "UPDATE Resident SET Status = ? WHERE ResidentID = ?";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setInt(2, residentId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isResidentExists(String phoneNumber, String cccd, String email) {
        String sql = "SELECT COUNT(*) FROM Resident WHERE PhoneNumber = ? OR CCCD = ? OR Email = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public List<Resident> searchResidents(String keyword, String sex, String status) {
        List<Resident> residents = new ArrayList<>();
        String query = "SELECT * FROM Resident WHERE (FullName LIKE ? OR Email LIKE ?)";

        if (sex != null && !sex.isEmpty()) {
            query += " AND Sex = ?";
        }
        if (status != null && !status.isEmpty()) {
            query += " AND Status = ?";
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

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
                    Resident resident = new Resident(
                            rs.getInt("ResidentID"),
                            rs.getString("FullName"),
                            rs.getString("Password"),
                            rs.getString("PhoneNumber"),
                            rs.getString("CCCD"),
                            rs.getString("Email"),
                            rs.getDate("DOB").toLocalDate(),
                            rs.getString("Sex"),
                            rs.getString("Status"),
                            imageDAO.selectById(rs.getInt("ImageID")),
                            roleDAO.selectById(rs.getInt("RoleID"))
                    );
                    residents.add(resident);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residents;
    }
}
