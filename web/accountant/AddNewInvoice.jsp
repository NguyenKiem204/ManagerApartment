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
            .active::-webkit-scrollbar {
                width: 0px;
                height: 0px;
            }
            .active {
                -ms-overflow-style: none;
                scrollbar-width: none;
            }
            .text-start {
                text-align: left ; /* Căn trái văn bản */
            }
        </style>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>


        <script>

            function formatCurrencyInput(input) {
                let value = input.value.replace(/[^\d.]/g, ''); // Loại bỏ ký tự không phải số hoặc dấu chấm
                let numericValue = parseFloat(value);

                if (!isNaN(numericValue)) {
                    input.value = numericValue.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
                } else {
                    input.value = '';
                }
            }

            // Hàm thêm chi tiết hóa đơn
            function addInvoiceDetail() {
                const table = document.getElementById("invoiceDetailsTable");
                const rowCount = table.rows.length;
                const row = table.insertRow(rowCount);

                const cell1 = row.insertCell(0);
                const cell2 = row.insertCell(1);
                const cell3 = row.insertCell(2);
                const cell4 = row.insertCell(3);
                const cell5 = row.insertCell(4);

                cell1.innerHTML = rowCount + 1;
                cell2.innerHTML = '<input type="text" class="form-control" name="descriptionde" required>';
                cell3.innerHTML = '<input type="text" class="form-control" name="amount" required oninput="this.value = this.value.replace(/[^0-9.]/g, \'\')" onblur="formatDecimal(this)">';

                cell4.innerHTML = `
                    <select name="typebills" class="form-select" required>
            <c:forEach items="${listType}" var="o">
                            <option value="${o.id}">${o.name}</option>
            </c:forEach>
                    </select>
                `;
                cell5.innerHTML = '<button type="button" class="btn btn-danger" onclick="removeInvoiceDetail(this)">Remove</button>';
            }

            // Hàm xóa chi tiết hóa đơn
            function removeInvoiceDetail(button) {
                const row = button.closest('tr');
                row.remove();
                const table = document.getElementById("invoiceDetailsTable");
                for (let i = 0; i < table.rows.length; i++) {
                    table.rows[i].cells[0].innerHTML = i + 1;
                }
            }

            // Định dạng ngày cho ô Due Date
            document.addEventListener("DOMContentLoaded", function () {
                flatpickr("#dueDate", {
                    dateFormat: "d/m/Y",
                    allowInput: true
                });
            });
        </script>
        <script>
            function formatInput(input) {
                // Chỉ giữ lại số và dấu '.'
                input.value = input.value.replace(/[^0-9.]/g, '');
            }

            function formatNumber(input) {
                let rawValue = input.value.replace(/,/g, ''); // Xóa dấu `,`
                let numericValue = parseFloat(rawValue);

                if (!isNaN(numericValue)) {
                    // Format số theo dạng #,##0.00
                    input.value = numericValue.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
                    document.getElementById('rawAmount').value = numericValue; // Lưu giá trị thực vào input ẩn
                } else {
                    input.value = '';
                    document.getElementById('rawAmount').value = '';
                }
            }
        </script>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">

            <main  id="content">
                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1000px; margin: 0 auto;">
                    <h4 class="text-center mb-4">Add New Invoice</h4>
                    <form action="addnewinvoice" method="post">
                        <div class="form-group mb-3">

                            <label>Apartment</label>
                            <select name="apartmentId" class="form-select" aria-label="Default select example">
                                <c:forEach items="${listApartment}" var="o">

                                    <option value="${o.apartmentId}">${o.apartmentName}</option>
                                </c:forEach>
                            </select>
                        </div>  
                        <div class="form-group mb-3">
                            <label for="description">Description</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>


                        <div class="form-group mb-3">
                            <label for="dueDate">Due Date</label>
                            <input type="date" class="form-control" id="dueDate" name="dueDate" required>

                        </div>
                        <!-- Hiển thị lỗi Due Date -->
                        <c:if test="${not empty dueDateError}">
                            <div class="alert alert-danger" role="alert">
                                ${dueDateError}
                            </div>
                        </c:if>


                        <h5 class="mt-4">Invoice Details</h5>
                        <table class="table table-bordered mt-3">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Description</th>
                                    <th>Amount</th>
                                    <th>Type Bill</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="invoiceDetailsTable">
                                <c:choose>
                                    <c:when test="${empty invoice.details}">
                                        <tr>
                                            <td>1</td>
                                            <td><input type="text" class="form-control" name="descriptionde" required></td>
                                            <td>
                                                <input type="text" class="form-control text-start" name="amountFormatted" required 
                                                       id="formattedNumber" oninput="formatInput(this)" onblur="formatNumber(this)">
                                                <input type="hidden" name="amount" id="rawAmount"> <!-- Input ẩn để gửi dữ liệu thực -->
                                            </td>
                                            <td>
                                                <select name="typebills" class="form-select" required>
                                                    <c:forEach items="${listType}" var="o">
                                                        <option value="${o.id}">${o.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-danger" onclick="removeInvoiceDetail(this)">Remove</button>
                                            </td>
                                        </tr>
                                    </c:when>
                                </c:choose>
                            </tbody>
                            <!-- Hiển thị lỗi Detail -->
                            <c:if test="${not empty detailError}">
                                <div class="alert alert-danger" role="alert">
                                    ${detailError}
                                </div>
                            </c:if>
                        </table>
                        <button type="button" class="btn btn-primary" onclick="addInvoiceDetail()">Add Invoice Detail</button> <c:if test="${not empty errorde}">
                            <div class="alert alert-danger" role="alert">
                                ${errorde}
                            </div>
                        </c:if>
                        <div class="d-flex justify-content-between mt-4">
                            <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Back</a>
                            <button type="submit" class="btn btn-success">Save <i class="bi bi-plus-lg"></i></button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>
