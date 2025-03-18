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
@WebServlet(name = "ViewInvoice", urlPatterns = {"/owner/ViewInvoice"})
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
        Resident resident = (Resident) session.getAttribute("resident");

        // Kiểm tra nếu không có cư dân đăng nhập
        if (resident == null) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You need to log in to access this page!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        // Kiểm tra nếu cư dân không có quyền xem hóa đơn (ví dụ: role không phải là cư dân)
        if (resident.getRole().getRoleID() != 7) { // Giả sử roleID của cư dân là 7
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access this page!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }

        InvoiceDAO iDAO = new InvoiceDAO();
        String fromDateStr = request.getParameter("FromDate");
        String dueDateStr = request.getParameter("dueDate");
        String search = request.getParameter("search");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fromDate = null;
            LocalDate dueDate = null;

            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                try {
                    fromDate = LocalDate.parse(fromDateStr, formatter);
                } catch (DateTimeParseException e) {
                    request.setAttribute("message", "Invalid From Date format. Please use dd/MM/yyyy.");
                    request.getRequestDispatcher("ViewInvoice.jsp").forward(request, response);
                    return;
                }
            }

            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                try {
                    dueDate = LocalDate.parse(dueDateStr, formatter);
                } catch (DateTimeParseException e) {
                    request.setAttribute("message", "Invalid Due Date format. Please use dd/MM/yyyy.");
                    request.getRequestDispatcher("ViewInvoice.jsp").forward(request, response);
                    return;
                }
            }

            // Lấy danh sách hóa đơn của cư dân đang đăng nhập
            List<Invoices> list1 = iDAO.getInvoicesByres(resident.getResidentId(), fromDate, dueDate, false);
            List<Invoices> list = new ArrayList<>();

            // Chỉ lấy các hóa đơn chưa thanh toán
            for (Invoices i : list1) {
                if (i.getStatus().equals("Unpaid")) {
                    list.add(i);
                }
            }

            // Xử lý tìm kiếm
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

            // Phân trang
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

            // Đặt các thuộc tính vào request để hiển thị trên JSP
            request.setAttribute("search", search);
            request.setAttribute("selectedFromDate", fromDateStr);
            request.setAttribute("selectedDueDate", dueDateStr);
            session.setAttribute("ListInvoiceOwner", list);
            request.getRequestDispatcher("ViewInvoice.jsp").forward(request, response);
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
