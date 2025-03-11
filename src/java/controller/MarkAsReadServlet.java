/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.NotificationDAO;
import dao.RequestDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import model.Notification;
import model.Request;
import model.TypeRequest;

/**
 *
 * @author admin
 */
@WebServlet(name="MarkAsReadServlet", urlPatterns={"/MarkAsRead"})
public class MarkAsReadServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MarkAsReadServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarkAsReadServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String notificationId_raw = request.getParameter("notificationId");
        System.out.println("notificationID LA: " + notificationId_raw);
        int notificationId = 0;
        try {
            notificationId = Integer.parseInt(notificationId_raw);
        } catch (NumberFormatException e) {
        }
        NotificationDAO notificationDAO = new NotificationDAO();
        RequestDAO requestDAO = new RequestDAO();
        StaffDAO staffDAO = new StaffDAO();
        
        notificationDAO.updateIsRead(notificationId);
        // Cập nhật trạng thái trong DB
        /*TH là thông báo request của staff, khi bấm vào xem là xác nhận chuyển
        từ status 2 -> 3 (Inprogress)*/
        Notification notification = notificationDAO.selectById(notificationId);
        if (notification.getReferenceTable().equalsIgnoreCase("Request")) {
            Request request1 = requestDAO.selectById(notification.getReferenceId());
            //get roleId -> xac dinh la staffId nao quan ly
            int roleId = request1.getTypeRq().getRole().getRoleID();
            //get staffId by roleId
            int staffId = staffDAO.getStaffByRoleIDAndStatus(roleId, "Active").getStaffId();
            
            if (request1.getStatus().getStatusID() == 2) {
                //update status assign to in progress
                requestDAO.updateStatus(request1.getRequestID(), 3);
                
                String statusName = requestDAO.selectById(notification.getReferenceId()).getStatus().getStatusName();
                //resident received notify about change status 
                Notification notification_resident = new Notification(staffId,
                      "Staff", "Request updated new status: " + statusName, "request",
                      LocalDateTime.now(), false, notification.getReferenceId(), "Request",
                      null, request1.getResident());
                notificationDAO.insert(notification_resident);
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
