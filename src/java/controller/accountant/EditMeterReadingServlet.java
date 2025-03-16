/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MeterReading;
import model.Staff;
import dao.MeterDAO;
import dao.MeterReadingDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(name = "EditMeterReadingServlet", urlPatterns = {"/accountant/edit-meter-reading"})
public class EditMeterReadingServlet extends HttpServlet {

    private MeterReadingDAO meterReadingDAO;
    private MeterDAO meterDAO;

    @Override
    public void init() throws ServletException {
        meterReadingDAO = new MeterReadingDAO();
        meterDAO = new MeterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        String previousURL = request.getHeader("Referer");
        if (previousURL != null) {
            request.setAttribute("previousPage", previousURL);
        }

        try {
            if (idParam != null && !idParam.isEmpty()) {
                int readingId = Integer.parseInt(idParam);
                MeterReading reading = meterReadingDAO.getMeterReadingById(readingId);

                if (reading == null) {
                    response.sendRedirect(request.getContextPath() + "/accountant/manager-meter-reading?error="
                            + java.net.URLEncoder.encode("Meter reading not found.", "UTF-8"));
                    return;
                }
                request.setAttribute("meterNumber", reading.getMeterNumber());
                request.setAttribute("apartmentName", reading.getApartmentName());
                request.setAttribute("meterType", reading.getMeterType());
                request.setAttribute("reading", reading);
                request.setAttribute("isEdit", true);
                request.setAttribute("pageTitle", "Update meter reading");

            } else {
                request.setAttribute("isEdit", false);
                request.setAttribute("pageTitle", "Add new meter reading");
            }
            request.setAttribute("meters", meterDAO.getAllActiveMeters());
            request.getRequestDispatcher("edit-meter-reading.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/accountant/manager-meter-reading?error="
                    + java.net.URLEncoder.encode("Invalid ID.", "UTF-8"));
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/accountant/manager-meter-reading?error="
                    + java.net.URLEncoder.encode("Database error: " + e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        try {
            String previousPage = request.getParameter("previousPage");
            String readingIdParam = request.getParameter("readingId");
            String meterIdParam = request.getParameter("meterId");
            String previousReadingParam = request.getParameter("previousReading");
            String currentReadingParam = request.getParameter("currentReading");
            String readingDateParam = request.getParameter("readingDate");
            String readingMonthParam = request.getParameter("readingMonth");
            String readingYearParam = request.getParameter("readingYear");
            String statusParam = request.getParameter("status");
            System.out.println("readingId: " + readingDateParam + "-meterId: " + meterIdParam + "-previousReading: " + previousReadingParam + "-currentReading: " + currentReadingParam + "-readingDate" + readingDateParam
                    + "-readingMonth: " + readingMonthParam + "-readingYear: " + readingYearParam + "-status: " + statusParam);
            if (meterIdParam == null || previousReadingParam == null || currentReadingParam == null
                    || readingDateParam == null || readingMonthParam == null || readingYearParam == null) {

                request.setAttribute("errorMessage", "Please fill in all required information.");
                doGet(request, response);
                return;
            }
            int meterId = Integer.parseInt(meterIdParam);
            BigDecimal previousReading = new BigDecimal(previousReadingParam);
            BigDecimal currentReading = new BigDecimal(currentReadingParam);
            int readingMonth = Integer.parseInt(readingMonthParam);
            int readingYear = Integer.parseInt(readingYearParam);
            String status = statusParam != null ? statusParam : "Active";
            if (currentReading.compareTo(previousReading) < 0) {
                response.sendRedirect(request.getContextPath() + "/accountant/edit-meter-reading?id="
                        + readingIdParam + "&errorMessage="
                        + java.net.URLEncoder.encode("The current reading cannot be less than the previous reading.", "UTF-8"));
                return;
            }

            MeterReading reading = new MeterReading();
            Integer readingId = null;
            if (readingIdParam != null && !readingIdParam.isEmpty()) {
                readingId = Integer.parseInt(readingIdParam);
                reading.setReadingId(readingId);
            }
            MeterReading readingOld = meterReadingDAO.getMeterReadingById(readingId);
            reading.setMeterId(meterId);
            reading.setPreviousReading(previousReading);
            reading.setCurrentReading(currentReading);
            reading.setReadingDate(readingOld.getReadingDate());
            reading.setReadingMonth(readingMonth);
            reading.setReadingYear(readingYear);
            reading.setStaffId(staff.getStaffId());
            reading.setStatus(status);
            BigDecimal consumption = currentReading.subtract(previousReading);
            reading.setConsumption(consumption);

            boolean isUpdate = readingIdParam != null && !readingIdParam.isEmpty();
            int result;

            if (isUpdate) {
                result = meterReadingDAO.updateMeterReading(reading);
                if (result > 0) {
                    response.sendRedirect(previousPage+"&success="
                            + java.net.URLEncoder.encode("The meter reading has been successfully updated.", "UTF-8")
                            + "&highlight=" + reading.getReadingId());
                } else {
                    response.sendRedirect(previousPage+"&error="
                            + java.net.URLEncoder.encode("Unable to update the meter reading.", "UTF-8"));
                }
            } else {
                result = meterReadingDAO.addMeterReading(reading);
                if (result > 0) {
                    response.sendRedirect(previousPage+"&success="
                            + java.net.URLEncoder.encode("New meter reading added successfully.", "UTF-8")
                            + "&highlight=" + result);
                } else {
                    response.sendRedirect(previousPage+"&error="
                            + java.net.URLEncoder.encode("Unable to add new meter reading.", "UTF-8"));
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid numeric value: " + e.getMessage());
            doGet(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}
