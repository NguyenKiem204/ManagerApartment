/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import config.FileUploadUtil;
import dao.ImageDAO;
import dao.NewsDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Image;
import model.News;
import validation.Validate;

/**
 *
 * @author nkiem
 */
@MultipartConfig
@WebServlet(name = "AddNewsServlet", urlPatterns = {"/manager/addnews"})
public class AddNewsServlet extends HttpServlet {

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
            out.println("<title>Servlet AddNewsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewsServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("addnews.jsp").forward(request, response);
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
        List<String> errors = new ArrayList<>();

        String title = request.getParameter("title");
        String imageURL = request.getParameter("imageURL");
        String description = request.getParameter("description");
        String staffIdParam = request.getParameter("staffId");

        Integer staffId = parseStaffId(staffIdParam, errors);
        validateInputs(title, description, errors);
        Part filePart = request.getPart("imageURL");
        if (filePart == null || filePart.getSize() == 0) {
            if (imageURL == null || imageURL.trim().isEmpty()) {
                errors.add("Image cannot be empty!");
            }
        } else {
            String newImageURL = handleFileUpload(request, errors);
            if (newImageURL != null) {
                imageURL = newImageURL;
            }
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("addnews.jsp").forward(request, response);
            return;
        }
        News news = News.builder()
                .staff(new StaffDAO().selectById(staffId))
                .image(new Image(imageURL))
                .title(Validate.trim(title))
                .description(description)
                .sentDate(LocalDateTime.now())
                .build();

        new NewsDAO().insertNewsWithImage(news);
        response.sendRedirect(request.getContextPath() + "/news");
    }

    private Integer parseStaffId(String staffIdParam, List<String> errors) {
        if (staffIdParam == null || staffIdParam.isEmpty()) {
            errors.add("Staff ID is required.");
            return null;
        }
        try {
            return Integer.parseInt(staffIdParam);
        } catch (NumberFormatException e) {
            errors.add("Invalid Staff ID format.");
            return null;
        }
    }

    private void validateInputs(String title, String description, List<String> errors) {
        if (title == null || title.trim().isEmpty()) {
            errors.add("Title cannot be empty!");
        }
        if (description == null || description.trim().isEmpty()) {
            errors.add("Description cannot be empty!");
        }
    }

    private String handleFileUpload(HttpServletRequest request, List<String> errors) throws IOException, ServletException {
        Part filePart = request.getPart("imageURL");
        List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "webp");

        if (filePart != null && filePart.getSize() > 0) {
            String mimeType = filePart.getContentType();
            String fileName = getSubmittedFileName(filePart);
            String fileExtension = getFileExtension(fileName).toLowerCase();

            if (!allowedMimeTypes.contains(mimeType) || !allowedExtensions.contains(fileExtension)) {
                errors.add("Invalid file type. Allowed types are: " + String.join(", ", allowedExtensions));
                return null;
            }
            return FileUploadUtil.insertNewsImage(request);
        }
        return null;
    }

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
