<%-- 
    Document   : resident
    Created on : Jan 16, 2025, 3:13:40 AM
    Author     : nkiem
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Invoice Manager</title>
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />   
        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="<%= request.getContextPath() %>/https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/iconly/bold.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/pages/index.css"/>
        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
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
    </head>

    <body>
        <%@include file="menuaccountant.jsp" %>
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
                        <!-- Hiển thị lỗi chung -->
                        <c:if test="${not empty errorde}">
                            <div class="alert alert-danger" role="alert">
                                ${errorde}
                            </div>
                        </c:if>

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
                                            <td><input type="text" class="form-control" name="descriptionde" required>
                                                <c:if test="${not empty error}">
                                                    <div class="alert alert-danger" role="alert">
                                                        ${error}
                                                    </div>
                                                </c:if></td>
                                            <td><input type="text" class="form-control" name="amount" required>
                                                <c:if test="${not empty amountError}">
                                                    <div class="alert alert-danger" role="alert">
                                                        ${amountError}
                                                    </div>
                                                </c:if>
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
                                    <c:otherwise>
                                        <c:forEach items="${invoice.details}" var="de" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td><input type="text" class="form-control" name="descriptionde" value="${de.description}" required></td>
                                                <td><input type="text" class="form-control" name="amount" value="${de.amount}" required></td>
                                                <td>
                                                    <select name="typebills" class="form-select" required>
                                                        <c:forEach items="${listType}" var="o">
                                                            <option value="${o.id}" ${o.id == de.typeBillId ? 'selected' : ''}>${o.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-danger" onclick="removeInvoiceDetail(this)">Remove</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                            <!-- Hiển thị lỗi Detail -->
                            <c:if test="${not empty detailError}">
                                <div class="alert alert-danger" role="alert">
                                    ${detailError}
                                </div>
                            </c:if>
                        </table>
                        <button type="button" class="btn btn-primary" onclick="addInvoiceDetail()">Add Invoice Detail</button>
                        <div class="d-flex justify-content-between mt-4">
                            <a href="<%= request.getContextPath() %>/InvoicesManager" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Back</a>
                            <button type="submit" class="btn btn-success">Save <i class="bi bi-plus-lg"></i></button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </div>
</div>
<script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
<script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>
<script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
<script>
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
                                cell3.innerHTML = '<input type="text" class="form-control" name="amount" required>';
                                cell4.innerHTML = `
            <select name="typebills" class="form-select" required>
    <c:forEach items="${listType}" var="o">
                    <option value="${o.id}">${o.name}</option>
    </c:forEach>
            </select>
        `;
                                cell5.innerHTML = '<button type="button" class="btn btn-danger" onclick="removeInvoiceDetail(this)">Remove</button>';
                            }

                            function removeInvoiceDetail(button) {
                                const row = button.closest('tr');
                                row.remove();
                                // Update row numbers
                                const table = document.getElementById("invoiceDetailsTable");
                                for (let i = 0; i < table.rows.length; i++) {
                                    table.rows[i].cells[0].innerHTML = i + 1;
                                }
                            }
</script>
</body>

</html>
