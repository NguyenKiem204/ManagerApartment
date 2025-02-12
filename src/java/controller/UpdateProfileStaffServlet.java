/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import config.FileUploadUtil;
import dao.ResidentDAO;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import model.Image;
import model.Resident;
import model.Role;
import model.Staff;

/**
 *
 * @author nkiem
 */
@MultipartConfig
@WebServlet(name = "UpdateProfileStaffServlet", urlPatterns = {"/update-profile-staff"})
public class UpdateProfileStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String userIDParam = request.getParameter("userID");
        String imageURL = request.getParameter("imageURL");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String dobParam = request.getParameter("dob");
        String sex = request.getParameter("sex");
        Integer userID = null;
        if (userIDParam != null) {
            userID = Integer.parseInt(userIDParam);
        }

        LocalDate dob = LocalDate.parse(dobParam);
        
        String newImageURL = FileUploadUtil.uploadAvatarImage(request, userIDParam);
        if (newImageURL != null) {
            imageURL = newImageURL;
        }
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        
        
        HttpSession session = request.getSession();
        if (staffDAO.selectById(userID)!= null) {
            Staff staff = Staff.builder()
                .staffId(userID)
                .image(new Image(staffDAO.selectById(userID).getImage().getImageID(), imageURL))
                .fullName(fullName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dob(dob)
                .role(new Role(staffDAO.selectById(userID).getRole().getRoleID(), staffDAO.selectById(userID).getRole().getRoleName(), ""))
                .sex(sex)
                .build();
            staffDAO.updateProfileStaff(staff);
            session.removeAttribute("staff");
            if(imageURL==null || imageURL.isEmpty())
            staff.getImage().setImageURL(staffDAO.getImageURL(userID));
            session.setAttribute("staff", staff);
        } else if (residentDAO.selectById(userID) != null) {
            Resident resident = Resident.builder()
                .residentId(userID)
                .image(new Image(residentDAO.selectById(userID).getImage().getImageID(), imageURL))
                .fullName(fullName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dob(dob)
                .role(new Role(residentDAO.selectById(userID).getRole().getRoleID(), residentDAO.selectById(userID).getRole().getRoleName(), ""))
                .sex(sex)
                .build();
            residentDAO.updateProfileResident(resident);
            session.removeAttribute("resident");
            if(imageURL==null || imageURL.isEmpty())
            resident.getImage().setImageURL(residentDAO.getImageURL(userID));
            session.setAttribute("resident", resident);
        }
        response.sendRedirect("profile-staff");
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
