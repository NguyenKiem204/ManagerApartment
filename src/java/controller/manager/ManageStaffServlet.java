/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.ImageDAO;
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
import java.time.format.DateTimeParseException;
import java.util.List;
import model.EmailUtil;
import model.Image;
import model.Role;
import model.Staff;
import org.json.JSONObject;

@WebServlet(name="ManageStaffServlet", urlPatterns={"/manager/manageStaff"})
public class ManageStaffServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String sex = request.getParameter("sex");
    String status = request.getParameter("status");
    String searchKeywordRaw = request.getParameter("searchKeyword"); 
    String searchKeyword = normalizeString(searchKeywordRaw);
    StaffDAO staffDAO = new StaffDAO();
    List<Staff> listStaff;
    int page = 1;
    int pageSize = 4; // Số bản ghi mỗi trang

    // Lấy page từ request nếu có
    String pageStr = request.getParameter("page");
    if (pageStr != null && !pageStr.isEmpty()) {
        page = Integer.parseInt(pageStr);
    }
    listStaff = staffDAO.searchStaffs(searchKeyword.trim(), sex, status, page, pageSize);
//    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
//        listStaff = staffDAO.searchStaffs(searchKeyword.trim(), sex, status);
//    } else {
//        listStaff = staffDAO.getAllStaffs(sex, status);
//    }
    int totalRecords = staffDAO.getTotalStaffs(searchKeyword.trim(), sex, status);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    request.setAttribute("listStaff", listStaff);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);
    request.getRequestDispatcher("/manager/mngstaff.jsp").forward(request, response);
}
    private String normalizeString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ""; // Nếu chuỗi null hoặc rỗng, trả về rỗng
        }
        
        // Xóa khoảng trắng thừa và tách các từ
        String[] words = input.trim().replaceAll("\\s+", " ").split(" ");
        
        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                // Viết hoa chữ cái đầu, các chữ sau viết thường
                normalized.append(Character.toUpperCase(word.charAt(0)))
                          .append(word.substring(1).toLowerCase())
                          .append(" ");
            }
        }
        
        return normalized.toString().trim(); // Xóa khoảng trắng cuối cùng
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        StaffDAO staffDAO = new StaffDAO();
        ImageDAO imageDAO = new ImageDAO();
        RoleDAO roleDAO = new RoleDAO();

        try {
            // Nhận dữ liệu từ request
            // Nhận dữ liệu từ request
            String fullName = request.getParameter("fullName");

            // Kiểm tra fullName không được null hoặc rỗng
            if (fullName == null || fullName.trim().isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Full name cannot be empty!");
            out.write(jsonResponse.toString());
            return;
            }

            // Kiểm tra định dạng fullName (chỉ chứa chữ cái và khoảng trắng)
            // Nếu tên có thể chứa ký tự đặc biệt hoặc số thì bạn cần điều chỉnh regex cho phù hợp
//            if (!fullName.matches("^[a-zA-Z\\s]+$")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Full name must contain only letters and spaces!");
//                out.write(jsonResponse.toString());
//                return;
//            }

            String phoneNumber = request.getParameter("phoneNumber");
            String cccd = request.getParameter("cccd");
            String email = request.getParameter("email");
            String dobStr = request.getParameter("dob");
            String sex = request.getParameter("sex");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            
            // Kiểm tra định dạng số điện thoại (10 số)
            if (!phoneNumber.matches("\\d{10}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Phone number must has 10 number characters!");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra định dạng CCCD (12 số)
            if (!cccd.matches("\\d{12}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "CCCD must has 12 number characters");
                out.write(jsonResponse.toString());
                return;
            }
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Định dạng mail sai");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra xem cư dân đã tồn tại chưa
            if (staffDAO.isStaffExists(phoneNumber, cccd, email)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "PhoneNumber, CCCD or Email is exist!");
                out.write(jsonResponse.toString());
                return;
            }

            // Chuyển đổi ngày sinh
            LocalDate dob = LocalDate.parse(dobStr);

            // Mặc định trạng thái là Active
            String status = "Active";
            Role role = roleDAO.selectById(roleId);

            // Tạo mật khẩu ngẫu nhiên (3 ký tự)
            String password = generateRandomPassword(5);

            // Tạo đối tượng Resident
            Staff newStaff = new Staff(fullName, password, phoneNumber, cccd, email, dob, sex, status, role);

            // Thêm vào database
            int isAdded = staffDAO.insert(newStaff);

            if (isAdded != 0) {
                // Gửi email chứa mật khẩu
                EmailUtil.sendEmail(email, password);
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Add staff successfully! Password was sent to the mail.");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Add staff failed!");
            }
        } catch (DateTimeParseException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Birthdate is not valid!");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Unknown error: " + e.getMessage());
        }

        // Trả về JSON response
        out.write(jsonResponse.toString());
    }
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            password.append(chars.charAt(randomIndex));
        }
        return password.toString();
    }
    
}