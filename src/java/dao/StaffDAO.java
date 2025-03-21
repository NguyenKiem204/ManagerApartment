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
import model.Image;

/**
 *
 * @author nkiem
 */
public class StaffDAO implements DAOInterface<Staff, Integer> {

    private final PasswordUtil passwordEncode = new PasswordUtil();
    ImageDAO imageDAO = new ImageDAO();
    RoleDAO roleDAO = new RoleDAO();

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
            ps.setInt(8, imageDAO.insert1(new Image(null)));
            ps.setString(9, staff.getStatus());
            ps.setInt(10, staff.getRole().getRoleID());

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
            ps.setInt(8, staff.getImage().getImageID());
            ps.setString(9, staff.getStatus());
            ps.setInt(10, staff.getRole().getRoleID());
            ps.setInt(11, staff.getStaffId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public int updateProfileStaff(Staff staff) {
        int row = 0;
        String updateStaffSQL = "UPDATE Staff SET FullName = ?,PhoneNumber = ?, DOB = ?, Sex = ?, ImageID = ? WHERE StaffID = ?";
        String updateImageSQL = "UPDATE Image SET ImageURL = ? WHERE ImageID = ?";

        try (Connection connection = DBContext.getConnection()) {
            connection.setAutoCommit(false);

            if (staff.getImage().getImageID() > 0 && staff.getImage().getImageURL() != null) {
                try (PreparedStatement psUpdateImage = connection.prepareStatement(updateImageSQL)) {
                    psUpdateImage.setString(1, staff.getImage().getImageURL());
                    psUpdateImage.setInt(2, staff.getImage().getImageID());
                    psUpdateImage.executeUpdate();
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(updateStaffSQL)) {
                ps.setString(1, staff.getFullName());
                ps.setString(2, staff.getPhoneNumber());
                ps.setDate(3, Date.valueOf(staff.getDob()));
                ps.setString(4, staff.getSex());
                ps.setInt(5, staff.getImage().getImageID());
                ps.setInt(6, staff.getStaffId());

                row = ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public String getImageURL(int staffId) {
        String imageURL = null;
        String query = "SELECT i.ImageURL FROM Image i "
                  + "JOIN Staff s ON i.ImageID = s.ImageID "
                  + "WHERE s.StaffID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    imageURL = rs.getString("ImageURL");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageURL;
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
public int delete1(Staff staff) {
    int row = 0;
    Connection connection = null;
    PreparedStatement ps = null;

    try {
        connection = DBContext.getConnection();
        connection.setAutoCommit(false); // Bắt đầu transaction

        int staffId = staff.getStaffId();

        // 1. Lấy ImageID của Staff
        String getImageQuery = "SELECT ImageID FROM Staff WHERE StaffID = ?";
        int imageId = -1;

        try (PreparedStatement getImageStmt = connection.prepareStatement(getImageQuery)) {
            getImageStmt.setInt(1, staffId);
            try (ResultSet rs = getImageStmt.executeQuery()) {
                if (rs.next()) {
                    imageId = rs.getInt("ImageID");
                }
            }
        }

        // 2. Xóa Staff
        String deleteStaffQuery = "DELETE FROM Staff WHERE StaffID = ?";
        try (PreparedStatement deleteStaffStmt = connection.prepareStatement(deleteStaffQuery)) {
            deleteStaffStmt.setInt(1, staffId);
            row = deleteStaffStmt.executeUpdate();
        }

        // 3. Nếu có ImageID, xóa ảnh liên quan
        if (imageId > 0) {
            String deleteImageQuery = "DELETE FROM Image WHERE ImageID = ?";
            try (PreparedStatement deleteImageStmt = connection.prepareStatement(deleteImageQuery)) {
                deleteImageStmt.setInt(1, imageId);
                deleteImageStmt.executeUpdate();
            }
        }

        connection.commit(); // Xác nhận transaction
    } catch (SQLException ex) {
        if (connection != null) {
            try {
                connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                Staff staff = new Staff(rs.getInt("StaffID"),
                          rs.getString("FullName"),
                          rs.getString("Password"),
                          rs.getString("PhoneNumber"),
                          rs.getString("CCCD"),
                          rs.getString("Email"),
                          rs.getDate("DOB").toLocalDate(),
                          rs.getString("Sex"),
                          rs.getString("Status"),
                          imageDAO.selectById(rs.getInt("ImageID")),
                          roleDAO.selectById(rs.getInt("RoleID")));

                list.add(staff);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Staff> selectAllSortedByLastMessage(String myEmail) {
    List<Staff> list = new ArrayList<>();
    String sql = """
        SELECT s.*, MAX(m.Timestamp) AS LastMessageTime
        FROM Staff s
        LEFT JOIN Message m 
            ON (s.Email = m.SenderEmail OR s.Email = m.ReceiverEmail)
            AND (m.SenderEmail = ? OR m.ReceiverEmail = ?)
        GROUP BY s.StaffID, s.FullName, s.Email, s.PhoneNumber, s.CCCD, 
                 s.Password, s.DOB, s.Sex, s.Status, s.ImageID, s.RoleID
        ORDER BY LastMessageTime DESC
    """;

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, myEmail);
        ps.setString(2, myEmail);

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
                    imageDAO.selectById(rs.getInt("ImageID")),
                    roleDAO.selectById(rs.getInt("RoleID"))
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
                          imageDAO.selectById(rs.getInt("ImageID")),
                          roleDAO.selectById(rs.getInt("RoleID"))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }

    public Staff selectByEmail(String email) {
        Staff staff = null;
        String sql = "SELECT * FROM Staff WHERE Email = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
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
                          imageDAO.selectById(rs.getInt("ImageID")),
                          roleDAO.selectById(rs.getInt("RoleID"))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }

    public Staff getStaffByID(Integer id) {
        Staff staff = null;
        String sql = """
        SELECT s.StaffID, s.FullName, s.PhoneNumber, s.CCCD, s.Email, s.DOB, 
               s.Sex, s.Status, i.ImageURL, i.ImageID, r.RoleName, r.RoleID
        FROM Staff s
        LEFT JOIN Role r ON s.RoleID = r.RoleID
        LEFT JOIN Image i ON s.ImageID = i.ImageID
        WHERE s.StaffID = ?
        """;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setStaffId(rs.getInt("StaffID"));
                staff.setFullName(rs.getString("FullName"));
                staff.setPhoneNumber(rs.getString("PhoneNumber"));
                staff.setCccd(rs.getString("CCCD"));
                staff.setEmail(rs.getString("Email"));
                staff.setDob(rs.getDate("DOB").toLocalDate());
                staff.setSex(rs.getString("Sex"));
                staff.setStatus(rs.getString("Status"));
                staff.setImage(imageDAO.selectById(rs.getInt("ImageID")));
                staff.setRole(roleDAO.selectById(rs.getInt("RoleID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return staff;
    }

    public boolean existEmail(String email) {
        String sql = "SELECT * FROM Staff WHERE Email = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public boolean existPhoneNumber(String phone) {
        String sql = "SELECT * FROM Staff WHERE PhoneNumber = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
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
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
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
                        staff = new Staff(staffId, fullName, password, phoneNumber, cccd, storedEmail, dob, sex, status, imageDAO.selectById(imageId), roleDAO.selectById(roleId));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean updatePasswordInDatabase(String email, String hashedPassword) {
        String sql = "UPDATE Staff SET Password = ? WHERE Email = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hashedPassword);
            statement.setString(2, email);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    Quang Dung
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
                          imageDAO.selectById(rs.getInt("ImageID")),
                          roleDAO.selectById(rs.getInt("RoleID"))
                );
                staffs.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }

    public List<Staff> searchStaffs(String keyword, String sex, String status, int page, int pageSize) {
        List<Staff> staffs = new ArrayList<>();
        String query = "SELECT * FROM Staff WHERE (FullName LIKE ? OR Email LIKE ?)";

        if (sex != null && !sex.isEmpty()) {
            query += " AND Sex = ?";
        }
        if (status != null && !status.isEmpty()) {
            query += " AND Status = ?";
        }
        // Thêm ORDER BY và phân trang
        query += " ORDER BY StaffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
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
            // Thiết lập OFFSET và FETCH NEXT cho phân trang
            int offset = (page - 1) * pageSize;
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);
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
                              imageDAO.selectById(rs.getInt("ImageID")),
                              roleDAO.selectById(rs.getInt("RoleID"))
                    );
                    staffs.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffs;
    }
    public int getTotalStaffs(String keyword, String sex, String status) {
    int total = 0;
    String query = "SELECT COUNT(*) FROM Staff WHERE (FullName LIKE ? OR Email LIKE ?)";

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
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return total;
}
    public boolean updatePassword(int staffId, String newPassword) throws SQLException {
    String query = "UPDATE Staff SET Password = ? WHERE StaffID = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, newPassword);
        ps.setInt(2, staffId);
        return ps.executeUpdate() > 0;
    }
}
    public boolean isStaffExists(String phoneNumber, String cccd, String email) {
        String sql = "SELECT COUNT(*) FROM Staff WHERE PhoneNumber = ? OR CCCD = ? OR Email = ?";
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

    public List<Staff> getStaffByPosition(int position) {
        List<Staff> staffs = new ArrayList<>();
        String query = "SELECT TOP 1 * FROM Staff WHERE RoleID = ? AND Status = 'Active' ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, position);
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
                              imageDAO.selectById(rs.getInt("ImageID")),
                              roleDAO.selectById(rs.getInt("RoleID"))
                    );
                    staffs.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffs;
    }

    public Staff getStaffByRoleIDAndStatus(Integer roleID, String status) {
        Staff staff = null;
        String sql = """
                     SELECT [StaffID]
                           ,[FullName]
                           ,[Password]
                           ,[PhoneNumber]
                           ,[CCCD]
                           ,[Email]
                           ,[DOB]
                           ,[Sex]
                           ,[Status]
                           ,[RoleID]
                           ,[ImageID]
                       FROM [ApartmentManagement].[dbo].[Staff]
                       WHERE Status = ? And RoleID = ?""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roleID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setStaffId(rs.getInt("StaffID"));
                staff.setFullName(rs.getString("FullName"));
                staff.setPhoneNumber(rs.getString("PhoneNumber"));
                staff.setCccd(rs.getString("CCCD"));
                staff.setEmail(rs.getString("Email"));
                staff.setDob(rs.getDate("DOB").toLocalDate());
                staff.setSex(rs.getString("Sex"));
                staff.setStatus(rs.getString("Status"));
                staff.setImage(imageDAO.selectById(rs.getInt("ImageID")));
                staff.setRole(roleDAO.selectById(rs.getInt("RoleID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return staff;
    }

    public boolean isExistStaffByRoleIDAndStatusID(int position, String status) {
        return getStaffByRoleIDAndStatus(position, status) != null;
    }
    public boolean isRoleHasActiveStaff(int roleId) {
        String query = "SELECT COUNT(*) FROM Staff WHERE RoleID = ? AND Status = 'Active'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, roleId);
            System.out.println("Checking active staff for roleId: " + roleId); // Debug

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Active staff count: " + count); // Debug
                    return count >= 1; // Nếu có ít nhất 1 nhân viên Active, trả về true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
