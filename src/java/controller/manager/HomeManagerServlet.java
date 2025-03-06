/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ApartmentDAO;
import dao.MessageDAO;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import model.Resident;
import model.Staff;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "HomeManagerServlet", urlPatterns = {"/manager/home"})
public class HomeManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
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
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        int numberAptm = apartmentDAO.numberApartment();
        int numberResident = residentDAO.numberResident();
        int numberApartmentOccupied = apartmentDAO.numberApartmentOccupied();
        int numberApartmentAvailable = apartmentDAO.numberApartmentAvailable();
        request.setAttribute("numberApartment", numberAptm);
        request.setAttribute("numberResident", numberResident);
        request.setAttribute("numberApartmentOccupied", numberApartmentOccupied);
        request.setAttribute("numberApartmentAvailable", numberApartmentAvailable);
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        String currentUserEmail = staff.getEmail();
        StaffDAO staffDAO = new StaffDAO();
        MessageDAO messageDAO = new MessageDAO();

        List<Staff> listStaff = staffDAO.selectAllSortedByLastMessage(currentUserEmail);
        List<Resident> listResident = residentDAO.selectAllSortedByLastMessage(currentUserEmail);

        listStaff.removeIf(s -> s.getEmail().equals(currentUserEmail));
        listResident.removeIf(r -> r.getEmail().equals(currentUserEmail));

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(listStaff);
        combinedList.addAll(listResident);

        Map<String, java.sql.Timestamp> lastMessageMap = messageDAO.getLastMessageTimestamps(currentUserEmail);
        combinedList.sort(Comparator.comparing(o -> {
            String email = (o instanceof Staff) ? ((Staff) o).getEmail() : ((Resident) o).getEmail();
            return lastMessageMap.getOrDefault(email, Timestamp.valueOf("1970-01-01 00:00:00"));
        }, Comparator.reverseOrder()));
        List<Object> list = combinedList.subList(0, combinedList.size());
        request.setAttribute("list", list);
        request.getRequestDispatcher("home.jsp").forward(request, response);
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
