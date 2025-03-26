/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.RequestDAO;
import dao.StatusRequestDAO;
import dao.TypeRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.Request;
import model.Resident;
import model.Staff;
import model.StatusRequest;
import model.TypeRequest;

/**
 *
 * @author admin
 */
@WebServlet(name="ListRequestServlet", urlPatterns={"/owner/listrequest"})
public class ListRequestServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ListRequestServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListRequestServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDAO requestDAO = new RequestDAO();
        
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        // Kiểm tra quyền truy cập (chỉ cho phép Staff ngoại trừ Manager)
        if (resident == null || staff != null) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }
        
        int ownerId = resident.getResidentId();
        
        List<Request> listFirstPage = requestDAO.selectFirstPageOfResident(ownerId);
        
//Phân trang
        // Lấy tham số từ request
        String pageSize_raw = request.getParameter("pageSize");
        String xpage = request.getParameter("page");

        // Khai báo biến
        int page = 1; // Trang mặc định
        int pageSize = 5; // Giá trị mặc định nếu không có tham số pageSize
        int size = requestDAO.selectAllToCountOfResident(ownerId);
        int num = 1;

        try {
            // Kiểm tra và parse pageSize nếu có giá trị hợp lệ
            if (pageSize_raw != null && !pageSize_raw.isEmpty()) {
                pageSize = Integer.parseInt(pageSize_raw);
                if (pageSize <= 0) {
                    pageSize = 5; // Tránh chia cho 0
                }
            }
            // Tính tổng số trang
            num = (size % pageSize == 0) ? (size / pageSize) : (size / pageSize + 1);

            // Kiểm tra và parse page nếu có giá trị hợp lệ
            if (xpage != null && !xpage.isEmpty()) {
                page = Integer.parseInt(xpage);
                if (page < 1) {
                    page = 1; // Tránh giá trị âm
                } else if (page > num) {
                    page = num; // Giữ trang trong phạm vi hợp lệ
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số: " + e.getMessage());
            page = 1;
            pageSize = 5;
        }

        try {
            // Lấy tham số từ request
            String keySort_raw = request.getParameter("sort");
            String keySearch_raw = request.getParameter("keySearch");
            String status_raw = request.getParameter("status");
            String typeRequestID_raw = request.getParameter("typeRequestID");
//            String date_raw = request.getParameter("date");
            System.out.println("key sort: " + keySort_raw );
            System.out.println("key keySearch_raw: " + keySearch_raw );
            System.out.println("key status_raw: " + status_raw );
            System.out.println("key typeRequestID_raw: " + typeRequestID_raw );
            
            String keySearch = (keySearch_raw != null) ? keySearch_raw.replaceAll("\\s+", " ").trim() : null;
            if (keySearch != null && keySearch.isEmpty()) {
                keySearch = null;
            }
            
            // Chuyển đổi giá trị, xử lý null và exception
            int status = (status_raw != null && !status_raw.isEmpty()) ? Integer.parseInt(status_raw) : 0;
            int typeRequestID = (typeRequestID_raw != null && !typeRequestID_raw.isEmpty()) ? Integer.parseInt(typeRequestID_raw) : 0;
            int keySort = (keySort_raw != null && !keySort_raw.isEmpty()) ? Integer.parseInt(keySort_raw) : 0;
//            LocalDate date = (date_raw != null && !date_raw.isEmpty()) ? LocalDate.parse(date_raw) : null;

            // Nếu có tham số lọc, thực hiện tìm kiếm
            if (keySearch_raw != null || status != 0 || typeRequestID != 0 || keySort != 0 || xpage != null) {
                listFirstPage = requestDAO.getAllRequestsBySearchOrFilterOrSortOfResident(keySearch, typeRequestID, status, keySort, page, pageSize, ownerId);
                int numberOfLine = requestDAO.getNumberOfRequestsBySearchOrFilterOrSortOfResident(keySearch, typeRequestID, status, keySort,ownerId);
                num = (numberOfLine % pageSize == 0) ? (numberOfLine / pageSize) : (numberOfLine / pageSize + 1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi o exception!");
        }
        
        TypeRequestDAO typeRequestDAO = new TypeRequestDAO();
        StatusRequestDAO statusRequestDAO = new StatusRequestDAO();
        List<TypeRequest> listTypeRq = typeRequestDAO.selectAll();
        List<StatusRequest> listStatus = statusRequestDAO.selectAll();
        
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("listrq", listFirstPage);
        request.setAttribute("listTypeRq", listTypeRq);
        request.setAttribute("listStatus", listStatus);
        request.getRequestDispatcher("listrequest.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
