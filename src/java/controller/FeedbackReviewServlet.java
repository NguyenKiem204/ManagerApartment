/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ManagerFeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Feedback;
import model.ManagerFeedback;
import model.Resident;
import model.Role;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name="FeedbackReviewServlet", urlPatterns={"/feedbackreview"})
public class FeedbackReviewServlet extends HttpServlet {
   
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
        ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staff = (Staff) session.getAttribute("staff");

        // Kiểm tra quyền truy cập (chỉ cho phép Staff ngoại trừ Manager)
        if (resident != null || staff == null || staff.getRole().getRoleID() == 1) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }
        
        List<ManagerFeedback> listFirstPage = managerFeedbackDAO.selectFirstPage(staff.getStaffId());

        //Phân trang
        // Lấy tham số từ request
        String pageSize_raw = request.getParameter("pageSize");
        String xpage = request.getParameter("page");

        // Khai báo biến
        int page = 1; // Trang mặc định
        int pageSize = 5; // Giá trị mặc định nếu không có tham số pageSize
        int size = managerFeedbackDAO.selectAllToCount(staff.getStaffId());
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

            // Chuyển đổi giá trị, xử lý null và exception
            int keySort = (keySort_raw != null && !keySort_raw.isEmpty()) ? Integer.parseInt(keySort_raw) : 0;

// Nếu có tham số lọc, thực hiện tìm kiếm
            if (keySort != 0 || xpage != null) {
                listFirstPage = managerFeedbackDAO.getAllFeedbacksBySort(keySort, page, pageSize, staff.getStaffId());
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi o exception!");
        }

        request.setAttribute("page", page);
        request.setAttribute("num", num);
        //send list role of staff for feedback of manager

        request.setAttribute("listfb", listFirstPage);
        request.getRequestDispatcher("feedbackreview.jsp").forward(request, response);
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
//        processRequest(request, response);
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
