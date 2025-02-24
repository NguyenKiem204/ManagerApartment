/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import model.Request;

/**
 *
 * @author admin
 */
public class RequestDAO implements DAOInterface<Request, Integer> {

    ResidentDAO residentdao = new ResidentDAO();
    StatusRequestDAO statusrequestdao = new StatusRequestDAO();
    TypeRequestDAO typerequestdao = new TypeRequestDAO();
    ApartmentDAO apartmentdao = new ApartmentDAO();

    @Override
    public int insert(Request t) {
        int row = 0;
        String sqlInsert = """
                           INSERT INTO [dbo].[Request]
                                      ([Description]
                                      ,[Title]
                                      ,[Date]
                                      ,[ResidentID]
                                      ,[TypeRqID]
                                      ,[StatusID]
                                      ,[ApartmentID]
                                      ,[CompletedAt]
                                      ,[ViewedAt])
                                VALUES
                                      (?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, t.getDescription());
            ps.setString(2, t.getTitle());
            ps.setDate(3, Date.valueOf(t.getDate()));
            ps.setInt(4, t.getResident().getResidentId());
            ps.setInt(5, t.getTypeRq().getTypeRqID());
            ps.setInt(6, t.getStatus().getStatusID());
            ps.setInt(7, t.getApartment().getApartmentId());
            if (t.getCompletedAt() != null) {
                ps.setDate(8, Date.valueOf(t.getCompletedAt()));
            } else {
                ps.setNull(8, Types.TIMESTAMP);
            }

            if (t.getViewedDate() != null) {
                ps.setDate(9, Date.valueOf(t.getViewedDate()));
            } else {
                ps.setNull(9, Types.TIMESTAMP);
            }
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Request t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Request t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Request> selectAll() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Request selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Request> getAllRequestsSortedByResident() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request ORDER BY ResidentID";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsSortedByService() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request ORDER BY TypeRqID";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsSortedByStatus() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request ORDER BY StatusID";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsByResidentOrApartment(String key) {
        List<Request> list = new ArrayList<>();
        String sql = """
                     SELECT [RequestID]
                           ,[Description]
                           ,[Title]
                           ,[Date]
                           ,[StaffID]
                           ,r.ResidentID
                           ,[TypeRqID]
                           ,r.StatusID
                           ,r.ApartmentID
                           ,re.FullName
                           ,a.ApartmentName
                       FROM [ApartmentManagement].[dbo].[Request] r 
                           join Resident re on r.ResidentID = re.ResidentID
                           join Apartment a on a.ApartmentID = r.ApartmentID
                       WHERE re.FullName LIKE ? OR a.ApartmentName LIKE ?""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsBySearchOrFilterOrSort(String keySearch,
              int typeRequestID, LocalDate date, int statusID, int keySort,
              int page, int pageSize) {
        List<Request> list = new ArrayList<>();
        String sql = """
                     SELECT [RequestID]
                        ,[Description]
                        ,[Title]
                        ,[Date]
                        ,r.StaffID
                        ,r.ResidentID
                        ,res.FullName
                        ,r.TypeRqID
                        ,r.StatusID
                        ,a.ApartmentID
                        ,a.ApartmentName
                    FROM [ApartmentManagement].[dbo].[Request] r 
                        JOIN Apartment a ON r.ApartmentID = a.ApartmentID
                        JOIN Resident res ON r.ResidentID = res.ResidentID
                        WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (res.FullName LIKE ? OR a.ApartmentName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (typeRequestID != 0) {
                sql += " AND r.TypeRqID = ?";
                params.add(typeRequestID);
            }

            //check rating is null or not
            if (statusID != 0) {
                sql += " AND r.StatusID = ?";
                params.add(statusID);
            }

            //check date is null or not
            if (date != null) {
                sql += " AND CAST([Date] AS DATE) = ?";
                params.add(Date.valueOf(date));
            }
//------------------------------------------

            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY a.ApartmentName";
                    case 2 ->
                        sql += " ORDER BY Date DESC";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY RequestID";
            }
//xu ly phan trang
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            int offset = (page - 1) * pageSize;
            params.add(offset);
            params.add(pageSize);
        } catch (Exception e) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsBySearchOrFilterOrSortOfStaff(String keySearch,
              int typeRequestID, LocalDate date, int statusID, int keySort,
              int page, int pageSize, int staffID) {
        List<Request> list = new ArrayList<>();
        String sql = """
                     SELECT [RequestID]
                        ,[Description]
                        ,[Title]
                        ,[Date]
                        ,r.StaffID
                        ,r.ResidentID
                        ,res.FullName
                        ,r.TypeRqID
                        ,r.StatusID
                        ,a.ApartmentID
                        ,a.ApartmentName
                    FROM [ApartmentManagement].[dbo].[Request] r 
                        JOIN Apartment a ON r.ApartmentID = a.ApartmentID
                        JOIN Resident res ON r.ResidentID = res.ResidentID
                        WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (res.FullName LIKE ? OR a.ApartmentName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (typeRequestID != 0) {
                sql += " AND r.TypeRqID = ?";
                params.add(typeRequestID);
            }

            //check rating is null or not
            if (statusID != 0) {
                sql += " AND r.StatusID = ?";
                params.add(statusID);
            } else {
                sql += " AND StatusID in (2, 3) AND StaffID = ?";
                params.add(staffID);
            }

            //check date is null or not
            if (date != null) {
                sql += " AND CAST([Date] AS DATE) = ?";
                params.add(Date.valueOf(date));
            }
//------------------------------------------

            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY a.ApartmentName";
                    case 2 ->
                        sql += " ORDER BY Date DESC";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY RequestID";
            }
//xu ly phan trang
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            int offset = (page - 1) * pageSize;
            params.add(offset);
            params.add(pageSize);
        } catch (Exception e) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getNumberOfRequestsBySearchOrFilterOrSort(String keySearch,
              int typeRequestID, LocalDate date, int statusID, int keySort) {
        int num = 0;
        String sql = """
                     SELECT [RequestID]
                                                  ,[Description]
                                                  ,[Title]
                                                  ,[Date]
                                                  ,r.StaffID
                                                  ,r.ResidentID
                                                  ,res.FullName
                                                  ,r.TypeRqID
                                                  ,r.StatusID
                                                  ,a.ApartmentID
                                                  ,a.ApartmentName
                                              FROM [ApartmentManagement].[dbo].[Request] r 
                                                JOIN Apartment a ON r.ApartmentID = a.ApartmentID
                                                JOIN Resident res ON r.ResidentID = res.ResidentID
                                              WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (res.FullName LIKE ? OR a.ApartmentName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (typeRequestID != 0) {
                sql += " AND r.TypeRqID = ?";
                params.add(typeRequestID);
            }

            //check rating is null or not
            if (statusID != 0) {
                sql += " AND r.StatusID = ?";
                params.add(statusID);
            }

            //check date is null or not
            if (date != null) {
                sql += " AND CAST([Date] AS DATE) = ?";
                params.add(Date.valueOf(date));
            }
//------------------------------------------

            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY a.ApartmentName";
                    case 2 ->
                        sql += " ORDER BY RequestID";
                    default ->
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                num++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public int getNumberOfRequestsBySearchOrFilterOrSortOfStaff(String keySearch,
              int typeRequestID, LocalDate date, int statusID, int keySort, int staffID) {
        int num = 0;
        String sql = """
                     SELECT [RequestID]
                                                  ,[Description]
                                                  ,[Title]
                                                  ,[Date]
                                                  ,r.StaffID
                                                  ,r.ResidentID
                                                  ,res.FullName
                                                  ,r.TypeRqID
                                                  ,r.StatusID
                                                  ,a.ApartmentID
                                                  ,a.ApartmentName
                                              FROM [ApartmentManagement].[dbo].[Request] r 
                                                JOIN Apartment a ON r.ApartmentID = a.ApartmentID
                                                JOIN Resident res ON r.ResidentID = res.ResidentID
                                              WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND (res.FullName LIKE ? OR a.ApartmentName LIKE ?)";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check roleName is null or not
            if (typeRequestID != 0) {
                sql += " AND r.TypeRqID = ?";
                params.add(typeRequestID);
            }

            //check rating is null or not
            if (statusID != 0) {
                sql += " AND r.StatusID = ?";
                params.add(statusID);
            } else {
                sql += " AND StatusID in (2, 3) AND StaffID = ?";
                params.add(staffID);
            }

            //check date is null or not
            if (date != null) {
                sql += " AND CAST([Date] AS DATE) = ?";
                params.add(Date.valueOf(date));
            }
//------------------------------------------

            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY a.ApartmentName";
                    case 2 ->
                        sql += " ORDER BY RequestID";
                    default ->
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                num++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public List<Request> getListByPage(List<Request> list, int start, int end) {
        List<Request> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Request> selectFirstPage() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request ORDER BY [RequestID] OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> selectFirstPageOfStaff(int staffID) {
        List<Request> list = new ArrayList<>();
        String sql = """
                     SELECT * 
                     FROM Request  
                     where StatusID in (2, 3) AND StaffID = ? 
                     ORDER BY [RequestID] OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Kiểm tra nếu CompletedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date completedAtSql = rs.getDate("CompletedAt");
                LocalDate completedAt = (completedAtSql != null) ? completedAtSql.toLocalDate() : null;

                // Kiểm tra nếu ViewedAt không NULL thì chuyển thành LocalDate, nếu NULL thì gán null
                java.sql.Date viewedAtSql = rs.getDate("ViewedAt");
                LocalDate viewedAt = (viewedAtSql != null) ? viewedAtSql.toLocalDate() : null;

                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")),
                          completedAt,
                          viewedAt
                );
                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int numberfLineOStaff(int staffID) {
        String sql = "SELECT * FROM Request where StatusID in (2, 3) AND StaffID = ?";
        int num = 0;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                num++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public boolean updateStatus(int requestID, int newStatus) {
        String query = "update Request set StatusID = ? where RequestID = ?";
        try {
            Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, newStatus);
            ps.setInt(2, requestID);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
