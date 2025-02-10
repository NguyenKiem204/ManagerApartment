/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

<<<<<<< HEAD
=======
import dao.NewsDAO;
>>>>>>> 3ca1e80d4dfacfa3f1b50e2a598feea2c78129b6
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 3ca1e80d4dfacfa3f1b50e2a598feea2c78129b6
import java.util.ArrayList;
import java.util.List;
import model.NewsDetail;

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
    private List<NewsDetail> getNewsFromDatabase() {
    List<NewsDetail> newsList = new ArrayList<>();
<<<<<<< HEAD
    newsList.add(new NewsDetail(1, 101, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 1", "Mô tả ngắn cho tin tức 1.", LocalDate.now(), 201, "Nguyễn Văn A"));
    newsList.add(new NewsDetail(2, 102, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 2", "Mô tả ngắn cho tin tức 2.", LocalDate.now(), 202, "Trần Thị B"));
    newsList.add(new NewsDetail(3, 103, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 3", "Mô tả ngắn cho tin tức 3.", LocalDate.now(), 203, "Lê Văn C"));
    newsList.add(new NewsDetail(4, 104, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 4", "Mô tả ngắn cho tin tức 4.", LocalDate.now(), 204, "Phạm Thị D"));
    newsList.add(new NewsDetail(5, 105, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 5", "Mô tả ngắn cho tin tức 5.", LocalDate.now(), 205, "Hoàng Văn E"));
    newsList.add(new NewsDetail(6, 106, "https://via.placeholder.com/300x200", "Tin tức 6", "Mô tả ngắn cho tin tức 6.", LocalDate.now(), 206, "Đặng Thị F"));
    newsList.add(new NewsDetail(7, 107, "https://via.placeholder.com/300x200", "Tin tức 7", "Mô tả ngắn cho tin tức 7.", LocalDate.now(), 207, "Ngô Văn G"));
    newsList.add(new NewsDetail(8, 108, "https://via.placeholder.com/300x200", "Tin tức 8", "Mô tả ngắn cho tin tức 8.", LocalDate.now(), 208, "Bùi Thị H"));
    newsList.add(new NewsDetail(9, 109, "https://via.placeholder.com/300x200", "Tin tức 9", "Mô tả ngắn cho tin tức 9.", LocalDate.now(), 209, "Vũ Văn I"));
    newsList.add(new NewsDetail(10, 110, "https://via.placeholder.com/300x200", "Tin tức 10", "Mô tả ngắn cho tin tức 10.", LocalDate.now(), 210, "Dương Thị K"));
=======
    newsList.add(new NewsDetail(1, 101, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 1", "Mô tả ngắn cho tin tức 1.", LocalDateTime.now(), 201, "Nguyễn Văn A"));
    newsList.add(new NewsDetail(2, 102, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 2", "Mô tả ngắn cho tin tức 2.", LocalDateTime.now(), 202, "Trần Thị B"));
    newsList.add(new NewsDetail(3, 103, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 3", "Mô tả ngắn cho tin tức 3.", LocalDateTime.now(), 203, "Lê Văn C"));
    newsList.add(new NewsDetail(4, 104, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 4", "Mô tả ngắn cho tin tức 4.", LocalDateTime.now(), 204, "Phạm Thị D"));
    newsList.add(new NewsDetail(5, 105, "https://images.unsplash.com/photo-1738447429433-69e3ecd0bdd0?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Tin tức 5", "Mô tả ngắn cho tin tức 5.", LocalDateTime.now(), 205, "Hoàng Văn E"));
    newsList.add(new NewsDetail(6, 106, "https://via.placeholder.com/300x200", "Tin tức 6", "Mô tả ngắn cho tin tức 6.", LocalDateTime.now(), 206, "Đặng Thị F"));
    newsList.add(new NewsDetail(7, 107, "https://via.placeholder.com/300x200", "Tin tức 7", "Mô tả ngắn cho tin tức 7.", LocalDateTime.now(), 207, "Ngô Văn G"));
    newsList.add(new NewsDetail(8, 108, "https://via.placeholder.com/300x200", "Tin tức 8", "Mô tả ngắn cho tin tức 8.", LocalDateTime.now(), 208, "Bùi Thị H"));
    newsList.add(new NewsDetail(9, 109, "https://via.placeholder.com/300x200", "Tin tức 9", "Mô tả ngắn cho tin tức 9.", LocalDateTime.now(), 209, "Vũ Văn I"));
    newsList.add(new NewsDetail(10, 110, "https://via.placeholder.com/300x200", "Tin tức 10", "Mô tả ngắn cho tin tức 10.", LocalDateTime.now(), 210, "Dương Thị K"));
>>>>>>> 3ca1e80d4dfacfa3f1b50e2a598feea2c78129b6
    
    return newsList;
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
        int page = 1;
    int newsPerPage = 6;

    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }
<<<<<<< HEAD

    List<NewsDetail> newsList = getNewsFromDatabase();
=======
        NewsDAO newsDAO = new NewsDAO();
    List<NewsDetail> newsList = newsDAO.getAllNews();
>>>>>>> 3ca1e80d4dfacfa3f1b50e2a598feea2c78129b6
    int totalNews = newsList.size();
    int totalPages = (int) Math.ceil((double) totalNews / newsPerPage);

    int startIndex = (page - 1) * newsPerPage;
    int endIndex = Math.min(startIndex + newsPerPage, totalNews);

    List<NewsDetail> newsForPage = newsList.subList(startIndex, endIndex);

    request.setAttribute("newsList", newsForPage);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);

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
