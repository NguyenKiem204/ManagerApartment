/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ApartmentDAO;
import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Apartment;

/**
 *
 * @author fptshop
 */
@WebServlet(name = "UpdateApartmentServlet", urlPatterns = {"/manager/updateApartment"})

public class UpdateApartmentServlet extends HttpServlet {

    private ApartmentDAO apartmentDAO = new ApartmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("apartmentId");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int apartmentId = Integer.parseInt(idStr);
                Apartment apartment = apartmentDAO.getApartmentById(apartmentId);

                if (apartment != null) {
                    request.setAttribute("apartment", apartment);
                    request.getRequestDispatcher("/manager/updateapartment.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String apartmentIdRaw = request.getParameter("apartmentId");
            String ownerIdRaw = request.getParameter("ownerId");

            if (apartmentIdRaw == null || apartmentIdRaw.trim().isEmpty()
                    || ownerIdRaw == null || ownerIdRaw.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing apartmentId or ownerId");
                return;
            }

            int apartmentId = Integer.parseInt(apartmentIdRaw.trim());
            int ownerId = Integer.parseInt(ownerIdRaw.trim());

            // Kiểm tra ownerId có phải là Resident có roleId = 7 không
            ResidentDAO residentDAO = new ResidentDAO();
            if (!residentDAO.isOwnerResident(ownerId)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("OwnerId is not exist");
                return;
            }

            String apartmentName = request.getParameter("apartmentName");
            String block = request.getParameter("block");
            String status = request.getParameter("status");
            String type = request.getParameter("type");

            if (apartmentName == null || apartmentName.trim().isEmpty() || !apartmentName.matches("^[A-Za-zÀ-ỹ0-9\\s]+$")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Apartment name cannot be empty or contains special characters!");
                return;
            }

            if (apartmentDAO.isDuplicateNameInBlock(apartmentName, block, apartmentId)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Apartment name already exists in this block");
                return;
            }
            if (ownerId != 0 && "available".equalsIgnoreCase(status.trim())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Status cannot be 'available' if apartment has an owner!");
                return;
            }

            Apartment updatedApartment = new Apartment(apartmentId, apartmentName, block, status, type, ownerId);
            System.out.println("Updating Apartment: ID = " + apartmentId + ", Name = " + apartmentName);

            int success = apartmentDAO.update(updatedApartment);

            if (success != 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Update successful");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Update failed");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error: " + e.getMessage());
        }
    }

}
