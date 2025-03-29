<%-- 
    Document   : import_export
    Created on : Mar 27, 2025, 2:19:55 PM
    Author     : Hoang-Tran
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
        <%@include file="menumanager.jsp" %>
        <!--=============================CONTENT HERE=======================-->
        <div class="w-100" style="display: flex;">
            
            <div  class="col-9 ct">
                <h1>Import Log</h1>
                <%-- Hiển thị thông báo nếu có --%>
                <c:if test="${not empty mess}">
                    <div class="message">${mess}</div>
                </c:if>
                <div class=" row mb-3">
                    <!-- Cột bên trái: Bộ lọc (45%) -->
                    <div class="d-flex gap-2">
                        <form action="allport" method="get" class="d-flex gap-2 flex-grow-1">
                            <input type="hidden" name="type" value="import">
                            <select name="status" id="statusFilter" class="form-select" style="width: 10%;">
                                
                                <option value="">AllStatus</option>
                                <option value="Success" ${selectedStatus == 'Success' ? 'selected' : ''}>Success</option>
                                <option value="Unsuccess" ${selectedStatus == 'Unsuccess' ? 'selected' : ''}>Unsuccess</option>
                            </select>
                            <button type="submit" class="btn btn-primary" style="width: 10%;">Filter</button>
                        </form>
                    </div>

                    <!-- Cột bên phải: Tìm kiếm (45%) -->
                </div>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ImportDate</th>
                                <th>File Name</th>
                                <th>RecordsCount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${allImportLogs}">
                                <tr>
                                    <td>${o.importDate}</td>
                                    <td>${o.fileName}</td>
                                    <td>${o.recordsCount}</td>
                                    <td>${o.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
<!--               phan trang-->
                <ul class="pagination justify-content-center">
                    <c:if test="${currentImPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentImPage - 1}
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                                   ">Previous</a>
                            </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalImportPages}" var="i">
                        <li class="page-item ${i == currentImPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}                              
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               ">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentImPage < totalImportPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentImPage + 1}
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                                   ">Next</a>
                            </li>
                    </c:if>
                </ul>

                <c:if test="${totalImportPages == 0}">
                    <p class="text-center text-muted">No apartments found.</p>
                </c:if>
            </div>
        </div>
        <script>
        function showTenants(apartmentId) {
            window.location.href = "viewTenants?apartmentId="+apartmentId;
        }
    </script>   
    
    
    
     <div class="w-100" style="display: flex;">
            
            <div  class="col-9 ct">
                <h1>Export Log</h1>
                <%-- Hiển thị thông báo nếu có --%>
                <c:if test="${not empty mess}">
                    <div class="message">${mess}</div>
                </c:if>
                <div class=" row mb-3">
<!--                     Cột bên trái: Bộ lọc (45%) -->
                    <div class="d-flex gap-2">
                        <form action="allport" method="get" class="d-flex gap-2 flex-grow-1">
                            <select name="exportType" id="statusFilter" class="form-select" style="width: 10%;">
                                <option value="">AllExportType</option>
                                <option value="Utility Bill" ${selectedStatus == 'Utility' ? 'selected' : ''}>Utility Bill</option>
                                 <option value="Orther" ${selectedStatus == 'Other' ? 'selected' : ''}>Orther</option>
                            </select>
                            <button type="submit" class="btn btn-primary" style="width: 10%;">Filter</button>
                        </form>
                    </div>

<!--                     Cột bên phải: Tìm kiếm (45%) -->
                </div>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ExportDate</th>
                                <th>File Name</th>
                                <th>Export Type</th>
                                <th>RecordsCount</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${allExportLogs}">
                                <tr>
                                    <td>${o.exportDate}</td>
                                    <td>${o.fileName}</td>
                                    <td>${o.exportType}</td>
                                    <td>${o.recordsCount}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
<!--               phan trang-->
                <ul class="pagination justify-content-center">
                    <c:if test="${currentExPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentExPage - 1}
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                                   ">Previous</a>
                            </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalExportPages}" var="i">
                        <li class="page-item ${i == currentExPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               ">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${currentExPage< totalExportPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentExPage + 1}
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                                   ">Next</a>
                            </li>
                    </c:if>
                </ul>

                <c:if test="${totalExportPages == 0}">
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
