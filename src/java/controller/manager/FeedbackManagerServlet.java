/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.FeedbackDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.Feedback;
import model.Role;

/**
 *
 * @author admin
 */
@WebServlet(name = "FeedbackManagerServlet", urlPatterns = {"/manager/feedback"})
public class FeedbackManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet FeedbackAdministrativeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackAdministrativeServlet at " + request.getContextPath() + "</h1>");
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
        FeedbackDAO fbDAO = new FeedbackDAO();
        List<Feedback> list = fbDAO.selectAll();

        try {
            // Lấy tham số từ request
            String keySort_raw = request.getParameter("sort");
            String keySearch = request.getParameter("keySearch");
            String roleID_raw = request.getParameter("roleID");
            String rating_raw = request.getParameter("rating");
            String date_raw = request.getParameter("date");

            // Chuyển đổi giá trị, xử lý null và exception
            int roleID = (roleID_raw != null && !roleID_raw.isEmpty()) ? Integer.parseInt(roleID_raw) : 0;
            int rating = (rating_raw != null && !rating_raw.isEmpty()) ? Integer.parseInt(rating_raw) : 0;
            int keySort = (keySort_raw != null && !keySort_raw.isEmpty()) ? Integer.parseInt(keySort_raw) : 0;
            LocalDate date = (date_raw != null && !date_raw.isEmpty()) ? LocalDate.parse(date_raw) : null;

            // Nếu có tham số lọc, thực hiện tìm kiếm
            if (keySearch != null || roleID != 0 || rating != 0 || date != null || keySort != 0) {
                list = fbDAO.getAllFeedbacksBySearchOrFilterOrSort(keySearch, roleID, rating, date, keySort);
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi o exception!");
        }

        //Phân trang
        // Lấy tham số từ request
        String numberpage_raw = request.getParameter("pageSize");
        String xpage = request.getParameter("page");

        // Khai báo biến
        int page = 1; // Trang mặc định
        int numberpage = 5; // Giá trị mặc định nếu không có tham số pageSize
        int size = list.size();
        int start = 0;
        int end = 0;
        int num = 1;

        try {
            // Kiểm tra và parse pageSize nếu có giá trị hợp lệ
            if (numberpage_raw != null && !numberpage_raw.isEmpty()) {
                numberpage = Integer.parseInt(numberpage_raw);
                if (numberpage <= 0) {
                    numberpage = 5; // Tránh chia cho 0
                }
            }
            // Tính tổng số trang
            num = (size % numberpage == 0) ? (size / numberpage) : (size / numberpage + 1);

            // Kiểm tra và parse page nếu có giá trị hợp lệ
            if (xpage != null && !xpage.isEmpty()) {
                page = Integer.parseInt(xpage);
                if (page < 1) {
                    page = 1; // Tránh giá trị âm
                } else if (page > num) {
                    page = num; // Giữ trang trong phạm vi hợp lệ
                }
            }

            // Xác định phạm vi dữ liệu của trang hiện tại
            start = (page - 1) * numberpage;
            end = Math.min(page * numberpage, size);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số: " + e.getMessage());
            page = 1;
            numberpage = 5;
            start = 0;
            end = Math.min(numberpage, size);
        }

        List<Feedback> listPage = fbDAO.getListByPage(list, start, end);
        for (Feedback feedback : listPage) {
            System.out.println(feedback.toString());
        }
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        //send list role of staff for feedback of manager
        RoleDAO rdao = new RoleDAO();
        List<Role> listrole = rdao.selectAll();
        request.setAttribute("listrole", listrole);

        request.setAttribute("listfb", listPage);
        request.getRequestDispatcher("/manager/feedback.jsp").forward(request, response);
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
