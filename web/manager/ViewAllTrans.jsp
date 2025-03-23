<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Transaction Manager</title>
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
            }
            .pagination {
                text-align: center;
                margin-top: 20px;
            }
            .pagination a {
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #ddd;
                color: #007bff;
                margin: 0 4px;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
                border: 1px solid #007bff;
            }
            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }
            .rows-per-page {
                margin-top: 10px;
                text-align: left; /* Di chuyển sang trái */
                margin-bottom: 20px; /* Thêm khoảng cách phía dưới */
            }

            .rows-per-page form {
                display: flex;
                align-items: center;
                gap: 10px; /* Khoảng cách giữa label và dropdown */
            }

            .rows-per-page label {
                font-size: 14px;
                margin: 0;
            }

            .rows-per-page select {
                padding: 5px 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 4px;
                background-color: #fff;
                cursor: pointer;
            }

            .rows-per-page select:hover {
                border-color: #888;
            }

            .rows-per-page select:focus {
                outline: none;
                border-color: #007bff;
            }
            .pagination-container {
                display: flex;
                justify-content: space-between; /* Dàn đều phần tử */
                align-items: center;
                margin-top: 20px;
            }

            .btn-back {
                display: inline-block;
                padding: 8px 16px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
            }

            .btn-back:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Transaction Manager</h2>

                <div class="row mb-3 align-items-center">
                    <div class="col-md-12">
                        <form action="ViewAllTrans" method="get" class="filter-form" >
                            <label for="fromDate">From Date:</label>
                            <input type="text"  id="datePicker" placeholder="dd/MM/yyyy" name="fromDate" value="${param.fromDate}">

                            <label for="toDate">To Date:</label>
                            <input type="text"  id="datePicker" placeholder="dd/MM/yyyy" name="toDate" value="${param.toDate}">

                            <label for="transactionType">Transaction Type:</label>
                            <select id="transactionType" name="transactionType">
                                <option value="">All</option>
                                <option value="Income" ${param.transactionType == 'Income' ? 'selected' : ''}>Income</option>
                                <option value="Expense" ${param.transactionType == 'Expense' ? 'selected' : ''}>Expense</option>
                            </select>

                            <label for="amountRange">Amount Range:</label>
                            <select id="amountRange" name="amountRange">
                                <option value="">All</option>
                                <option value="0-500000" ${param.amountRange == '0-500000' ? 'selected' : ''}>Dưới 500k</option>
                                <option value="1000000-5000000" ${param.amountRange == '1000000-5000000' ? 'selected' : ''}>1 triệu - 5 triệu</option>
                                <option value="5000000-10000000" ${param.amountRange == '5000000-10000000' ? 'selected' : ''}>5 triệu - 10 triệu</option>
                                <option value="10000000-50000000" ${param.amountRange == '10000000-50000000' ? 'selected' : ''}>10 triệu - 50 triệu</option>
                                <option value="50000000-100000000" ${param.amountRange == '50000000-100000000' ? 'selected' : ''}>50 triệu - 100 triệu</option>
                                <option value="100000000-" ${param.amountRange == '100000000-' ? 'selected' : ''}>Trên 100 triệu</option>
                            </select>

                            <button type="submit" class="btn btn-primary">Filter</button>
                        </form>
                    </div> 
                </div>
                <!-- Rows Per Page -->
                <div class="rows-per-page">
                    <form action="ViewAllTrans" method="get">
                        <label for="rowsPerPage">Rows per page:</label>
                        <select name="rowsPerPage" id="rowsPerPage" onchange="this.form.submit()">
                            <option value="5" ${param.rowsPerPage == 5 ? 'selected' : ''}>5</option>
                            <option value="10" ${param.rowsPerPage == 10 ? 'selected' : ''}>10</option>
                            <option value="20" ${param.rowsPerPage == 20 ? 'selected' : ''}>20</option>
                            <option value="50" ${param.rowsPerPage == 50 ? 'selected' : ''}>50</option>
                        </select>
                        <input type="hidden" name="fromDate" value="${param.fromDate}">
                        <input type="hidden" name="toDate" value="${param.toDate}">
                        <input type="hidden" name="transactionType" value="${param.transactionType}">
                        <input type="hidden" name="amountRange" value="${param.amountRange}">
                    </form>
                </div>

                <!-- Transaction Table -->
                <table>
                    <thead>
                        <tr>
                            <th>Transaction ID</th>

                            <th>Amount</th>
                            <th>Transaction Type</th>
                            <th>Description</th>
                            <th>Transaction Date</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.allTransactions}" var="t">
                            <tr>
                                <td>${t.transactionID}</td>

                                <td><fmt:formatNumber value="${t.amount}" pattern="#,##0.00"/></td>
                                <td>${t.transactionType}</td>
                                <td>${t.description}</td>
                                <td>${t.transactionDateft}</td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <div class="pagination-container">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="ViewAllTrans?page=${currentPage - 1}&rowsPerPage=${rowsPerPage}&fromDate=${param.fromDate}&toDate=${param.toDate}&transactionType=${param.transactionType}&amountRange=${param.amountRange}">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="ViewAllTrans?page=${i}&rowsPerPage=${rowsPerPage}&fromDate=${param.fromDate}&toDate=${param.toDate}&transactionType=${param.transactionType}&amountRange=${param.amountRange}" ${i == currentPage ? 'class="active"' : ''}>${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="ViewAllTrans?page=${currentPage + 1}&rowsPerPage=${rowsPerPage}&fromDate=${param.fromDate}&toDate=${param.toDate}&transactionType=${param.transactionType}&amountRange=${param.amountRange}">Next</a>
                        </c:if>
                    </div>

                    <!-- Nút Back -->
                    <a href="<%= request.getContextPath() %>/manager/FundManager" class="btn-back">Back</a>
                </div>

            </div>
    </body>
</html>