/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package controller.auth;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Resident;
import model.Staff;

/**
 *
 * @author nkiem
 */
public class AdministrativeFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;
        HttpSession session = rq.getSession(false);

        Resident resident = (session != null) ? (Resident) session.getAttribute("resident") : null;
        if (resident != null) {
            forwardToErrorPage(rq, rs, 403, "You do not have permission to access this page as a Resident.");
            return;
        }

        Staff staff = (session != null) ? (Staff) session.getAttribute("staff") : null;
        if (staff == null) {
            forwardToErrorPageAuth(rq, rs, 401, "Please log in to continue.");
            return;
        }

        Integer roleId = (staff.getRole() != null) ? staff.getRole().getRoleID() : null;
        if (roleId == null || roleId != 2) {
            forwardToErrorPage(rq, rs, 403, "You do not have permission to access this page.");
            return;
        }

        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            forwardToErrorPage(rq, rs, 500, "An error occurred: " + t.getMessage());
        }
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response,
            int statusCode, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
    }

    private void forwardToErrorPageAuth(HttpServletRequest request, HttpServletResponse response, int statusCode, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error-authorization.jsp").forward(request, response);
    }
    
}
