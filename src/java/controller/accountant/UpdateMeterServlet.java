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
@WebServlet(name="UpdateMeterServlet", urlPatterns={"/accountant/update-meter"})
public class UpdateMeterServlet extends HttpServlet {
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     String meterIdStr = request.getParameter("meterId");
    String status = request.getParameter("status");

    System.out.println("Received meterId: " + meterIdStr); // Debug
    System.out.println("Received status: " + status);

    if (meterIdStr == null || meterIdStr.trim().isEmpty()) {
        response.sendRedirect("viewmeter.jsp?error=missing_meter_id");
        return;
    }

    try {
        int meterId = Integer.parseInt(meterIdStr);
        MeterDAO meterDAO = new MeterDAO();
        boolean updated = meterDAO.updateMeterStatus(meterId, status);
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
