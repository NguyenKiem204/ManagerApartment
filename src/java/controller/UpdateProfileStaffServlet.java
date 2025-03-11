/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import config.FileUploadUtil;
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
import model.Role;
import model.Staff;
import java.util.List;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import validation.Validate;
import java.time.format.DateTimeFormatter;

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
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String dobParam = request.getParameter("dob");
        System.out.println(dobParam);
        String sex = request.getParameter("sex");

        List<String> errorMessages = new ArrayList<>();
        validateInput(fullName, phoneNumber, dobParam, errorMessages);
        String newImageURL = null;
        try {
            newImageURL = handleFileUpload(request, userIDParam, errorMessages);
        } catch (ServletException e) {
            errorMessages.add(e.getMessage());
        }
        StaffDAO staffDAO = new StaffDAO();
        Integer userID = parseUserId(userIDParam);
        Staff oldStaff = staffDAO.selectById(userID);
        if (phoneNumber.equals(oldStaff.getPhoneNumber())) {
            System.out.println("Nothing!");
        } else if (staffDAO.existPhoneNumber(phoneNumber)) {
            errorMessages.add("PhoneNumber to exist!");
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errors", errorMessages);
            request.getRequestDispatcher("changeprofilestaff.jsp").forward(request, response);
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dob = LocalDate.parse(dobParam, formatter);
        fullName = fullName.trim().replaceAll("\\s+", " ");

        HttpSession session = request.getSession();
        if (oldStaff != null) {
            Staff staff = createStaff(oldStaff, fullName, phoneNumber, dob, sex, newImageURL);
            staffDAO.updateProfileStaff(staff);
            session.setAttribute("staff", staff);
        }
        response.sendRedirect("profile-staff");
    }

    private void validateInput(String fullName, String phoneNumber, String dobParam, List<String> errorMessages) {
        String fullNameError = Validate.validateFullName(fullName);
        if (fullNameError != null) {
            errorMessages.add(fullNameError);
        }

        String phoneNumberError = Validate.validatePhoneNumber(phoneNumber);
        if (phoneNumberError != null) {
            errorMessages.add(phoneNumberError);
        }

        String dobError = Validate.validateDob(dobParam);
        if (dobError != null) {
            errorMessages.add(dobError);
        }
    }

    private String handleFileUpload(HttpServletRequest request, String userIDParam, List<String> errorMessages) throws IOException, ServletException {
        Part filePart = request.getPart("imgURL");
        List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "webp");

        if (filePart != null && filePart.getSize() > 0) {
            String mimeType = filePart.getContentType();
            String fileName = getSubmittedFileName(filePart);
            String fileExtension = getFileExtension(fileName).toLowerCase();

            if (allowedMimeTypes.contains(mimeType) && allowedExtensions.contains(fileExtension)) {
                return FileUploadUtil.uploadAvatarImage(request, userIDParam);
            } else {
                errorMessages.add("Invalid file type. Allowed types are: " + String.join(", ", allowedExtensions));
            }
        }
        return null;
    }

    private Integer parseUserId(String userIDParam) {
        try {
            return userIDParam != null ? Integer.valueOf(userIDParam) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Staff createStaff(Staff oldStaff, String fullName, String phoneNumber, LocalDate dob, String sex, String newImageURL) {
        String finalImageURL = (newImageURL != null && !newImageURL.isEmpty()) ? newImageURL : oldStaff.getImage().getImageURL();
        return Staff.builder()
                .staffId(oldStaff.getStaffId())
                .image(new Image(oldStaff.getImage().getImageID(), finalImageURL))
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .dob(dob)
                .email(oldStaff.getEmail())
                .role(new Role(oldStaff.getRole().getRoleID(), oldStaff.getRole().getRoleName(), ""))
                .sex(sex)
                .build();
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
