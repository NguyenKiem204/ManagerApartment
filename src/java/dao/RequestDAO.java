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
import model.Request;

/**
 *
 * @author admin
 */
public class RequestDAO implements DAOInterface<Request, Integer> {

    StaffDAO staffdao = new StaffDAO();
    ResidentDAO residentdao = new ResidentDAO();
    StatusRequestDAO statusrequestdao = new StatusRequestDAO();
    TypeRequestDAO typerequestdao = new TypeRequestDAO();
    ApartmentDAO apartmentdao = new ApartmentDAO();

    @Override
    public int insert(Request t) {
        int row = 0;
        String sqlInsert = "INSERT INTO [dbo].[Request]\n"
                  + "           ([Description]\n"
                  + "           ,[Title]\n"
                  + "           ,[Date]\n"
                  + "           ,[StaffID]\n"
                  + "           ,[ResidentID]\n"
                  + "           ,[TypeRqID]\n"
                  + "           ,[StatusID], [ApartmentID])\n"
                  + "     VALUES\n"
                  + "           (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, t.getDescription());
            ps.setString(2, t.getTitle());
            ps.setDate(3, Date.valueOf(t.getDate()));
            ps.setInt(4, t.getStaff().getStaffId());
            ps.setInt(5, t.getResident().getResidentId());
            ps.setInt(6, t.getTypeRq().getTypeRqID());
            ps.setInt(7, t.getStatus().getStatusID());
            ps.setInt(8, t.getApartment().getApartmentId());
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
                Request fb = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("StatusID")),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID"))
                );
                list.add(fb);
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
                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("statusID")),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")));

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
                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("statusID")),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")));

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
                Request rq = new Request(rs.getInt("RequestID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("statusID")),
                          staffdao.selectById(rs.getInt("StaffID")),
                          residentdao.selectById(rs.getInt("ResidentID")),
                          typerequestdao.selectById(rs.getInt("TypeRqID")),
                          apartmentdao.selectById(rs.getInt("ApartmentID")));

                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Request> getAllRequestsByResidentOrApartment(String key) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT [RequestID]\n"
                  + "      ,[Description]\n"
                  + "      ,[Title]\n"
                  + "      ,[Date]\n"
                  + "      ,[StaffID]\n"
                  + "      ,r.ResidentID\n"
                  + "      ,[TypeRqID]\n"
                  + "      ,r.StatusID\n"
                  + "      ,r.ApartmentID\n"
                  + "	  ,re.FullName\n"
                  + "	  ,a.ApartmentName\n"
                  + "  FROM [ApartmentManagement].[dbo].[Request] r \n"
                  + "		join Resident re on r.ResidentID = re.ResidentID\n"
                  + "		join Apartment a on a.ApartmentID = r.ApartmentID\n"
                  + "  WHERE re.FullName LIKE ? OR a.ApartmentName LIKE ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request rq = new Request(rs.getInt("ResidentID"),
                          rs.getString("Description"),
                          rs.getString("Title"),
                          rs.getDate("Date").toLocalDate(),
                          statusrequestdao.selectById(rs.getInt("statusID")),
                          staffdao.selectById(rs.getInt("staffID")),
                          residentdao.selectById(rs.getInt("residentID")),
                          typerequestdao.selectById(rs.getInt("typeRqID")),
                          apartmentdao.selectById(rs.getInt("apartmentID")));

                list.add(rq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
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
