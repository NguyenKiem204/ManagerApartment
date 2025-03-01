/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import dao.FeedbackDAO;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Role;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "FormFeedbackManagerServlet", urlPatterns = {"/manager/formfeedbackmanager"})
public class FormFeedbackManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet FormFeedbackManagerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FormFeedbackManagerServlet at " + request.getContextPath() + "</h1>");
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
        // Lấy tham số từ request
        String position_raw = request.getParameter("position");
        String monthYear_raw = request.getParameter("monthYear");

        if (position_raw != null) {  // AJAX request
            try {
                int position = Integer.parseInt(position_raw);
                StaffDAO staffDAO = new StaffDAO();
                List<Staff> staffList = staffDAO.getStaffByPosition(position);

                // Tạo đối tượng JSON phản hồi
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("staffList", staffList);

                // Nếu người dùng chọn cả Month & Year, lấy Overall Rating
                if (monthYear_raw == null || monthYear_raw.isEmpty()) {
                    // Khi chưa chọn tháng, trả về N/A cho tất cả các giá trị rating
                    jsonResponse.put("totalFeedback", "N/A");
                    jsonResponse.put("avgRating", "N/A");
                    jsonResponse.put("positiveFeedback", "N/A");
                    jsonResponse.put("negativeFeedback", "N/A");
                } else {
                    // Nếu đã chọn tháng, thực hiện truy vấn dữ liệu
                    LocalDate monthYear = LocalDate.parse(monthYear_raw + "-01"); // Chuyển thành yyyy-MM-dd
                    FeedbackDAO feedbackDAO = new FeedbackDAO();

                    Map<String, Object> feedbackData = feedbackDAO.getFeedbackSummary(position, monthYear);

                    jsonResponse.putAll(feedbackData);
                }

                // Chuyển đổi JSON và gửi phản hồi
                Gson gson = new GsonBuilder()
                          .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, jsonSerializationContext)
                                    -> new JsonPrimitive(date.toString()))
                          .create();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(jsonResponse));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid position ID");
            }
            return;  // Dừng ở đây, không `forward` JSP
        }

        // Gửi danh sách vai trò cho JSP
        RoleDAO rdao = new RoleDAO();
        List<Role> listrole = rdao.selectAll();
        request.setAttribute("listrole", listrole);
        request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
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
