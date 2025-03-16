/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ApartmentAssetsDAO;
import dao.AssetCategoryDAO;
import dao.StatusApartmentAssetsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.sql.Date;
import java.time.LocalDate;
import model.ApartmentAssets;
import model.AssetCategory;
import model.StatusApartmentAssets;
import validation.Validate;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateAssetServlet", urlPatterns = {"/manager/updateasset"})
public class UpdateAssetServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateAssetServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAssetServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();
        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
        StatusApartmentAssetsDAO statusApartmentAssetsDAO = new StatusApartmentAssetsDAO();

        String assetId_raw = request.getParameter("assetId");
        String assetName = request.getParameter("assetName").trim();
        String categoryId_raw = request.getParameter("categoryId");
        String quantity_raw = request.getParameter("quantity").trim();
        String location = request.getParameter("location").trim();
        String statusId_raw = request.getParameter("status");

        int assetId = 0;
        int categoryId = 0;
        int quantity = 0;
        int statusId = 0;
        try {
            assetId = Integer.parseInt(assetId_raw);
            categoryId = Integer.parseInt(categoryId_raw);
            quantity = Integer.parseInt(quantity_raw);
            statusId = Integer.parseInt(statusId_raw);

            //check asset name
            if (assetName != null) {
                assetName = assetName.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            if (assetName == null || assetName.trim().isEmpty()) {
//            request.setAttribute(error, "Title cannot be empty!");
//            request.setAttribute("listrole", listrole);
//            request.getRequestDispatcher("feedback.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ dài title
            if (assetName.length() > 100) {
//            request.setAttribute(error, "Title must be between 5 and 100 characters!");
//            request.setAttribute("listrole", listrole);
//            request.getRequestDispatcher("feedback.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidTitle(assetName)) {
//            request.setAttribute(error, "Title contains invalid characters!");
//            request.setAttribute("listrole", listrole);
//            request.getRequestDispatcher("feedback.jsp").forward(request, response);
                return;
            }
            //check quantity
            //check location

            ApartmentAssets apartmentAssets = new ApartmentAssets(assetId, assetName,
                      assetCategoryDAO.selectById(categoryId),
                      Date.valueOf(LocalDate.now()), quantity,
                      LocalDateTime.now(), location,
                      statusApartmentAssetsDAO.selectById(statusId));
            apartmentAssetsDAO.update(apartmentAssets);
        } catch (NumberFormatException e) {
        }
//        request.getRequestDispatcher("/manager/manageassets").forward(request, response);
        response.sendRedirect("manageassets");
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
