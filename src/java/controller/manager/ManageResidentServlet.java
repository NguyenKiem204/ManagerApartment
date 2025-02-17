/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.ImageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.ResidentDAO;
import dao.RoleDAO;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.EmailUtil;
import model.Image;
import model.Resident;
import model.Role;
import org.json.JSONObject;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ManageResidentControl", urlPatterns={"/manager/manageResident"})
public class ManageResidentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String sex = request.getParameter("sex");
    String status = request.getParameter("status");
    String searchKeyword = request.getParameter("searchKeyword"); 

    ResidentDAO residentDAO = new ResidentDAO();
    List<Resident> listResident;

    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        listResident = residentDAO.searchResidents(searchKeyword.trim(), sex, status);
    } else {
        listResident = residentDAO.getAllResidents(sex, status);
    }

    request.setAttribute("listResident", listResident);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 

    request.getRequestDispatcher("/manager/mngresident.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        ResidentDAO residentDAO = new ResidentDAO();
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
            if (residentDAO.isResidentExists(phoneNumber, cccd, email)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "PhoneNumber, CCCD or Email is exist!");
                out.write(jsonResponse.toString());
                return;
            }

            // Chuyển đổi ngày sinh
            LocalDate dob = LocalDate.parse(dobStr);

            // Mặc định trạng thái là Active
            String status = "Active";
            //Image image = null;
            Role role = roleDAO.selectById(roleId);

            // Tạo mật khẩu ngẫu nhiên (3 ký tự)
            String password = generateRandomPassword(3);

            // Tạo đối tượng Resident
            Resident newResident = new Resident(fullName, password, phoneNumber, cccd, email, dob, sex, status, role);

            // Thêm vào database
            int isAdded = residentDAO.insert(newResident);

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
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}