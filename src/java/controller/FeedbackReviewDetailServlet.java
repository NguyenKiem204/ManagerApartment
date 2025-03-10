/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.FeedbackDAO;
import dao.ManagerFeedbackDAO;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
import model.ManagerFeedback;
import model.Resident;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "FeedbackReviewDetailServlet", urlPatterns = {"/feedbackreviewdetail"})
public class FeedbackReviewDetailServlet extends HttpServlet {

    private ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();
    private FeedbackDAO feedbackDAO = new FeedbackDAO();
    private StaffDAO staffDAO = new StaffDAO();

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
            out.println("<title>Servlet FeedbackReviewDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackReviewDetailServlet at " + request.getContextPath() + "</h1>");
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
        String managerFeedbackId_raw = request.getParameter("managerFeedbackId");

        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        if (resident != null || staff == null) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        int managerFeedbackId = 0;
        ManagerFeedback managerFeedback = new ManagerFeedback();
        List<Feedback> listFB = new ArrayList<>();
        try {
            managerFeedbackId = Integer.parseInt(managerFeedbackId_raw);
            managerFeedback = managerFeedbackDAO.selectById(managerFeedbackId);

            //truyền 1 list feedback detail theo tháng của từng staff
            listFB = feedbackDAO.getFeedbackByMonthYearAndRoleID(managerFeedback.getMonthYear(), staff.getRole().getRoleID());

        } catch (NumberFormatException e) {
        }
        request.setAttribute("managerFb", managerFeedback);
        request.setAttribute("listFb", listFB);
        request.getRequestDispatcher("feedbackreviewdetail.jsp").forward(request, response);
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
        processRequest(request, response);
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
