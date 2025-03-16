/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;


import dao.NewsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.News;

/**
 *
 * @author nkiem
 */
@WebServlet(name="NewsServlet", urlPatterns={"/news"})
public class NewsServlet extends HttpServlet {
   
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
            out.println("<title>Servlet NewsServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    int pageSize = 6;
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        String searchTitle = validation.Validate.normalizeString(request.getParameter("searchTitle"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        NewsDAO newsDAO = new NewsDAO();
        List<News> newsList;
        int totalRecords;

        if (searchTitle != null || startDate != null || endDate != null) {
            newsList = newsDAO.searchNews(searchTitle, startDate, endDate, page, pageSize);
            totalRecords = newsDAO.getTotalSearchRecords(searchTitle, startDate, endDate);
        } else {
            newsList = newsDAO.selectAll(page, pageSize);
            totalRecords = newsDAO.getTotalRecords();
        }
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("newsList", newsList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTitle", searchTitle);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
    request.getRequestDispatcher("/news.jsp").forward(request, response);
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
        processRequest(request, response);
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
