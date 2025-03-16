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
        </style>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <script>
            // Hàm định dạng số thành XX.00
            function formatDecimal(input) {
                let value = input.value.replace(/[^0-9.]/g, '').trim(); // Chỉ giữ lại số và dấu chấm
                let floatValue = parseFloat(value);

                if (!isNaN(floatValue)) {
                    input.value = floatValue.toFixed(2); // Hiển thị dạng XX.00
                } else {
                    input.value = "0.00"; // Nếu không hợp lệ, đặt thành 0.00
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
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <main id="content">
                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1000px; margin: 0 auto;">
                    <h4 class="text-center mb-4">Import Expense</h4>
                    <form action="addnewinvoice" method="post">

                        <div class="form-group mb-3">
                            <label for="description">Description</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>
                        <div class="form-group mb-3">
                            <label for="description">Amount</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>
                        <div class="form-group mb-3">
                            <label>Type</label>
                            <select name="typeid" class="form-select" aria-label="Default select example">
                                <c:forEach items="${lte}" var="o">
                                    <option value="${o.typeExpenseID}" ${o.typeExpenseID == typeexp ? 'selected' : ''}>${o.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="<%= request.getContextPath() %>/accountant/ViewExpense" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Back</a>
                            <button type="submit" class="btn btn-success">Save <i class="bi bi-plus-lg"></i></button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>
