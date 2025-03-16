/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.ApartmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Apartment;
import model.Resident;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ManageOwnerApartmentServlet", urlPatterns={"/owner/manageOwnerApartment"})
public class ManageOwnerApartmentServlet extends HttpServlet {
    ApartmentDAO apartmentDAO = new ApartmentDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String nameRaw = request.getParameter("name");
        String name = (nameRaw != null) ? normalizeString(nameRaw) : "";
        HttpSession session = request.getSession();
        Resident owner = (Resident) session.getAttribute("resident");

        if (owner == null || owner.getRole().getRoleID() != 7) {
            response.sendRedirect("/login.jsp");
            return;
        }

        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String block = request.getParameter("block");

        int page = 1;
        int pageSize = 3; // Số bản ghi mỗi trang

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
    List<Apartment> listApartment = apartmentDAO.searchApartments(name, owner.getResidentId(), type, status, block, page, pageSize);
    int totalRecords = apartmentDAO.getTotalApartments(name, owner.getResidentId(), type, status, block);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

    // Gửi dữ liệu về JSP
    request.setAttribute("listApartment", listApartment);
    request.setAttribute("selectedName", name);
    request.setAttribute("selectedType", type);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("selectedBlock", block);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);

    request.getRequestDispatcher("/owner/ownerapartment.jsp").forward(request, response);
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
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
