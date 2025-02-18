/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.ApartmentDAO;
import dao.InvoiceDAO;
import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.InvoiceDetail;
import model.Invoices;
import model.Resident;
import model.TypeBill;

/**
 *
 * @author nguye
 */
@WebServlet(name = "EditInvoice", urlPatterns = {"/editinvoice"})
public class EditInvoice extends HttpServlet {

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
            out.println("<title>Servlet EditInvoice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditInvoice at " + request.getContextPath() + "</h1>");
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

        String idinv = request.getParameter("invoiceID");
        try {
            int id = Integer.parseInt(idinv);
            InvoiceDAO idao = new InvoiceDAO();
            Invoices inv = idao.selectById(id);

            List<TypeBill> lt = idao.getAllTypeBills();
            request.setAttribute("listType", lt);
            request.setAttribute("invoice", inv);
            String error = (String) request.getAttribute("error");
            String dueDateError = (String) request.getAttribute("dueDateError");
            String amountError = (String) request.getAttribute("amountError");
            String errorde = (String) request.getAttribute("errorde");
            String detailError = (String) request.getAttribute("detailError");

            if (error != null) {
                request.setAttribute("error", error);
            }
            if (dueDateError != null) {
                request.setAttribute("dueDateError", dueDateError);
            }
            if (amountError != null) {
                request.setAttribute("amountError", amountError);
            }
            if (detailError != null) {
                request.setAttribute("detailError", detailError);
            }
            if (errorde != null) {
                request.setAttribute("errorde", errorde);
            }
            request.getRequestDispatcher("accountant/EditInvoice.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");

        try {
            String description = request.getParameter("description");
            String dueDateStr = request.getParameter("dueDate");
            String invoiceiDsc = request.getParameter("invoiceId");
            if (invoiceiDsc == null || invoiceiDsc.isEmpty()
                    || description == null || description.trim().isEmpty()
                    || dueDateStr == null || dueDateStr.isEmpty()) {
                request.setAttribute("errorde", "Missing required parameters");
                doGet(request, response);
                return;
            }
            int invoiceid;
            LocalDate dueDate;
            try {
                invoiceid = Integer.parseInt(invoiceiDsc);
                dueDate = LocalDate.parse(dueDateStr);
            } catch (NumberFormatException | DateTimeParseException e) {
                request.setAttribute("dueDateError", "Invalid date format (MM/DD/YYYY)");
                doGet(request, response);
                return;
            }

            // Lấy danh sách chi tiết hóa đơn từ request
            String[] typeBillIds = request.getParameterValues("typebills");
            String[] amounts = request.getParameterValues("amount");
            String[] descriptions = request.getParameterValues("descriptionde");
            if (typeBillIds == null || amounts == null || descriptions == null) {
                request.setAttribute("detailError", "Invoice must have at least one valid detail");
                doGet(request, response);
            }

            // Tạo danh sách chi tiết hóa đơn và tính tổng số tiền
            List<InvoiceDetail> details = new ArrayList<>();
            double totalAmount = 0.0;

            for (int i = 0; i < typeBillIds.length; i++) {
                try {
                    int typeBillId = Integer.parseInt(typeBillIds[i]);
                    double amount = Double.parseDouble(amounts[i]);
                    String desc = descriptions[i];
                    if (desc.trim().isEmpty()) {
                        request.setAttribute("detailError", "Description detail mustn't empty or only space");
                        doGet(request, response);
                        return;
                    }
                    if (amount <= 0) {
                        request.setAttribute("amountError", "Amount cannot be negative");
                        doGet(request, response);
                        return;
                    }
                    if (amount > 0) {
                        details.add(new InvoiceDetail(amount, desc, typeBillId));
                        totalAmount += amount;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("amountError", "Invalid number format in invoice details");
                    doGet(request, response);
                    return;
                }
            }

            // Kiểm tra xem có ít nhất một chi tiết hóa đơn hợp lệ không
            if (details.isEmpty()) {
                request.setAttribute("detailError", "Invoice must have at least one valid detail");
                doGet(request, response);
                return;
            }

            Invoices invoice = new Invoices(invoiceid, totalAmount, description, dueDate, details);
            InvoiceDAO iDAO = new InvoiceDAO();
            iDAO.updateInvoice(invoice);
            iDAO.deleteInvoiceDetail(invoice.getInvoiceID());

            for (InvoiceDetail detail : details) {
                iDAO.insertInvoiceDetail(invoice.getInvoiceID(), detail);
            }

            response.sendRedirect("InvoicesManager");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred: " + e.getMessage());
            doGet(request, response);
        }
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
