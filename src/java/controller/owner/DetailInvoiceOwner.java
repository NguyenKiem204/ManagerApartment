/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Invoices;
import model.Resident;
import model.Staff;
import model.TypeBill;

/**
 *
 * @author nguye
 */
@WebServlet(name = "DetailInvoiceOwner", urlPatterns = {"/owner/DetailInvoiceOwner"})
public class DetailInvoiceOwner extends HttpServlet {

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
            out.println("<title>Servlet DetailInvoiceOwner</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailInvoiceOwner at " + request.getContextPath() + "</h1>");
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
        Staff staff = (Staff) session.getAttribute("staff");

        // Kiểm tra nếu không có người dùng đăng nhập
        if (resident == null && staff == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idinv = request.getParameter("invoiceID");
        if (idinv == null || idinv.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu invoiceID");
            return;
        }

        try {
            int id = Integer.parseInt(idinv);
            InvoiceDAO idao = new InvoiceDAO();
            Invoices inv = idao.selectById(id);

            // Kiểm tra nếu hóa đơn không tồn tại
            if (inv == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn");
                return;
            }

            // Phân quyền:
            // 1. Nếu là staff và có vai trò là accountant (ví dụ: roleID = 2) → Cho phép xem tất cả hóa đơn
            // 2. Nếu là resident → Chỉ được xem hóa đơn của chính mình
            if (staff != null && staff.getRole().getRoleID() == 3) {
                String redirectURL = request.getContextPath() + "/accountant/DetailInvoice?invoiceID=" + id;
                response.sendRedirect(redirectURL);
            } else if (resident != null) {
                if (inv.getResident().getResidentId() != resident.getResidentId()) {
                    request.setAttribute("errorCode", "403");
                    request.setAttribute("errorMessage", "Bạn không có quyền xem hóa đơn này!");
                    request.getRequestDispatcher("/error-authorization.jsp").forward(request, response);
                    return;
                }
            } else {
                // Nếu không phải accountant hoặc resident → Chuyển hướng đến trang đăng nhập
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Lấy danh sách loại hóa đơn
            List<TypeBill> lt = idao.getAllTypeBills();

            // Đặt thông tin vào request để hiển thị trên JSP
            request.setAttribute("invoice", inv);
            request.setAttribute("typeBills", lt);
            request.getRequestDispatcher("DetailInvoiceOnwer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu");
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
