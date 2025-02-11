/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ResidentDAO;
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
@WebServlet(name="UpdateResidentStatusServlet", urlPatterns={"/updateResidentStatus"})
public class UpdateResidentStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int residentId = Integer.parseInt(request.getParameter("residentId"));
            String status = request.getParameter("status");

            ResidentDAO residentDAO = new ResidentDAO();
            boolean isUpdated = residentDAO.updateStatus(residentId, status);

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