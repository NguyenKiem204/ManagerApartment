package controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class BlockJspHtmlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        // Chặn truy cập trực tiếp vào file .jsp và .html nhưng cho phép forward từ servlet
        if ((requestURI.endsWith(".jsp") || requestURI.endsWith(".html"))
            && request.getDispatcherType() == DispatcherType.REQUEST
            && !requestURI.endsWith("/error-403.jsp")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error-403");
            return;
        }
        
        chain.doFilter(request, response);
    }
}
