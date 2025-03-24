/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Feedback;
import model.Resident;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "FeedbackDetailStaffServlet", urlPatterns = {"/feedbackdetail"})
public class FeedbackDetailStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet FeedbackDetailStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackDetailStaffServlet at " + request.getContextPath() + "</h1>");
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
        String fbID_raw = request.getParameter("feedbackId");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        // Kiểm tra nếu user là Resident hoặc không có Staff trong session => Không có quyền
        if (resident != null || staff == null) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        // Kiểm tra nếu fbID_raw là null hoặc không phải số
        int fbID;
        try {
            fbID = Integer.parseInt(fbID_raw);
        } catch (NumberFormatException e) {
            request.setAttribute("errorCode", "400");
            request.setAttribute("errorMessage", "Invalid feedback ID!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        // Lấy Feedback từ DB
        Feedback f = feedbackDAO.selectById(fbID);

        // Kiểm tra nếu Feedback không tồn tại
        if (f == null) {
            request.setAttribute("errorCode", "404");
            request.setAttribute("errorMessage", "Feedback not found!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        // Kiểm tra nếu Staff không phải là người phụ trách Feedback này
        if (staff.getStaffId() != f.getStaff().getStaffId()) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access this feedback!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        // Nếu tất cả điều kiện hợp lệ, gửi feedback đến trang JSP
        request.setAttribute("feedback", f);
        request.getRequestDispatcher("feedbackdetail.jsp").forward(request, response);
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
