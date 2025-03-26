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
import org.json.JSONObject;

/**
 *
 * @author fptshop
 */
@WebServlet(name="UpdateResidentStatusServlet", urlPatterns={"/manager/updateResidentStatus"})
public class UpdateResidentStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String residentIdStr = request.getParameter("residentId");
            String status = request.getParameter("status");

            System.out.println("Received residentId: " + residentIdStr);
            System.out.println("Received status: " + status);

            if (residentIdStr == null || status == null) {
                out.write("{\"success\": false, \"message\": \"Thiếu dữ liệu residentId hoặc status!\"}");
                return;
            }
            int residentId = Integer.parseInt(residentIdStr);
            ResidentDAO residentDAO = new ResidentDAO();
            ApartmentDAO apartmentDAO = new ApartmentDAO();

            if (residentDAO.isOwnerResident(residentId) && apartmentDAO.isResidentAnOwner(residentId)) {
                // Lấy lại trạng thái Resident từ DB để cập nhật giao diện
                String currentStatus = residentDAO.getResidentStatus(residentId);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Không thể thay đổi trạng thái của Resident là Owner của một Apartment!");
                jsonResponse.put("currentStatus", currentStatus); // Gửi trạng thái hiện tại về client
                out.write(jsonResponse.toString());
                return;
            }

            boolean isUpdated = residentDAO.updateStatus(residentId, status);

            if (isUpdated) {
                out.write("{\"success\": true, \"message\": \"Cập nhật trạng thái thành công!\"}");
            } else {
                out.write("{\"success\": false, \"message\": \"Cập nhật thất bại!\"}");
            }
        } catch (NumberFormatException e) {
            out.write("{\"success\": false, \"message\": \"residentId phải là số!\"}");
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi ra console
            out.write("{\"success\": false, \"message\": \"Lỗi: " + e.getMessage() + "\"}");
        }
    }
}
