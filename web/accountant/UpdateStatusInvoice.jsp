<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Invoice Manager</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
            #pageNumber {
                color: black;
                font-weight: bold;
                margin-top: auto;
                margin-bottom: auto;
            }
            .modal-content {
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                border: none;
            }

            .modal-header {
                background-color: #ff9800;
                color: white;
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
                padding: 15px;
            }

            .modal-title {
                font-size: 20px;
                font-weight: bold;
            }

            .modal-body {
                font-size: 16px;
                padding: 20px;
                background: #f9f9f9;
            }

            .modal-footer {
                background: #f1f1f1;
                padding: 15px;
                border-bottom-left-radius: 10px;
                border-bottom-right-radius: 10px;
            }

            .modal-footer .btn {
                padding: 10px 20px;
                font-size: 16px;
                border-radius: 5px;
            }

            .modal-footer .btn-primary {
                background-color: #ff9800;
                border: none;
            }

            .modal-footer .btn-primary:hover {
                background-color: #e68900;
            }

            .modal-footer .btn-secondary {
                background-color: #6c757d;
                border: none;
            }

            .modal-footer .btn-secondary:hover {
                background-color: #5a6268;
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
                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i>Back to Invoice Manager
                        </a>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="row mb-3 align-items-center">
                        <div class="col-md-8">
                            <form action="UpdateStatusInvoice" method="get" class="d-flex gap-2">
                                <label for="FromDate" class="form-label align-self-center">From:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="FromDate" value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="dueDate" value="${selectedDueDate}">
                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>
                                <a href="<%= request.getContextPath() %>/accountant/UpdateStatusInvoice" class="btn btn-info btn-sm">
                                    <i class="fas fa-sync-alt"></i>
                                </a>
                            </form>
                        </div>
                        <div class="col-md-4">
                            <form action="UpdateStatusInvoice" method="get" class="d-flex gap-2">
                                <input type="text" name="search" placeholder="Search by title.." value="${search}" class="form-control me-2">
                                <input type="hidden" name="FromDate" value="${selectedFromDate}">  
                                <input type="hidden" name="dueDate" value="${selectedDueDate}"> 
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>
                        </div>
                    </div>
                </div>
                <table class="tableinvoice">
                    <thead class="table">
                        <tr>
                            <th>Invoice Code</th>
                            <th>Title</th>
                            <th>Amount</th>
                            <th>Apartment</th>
                            <th>Status</th>
                            <th>Payment Term</th>
                            <th>Public Date</th>
                            <th>Late(2%)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody style="background:white" id="tableBody">
                        <c:forEach items="${sessionScope.ListInvoices}" var="l">
                            <tr>
                                <td>${l.invoiceID}</td>
                                <td style="max-width: 200px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${l.description}">
                                    ${l.description}
                                </td>

                                <td><fmt:formatNumber value="${l.totalAmount + l.muon}" pattern="#,##0.00"/></td>
                                <td>${l.apartment.apartmentName}</td>
                                <td>${l.status}</td>
                                <td>${l.publicDateft}</td>
                                <td>${l.dueDateft}</td>
                                <td>
                                    <c:if test="${l.muon == 1}">
                                        <p>Islate</p>
                                    </c:if>
                                <td style="width:50px">
                                    <button onclick="showInvoiceDetails(${l.invoiceID})" class="btn btn-success">Payment</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="display: flex; align-items: center; gap: 10px;margin-top:10px">
                    <c:if test="${not empty sessionScope.ListInvoices && requestScope.totalPage > 1}">
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
                            <a href="UpdateStatusInvoice?page=${requestScope.currentPage - 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; font-size: 14px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none; cursor: pointer;">
                                &lt;
                            </a>
                        </c:if>
                        <c:forEach begin="${startPage}" end="${endPage}" var="page">
                            <a href="UpdateStatusInvoice?page=${page}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; font-size: 14px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;
                               <c:if test='${page == requestScope.currentPage}'> background-color: #007bff; color: white; </c:if>">
                                ${page}
                            </a>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage < requestScope.totalPage}">
                            <a href="UpdateStatusInvoice?page=${requestScope.currentPage + 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; font-size: 14px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none; cursor: pointer;">
                                &gt;
                            </a>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>


        <c:if test="${not empty inv}">
            <div class="modal fade" id="invoiceDetailModal" tabindex="-1" aria-labelledby="invoiceDetailModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered"> <!-- Thêm class modal-dialog-centered -->
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title" id="invoiceDetailModalLabel">Invoice Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <h2>Invoice code: <span id="invoiceID">${inv.invoiceID}</span></h2>
                            </div>
                            <div class="mb-3">
                                <strong>Apartment:</strong> <span id="apartmentName">${inv.apartment.apartmentName}</span>
                            </div>
                            <div class="mb-3">
                                <strong>Resident:</strong> <span id="residentName">${inv.resident.fullName}</span>
                            </div>
                            <div class="mb-3">
                                <strong>Description:</strong> <span id="description">${inv.description}</span>
                            </div>
                            <div class="mb-3">
                                <strong>Public Date:</strong> <span id="publicDate">${inv.publicDateft}</span>
                            </div>
                            <div class="mb-3">
                                <strong>Due Date:</strong> <span id="dueDate">${inv.dueDateft}</span>
                            </div>
                            <c:if test="${inv.status eq 'Paid'}">
                                <div class="mb-3">
                                    <strong>Pay Date:</strong> <span id="payDate">${inv.paydateft}</span>
                                </div>
                            </c:if>
                            <div class="mb-3">
                                <strong>Status:</strong>
                                <span class="badge bg-${inv.status == 'Paid' ? 'success' : 'warning'}" id="statusBadge">
                                    ${inv.status}
                                </span>
                            </div>
                            <h5 class="mt-4">Invoice Details</h5>
                            <table class="table table-bordered mt-3">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Description</th>
                                        <th>Amount</th>
                                        <th>Type Bill</th>
                                    </tr>
                                </thead>
                                <tbody id="invoiceDetailsBody">
                                    <c:forEach var="detail" items="${inv.details}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${detail.description}</td>
                                            <td>${detail.amount}</td>
                                            <td>${detail.billType}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${inv.muon > 0}">
                                <div class="mb-3">
                                    <strong>Late bill penalty:</strong> $<span id="lateFee">${inv.muon}</span>
                                </div>
                            </c:if>
                            <div class="mb-3">
                                <strong>Total Amount:</strong> $<span id="totalAmount">${inv.totalAmount}</span>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="showConfirmPaymentModal(${inv.invoiceID})">Confirm Payment</button>

                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <!-- Modal Confirm Payment -->
        <div class="modal fade" id="confirmPaymentModal" tabindex="-1" aria-labelledby="confirmPaymentModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered"> <!-- Thêm class modal-dialog-centered -->
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmPaymentModalLabel">Confirm Payment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to confirm payment for this invoice?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a id="confirmPaymentBtn" href="#" class="btn btn-primary">Confirm</a>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showInvoiceDetails(invoiceID) {
                window.location.href = "<%= request.getContextPath() %>/accountant/UpdateStatusInvoice?invoiceID=" + invoiceID;
            }


            function showConfirmPaymentModal(invoiceID) {
                document.getElementById("confirmPaymentBtn").href = "<%= request.getContextPath() %>/accountant/makepaid?invoiceID=" + invoiceID;
                var myModal = new bootstrap.Modal(document.getElementById('confirmPaymentModal'));
                myModal.show();
            }
            document.addEventListener("DOMContentLoaded", function () {
                const inv = "${inv}";
                if (inv && inv !== "") {
                    const modal = new bootstrap.Modal(document.getElementById('invoiceDetailModal'));
                    modal.show();
                }
            });
        </script>

    </body>
</html>