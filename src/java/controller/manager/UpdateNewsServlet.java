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
import model.Image;
import model.News;

/**
 *
 * @author nkiem
 */
@MultipartConfig
@WebServlet(name="UpdateNewsServlet", urlPatterns={"/manager/updatenews"})
public class UpdateNewsServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<h1>Servlet UpdateNewsServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        Integer newsID = null;
        Integer staffId = null;
        if(newsIDParam!=null){
            newsID = Integer.parseInt(newsIDParam);
        }
        if (staffIdParam != null && !staffIdParam.isEmpty()) {
            staffId = Integer.parseInt(staffIdParam);
        }
        NewsDAO newsDAO = new NewsDAO();
        StaffDAO staffDAO = new StaffDAO();
        String newImageURL = FileUploadUtil.insertNewsImage(request);
        News oldNews = newsDAO.selectById(newsID);
        if (newImageURL != null) {
            imageURL = newImageURL;
        }
        News news = News.builder()
                .newsID(newsID)
                .staff(staffDAO.selectById(staffId))
                .image(new Image(oldNews.getImage().getImageID(),imageURL))
                .title(title)
                .description(description)
                .sentDate(LocalDateTime.now())
                .build();
        newsDAO.updateNewsWithImage(news);
        response.sendRedirect(request.getContextPath()+"/manager/managernews");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
