/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.ApartmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ResetOwnerServlet", urlPatterns={"/manager/resetOwner"})
public class ResetOwnerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String idParam = request.getParameter("apartmentId");
            System.out.println("Received apartmentId: " + idParam);

            if (idParam == null || idParam.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Apartment ID is missing!");
                out.write(jsonResponse.toString());
                return;
            }

            int apartmentId = Integer.parseInt(idParam);
            ApartmentDAO apartmentDAO = new ApartmentDAO();

            boolean isReset = apartmentDAO.resetOwner(apartmentId);
            if (isReset) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Reset owner successfully!");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to reset owner because apartment has an active contract!");
            }

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error: " + e.getMessage());
        }

        out.write(jsonResponse.toString());
    }


}
