/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.ApartmentDAO;
import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Apartment;
import org.json.JSONObject;


@WebServlet(name="ManageApartmentServlet", urlPatterns={"/manager/manageApartment"})
public class ManageApartmentServlet extends HttpServlet {
    ApartmentDAO apartmentDAO = new ApartmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String nameRaw = request.getParameter("name");
    String name = (nameRaw != null) ? normalizeString(nameRaw) : "";
    
    String ownerIdraw = request.getParameter("ownerId");
    int ownerId = -1; // Mặc định là -1 nếu không có giá trị hợp lệ
    if (ownerIdraw != null && !ownerIdraw.trim().isEmpty()) {
        try {
            ownerId = Integer.parseInt(ownerIdraw.trim());
        } catch (NumberFormatException e) {
            ownerId = -1; // Nếu lỗi, đặt về -1 để không ảnh hưởng SQL
        }
    }

    String type = request.getParameter("type");
    String status = request.getParameter("status");
    String block = request.getParameter("block");

    int page = 1;
    int pageSize = 10; // Số bản ghi mỗi trang

    // Lấy page từ request nếu có
    String pageStr = request.getParameter("page");
    if (pageStr != null && !pageStr.trim().isEmpty()) {
        try {
            page = Integer.parseInt(pageStr.trim());
        } catch (NumberFormatException e) {
            page = 1; // Nếu lỗi, đặt về trang 1
        }
    }

    // Gọi DAO để lấy danh sách
    List<Apartment> listApartment = apartmentDAO.searchApartments(name, ownerId, type, status, block, page, pageSize);
    int totalRecords = apartmentDAO.getTotalApartments(name, ownerId, type, status, block);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

    // Gửi dữ liệu về JSP
    request.setAttribute("listApartment", listApartment);
    request.setAttribute("selectedName", name);
    request.setAttribute("selectedOwnerId", ownerId);
    request.setAttribute("selectedType", type);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("selectedBlock", block);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);

    request.getRequestDispatcher("/manager/mngapartment.jsp").forward(request, response);
}


    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        ApartmentDAO apartmentDAO = new ApartmentDAO();

        try {
            // Nhận dữ liệu từ request
            String apartmentName = request.getParameter("apartmentName");

            // Kiểm tra apartmentName không được null hoặc rỗng
            if (apartmentName == null || apartmentName.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Apartment name cannot be empty!");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra định dạng apartmentName (chỉ chứa chữ cái, số và khoảng trắng)
            // Nếu bạn cho phép ký tự đặc biệt khác, hãy điều chỉnh regex cho phù hợp
            if (!apartmentName.matches("^[a-zA-Z0-9\\s]+$")) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Apartment name must contain only letters, numbers, and spaces!");
            out.write(jsonResponse.toString());
            return;
            }

            String block = request.getParameter("block");
            String status = request.getParameter("status");
            String type = request.getParameter("type");
            int ownerId = Integer.parseInt(request.getParameter("ownerId"));

            //Kiểm tra ownerId có hợp lệ không
            ResidentDAO residentDAO = new ResidentDAO();
            if (!residentDAO.isOwnerResident(ownerId)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Owner is not valid!");
                out.write(jsonResponse.toString());
                return;
            }

            // Kiểm tra xem cư dân đã tồn tại chưa
            if (apartmentDAO.isApartmentExists(apartmentName, block)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "This apartment is dupplicated!");
                out.write(jsonResponse.toString());
                return;
            }

            Apartment newApartment = new Apartment(apartmentName, block, status, type, ownerId);

            // Thêm vào database
            int isAdded = apartmentDAO.insert(newApartment);

            if (isAdded != 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Add apartment information successfully!");
                
                
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Add apartment information failed!");
            }
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Unknown error: " + e.getMessage());
        }

        // Trả về JSON response
        out.write(jsonResponse.toString());
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
}
