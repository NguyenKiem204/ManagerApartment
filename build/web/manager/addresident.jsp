<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="assets/css/bootstrap.css" />
        <title>Thêm Cư Dân</title>
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
            <h1>Thêm Cư Dân</h1>
            <%-- Hiển thị thông báo nếu có --%>
            <c:if test="${not empty mess}">
                <div style="color: red; font-weight: bold;">${mess}</div>
                <c:remove var="mess" scope="session"/>
            </c:if>
            <form id="insertResidentForm" style="padding-right: 20px";>
    <label for="fullName">Họ và Tên:</label>
    <input type="text" id="fullName" name="fullName" required>

    <label for="phoneNumber">Số Điện Thoại:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required maxlength="10" pattern="\d{10}">

    <label for="cccd">CCCD:</label>
    <input type="text" id="cccd" name="cccd" required maxlength="12" pattern="\d{12}">

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="dob">Ngày Sinh:</label>
    <input type="date" id="dob" name="dob" required>

    <label for="sex">Giới Tính:</label>
    <select id="sex" name="sex" required class="w-25 pe-5 me-5">
        <option value="Male">Male</option>
        <option value="Female">Female</option>
    </select>

    <label for="role">Phân loại:</label>
    <select id="roleId" name="roleId" required class="w-25 pe-5 me-5">
        <option value="6">Tenant</option>
        <option value="7">Owner Department</option>
    </select>

    <button type="button" id="submitBtn" style="background-color: #d5460d;">Thêm</button>
</form>

<div id="message"></div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $("#submitBtn").click(function () {
            let formData = $("#insertResidentForm").serialize();
            $.ajax({
                type: "POST",
                url: "insertResident",
                data: formData,
                dataType: "json",
                success: function (response) {
                    if (response.success) {
                        $("#message").html("<span style='color: green;'>" + response.message + "</span>");
                        $("#insertResidentForm")[0].reset(); // Reset form
                    } else {
                        $("#message").html("<span style='color: red;'>" + response.message + "</span>");
                    }
                },
                error: function () {
                    $("#message").html("<span style='color: red;'>Lỗi khi gửi dữ liệu!</span>");
                }
            });
        });
    });
</script>

    </body>
</html>