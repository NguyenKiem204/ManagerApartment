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

public class NewsDAO implements DAOInterface<News, Integer> {

    @Override
    public int insert(News news) {
        int row = 0;
        String sqlInsert = "INSERT INTO [News] (ImageID, Title, Description, SentDate, StaffID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setInt(1, news.getImage().getImageID());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(news.getSentDate()));
            ps.setInt(5, news.getStaff().getStaffId());
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
            ps.setInt(1, news.getImage().getImageID());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(news.getSentDate()));
            ps.setInt(5, news.getStaff().getStaffId());
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

    ImageDAO imagedao = new ImageDAO();
    StaffDAO staffdao = new StaffDAO();

    @Override
    public List<News> selectAll() {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM [News]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News news = new News(
                        rs.getInt("NewsID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getTimestamp("SentDate").toLocalDateTime(),
                        staffdao.selectById(rs.getInt("StaffID")),
                        imagedao.selectById(rs.getInt("ImageID"))
                );
                list.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<News> searchNews(String title, String startDate, String endDate, int page, int pageSize) {
    List<News> list = new ArrayList<>();
    int offset = (page - 1) * pageSize;

    StringBuilder sql = new StringBuilder("SELECT * FROM [News] WHERE 1=1");
    List<Object> params = new ArrayList<>();

    boolean hasTitle = title != null && !title.trim().isEmpty();
    boolean hasStartDate = startDate != null && !startDate.trim().isEmpty();
    boolean hasEndDate = endDate != null && !endDate.trim().isEmpty();

    if (hasTitle) {
        sql.append(" AND Title LIKE ?");
        params.add("%" + title + "%");
    }

    if (hasStartDate) {
        sql.append(" AND CAST(SentDate AS DATE) >= ?");
        params.add(startDate);
    }
    if (hasEndDate) {
        sql.append(" AND CAST(SentDate AS DATE) <= ?");
        params.add(endDate);
    }

    // Nếu có điều kiện ngày tháng thì sắp xếp theo độ gần nhất
    if (hasStartDate && hasEndDate) {
        sql.append(" ORDER BY ABS(DATEDIFF(DAY, CAST(SentDate AS DATE), DATEADD(DAY, DATEDIFF(DAY, CAST(? AS DATE), CAST(? AS DATE)) / 2, CAST(? AS DATE))))");
        params.add(startDate);
        params.add(endDate);
        params.add(startDate);
    } else if (hasStartDate) {
        sql.append(" ORDER BY ABS(DATEDIFF(DAY, CAST(SentDate AS DATE), CAST(? AS DATE)))");
        params.add(startDate);
    } else if (hasEndDate) {
        sql.append(" ORDER BY ABS(DATEDIFF(DAY, CAST(SentDate AS DATE), CAST(? AS DATE)))");
        params.add(endDate);
    } 
    else if (!hasTitle) {
        sql.append(" ORDER BY SentDate DESC");
    }

    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    try (Connection connection = DBContext.getConnection(); 
         PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        
        int paramIndex = 1;
        for (Object param : params) {
            ps.setObject(paramIndex++, param);
        }
        ps.setInt(paramIndex++, offset);
        ps.setInt(paramIndex++, pageSize);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            News news = new News(
                    rs.getInt("NewsID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getTimestamp("SentDate").toLocalDateTime(),
                    staffdao.selectById(rs.getInt("StaffID")),
                    imagedao.selectById(rs.getInt("ImageID"))
            );
            list.add(news);
        }
    } catch (SQLException ex) {
        Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}


    public int getTotalSearchRecords(String title, String startDate, String endDate) {
        int totalRecords = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM [News] WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (title != null && !title.trim().isEmpty()) {
            sql.append(" AND Title LIKE ?");
            params.add("%" + title + "%");
        }

        if ((startDate != null && !startDate.trim().isEmpty()) || (endDate != null && !endDate.trim().isEmpty())) {
            if (startDate != null && !startDate.trim().isEmpty()) {
                sql.append(" AND CAST(SentDate AS DATE) >= ?");
                params.add(startDate);
            }
            if (endDate != null && !endDate.trim().isEmpty()) {
                sql.append(" AND CAST(SentDate AS DATE) <= ?");
                params.add(endDate);
            }
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : params) {
                ps.setObject(paramIndex++, param);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRecords;
    }

    public List<News> selectAll(int page, int pageSize) {
        List<News> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM [News] ORDER BY [SentDate] DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News news = new News(
                        rs.getInt("NewsID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getTimestamp("SentDate").toLocalDateTime(),
                        staffdao.selectById(rs.getInt("StaffID")),
                        imagedao.selectById(rs.getInt("ImageID"))
                );
                list.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getTotalRecords() {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM [News]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRecords;
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
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getTimestamp("SentDate").toLocalDateTime(),
                            staffdao.selectById(rs.getInt("StaffID")),
                            imagedao.selectById(rs.getInt("ImageID"))
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public void insertNewsWithImage(News news) {
        String sqlInsertImage = "INSERT INTO Image (ImageURL) VALUES (?)";
        String sqlInsertNews = "INSERT INTO News (Title, Description, SentDate, StaffID, ImageID) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement psImage = connection.prepareStatement(sqlInsertImage, Statement.RETURN_GENERATED_KEYS); PreparedStatement psNews = connection.prepareStatement(sqlInsertNews, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);
            psImage.setString(1, news.getImage().getImageURL());
            psImage.executeUpdate();
            ResultSet rs = psImage.getGeneratedKeys();
            if (!rs.next()) {
                System.out.println("Thêm ảnh thất bại!");
                connection.rollback();
                return;
            }
            int imageID = rs.getInt(1);
            psNews.setString(1, news.getTitle());
            psNews.setString(2, news.getDescription());
            psNews.setTimestamp(3, Timestamp.valueOf(news.getSentDate()));
            psNews.setInt(4, news.getStaff().getStaffId());
            psNews.setInt(5, imageID);
            psNews.executeUpdate();

            connection.commit();
            System.out.println("Thêm tin tức thành công!");

        } catch (SQLException ex) {
            System.out.println("Lỗi khi thêm tin tức: " + ex.getMessage());
        }
    }

    public void updateNewsWithImage(News news) {
        String sqlUpdateImage = "UPDATE Image SET ImageURL = ? WHERE ImageID = ?";
        String sqlUpdateNews = "UPDATE News SET Title = ?, Description = ?, StaffID = ?, ImageID = ? WHERE NewsID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement psUpdateImage = connection.prepareStatement(sqlUpdateImage); PreparedStatement psUpdateNews = connection.prepareStatement(sqlUpdateNews)) {

            connection.setAutoCommit(false);
            int imageID = news.getImage().getImageID();

            if (news.getImage().getImageURL() != null) {
                if (imageID != 0) {
                    psUpdateImage.setString(1, news.getImage().getImageURL());
                    psUpdateImage.setInt(2, imageID);
                    psUpdateImage.executeUpdate();
                }
            }

            psUpdateNews.setString(1, news.getTitle());
            psUpdateNews.setString(2, news.getDescription());
            psUpdateNews.setInt(3, news.getStaff().getStaffId());
            psUpdateNews.setInt(4, imageID);
            psUpdateNews.setInt(5, news.getNewsID());
            psUpdateNews.executeUpdate();

            connection.commit();
            System.out.println("Cập nhật tin tức thành công!");

        } catch (SQLException ex) {
            System.out.println("Lỗi khi cập nhật tin tức: " + ex.getMessage());
        }
    }

}
