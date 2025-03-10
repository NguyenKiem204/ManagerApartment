<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
            }
            .container {
                max-width: 700px;
                background-color: #fff;
                padding: 20px;
                margin: auto;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                position: relative;
                margin-top: 30px;
            }
            h2 {
                text-align: center;
                color: #ff9800;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .detail-row {
                display: flex;
                justify-content: space-between;
                padding: 10px 0;
                border-bottom: 1px solid #ddd;
            }
            .detail-row:last-child {
                border-bottom: none;
            }
            .label {
                font-weight: bold;
                color: #555;
            }
            .value {
                color: #333;
                font-weight: normal;
            }
            .status {
                font-weight: bold;
                color: white;
                padding: 6px 12px;
                border-radius: 5px;
                display: inline-block;
                text-transform: uppercase;
                font-size: 14px;
            }
            .status.pending {
                background-color: #ffa500;
            }
            .status.approved {
                background-color: #28a745;
            }
            .status.rejected {
                background-color: #dc3545;
            }
            .status.completed {
                background-color: #007bff;
            }
            .btn-back {
                display: block;
                width: 100%;
                text-align: center;
                padding: 10px;
                font-size: 16px;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                text-decoration: none;
                margin-top: 20px;
                transition: 0.3s;
            }
            .btn-back:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id = "main">
            <div class="container">
                <h2>Request Detail</h2>
                <div class="detail-row">
                    <span class="label">Request ID:</span> 
                    <span class="value">1</span>
                </div>
                <div class="detail-row">
                    <span class="label">Apartment:</span> 
                    <span class="value">a101</span>
                </div>
                <div class="detail-row">
                    <span class="label">Title:</span> 
                    <span class="value">Sửa chữa điện nước</span>
                </div>
                <div class="detail-row">
                    <span class="label">Service Type:</span> 
                    <span class="value">Water</span>
                </div>
                <div class="detail-row">
                    <span class="label">Description:</span>
                    <span class="value">aaaaaaaaaaaaaaaaaaaaaaDưới đây là phiên bản CSS đã được cải thiện để giao diện đẹp hơn, dễ đọc hơn và chuyên nghiệp hơn:

Edited

Answer in chat instead
Tôi đã cải thiện CSS để giao diện đẹp hơn, dễ đọc và chuyên nghiệp hơn. Các phần tử được căn chỉnh tốt hơn, màu sắc rõ ràng hơn, và có hiệu ứng hover cho nút "Back to List". Bạn có muốn thêm điều chỉnh gì nữa không? bbbbbbbbbbbbbbbbbbbbb ccccccccccccccccccccc</span>
                </div>
                <div class="detail-row">
                    <span class="label">Status:</span>
                    <span class="status pending">Pending</span>
                </div>
                <a href="requestList.jsp" class="btn-back">Back to List</a>
            </div>
        </div>
    </body>
</html>
