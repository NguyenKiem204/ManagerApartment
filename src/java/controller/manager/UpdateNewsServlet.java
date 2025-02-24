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
@WebServlet(name = "UpdateNewsServlet", urlPatterns = {"/manager/updatenews"})
public class UpdateNewsServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateNewsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateNewsServlet at " + request.getContextPath() + "</h1>");
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
        String newsIDParam = request.getParameter("newsID");
        String title = request.getParameter("title");
        String imageURL = request.getParameter("imageURL");
        String description = request.getParameter("description");
        String staffIdParam = request.getParameter("staffId");
        String url = request.getParameter("url");
        System.out.println(url);
        Integer newsID = null;
        Integer staffId = null;
        NewsDAO newsDAO = new NewsDAO();
        StaffDAO staffDAO = new StaffDAO();
        if (newsIDParam != null) {
            newsID = Integer.parseInt(newsIDParam);
        }
        if (staffIdParam != null && !staffIdParam.isEmpty()) {
            staffId = Integer.parseInt(staffIdParam);
        }
        List<String> errors = new ArrayList<>();

        if (title == null || title.trim().isEmpty()) {
            errors.add("Title cannot be empty!");
        }

        if (description == null || description.isBlank() || description.isEmpty()) {
            errors.add("Description cannot empty!");
        }

        String newImageURL = FileUploadUtil.insertNewsImage(request);
        News oldNews = newsDAO.selectById(newsID);
        if (newImageURL != null) {
            if (!isValidImage(newImageURL)) {
                errors.add("Invalid image format. Only JPG, PNG, and JPEG, GIF, WEBP are allowed.");
            } else {
                imageURL = newImageURL;
            }
        }
        if (!errors.isEmpty()) {
            request.getSession().setAttribute("errors", errors);

            response.sendRedirect(url);
            return;
        } else {
            request.getSession().removeAttribute("errors");
        }
        News news = News.builder()
                .newsID(newsID)
                .staff(staffDAO.selectById(staffId))
                .image(new Image(oldNews.getImage().getImageID(), imageURL))
                .title(Validate.trim(title))
                .description(description)
                .build();
        newsDAO.updateNewsWithImage(news);
        response.sendRedirect(url);
    }

    private boolean isValidImage(String imageUrl) {
        String extension = getFileExtension(imageUrl);
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")
                || extension.equals("gif") || extension.equals("webp");
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
