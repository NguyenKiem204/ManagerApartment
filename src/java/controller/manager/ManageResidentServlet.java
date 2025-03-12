/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.ApartmentDAO;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.Apartment;
import model.EmailUtil;
import model.Image;
import model.Resident;
import model.Role;
import org.json.JSONObject;
import validation.Validate;

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
    String searchKeywordRaw = request.getParameter("searchKeyword"); 
    String searchKeyword = normalizeString(searchKeywordRaw);
    ResidentDAO residentDAO = new ResidentDAO();
    List<Resident> listResident;
    int page = 1;
    int pageSize = 4; // Số bản ghi mỗi trang

    // Lấy page từ request nếu có
    String pageStr = request.getParameter("page");
    if (pageStr != null && !pageStr.isEmpty()) {
        page = Integer.parseInt(pageStr);
    }
//    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        listResident = residentDAO.searchResidents(searchKeyword.trim(), sex, status, page, pageSize);
//    } else {
//        listResident = residentDAO.getAllResidents(sex, status);
//    }
    int totalRecords = residentDAO.getTotalResidents(searchKeyword.trim(), sex, status);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    ApartmentDAO apartmentDAO = new ApartmentDAO();
    List<Apartment> apartmentList = apartmentDAO.getNotNullOwnerApartments();
request.setAttribute("apartmentList", apartmentList);
    request.setAttribute("listResident", listResident);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);
    request.getRequestDispatcher("/manager/mngresident.jsp").forward(request, response);
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


//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        JSONObject jsonResponse = new JSONObject();
//
//        ResidentDAO residentDAO = new ResidentDAO();
//        ImageDAO imageDAO = new ImageDAO();
//        RoleDAO roleDAO = new RoleDAO();
//
//        try {
//            // Nhận dữ liệu từ request
//            String fullName = request.getParameter("fullName");
//
//            // Kiểm tra fullName không được null hoặc rỗng
//            if (fullName == null || fullName.trim().isEmpty()) {
//            jsonResponse.put("success", false);
//            jsonResponse.put("message", "Full name cannot be empty!");
//            out.write(jsonResponse.toString());
//            return;
//            }
//            String phoneNumber = request.getParameter("phoneNumber");
//            String cccd = request.getParameter("cccd");
//            String email = request.getParameter("email");
//            String dobStr = request.getParameter("dob");
//            String sex = request.getParameter("sex");
//            //int roleId = Integer.parseInt(request.getParameter("roleId"));
//            int roleId = 7;
//            // Kiểm tra định dạng số điện thoại (10 số)
//            if (!phoneNumber.matches("\\d{10}")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Phone number must has 10 number characters!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            // Kiểm tra định dạng CCCD (12 số)
//            if (!cccd.matches("\\d{12}")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "CCCD must has 12 number characters");
//                out.write(jsonResponse.toString());
//                return;
//            }
//            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Định dạng mail sai");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            // Kiểm tra xem cư dân đã tồn tại chưa
//            if (residentDAO.isResidentExists(phoneNumber, cccd, email)) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "PhoneNumber, CCCD or Email is exist!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            // Chuyển đổi ngày sinh
//            LocalDate dob = LocalDate.parse(dobStr);
//
//            // Mặc định trạng thái là Active
//            String status = "Active";
//            //Image image = null;
//            Role role = roleDAO.selectById(roleId);
//
//            // Tạo mật khẩu ngẫu nhiên (3 ký tự)
//            String password = generateRandomPassword(5);
//
//            // Tạo đối tượng Resident
//            Resident newResident = new Resident(fullName, password, phoneNumber, cccd, email, dob, sex, status, role);
//
//            // Thêm vào database
//            int isAdded = residentDAO.insert(newResident);
//
//            if (isAdded != 0) {
//                // Gửi email chứa mật khẩu
//                EmailUtil.sendEmail(email, password);
//                jsonResponse.put("success", true);
//                jsonResponse.put("message", "Add staff successfully! Password was sent to the mail.");
//                
//            } else {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Add staff failed!");
//            }
//        } catch (DateTimeParseException e) {
//            jsonResponse.put("success", false);
//            jsonResponse.put("message", "Birthdate is not valid!");
//        } catch (Exception e) {
//            jsonResponse.put("success", false);
//            jsonResponse.put("message", "Unknown error: " + e.getMessage());
//        }
//
//        // Trả về JSON response
//        out.write(jsonResponse.toString());
//    }
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    JSONObject jsonResponse = new JSONObject();

    ResidentDAO residentDAO = new ResidentDAO();
    ApartmentDAO apartmentDAO = new ApartmentDAO();
    RoleDAO roleDAO = new RoleDAO();

    try {
        // Nhận dữ liệu từ request
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String cccd = request.getParameter("cccd");
        String email = request.getParameter("email");
        String dobStr = request.getParameter("dob");
        String sex = request.getParameter("sex");
        int roleId = 7; // Mặc định role Resident
        String apartmentIdRaw = request.getParameter("apartmentId");

        // Kiểm tra dữ liệu đầu vào
        if (fullName == null || fullName.trim().isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Full name cannot be empty!");
            out.write(jsonResponse.toString());
            return;
        }
        String dobError = Validate.validateDob(dobStr);
        if(dobError != null) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", dobError);
            out.write(jsonResponse.toString());
            return;
        }
        if (!phoneNumber.matches("\\d{10}")) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Phone number must have 10 digits!");
            out.write(jsonResponse.toString());
            return;
        }

        if (!cccd.matches("\\d{12}")) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "CCCD must have 12 digits!");
            out.write(jsonResponse.toString());
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Invalid email format!");
            out.write(jsonResponse.toString());
            return;
        }

        if (residentDAO.isResidentExists(phoneNumber, cccd, email)) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "PhoneNumber, CCCD, or Email already exists!");
            out.write(jsonResponse.toString());
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dob = LocalDate.parse(dobStr, formatter);
        String status = "Active";
        String password = generateRandomPassword(5);
        Role role = roleDAO.selectById(roleId);

        Resident newResident = new Resident(fullName, password, phoneNumber, cccd, email, dob, sex, status, role);
        int residentId = residentDAO.insert1(newResident);
        if (residentId <= 0) {  // Kiểm tra nếu residentId không hợp lệ
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Failed to insert resident. Resident ID not generated.");
            out.write(jsonResponse.toString());
            return;
        }


        int apartmentId = -1; // Giá trị mặc định
        if (apartmentIdRaw != null && !apartmentIdRaw.isEmpty()) {
        try {
            apartmentId = Integer.parseInt(apartmentIdRaw);
            apartmentDAO.updateOwner(apartmentId, residentId);
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Invalid apartment ID format.");
            out.write(jsonResponse.toString());
            return;
        }
            EmailUtil.sendEmail(email, password);
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Resident added successfully! Password sent to email.");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Failed to add resident!");
        }
    } catch (Exception e) {
        jsonResponse.put("success", false);
        jsonResponse.put("message", "Error: " + e.getMessage());
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
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}