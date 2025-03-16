/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package config;

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
import model.GoogleAccount;
import model.Resident;
import model.Staff;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "LoginGoogle", urlPatterns = {"/logingoogle"})
public class LoginGoogle extends HttpServlet {

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
            out.println("<title>Servlet LoginGoogle</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginGoogle at " + request.getContextPath() + "</h1>");
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
        String code = request.getParameter("code");
        String accessToken = GoogleLogin.getToken(code);
        GoogleAccount account = GoogleLogin.getUserInfo(accessToken);
        String email = null;
        if (account != null) {
            email = account.getEmail();
        }
        if (account != null) {
            StaffDAO staffDAO = new StaffDAO();
            ResidentDAO residentDAO = new ResidentDAO();
            if (staffDAO.existEmail(email)) {
                Staff staff = staffDAO.selectByEmail(email);
                if (staff.getStatus().equalsIgnoreCase("ACTIVE")) {
                    request.getSession().setAttribute("staff", staff);
                    redirectBasedOnRole(response, request, staff.getRole().getRoleID());
                }
            } else if (residentDAO.existEmail(email)) {
                Resident resident = residentDAO.selectByEmail(email);
                if (resident.getStatus().equalsIgnoreCase("ACTIVE")) {
                    request.getSession().setAttribute("resident", resident);
                    redirectBasedOnRole(response, request, resident.getRole().getRoleID());
                }

            } else {
                request.setAttribute("error", "Email or Account not exist!");
                request.getRequestDispatcher("login").forward(request, response);
            }
        }
    }

    private void redirectBasedOnRole(HttpServletResponse response, HttpServletRequest request, int roleID) throws IOException {
        switch (roleID) {
            case 1:
                redirectToPage(response, request, "/manager/home");
                break;
            case 2:
                redirectToPage(response, request, "/administrative/menuadministrative.jsp");
                break;
            case 3:
                redirectToPage(response, request, "/accountant/home");
                break;
            case 4:
                redirectToPage(response, request, "/technical/home");
                break;
            case 5:
                redirectToPage(response, request, "menuservice.jsp");
                break;
            case 6:
                redirectToPage(response, request, "/tenant/home");
                break;
            case 7:
                redirectToPage(response, request, "/owner/home");
                break;
            default:
                redirectToPage(response, request, "error-403");
                break;
        }
    }

    private void redirectToPage(HttpServletResponse response, HttpServletRequest request, String page) throws IOException {
        response.sendRedirect(request.getContextPath() + page);
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
