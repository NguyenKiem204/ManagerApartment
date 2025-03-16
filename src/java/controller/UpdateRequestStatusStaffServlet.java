/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.NotificationDAO;
import dao.RequestDAO;
import dao.StaffDAO;
import dao.StatusRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import model.Notification;
import model.Request;
import model.Staff;
import model.StatusRequest;

/**
 *
 * @author admin
 */
@WebServlet(name="UpdateRequestStatusStaffServlet", urlPatterns={"/updateRequestStatusStaff"})
public class UpdateRequestStatusStaffServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateRequestStatusStaffServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRequestStatusStaffServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Nhận dữ liệu từ AJAX
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            int requestId = json.get("id").getAsInt();
            int newStatus = json.get("statusID").getAsInt();

            // Gọi DAO để cập nhật trạng thái trong DB
            RequestDAO requestDAO = new RequestDAO();
            boolean updated = requestDAO.updateStatus(requestId, newStatus);
            Request rq = requestDAO.selectById(requestId);
            //lay roleId dua vao typr request cua request duoc gui
            int roleId = rq.getTypeRq().getRole().getRoleID();

            StatusRequestDAO statusRequestDAO = new StatusRequestDAO();
            StatusRequest sr = statusRequestDAO.selectById(newStatus);

            //gửi thông báo đến manager
            HttpSession session = request.getSession();
            Staff staff = (Staff) session.getAttribute("staff");
            NotificationDAO notificationDAO = new NotificationDAO();
            StaffDAO staffDAO = new StaffDAO();
            Staff st = staffDAO.getStaffByRoleIDAndStatus(1, "Active");

            //staff gui thong bao cho manager khi staff xac nhan la da nhan duoc thong bao
            //trang thai moi ma staff update cho request la: In progress va Completed -> thong bao toi manager
            if (newStatus == 3 || newStatus == 4) {
                Notification notification_staff = new Notification(staff.getStaffId(),
                          "Staff", "Request updated new status: " + sr.getStatusName(), "request",
                          LocalDateTime.now(), false, requestId,
                          "Request", st, null);
                notificationDAO.insert(notification_staff);
            }
            
              //staff gui thong bao sau khi update trang thai moi cho owner(tat ca)
            Notification notification_resident = new Notification(staff.getStaffId(),
                      "Staff", "Request updated new status: " + sr.getStatusName(), "request",
                      LocalDateTime.now(), false, requestId, "Request",
                      null, rq.getResident());

            notificationDAO.insert(notification_resident);
            
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", updated);
            out.print(jsonResponse.toString());

        } catch (IOException e) {
            e.printStackTrace();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", false);
            out.print(jsonResponse.toString());
        } finally {
            out.flush();
        }
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
