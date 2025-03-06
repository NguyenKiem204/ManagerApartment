package controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Resident;
import model.Staff;

@WebServlet("/redirect/home")
public class HomeRedirectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String homeUrl = request.getContextPath() + "/home";

        if (session != null) {
            Resident resident = (Resident) session.getAttribute("resident");
            Staff staff = (Staff) session.getAttribute("staff");

            Integer roleId = null;
            if (resident != null && resident.getRole() != null) {
                roleId = resident.getRole().getRoleID();
            } else if (staff != null && staff.getRole() != null) {
                roleId = staff.getRole().getRoleID();
            }
            System.out.println("RoleId = "+roleId);
            if (roleId != null) {
                switch (roleId) {
                    case 1: homeUrl = request.getContextPath() + "/manager/home"; break;
                    case 2: homeUrl = request.getContextPath() + "/administrative/home"; break;
                    case 3: homeUrl = request.getContextPath() + "/accountant/home"; break;
                    case 4: homeUrl = request.getContextPath() + "/technical/home"; break;
                    case 5: homeUrl = request.getContextPath() + "/service/home"; break;
                    case 6: homeUrl = request.getContextPath() + "/tenant/home"; break;
                    case 7: homeUrl = request.getContextPath() + "/owner/home"; break;
                    default: homeUrl = request.getContextPath() + "/login";
                }
            }
        }
        System.out.println("HomeURL: " + homeUrl);
        response.sendRedirect(homeUrl);
    }
}
