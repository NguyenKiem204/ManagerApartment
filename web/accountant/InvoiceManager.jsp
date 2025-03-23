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
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 1200px;
                background-color: #fff;
                padding: 20px;
                margin: 20px auto;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                color: #343a40;
                margin-bottom: 20px;
            }

            .btn {
                display: inline-flex;
                align-items: center;
                gap: 5px;
                padding: 8px 16px;
                border-radius: 5px;
                font-size: 14px;
                font-weight: 500;
                transition: background-color 0.3s ease;
            }

            .btn-primary {
                background-color: #007bff;
                border: none;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            .btn-warning {
                background-color: #ffc107;
                border: none;
            }

            .btn-warning:hover {
                background-color: #e0a800;
            }

            .btn-success {
                background-color: #28a745;
                border: none;
            }

            .btn-success:hover {
                background-color: #218838;
            }

            .btn-info {
                background-color: #17a2b8;
                border: none;
            }

            .btn-info:hover {
                background-color: #138496;
            }

            .search-sort-container {
                margin-bottom: 20px;
            }

            .search-sort-container .form-control {
                border-radius: 5px;
                border: 1px solid #ced4da;
            }

            .search-sort-container .btn {
                border-radius: 5px;
            }

            .tableinvoice {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                margin-top: 20px;
            }

            .tableinvoice th,
            .tableinvoice td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #dee2e6;
            }

            .tableinvoice th {
                background-color: #FFA500; /* Màu cam */
                color: white;
                font-weight: 600;
            }

            .tableinvoice tbody tr:hover {
                background-color: #f8f9fa;
            }

            .tableinvoice tbody tr td {
                vertical-align: middle;
            }

            .status-paid {
                background-color: #28a745;
                color: white;
                border-radius: 8px;
                padding: 5px 10px;
                display: inline-block;
                text-align: center;
            }

            .status-unpaid {
                background-color: #dc3545;
                color: white;
                border-radius: 8px;
                padding: 5px 10px;
                display: inline-block;
                text-align: center;
            }

            
            .pagination-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 20px;
            }

            .rows-per-page {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .pagination {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .pagination a {
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #dee2e6;
                color: #007bff;
                margin: 0 4px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .pagination a.active {
                background-color: #007bff;
                color: white;
                border: 1px solid #007bff;
            }

            .pagination a:hover:not(.active) {
                background-color: #f8f9fa;
            }

            .total-amount {
                text-align: right;
                font-size: 18px;
                font-weight: bold;
                margin-top: 20px;
            }

            .total-amount span {
                color: #dc3545;
            }
           
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                    <h2>Invoices Manager</h2>
                    <div class="d-flex align-items-center">
                        <a href="<%= request.getContextPath() %>/accountant/UpdateStatusInvoice" class="btn btn-primary d-flex align-items-center me-2">
                            <i class="bi bi-arrow-repeat me-1"></i> Update Status
                        </a>
                        <a href="<%= request.getContextPath() %>/accountant/ViewExpense" class="btn btn-warning d-flex align-items-center me-2">
                            <i class="bi bi-cash-stack me-1"></i> Expense
                        </a>
                        <a href="<%= request.getContextPath() %>/accountant/addnewinvoice" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i> Add New Invoice
                        </a>
                    </div>
                </div>
                <div class="search-sort-container">
                    <div class="row mb-3 align-items-center">
                        <div class="col-md-8">
                            <form action="InvoicesManager" method="get" class="d-flex gap-2">
                                <select class="form-select" name="status">
                                    <option value="">All Status</option>
                                    <option value="Paid" ${selectedStatus == 'Paid' ? 'selected' : ''}>Paid</option>
                                    <option value="Unpaid" ${selectedStatus == 'Unpaid' ? 'selected' : ''}>Unpaid</option>
                                </select>
                                <label for="FromDate" class="form-label align-self-center">From:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="FromDate" value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="dueDate" value="${selectedDueDate}">
                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>
                                <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-info btn-sm">
                                    <i class="fas fa-sync-alt"></i>
                                </a>
                            </form>
                        </div>
                        <div class="col-md-4">
                            <form action="InvoicesManager" method="get" class="d-flex gap-2">
                                <input type="text" name="search" placeholder="Search by title.." value="${search}" class="form-control me-2">
                                <input type="hidden" name="status" value="${selectedStatus}">
                                <input type="hidden" name="FromDate" value="${selectedFromDate}">
                                <input type="hidden" name="dueDate" value="${selectedDueDate}">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="pagination">
                    <div class="rows-per-page">
                        <form action="InvoicesManager" method="get" class="d-flex align-items-center gap-2">
                            <label for="rowsPerPage">Rows per page:</label>
                            <select name="rowsPerPage" id="rowsPerPage" onchange="this.form.submit()">
                                <option value="5" ${rowsPerPage == 5 ? 'selected' : ''}>5</option>
                                <option value="10" ${rowsPerPage == 10 ? 'selected' : ''}>10</option>
                                <option value="20" ${rowsPerPage == 20 ? 'selected' : ''}>20</option>
                                <option value="50" ${rowsPerPage == 50 ? 'selected' : ''}>50</option>
                                <option value="100" ${rowsPerPage == 100 ? 'selected' : ''}>100</option>
                            </select>
                            <input type="hidden" name="page" value="1">
                            <input type="hidden" name="search" value="${search}">
                            <input type="hidden" name="status" value="${selectedStatus}">
                            <input type="hidden" name="FromDate" value="${selectedFromDate}">
                            <input type="hidden" name="dueDate" value="${selectedDueDate}">
                        </form>
                    </div>
                </div>
                <table class="tableinvoice">
                    <thead class="table">
                        <tr>
                            <th>Invoice Code</th>
                            <th>Title</th>
                            <th>Apartment</th>
                            <th>Status</th>
                            <th>Payment Term</th>
                            <th>Payment Date</th>
                            <th>Public Date</th>
                            <th>Amount</th>
                            <th>Late(0,1%/d)</th>
                            <th style="width:30px">Actions</th>
                        </tr>
                    </thead>
                    <tbody style="background:white" id="tableBody">
                        <c:forEach items="${sessionScope.ListInvoices}" var="l">
                            <tr>
                                <td>${l.invoiceID}</td>
                                <td>${l.description}</td>
                                <td>${l.apartment.apartmentName}</td>
                                <td>
                                    <c:if test="${'Unpaid' eq l.status}">
                                        <p style="background-color: orange; color: white; border-radius: 8px; padding: 5px; display: inline-block; text-align: center;">
                                            ${l.status}
                                        </p>
                                    </c:if>
                                    <c:if test="${'Paid' eq l.status}">
                                        <p style="background-color: green; color: white; border-radius: 8px; padding: 5px; display: inline-block; text-align: center;">
                                            ${l.status}
                                        </p>
                                    </c:if>
                                </td>
                                <td>${l.dueDateft}</td>
                                <td>${l.paydateft}</td>
                                <td>${l.publicDateft}</td>
                                <td><fmt:formatNumber value="${l.totalAmount + l.muon}" pattern="#,##0.00"/></td>
                                <td>
                                    <c:if test="${l.muon != 0}">
                                        <p style="color:red">Islate</p>
                                    </c:if>
                                </td>
                                <td style="width:30px">
                                    <a href="<%= request.getContextPath() %>/accountant/DetailInvoice?invoiceID=${l.invoiceID}" class="btn btn-info btn-sm">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:set var="totalAmount" value="0"/>
                <c:forEach items="${sessionScope.ListInvoices}" var="l">
                    <c:set var="totalAmount" value="${totalAmount + l.totalAmount + l.muon}"/>
                </c:forEach>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px;">
                    <div></div>
                    <div style="text-align: right; font-size: 18px; font-weight: bold;">
                        Total Invoice Amount: 
                        <span style="color: red;">
                            <fmt:formatNumber value="${totalAmount}" type="number" pattern="#,##0.00"/>
                        </span>
                    </div>
                </div>
                <c:if test="${not empty sessionScope.ListInvoices && requestScope.totalPage > 1}">
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
                            <a href="InvoicesManager?page=${requestScope.currentPage - 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&rowsPerPage=${rowsPerPage}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &lt;
                            </a>
                        </c:if>
                        <c:forEach begin="${startPage}" end="${endPage}" var="page">
                            <a href="InvoicesManager?page=${page}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&rowsPerPage=${rowsPerPage}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;
                               <c:if test='${page == requestScope.currentPage}'> background-color: #007bff; color: white; </c:if>">
                                ${page}
                            </a>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage < requestScope.totalPage}">
                            <a href="InvoicesManager?page=${requestScope.currentPage + 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}&rowsPerPage=${rowsPerPage}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &gt;
                            </a>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${empty sessionScope.ListInvoices}">
                    <div style="display: flex; justify-content: center; align-items: center; height: 50vh;">
                        <p style="font-size: 20px;">${message}</p>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>