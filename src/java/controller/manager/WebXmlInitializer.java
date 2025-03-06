/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager;

/**
 *
 * @author nkiem
 */

import dao.UrlDAO;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.EnumSet;
import java.util.List;
import validation.UrlUtils.FilterMapping;

@WebListener
public class WebXmlInitializer implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        UrlDAO urlDAO = new UrlDAO();
        List<FilterMapping> mappings = urlDAO.getAllFilterMappings();
        
        for (FilterMapping mapping : mappings) {
            String filterName = mapping.getFilterName();
            List<String> urlPatterns = mapping.getUrlPatterns();
            
            if (filterName == null || filterName.isEmpty() || urlPatterns == null || urlPatterns.isEmpty()) {
                continue;
            }
            FilterRegistration filterReg = context.getFilterRegistration(filterName);
            if (filterReg != null) {
                for (String urlPattern : urlPatterns) {
                    filterReg.addMappingForUrlPatterns(
                        EnumSet.of(
                            DispatcherType.REQUEST, 
                            DispatcherType.FORWARD, 
                            DispatcherType.INCLUDE, 
                            DispatcherType.ERROR, 
                            DispatcherType.ASYNC
                        ), 
                        true, 
                        urlPattern
                    );
                }
                
                System.out.println("Dynamically mapped filter '" + filterName + 
                                  "' to " + urlPatterns.size() + " URL patterns");
            } else {
                System.err.println("Filter '" + filterName + "' not found in web.xml, cannot map dynamically");
            }
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed
    }
}