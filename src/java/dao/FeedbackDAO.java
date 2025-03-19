/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import model.Staff;

/**
 *
 * @author admin
 */
public class FeedbackDAO implements DAOInterface<Feedback, Integer> {

    StaffDAO st = new StaffDAO();

    @Override
    public int insert(Feedback fb) {
        int row = 0;
        String sqlInsert = "INSERT INTO [dbo].[Feedback]\n"
                  + "           ([Title]\n"
                  + "           ,[Description]\n"
                  + "           ,[Date]\n"
                  + "           ,[Rate]\n"
                  + "           ,[StaffID]\n"
                  + "           ,[ResidentID])\n"
                  + "     VALUES\n"
                  + "           (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, fb.getTitle());
            ps.setString(2, fb.getDescription());
            ps.setDate(3, Date.valueOf(fb.getDate()));
            ps.setInt(4, fb.getRate());
            ps.setInt(5, fb.getStaff().getStaffId());
            ps.setInt(6, fb.getResident().getResidentId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Feedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Feedback t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    StaffDAO staff = new StaffDAO();
    ResidentDAO resident = new ResidentDAO();

    @Override
    public List<Feedback> selectAll() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int selectAllToCount() {
        String sql = "SELECT count(*) FROM Feedback";
        int num = 0;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    @Override
    public Feedback selectById(Integer id) {
        String sql = "SELECT * FROM [Feedback] WHERE FeedbackID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Feedback f = new Feedback(
                              rs.getInt("FeedbackID"),
                              rs.getString("Title"),
                              rs.getString("Description"),
                              rs.getDate("Date").toLocalDate(),
                              rs.getInt("Rate"),
                              staff.selectById(rs.getInt("StaffID")),
                              resident.selectById(rs.getInt("ResidentID"))
                    );
                    return f;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Feedback> selectAllByRoleIDAndStatus(int roleID) {
        List<Feedback> list = new ArrayList<>();
        String sql = """
                     SELECT [FeedbackID], [Title], [Description], [Date], [Rate], f.StaffID, [ResidentID], s.RoleID
                                         FROM Feedback f
                                JOIN Staff s ON f.StaffID = s.StaffID
                                         WHERE s.RoleID = ? 
                                         AND s.Status = 'Active';""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Feedback> selectFirstPage() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY [FeedbackID] DESC OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Feedback> selectFirstPageOfFormFeedbackStatistic(int roleID) {
        List<Feedback> list = new ArrayList<>();
        String sql = """
                     SELECT f.*
                     FROM Feedback f
                     JOIN Staff s ON f.StaffID = s.StaffID
                     JOIN [Role] r ON s.RoleID = r.RoleID
                     WHERE s.[Status] = 'Active' 
                       AND r.RoleID = ?
                     ORDER BY f.FeedbackID
                     OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY;""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Feedback> getAllFeedbacksBySearchOrFilterOrSort(String keySearch,
              int roleID, int rating, int keySort, int page,
              int pageSize) {
        List<Feedback> list = new ArrayList<>();
        String sql = """
                     SELECT [FeedbackID]
                           ,[Title]
                           ,f.Description
                           ,[Date]
                           ,[Rate]
                           ,f.StaffID
                           ,[ResidentID]
                           ,s.FullName
                     ,s.RoleID
                           ,r.RoleName
                       FROM [dbo].[Feedback] f 
                     JOIN Staff s on f.StaffID = s.StaffID
                                join Role r on s.RoleID = r.RoleID
                       WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (r.RoleName LIKE ? OR Title LIKE ? OR s.FullName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (roleID != 0) {
                sql += " AND r.RoleID = ?";
                params.add(roleID);
            }

            //check rating is null or not
            if (rating != 0) {
                sql += " AND Rate = ?";
                params.add(rating);
            }

            //check date is null or not
//            if (date != null) {
//                sql += " AND CAST([Date] AS DATE) = ?";
//                params.add(Date.valueOf(date));
//            }
//------------------------------------------
            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 2 ->
                        sql += " ORDER BY Rate";
                    case 1 ->
                        sql += " ORDER BY StaffID";
                    case 3 ->
                        sql += " ORDER BY Date";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY FeedbackID desc";
            }

//xu ly phan trang
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            int offset = (page - 1) * pageSize;
            params.add(offset);
            params.add(pageSize);

        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getNumberOfFeedbacksBySearchOrFilterOrSort(String keySearch,
              int roleID, int rating) {
        int num = 0;
        String sql = """
                     SELECT COUNT(*)
                       FROM [dbo].[Feedback] f 
                       JOIN [dbo].[Staff] s on f.StaffID = s.StaffID
                       JOIN [dbo].[Role] r on s.RoleID = r.RoleID
                       WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (r.RoleName LIKE ? OR Title LIKE ? OR s.FullName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (roleID != 0) {
                sql += " AND r.RoleID = ?";
                params.add(roleID);
            }

            //check rating is null or not
            if (rating != 0) {
                sql += " AND Rate = ?";
                params.add(rating);
            }


        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                num =rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public int getLatestFeedbackID() {
        int latestFeedbackID = 1; // Giá trị mặc định nếu không có feedback nào
        String sql = "SELECT MAX(FeedbackID) FROM Feedback";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                latestFeedbackID = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return latestFeedbackID;
    }

    public List<Feedback> getFeedbackByMonthYearAndRoleID(LocalDate monthYear, int roleID) {
        List<Feedback> list = new ArrayList<>();
        String sql = """
                     SELECT [FeedbackID], [Title], [Description], [Date], [Rate], f.StaffID, [ResidentID], s.RoleID
                                         FROM Feedback f
                                         JOIN Staff s ON f.StaffID = s.StaffID
                                         WHERE s.RoleID = ? 
                                         AND FORMAT([Date], 'yyyy-MM') = ? AND s.Status = 'Active'
                                         ORDER BY [Date] DESC;""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            String formattedMonthYear = monthYear.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            ps.setObject(2, formattedMonthYear);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                          rs.getInt("FeedbackID"),
                          rs.getString("Title"),
                          rs.getString("Description"),
                          rs.getDate("Date").toLocalDate(),
                          rs.getInt("Rate"),
                          staff.selectById(rs.getInt("StaffID")),
                          resident.selectById(rs.getInt("ResidentID"))
                );
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getPositiveFeedback(int position, LocalDate monthYear) {
        List<Feedback> list = getFeedbackByMonthYearAndRoleID(monthYear, position);

        if (list.isEmpty()) {
            return 0; // Trả về 0 nếu không có feedback nào
        }

        // Đếm số feedback có rating từ 3 đến 5 (positive feedback)
        long positiveCount = list.stream().filter(fb -> fb.getRate() >= 3).count();

        // Tính phần trăm feedback positive
        double percentage = (positiveCount * 100.0) / list.size();

        return (int) Math.round(percentage);
    }

    public Map<String, Object> getFeedbackSummary(int position, LocalDate monthYear) {
        Map<String, Object> feedbackSummary = new HashMap<>();

        List<Feedback> feedbackList = getFeedbackByMonthYearAndRoleID(monthYear, position);

        if (feedbackList.isEmpty()) {
            // Nếu không có feedback, trả về giá trị mặc định "N/A"
            feedbackSummary.put("totalFeedback", "N/A");
            feedbackSummary.put("avgRating", "N/A");
            feedbackSummary.put("positiveFeedback", "N/A");
            feedbackSummary.put("negativeFeedback", "N/A");
            feedbackSummary.put("feedbackList", "N/A");
            return feedbackSummary;
        }

        // Tính tổng số feedback
        int totalFeedback = feedbackList.size();

        // Tính tổng rating và số lượng feedback
        double totalRating = 0;
        int positiveCount = 0;

        for (Feedback feedback : feedbackList) {
            totalRating += feedback.getRate();
            if (feedback.getRate() >= 3) {
                positiveCount++;
            }
        }

        // Tính trung bình rating (làm tròn 1 chữ số thập phân)
        double avgRating = Math.round((totalRating / totalFeedback) * 10.0) / 10.0;

        // Tính phần trăm feedback tích cực
        int positiveFeedback = (int) Math.round((positiveCount * 100.0) / totalFeedback);

        // Tính phần trăm feedback tiêu cực
        int negativeFeedback = 100 - positiveFeedback;

        // Lưu vào Map kết quả
        feedbackSummary.put("totalFeedback", totalFeedback);
        feedbackSummary.put("avgRating", avgRating);
        feedbackSummary.put("positiveFeedback", positiveFeedback);
        feedbackSummary.put("negativeFeedback", negativeFeedback);
        feedbackSummary.put("feedbackList", feedbackList);

        return feedbackSummary;
    }

    public int countFeedbackByRole(int roleID, LocalDate monthYear) {
        String sql = "SELECT COUNT(*) FROM Feedback f "
                  + "JOIN Staff s ON f.StaffID = s.StaffID "
                  + "WHERE s.RoleID = ? AND s.Status = 'Active' AND FORMAT(f.Date, 'yyyy-MM') = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            String formattedMonthYear = monthYear.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            ps.setObject(2, formattedMonthYear);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Feedback> selectFeedbackByPage(int roleID, int page, int pageSize, LocalDate monthYear) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.* FROM Feedback f "
                  + "JOIN Staff s ON f.StaffID = s.StaffID "
                  + "WHERE s.RoleID = ? AND s.Status = 'Active' AND FORMAT(f.Date, 'yyyy-MM') = ? "
                  + "ORDER BY f.FeedbackID "
                  + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            String formattedMonthYear = monthYear.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            ps.setObject(2, formattedMonthYear);
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackID(rs.getInt("FeedbackID"));
                feedback.setTitle(rs.getString("Title"));
                feedback.setDescription(rs.getString("Description"));
                feedback.setDate(rs.getDate("Date").toLocalDate());
                feedback.setRate(rs.getInt("Rate"));
                list.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static Map<Integer, Integer> getFeedbackCountsByMonth() {
        Map<Integer, Integer> feedbackCounts = new HashMap<>();
        String sql = "SELECT MONTH([Date]) AS Month, COUNT(*) AS Total FROM Feedback GROUP BY MONTH([Date])";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                feedbackCounts.put(rs.getInt("Month"), rs.getInt("Total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackCounts;
    }
}
