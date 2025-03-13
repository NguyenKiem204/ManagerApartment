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
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />   


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

        </style>



    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>







        <div id="main">
            <div class="container ">

                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                    <h2>Invoices Manager</h2>
                    <div class="d-flex align-items-center">


                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-plus-lg me-1"></i>Back to Invoice Manager
                        </a>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="row mb-3 align-items-center" >

                        <div class="col-md-8">
                            <form action="UpdateStatusInvoice" method="get" class="d-flex gap-2">
                                <label for="FromDate" class="form-label align-self-center">From:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="FromDate" 
                                       value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="text" class="form-control" id="datePicker" placeholder="dd/MM/yyyy" name="dueDate" 
                                       value="${selectedDueDate}">
                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>
                                <a href="<%= request.getContextPath() %>/accountant/UpdateStatusInvoice" class="btn btn-info btn-sm">
                                    <i class="fas fa-sync-alt"></i> <!-- Icon reload -->
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


                <table class="tableinvoice ">
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
                                <td>${l.description}</td>
                                <td>${l.totalAmount}</td>
                                <td>${l.apartment.apartmentName}</td>
                                <td>${l.status}</td>
                                <td>${l.dueDate}</td>
                                <td>${l.publicDate}</td>
                                <td>
                                    <c:if test="${l.muon == 1}">

                                        <p>Islate</p>
                                    </c:if>
                                </td>

                                <td style="width:50px">

                                    <button onclick="confirmDelete('${l.invoiceID}')" class="btn btn-success ">Payment</button>
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
                            <a href="UpdateStaqtusInvoice?page=${requestScope.currentPage - 1}&search=${search}&status=${selectedStatus}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
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





        <script src="assets/js/main.js"></script>
        <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmDeleteLabel">Confirm Payment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        The bill was paid at the counter?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a id="deleteConfirmBtn" href="#" class="btn btn-danger">yes</a>
                    </div>
                </div>
            </div>
        </div>


        <script>
                                        function confirmDelete(invoiceID) {
                                            let deleteUrl = "<%= request.getContextPath() %>/accountant/makepaid?invoiceID=" + invoiceID;
                                            document.getElementById("deleteConfirmBtn").href = deleteUrl;
                                            var myModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
                                            myModal.show();
                                        }

                                        window.onload = function () {
                                            // Kiểm tra nếu có thông báo tải file PDF
                                            const invoiceID = new URLSearchParams(window.location.search).get("invoiceID");
                                            if (invoiceID) {
                                                // Tạo URL tải file PDF
                                                const pdfUrl = `<%= request.getContextPath() %>/accountant/makepaid?invoiceID=${invoiceID}`;
                                                            // Chuyển hướng đến URL tải file PDF
                                                            window.location.href = pdfUrl;
                                                        }
                                                    }
                                                    </<script>
                                                    </body>
                </html>
