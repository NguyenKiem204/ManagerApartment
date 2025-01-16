
<%-- 
    Document   : newpassword
    Created on : Jan 16, 2025, 4:32:18 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                color: #333;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                max-width: 400px;
                width: 100%;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }
            h1 {
                text-align: center;
                color: #ff8c00;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            input {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }
            button {
                width: 100%;
                padding: 10px;
                background-color: #ff8c00;
                color: white;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background-color: #e67e00;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Đặt lại mật khẩu</h1>
            <form action="reset-password" method="POST">
                <input type="hidden" name="email" value="${sessionScope.email}" />
                <label for="password">Mật khẩu mới:</label>
                <input type="password" name="password" id="password" required />
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" name="confirmPassword" id="confirmPassword" required />
                <button type="submit">Đổi mật khẩu</button>
            </form>
        </div>
    </body>
</html>
