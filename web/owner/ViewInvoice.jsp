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

        <%@include file="/manager/menumanager.jsp" %>


        <div id="main">
            <div class="container ">

                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                    <h2>Invoices</h2>
                    <div class="d-flex align-items-center">
                        <a href="<%= request.getContextPath() %>/owner/ViewHistoryInvoice" class="btn d-flex align-items-center me-2" 
                           style="background-color: orange; border-color: orange; color: white;">
                            View History
                        </a>
                    </div>
                </div>
                <div class="search-sort-container">
                    <div class="row mb-3 align-items-center" >

                        <div class="col-md-8">
                            <form action="ViewInvoice" method="get" class="d-flex gap-2">
                                <label for="FromDate" class="form-label align-self-center">From:</label>
                                <input type="date" class="form-control" id="FromDate" name="FromDate" 
                                       value="${selectedFromDate}">
                                <label for="dueDate" class="form-label align-self-center">Due:</label>
                                <input type="date" class="form-control" id="dueDate" name="dueDate" 
                                       value="${selectedDueDate}">
                                <button type="submit" class="btn btn-primary" style="width: 70px;">Filter</button>
                            </form>
                        </div>
                        <div class="col-md-4">
                            <form action="ViewInvoice" method="get" class="d-flex gap-2">
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
                            <th>Public Date</th>
                            <th>Payment Term</th>

                            <th>Late(0,1%/d)</th>
                            <th  style="width:60px">Actions</th>
                        </tr>
                    </thead>
                    <tbody style="background:white" id="tableBody">

                        <c:forEach items="${sessionScope.ListInvoiceOwner}" var="l">

                            <tr>
                                <td>${l.invoiceID}</td>
                                <td>${l.description}</td>
                                <td>${l.totalAmount + l.muon}</td>
                                <td>${l.apartment.apartmentName}</td>
                                <td>${l.publicDateft}</td>
                                <td>${l.dueDateft}</td>
                                <td>
                                    <c:if test="${l.muon != 0}">
                                        <p>Islate</p>
                                    </c:if>
                                </td>
                                <td style="width:100px"><a href="<%= request.getContextPath() %>/owner/PaymentServlet?invoiceID=${l.invoiceID}" class="btn btn-success btn-sm">
                                        <i class="fas fa-credit-card"></i> 
                                    </a>
                                    <a href="<%= request.getContextPath() %>/owner/DetailInvoiceOwner?invoiceID=${l.invoiceID}&page=viewinvoice" class="btn btn-info btn-sm">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </td>
                            </tr>

                        </c:forEach>

                    </tbody>
                </table>
                <c:if test="${not empty  sessionScope.ListInvoiceOwner}">
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
                            <a href="/ownerViewInvoice?page=${requestScope.currentPage - 1}&search=${search}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &lt;
                            </a>
                        </c:if>
                        <c:forEach begin="${startPage}" end="${endPage}" var="page">
                            <a href="/ownerViewInvoice?page=${page}&search=${search}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;
                               <c:if test='${page == requestScope.currentPage}'> background-color: #007bff; color: white; </c:if>">
                                ${page}
                            </a>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage < requestScope.totalPage}">
                            <a href="/ownerViewInvoice?page=${requestScope.currentPage + 1}&search=${search}&FromDate=${selectedFromDate}&dueDate=${selectedDueDate}"
                               style="padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                &gt;
                            </a>
                        </c:if>
                    </div>

                </c:if>
                <c:if test="${empty sessionScope.ListInvoiceOwner}">
                    <div style="display: flex; justify-content: center; align-items: center; height: 50vh;">
                        <p style="font-size: 20px;">${message}</p>
                    </div>
                </c:if>

            </div>
        </div>





        <!-- <script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script> -->
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <script>
            $(document).ready(function () {
                $("#dueDate").datepicker({
                    dateFormat: "dd/mm/yy"
                });
            });
        </script>


    </body>

</html>
