package dao;

import config.PasswordUtil;
import model.Staff;
import model.Resident;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResidentDAO implements DAOInterface<Resident, Integer> {

    private final PasswordUtil passwordEncode = new PasswordUtil();

    @Override
    public int insert(Resident resident) {
        int row = 0;
        String sqlInsert = "INSERT INTO Resident (FullName, Password, PhoneNumber, CCCD, Email, DOB, Sex, ImageID, Status, RoleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, resident.getFullName());
            ps.setString(2, passwordEncode.hashPassword(resident.getPassword()));
            ps.setString(3, resident.getPhoneNumber());
            ps.setString(4, resident.getCccd());
            ps.setString(5, resident.getMail());
            ps.setDate(6, Date.valueOf(resident.getDob()));
            ps.setString(7, resident.getSex());
            ps.setInt(8, resident.getImageId());
            ps.setString(9, resident.getStatus());
            ps.setInt(10, resident.getRoleId());

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
            ps.setString(5, resident.getMail());
            ps.setDate(6, Date.valueOf(resident.getDob()));
            ps.setString(7, resident.getSex());
            ps.setInt(8, resident.getImageId());
            ps.setString(9, resident.getStatus());
            ps.setInt(10, resident.getRoleId());
            ps.setInt(11, resident.getResidentId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
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
                        rs.getInt("ImageID"),
                        rs.getInt("RoleID")
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
                        rs.getInt("ImageID"),
                        rs.getInt("RoleID")
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
                        int imageId = rs.getInt("ImageID");
                        int roleId = rs.getInt("RoleID");
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
}
