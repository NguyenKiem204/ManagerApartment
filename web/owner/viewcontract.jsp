<%-- 
    Document   : viewcontract
    Created on : Mar 10, 2025, 10:33:44 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.Contract" %>

<jsp:useBean id="contract" scope="request" type="model.Contract" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Hợp Đồng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F4A08C;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .contract-container {
            max-width: 500px;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .contract-title {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
        }

        .contract-details {
            font-size: 16px;
            color: #555;
            text-align: left;
            line-height: 1.6;
        }

        .contract-details p {
            margin: 8px 0;
            padding: 10px;
            background: #f9f9f9;
            border-radius: 5px;
        }

        .no-contract {
            font-size: 18px;
            color: red;
            font-weight: bold;
        }

        .back-btn {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        .back-btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
<div class="contract-container">
    <c:if test="${not empty contract}">
        <h2 class="contract-title">Thông tin Hợp đồng</h2>
        <div class="contract-details">
            <p><strong>Tên Tenant:</strong> ${contract.resident.fullName}</p>
            <p><strong>Tên Phòng:</strong> ${contract.apartment.apartmentName}</p>
            <p><strong>Ngày Bắt Đầu:</strong> 
                <fmt:formatDate value="${contract.formattedDate1}" pattern="dd/MM/yyyy"></fmt:formatDate>
            </p>
            <p><strong>Ngày Kết Thúc:</strong> 
                <fmt:formatDate value="${contract.formattedDate2}" pattern="dd/MM/yyyy"></fmt:formatDate>
            </p>
        </div>
    </c:if>
    
    <c:if test="${empty contract}">
        <p class="no-contract">Không tìm thấy hợp đồng cho Tenant này.</p>
    </c:if>

    <button class="back-btn" onclick="window.history.back()">Quay lại</button>
</div>

</body>
</html>

