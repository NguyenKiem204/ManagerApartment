/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.NewsDAO;
import dao.RequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.News;
import model.Request;
import model.Resident;

/**
 *
 * @author admin
 */
@WebServlet(name = "HomeOwnerServlet", urlPatterns = {"/owner/home"})
public class HomeOwnerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        int ownerId = resident.getResidentId();
//        Resident resident = HttpSession.
        NewsDAO newsDAO = new NewsDAO();
        RequestDAO requestDAO = new RequestDAO();
        List<News> newsList = newsDAO.selectAll(1, 3);
        List<Request> listRequest = requestDAO.selectFirstPageOfResident(ownerId);

        request.setAttribute("listrequest", listRequest);
        request.setAttribute("newsList", newsList);
//        request.getRequestDispatcher("home.jsp").forward(request, response);
//        System.out.println("Forwarded to home.jsp");
        try {
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi ra console để kiểm tra
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
