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
import dao.ManagerFeedbackDAO;
import dao.NotificationDAO;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.Console;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import model.Feedback;
import model.ManagerFeedback;
import model.Notification;
import model.Resident;
import model.Role;
import model.Staff;
import org.jsoup.Jsoup;
import validation.Validate;

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
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();
        RoleDAO rdao = new RoleDAO();
        // Lấy tham số từ request
        String position_raw = request.getParameter("position");
        String monthYear_raw = request.getParameter("monthYear");

        LocalDate monthYear = LocalDate.now();
        int position = 0;

        try {
            if (position_raw != null && !position_raw.isEmpty()) {
                position = Integer.parseInt(position_raw);
            }
            if (monthYear_raw != null && !monthYear_raw.isEmpty()) {
                monthYear = LocalDate.parse(monthYear_raw + "-01");
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số: " + e.getMessage());
        }

        if (position_raw != null) {  // Xử lý AJAX request
            try {
                StaffDAO staffDAO = new StaffDAO();
                List<Staff> staffList = staffDAO.getStaffByPosition(position);

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("staffList", staffList);
                jsonResponse.put("totalFeedback", "N/A");
                jsonResponse.put("avgRating", "N/A");
                jsonResponse.put("positiveFeedback", "N/A");
                jsonResponse.put("negativeFeedback", "N/A");

                if (monthYear_raw != null && !monthYear_raw.isEmpty()) {
                    Map<String, Object> feedbackData = feedbackDAO.getFeedbackSummary(position, monthYear);
                    jsonResponse.putAll(feedbackData);
                }

                Gson gson = new GsonBuilder()
                          .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, jsonSerializationContext)
                                    -> new JsonPrimitive(date.toString()))
                          .create();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(jsonResponse));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error processing request");
            }
            return;
        }

