/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.ApartmentDAO;
import dao.InvoiceDAO;
import dao.NotificationDAO;
import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Apartment;
import model.InvoiceDetail;
import model.Invoices;
import model.Notification;
import model.Resident;
import model.Staff;
import model.TypeBill;

/**
 *
 * @author nguye
 */
@WebServlet(name = "AddNewInvoices", urlPatterns = {"/accountant/addnewinvoice"})
public class AddNewInvoices extends HttpServlet {

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
            out.println("<title>Servlet AddNewInvoces</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewInvoces at " + request.getContextPath() + "</h1>");
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
        InvoiceDAO idao = new InvoiceDAO();
        ResidentDAO rdao = new ResidentDAO();
        ApartmentDAO adao = new ApartmentDAO();
        List<Apartment> la = adao.selectAllOcc();
        List<Resident> lr = rdao.selectAll();
        List<TypeBill> lt = idao.getAllTypeBills();
        request.setAttribute("listType", lt);
        request.setAttribute("listResident", lr);
        request.setAttribute("listApartment", la);
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
        request.getRequestDispatcher("AddNewInvoice.jsp").forward(request, response);
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
            //lay parameter
            String apartmentIdStr = request.getParameter("apartmentId");
            String description = request.getParameter("description");
            String dueDateStr = request.getParameter("dueDate");
            description = description.trim().replaceAll("\\s+", " ");

            description = capitalizeFirstLetter(description);

            if (!isValidDescription(description)) {
                request.setAttribute("errorde", "Description contains invalid characters.");
                doGet(request, response);
                return;
            }

            if (apartmentIdStr == null || apartmentIdStr.isEmpty()
                    || description == null || description.trim().isEmpty()
                    || dueDateStr == null || dueDateStr.isEmpty()) {
                request.setAttribute("errorde", "Missing required parameters");
                doGet(request, response);
                return;
            }
// check format
            int apartmentId;
            LocalDate dueDate;
            try {
                apartmentId = Integer.parseInt(apartmentIdStr);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dueDate = LocalDate.parse(dueDateStr, formatter);
            } catch (NumberFormatException | DateTimeParseException e) {
                request.setAttribute("dueDateError", "Invalid date format (dd/MM/yyyy)");
                doGet(request, response);
                return;
            }
            LocalDate currentDate = LocalDate.now();
            if (dueDate.isBefore(currentDate)) {
                request.setAttribute("dueDateError", "Due date cannot be before the current date");
                doGet(request, response);
                return;
            }

            ApartmentDAO adao = new ApartmentDAO();
            ResidentDAO rdao = new ResidentDAO();
            Apartment apartment = adao.selectById(apartmentId);
            Resident resident = rdao.getApartmentOwnerByDepartment(apartmentId);

            if (apartment == null || resident == null) {
                request.setAttribute("error", "Apartment or resident not found");
                doGet(request, response);
                return;
            }
            String[] typeBillIds = request.getParameterValues("typebills");
            String[] amounts = request.getParameterValues("amount");
            String[] descriptions = request.getParameterValues("descriptionde");
            if (typeBillIds == null || amounts == null || descriptions == null) {
                request.setAttribute("detailError", "Invoice must have at least one valid detail");
                doGet(request, response);
            }

            List<InvoiceDetail> details = new ArrayList<>();
            double totalAmount = 0.0;

            for (int i = 0; i < typeBillIds.length; i++) {
                try {
                    int typeBillId = Integer.parseInt(typeBillIds[i]);
                    if (!amounts[i].matches("^[0-9]+(\\.[0-9]+)?$")) {
                        request.setAttribute("amountError", "Invalid amount format. Example: 10.5, 100");
                        doGet(request, response);
                        return;
                    }

                    double amount = Double.parseDouble(amounts[i]);

                    String desc = descriptions[i].trim().replaceAll("\\s+", " ");
                    desc = capitalizeFirstLetter(desc);

                    if (!isValidDescription(desc)) {
                        request.setAttribute("errorde", "Description contains invalid characters.");
                        doGet(request, response);
                        return;
                    }

                    if (!isValidDescription(desc)) {
                        request.setAttribute("errorde", "Description contains invalid characters.");
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
            if (details.isEmpty()) {
                request.setAttribute("detailError", "Invoice must have at least one valid detail");
                doGet(request, response);
                return;
            }
            Invoices invoice = new Invoices(totalAmount, description, dueDate, resident, apartment, details);
            InvoiceDAO iDAO = new InvoiceDAO();
            iDAO.insertInvoice(invoice, apartment.getApartmentId());
            int newInvoiceId = iDAO.getLatestInvoiceID();

            for (InvoiceDetail detail : details) {
                iDAO.insertInvoiceDetail(newInvoiceId, detail);
            }
            int idinv= iDAO.getLatestInvoiceID();
            HttpSession session = request.getSession();
            Staff staff= (Staff) session.getAttribute("staff");
            NotificationDAO notificationDAO= new NotificationDAO();
            Notification notification_staff = new Notification(staff.getStaffId(),
                          "Staff",invoice.getDescription() +"have been created. Please check details in the system and pay first " +invoice.getDueDateft(), "Invoice",
                          LocalDateTime.now(), false, idinv,
                          "Invoice", null, resident);
            notificationDAO.insert(notification_staff);
            response.sendRedirect("InvoicesManager");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred: " + e.getMessage());
            doGet(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
    }

    public boolean isValidDescription(String input) {
        return input.matches("^[\\p{L}0-9\\s.,!?'\"()\\-_/]{1,500}$");
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
