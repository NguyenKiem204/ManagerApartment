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

public class OwnerFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;
        HttpSession session = rq.getSession(false);
        
        Staff staff = (session != null) ? (Staff) session.getAttribute("staff") : null;
        if (staff != null) {
           forwardToErrorPage(rq, rs, 403, "You do not have permission to access this resource.");
            return;
        }
        
        Resident resident = (session != null) ? (Resident) session.getAttribute("resident") : null;
        if (resident == null) {
            forwardToErrorPageAuth(rq, rs, 401, "Please log in to continue.");
            return;
            
        }
        
        Integer roleId = (resident.getRole() != null) ? resident.getRole().getRoleID() : null;
        if (roleId == null || roleId != 7) {
            forwardToErrorPage(rq, rs, 403, "You do not have permission to access this resource.");
            return;
        }
        
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            forwardToErrorPage(rq, rs, 500, "An unexpected error occurred: " + t.getMessage());
        }
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response, int statusCode, String errorMessage) throws ServletException, IOException {
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
