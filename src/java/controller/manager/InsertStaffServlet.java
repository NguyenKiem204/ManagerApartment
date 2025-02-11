package controller.manager;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//package controller;
//
//import dao.StaffDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import model.EmailUtil;
//import model.Staff;
//
///**
// *
// * @author fptshop
// */
//@WebServlet(name="InsertStaffServlet", urlPatterns={"/insertStaff"})
//public class InsertStaffServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        StaffDAO staffDAO = new StaffDAO();
//        try {
//            String phoneNumber = request.getParameter("phoneNumber");
//            String cccd = request.getParameter("cccd");
//            String mail = request.getParameter("email");
//            if (!phoneNumber.matches("\\d{10}")) {
//                request.getSession().setAttribute("mess", "Số điện thoại phải có đúng 12 chữ số!");
//                response.sendRedirect("addstaff.jsp");
//                return;
//            }
//
//            if (!cccd.matches("\\d{12}")) {
//                request.getSession().setAttribute("mess", "CCCD phải có đúng 12 chữ số!");
//                response.sendRedirect("addstaff.jsp");
//                return;
//            }
//            if (staffDAO.isStaffExists(phoneNumber, cccd, mail)) {
//                request.getSession().setAttribute("mess", "Số điện thoại, CCCD hoặc Email đã tồn tại!");
//                response.sendRedirect("addstaff.jsp");
//                return;
//            }
//            String fullName = request.getParameter("fullName");
//            LocalDate dob = LocalDate.parse(request.getParameter("dob"));
//            String sex = request.getParameter("sex");
//            String status = request.getParameter("status");
//        //int imageId = Integer.parseInt(request.getParameter("imageId"));
//            int imageId = 3;
//            int roleId = Integer.parseInt(request.getParameter("roleId"));
//            //int roleId = 5;
//
//        // Tạo mật khẩu ngẫu nhiên
//            String password = generateRandomPassword(3);
//
//            Staff newStaff = new Staff(fullName, password, phoneNumber, cccd, mail, dob, sex, status,imageId, roleId);
//            
//            int isAdded = staffDAO.insert(newStaff);
//
////        String msg = isAdded!=0 ? "Thêm cư dân thành công! Mật khẩu: " + password : "Thêm cư dân thất bại!";
////        request.setAttribute("mess", msg);
////        request.getRequestDispatcher("manageResident").forward(request, response);
//        if (isAdded != 0) {
//        EmailUtil.sendEmail(mail, password);
//        request.getSession().setAttribute("mess", "Thêm nhân viên thành công!");
//    } else {
//        request.getSession().setAttribute("mess", "Thêm nhân viên thất bại!");
//    }
//    response.sendRedirect("manageStaff");
//} catch (DateTimeParseException e) {
//    request.getSession().setAttribute("mess", "Ngày sinh không hợp lệ!");
//    response.sendRedirect("addstaff.jsp");
//} catch (Exception e) {
//    request.getSession().setAttribute("mess", "Lỗi không xác định: " + e.getMessage());
//    response.sendRedirect("addstaff.jsp");
//}
//        
//        /*if (isAdded != 0) {
//            out.write("{\"success\": true, \"message\": \"Thêm cư dân thành công!\"}");
//        } else {
//            out.write("{\"success\": false, \"message\": \"Thêm cư dân thất bại!\"}");
//        }*/
//
//        
//    }
//
//    // Hàm tạo mật khẩu ngẫu nhiên gồm 3 ký tự
//    private String generateRandomPassword(int length) {
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder password = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            int randomIndex = (int) (Math.random() * chars.length());
//            password.append(chars.charAt(randomIndex));
//        }
//        return password.toString();
//    }
//}