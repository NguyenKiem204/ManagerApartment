<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Revenue</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <style>
            .text-truncation {
                white-space: nowrap !important;
                overflow: hidden !important;
                width: 85% !important;
                text-overflow: ellipsis !important;
            }
            .custom-card {
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
                position: relative;
                background-color: #fff;
            }
            .custom-card .icon-container {
                position: absolute;
                top: 15px;
                right: 15px;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .custom-card .icon-box {
                background-color: rgba(0, 123, 255, 0.1);
                color: #007bff;
                border-radius: 50%;
                width: 36px;
                height: 36px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .custom-card .icon-box i {
                font-size: 18px;
            }
            .transaction-list {
                list-style: none;
                padding-left: 0;
            }
            .transaction-item {
                display: flex;
                align-items: center;
                padding: 10px;
                border-left: none;
                position: relative;
            }
            .transaction-item::before {
                content: none;
            }
            .transaction-icon {
                margin-right: 10px;
                color: red;
            }
            .transaction-text {
                flex-grow: 1;
            }
            .transaction-amount {
                font-weight: bold;
            }
            .positive {
                color: green;
            }
            .negative {
                color: red;
            }
            .time {
                font-size: 0.8em;
                color: gray;
            }
            .form-select {
                height: 35px; /* Điều chỉnh chiều cao để vừa với label */
                padding: 5px 10px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main" style="margin-top: -60px">
            <div class="page-heading d-flex justify-content-between align-items-center">
                <h3>Statistics</h3>
                <div class="d-flex align-items-center">
                    <form action="Revenue" method="get">
                        <label for="filter" class="mb-0">View:</label>
                        <select name="filter" id="time" class="form-control form-select" onchange="this.form.submit()">
                            <option value="Day" ${filter == 'Day' ? 'selected' : ''}>Day</option>
                            <option value="Month" ${filter == 'Month' ? 'selected' : ''}>Month</option>
                            <option value="Year" ${filter == 'Year' ? 'selected' : ''}>Year</option>
                        </select>
                    </form>

                </div>
            </div>

            <div class="page-content">
                <section class="row">
                    <div class="col-12">
                        <div class="row">
                            <div class="col-6 col-lg-3 col-md-6">
                                <div class="card custom-card">
                                    <div class="card-body">
                                        <h6 class="text-muted font-semibold">Total current balance </h6>
                                        <h2 class="font-extrabold mb-2">${total}d</h2>

                                        <div class="icon-container">
                                            <div class="icon-box">
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-lg-3 col-md-6">
                                <div class="card custom-card">
                                    <div class="card-body">
                                        <h6 class="text-muted font-semibold">${time} Income </h6>
                                        <h2 class="font-extrabold mb-2">${income}d</h2>

                                        <div class="icon-container">
                                            <div class="icon-box">
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> <div class="col-6 col-lg-3 col-md-6">
                                <div class="card custom-card">
                                    <div class="card-body">
                                        <h6 class="text-muted font-semibold">Spending ${time}  </h6>
                                        <h2 class="font-extrabold mb-2">${spending}d</h2>

                                        <div class="icon-container">
                                            <div class="icon-box">
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> <div class="col-6 col-lg-3 col-md-6">
                                <div class="card custom-card">
                                    <div class="card-body">
                                        <h6 class="text-muted font-semibold">${time} balance</h6>
                                        <h2 class="font-extrabold mb-2">${balance}d</h2>

                                        <div class="icon-container">
                                            <div class="icon-box">
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="card border-cam">
                                    <div class="card-header d-flex justify-content-between align-items-center">
                                        <h4>Monthly Cash Flow</h4>
                                    </div>
                                    <div class="card-body">
                                        <div id="monthly-cashflow-chart"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="card border-cam">
                                    <div class="card-header bg-white">
                                        <h4 class="mb-0">Recent Transactions</h4>
                                    </div>
                                    <div class="card-body">
                                        <ul class="transaction-list">

                                            <c:forEach var="transaction" items="${rencent}" varStatus="status" begin="0" end="5">
                                                <li class="transaction-item">

                                                    <div class="transaction-text">
                                                        <div>${transaction.transactionID}</div>
                                                        <div>${transaction.description}</div>
                                                    </div>
                                                    <div class="transaction-amount ${transaction.transactionType == 'Income' ? 'positive' : 'negative'}">
                                                        ${transaction.transactionType == 'Income' ? '+' : '-'} <fmt:formatNumber value="${transaction.amount}" pattern="#,##0.00" type="number"/>
                                                                                                                               
                                                    </div>
                                                   
                                                </li>
                                            </c:forEach>

                                        </ul>
                                        <a href="<%= request.getContextPath() %>/manager/ViewAllTrans" class="text-primary d-block text-center mt-3">See more ></a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <div class="row">
                <div class="col-12 col-xl-5">
                    <div class="card custom-card">
                        <div class="card-header">
                            <h6 class="text-muted font-semibold">
                                This Month's Statistics <i class="fa-solid fa-circle-question"></i>
                            </h6>
                        </div>
                        <div class="card-body text-center">
                            <h2 class="font-extrabold">${income}d</h2>
                            <span class="badge bg-primary">Average:${avg}d/income</span>
                            <div class="row mt-4">

                                <div class="col-md-6 stat-box">
                                    <h6 class="text-muted">Income Transactions</h6>
                                    <h5 class="font-extrabold">${tcome}</h5>
                                    <span class="text-muted">Successful Income Transactions</span>
                                    <h6 class="font-bold">${pcome}</h6>
                                </div>
                                <div class="col-md-6 stat-box">
                                    <h6 class="text-muted">Expense Transactions</h6>
                                    <h5 class="font-extrabold">${tout}</h5>
                                    <span class="text-muted">Successful Expense Transactions</span>
                                    <h6 class="font-bold">${aout}</h6>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-xl-7">
                    <div class="card border-cam">
                        <div class="card-header">
                            <h4>Fund Management</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <c:choose>
                                    <c:when test="${not empty funds}">
                                        <table class="table table-hover table-lg">
                                            <thead class="table-dark">
                                                <tr>
                                                    <th>Fund Name</th>

                                                    <th>Current Balance</th>
                                                    <th>Status</th>
                                                    <th>Created At</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${funds}" var="fund">
                                                    <tr>
                                                        <td>${fund.fundName}</td>

                                                        <td>
                                                            <fmt:formatNumber value="${fund.currentBalance}" pattern="#,##0.00" />
                                                        </td>
                                                        <td>${fund.status}</td>
                                                        <td>${fund.createdAtft}</td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-center text-muted">No funds available.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/apexcharts/3.41.0/apexcharts.min.js"></script>

        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                // Lấy dữ liệu từ JSP (chuỗi số cách nhau bằng dấu phẩy)
                                var monthlyIncome = "${monthlyIncome}".split(",").map(Number);
                                var monthlySpending = "${monthlySpending}".split(",").map(Number);
                                var monthlyBalance = "${monthlyBalance}".split(",").map(Number);

                                console.log("Income Data:", monthlyIncome);
                                console.log("Spending Data:", monthlySpending);
                                console.log("Balance Data:", monthlyBalance);

                                var options = {
                                    series: [
                                        {name: "Income", data: monthlyIncome},
                                        {name: "Expenses", data: monthlySpending},
                                        {name: "Balance", data: monthlyBalance}
                                    ],
                                    chart: {type: "bar", height: 400},
                                    plotOptions: {bar: {horizontal: false, columnWidth: "50%"}},
                                    dataLabels: {enabled: false},
                                    colors: ["#007bff", "#f4a261", "#2a9d8f"],
                                    xaxis: {
                                        categories: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
                                    },
                                    yaxis: {
                                        labels: {formatter: (value) => value.toLocaleString() + " VND"}
                                    },
                                    legend: {position: "bottom"}
                                };

                                var chart = new ApexCharts(document.querySelector("#monthly-cashflow-chart"), options);
                                chart.render();
                            });
        </script>

    </body>
</html>