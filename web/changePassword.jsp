<%-- 
    Document   : changePassword
    Created on : Mar 6, 2025, 4:55:55 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Change Password</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h2>Change Password</h2>
    
    <form id="changePasswordForm">
        <label>New Password:</label>
        <input type="password" name="newPassword" id="newPassword" required>
        <small id="passwordError" style="color: red;"></small>
        <br>

        <label>Confirm Password:</label>
        <input type="password" name="confirmPassword" id="confirmPassword" required>
        <small id="confirmError" style="color: red;"></small>
        <br>

        <button type="button" id="submitChange">Change Password</button>
    </form>

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

