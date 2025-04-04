
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        loadCookies(request);
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
        String userType = request.getParameter("userType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember_me");

        handleCookies(response, email, password, remember, userType);
        handleLogin(request, response, userType, email, password);
    }

    private void handleCookies(HttpServletResponse response, String email, String password, String remember, String userType) {
        Cookie[] cookies = {
            new Cookie("email", email),
            new Cookie("password", password),
            new Cookie("remember", remember),
            new Cookie("userType", userType)
        };

        int maxAge = (remember != null) ? 60 * 60 * 24 * 365 : 0; // 1 năm nếu remember được chọn, ngược lại xóa cookie
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }
    }

    private void loadCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userType".equals(cookie.getName())) {
                    request.setAttribute("userType", cookie.getValue());
                }
            }
        }
    }

//    private void handleLogin(HttpServletRequest request, HttpServletResponse response, String userType, String email, String password)
//            throws ServletException, IOException {
//        StaffDAO staffDAO = new StaffDAO();
//        ResidentDAO residentDAO = new ResidentDAO();
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(600);
//
//        if ("staff".equalsIgnoreCase(userType)) {
//            Staff staff = staffDAO.checkLogin(email, password);
//            if (staff != null) {
//                session.setAttribute("staff", staff);
//                redirectBasedOnRole(response, request, staff.getRole().getRoleID());
//                return;
//            }
//        } else if ("resident".equalsIgnoreCase(userType)) {
//            Resident resident = residentDAO.checkLogin(email, password);
//            if (resident != null) {
//                session.setAttribute("resident", resident);
//                redirectBasedOnRole(response, request, resident.getRole().getRoleID());
//                return;
//            }
//        }
//        handleLoginFailure(request, response, userType, email, password);
//    }
    private void handleLogin(HttpServletRequest request, HttpServletResponse response, String userType, String email, String password)
              throws ServletException, IOException {
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(600);

        if ("staff".equalsIgnoreCase(userType)) {
            Staff staff = staffDAO.checkLogin(email, password);
            if (staff != null) {
                session.setAttribute("staff", staff);
                session.setAttribute("roleId", staff.getRole().getRoleID());

                // Kiểm tra nếu mật khẩu có đúng 5 ký tự (lần đầu đăng nhập)
                if (password.length() == 5) {
                    response.sendRedirect(request.getContextPath() + "/changePassword");
                    return;
                }

                redirectBasedOnRole(response, request, staff.getRole().getRoleID());
                return;
            }
        } else if ("resident".equalsIgnoreCase(userType)) {
            Resident resident = residentDAO.checkLogin(email, password);
            if (resident != null) {
                session.setAttribute("resident", resident);
                session.setAttribute("roleId", resident.getRole().getRoleID());

                if (password.length() == 5) {
                    response.sendRedirect(request.getContextPath() + "/changePassword");
                    return;
                }

                redirectBasedOnRole(response, request, resident.getRole().getRoleID());
                return;
            }
        }
        handleLoginFailure(request, response, userType, email, password);
    }

    private void redirectBasedOnRole(HttpServletResponse response, HttpServletRequest request, int roleID) throws IOException {
        switch (roleID) {
            case 1:
                redirectToPage(response, request, "/manager/home");
                break;
            case 2:
                redirectToPage(response, request, "/administrative/home");
                break;
            case 3:
                redirectToPage(response, request, "/accountant/home");
                break;
            case 4:
                redirectToPage(response, request, "/technical/home");
                break;
            case 5:
                redirectToPage(response, request, "/service/home");
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

    private void handleLoginFailure(HttpServletRequest request, HttpServletResponse response, String userType, String email, String password)
              throws ServletException, IOException {
        request.setAttribute("userType", userType);
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("error", "***Email or Password fail");
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
