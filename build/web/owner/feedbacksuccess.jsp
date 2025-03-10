<%-- 
    Document   : sidebar
    Created on : Feb 7, 2025, 2:26:08 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="assets/css/pages/index.css">
        <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #FFA384;
                margin: 0;
                padding: 20px;
            }
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
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: #555;
            }
            input, select, textarea {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            textarea {
                resize: vertical;
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
            .rating {
                display: flex;
                justify-content: center;
                flex-direction: row-reverse;
            }
            .rating input {
                display: none;
            }
            .rating label {
                font-size: 30px;
                color: #ccc;
                cursor: pointer;
                transition: color 0.3s;
            }
            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: #FFD700;
            }
            .result {
                text-align: center;
                font-size: 16px;
                margin-top: 10px;
                color: #333;
            }
            .error {
                color: red;
                font-size: 14px;
                display: none;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>

        <div id = "main">
            <div class="container">
                <h2>Thank You!</h2>
                <p>Your feedback has been submitted successfully.</p>
                <button onclick="window.location.href = 'feedback'">Submit Another Feedback</button>
            </div>
        </div>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>
</html>
