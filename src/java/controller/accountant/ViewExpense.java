/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.ApartmentDAO;
import dao.ExpenseDAO;
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
import java.util.regex.Pattern;
import model.Expense;
import model.Invoices;
import model.TypeExpense;

/**
 *
 * @author nguye
 */
@WebServlet(name = "ViewExpense", urlPatterns = {"/accountant/ViewExpense"})
public class ViewExpense extends HttpServlet {

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
            out.println("<title>Servlet ViewExpense</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewExpense at " + request.getContextPath() + "</h1>");
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
        String status = request.getParameter("status");
        String fromDateStr = request.getParameter("FromDate");
        String dueDateStr = request.getParameter("dueDate");
        String search = request.getParameter("search");
        String typeexp = request.getParameter("typeid");

        try {
            ExpenseDAO Edao = new ExpenseDAO();
            LocalDate fromDate = (fromDateStr != null && !fromDateStr.isEmpty())
                    ? LocalDate.parse(fromDateStr)
                    : null;
            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty())
                    ? LocalDate.parse(dueDateStr)
                    : null;
            int typeId = 0; // Giá trị mặc định
            if (typeexp != null && !typeexp.isEmpty()) {
                try {
                    typeId = Integer.parseInt(typeexp);
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid expense type");
                }
            }
            List<Expense> list = Edao.filterExpenses(status, typeId, fromDate, dueDate);
            if (search != null && !search.isEmpty()) {
                search = search.trim().replaceAll("\\s+", " ").replace('"', '\'');
                if (!Pattern.matches("^[a-zA-Z0-9 ,.!?'-]+$", search)) {
                    request.setAttribute("message", "Invalid search input");
                } else {
//                    List<Expense> searchResults = new ArrayList<>();
//                    for (Expense exp : list) {
//                        if (exp.getDescription().contains(search)) {
//                            searchResults.add(exp);
//                        }
//                    }
//                    list = searchResults;
                }
            }
            List<TypeExpense> lte = Edao.getAllTypeExpenses();
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
            request.setAttribute("lte", lte);
            request.setAttribute("typeid", typeId);
            request.setAttribute("search", search);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedFromDate", fromDateStr);
            request.setAttribute("selectedDueDate", dueDateStr);
            session.setAttribute("ListExpense", list);
            request.getRequestDispatcher("ViewExpense.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
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
