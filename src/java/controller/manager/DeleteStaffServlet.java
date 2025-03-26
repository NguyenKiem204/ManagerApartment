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
import model.Staff;

/**
 *
 * @author fptshop
 */
@WebServlet(name="DeleteStaffServlet", urlPatterns={"/manager/deleteStaff"})
public class DeleteStaffServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String StaffId = request.getParameter("staffId");
        StaffDAO staffDAO = new StaffDAO();
        String msg = "";
        Staff staff = staffDAO.selectById(Integer.parseInt(StaffId));
        int isDeleted = staffDAO.delete1(staff); 
        if (isDeleted == 1) {
            msg = "Delete staff " + StaffId + " successfully!";
            
        } else {
            msg = "Delete failed!";
        }
        request.setAttribute("mess", msg);
        //request.getRequestDispatcher("/manager/manageStaff").forward(request, response);
        response.sendRedirect("manageStaff");
    }
}