///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
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
//import java.sql.SQLException;
//import model.Meter;
//
///**
// *
// * @author Hoang-Tran
// */
//@WebServlet(name="EditServlet", urlPatterns={"/accountant/edit-meter"})
//public class EditServlet extends HttpServlet {
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        
//    } 
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        //processRequest(request, response);
//        MeterDAO meterDAO = new MeterDAO();
//         String action = request.getParameter("action");
//        
//        if ("edit".equals(action)) {
//            int meterId = Integer.parseInt(request.getParameter("meterId"));
//            try {
//                Meter meter = meterDAO.getMeterById(meterId);
//                request.setAttribute("meter", meter);
//                request.getRequestDispatcher("editMeter.jsp").forward(request, response);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//       // processRequest(request, response);
//       int meterId = Integer.parseInt(request.getParameter("meterId"));
//        int apartmentId = Integer.parseInt(request.getParameter("apartmentId"));
//        String meterType = request.getParameter("meterType");
//        String meterNumber = request.getParameter("meterNumber");
//        String installationDate = request.getParameter("installationDate");
//        String status = request.getParameter("status");
//         MeterDAO meterDAO = new MeterDAO();
//        Meter meter = new Meter();
//        meter.setMeterId(meterId);
//        meter.setApartmentId(apartmentId);
//        meter.setMeterType(meterType);
//        meter.setMeterNumber(meterNumber);
//        meter.setInstallationDate(java.time.LocalDateTime.parse(installationDate));
//        meter.setStatus(status);
//
//        try {
//            meterDAO.updateMeter(meter);
//            response.sendRedirect("viewmeter.jsp");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
