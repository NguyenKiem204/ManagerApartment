///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller.accountant;
//
//import dao.MeterDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import model.Meter;
//
///**
// *
// * @author Hoang-Tran
// */
//@WebServlet(name = "AddMeterServlet", urlPatterns = {"accountant/add-meter"})
//public class AddMeterServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        MeterDAO meterDAO = new MeterDAO();
//            // Lấy dữ liệu từ form
//            int apartmentId = Integer.parseInt(request.getParameter("apartmentId"));
//            String meterType = request.getParameter("meterType");
//            String meterNumber = request.getParameter("meterNumber");
//            String installationDate = request.getParameter("installationDate");
//            String status = request.getParameter("status");        
//            // Tạo đối tượng Meter
//            LocalDateTime installationDateTime = LocalDateTime.parse(installationDate);
//           Meter meter = new Meter();
//            meter.setApartmentId(apartmentId);
//            meter.setMeterType(meterType);
//            meter.setMeterNumber(meterNumber);
//            meter.setInstallationDate(LocalDateTime.parse(installationDate));
//            meter.setStatus(status);
//
//        try {
//            // Thêm vào database
//            int newMeterId = meterDAO.addMeter(meter);
//        } catch (SQLException ex) {
//            Logger.getLogger(AddMeterServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//           response.sendRedirect("viewmeter.jsp"  );
//    }
//}
