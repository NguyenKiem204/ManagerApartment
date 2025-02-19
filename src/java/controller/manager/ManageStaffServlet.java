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
    String searchKeyword = request.getParameter("searchKeyword"); 

    StaffDAO staffDAO = new StaffDAO();
    List<Staff> listStaff;

    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        listStaff = staffDAO.searchStaffs(searchKeyword.trim(), sex, status);
    } else {
        listStaff = staffDAO.getAllStaffs(sex, status);
    }

    request.setAttribute("listStaff", listStaff);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 

    request.getRequestDispatcher("/manager/mngstaff.jsp").forward(request, response);
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
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String cccd = request.getParameter("cccd");
            String email = request.getParameter("email");
            String dobStr = request.getParameter("dob");
            String sex = request.getParameter("sex");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            int imageId = imageDAO.selectAll().size() + 1;
            
            // Kiểm tra định dạng số điện thoại (10 số)
            if (!phoneNumber.matches("\\d{10}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Số điện thoại phải có đúng 10 chữ số!");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra định dạng CCCD (12 số)
            if (!cccd.matches("\\d{12}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "CCCD phải có đúng 12 chữ số!");
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
            Image image = imageDAO.selectById(imageDAO.insert(new Image(imageId, null))); // Ảnh mặc định
            Role role = roleDAO.selectById(roleId);

            // Tạo mật khẩu ngẫu nhiên (3 ký tự)
            String password = generateRandomPassword(3);

            // Tạo đối tượng Resident
            Staff newStaff = new Staff(fullName, password, phoneNumber, cccd, email, dob, sex, status, image, role);

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