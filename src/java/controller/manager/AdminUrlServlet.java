package controller.manager;

import dao.UrlDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import validation.UrlUtils.FilterMapping;

@WebServlet(name="AdminUrlServlet", urlPatterns={"/manager/manage-urls"})
public class AdminUrlServlet extends HttpServlet {
   
    private UrlDAO urlDAO = new UrlDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                List<String> urlPatterns = Arrays.asList(urlPatternsArray);
                urlDAO.saveFilterMapping(filterName, urlPatterns);
                reloadFilterMappings(request);
            }
            response.sendRedirect(request.getContextPath() + "/manager/manage-urls?success=true");
        } else if ("delete".equals(action)) {
            int urlId = Integer.parseInt(request.getParameter("urlId"));
            urlDAO.deleteFilterMapping(urlId);
            reloadFilterMappings(request);
            response.sendRedirect(request.getContextPath() + "/manager/manage-urls?deleted=true");}
    }
    
    private void reloadFilterMappings(HttpServletRequest request) {
        request.getSession().setAttribute("reloadMessage", 
                "Filter mappings updated. Server restart may be required for changes to take effect.");
    }
}