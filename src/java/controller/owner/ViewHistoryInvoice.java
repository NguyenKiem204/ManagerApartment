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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import model.Invoices;
import model.Resident;

/**
 *
 * @author nguye
 */
@WebServlet(name = "ViewHistoryInvoice", urlPatterns = {"/owner/ViewHistoryInvoice"})
public class ViewHistoryInvoice extends HttpServlet {

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
            out.println("<title>Servlet ViewHistoryInvoice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewHistoryInvoice at " + request.getContextPath() + "</h1>");
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
        String page1 = request.getParameter("page1");
        request.setAttribute("page1", page1);
        try {
            InvoiceDAO Idao = new InvoiceDAO();
            ApartmentDAO adao = new ApartmentDAO();
            // Kiểm tra định dạng ngày tháng (dd/MM/yyyy)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fromDate = null;
            LocalDate dueDate = null;

            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                try {
                    fromDate = LocalDate.parse(fromDateStr, formatter);
                } catch (DateTimeParseException e) {
                    request.setAttribute("message", "Invalid From Date format. Please use dd/MM/yyyy.");
                    request.getRequestDispatcher("InvoiceManager.jsp").forward(request, response);
                    return;
                }
            }

            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                try {
                    dueDate = LocalDate.parse(dueDateStr, formatter);
                } catch (DateTimeParseException e) {
                    request.setAttribute("message", "Invalid Due Date format. Please use dd/MM/yyyy.");
                    request.getRequestDispatcher("InvoiceManager.jsp").forward(request, response);
                    return;
                }
            }

            List<Invoices> list1 = iDAO.getInvoicesByres(rs.getResidentId(), fromDate, dueDate, true);
            List<Invoices> list = new ArrayList<>();
            for (Invoices i : list1) {
                if (i.getStatus().equals("Paid")) {
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
            session.setAttribute("ListHistory", list);
            request.getRequestDispatcher("ViewHistoryInvoice.jsp").forward(request, response);
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
