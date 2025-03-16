/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import config.PasswordUtil;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Resident;
import model.Staff;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ChangePasswordServlet", urlPatterns={"/changePassword"})
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PasswordUtil passwordUtil = new PasswordUtil();
        HttpSession session = request.getSession();
        String newPassword = request.getParameter("newPassword").trim();

        // Kiểm tra xem người dùng có đăng nhập không
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        if ((resident == null) && (staff == null)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized. Please log in again.");
            return;
        }

        // Kiểm tra mật khẩu có ít nhất 8 ký tự
        if (newPassword.length() < 8) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Password must be at least 8 characters.");
            return;
        }

        try {
            boolean updateSuccess = false;
            if (resident != null) {
                ResidentDAO residentDAO = new ResidentDAO();
                updateSuccess = residentDAO.updatePassword(resident.getResidentId(),  passwordUtil.hashPassword(newPassword));
            } else if (staff != null) {
                StaffDAO staffDAO = new StaffDAO();
                updateSuccess = staffDAO.updatePassword(staff.getStaffId(), passwordUtil.hashPassword(newPassword));
            }

            if (updateSuccess) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Password changed successfully.");
                session.invalidate(); // Đăng xuất sau khi đổi mật khẩu
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update password.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
