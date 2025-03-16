/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author fptshop
 */
@WebServlet(name="UpdateStaffStatusServlet", urlPatterns={"/manager/updateStaffStatus"})
public class UpdateStaffStatusServlet extends HttpServlet {
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        try {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            String status = request.getParameter("status");

            StaffDAO residentDAO = new StaffDAO();
            boolean isUpdated = residentDAO.updateStatus(staffId, status);

            if (isUpdated) {
                out.write("{\"success\": true, \"message\": \"Cập nhật trạng thái thành công!\"}");
            } else {
                out.write("{\"success\": false, \"message\": \"Cập nhật thất bại!\"}");
            }
        } catch (Exception e) {
            out.write("{\"success\": false, \"message\": \"Lỗi: " + e.getMessage() + "\"}");
        }
    }
}