/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.MeterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.Meter;

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name = "ManagerMeter", urlPatterns = {"/accountant/managermeter"})
public class ManagerMeter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         MeterDAO meterDAO = new MeterDAO();
//         String indexPage = request.getParameter("index");
        String indexPage = request.getParameter("index");
        int index = (indexPage != null) ? Integer.parseInt(indexPage) : 1;
// Tổng số bản ghi & số trang cuối cùng
        int totalRecords = meterDAO.getTotalRecords();
        int endPage = (int) Math.ceil((double) totalRecords / 3); // Chia trang chính xác hơn
// Lấy danh sách công tơ theo trang
        List<Meter> pagedMeters = meterDAO.paegingMeter(index);
// Gửi dữ liệu tới JSP
        request.setAttribute("meterList", pagedMeters);
        request.setAttribute("endP", endPage);
        request.setAttribute("currentPage", index);
// Chuyển hướng đến JSP
        request.getRequestDispatcher("viewmeter.jsp").forward(request, response);

//MeterDAO meterDAO = new MeterDAO();
//    
//    // Nhận giá trị meterType từ request
//    String meterType = request.getParameter("meterType");
//
//    int page = 1;
//    int pageSize = 3; // Số bản ghi mỗi trang
//
//    // Nhận giá trị page từ request nếu có
//    String pageStr = request.getParameter("page");
//    if (pageStr != null && !pageStr.trim().isEmpty()) {
//        try {
//            page = Integer.parseInt(pageStr.trim());
//        } catch (NumberFormatException e) {
//            page = 1; // Nếu lỗi, đặt về trang 1
//        }
//    }
//
//    // Lấy danh sách đồng hồ đã lọc theo meterType
//    List<Meter> meterList = meterDAO.searchMeters(meterType, page, pageSize);
//    int totalRecords = meterDAO.getTotalMeters(meterType);
//    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//    // Đẩy dữ liệu về JSP
//    request.setAttribute("meterList", meterList);
//    request.setAttribute("selectedMeterType", meterType); // Lưu meterType đã chọn
//    request.setAttribute("totalPages", totalPages);
//    request.setAttribute("currentPage", page);
//
//    request.getRequestDispatcher("viewmeter.jsp").forward(request, response);
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
