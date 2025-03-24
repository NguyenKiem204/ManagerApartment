<%-- 
    Document   : ownerapartment
    Created on : Mar 13, 2025, 1:40:03 AM
    Author     : fptshop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Owner Apartment</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #ffe6cc;
            }

            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px;
            }

            h1 {
                color: #e65c00;
                margin-bottom: 20px;
            }

            .table-container {
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                width: 100%;
            }

            table {
                border-collapse: collapse;
                width: 100%;
            }
            .ct{
                margin-left: 400px;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #ff944d;
                color: white;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            .actions {
                display: flex;
                gap: 10px;
            }

            .actions button {
                background-color: #ff944d;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .actions button:hover {
                background-color: #e65c00;
            }

            .sidebar {
                position: fixed;
                width: 250px;
                height: 100%;
                background-color: #ff944d;
                padding: 20px;
                color: white;
            }

            .sidebar ul {
                list-style-type: none;
                padding: 0;
            }

            .sidebar ul li {
                margin: 20px 0;
            }

            .sidebar ul li a {
                color: white;
                text-decoration: none;
                font-size: 18px;
            }

            .sidebar ul li a:hover {
                text-decoration: underline;
            }

            .content {
                margin-left: 270px;
                padding: 20px;
            }
            .message {
                color: green;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .add-apartment-button {
                display: inline-block;
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 8px;
                font-size: 16px;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .add-apartment-button:hover {
                background-color: #45a049;
            }
            .switch {
                position: relative;
                display: inline-block;
                width: 34px;
                height: 20px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 34px;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 14px;
                width: 14px;
                left: 3px;
                bottom: 3px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
            }

            input:checked + .slider {
                background-color: #4CAF50;
            }

            input:checked + .slider:before {
                transform: translateX(14px);
            }
            .action-btn {
                background-color: #007bff; 
                color: white; 
                border: none; 
                padding: 2px 8px; 
                font-size: 10px; 
                font-weight: bold; 
                border-radius: 5px; 
                cursor: pointer; 
                transition: all 0.3s ease-in-out; 
            }

            .action-btn:hover {
                background-color: #0056b3; 
                transform: scale(1.05); 
            }

            .action-btn:active {
                background-color: #004494; 
                transform: scale(0.98); 
            }

        </style>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <!--=============================CONTENT HERE=======================-->
        <div class="w-100" style="display: flex;">
            
            <div  class="col-9 ct">
                <h1>My Apartments</h1>
                <%-- Hiển thị thông báo nếu có --%>
                <c:if test="${not empty mess}">
                    <div class="message">${mess}</div>
                </c:if>
                <div class=" row mb-3">
                    <!-- Cột bên trái: Bộ lọc (45%) -->
                    <div class="d-flex gap-2">
                        <form action="manageOwnerApartment" method="get" class="d-flex gap-2 flex-grow-1">
                            <select name="type" id="typeFilter" class="form-select" style="width: 100%;" >
                                <option value="">AllTypes</option>
                                <option value="1 Bedroom" ${selectedType == '1 Bedroom' ? 'selected' : ''}>1 Bedroom</option>
                                <option value="2 Bedrooms" ${selectedType == '2 Bedrooms' ? 'selected' : ''}>2 Bedrooms</option>
                                <option value="3 Bedrooms" ${selectedType == '3 Bedrooms' ? 'selected' : ''}>3 Bedrooms</option>

                            </select>

                            <select name="status" id="statusFilter" class="form-select" style="width: 100%;">
                                <option value="">AllStatus</option>
                                <option value="Available" ${selectedStatus == 'Available' ? 'selected' : ''}>Available</option>
                                <option value="Occupied" ${selectedStatus == 'Occupied' ? 'selected' : ''}>Occupied</option>
                                <option value="Occupied" ${selectedStatus == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                            </select>
                            <select name="block" id="blockFilter" class="form-select" style="width: 100%;">
                                <option value="">AllBlocks</option>
                                <option value="Block A" ${selectedBlock == 'Block A' ? 'selected' : ''}>Block A</option>
                                <option value="Block B" ${selectedBlock == 'Block B' ? 'selected' : ''}>Block B</option>
                                <option value="Block C" ${selectedBlock == 'Block C' ? 'selected' : ''}>Block C</option>
                            </select>
                            <button type="submit" class="btn btn-primary" style="width: 20%;">Filter</button>
                        </form>
                    </div>

                    <!-- Cột bên phải: Tìm kiếm (45%) -->
                    <div class="col-md-6">
                        <form action="manageOwnerApartment" method="get" class="d-flex">
                            <input type="text" name="name" placeholder="Enter an apartment name..." value="${selectedName}" class="form-control me-2" style="width: 70%;">
                            <button type="submit" class="btn btn-primary" style="width: 30%;">Search</button>
                        </form>
                    </div>
                    
                </div>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Apartment Name</th>
                                <th>Block</th>
                                <th>Status</th>
                                <th>Type</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="apartment" items="${listApartment}">
                                <tr>
                                    <td>${apartment.apartmentId}</td>
                                    <td>${apartment.apartmentName}</td>
                                    <td>${apartment.block}</td>
                                    <td>${apartment.status}</td>
                                    <td>${apartment.type}</td>
                                    <td>
                                        <button class="action-btn" onclick="showTenants(${apartment.apartmentId})">View Tenants</button>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal fade" id="editModal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg" role="document"> <!-- Thêm modal-lg hoặc modal-xl -->
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Apartment</h5>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <!-- Nội dung AJAX sẽ load vào đây -->
                            </div>
                        </div>
                    </div>
                </div>


                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">Previous</a>
                            </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                               ">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">Next</a>
                            </li>
                    </c:if>
                </ul>

                <c:if test="${totalPages == 0}">
                    <p class="text-center text-muted">No apartments found.</p>
                </c:if>
            </div>
        </div>
        <script>
        function showTenants(apartmentId) {
            window.location.href = "viewTenants?apartmentId="+apartmentId;
        }
    </script>                    

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>

</html>

