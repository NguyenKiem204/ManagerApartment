package controller.accountant;

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
import java.util.regex.Pattern;
import model.Apartment;
import model.Invoices;

@WebServlet(name = "UpdateStatusInvoice", urlPatterns = {"/accountant/UpdateStatusInvoice"})
public class UpdateStatusInvoice extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String fromDateStr = request.getParameter("FromDate");
        String dueDateStr = request.getParameter("dueDate");
        String search = request.getParameter("search");
        String invidstr= request.getParameter("invoiceID");

        try {
            InvoiceDAO Idao = new InvoiceDAO();
            ApartmentDAO adao = new ApartmentDAO();
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
            if(invidstr!=null && !invidstr.isEmpty()){
                int id = Integer.parseInt(invidstr);
                Invoices inv= Idao.selectById(id);
                request.setAttribute("inv", inv);
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

            List<Invoices> list = Idao.filterInvoices("UnPaid", fromDate, dueDate);

            if (search != null && !search.isEmpty()) {
                search = search.trim().replaceAll("\\s+", " ").replace('"', '\'');
                if (!Pattern.matches("^[a-zA-Z0-9 ,.!?'-]+$", search)) {
                    request.setAttribute("message", "Invalid search input");
                } else {
                    List<Invoices> searchResults = new ArrayList<>();
                    for (Invoices inv : list) {
                        if (inv.getDescription().toLowerCase().contains(search.toLowerCase())
                                || inv.getApartment().getApartmentName().toLowerCase().contains(search.toLowerCase())) {
                            searchResults.add(inv);
                        }
                    }
                    list = searchResults;
                }
            }
            String page = request.getParameter("page");
            if (page == null) {
                page = "1";
            }
            InvoiceDAO u = new InvoiceDAO();
            int totalPage = u.getTotalPage(list, 8);

            if (list.size() != 0) {
                list = u.getListPerPage(list, 8, page);
                request.setAttribute("apartmentes", list);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("currentPage", Integer.parseInt(page));
                request.setAttribute("isFilter", "true");
            } else {
                request.setAttribute("totalPage", 1);
                request.setAttribute("currentPage", 1);
                request.setAttribute("message", "No result");
            }
            request.setAttribute("search", search);
            request.setAttribute("selectedFromDate", fromDateStr);
            request.setAttribute("selectedDueDate", dueDateStr);
            session.setAttribute("ListInvoices", list);
            request.getRequestDispatcher("UpdateStatusInvoice.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
