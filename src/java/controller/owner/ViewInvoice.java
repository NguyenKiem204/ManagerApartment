/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.ApartmentDAO;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Invoices;
import model.Resident;

/**
 *
 * @author nguye
 */
@WebServlet(name = "ViewInvoice", urlPatterns = {"/ViewInvoice"})
public class ViewInvoice extends HttpServlet {

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
            out.println("<title>Servlet ViewInvoice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewInvoice at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Resident rs = (Resident) session.getAttribute("resident");
        InvoiceDAO iDAO = new InvoiceDAO();
        String fromDateStr = request.getParameter("FromDate");
        String dueDateStr = request.getParameter("dueDate");
        String search = request.getParameter("search");
        try {
            InvoiceDAO Idao = new InvoiceDAO();
            ApartmentDAO adao = new ApartmentDAO();
            LocalDate fromDate = (fromDateStr != null && !fromDateStr.isEmpty())
                    ? LocalDate.parse(fromDateStr)
                    : null;
            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty())
                    ? LocalDate.parse(dueDateStr)
                    : null;

            List<Invoices> list1 = iDAO.getInvoicesByres(rs.getResidentId(), fromDate, dueDate,false);
            List<Invoices> list= new ArrayList<>();
            for(Invoices i: list1){
                if(i.getStatus().equals("Unpaid")){
                    list.add(i);
                }
            }
            if (search != null && !search.isEmpty()) {
                search = search.trim().replaceAll("\\s+", " ");
                List<Invoices> searchResults = new ArrayList<>();
                for (Invoices inv : list) {
                    if (inv.getDescription().toLowerCase().contains(search.toLowerCase()) || inv.getApartment().getApartmentName().toLowerCase().contains(search.toLowerCase())) {
                        searchResults.add(inv);
                    }
                }
                list = searchResults;
            }
            String page = request.getParameter("page");
            if (page == null) {
                page = "1";
            }
            InvoiceDAO u = new InvoiceDAO();
            int totalPage = u.getTotalPage(list, 8);

            if (list.size() != 0) {
                list = u.getListPerPage(list, 8, page);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("currentPage", Integer.parseInt(page));
            } else {
                request.setAttribute("message", "No result");
            }
            request.setAttribute("search", search);
            request.setAttribute("selectedFromDate", fromDateStr);
            request.setAttribute("selectedDueDate", dueDateStr);
            session.setAttribute("ListInvoiceOwner", list);
            request.getRequestDispatcher("owner/ViewInvoice.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
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
