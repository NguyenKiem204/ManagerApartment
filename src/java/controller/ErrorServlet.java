package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/error-handler")
public class ErrorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleError(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        if (statusCode == null) {
            statusCode = 500;
            errorMessage = "Unknown error occurred!";
        }
        if (throwable != null) {
            errorMessage = throwable.getMessage();
        }
        request.setAttribute("errorCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        response.setStatus(statusCode);

        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
    }
}
