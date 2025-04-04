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
            ps.setInt(8, imageDAO.insert1(new Image(null)));
            ps.setString(9, resident.getStatus());
            ps.setInt(10, resident.getRole().getRoleID());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
    public int insert1(Resident resident) {
    int residentId = -1;
    String sql = "INSERT INTO Resident (FullName, Password, PhoneNumber, CCCD, Email, DOB, Sex, ImageID, Status, RoleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, resident.getFullName());
        ps.setString(2, passwordEncode.hashPassword(resident.getPassword()));
        ps.setString(3, resident.getPhoneNumber());
        ps.setString(4, resident.getCccd());
        ps.setString(5, resident.getEmail());
        ps.setDate(6, Date.valueOf(resident.getDob()));
        ps.setString(7, resident.getSex());
        ps.setInt(8, imageDAO.insert1(new Image(null)));
        ps.setString(9, resident.getStatus());
        ps.setInt(10, resident.getRole().getRoleID());

        int row = ps.executeUpdate();
        if (row > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    residentId = generatedKeys.getInt(1);
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return residentId;
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
        String updateRessidentSQL = "UPDATE Resident SET FullName = ?,PhoneNumber = ?, DOB = ?, Sex = ?, ImageID = ? WHERE ResidentID = ?";
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
                ps.setDate(3, Date.valueOf(resident.getDob()));
                ps.setString(4, resident.getSex());
                ps.setInt(5, resident.getImage().getImageID());
                ps.setInt(6, resident.getResidentId());

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
    
    public int delete1(Resident resident) {
    int row = 0;
    Connection connection = null;
    PreparedStatement ps = null;

    try {
        connection = DBContext.getConnection();
        connection.setAutoCommit(false); // Bắt đầu transaction

        int residentId = resident.getResidentId();
        int roleId = resident.getRole().getRoleID();

        // 1. Lấy ImageID của Resident
        String getImageQuery = "SELECT ImageID FROM Resident WHERE ResidentID = ?";
        int imageId = -1;

        try (PreparedStatement getImageStmt = connection.prepareStatement(getImageQuery)) {
            getImageStmt.setInt(1, residentId);
            try (ResultSet rs = getImageStmt.executeQuery()) {
                if (rs.next()) {
                    imageId = rs.getInt("ImageID");
                }
            }
        }

        // 2. Nếu roleId = 7 (Owner), cập nhật OwnerID trong Apartment thành NULL
        if (roleId == 7) {
            String updateApartmentQuery = "UPDATE Apartment SET OwnerID = NULL WHERE OwnerID = ?";
            try (PreparedStatement updateApartmentStmt = connection.prepareStatement(updateApartmentQuery)) {
                updateApartmentStmt.setInt(1, residentId);
                updateApartmentStmt.executeUpdate();
            }
        }

        // 3. Nếu roleId = 6 (Tenant), xóa tất cả Contract liên quan trước khi xóa Tenant
        if (roleId == 6) {
            String deleteContractQuery = "DELETE FROM Contract WHERE ResidentID = ?";
            try (PreparedStatement deleteContractStmt = connection.prepareStatement(deleteContractQuery)) {
                deleteContractStmt.setInt(1, residentId);
                deleteContractStmt.executeUpdate();
            }
        }

        // 4. Xóa Resident
        String deleteResidentQuery = "DELETE FROM Resident WHERE ResidentID = ?";
        try (PreparedStatement deleteResidentStmt = connection.prepareStatement(deleteResidentQuery)) {
            deleteResidentStmt.setInt(1, residentId);
            row = deleteResidentStmt.executeUpdate();
        }

        // 5. Nếu có ImageID, xóa ảnh liên quan
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
        Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public int numberResident() {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM Resident";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            count = rs.getInt(1);
        }

    } catch (SQLException ex) {
        Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return count;
}

    public List<Resident> selectAllSortedByLastMessage(String myEmail) {
    List<Resident> list = new ArrayList<>();
    String sql = """
        SELECT r.*, MAX(m.Timestamp) AS LastMessageTime
        FROM Resident r
        LEFT JOIN Message m 
            ON (r.Email = m.SenderEmail OR r.Email = m.ReceiverEmail)
            AND (m.SenderEmail = ? OR m.ReceiverEmail = ?)
        GROUP BY r.ResidentID, r.FullName, r.Email, r.PhoneNumber, r.CCCD, 
                 r.Password, r.DOB, r.Sex, r.Status, r.ImageID, r.RoleID
        ORDER BY LastMessageTime DESC
    """;

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, myEmail);
        ps.setString(2, myEmail);

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

    public Resident selectByEmail(String email) {
        Resident resident = null;
        String sql = "SELECT * FROM Resident WHERE Email = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
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
    public boolean existPhoneNumber(String phone) {
        String sql = "SELECT * FROM Resident WHERE PhoneNumber = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
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
    public List<Resident> getTenantsByOwner(int ownerId, String keyword, String sex, String status, int page, int pageSize) {
    List<Resident> tenants = new ArrayList<>();
    String sql = "SELECT r.ResidentID, r.FullName, r.PhoneNumber, r.CCCD, r.Email, r.DOB, r.Sex, r.Status, a.ApartmentName, c.LeaseStartDate, c.LeaseEndDate " +
                 "FROM Resident r " +
                 "JOIN Contract c ON r.ResidentID = c.ResidentID " +
                 "JOIN Apartment a ON c.ApartmentID = a.ApartmentID " +
                 "WHERE r.RoleID = 6 AND a.OwnerID = ?";
    if (keyword != null && !keyword.isEmpty()) {
        sql += " AND (r.FullName LIKE ? OR r.Email LIKE ?)";
    }
    if (sex != null && !sex.isEmpty()) {
        sql += " AND r.Sex = ?";
    }
    if (status != null && !status.isEmpty()) {
        sql += " AND r.Status = ?";
    }

    // Phân trang
    sql += " ORDER BY r.ResidentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        int paramIndex = 1;
        ps.setInt(paramIndex++, ownerId);

        if (keyword != null && !keyword.isEmpty()) {
            ps.setString(paramIndex++, "%" + keyword + "%");
            ps.setString(paramIndex++, "%" + keyword + "%");
        }
        if (sex != null && !sex.isEmpty()) {
            ps.setString(paramIndex++, sex);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        // Phân trang
        int offset = (page - 1) * pageSize;
        ps.setInt(paramIndex++, offset);
        ps.setInt(paramIndex++, pageSize);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Resident tenant = new Resident();
            tenant.setResidentId(rs.getInt("ResidentID"));
            tenant.setFullName(rs.getString("FullName"));
            tenant.setPhoneNumber(rs.getString("PhoneNumber"));
            tenant.setCccd(rs.getString("CCCD"));
            tenant.setEmail(rs.getString("Email"));
            tenant.setDob(rs.getDate("DOB").toLocalDate());
            tenant.setSex(rs.getString("Sex"));
            tenant.setStatus(rs.getString("Status"));
            tenants.add(tenant);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tenants;
}
public int countTenantsByOwner(int ownerId, String keyword, String sex, String status) {
    int count = 0;
    String query = "SELECT COUNT(*) FROM Resident r " +
                   "JOIN Contract c ON r.ResidentID = c.ResidentID " +
                   "JOIN Apartment a ON c.ApartmentID = a.ApartmentID " +
                   "WHERE a.OwnerID = ? AND r.RoleID = 6";

    if (keyword != null && !keyword.isEmpty()) {
        query += " AND (r.FullName LIKE ? OR r.Email LIKE ?)";
    }
    if (sex != null && !sex.isEmpty()) {
        query += " AND r.Sex = ?";
    }
    if (status != null && !status.isEmpty()) {
        query += " AND r.Status = ?";
    }

    try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
        int paramIndex = 1;
        ps.setInt(paramIndex++, ownerId);

        if (keyword != null && !keyword.isEmpty()) {
            ps.setString(paramIndex++, "%" + keyword + "%");
            ps.setString(paramIndex++, "%" + keyword + "%");
        }
        if (sex != null && !sex.isEmpty()) {
            ps.setString(paramIndex++, sex);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return count;
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

    public List<Resident> searchResidents(String keyword, String sex, String status, int page, int pageSize) {
    List<Resident> residents = new ArrayList<>();
    String query = "SELECT * FROM Resident WHERE (FullName LIKE ? OR Email LIKE ?)";

    if (sex != null && !sex.isEmpty()) {
        query += " AND Sex = ?";
    }
    if (status != null && !status.isEmpty()) {
        query += " AND Status = ?";
    }

    // Thêm ORDER BY và phân trang
    query += " ORDER BY ResidentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

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
    public int getTotalResidents(String keyword, String sex, String status) {
    int total = 0;
    String query = "SELECT COUNT(*) FROM Resident WHERE (FullName LIKE ? OR Email LIKE ?)";

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
public boolean isOwnerResident(int ownerId) {
    boolean isValid = false;
    String query = "SELECT COUNT(*) FROM Resident WHERE ResidentID = ? AND RoleID = 7 AND Status = 'Active'";
    
    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, ownerId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                isValid = rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return isValid;
}
public boolean updatePassword(int residentId, String newPassword) throws SQLException {
    String query = "UPDATE Resident SET Password = ? WHERE ResidentID = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        //ps.setString(1, passwordEncode.hashPassword(newPassword));
        ps.setString(1, newPassword);
        ps.setInt(2, residentId);
        return ps.executeUpdate() > 0;
    }
}

    public Resident getApartmentOwnerByDepartment(int departmentID) {
        String sql = "SELECT "
            + "    r.ResidentID, "
            + "    r.FullName, "
            + "    r.PhoneNumber, "
            + "    r.Email "
            + "FROM Apartment a "
            + "JOIN Resident r ON a.OwnerID = r.ResidentID "
            + "WHERE a.ApartmentID = ? AND r.Status = 'Active'";

        Resident owner = null;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, departmentID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    owner = new Resident();
                    owner.setResidentId(rs.getInt("ResidentID"));
                    owner.setFullName(rs.getString("FullName"));
                    owner.setPhoneNumber(rs.getString("PhoneNumber"));
                    owner.setEmail(rs.getString("Email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return owner;
    }
    public List<Resident> getTenantsByApartment(int apartmentId) {
    String sql = "SELECT " +
                 "    r.ResidentID, " +
                 "    r.FullName, " +
                 "    r.PhoneNumber, " +
                 "    r.Email, " +
                 "    r.Status " +
                 "FROM Contract c " +
                 "JOIN Resident r ON c.ResidentID = r.ResidentID " +
                 "WHERE c.ApartmentID = ? AND r.RoleID = 6";

    List<Resident> tenants = new ArrayList<>();

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setInt(1, apartmentId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Resident tenant = new Resident();
                tenant.setResidentId(rs.getInt("ResidentID"));
                tenant.setFullName(rs.getString("FullName"));
                tenant.setPhoneNumber(rs.getString("PhoneNumber"));
                tenant.setEmail(rs.getString("Email"));
                tenant.setStatus(rs.getString("Status"));
                tenants.add(tenant);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tenants;
}
    public String getResidentStatus(int residentId) {
    String status = null;
    String sql = "SELECT Status FROM Resident WHERE ResidentID = ?";
    
    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, residentId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                status = rs.getString("Status");
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return status;
}

}
