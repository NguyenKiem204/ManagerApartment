/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.ResidentDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Resident;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ViewTenantByApartmentServlet", urlPatterns={"/owner/viewTenants"})
public class ViewTenantByApartmentServlet extends HttpServlet {
 private ResidentDAO residentDAO = new ResidentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String apartmentIdRaw = request.getParameter("apartmentId");

        if (apartmentIdRaw == null || apartmentIdRaw.isEmpty()) {
            response.sendRedirect("/login.jsp");
            return;
        }

        int apartmentId = Integer.parseInt(apartmentIdRaw);
        List<Resident> tenants = residentDAO.getTenantsByApartment(apartmentId);

        request.setAttribute("tenants", tenants);
        request.setAttribute("apartmentId", apartmentId);
        request.getRequestDispatcher("viewtenants.jsp").forward(request, response);
    }
}
