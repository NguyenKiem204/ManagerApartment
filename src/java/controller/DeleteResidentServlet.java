/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ResidentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Resident;

/**
 *
 * @author fptshop
 */
@WebServlet(name="DeleteResidentServlet", urlPatterns={"/deleteResident"})
public class DeleteResidentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String residentId = request.getParameter("residentId");
        ResidentDAO residentDAO = new ResidentDAO();
        String msg = "";
        Resident resident = residentDAO.selectById(Integer.parseInt(residentId));
        int isDeleted = residentDAO.delete(resident); 
        if (isDeleted == 1) {
            msg = "Delete resident " + residentId + " successfully!";
            
        } else {
            msg = "Delete failed!";
        }
        request.setAttribute("mess", msg);
        request.getRequestDispatcher("manageResident").forward(request, response);
    }
}