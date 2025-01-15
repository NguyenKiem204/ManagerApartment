/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBContext {

    public static Connection getConnection() throws SQLException {
        try {
            // Nạp driver JDBC vào bộ nhớ (nếu cần)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

             String url = "jdbc:sqlserver://localhost:1433;databaseName=ApartmentManagement;encrypt=false;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456789";

            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver class not found: " + e.getMessage());
    }
        return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void testConnection() {
        Connection connection = null;
        try {
            connection = getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }
}
