/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import dao.ContractDAO;
import dao.ResidentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Contract;
import model.EmailUtil;
import org.json.JSONObject;

/**
 *
 * @author fptshop
 */
@WebServlet(name = "ViewContractServlet", urlPatterns = {"/owner/viewContract"})
public class ViewContractServlet extends HttpServlet {
    ResidentDAO residentDAO = new ResidentDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int tenantId = Integer.parseInt(request.getParameter("tenantId"));
        ContractDAO contractDAO = new ContractDAO();
        Contract contract = contractDAO.getContractByResidentId(tenantId);

        request.setAttribute("contract", contract);
        request.getRequestDispatcher("viewcontract.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            int tenantId = Integer.parseInt(request.getParameter("tenantId"));
            String newEndDateStr = request.getParameter("newEndDate");

            // Kiểm tra ngày nhập vào
            if (newEndDateStr == null || newEndDateStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "New end date cannot be empty!");
                out.write(jsonResponse.toString());
                return;
            }

            try {
                // Chuyển đổi ngày từ String sang LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate newEndDate = LocalDate.parse(newEndDateStr, formatter);
                ContractDAO contractDAO = new ContractDAO();
                Contract contract = contractDAO.getContractByResidentId(tenantId);

                if (contract == null) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Contract not found!");
                    out.write(jsonResponse.toString());
                    return;
                }

                LocalDate oldEndDate = contract.getLeaseEndDate();

                // Kiểm tra ngày mới phải lớn hơn ngày cũ
                if (newEndDate.isBefore(oldEndDate)) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "New end date must be after the current end date!");
                    out.write(jsonResponse.toString());
                    return;
                }
                boolean success = contractDAO.renewContract(tenantId, newEndDate);

                if (success) {
                    String subject = "Your Contract has been Renewed!";
                    String message = "Dear " + residentDAO.selectById(tenantId).getFullName() + ",\n\n"
                        + "Your contract has been successfully renewed.\n"
                        + "New End Date: " + newEndDate + "\n\n"
                        + "Best regards,\nApartment Management System";

                    EmailUtil.sendEmailRenewContract(residentDAO.selectById(tenantId).getEmail(), subject, message);
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Contract renewed successfully!");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Failed to renew contract. Please try again.");
                }
            } catch (DateTimeParseException e) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid date format. Please enter a valid date.");
            }

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "An unexpected error occurred: " + e.getMessage());
        }

        out.write(jsonResponse.toString());
    }

}
