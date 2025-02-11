
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
            .error{
                color: red;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Đặt lại mật khẩu</h1>
            <form action="reset-password" method="POST" id="resetForm">
                <input type="hidden" name="email" value="${sessionScope.email}" />

                <label for="password">New Password:</label>
                <input type="password" name="password" id="password" required />

                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" name="confirmPassword" id="confirmPassword" required />
                <p class="error" id="passwordError">${requestScope.passwordError}</p>

                <button type="submit">Change Password</button>
            </form>
        </div>
    </body>


    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.getElementById("resetForm");
            const password = document.getElementById("password");
            const confirmPassword = document.getElementById("confirmPassword");
            const passwordError = document.getElementById("passwordError");

            form.addEventListener("submit", function (event) {
                let isValid = true;
                passwordError.textContent = "";
                if (password.value.length < 6) {
                    passwordError.textContent = "The password must be at least 6 characters long!";
                    isValid = false;
                }
                else if (password.value !== confirmPassword.value) {
                    passwordError.textContent = "The password does not match!";
                    isValid = false;
                } else if (!/\d/.test(password.value) || !/[!@#$%^&*(),.?":{}|<>]/.test(password.value) ||
                        !/[a-zA-Z]/.test(password.value) ) {
                    passwordError.textContent = "The password must contain at least one digit, one special character, and one letter!";
                    isValid = false;
                }

                if (!isValid) {
                    event.preventDefault();
                }
            });
        });
    </script>

</html>
