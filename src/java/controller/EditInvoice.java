/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<< Updated upstream:src/java/controller/EditInvoice.java
package controller;
=======
package controller.accountant;
>>>>>>> Stashed changes:src/java/controller/accountant/EditInvoice.java

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
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dueDate = "";
            if (inv.getDueDate() != null && !inv.getDueDate().isEmpty()) {
                Date parsedDate = inputFormat.parse(inv.getDueDate());
                dueDate = outputFormat.format(parsedDate);
            }

            request.setAttribute("formattedDueDate", dueDate);
            List<TypeBill> lt = idao.getAllTypeBills();
            request.setAttribute("listType", lt);
            request.setAttribute("invoice", inv);
            request.getRequestDispatcher("EditInvoice.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(EditInvoice.class.getName()).log(Level.SEVERE, null, ex);
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
            int invoiceid;
            LocalDate dueDate;
            try {
                invoiceid = Integer.parseInt(invoiceiDsc);
                dueDate = LocalDate.parse(dueDateStr);
            } catch (NumberFormatException | DateTimeParseException e) {
                response.sendRedirect("error.jsp2?msg=Invalid parameter format");
                return;
            }

            // Lấy danh sách chi tiết hóa đơn từ request
            String[] typeBillIds = request.getParameterValues("typebills");
            String[] amounts = request.getParameterValues("amount");
            String[] descriptions = request.getParameterValues("descriptionde");

            // Kiểm tra danh sách chi tiết hóa đơn
            if (typeBillIds == null || amounts == null || descriptions == null
                    || typeBillIds.length == 0 || amounts.length == 0 || descriptions.length == 0) {
                response.sendRedirect("error.jsp4?msg=Invoice details are missing");
                return;
            }

            // Tạo danh sách chi tiết hóa đơn và tính tổng số tiền
            List<InvoiceDetail> details = new ArrayList<>();
            double totalAmount = 0.0;

            for (int i = 0; i < typeBillIds.length; i++) {
                try {
                    int typeBillId = Integer.parseInt(typeBillIds[i]);
                    double amount = Double.parseDouble(amounts[i]);
                    String desc = descriptions[i];

                    if (amount > 0) {
                        details.add(new InvoiceDetail(amount, desc, typeBillId));
                        totalAmount += amount;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("error.jsp5?msg=Invalid number format in invoice details");
                    return;
                }
            }

            // Kiểm tra xem có ít nhất một chi tiết hóa đơn hợp lệ không
            if (details.isEmpty()) {
                response.sendRedirect("error.jsp6?msg=Invoice must have at least one valid detail");
                return;
            }

            // Tạo đối tượng hóa đơn và lưu vào cơ sở dữ liệu
            Invoices invoice = new Invoices(invoiceid, totalAmount, description, dueDate.toString(), details);
            InvoiceDAO iDAO = new InvoiceDAO();
            iDAO.updateInvoice(invoice);
            iDAO.deleteInvoiceDetail(invoice.getInvoiceID());
            // Thêm chi tiết hóa đơn vào cơ sở dữ liệu
            for (InvoiceDetail detail : details) {
                iDAO.insertInvoiceDetail(invoice.getInvoiceID(), detail);
            }

            // Chuyển hướng về trang quản lý hóa đơn sau khi thêm thành công
            response.sendRedirect("InvoicesManager");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp7?msg=Unexpected error occurred: " + e.getMessage());
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
