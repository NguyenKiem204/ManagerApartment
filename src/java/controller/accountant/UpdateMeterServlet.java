/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.DBContext;
import dao.MeterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name = "UpdateMeterServlet", urlPatterns = {"/accountant/update-meter"})
public class UpdateMeterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String meterIdStr = request.getParameter("meterId");
        String status = request.getParameter("status");
        String meterNumber = request.getParameter("meterNumber");

        System.out.println("Received meterId: " + meterIdStr); // Debug
        System.out.println("Received status: " + status);

        if (meterIdStr == null || meterIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Missing meter ID.");
            response.sendRedirect("viewmeter.jsp?error=missing_meter_id");
            return;
        }
        // Xóa khoảng trắng thừa trong meterNumber
        if (meterNumber != null) {
            meterNumber = meterNumber.trim().replaceAll("\\s+", "");
        }
        if (meterNumber == null || !meterNumber.matches("^[a-zA-Z0-9.]+$")) {
            request.setAttribute("errorMessage", "Error to Update");
//            response.sendRedirect("viewmeter.jsp?error=invalid_meter_number");
            request.setAttribute("meterNumber", meterNumber); // Giữ lại giá trị nhập vào
            request.setAttribute("meterId", meterIdStr); // Giữ lại meterId
            request.setAttribute("status", status); // Giữ lại trạng thái
            request.getRequestDispatcher("viewmeter.jsp").forward(request, response);
            return;
        }

        try {
            int meterId = Integer.parseInt(meterIdStr);
            MeterDAO meterDAO = new MeterDAO();
            boolean updated = meterDAO.updateMeterInfo(meterId, meterNumber, status);
            if (!updated) {
                request.setAttribute("errorMessage", "Cập nhật thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("viewmeter.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("/ManagerApartment/accountant/managermeter");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("viewmeter.jsp?error=invalid_meter_id");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
