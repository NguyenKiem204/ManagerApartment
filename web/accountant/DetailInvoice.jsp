<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Invoice Manager</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />   
        <link rel="preconnect" href="<%= request.getContextPath() %>/https://fonts.gstatic.com" />
        <link href="<%= request.getContextPath() %>/https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
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
        <div id="app">
            <!-- Sidebar -->
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
                                <a href="InvoicesManager" class="sidebar-link">
                                    <i class="bi bi-receipt-cutoff"></i>
                                    <span>Invoice Management</span>
                                </a>
                            </li>
                            <li class="sidebar-item">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-file-earmark-text-fill"></i>
                                    <span>Request</span>
                                </a>
                            </li>
                            <li class="sidebar-item">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-chat-dots-fill"></i>
                                    <span>FeedBack</span>
                                </a>
                            </li>
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
            <!-- Main content -->
            <div id="main">
                <header class="mb-3">
                    <a href="#" class="burger-btn d-block d-xl-none">
                        <i class="bi bi-justify fs-3"></i>
                    </a>
                </header>
                <div class="container mt-5">
                    <div class="card p-4 shadow-lg rounded-4" style="max-width: 1000px; margin: 0 auto;">
                        <h3 class="text-center mb-4">Invoice Details</h3>
                        <div class="mb-3">
                            <H2>Invoice code:</h2> <c:out value="${invoice.apartment.apartmentName}" />
                        </div>

                        <div class="mb-3">
                            <strong>Apartment:</strong> <c:out value="${invoice.apartment.apartmentName}" />
                        </div>
                        <div class="mb-3">
                            <strong>Resident:</strong> <c:out value="${invoice.resident.fullName}" />
                        </div>
                        <div class="mb-3">
                            <strong>Description:</strong> <c:out value="${invoice.description}" />
                        </div>
                        
                        <div class="mb-3">
                            <strong>Public Date:</strong> <c:out value="${invoice.publicDate}" />
                        </div>
                        <div class="mb-3">
                            <strong>Due Date:</strong> <c:out value="${invoice.dueDate}" />
                        </div>
                        <div class="mb-3">
                            <strong>Status:</strong> 
                            <span class="badge bg-${invoice.status == 'Paid' ? 'success' : 'warning'}">
                                <c:out value="${invoice.status}" />
                            </span>
                        </div>


                        <h5 class="mt-4">Invoice Details</h5>
                        <table class="table table-bordered mt-3">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Description</th>
                                    <th>Amount</th>
                                    <th>Type Bill</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail" items="${invoice.details}" varStatus="loop">
                                    <tr>
                                        <td><c:out value="${loop.index + 1}" /></td>
                                        <td><c:out value="${detail.description}" /></td>
                                        <td><c:out value="${detail.amount}" /></td>
                                        <td><c:out value="${detail.billType}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="mb-3">
                            <strong>Total Amount:</strong> $<c:out value="${invoice.totalAmount}" />
                        </div>

                        <div class="d-flex justify-content-end mt-4">
                            <a href="<%= request.getContextPath() %>/InvoicesManager" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Back
                            </a>

                            <c:if test='${invoice.status eq "Unpaid"}'>
                                <a href="<%= request.getContextPath() %>/editinvoice?id=${invoice.invoiceID}" class="btn btn-primary ms-2">Edit Invoice</a>
                            </c:if>
                        </div>


                    </div>
                </div>

            </div>
        </div>



        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>