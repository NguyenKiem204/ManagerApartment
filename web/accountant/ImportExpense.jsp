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
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-bottom: 15px;
                text-align: center;
            }
            .tableinvoice th, .tableinvoice td {
                width: 25%;
                text-align: center;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
        </style>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <script>
            function formatDecimal(input) {
                let value = input.value.replace(/[^0-9.]/g, '').trim(); // Chỉ giữ lại số và dấu chấm
                let floatValue = parseFloat(value);

                if (!isNaN(floatValue)) {
                    input.value = floatValue.toFixed(2); // Hiển thị dạng XX.00
                } else {
                    input.value = "0.00"; // Nếu không hợp lệ, đặt thành 0.00
                }
            }
        </script>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>

        <div id="main">
            <main id="content">
                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1100px; margin: 0 auto;">
                    <h4 class="text-center mb-4">Import Expense</h4>

                    <!-- Hiển thị thông báo lỗi -->
                    <c:if test="${not empty errorMessage}">
                        <div class="error-message">${errorMessage}</div>
                    </c:if>

                    <form action="<%= request.getContextPath() %>/accountant/ImportExpense" method="post">
                        <div class="form-group mb-3">
                            <label for="description">Description</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>

                        <div class="form-group mb-3">
                            <label for="amount">Amount</label>
                            <input type="text" class="form-control" id="amount" name="amount" required onblur="formatDecimal(this)">
                        </div>

                        <div class="form-group mb-3">
                            <label>Type</label>
                            <select name="typeid" class="form-select">
                                <c:forEach items="${lte}" var="o">
                                    <option value="${o.typeExpenseID}" ${o.typeExpenseID == typeexp ? 'selected' : ''}>
                                        ${o.typeName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="<%= request.getContextPath() %>/accountant/ViewExpense" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Back
                            </a>
                            <button type="submit" class="btn btn-success">
                                Save <i class="bi bi-plus-lg"></i>
                            </button>
                        </div>
                    </form>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="totalAmount" value="0" />
                            <c:choose>
                                <c:when test="${not empty exptoday.expenseDetails}">
                                    <c:forEach items="${exptoday.expenseDetails}" var="detail">
                                        <tr>
                                            <td>${detail.description}</td>
                                            <td>${detail.typeExpense.typeName}</td>
                                            <td>${detail.status}</td>
                                            <td>
                                                <fmt:formatNumber value="${detail.amount}" pattern="0.00" />
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
                                    <fmt:formatNumber value="${totalAmount}" pattern="0.00" />
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </main>
        </div>
    </body>
</html>
