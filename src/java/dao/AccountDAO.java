/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author nkiem
 */
import config.PasswordUtil;
import model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO implements DAOInterface<Account, Integer> {

    private final PasswordUtil passwordEncode = new PasswordUtil();

    @Override
    public int insert(Account account) {
        int row = 0;
        String sqlInsert = "INSERT INTO Account (Mail, Password, Status, Sex, CCCD, FullName, PhoneNumber, ImageURL, RoleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(sqlInsert);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, account.getMail());
            String password = passwordEncode.hashPassword(account.getPassword());
            ps.setString(2, password);
            ps.setString(3, account.getStatus());
            ps.setString(4, account.getSex());
            ps.setString(5, account.getCccd());
            ps.setString(6, account.getFullName());
            ps.setString(7, account.getPhoneNumber());
             ps.setString(8, account.getImageURL());
            ps.setInt(9, account.getRoleId());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(Account account) {
        int row = 0;
        String sql = "UPDATE Account SET Mail = ?, Password = ?, Status = ?, Sex = ?, CCCD = ?, FullName = ?, PhoneNumber = ?, ImageURL = ?, RoleId = ? WHERE AccountId = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, account.getMail());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getStatus());
            ps.setString(4, account.getSex());
            ps.setString(5, account.getCccd());
            ps.setString(6, account.getFullName());
            ps.setString(7, account.getPhoneNumber());
            ps.setString(8, account.getImageURL());
            ps.setInt(9, account.getRoleId());
            ps.setInt(10, account.getAccountId());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int delete(Account account) {
        int row = 0;
        String sql = "DELETE FROM Account WHERE AccountId = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, account.getAccountId());

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) affected)");
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<Account> selectAll() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                          rs.getInt("AccountId"),
                          rs.getString("Mail"),
                          rs.getString("Password"),
                          rs.getString("Status"),
                          rs.getString("Sex"),
                          rs.getString("CCCD"),
                          rs.getString("FullName"),
                          rs.getString("PhoneNumber"),
                          rs.getString("ImageURL"),
                          rs.getInt("RoleId")
                );
                list.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Account selectById(Integer id) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE AccountId = ?";
        System.out.println(sql);

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                              rs.getInt("AccountId"),
                              rs.getString("Mail"),
                              rs.getString("Password"),
                              rs.getString("Status"),
                              rs.getString("Sex"),
                              rs.getString("CCCD"),
                              rs.getString("FullName"),
                              rs.getString("PhoneNumber"),
                              rs.getString("ImageURL"),
                              rs.getInt("RoleId")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
    public boolean existEmail(String email) {
    String sql = "SELECT * FROM Account WHERE Mail = ?";
    System.out.println(sql);

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return true;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

    public Account checkLogin(String mail, String password) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE Mail = ?";
        System.out.println(sql);
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int accountId = rs.getInt("AccountID");
                    String storedMail = rs.getString("Mail");
                    String storedPasswordHash = rs.getString("Password");
                    String status = rs.getString("Status");
                    String sex = rs.getString("Sex");
                    String cccd = rs.getString("CCCD");
                    String fullName = rs.getString("FullName");
                    String phoneNumber = rs.getString("PhoneNumber");
                    int roleId = rs.getInt("RoleID");
                    String imageURL = rs.getString("ImageURL");
                    if (passwordEncode.checkPassword(password, storedPasswordHash) && "ACTIVE".equals(status.toUpperCase())) {
                        account = new Account(accountId, mail, password, status, sex, cccd, fullName, phoneNumber, imageURL, roleId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public boolean updatePasswordInDatabase(String email, String hashedPassword) {

        try {
            Connection connection = DBContext.getConnection();
            String sql = "UPDATE Account SET Password = ? WHERE Mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, hashedPassword);
            statement.setString(2, email);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
