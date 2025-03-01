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
import org.jsoup.Jsoup;

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
        RoleDAO rdao = new RoleDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int latestFeedbackID = feedbackDAO.getLatestFeedbackID();
        List<Role> listrole = rdao.selectAll();
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
        String staffID_raw = request.getParameter("staff");
        String rating_raw = request.getParameter("rating");
        String description = request.getParameter("description");
        // Loại bỏ HTML & ký tự khoảng trắng
        String cleanText = Jsoup.parse(description).text().trim();
        String error = "error";

        RoleDAO rdao = new RoleDAO();
        List<Role> listrole = rdao.selectAll();

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
        if (title.length() < 5 || title.length() > 100) {
            request.setAttribute(error, "Title must be between 5 and 100 characters!");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        // Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
//        if (!title.matches("^[a-zA-Z0-9 .,!?()-]+$")) {
//            request.setAttribute(error, "Title contains invalid characters!");
//            request.setAttribute("listrole", listrole);
//            request.getRequestDispatcher("feedback.jsp").forward(request, response);
//            return;
//        }

        //check number star
        if (rating_raw == null || rating_raw.trim().isEmpty()) {
            request.setAttribute(error, "Please choose number star");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }

        //validate description
        if (description != null) {
            description = description.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
        }
        //check description null or not
        if (cleanText == null || cleanText.trim().isEmpty()) {
            request.setAttribute(error, "Description cannot be empty!");
            request.setAttribute("listrole", listrole);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        int rating;
        int staffID;

        try {
            rating = Integer.parseInt(rating_raw);
            staffID = Integer.parseInt(staffID_raw);
        } catch (NumberFormatException e) {
            response.getWriter().println("LOI DINH DANG SO. VUI LONG KIEM TRA LAI");
            return; // Dừng việc xử lý nếu có lỗi định dạng
        }

        if (rating < 1 || rating > 5) {
            response.getWriter().println("Rating phải trong khoảng từ 1 đến 5.");
            return;
        }

        Collection<Part> fileParts = request.getParts();
        List<ImageFeedback> images = new ArrayList<>();

        for (Part part : fileParts) {
            if (part.getName().equals("imgURL") && part.getSize() > 0) {
                InputStream inputStream = part.getInputStream();
                images.add(new ImageFeedback(inputStream, part.getSize())); // Đọc dữ liệu nhị phân
            }
        }
        FeedbackDAO fbDAO = new FeedbackDAO();
        try {
            // Tạo đối tượng Feedback và lưu vào DB
            Feedback fb = new Feedback(title, description, LocalDate.now(), rating, staffDAO.selectById(staffID), resident); // resident.getResidentId()
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
