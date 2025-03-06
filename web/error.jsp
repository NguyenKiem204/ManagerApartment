<%-- 
    Document   : error
    Created on : Feb 25, 2025, 9:34:11 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            
            .error-container {
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
                max-width: 600px;
                width: 100%;
                text-align: center;
            }
            
            .error-icon {
                font-size: 60px;
                color: #e74c3c;
                margin-bottom: 20px;
            }
            
            .error-title {
                font-size: 24px;
                color: #333;
                margin-bottom: 15px;
            }
            
            .error-message {
                color: #666;
                margin-bottom: 20px;
                line-height: 1.5;
            }
            
            .error-details {
                background-color: #f8f9fa;
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 20px;
                text-align: left;
                max-height: 200px;
                overflow-y: auto;
                font-family: monospace;
                font-size: 14px;
            }
            
            .back-button {
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 4px;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            
            .back-button:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="error-container">
            <div class="error-icon">
                <i class="fas fa-exclamation-circle"></i>
            </div>
            
            <h1 class="error-title">Something went wrong</h1>
            
            <p class="error-message">
                <% if(request.getAttribute("errorMessage") != null) { %>
                    <%= request.getAttribute("errorMessage") %>
                <% } else { %>
                    An unexpected error occurred while processing your request. Please try again later.
                <% } %>
            </p>
            
            <% if(exception != null) { %>
                <div class="error-details">
                    <strong>Error details:</strong><br>
                    <%= exception.getMessage() %>
                </div>
            <% } %>
            
            <button class="back-button" onclick="window.history.back()">Go Back</button>
        </div>
    </body>
</html>