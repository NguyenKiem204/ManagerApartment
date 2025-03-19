/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.FeedbackDAO;
import dao.ResidentDAO;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Feedback;
import model.ImageFeedback;
import model.Resident;
import model.Role;
import model.Staff;
import org.jsoup.Jsoup;
import validation.Validate;

/**
 *
 * @author admin
 */
@WebServlet(name = "FeedbackServlet", urlPatterns = {"/owner/feedback"})
@MultipartConfig
public class FeedbackServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "C:/uploads"; // Thư mục lưu ảnh

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
            out.println("<title>Servlet FeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackServlet at " + request.getContextPath() + "</h1>");
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

        // Kiểm tra quyền truy cập (chỉ cho phép Staff ngoại trừ Manager)
        if (resident == null || staff != null) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }
        
        RoleDAO rdao = new RoleDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int latestFeedbackID = feedbackDAO.getLatestFeedbackID();
        List<Role> listrole = rdao.getListRoleWithStaffExits();
        request.setAttribute("listrole", listrole);
        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(latestFeedbackID));
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
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
//      lấy được residentID dựa trên session
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();

        String title = request.getParameter("title");
        String roleID_raw = request.getParameter("staff");
        String rating_raw = request.getParameter("rating");
        String description = request.getParameter("description");
        // Loại bỏ HTML & ký tự khoảng trắng
        String cleanText = (description != null) ? Jsoup.parse(description).text().trim() : ""; //vì trong textarea lưu trong DB
                                                                   //thì dấu space khác so với ô input                                     
        String error = "error";
        RoleDAO rdao = new RoleDAO();
        List<Role> listrole = rdao.getListRoleWithStaffExits();

        //validate title
        if (title != null) {
            title = title.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
        }

        if (title == null || title.trim().isEmpty()) {
            request.setAttribute(error, "Title cannot be empty!");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ dài title
        if (title.length() > 100) { //nên giới hạn độ dài title vì nó chỉ nên có độ dài nhất định
            request.setAttribute(error, "Title must be <100 characters!");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }  

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
        if (!Validate.isValidTitle(title)) {
            request.setAttribute(error, "Title contains invalid characters!");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }
        
        //check chọn nhân viên chưa
        if (roleID_raw == null || roleID_raw.trim().isEmpty()) {
            request.setAttribute(error, "Please choose staff to feedback");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }

        //check number star
        if (rating_raw == null || rating_raw.trim().isEmpty()) {
            request.setAttribute(error, "Please choose number star");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }
        
        //validate description
        if (description != null) {
            description = description.replaceAll("[\\s\\u00A0]+", " ").trim(); // Loại bỏ khoảng trắng dư thừa
        }
        //check description null or not
        if (cleanText.trim().isEmpty() || cleanText.length() < 10) { //check nó toàn dấu trắng
            request.setAttribute(error, "You need to describe this feedback! More 10 characters.");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        int rating;
        int roleID;
        Staff staff = new Staff();

        try {
            rating = Integer.parseInt(rating_raw);
            roleID = Integer.parseInt(roleID_raw);
            staff = staffDAO.getStaffByRoleIDAndStatus(roleID, "Active");
        } catch (NumberFormatException e) {
            response.getWriter().println("LOI DINH DANG SO. VUI LONG KIEM TRA LAI");
            return; // Dừng việc xử lý nếu có lỗi định dạng
        }

        if (rating < 1 || rating > 5) {
            response.getWriter().println("Rating must be between 1 and 5.");
            return;
        }

        FeedbackDAO fbDAO = new FeedbackDAO();
        try {
            // Tạo đối tượng Feedback và lưu vào DB
            Feedback fb = new Feedback(title, description, LocalDate.now(), rating, staff, resident);
            fbDAO.insert(fb);

            // Redirect đến trang thành công
            response.sendRedirect("feedbacksuccessfull");

        } catch (NumberFormatException e) {
            e.printStackTrace();
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
