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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Meter;

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name = "AddMeterServlet", urlPatterns = {"/accountant/add-meter"})
public class AddMeterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // lay gia tri trong form
        MeterDAO meterDAO = new MeterDAO();
        try {
            int apartmentId = Integer.parseInt(request.getParameter("apartmentId"));
            String meterType = request.getParameter("meterType");
            String meterNumber = request.getParameter("meterNumber");
            String installationDate = request.getParameter("installationDate");
            String status = request.getParameter("status");
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy"); // Định dạng ngày
                LocalDate parsedDate = LocalDate.parse(installationDate, formatter);
                LocalDateTime installDateTime = parsedDate.atStartOfDay();
                Meter meter = new Meter(apartmentId, meterType, meterNumber, installDateTime, status);
                System.out.println("Parsed DateTime: " + installDateTime);
                int newMeter = meterDAO.addMeter(meter);

            if (newMeter > 0) {
                request.setAttribute("message", "Meter added successfully!");
            } else {
                request.setAttribute("error", "Failed to add Meter!");
            }

            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
//            Meter meter = new Meter(apartmentId, meterType, meterNumber, installDateTime, status);

//            // Gọi DAO để thêm vào database
//            int newMeter = meterDAO.addMeter(meter);
//
//            if (newMeter > 0) {
//                request.setAttribute("message", "Meter added successfully!");
//            } else {
//                request.setAttribute("error", "Failed to add Meter!");
//            }

            // Chuyển hướng về danh sách
            response.sendRedirect("viewmeter.jsp");

        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Invalid input data!");
            //request.getRequestDispatcher("addmeter.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
