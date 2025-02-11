/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Staff;

@WebServlet(name="ManageStaffServlet", urlPatterns={"/manageStaff"})
public class ManageStaffServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String sex = request.getParameter("sex");
    String status = request.getParameter("status");
    String searchKeyword = request.getParameter("searchKeyword"); 

    StaffDAO staffDAO = new StaffDAO();
    List<Staff> listStaff;

    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        listStaff = staffDAO.searchStaffs(searchKeyword.trim(), sex, status);
    } else {
        listStaff = staffDAO.getAllStaffs(sex, status);
    }

    request.setAttribute("listStaff", listStaff);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 

    request.getRequestDispatcher("mngstaff.jsp").forward(request, response);
}
}