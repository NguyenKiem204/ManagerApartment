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
        <title>Fund Manager</title>
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
            <main id="content">

                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1100px; margin: 10px auto;">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="text-left">Fund Management List</h3>
                        <div>
                            <a href="<%= request.getContextPath() %>/manager/ViewAllTrans" class="btn btn-secondary">
                                <i class="fas fa-list"></i> View All Transactions
                            </a>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty funds}">
                            <table class="table mt-3">
                                <thead class="table-dark">
                                    <tr>
                                        <th>Fund Name</th>
                                        <th>Type Fund</th>
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
                                                ${fund.typeFund.typeName}

                                            </td>
                                            
                                            <td>
                                                <fmt:formatNumber value="${fund.currentBalance}" pattern="#,##0" />
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



                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1100px; margin: 10px auto;">
                    <h4 class="text-center">Expense Detail on </h4>
                    <table class="tableinvoice mt-4">
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Amount</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="totalAmount" value="0" />
                            <c:choose>
                                <c:when test="${not empty expenseDetails}">
                                    <c:forEach items="${expenseDetails}" var="detail">
                                        <tr>
                                            <td>${detail.description}</td>
                                            <td>${detail.typeExpense.typeName}</td>
                                            <td>${detail.status}</td>
                                            <td>
                                                <fmt:formatNumber value="${detail.amount}" pattern="#,##0.00" />
                                            </td>
                                            <td>
                                                <a href="<%= request.getContextPath() %>/manager/updateExpenseStatus?expenseDetailID=${detail.expenseDetailID}&status=Approved&typeid=${detail.typeExpense.typeExpenseID}&amount=${detail.amount}&des=${detail.description}" class="btn btn-success"><i class="fa-solid fa-thumbs-up"></i>

                                                    <a href="<%= request.getContextPath() %>/manager/updateExpenseStatus?expenseDetailID=${detail.expenseDetailID}&status=Rejected&typeid=${detail.typeExpense.typeExpenseID}&amount=${detail.amount}&des=${detail.description}" class="btn btn-danger"><i class="fa-solid fa-ban"></i>

                                                        </td>
                                                        </tr>
                                                        <c:set var="totalAmount" value="${totalAmount + (detail.amount ne null ? detail.amount : 0)}" />
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="4" class="empty-message">No expenses recorded for today.</td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                            </tbody>

                                            <tfoot>
                                                <tr>
                                                    <td colspan="3" style="text-align: right; font-weight: bold;">Total Amount:</td>
                                                    <td style="font-weight: bold;">
                                                        <fmt:formatNumber value="${totalAmount}" pattern="#,##0.00" />
                                                    </td>
                                                </tr>
                                            </tfoot>
                                            </table>
                                            </div>
                                            </main>
                                            </div>
                                            <script>

                                                <script>
                                                function updateExpenseStatus(expenseDetailID, status) {
                                                        if (confirm(`Are you sure you want to ${status.toLowerCase()} this expense?`)) {
                                                fetch(`UpdateExpenseStatusServlet?expenseDetailID=${expenseDetailID}&status=${status}`, {
                                                method: "POST"
                                                })
                                                        .then(response => response.text())
                                                        .then(data => {
                                                        alert(data);
                                                                location.reload();
                                                        })
                                                        .catch(error => console.error("Error:", error));
                                                }
            }
                                            </script>
                                            </body>





                                            </html>
