<%-- 
    Document   : newpasword
    Created on : Jan 16, 2025, 4:32:18 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Reset Password</title>
</head>
<body>
    <h1>Đặt lại mật khẩu</h1>
    <form action="reset-password" method="POST">
        <input type="hidden" name="email" value="${email}" />
        <label for="password">Mật khẩu mới:</label>
        <input type="password" name="password" id="password" required />
        <label for="confirmPassword">Xác nhận mật khẩu:</label>
        <input type="password" name="confirmPassword" id="confirmPassword" required />
        <button type="submit">Đổi mật khẩu</button>
    </form>
</body>
</html>
