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
import java.util.List;
import model.ApartmentAssets;
import model.AssetCategory;
import model.StatusApartmentAssets;

/**
 *
 * @author admin
 */
@WebServlet(name="ManageAssetsServlet", urlPatterns={"/manager/manageassets"})
public class ManageAssetsServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ManageAssetsServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageAssetsServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ApartmentAssetsDAO apartmentAssetsDAO = new ApartmentAssetsDAO();
        List<ApartmentAssets> listFirstPage = apartmentAssetsDAO.selectFirstPage();

        //Phân trang
        // Lấy tham số từ request
        String pageSize_raw = request.getParameter("pageSize");
        String xpage = request.getParameter("page");

        // Khai báo biến
        int page = 1; // Trang mặc định
        int pageSize = 5; // Giá trị mặc định nếu không có tham số pageSize
        int size = apartmentAssetsDAO.selectAllToCount();
        int num = 1;

        try {
            // Kiểm tra và parse pageSize nếu có giá trị hợp lệ
            if (pageSize_raw != null && !pageSize_raw.isEmpty()) {
                pageSize = Integer.parseInt(pageSize_raw);
                if (pageSize <= 0) {
                    pageSize = 5; // Tránh chia cho 0
                }
            }
            // Tính tổng số trang
            num = (size % pageSize == 0) ? (size / pageSize) : (size / pageSize + 1);

            // Kiểm tra và parse page nếu có giá trị hợp lệ
            if (xpage != null && !xpage.isEmpty()) {
                page = Integer.parseInt(xpage);
                if (page < 1) {
                    page = 1; // Tránh giá trị âm
                } else if (page > num) {
                    page = num; // Giữ trang trong phạm vi hợp lệ
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số: " + e.getMessage());
            page = 1;
            pageSize = 5;
        }

        try {
            // Lấy tham số từ request
            String keySort_raw = request.getParameter("sort");
            String keySearch_raw = request.getParameter("keySearch");
            String catId_raw = request.getParameter("catId");

            // Chuyển đổi giá trị, xử lý null và exception
            int catId = (catId_raw != null && !catId_raw.isEmpty()) ? Integer.parseInt(catId_raw) : 0;
            int keySort = (keySort_raw != null && !keySort_raw.isEmpty()) ? Integer.parseInt(keySort_raw) : 0;
            String keySearch = (keySearch_raw != null) ? keySearch_raw.replaceAll("\\s+", " ").trim() : null;
            if (keySearch != null && keySearch.isEmpty()) {
                keySearch = null;
            }

// Nếu có tham số lọc, thực hiện tìm kiếm
            if (keySearch_raw != null || catId != 0 || keySort != 0 || xpage != null) {
                listFirstPage = apartmentAssetsDAO.getAllApartmentAssetsBySearchOrFilterOrSort(keySearch, catId, keySort, page, pageSize);
                int numberOfLine = apartmentAssetsDAO.getNumberOfApartmentAssetsBySearchOrFilterOrSort(keySearch, catId);
                num = (numberOfLine % pageSize == 0) ? (numberOfLine / pageSize) : (numberOfLine / pageSize + 1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi o exception!");
        }

        request.setAttribute("page", page);
        request.setAttribute("num", num);
        //send list role of staff for feedback of manager
//        RoleDAO rdao = new RoleDAO();
//        List<Role> listrole = rdao.selectAll();
//        request.setAttribute("listrole", listrole);

        AssetCategoryDAO assetCategoryDAO = new AssetCategoryDAO();
        StatusApartmentAssetsDAO statusApartmentAssetsDAO = new StatusApartmentAssetsDAO();
        List<AssetCategory> listcategory = assetCategoryDAO.selectAll();
        List<StatusApartmentAssets> liststatus = statusApartmentAssetsDAO.selectAll();
        
        request.setAttribute("listcategory", listcategory);
        request.setAttribute("liststatus", liststatus);
//
        request.setAttribute("listas", listFirstPage);
        
        request.getRequestDispatcher("manageassets.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
