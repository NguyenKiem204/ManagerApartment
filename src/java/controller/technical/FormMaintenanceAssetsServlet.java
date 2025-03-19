/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.technical;

import dao.ApartmentAssetsDAO;
import dao.AssetMaintenanceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.LocalDate;
import model.AssetMaintenance;
import model.Staff;

/**
 *
 * @author admin
 */
@WebServlet(name = "FormMaintenanceAssetsServlet", urlPatterns = {"/technical/formmaintenanceassets"})
public class FormMaintenanceAssetsServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        request.getRequestDispatcher("formMaintenanceAssets.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        AssetMaintenanceDAO assetMaintenanceDAO = new AssetMaintenanceDAO();
        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();

        String assetId_raw = request.getParameter("assetID");
        String maintenanceDate_raw = request.getParameter("maintenanceDate");
        String description = request.getParameter("description");
        String cost_raw = request.getParameter("cost");
        String nextMaintenanceDate_raw = request.getParameter("nextMaintenanceDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int assetId = 0;
        Date maintenanceDate;
        BigDecimal cost = new BigDecimal(cost_raw);
        Date nextMaintenanceDate;

        try {
            assetId = Integer.parseInt(assetId_raw);
            maintenanceDate = Date.valueOf(LocalDate.parse(maintenanceDate_raw, formatter));
            nextMaintenanceDate = Date.valueOf(LocalDate.parse(nextMaintenanceDate_raw, formatter));

            //validate assetID
            //check ngày bảo trì(ko thể ở trong tương lai)
            //check description
            //check giá nhập vào(ko âm)
            //check ngày bảo trì tiếp theo(trong tương lai)
            AssetMaintenance am = new AssetMaintenance(apartmentAssetsDAO.selectById(assetId),
                      maintenanceDate, staff, description, cost, nextMaintenanceDate);
            assetMaintenanceDAO.insert(am);
        } catch (NumberFormatException e) {
        }
        request.getRequestDispatcher("/technical/home").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
