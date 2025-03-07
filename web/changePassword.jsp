<%-- 
    Document   : changePassword
    Created on : Mar 6, 2025, 4:55:55 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #FFE6CC; 
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        label {
            font-weight: bold;
            align-self: flex-start;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
            margin-bottom: 10px;
            text-align: center; /* Căn giữa text */
        }

        input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        small {
            color: red;
            height: 20px;
            margin-bottom: 5px;
        }

        button {
            width: 100%;
            padding: 12px;
            border: none;
            background: linear-gradient(to right, #4CAF50, #2E7D32); 
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
            margin-top: 10px;
        }

        button:hover {
            background: linear-gradient(to right, #2E7D32, #1B5E20);
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Change Password</h2>
        
        <form id="changePasswordForm">
            <label for="newPassword">New Password:</label>
            <input type="password" name="newPassword" id="newPassword" required>
            <small id="passwordError"></small>

            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" name="confirmPassword" id="confirmPassword" required>
            <small id="confirmError"></small>

            <button type="button" id="submitChange">Change Password</button>
        </form>
    </div>
    <script>
        $(document).ready(function () {
            $("#submitChange").click(function () {
                var newPassword = $("#newPassword").val().trim();
                var confirmPassword = $("#confirmPassword").val().trim();

                // Kiểm tra mật khẩu có ít nhất 8 ký tự
                if (newPassword.length < 8) {
                    $("#passwordError").text("Password must be at least 8 characters.");
                    return;
                } else {
                    $("#passwordError").text("");
                }

                // Kiểm tra mật khẩu nhập lại có trùng không
                if (newPassword !== confirmPassword) {
                    $("#confirmError").text("Passwords do not match.");
                    return;
                } else {
                    $("#confirmError").text("");
                }

                $.ajax({
                    url: "/ManagerApartment/changePassword",
                    type: "POST",
                    data: { newPassword: newPassword },
                    success: function (response) {
                        alert("Password changed successfully! Please log in again.");
                        window.location.href = "/ManagerApartment/login.jsp";
                    },
                    error: function (xhr) {
                        alert("Error changing password: " + xhr.responseText);
                    }
                });
            });
        });
    </script>
</body>
</html>

