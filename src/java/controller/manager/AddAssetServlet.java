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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.ApartmentAssets;
import model.AssetCategory;
import model.StatusApartmentAssets;
import validation.Validate;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddAssetServlet", urlPatterns = {"/manager/addasset"})
public class AddAssetServlet extends HttpServlet {

    private AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
    private StatusApartmentAssetsDAO statusApartmentAssetsDAO = new StatusApartmentAssetsDAO();

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
            out.println("<title>Servlet AddAssetServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAssetServlet at " + request.getContextPath() + "</h1>");
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

        List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
        List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

        request.setAttribute("listcategory", listcategory);
        request.setAttribute("liststatus", liststatus);
        request.getRequestDispatcher("addasset.jsp").forward(request, response);
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

        String assetName = request.getParameter("assetName");
        String categoryId_raw = request.getParameter("categoryId");
        String quantity_raw = request.getParameter("quantity");
        String location = request.getParameter("location");
        String boughtOn_raw = request.getParameter("boughtOn");
        String statusId_raw = request.getParameter("statusId");

        int categoryId = 0;
        int quantity = 0;
        int statusId = 0;
        Date boughtOn = null;
        try {
            categoryId = Integer.parseInt(categoryId_raw);
            quantity = Integer.parseInt(quantity_raw);
            statusId = Integer.parseInt(statusId_raw);
            boughtOn = Date.valueOf(boughtOn_raw);

            //check asset name
            if (assetName != null) {
                assetName = assetName.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            if (assetName == null || assetName.trim().isEmpty()) {
                request.setAttribute("error", "Asset name cannot be empty!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ dài title
            if (assetName.length() > 100) {
                request.setAttribute("error", "Asset name must be <100 characters!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidTitle(assetName)) {
                request.setAttribute("error", "Asset name contains invalid characters!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }
            //check quantity
            if (quantity <=0) {
                request.setAttribute("error", "Quantity of Asset must be >0!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }
            //location
            if (location != null) {
                location = location.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng dư thừa
            }

            
            // Kiểm tra độ dài title
            if (assetName.length() > 100) {
                request.setAttribute("error", "Asset name must be <100 characters!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }

//         Kiểm tra ký tự đặc biệt (chỉ cho phép chữ, số, khoảng trắng, và một số dấu câu)
            if (!Validate.isValidTitle(assetName)) {
                request.setAttribute("error", "Asset name contains invalid characters!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }
            //bought on k duoc chon tuong lai
            if (!Validate.validateBoughtOn(boughtOn_raw)) {
                request.setAttribute("error", "Bought on can not in the future!");
                List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
                List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();

                request.setAttribute("listcategory", listcategory);
                request.setAttribute("liststatus", liststatus);
                request.getRequestDispatcher("addasset.jsp").forward(request, response);
                return;
            }

            ApartmentAssets apartmentAssets = new ApartmentAssets(assetName,
                      assetCategoryDAO.selectById(categoryId),
                      boughtOn, quantity,
                      LocalDateTime.now(), location,
                      statusApartmentAssetsDAO.selectById(statusId));
            apartmentAssetsDAO.insert(apartmentAssets);
        } catch (NumberFormatException e) {
        }
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
