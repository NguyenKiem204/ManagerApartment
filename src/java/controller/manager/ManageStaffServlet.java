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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.EmailUtil;
import model.Image;
import model.Role;
import model.Staff;
import org.json.JSONObject;
import validation.Validate;

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
        RoleDAO roleDAO = new RoleDAO();

        try {
            String fullName = request.getParameter("fullName");

            if (fullName == null || fullName.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Full name cannot be empty!");
                out.write(jsonResponse.toString());
                return;
            }

            String phoneNumber = request.getParameter("phoneNumber");
            String cccd = request.getParameter("cccd");
            String email = request.getParameter("email");
            String dobStr = request.getParameter("dob");
            String sex = request.getParameter("sex");
            int roleId = Integer.parseInt(request.getParameter("roleId"));

            if (!phoneNumber.matches("\\d{10}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Phone number must have 10 number characters!");
                out.write(jsonResponse.toString());
                return;
            }

            String dobError = Validate.validateDob(dobStr);
            if (dobError != null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", dobError);
                out.write(jsonResponse.toString());
                return;
            }

            if (!cccd.matches("\\d{12}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "CCCD must have 12 number characters");
                out.write(jsonResponse.toString());
                return;
            }

            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid email format");
                out.write(jsonResponse.toString());
                return;
            }

            if (staffDAO.isStaffExists(phoneNumber, cccd, email)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "PhoneNumber, CCCD or Email already exists!");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra nếu roleId đã có một nhân viên Active
            if (staffDAO.isRoleHasActiveStaff(roleId)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Only one active staff is allowed per role!");
                out.write(jsonResponse.toString());
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dob = LocalDate.parse(dobStr, formatter);

            String status = "Active"; // Mặc định là Active
            Role role = roleDAO.selectById(roleId);
            String password = generateRandomPassword(5);

            Staff newStaff = new Staff(fullName, password, phoneNumber, cccd, email, dob, sex, status, role);

            int isAdded = staffDAO.insert(newStaff);

            if (isAdded != 0) {
                EmailUtil.sendEmail(email, password);
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Add staff successfully! Password was sent to the email.");
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