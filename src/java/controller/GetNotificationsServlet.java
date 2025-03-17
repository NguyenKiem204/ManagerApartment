/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import model.Notification;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetNotificationsServlet", urlPatterns = {"/GetNotifications"})
public class GetNotificationsServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        NotificationDAO notificationDAO = new NotificationDAO();
        String roleId_raw = request.getParameter("roleId");
        String residentId_raw = request.getParameter("residentId");
        String staffId_raw = request.getParameter("staffId");

        int roleId = 1;
        int residentId = 0;
        int staffId = 0;
        try {
            roleId = Integer.parseInt(roleId_raw);
            residentId = Integer.parseInt(residentId_raw);
            staffId = Integer.parseInt(staffId_raw);
        } catch (NumberFormatException e) {
        }
        List<Notification> list = new ArrayList<>();
        if (roleId != 6 && roleId != 7) {
            list = notificationDAO.selectAllByStaffID(roleId);
        } else {
            list = notificationDAO.selectAllByResidentId(residentId);
        }

        //lấy theo residentId
        JSONArray notificationsArray = new JSONArray();

        for (Notification notif : list) {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", notif.getNotificationId());
            obj.put("senderId", notif.getSenderId());
            obj.put("senderTable", notif.getSenderTable());
            obj.put("message", notif.getMessage());
            obj.put("type", notif.getType());
            obj.put("createdAt", notif.getCreatedAt());
            obj.put("isRead", notif.isRead());
            obj.put("referenceId", notif.getReferenceId());
            obj.put("referenceTable", notif.getReferenceTable());
            obj.put("staff", notif.getStaff());
            obj.put("resident", notif.getResident());

            notificationsArray.put(obj);
        }
        // Tạo ETag từ danh sách thông báo
        String etag = Integer.toHexString(notificationsArray.toString().hashCode());
        // Kiểm tra ETag từ request
        String ifNoneMatch = request.getHeader("If-None-Match");
        if (etag.equals(ifNoneMatch)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        // Trả về response JSON với ETag
        response.setContentType("application/json");
        response.setHeader("ETag", etag);
        response.getWriter().write(notificationsArray.toString());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
