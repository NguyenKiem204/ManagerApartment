<%-- 
    Document   : staffresponsefeedbacksuccessfull
    Created on : Mar 9, 2025, 3:04:45 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully</title>
        <style>
            .container {
                max-width: 600px;
                background-color: #fff;
                padding: 20px;
                margin: auto;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #ff9800;
            }
            button {
                background-color: #ff9800;
                color: white;
                padding: 12px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                width: 100%;
                margin-top: 15px;
            }
            button:hover {
                background-color: #e68900;
            }

        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
<%
    Integer roleId = (Integer) session.getAttribute("roleId"); // Lấy role từ session
    String homeUrl = request.getContextPath() + "/home"; // Đường dẫn mặc định

    if (roleId != null) {
        switch (roleId) {
            case 4: // Technical role
                homeUrl = request.getContextPath() + "/technical/home";
                break;
            case 2: // Administrative role
                homeUrl = request.getContextPath() + "/administrative/home";
                break;
            case 3: // Example: Manager role
                homeUrl = request.getContextPath() + "/accountant/home";
                break;
            // Thêm case nếu có các vai trò khác
            default:
                homeUrl = request.getContextPath() + "/home"; // Mặc định nếu roleId không khớp
        }
    }
%>
        <div id = "main">
            <div class="container">
                <h2>Thank You!</h2>
                <p>Your form response has been submitted successfully.</p>
                <button onclick="window.location.href = '<%= homeUrl %>'">Comeback Home</button>
            </div>
        </div>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>
</html>
