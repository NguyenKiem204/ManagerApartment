/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.News;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NewsDetail;

public class NewsDAO implements DAOInterface<News, Integer> {
    
    @Override
    public int insert(News news) {
        int row = 0;
        String sqlInsert = "INSERT INTO [News] (ImageID, Title, Description, SentDate, StaffID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setInt(1, news.getImageID());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getDescription());
            ps.setDate(4, Date.valueOf(news.getSentDate()));
            ps.setInt(5, news.getStaffID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(News news) {
        int row = 0;
        String sql = "UPDATE [News] SET ImageID = ?, Title = ?, Description = ?, SentDate = ?, StaffID = ? WHERE NewsID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, news.getImageID());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getDescription());
            ps.setDate(4, Date.valueOf(news.getSentDate()));
            ps.setInt(5, news.getStaffID());
            ps.setInt(6, news.getNewsID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(News news) {
        int row = 0;
        String sql = "DELETE FROM [News] WHERE NewsID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, news.getNewsID());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<News> selectAll() {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM [News]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News news = new News(
                        rs.getInt("NewsID"),
                        rs.getInt("ImageID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("SentDate").toLocalDate(),
                        rs.getInt("StaffID")
                );
                list.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public News selectById(Integer id) {
        News news = null;
        String sql = "SELECT * FROM [News] WHERE NewsID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    news = new News(
                            rs.getInt("NewsID"),
                            rs.getInt("ImageID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getDate("SentDate").toLocalDate(),
                            rs.getInt("StaffID")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }
    
    public List<NewsDetail> getAllNews() {
        List<NewsDetail> list = new ArrayList<>();
        String sql = "SELECT n.NewsID, n.ImageID, i.ImageURL, n.Title, n.Description, n.SentDate, n.StaffID, s.FullName " +
                     "FROM [News] n " +
                     "JOIN [Image] i ON n.ImageID = i.ImageID " +
                     "JOIN [Staff] s ON n.StaffID = s.StaffID";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NewsDetail news = new NewsDetail(
                        rs.getInt("NewsID"),
                        rs.getInt("ImageID"),
                        rs.getString("ImageURL"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getTimestamp("SentDate").toLocalDateTime(),
                        rs.getInt("StaffID"),
                        rs.getString("FullName")
                );
                list.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public NewsDetail getNewsDetailByID(Integer id) {
        NewsDetail news = null;
        String sql = "SELECT n.NewsID, n.ImageID, i.ImageURL, n.Title, n.Description, n.SentDate, n.StaffID, s.FullName " +
                     "FROM [News] n " +
                     "JOIN [Image] i ON n.ImageID = i.ImageID " +
                     "JOIN [Staff] s ON n.StaffID = s.StaffID " +
                     "WHERE n.NewsID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    news = new NewsDetail(
                            rs.getInt("NewsID"),
                            rs.getInt("ImageID"),
                            rs.getString("ImageURL"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getTimestamp("SentDate").toLocalDateTime(),
                            rs.getInt("StaffID"),
                            rs.getString("FullName")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }
    public void insertNewsWithImage(NewsDetail newsDetail) {
    String sqlInsertImage = "INSERT INTO Image (ImageURL) VALUES (?)";
    String sqlInsertNews = "INSERT INTO News (Title, Description, SentDate, StaffID, ImageID) VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement psImage = connection.prepareStatement(sqlInsertImage, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement psNews = connection.prepareStatement(sqlInsertNews, Statement.RETURN_GENERATED_KEYS)) {

        connection.setAutoCommit(false);
        psImage.setString(1, newsDetail.getImageURL());
        psImage.executeUpdate();
        ResultSet rs = psImage.getGeneratedKeys();
        if (!rs.next()) {
            System.out.println("Thêm ảnh thất bại!");
            connection.rollback();
            return;
        }
        int imageID = rs.getInt(1);
        psNews.setString(1, newsDetail.getTitle());
        psNews.setString(2, newsDetail.getDescription());
        psNews.setTimestamp(3, Timestamp.valueOf(newsDetail.getSentDate()));
        psNews.setInt(4, newsDetail.getStaffID());
        psNews.setInt(5, imageID);
        psNews.executeUpdate();

        connection.commit();
        System.out.println("Thêm tin tức thành công!");

    } catch (SQLException ex) {
        System.out.println("Lỗi khi thêm tin tức: " + ex.getMessage());
    }
}

}

