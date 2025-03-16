/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import com.google.gson.Gson;
import dao.ApartmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetApartmentServlet", urlPatterns = {"/owner/GetApartmentServlet"})
public class GetApartmentServlet extends HttpServlet {

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
            out.println("<title>Servlet GetApartmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetApartmentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
         response.setContentType("application/json;charset=UTF-8");
        String searchQuery = request.getParameter("search");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            searchQuery = "";  // Đặt thành chuỗi rỗng nếu không có dữ liệu
        } else {
            searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        }
        // Lấy dữ liệu từ Database
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        List<String> apartmentList;
        try {
            apartmentList = apartmentDAO.getApartmentNames(searchQuery);
        } catch (Exception e) {
            apartmentList = List.of(); // Trả về danh sách rỗng nếu có lỗi
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // Xuất dữ liệu JSON
        try (PrintWriter out = response.getWriter()) {
            out.print(new Gson().toJson(apartmentList));
            out.flush();
        }
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
        processRequest(request, response);
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
