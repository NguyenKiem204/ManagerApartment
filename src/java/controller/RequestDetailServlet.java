/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Request;
import model.Resident;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "RequestDetailServlet", urlPatterns = {"/requestdetail"})
public class RequestDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet RequestDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestDetailServlet at " + request.getContextPath() + "</h1>");
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
        String requestId_raw = request.getParameter("requestId");

        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        RequestDAO requestDAO = new RequestDAO();
        int requestId = 0;
        Request requestObj = null;
        //owner theo doi request cua no
        //manager co the coi all request detail
        //staff chi co the coi request detail ma no quan ly va kp la pending va cancel
        try {
            requestId = Integer.parseInt(requestId_raw);
            requestObj = requestDAO.selectById(requestId);

            // Kiểm tra nếu request không tồn tại
            if (requestObj == null) {
                request.setAttribute("errorCode", "404");
                request.setAttribute("errorMessage", "Request not found!");
                request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
                return;
            }

            // **1. Nếu là Owner** → Chỉ xem request của chính họ
            if (resident != null && resident.getRole().getRoleID() == 7) {
                if (requestObj.getResident().getResidentId() != resident.getResidentId()) {
                    request.setAttribute("errorCode", "403");
                    request.setAttribute("errorMessage", "You do not have permission to access this request!");
                    request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
                    return;
                }
            }

            // **2. Nếu là Staff**
            if (staff != null) {
                int roleId_ss = staff.getRole().getRoleID();

                if (roleId_ss != 1) { // Nếu không phải Manager
                    // Chỉ xem được request mà họ quản lý
                    if (requestObj.getTypeRq().getRole().getRoleID() != roleId_ss) {
                        request.setAttribute("errorCode", "403");
                        request.setAttribute("errorMessage", "You do not have permission to access this request!");
                        request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
                        return;
                    }

                    // Không được xem request có trạng thái "Pending" hoặc "Cancel"
                    String status = requestObj.getStatus().getStatusName();
                    if ("Pending".equalsIgnoreCase(status) || "Cancel".equalsIgnoreCase(status)) {
                        request.setAttribute("errorCode", "403");
                        request.setAttribute("errorMessage", "You do not have permission to access this request!");
                        request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
                        return;
                    }
                }
            }
            System.out.println("request obj: " + requestObj.toString());
            request.setAttribute("request", requestObj);
        } catch (NumberFormatException e) {
        }
        request.getRequestDispatcher("requestdetail.jsp").forward(request, response);
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
