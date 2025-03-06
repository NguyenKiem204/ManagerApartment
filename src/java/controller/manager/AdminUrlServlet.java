package controller.manager;

import dao.UrlDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import validation.UrlUtils.FilterMapping;

@WebServlet(name="AdminUrlServlet", urlPatterns={"/manager/manage-urls"})
public class AdminUrlServlet extends HttpServlet {
   
    private UrlDAO urlDAO = new UrlDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String editId = request.getParameter("edit");
        if (editId != null && !editId.isEmpty()) {
            try {
                int id = Integer.parseInt(editId);
                FilterMapping mapping = urlDAO.getFilterMapping(id);
                if (mapping != null) {
                    request.setAttribute("editMapping", mapping);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID không hợp lệ");
            }
        }
        
        List<FilterMapping> mappings = urlDAO.getAllFilterMappings();
        request.setAttribute("filterMappings", mappings);
        request.getRequestDispatcher("manage-urls.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            String filterName = request.getParameter("filterName");
            String[] urlPatternsArray = request.getParameterValues("urlPatterns");
            
            if (filterName != null && urlPatternsArray != null && urlPatternsArray.length > 0) {
                List<String> urlPatterns = validateUrlPatterns(urlPatternsArray);
                
                if (urlPatterns.isEmpty()) {
                    request.setAttribute("error", "Tất cả URL pattern không hợp lệ. Vui lòng kiểm tra lại.");
                    doGet(request, response);
                    return;
                }
                
                urlDAO.saveFilterMapping(filterName, urlPatterns);
                reloadFilterMappings(request);
                response.sendRedirect(request.getContextPath() + "/manager/manage-urls?success=true");
            } else {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
                doGet(request, response);
            }
        } else if ("update".equals(action)) {
            String mappingId = request.getParameter("mappingId");
            String filterName = request.getParameter("filterName");
            String[] urlPatternsArray = request.getParameterValues("urlPatterns");
            
            if (mappingId != null && filterName != null && urlPatternsArray != null && urlPatternsArray.length > 0) {
                try {
                    int id = Integer.parseInt(mappingId);
                    List<String> urlPatterns = validateUrlPatterns(urlPatternsArray);
                    
                    if (urlPatterns.isEmpty()) {
                        request.setAttribute("error", "Tất cả URL pattern không hợp lệ. Vui lòng kiểm tra lại.");
                        request.setAttribute("editMapping", urlDAO.getFilterMapping(id));
                        doGet(request, response);
                        return;
                    }
                    urlDAO.deleteFilterMapping(id);
                    urlDAO.saveFilterMapping(filterName, urlPatterns);
                    
                    reloadFilterMappings(request);
                    response.sendRedirect(request.getContextPath() + "/manager/manage-urls?updated=true");
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID không hợp lệ");
                    doGet(request, response);
                }
            } else {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
                doGet(request, response);
            }
        } else if ("delete".equals(action)) {
            String urlId = request.getParameter("urlId");
            if (urlId != null && !urlId.isEmpty()) {
                try {
                    int id = Integer.parseInt(urlId);
                    urlDAO.deleteFilterMapping(id);
                    reloadFilterMappings(request);
                    response.sendRedirect(request.getContextPath() + "/manager/manage-urls?deleted=true");
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID không hợp lệ");
                    doGet(request, response);
                }
            }
        }
    }
    
    private List<String> validateUrlPatterns(String[] urlPatternsArray) {
        List<String> validPatterns = new ArrayList<>();
        
        for (String pattern : urlPatternsArray) {
            pattern = pattern.trim();
            if (pattern.isEmpty()) {
                continue;
            }
            if (isValidUrlPattern(pattern)) {
                validPatterns.add(pattern);
            }
        }
        
        return validPatterns;
    }
    
    private boolean isValidUrlPattern(String pattern) {
        if (pattern.equals("/*")) {
            return true;
        }
        if (!pattern.startsWith("/")) {
            return false;
        }
        if (pattern.contains("../") || pattern.contains("./")) {
            return false;
        }
        if (pattern.contains("<") || pattern.contains(">") || pattern.contains("\"") || 
            pattern.contains("'") || pattern.contains("&") || pattern.contains("%")) {
            return false;
        }
        if (pattern.startsWith("*.")) {
            return pattern.length() > 2;
        }
        if (pattern.length() > 200) {
            return false;
        }
        
        return true;
    }
    
    private void reloadFilterMappings(HttpServletRequest request) {
        request.getSession().setAttribute("reloadMessage", 
                "Filter mappings updated. Server restart may be required for changes to take effect.");
    }
}