/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ExportLogDAO;
import dao.ImportLogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ExportLog;
import model.ImportLog;

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name = "Im_Ex_Servlet", urlPatterns = {"/manager/allport"})
public class Im_Ex_Servlet extends HttpServlet {

    ImportLogDAO importLogDAO = new ImportLogDAO();
    ExportLogDAO exportLogDAO = new ExportLogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportType = request.getParameter("exportType");
        String status = request.getParameter("status");

        // Xử lý phân trang
        int pageIm = 1;
        int pageImSize = 3; // Số bản ghi mỗi trang

        String pageStr = request.getParameter("Impage");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                pageIm = Integer.parseInt(pageStr.trim());
            } catch (NumberFormatException e) {
                pageIm = 1; // Nếu lỗi, đặt về trang 1
            }
        }

        // Gọi DAO để lấy danh sách Import Logs
        List<ImportLog> listImportLogs = importLogDAO.searchImportLogs("", 0, status, "", "", pageIm, pageImSize);
        int totalImportRecords = importLogDAO.getTotalImportLogs(status);
        int totalImportPages = (int) Math.ceil((double) totalImportRecords /pageImSize );

        // Gọi DAO để lấy danh sách Export Logs
        int pageEx = 1;
        int pageExSize = 3; // Số bản ghi mỗi trang

        String pageStrr = request.getParameter("Expage");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                pageEx = Integer.parseInt(pageStr.trim());
            } catch (NumberFormatException e) {
                pageEx = 1; // Nếu lỗi, đặt về trang 1
            }
        }
        List<ExportLog> listExportLogs = exportLogDAO.searchExportLogs("", 0, exportType, "", "", pageEx, pageExSize);
        int totalExportRecords = exportLogDAO.getTotalExportLogs(exportType);
        int totalExportPages = (int) Math.ceil((double) totalExportRecords / pageExSize);

        // Gán dữ liệu vào request để hiển thị trên JSP
        request.setAttribute("allImportLogs", listImportLogs);
        request.setAttribute("selectedStatus", status);
        request.setAttribute("totalImportPages", totalImportPages);
        request.setAttribute("currentImPage", pageIm);

        request.setAttribute("allExportLogs", listExportLogs);
        request.setAttribute("selectedExportType", exportType);
        request.setAttribute("totalExportPages", totalExportPages);

        request.setAttribute("currentExPage", pageEx);

        // Chuyển hướng sang JSP
        request.getRequestDispatcher("import_export.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
