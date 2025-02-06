/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import dao.AccountDAO;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Resident;
import model.Staff;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        AccountDAO accountDAO = new AccountDAO();
        String userType = request.getParameter("userType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember_me");
        Cookie cookie1 = new Cookie("email", email);  
        Cookie cookie2 = new Cookie("password", password);
        Cookie cookie3 = new Cookie("remember", remember);
        Cookie cookie4 = new Cookie("userType", userType);
        if(remember!=null){
            cookie1.setMaxAge(60*60*24*365);
            cookie2.setMaxAge(60*60*24*365);
            cookie3.setMaxAge(60*60*24*365);
        }else{
            cookie1.setMaxAge(0);
            cookie2.setMaxAge(0);
            cookie3.setMaxAge(0);
        }
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        Staff staff = null;
        Resident resident = null;
        if(userType.toLowerCase().equals("staff")){
            staff = staffDAO.checkLogin(email, password);
        }else resident = residentDAO.checkLogin(email, password);
        HttpSession session = request.getSession();
        if (staff == null && resident == null) {
            request.setAttribute("userType", userType);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("error", "***Email or Password fail");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            session.setAttribute("staff", staff);
            session.setAttribute("resident", resident);
            session.setMaxInactiveInterval(600);
            response.sendRedirect("home");
        }
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
