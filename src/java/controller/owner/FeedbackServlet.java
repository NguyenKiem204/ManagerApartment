/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.FeedbackDAO;
import dao.ResidentDAO;
import dao.RoleDAO;
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
import java.util.List;
import model.Feedback;
import model.Resident;
import model.Role;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name="FeedbackServlet", urlPatterns={"/owner/feedback"})
public class FeedbackServlet extends HttpServlet {
   
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
            out.println("<title>Servlet FeedbackServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackServlet at " + request.getContextPath () + "</h1>");
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
        FeedbackDAO fbDAO = new FeedbackDAO();
//        StaffDAO stdao = new StaffDAO();
        RoleDAO rdao = new RoleDAO();
        List<Role> listrole = rdao.selectAll();
        request.setAttribute("listrole", listrole);
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
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
//      lấy được residentID dựa trên session
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        
        String title = request.getParameter("title");
        String staffID_raw = request.getParameter("staff");
        String rating_raw = request.getParameter("rating");
        String description = request.getParameter("description");
        
        int rating;
        int staffID;
        
        try {
            rating = Integer.parseInt(rating_raw);
            staffID = Integer.parseInt(staffID_raw);
            
            //add data into DB
            FeedbackDAO fbDAO = new FeedbackDAO();
            
            Feedback fb = new Feedback(title, description, LocalDate.now(), rating, staffDAO.selectById(staffID), resident); //resident.getResidentId()
//            Feedback fb = new Feedback
            System.out.println(fb.toString());
            fbDAO.insert(fb);
            
        } catch (NumberFormatException e) {
        }
        response.sendRedirect("feedbacksuccess.jsp");
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
