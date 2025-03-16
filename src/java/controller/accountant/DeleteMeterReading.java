/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import dao.ExportLogDAO;
import dao.ImportLogDAO;
import dao.MeterDAO;
import dao.MeterReadingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.MeterReading;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "DeleteMeterReading", urlPatterns = {"/accountant/delete-meter-reading"})
public class DeleteMeterReading extends HttpServlet {

    private MeterReadingDAO meterReadingDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        meterReadingDAO = new MeterReadingDAO();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String previousURL = request.getHeader("Referer");
        if (previousURL != null) {
            request.setAttribute("previousPage", previousURL);
        }
        try {
            int readingId = Integer.parseInt(request.getParameter("readingId"));
            meterReadingDAO.deleteMeterReadingPermanently(readingId);
            response.sendRedirect(previousURL);

        } catch (SQLException | NumberFormatException e) {
            handleError(request, response, "Error deleting meter reading", e);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response,
            String message, Exception e) throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorCode", "500");
        request.setAttribute("errorMessage", message + ": " + e.getMessage());
        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return;
    }

}
