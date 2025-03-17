<%-- 
    Document   : assetdetail
    Created on : Mar 16, 2025, 11:05:49 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 700px;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .back-btn {
            border-radius: 5px;
            background-color: #FF9800;
            color: white;
            cursor: pointer;
            transition: 0.3s;
        }
        h2 {
            text-align: center;
            color: #007bff;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .detail-row {
            display: flex;
            justify-content: space-between;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        .label {
            font-weight: bold;
            color: #555;
        }
        .value {
            color: #333;
            font-size: 16px;
            word-wrap: break-word;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }
        .update-btn, .delete-btn {
            padding: 10px 15px;
            font-size: 14px;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            transition: 0.3s;
        }
        .update-btn {
            background-color: #28a745;
        }
        .delete-btn {
            background-color: #dc3545;
        }
        .update-btn:hover {
            background-color: #218838;
        }
        .delete-btn:hover {
            background-color: #c82333;
        }
    </style>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id = "main">
            <div class="container">
                <button class="back-btn" onclick="window.location.href = '${pageContext.request.contextPath}/manager/manageassets'">Back</button>
                <h2>Asset Detail</h2>
                <div class="detail-row">
                    <span class="label">Asset Name:</span>
                    <span class="value">${asset.assetName}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Category:</span>
                    <span class="value">${asset.category.categoryName}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Bought On:</span>
                    <span class="value"><fmt:formatDate value="${asset.boughtOn}" pattern="dd/MM/yyyy" /></span>
                </div>
                <div class="detail-row">
                    <span class="label">Quantity:</span>
                    <span class="value">${asset.quantity}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Updated At:</span>
                    <span class="value">${asset.formattedUpdatedAt}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Location:</span>
                    <span class="value">${asset.location}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Status:</span>
                    <span class="value">${asset.status.statusName}</span>
                </div>
                <div class="action-buttons">
                    <button class="update-btn" onclick="updateAsset(${asset.assetId})">Update</button>
                    <button class="delete-btn" onclick="confirmDelete(${asset.assetId}, '${asset.assetName}')">Delete</button>
                </div>
            </div>
        </div>
    </body>
    <script>
        function updateAsset(assetID) {
            window.location.href = '${pageContext.request.contextPath}/manager/updateasset?assetId=' + assetID;
        }
        function confirmDelete(assetId, assetName) {
            const confirmation = confirm(`Do you want to delete the asset: ` + assetName + `?`);
            if (confirmation) {
                // Chuyển hướng đến servlet xử lý xóa
                window.location = `deleteasset?assetId=` + assetId;

                // Chờ 1 giây sau đó reload trang danh sách tài sản
                setTimeout(() => {
                    window.location.href = 'manageassets'; // Đổi thành URL trang danh sách asset
                }, 1000);
            } else {
                console.log('Hành động xóa đã bị hủy.');
            }
        }

    </script>
</body>
</html>
