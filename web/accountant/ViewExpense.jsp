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
        <title>Invoice Manager</title>
          <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" /> 
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

        </style>


    </head>

    <body>

        <%@include file="menuaccountant.jsp" %>



        <div id="main">
            <div class="container ">

                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                    <h2>Invoices Manager</h2>
                    <div class="d-flex align-items-center">
                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i> Import Expense
                        </a>
                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i> Back Invoice Manager
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
                                <input type="date" class="form-control" id="FromDate" name="FromDate" 
                                       value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="date" class="form-control" id="dueDate" name="dueDate" 
                                       value="${selectedDueDate}">
                                <label>Type</label>
                                <select name="typeid" class="form-select" aria-label="Default select example">
                                    <c:forEach items="${lte}" var="o">
                                        <option value="0">All Status</option>
                                        <option value="${o.typeExpenseID}" ${o.typeExpenseID == typeexp ? 'selected' : ''}>${o.typeName}</option>
                                    </c:forEach>
                                </select>

                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>

                                <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-info btn-sm">
                                    <i class="fas fa-sync-alt"></i> <!-- Icon reload -->
                                </a>

                            </form>
                        </div>


                        <div class="col-md-4">
                            <form action="ViewExpense" method="get" class="d-flex gap-2">
                                <input type="text" name="search" placeholder="Search by title.." value="${search}" class="form-control me-2">
                                <input type="hidden" name="typeid" value="${typeid}">
                                <input type="hidden" name="status" value="${selectedStatus}">  
                                <input type="hidden" name="FromDate" value="${selectedFromDate}">  
                                <input type="hidden" name="dueDate" value="${selectedDueDate}"> 
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>
                        </div>
                    </div>


                </div>


                <table class="tableinvoice ">
                    <thead class="table">
                        <tr>
                            <th>Expense Code</th>
                            <th>Description</th>
                            <th>Expense Date</th>
                            <th>Amount</th>
                            <th>Type Expense</th>
                            <th>Status</th>
                            <th  style="width:30px">Actions</th>
                        </tr>
                    </thead>
                    <tbody style="background:white" id="tableBody">
                        <c:forEach items="${sessionScope.ListExpense}" var="l">
                            <tr>
                                <td>${l.expenseID}</td>
                                <td>${l.description}</td>
                                <td></td>
                                <td>
                                    ${l.amount}
                                </td>
                                <td>
                                    ${l.typeid}
                                </td>
                                <td>
                        

                        </td>

                        <td style="width:30px">
                            <a href="" class="btn btn-info btn-sm">
                                <i class="bi bi-eye"></i>
                            </a>
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







    </body>

</html>
