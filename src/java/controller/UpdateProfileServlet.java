/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import config.FileUploadUtil;
import dao.ResidentDAO;
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
import java.util.List;
import jakarta.servlet.http.Part;

/**
 *
 * @author nkiem
 */
@MultipartConfig
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/update-profile"})
public class UpdateProfileServlet extends HttpServlet {

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
    private String getSubmittedFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

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

        Integer userID = (userIDParam != null) ? Integer.parseInt(userIDParam) : null;
        LocalDate dob = LocalDate.parse(dobParam);
        Part filePart = request.getPart("imgURL");

        List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "webp");

        if (filePart != null && filePart.getSize() > 0) {
            String mimeType = filePart.getContentType();
            String fileName = getSubmittedFileName(filePart);
            String fileExtension = getFileExtension(fileName);

            if (allowedMimeTypes.contains(mimeType) && allowedExtensions.contains(fileExtension)) {
                String newImageURL = FileUploadUtil.uploadAvatarImage(request, userIDParam);
                if (newImageURL != null) {
                    imageURL = newImageURL;
                }
            }
        }

        ResidentDAO residentDAO = new ResidentDAO();
        HttpSession session = request.getSession();

        Resident oldResident = residentDAO.selectById(userID);
        if (oldResident != null) {
            Resident resident = Resident.builder()
                    .residentId(userID)
                    .image(new Image(oldResident.getImage().getImageID(), imageURL))
                    .fullName(fullName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .dob(dob)
                    .role(new Role(oldResident.getRole().getRoleID(), oldResident.getRole().getRoleName(), ""))
                    .sex(sex)
                    .build();
            residentDAO.updateProfileResident(resident);

            if (imageURL == null || imageURL.isEmpty()) {
                imageURL = oldResident.getImage().getImageURL();
            }
            resident.getImage().setImageURL(imageURL);
            session.setAttribute("resident", resident);
        }

        response.sendRedirect("profile");
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
