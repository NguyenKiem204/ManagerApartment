/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="DeleteTenantServlet", urlPatterns={"/owner/deleteTenant"})
public class DeleteTenantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String residentId = request.getParameter("tenantId");
        ResidentDAO residentDAO = new ResidentDAO();
        String msg = "";
        Resident resident = residentDAO.selectById(Integer.parseInt(residentId));
        int isDeleted = residentDAO.delete1(resident); 
        if (isDeleted == 1) {
            msg = "Delete tenant" + residentId + " successfully!";
            
        } else {
            msg = "Delete failed!";
        }
        request.setAttribute("mess", msg);
        request.getRequestDispatcher("/owner/manageContract").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
