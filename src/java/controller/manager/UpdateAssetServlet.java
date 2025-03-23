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
import java.util.List;
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
        String assetId_raw = request.getParameter("assetId");
        int assetId = 0;
        try {
            assetId = Integer.parseInt(assetId_raw);
            ApartmentAssets apartmentAssets = apartmentAssetsDAO.selectById(assetId);
            request.setAttribute("asset", apartmentAssets);
        } catch (Exception e) {
        }

        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
        StatusApartmentAssetsDAO statusApartmentAssetsDAO = new StatusApartmentAssetsDAO();
        List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
        List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

        request.setAttribute("listcategory", listcategory);
        request.setAttribute("liststatus", liststatus);
        request.getRequestDispatcher("updateasset.jsp").forward(request, response);
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
        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();
        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
        StatusApartmentAssetsDAO statusApartmentAssetsDAO = new StatusApartmentAssetsDAO();

        String assetId_raw = request.getParameter("assetId");
        String assetName = request.getParameter("assetName").trim();
        String categoryId_raw = request.getParameter("categoryId");
        String quantity_raw = request.getParameter("quantity").trim();
        String location = request.getParameter("location").trim();
        String statusId_raw = request.getParameter("statusId");

        int assetId = 0;
        int categoryId = 0;
        int quantity = 0;
        int statusId = 0;
        try {
            assetId = Integer.parseInt(assetId_raw);
            categoryId = Integer.parseInt(categoryId_raw);
            quantity = Integer.parseInt(quantity_raw);
            statusId = Integer.parseInt(statusId_raw);
            List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
            List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

            ApartmentAssets apartmentAssets = apartmentAssetsDAO.selectById(assetId);

            //check asset name
            if (assetName != null) {
                assetName = assetName.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            if (assetName == null || assetName.trim().isEmpty()) {
                request.setAttribute("error", "Asset name cannot be empty!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ dài ten asset
            if (assetName.length() > 100) {
                request.setAttribute("error", "Asset name must be <100 characters!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidTitle(assetName)) {
                request.setAttribute("error", "Asset name contains invalid characters!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }
            //check asset duplicate 
            if(apartmentAssetsDAO.isAssetNameExists(assetName)){
                request.setAttribute("error", "Asset name already exists!");
                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }
            //check quantity
            if (quantity <= 0) {
                request.setAttribute("error", "Quantity of Asset must be >0!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }
            //location
            if (location != null) {
                location = location.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            // Kiểm tra độ dài title
            if (location.length() > 100) {
                request.setAttribute("error", "Location must be <100 characters!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidLocation(location)) {
                request.setAttribute("error", "Location contains invalid characters!");

                request.setAttribute("asset", apartmentAssets);
                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("updateasset.jsp").forward(request, response);
                return;
            }

            ApartmentAssets apartmentAssets1 = new ApartmentAssets(assetId, assetName,
                      assetCategoryDAO.selectById(categoryId),
                      apartmentAssets.getBoughtOn(), quantity,
                      LocalDateTime.now(), location,
                      statusApartmentAssetsDAO.selectById(statusId));
            apartmentAssetsDAO.update(apartmentAssets1);
        } catch (NumberFormatException e) {
        }
//        request.getRequestDispatcher("/manager/manageassets").forward(request, response);
        response.sendRedirect("manageassets");
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
