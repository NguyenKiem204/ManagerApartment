<%-- 
    Document   : viewtenants
    Created on : Mar 13, 2025, 3:29:55 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <style>
        /* Tổng thể */
body {
    font-family: Arial, sans-serif;
    background-color: #f79572;
    margin: 0;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

/* Tiêu đề */
h2 {
    color: white;
    text-align: center;
}

/* Khung bảng */
.table-container {
    width: 80%;
    background: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

/* Bảng */
table {
    width: 100%;
    border-collapse: collapse;
    border-radius: 10px;
    overflow: hidden;
}

/* Header bảng */
thead {
    background-color: #f98950;
    color: white;
}

thead th {
    padding: 12px;
    text-align: left;
}

/* Dòng chẵn - lẻ */
tbody tr:nth-child(even) {
    background-color: #f8f8f8;
}

tbody tr {
    border-bottom: 1px solid #ddd;
}

tbody td {
    padding: 12px;
    color: #333;
}

/* Nút "Quay lại" */
.back-container {
    text-align: center;
    margin-top: 20px;
}

.btn {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 8px 12px;
    border-radius: 5px;
    cursor: pointer;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    background-color: #0056b3;
}

    </style>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Tenant</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h2>Tenants of Apartment ${apartmentId}</h2>

    <div class="table-container">
        <c:if test="${empty tenants}">
            <p style="text-align: center; color: red;">Không có tenant nào cho căn hộ này.</p>
        </c:if>

        <c:if test="${not empty tenants}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tenant" items="${tenants}">
                        <tr>
                            <td>${tenant.residentId}</td>
                            <td>${tenant.fullName}</td>
                            <td>${tenant.phoneNumber}</td>
                            <td>${tenant.email}</td>
                            <td>${tenant.status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <div class="back-container">
        <button class="btn" onclick="window.history.back()">Quay lại</button>
    </div>

</body>
</html>