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
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
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
        <div id="app">
            <div id="sidebar" class="active">
                <div class="sidebar-wrapper active" style="border: 2px solid orangered; border-radius: 0 20px 20px 0">
                    <div class="sidebar-header">
                        <div class="d-flex justify-content-between">
                            <div class="logo">
                                <a href="index.html"><img src="assets/images/logo/logo.png" alt="Logo" /></a>
                            </div>
                            <div class="toggler">
                                <a href="#" class="sidebar-hide d-xl-none d-block"><i class="bi bi-x bi-middle"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="sidebar-menu">
                        <ul class="menu">
                            <li class="sidebar-title">Menu</li>

                            <li class="sidebar-item">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-house-door-fill"></i>
                                    <span>Home</span>
                                </a>
                            </li>
                            <li class="sidebar-item active">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-receipt-cutoff"></i>
                                    <span>Invoice Management</span>
                                </a>
                            </li>
                            <li class="sidebar-item ">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-file-earmark-text-fill"></i>
                                    <span>Request</span>
                                </a>
                            </li>
                            <li class="sidebar-item ">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-chat-dots-fill"></i>
                                    <span>FeedBack</span>
                                </a>
                            </li>




                            <!-- =================================??ng nh?p, log out..==================== -->
                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-person-badge-fill"></i>
                                    <span>Setting</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="auth-login.html">Profile</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="LogoutServlet">Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <button class="sidebar-toggler btn x">
                        <i data-feather="x"></i>
                    </button>
                </div>
            </div>
            <div id="main">
                <header class="mb-3">
                    <a href="#" class="burger-btn d-block d-xl-none">
                        <i class="bi bi-justify fs-3"></i>
                    </a>
                </header>
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
                                                    <td><input type="number" class="form-control" name="amount" required></td>
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
                                                        <td><input type="number" class="form-control" name="amount" value="${de.amount}" required></td>
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
                                </table>
                                <button type="button" class="btn btn-primary" onclick="addInvoiceDetail()">Add Invoice Detail</button>
                                <div class="d-flex justify-content-between mt-4">
                                    <a href="InvoicesManager" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Back</a>
                                    <button type="submit" class="btn btn-success">Save <i class="bi bi-plus-lg"></i></button>
                                </div>
                            </form>
                        </div>
                    </main>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>
        <script src="assets/js/main.js"></script>
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
                                        cell3.innerHTML = '<input type="number" class="form-control" name="amount" required>';
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
