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
            #main {
                margin-left: 150px;/* Điều chỉnh theo kích thước sidebar */
                width: calc(100% - 250px); /* Đảm bảo không đè lên sidebar */
                transition: all 0.3s;
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
                        <div class="container mt-2">
                            <div class="container mt-2">
                                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                                    <h2>Invoices Manager</h2>
                                    <div class="d-flex align-items-center">
                                        <a href="updateinvoice" class="btn btn-primary d-flex align-items-center me-2">
                                            <i class="bi bi-arrow-repeat me-1"></i> Update Status
                                        </a>

                                        <a href="addnewinvoice" class="btn btn-success d-flex align-items-center">
                                            <i class="bi bi-plus-lg me-1"></i> Add New Invoice
                                        </a>
                                    </div>
                                </div>



                                <div class="row mb-3">
                                    <div class="col-md-3">
                                        <input type="text" class="form-control" placeholder="Search Invoice...">
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-select">
                                            <option selected>All Apartments</option>
                                            <option>Apartment A</option>
                                            <option>Apartment B</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <select class="form-select">
                                            <option selected>All Status</option>
                                            <option>Paid</option>
                                            <option>Pending</option>
                                            <option>Overdue</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="month" class="form-control">
                                    </div>
                                    <div class="col-md-2">
                                        <input type="date" class="form-control">
                                    </div>
                                </div>

                                <table class="table table-striped table-hover table-bordered caption-top">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Invoice Code</th>
                                            <th>Title</th>
                                            <th>Amount</th>
                                            <th>Apartment</th>
                                            <th>Status</th>
                                            <th>Payment Term</th>
                                            <th>Payment Date</th>
                                            <th>Public Date</th>
                                            <th>Late Payment</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody style="background:white">
                                        <c:forEach items="${sessionScope.ListInvoices}" var="l">

                                            <tr>
                                                <td>${l.invoiceID}</td>
                                                <td>${l.description}</td>
                                                <td>${l.totalAmount}</td>
                                                <td>${l.apartment.apartmentName}</td>
                                                <td>${l.status}</td>
                                                <td>${l.dueDate}</td>
                                                <td></td>

                                                <td>${l.publicDate}</td>
                                                <td>
                                                    <c:if test="${l.late}">
                                                        <p>IsLate</p>
                                                    </c:if>
                                                </td>

                                                <td style="width:200px">
                                        <a href="DetailInvoice?invoiceID=${l.invoiceID}" class="btn btn-info btn-sm">Detail</a>
                                        <c:if test='${l.status eq "Unpaid"}'>
                                            <a href="editinvoice?invoiceID=${l.invoiceID}" class="btn btn-warning btn-sm">Edit</a>
                                            <button onclick="confirmDelete('${l.invoiceID}')" class="btn btn-danger btn-sm">Delete</button>
                                        </c:if>
                                    </td>

                                            </tr>

                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                </div>

            </div>




            <!-- <script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script> -->
            <script src="assets/js/bootstrap.bundle.min.js"></script>

            <script src="assets/vendors/apexcharts/apexcharts.js"></script>
            <script src="assets/js/pages/dashboard.js"></script>

            <script src="assets/js/main.js"></script>
            <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmDeleteLabel">Confirm Deletion</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete this invoice?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <a id="deleteConfirmBtn" href="#" class="btn btn-danger">Delete</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- JavaScript -->
            <script src="assets/js/bootstrap.bundle.min.js"></script>
            <script>
                function confirmDelete(invoiceID) {
                    let deleteUrl = "DeleteInvoice?invoiceID=" + invoiceID;
                    document.getElementById("deleteConfirmBtn").href = deleteUrl;
                    var myModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
                    myModal.show();
                }
            </script>
    </body>

</html>