//System.out.println("Current Page (before validation): " + page);
        // Gửi danh sách vai trò và phân trang cho JSP
        List<Role> listrole = rdao.selectAll();
        request.setAttribute("listrole", listrole);
        response.setLocale(new Locale("vi", "VN"));
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
        
        HttpSession session = request.getSession();
        Resident resident = (Resident) session.getAttribute("resident");
        Staff staffss = (Staff) session.getAttribute("staff");
        // Kiểm tra quyền truy cập (chỉ cho phép Staff ngoại trừ Manager)
        if (resident != null || staffss == null || staffss.getRole().getRoleID() != 1) {
            request.setAttribute("errorCode", "403");
            request.setAttribute("errorMessage", "You do not have permission to access!");
            request.getRequestDispatcher("error-authorization.jsp").forward(request, response);
            return;
        }
        
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Validate validate = new Validate();
        StaffDAO staffDAO = new StaffDAO();
        ManagerFeedbackDAO managerFeedbackDAO = new ManagerFeedbackDAO();
        NotificationDAO notificationDAO = new NotificationDAO();

        String position_raw = request.getParameter("position");
        String monthYear_raw = request.getParameter("feedback-month");
        String strengths = request.getParameter("strengths");
        String weaknesses = request.getParameter("weaknesses");
        String actionPlan = request.getParameter("actionPlan");
        String deadline_raw = request.getParameter("deadline");
        String cleanTextstr = Jsoup.parse(strengths).text().trim();
        String cleanTextwea = Jsoup.parse(weaknesses).text().trim();

        System.out.println("Deadline raw: " + deadline_raw);
        
        LocalDate monthYear = LocalDate.now();
        LocalDate deadline = LocalDate.now().plusDays(3);
        int position = 0;
        int totalFeedback = 0;
        double avgRating = 0.0;
        int positivePercentage = 0;
        int negativePercentage = 0;

        try {
            //OK check if position null -> bắt nhập(nếu không có nhân viên ở vị trí đó)
            //OK check if monthyear null -> bắt chọn
            /*validate for strengths(empty hoặc null, nhiều khoảng cách, chỉ nhập mỗi khoảng 
            cách, kiểu ảnh có thể chèn, giới hạn độ dài)*/
            //validate for weaknesses(ttu strengths)
            //OK validate for improvement(empty, chỉ có dấu cách, nhiều khoảng trắng, ký tự đặc biệt)
            //OK validate deadline(chon dl la ngay trong qua khu)
            //phan trang
            //feedbackdetail
            //format ngay thang trong bang feedback

            position = Integer.parseInt(position_raw);
            //check if not choose position
            if (position == 0) {
                // Gửi danh sách vai trò cho JSP
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Position isn't choose!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            //check if this position don't have staff -> chọn vị trí khác
            if (!staffDAO.isExistStaffByRoleIDAndStatusID(position, "Active")) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "This position don't have staff.");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            // Kiểm tra nếu người dùng chưa chọn Month & Year
            if (monthYear_raw == null || monthYear_raw.trim().isEmpty()) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please select Month & Year!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }
            if (monthYear_raw != null) {
                try {
                    // Lấy giá trị tháng năm từ input
                    YearMonth selectedMonthYear = YearMonth.parse(monthYear_raw);

                    // Lấy tháng năm hiện tại
                    YearMonth currentMonthYear = YearMonth.now();

                    // Kiểm tra nếu người dùng chọn tháng tương lai
                    if (selectedMonthYear.isAfter(currentMonthYear)) {
                        RoleDAO rdao = new RoleDAO();
                        List<Role> listrole = rdao.selectAll();
                        request.setAttribute("listrole", listrole);
                        request.setAttribute("error", "You cannot select a future month!");
                        request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                        return;
                    }

                    // Nếu hợp lệ, tiếp tục xử lý
                    monthYear = selectedMonthYear.atDay(1); // Chuyển đổi thành LocalDate
                } catch (DateTimeParseException e) {
                    request.setAttribute("error", "Invalid date format!");
                    request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                    return;
                }
            }

            
            //validate for strengths
            if (strengths != null) {
                strengths = strengths.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            //check strengths null or not
            if (cleanTextstr == null || cleanTextstr.trim().isEmpty()) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter Strengths!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }
            // Kiểm tra độ dài strengths
            if (cleanTextstr.trim().replaceAll("\\s+", " ").length() < 10) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter strengths more 10 keyword");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            //validate for weaknesses
            if (weaknesses != null) {
                weaknesses = weaknesses.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            //check weaknesses null or not
            if (cleanTextwea == null || cleanTextwea.trim().isEmpty()) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter Weaknesses!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ dài weaknesses
            if (cleanTextstr.trim().replaceAll("\\s+", " ").length() < 10) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter weaknesses more 10 keyword");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            //validate actionPlan
            if (actionPlan != null) {
                actionPlan = actionPlan.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            if (actionPlan == null || actionPlan.trim().isEmpty()) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter into actionPlan!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ dài action plan
            if (actionPlan.length() > 200) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Please enter Improvement Suggestions <200 keyword.");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidTitle(actionPlan)) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Improvement Suggestions contains invalid characters!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            //Kiểm tra user chọn dl là ngày trong quá khứ
            if (!Validate.validateDeadline(deadline_raw)) {
                RoleDAO rdao = new RoleDAO();
                List<Role> listrole = rdao.selectAll();
                request.setAttribute("listrole", listrole);
                request.setAttribute("error", "Deadline need to choose date in the future!");
                request.getRequestDispatcher("formfeedbackmanager.jsp").forward(request, response);
                return;
            }

            Map<String, Object> feedbackData = feedbackDAO.getFeedbackSummary(position, monthYear);

            // Ép kiểu dữ liệu từ Map
            totalFeedback = parseIntSafe(feedbackData.get("totalFeedback"));
            avgRating = parseDoubleSafe(feedbackData.get("avgRating"));
            positivePercentage = parseIntSafe(feedbackData.get("positiveFeedback"));
            negativePercentage = parseIntSafe(feedbackData.get("negativeFeedback"));
            if (deadline_raw != null && !deadline_raw.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                deadline = LocalDate.parse(deadline_raw, formatter);
            }
            Staff staff = staffDAO.getStaffByRoleIDAndStatus(position, "Active");

            ManagerFeedback managerFeedback = new ManagerFeedback(monthYear,
                      totalFeedback, avgRating, positivePercentage, negativePercentage,
                      strengths, weaknesses, null, actionPlan,
                      deadline, LocalDateTime.now(), staff);
            managerFeedbackDAO.insert(managerFeedback);
            System.out.println("Result last ID in form fb mmanager servlet: " + managerFeedbackDAO.selectLastId());

//gửi thông báo tới staff
            Notification notification = new Notification(staffss.getStaffId(), "Staff", "You have new feedback from your manager.", "feedback", LocalDateTime.now(), false, managerFeedbackDAO.selectLastId(), "ManagerFeedback", staff, null);
            notificationDAO.insert(notification);
        } catch (NumberFormatException e) {
            log("LOIIIII!");
        }
        // Lấy dữ liệu từ Map
        request.getRequestDispatcher("fbstatisticsuccessfull.jsp").forward(request, response);
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

    //Chuyển Object thành int an toàn
    private int parseIntSafe(Object value) {
        if (value == null || "N/A".equals(value)) {
            return 0; // Giá trị mặc định nếu không có dữ liệu
        }
        try {
            return (value instanceof Number) ? ((Number) value).intValue() : Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return 0; // Trả về 0 nếu có lỗi
        }
    }

//  Chuyển Object thành double an toàn
    private double parseDoubleSafe(Object value) {
        if (value == null || "N/A".equals(value)) {
            return 0.0; // Giá trị mặc định nếu không có dữ liệu
        }
        try {
            return (value instanceof Number) ? ((Number) value).doubleValue() : Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0.0; // Trả về 0.0 nếu có lỗi
        }
    }
}
