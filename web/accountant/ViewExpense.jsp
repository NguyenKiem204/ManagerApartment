<%-- 
    Document   : resident
    Created on : Jan 16, 2025, 3:13:40 AM
    Author     : nkiem
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Expenses Management</title>
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" /> 
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap JS (cần cho dropdown) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <style>

            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
            }
            .container {
                max-width: 1200px;
                background-color: #fff;
                padding: 20px;
                margin: auto;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #ff9800;
            }

            input, select {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            input {
                width: 70%;
            }
            select {
                width: 28%;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: #ff9800;
                color: white;
                cursor: pointer;
                position: relative;
            }
            th .sort-icon {
                margin-left: 5px;
                font-size: 12px;
            }
            .rating {
                color: #FFD700;
                font-size: 20px;
            }
            .pagination {
                text-align: center;
                margin-top: 10px;
            }
            .pagination button {
                background-color: #ff9800;
                color: white;
                border: none;
                padding: 8px 15px;
                margin: 5px;
                cursor: pointer;
                border-radius: 4px;
            }
            .pagination button:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }
            .tableinvoice th, .tableinvoice td {
                width: 25%; /* Chia đều 4 cột */
                text-align: center;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .table-bordered th, .table-bordered td {
                width: 25%;
                text-align: center;
            }
            .tableinvoice th:nth-child(4), .tableinvoice td:nth-child(4) {
                width: 40%; /* Cột Description rộng hơn */
            }
            td.description {
    max-width: 400px; /* Điều chỉnh độ rộng tùy ý */
    word-wrap: break-word;
    white-space: pre-wrap; /* Giữ khoảng trắng và xuống dòng tự động */
}


        </style>


    </head>

    <body>

        <%@include file="/manager/menumanager.jsp" %>


        <div id="main">
            <div class="container ">

                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                    <h2>Expenses Management</h2>
                    <div class="d-flex align-items-center gap-3">
                        <a href="<%= request.getContextPath() %>/accountant/ImportExpense" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i> Import Expense
                        </a>
                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i>Invoice Manager
                        </a>
                    </div>
                </div>
                <div class="search-sort-container">
                    <div class="row mb-3 align-items-center" >

                        <div class="col-md-8">
                            <form action="ViewExpense" method="get" class="d-flex gap-2">

                                <select class="form-select" name="status">
                                    <option value="">All Status</option>
                                    <option value="Pending" ${selectedStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="Approved" ${selectedStatus == 'Approved' ? 'selected' : ''}>Approved</option>
                                    <option value="Rejected" ${selectedStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                </select>
                                <label for="FromDate" class="form-label align-self-center">From:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="FromDate" 
                                       value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="dueDate" 
                                       value="${selectedDueDate}">
                                <label>Type</label>
                                <select name="typeid" class="form-select" aria-label="Default select example">
                                    <option value="0">All Status</option>
                                    <c:forEach items="${lte}" var="o">

                                        <option value="${o.typeExpenseID}" ${o.typeExpenseID == typeexp ? 'selected' : ''}>${o.typeName}</option>
                                    </c:forEach>
                                </select>

                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>

                                <a href="<%= request.getContextPath() %>/accountant/ViewExpense" class="btn btn-info btn-sm">
                                    <i class="fas fa-sync-alt"></i> <!-- Icon reload -->
                                </a>

                            </form>
                        </div>



                    </div>


                </div>


                <table class="tableinvoice">
                    <thead class="table">
                        <tr>
                            <th>Expense Code</th>
                            <th>Expense Date</th>
                            <th>Total Amount</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody style="background:white" id="tableBody">
                        <c:forEach items="${sessionScope.ListExpense}" var="expense">
                            <!-- Hàng chính hiển thị thông tin Expense -->
                            <tr>
                                <td>${expense.expenseID}</td>
                                <td>${expense.expenseDateft}</td>
                                <td><fmt:formatNumber value="${expense.totalAmount}" pattern="#,##0.00"/>d</td>
                                <td style="width:30px">

                                    <button class="btn btn-secondary btn-sm" type="button" onclick="toggleDetails(${expense.expenseID})">
                                        View Details
                                    </button>
                                </td>
                            </tr>
                            <!-- Hàng ẩn hiển thị chi tiết ExpenseDetail -->
                            <tr id="details-${expense.expenseID}" style="display: none;">
                                <td colspan="5">
                                    <table class="table table-bordered mt-2">
                                        <thead>
                                            <tr>
                                                <th>Description</th>
                                                <th>Type</th>
                                                <th>Amount</th>
                                                <th>Status</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${expense.expenseDetails}" var="detail">
                                                <tr>
                                                   <td class="description">${detail.description}</td>

                                                    <td>${detail.typeExpense.typeName}</td>
                                                    <td><fmt:formatNumber value="${detail.amount}" pattern="#,##0.00"/></td>

                                                    <td>${detail.status}</td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>



                <c:if test="${not empty sessionScope.ListExpense && requestScope.totalPage > 1}">

                    <div style="display: flex; align-items: center; gap: 10px; margin-top: 10px;">
                        <c:set var="startPage" value="${requestScope.currentPage - 1}" />
                        <c:set var="endPage" value="${requestScope.currentPage + 1}" />
                        <c:if test="${startPage < 1}">
                            <c:set var="startPage" value="1"/>
                            <c:set var="endPage" value="3"/>
                        </c:if>
                        <c:if test="${endPage > requestScope.totalPage}">
                            <c:set var="endPage" value="${requestScope.totalPage}"/>
                            <c:set var="startPage" value="${endPage - 2}" />
                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1"/>
                            </c:if>
                        </c:if>
                        <c:if test="${requestScope.currentPage > 1}">
                            <a href="ViewExpense?page=${requestScope.currentPage - 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&typeid=${typeexp}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &lt;
                            </a>
                        </c:if>
                        <c:forEach begin="${startPage}" end="${endPage}" var="page">
                            <a href="ViewExpense?page=${page}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&typeid=${typeexp}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;
                               <c:if test='${page == requestScope.currentPage}'> background-color: #007bff; color: white; </c:if>">
                                ${page}
                            </a>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage < requestScope.totalPage}">
                            <a href="ViewExpense?page=${requestScope.currentPage + 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&typeid=${typeexp}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &gt;
                            </a>
                        </c:if>
                    </div>

                </c:if>
                <c:if test="${empty sessionScope.ListExpense}">
                    <div style="display: flex; justify-content: center; align-items: center; height: 50vh;">
                        <p style="font-size: 20px;">${message}</p>
                    </div>
                </c:if>

            </div>
        </div>
        <% if (request.getAttribute("message") != null) { %>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let errorMessage = "<%= request.getAttribute("message") %>";
                let notification = document.createElement("div");
                notification.innerText = errorMessage;
                notification.style.position = "fixed";
                notification.style.top = "20px";
                notification.style.right = "20px";
                notification.style.backgroundColor = "red";
                notification.style.color = "white";
                notification.style.padding = "15px";
                notification.style.borderRadius = "5px";
                notification.style.boxShadow = "0px 0px 10px rgba(0,0,0,0.5)";
                notification.style.zIndex = "1000";
                notification.style.fontSize = "16px";
                notification.style.fontWeight = "bold";
                document.body.appendChild(notification);

                // Hiển thị thông báo từ 5 đến 10 giây (ngẫu nhiên)
                let displayTime = Math.floor(Math.random() * (10000 - 5000 + 1)) + 5000;
                setTimeout(() => {
                    notification.remove();
                }, displayTime);
            });
        </script>
        <% } %>
        <script>
            function toggleDetails(expenseID) {
                const detailsRow = document.getElementById("details-" + expenseID);
                if (detailsRow.style.display === "none") {
                    detailsRow.style.display = "table-row";
                } else {
                    detailsRow.style.display = "none";
                }
            }
        </script>







    </body>

</html>
