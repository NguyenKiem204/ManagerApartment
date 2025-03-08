/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import model.Rule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class RuleDAO implements DAOInterface<Rule, Integer> {

    @Override
    public int insert(Rule rule) {
        // TODO: chua add staffID
        int row = 0;
        String sqlInsert = "INSERT INTO [Rule] (RuleName, RuleDescription, PublicDate) \n"
                + "VALUES (?, ?, ?);";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, rule.getRuleName());
            ps.setString(2, rule.getRuleDescription());
            ps.setDate(3, Date.valueOf(rule.getPublicDate()));
//            ps.setInt(4, rule.getStaffID());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Rule rule) {
        int row = 0;
        String sql = "UPDATE [Rule] \n"
                + "SET RuleName = ? \n"
                + ", RuleDescription = ? \n"
                + ", PublicDate = ? \n"
                //                + ", StaffID = ? \n"
                + "WHERE RuleID = ?;";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rule.getRuleName());
            ps.setString(2, rule.getRuleDescription());
            ps.setDate(3, Date.valueOf(rule.getPublicDate()));
//            ps.setInt(4, rule.getStaffID());
            ps.setInt(4, rule.getRuleID());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Rule rule) {
        int row = 0;
        String sql = "DELETE FROM [Rule] WHERE RuleID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rule.getRuleID());

            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Rule selectById(Integer id) {
        Rule rule = null;
        StaffDAO staffDAO = new StaffDAO();
        String sql = "SELECT [RuleID] \n"
                + ", [RuleName] \n"
                + ", [RuleDescription] \n"
                + ", [PublicDate] \n"
                + ", [StaffID] \n"
                + "FROM [Rule] \n"
                + "WHERE [RuleID] = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rule = new Rule(
                            rs.getInt("RuleID"),
                            rs.getString("RuleName"),
                            rs.getString("RuleDescription"),
                            rs.getDate("PublicDate").toLocalDate(),
                            staffDAO.getStaffByID(rs.getInt("StaffID"))
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rule;
    }

    public List<Rule> selectAll() {
        List<Rule> list = new ArrayList<>();
        StaffDAO staffDAO = new StaffDAO();
        String sql = "SELECT [RuleID] \n"
                + ", [RuleName] \n"
                + ", [RuleDescription] \n"
                + ", [PublicDate] \n"
                + ", [StaffID] \n"
                + "FROM [Rule]";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rule rule = new Rule(
                        rs.getInt("RuleID"),
                        rs.getString("RuleName"),
                        rs.getString("RuleDescription"),
                        rs.getDate("PublicDate").toLocalDate(),
                        staffDAO.getStaffByID(rs.getInt("StaffID"))
                );
                list.add(rule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Rule> selectByName(String name) {
        List<Rule> list = new ArrayList<>();
        StaffDAO staffDAO = new StaffDAO();
        String sql = "SELECT [RuleID], [RuleName], [RuleDescription], [PublicDate], [StaffID] "
                + "FROM [Rule] WHERE [RuleName] = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name); // Đặt tham số cho câu lệnh SQL

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { // Duyệt qua tất cả kết quả tìm được
                    Rule rule = new Rule(
                            rs.getInt("RuleID"),
                            rs.getString("RuleName"),
                            rs.getString("RuleDescription"),
                            rs.getDate("PublicDate").toLocalDate(),
                            staffDAO.getStaffByID(rs.getInt("StaffID"))
                    );
                    list.add(rule); // Thêm vào danh sách
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; // Trả về danh sách các Rule
    }

    public List<Rule> searchByName(String str) {
        List<Rule> list = new ArrayList<>();
        StaffDAO staffDAO = new StaffDAO();
        String sql = "SELECT [RuleID]\n"
                + "      ,[RuleName]\n"
                + "      ,[RuleDescription]\n"
                + "      ,[PublicDate]\n"
                + "      ,[StaffID]\n"
                + "  FROM [Rule]\n"
                + "  WHERE RuleName LIKE ?;";

        try (
                Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + str + "%"); // Đặt tham số cho câu lệnh SQL

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { // Duyệt qua tất cả kết quả tìm được
                    Rule rule = new Rule(
                            rs.getInt("RuleID"),
                            rs.getString("RuleName"),
                            rs.getString("RuleDescription"),
                            rs.getDate("PublicDate").toLocalDate(),
                            staffDAO.getStaffByID(rs.getInt("StaffID"))
                    );
                    list.add(rule); // Thêm vào danh sách
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; // Trả về danh sách các Rule
    }

}
