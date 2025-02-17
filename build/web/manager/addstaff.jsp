<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="assets/css/bootstrap.css" />
    <title>Thêm Nhân Viên</title>
    <style>
        body {
            font-family: Arial, sans-serif;
                background-color: #ffa384;
            padding: 20px;
        }

        .form-container {
            max-width: 400px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-top: 10px;
        }

        input, select, button {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #45a049;
        }

        .back-button {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #333;
            font-size: 14px;
        }

        .back-button:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="form-container"style="
    max-width: 700px;
">
        <h1>Thêm Nhân Viên</h1>
         <%-- Hiển thị thông báo nếu có --%>
        <c:if test="${not empty mess}">
    <div style="color: red; font-weight: bold;">${mess}</div>
    <c:remove var="mess" scope="session"/>
</c:if>
        <form action="insertStaff" method="post" style="
    padding-right: 20px; 
">
            <label for="fullName">Họ và Tên:</label>
            <input type="text" id="fullName" name="fullName" required>

            <label for="phoneNumber">Số Điện Thoại:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required maxlength="10" pattern="\d{10}" title="Số điện thoại phải có đúng 10 chữ số" oninput="validateInput(this, 10)">

            <label for="cccd">CCCD:</label>
            <input type="text" id="cccd" name="cccd" required maxlength="12" pattern="\d{12}" title="CCCD phải có đúng 12 chữ số" oninput="validateInput(this, 12)">

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="dob">Ngày Sinh:</label>
            <input type="date" id="dob" name="dob" required>
            <div class="d-flex">
            <label for="sex" class="me-2">Giới Tính:</label>
<select id="sex" name="sex" required class="w-25 pe-5 me-5">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <label for="sex" class="me-2">Phân loại:</label>
            <select id="role" name="role" required class="w-25 pe-5 me-5">
                <option value="1">Manager</option>
                <option value="2">Administrative Staff</option>
                <option value="3">Accountant</option>
                <option value="4">Technical Board</option>
                <option value="5">Service Provider</option>
            </select>

            <%--<label for="status" class="me-2">Trạng Thái:</label>
            <select id="status" name="status" required class="w-25">
                <option value="Active">Active</option>
                <option value="Deactive">Deactive</option>
</select>--%>
</div>
            <button type="submit" style="background-color: #d5460d;">Thêm</button>
        </form>
        <a href="manageStaff" class="back-button">Quay lại trang Quản lý</a>
    </div>
<script>
    function validateInput(input, length) {
        input.value = input.value.replace(/\D/g, '').slice(0, length);
    }

    function validateForm() {
        const phone = document.getElementById("phoneNumber").value;
        const cccd = document.getElementById("cccd").value;
        
        if (phone.length !== 9) {
            alert("Số điện thoại phải có đúng 9 chữ số!");
            return false;
        }
        if (cccd.length !== 12) {
            alert("CCCD phải có đúng 12 chữ số!");
            return false;
        }
        return true;
    }
</script>
</body>
</html>