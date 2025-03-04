/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.ApartmentDAO;
import dao.RequestDAO;
import dao.StaffDAO;
import dao.StatusRequestDAO;
import dao.TypeRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.Apartment;
import model.Request;
import model.Resident;
import model.TypeRequest;
import org.jsoup.Jsoup;
import validation.Validate;

/**
 *
 * @author admin
 */
@WebServlet(name = "RequestServlet", urlPatterns = {"/owner/request"})
public class RequestServlet extends HttpServlet {

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
            out.println("<title>Servlet RequestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestServlet at " + request.getContextPath() + "</h1>");
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
        TypeRequestDAO typeRequestDAO = new TypeRequestDAO();
        List<TypeRequest> listrq = typeRequestDAO.selectAll();
        request.setAttribute("listtyperq", listrq);
        request.getRequestDispatcher("request.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident"); // Loi vi dang xung dot resident va resident detail

        StatusRequestDAO statusRequestDAO = new StatusRequestDAO();
        StaffDAO staffDAO = new StaffDAO();
        TypeRequestDAO typeRequestDAO = new TypeRequestDAO();
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        RequestDAO rqDAO = new RequestDAO();
        
        List<TypeRequest> listrq = typeRequestDAO.selectAll();

        String apartmentName = request.getParameter("apartment");
        String title = request.getParameter("title");
        String typerq_raw = request.getParameter("service");
        String description = request.getParameter("description");
        // Loại bỏ HTML & ký tự khoảng trắng
        String cleanText = Jsoup.parse(description).text().trim();
        String error = "error";
        //check apartment name is correct or not
        List<Apartment> apartments = apartmentDAO.selectAll();
        
        if (apartmentName == null || apartmentName.trim().isEmpty()) {
            request.setAttribute(error, "Apartment Name cannot be empty!");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }
        
        boolean isValidApartment = false;
        for (Apartment apartment : apartments) {
            if (apartment.getApartmentName().trim().replaceAll("\\s+", " ").equalsIgnoreCase(apartmentName.trim().replaceAll("\\s+", " "))) {
                isValidApartment = true;
                break;
            }
        }
        if (!isValidApartment) {
            request.setAttribute(error, "Apartment name incorrect. Please enter a valid apartment name.");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }
        
        //check title empty or not
        if (title != null) {
            title = title.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
        }

        if (title == null || title.trim().isEmpty()) {
            request.setAttribute(error, "Title cannot be empty!");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }
        
        if (!Validate.isValidTitle(title)) {
            request.setAttribute(error, "Title contains invalid characters!");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ dài title
        if (title.length() < 5 || title.length() > 100) {
            request.setAttribute(error, "Title must be between 5 and 100 characters!");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }
        
        //check description empty or not
        if (description != null) {
            description = description.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
        }
//        if (!description.matches("^[a-zA-Z0-9 .,!?()-]+$")) {
//            request.setAttribute(error, "Description contains invalid characters!");
//            request.setAttribute("listtyperq", listrq);
//            request.getRequestDispatcher("request.jsp").forward(request, response);
//            return;
//        }
        //check description null or not
        if (cleanText == null || cleanText.trim().isEmpty()) {
            request.setAttribute(error, "Description cannot be empty!");
            request.setAttribute("listtyperq", listrq);
            request.getRequestDispatcher("request.jsp").forward(request, response);
            return;
        }

        int typerq;

        try {
            typerq = Integer.parseInt(typerq_raw);
            Apartment apartment = apartmentDAO.getApartmentByName(apartmentName);
            Request rq = new Request(description, title, LocalDate.now(), statusRequestDAO.selectById(1), resident, typeRequestDAO.selectById(typerq), apartmentDAO.getApartmentByName(apartmentName));
            System.out.println(rq.toString());
            int row = rqDAO.insert(rq);
            if (row != 0) {
                request.setAttribute("msg", "Submit form success");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid service type");
        }
        request.setAttribute("listtyperq", listrq);
        request.getRequestDispatcher("request.jsp").forward(request, response);
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
