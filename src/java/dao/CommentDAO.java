/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Comment;

/**
 *
 * @author nkiem
 */
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentDAO implements DAOInterface<Comment, Integer> {

    @Override
    public int insert(Comment comment) {
        int row = 0;
        String sqlInsert = "INSERT INTO Comment (NewsID, UserID, UserType, Content, CommentDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setInt(1, comment.getNewsId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getUserType());
            ps.setString(4, comment.getContent());
            ps.setTimestamp(5, Timestamp.valueOf(comment.getCommentDate()));

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Comment comment) {
        int row = 0;
        String sql = "UPDATE Comment SET Content = ?, CommentDate = ? WHERE CommentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, comment.getContent());
            ps.setTimestamp(2, Timestamp.valueOf(comment.getCommentDate()));
            ps.setInt(3, comment.getCommentId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Comment comment) {
        int row = 0;
        String sql = "DELETE FROM Comment WHERE CommentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, comment.getCommentId());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Comment> selectAll() {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * FROM Comment";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("CommentID"),
                        rs.getInt("NewsID"),
                        rs.getInt("UserID"),
                        rs.getString("UserType"),
                        rs.getString("Content"),
                        rs.getTimestamp("CommentDate").toLocalDateTime()
                );
                list.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Comment> selectTop2CommentRecent() {
    List<Comment> list = new ArrayList<>();
    String sql = "SELECT TOP 2 c.*, " +
                "CASE " +
                "   WHEN c.UserType = 'Staff' THEN s.FullName " +
                "   WHEN c.UserType = 'Resident' THEN r.FullName " +
                "   ELSE 'Unknown User' " +
                "END AS UserName, " +
                "CASE " +
                "   WHEN c.UserType = 'Staff' THEN si.ImageURL " +
                "   WHEN c.UserType = 'Resident' THEN ri.ImageURL " +
                "   ELSE 'default_avatar.jpg' " +
                "END AS UserAvatar " +
                "FROM Comment c " +
                "LEFT JOIN Staff s ON c.UserID = s.StaffID AND c.UserType = 'Staff' " +
                "LEFT JOIN Resident r ON c.UserID = r.ResidentID AND c.UserType = 'Resident' " +
                "LEFT JOIN Image si ON s.ImageID = si.ImageID " +
                "LEFT JOIN Image ri ON r.ImageID = ri.ImageID " +
                "ORDER BY c.CommentDate DESC";

    try (Connection connection = DBContext.getConnection(); 
         PreparedStatement ps = connection.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Comment comment = new Comment(
                rs.getInt("CommentID"),
                rs.getInt("NewsID"),
                rs.getInt("UserID"),
                rs.getString("UserType"),
                rs.getString("Content"),
                rs.getTimestamp("CommentDate").toLocalDateTime()
            );
            comment.setUserName(rs.getString("UserName"));
            comment.setUserAvatar(rs.getString("UserAvatar"));
            
            list.add(comment);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

   public int findCommentOffset(int newsId, int commentId, int limit) {
    int position = 0;
    String sql = "SELECT COUNT(*) AS commentPosition FROM Comment " +
                 "WHERE NewsID = ? AND CommentDate > (SELECT CommentDate FROM Comment WHERE CommentID = ?)";
    
    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, newsId);
        ps.setInt(2, commentId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                position = rs.getInt("commentPosition");
                // Round down to the nearest multiple of limit
                position = (position / limit) * limit;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return position;
}
    @Override
    public Comment selectById(Integer id) {
        Comment comment = null;
        String sql = "SELECT * FROM Comment WHERE CommentID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comment = new Comment(
                            rs.getInt("CommentID"),
                            rs.getInt("NewsID"),
                            rs.getInt("UserID"),
                            rs.getString("UserType"),
                            rs.getString("Content"),
                            rs.getTimestamp("CommentDate").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comment;
    }
    public List<Comment> getCommentsByNewsId(int newsId) {
    return getCommentsByNewsId(newsId, 0, Integer.MAX_VALUE); // Lấy tất cả
}

public List<Comment> getCommentsByNewsId(int newsId, int offset, int limit) {
    List<Comment> list = new ArrayList<>();
    String sql = "SELECT c.*, " +
                "CASE " +
                "   WHEN c.UserType = 'Staff' THEN s.FullName " +
                "   WHEN c.UserType = 'Resident' THEN r.FullName " +
                "   ELSE 'Unknown User' " +
                "END AS UserName, " +
                "CASE " +
                "   WHEN c.UserType = 'Staff' THEN si.ImageURL " +
                "   WHEN c.UserType = 'Resident' THEN ri.ImageURL " +
                "   ELSE 'default_avatar.jpg' " +
                "END AS UserAvatar " +
                "FROM Comment c " +
                "LEFT JOIN Staff s ON c.UserID = s.StaffID AND c.UserType = 'Staff' " +
                "LEFT JOIN Resident r ON c.UserID = r.ResidentID AND c.UserType = 'Resident' " +
                "LEFT JOIN Image si ON s.ImageID = si.ImageID " +
                "LEFT JOIN Image ri ON r.ImageID = ri.ImageID " +
                "WHERE c.NewsID = ? " +
                "ORDER BY c.CommentDate DESC";

    // Nếu cần phân trang, thêm OFFSET-FETCH vào câu truy vấn
    if (limit < Integer.MAX_VALUE) {
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    }

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, newsId);
        
        if (limit < Integer.MAX_VALUE) {
            ps.setInt(2, offset);
            ps.setInt(3, limit);
        }
        
        System.out.println("Executing query: " + sql.replace("?", "_?_") + 
                          " with params: newsId=" + newsId + 
                          ", offset=" + offset + 
                          ", limit=" + limit); // Log để debug
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getInt("CommentID"),
                    rs.getInt("NewsID"),
                    rs.getInt("UserID"),
                    rs.getString("UserType"),
                    rs.getString("Content"),
                    rs.getTimestamp("CommentDate").toLocalDateTime()
                );
                // Add additional fields that aren't in the Comment model
                comment.setUserName(rs.getString("UserName"));
                comment.setUserAvatar(rs.getString("UserAvatar"));
                
                list.add(comment);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace(); // In chi tiết lỗi
    }
    return list;
}

    public int insertAndGetId(Comment comment) {
        int generatedId = -1;
        String sqlInsert = "INSERT INTO Comment (NewsID, UserID, UserType, Content, CommentDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, comment.getNewsId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getUserType());
            ps.setString(4, comment.getContent());
            ps.setTimestamp(5, Timestamp.valueOf(comment.getCommentDate()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    comment.setCommentId(generatedId);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generatedId;
    }
}
