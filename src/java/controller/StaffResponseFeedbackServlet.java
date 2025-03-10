/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ManagerFeedbackDAO;
import dao.NotificationDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.ManagerFeedback;
import model.Notification;
import model.Resident;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "StaffResponseFeedbackServlet", urlPatterns = {"/staffresponsefeedback"})
public class StaffResponseFeedbackServlet extends HttpServlet {

    private ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet StaffResponseFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffResponseFeedbackServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        // Kiểm tra quyền truy cập (chỉ cho phép Staff ngoại trừ Manager)
        if (resident != null || staff == null || staff.getRole().getRoleID() == 1) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("staffresponsefeedbacksuccessfull.jsp").forward(request, response);
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
        
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");
        
        NotificationDAO notificationDAO = new NotificationDAO();
        StaffDAO staffDAO = new StaffDAO();
        String managerFeedbackId_raw = request.getParameter("managerFeedbackId");
        String staffResponse = request.getParameter("staffResponse");
        int managerFeedbackId = 0;

        try {
            managerFeedbackId = Integer.parseInt(managerFeedbackId_raw);
            ManagerFeedback managerFb = managerFeedbackDAO.selectById(managerFeedbackId);
            managerFb.setStaffResponse(staffResponse);
            //update staffresponse in DB
            managerFeedbackDAO.update(managerFb);

            //manager received notifycation
//            Notification notification = new Notification("Staff response feedback review of this month.", "Response", LocalDate.now(), false, managerFeedbackId, "ManagerFeedback", staffDAO.getStaffByRoleIDAndStatus(1, "Adctive"), null);
//            Notification notification = new Notification(staff.getStaffId(), 
//                      "Staff", "Staff response feedback review of this month.",
//                      "Response", LocalDateTime.now(), false, 
//                      managerFeedbackId, "ManagerFeedback", 
//                      staffDAO.getStaffByRoleIDAndStatus(1, "Adctive"), 
//                      null);

            Notification notification = new Notification(staff.getStaffId(), 
                  "Staff", "Staff response feedback review of this month.", "ManagerFeedback",
                  LocalDateTime.now(), false, managerFeedbackId, 
                  "ManagerFeedback", staffDAO.getStaffByRoleIDAndStatus(1, "Active"), null);

            notificationDAO.insert(notification);
        } catch (NumberFormatException e) {
        }
        request.getRequestDispatcher("staffresponsefeedbacksuccessfull.jsp").forward(request, response);
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
