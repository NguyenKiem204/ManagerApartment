<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Home</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/pages/index.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />
    </head>

    <body>
        <div id="app">
            <nav class="app-header navbar navbar-expand bg-body">
                <div class="container-fluid">
                    <!-- Navbar Links -->
                    <ul class="navbar-nav kiem_can_trai">
                        <li class="nav-item d-none d-md-block">
                            <a href="<%= request.getContextPath() %>/redirect/home" class="nav-link">Home</a>
                        </li>
                        <li class="nav-item d-none d-md-block">
                            <a href="#" class="nav-link">Contact</a>
                        </li>
                    </ul>

                    <!-- User and Notification Dropdowns -->
                    <ul class="navbar-nav ms-auto">
                        <!-- User Menu -->
                        <c:choose>
                            <c:when test="${not empty sessionScope.staff}">
                                <c:set var="user" value="${sessionScope.staff}" />
                                <c:set var="role" value="staff" />
                            </c:when>
                            <c:when test="${not empty sessionScope.resident}">
                                <c:set var="user" value="${sessionScope.resident}" />
                                <c:set var="role" value="resident" />
                            </c:when>
                        </c:choose>

                        <li class="nav-item dropdown user-menu">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img src="<%= request.getContextPath() %>/${user.image.imageURL}" class="user-image rounded-circle shadow"
                                     alt="User Image" />
                                <span class="d-none d-md-inline">${user.fullName}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="<%= request.getContextPath() %>/${user.image.imageURL}" class="rounded-circle shadow"
                                         alt="User Image" />
                                    <p>
                                        ${user.fullName} - ${role}
                                        <small>Member since Nov. 2024</small>
                                    </p>
                                </li>
                                <li class="user-footer d-flex justify-content-between">
                                    <a href="<%= request.getContextPath() %>/profile-${role}" class="btn btn-default btn-flat">Profile</a>
                                    <a href="#" class="btn btn-default btn-flat">Setting</a>
                                    <a href="<%= request.getContextPath() %>/logout" class="btn btn-default btn-flat">Logout</a>
                                </li>
                            </ul>
                        </li>


                        <!-- Notification Menu -->
                        <li class="nav-item dropdown user-menu">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa-solid fa-bell" style="font-size: 1.5rem; margin-top: 10px"></i>
                            </a>
                            <ul class="notify-menu dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="notify">
                                    <a href="!#">
                                        <div class="user-alert">
                                            <img src="./assets/images/faces/nguyenkiem.jpg"
                                                 class="user-image rounded-circle shadow" alt="User Image" />
                                        </div>
                                        <p class="text-alert">
                                            <strong>Dinh Tung</strong> mentioned you in a comment in
                                            the Spring-boot Java web Vietnam group.
                                        </p>
                                    </a>
                                </li>

                                <li class="notify">
                                    <a href="#!">
                                        <div class="user-alert">
                                            <img src="./assets/images/faces/nguyenkiem.jpg"
                                                 class="user-image rounded-circle shadow" alt="User Image" />
                                        </div>
                                        <p class="text-alert">
                                            <strong>Dinh Tung</strong> mentioned you in a comment in
                                            the Spring-boot Java web Vietnam group.
                                        </p>
                                    </a>
                                </li>

                                <li class="notify">
                                    <a href="#!">
                                        <div class="user-alert">
                                            <img src="./assets/images/faces/nguyenkiem.jpg"
                                                 class="user-image rounded-circle shadow" alt="User Image" />
                                        </div>
                                        <p class="text-alert">
                                            <strong>Dinh Tung</strong> mentioned you in a comment in
                                            the Spring-boot Java web Vietnam group.
                                        </p>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
            <div id="sidebar" class="active">
                <div class="sidebar-wrapper active" style="border: 2px solid orangered">
                    <div class="sidebar-header">
                        <div class="d-flex justify-content-between">
                            <div class="logo">
                                <a href="menumanager.jsp"><img src="<%= request.getContextPath() %>/assets/images/logo/logo1.png"
                                                               alt="Logo" /></a>
                            </div>
                            <div class="toggler">
                                <a href="#" class="sidebar-hide d-xl-none d-block"><i
                                        class="bi bi-x bi-middle"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="sidebar-menu">
                        <ul class="menu">
                            <li class="sidebar-title">Menu</li>

                            <li class="sidebar-item active">
                                <a href="<%= request.getContextPath() %>/redirect/home" class="sidebar-link">
                                    <i class="bi bi-grid-fill"></i>
                                    <span>Home</span>
                                </a>
                            </li>
                            <c:if test="${sessionScope.staff.role.roleID == 1}">
                                <li class="sidebar-item has-sub">
                                    <a href="#" class="sidebar-link">
                                        <i class="bi bi-stack"></i>
                                        <span>Apartment</span>
                                    </a>
                                    <ul class="submenu">
                                        <li class="submenu-item">
                                            <a href="manageApartment">Apartment</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="component-badge.html">Floor</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="component-breadcrumb.html">Apartment</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="component-button.html">Utilities</a>
                                        </li>
                                    </ul>
                                </li>


                                <li class="sidebar-item has-sub">
                                    <a href="#" class="sidebar-link">
                                        <i class="bi bi-people-fill"></i>
                                        <span>Staff</span>
                                    </a>
                                    <ul class="submenu">
                                        <li class="submenu-item">
                                            <a href="#">Administrative Staff</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="#">Accountant</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="#">Technical Board</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="#">Service Provider</a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="sidebar-item has-sub">
                                    <a href="#" class="sidebar-link">
                                        <i class="bi bi-collection-fill"></i>
                                        <span>Residents</span>
                                    </a>
                                    <ul class="submenu">
                                        <li class="submenu-item">
                                            <a href="extra-component-avatar.html">List</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-sweetalert.html">Vehicles</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-toastify.html">Notifications</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-rating.html">Complaints</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-divider.html">Divider</a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="sidebar-item has-sub">
                                    <a href="#" class="sidebar-link">
                                        <i class="fa-solid fa-users-gear"></i>
                                        <span>Account</span>
                                    </a>
                                    <ul class="submenu">
                                        <li class="submenu-item">
                                            <a href="manageResident">Resident</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="manageStaff">Staff</a>
                                        </li>
                                    </ul>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.staff.role.roleID == 3}">
                                <li class="sidebar-item ">
                                    <a href="<%= request.getContextPath() %>/InvoicesManager" class="sidebar-link">
                                        <i class="bi bi-receipt-cutoff"></i>
                                        <span>Invoice Management</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.staff.role.roleID == 7}">
                                <li class="sidebar-item ">
                                    <a href="<%= request.getContextPath() %>/ViewInvoice" class="sidebar-link">
                                        <i class="bi bi-receipt-cutoff"></i>
                                        <span>Invoice Management</span>
                                    </a>
                                </li>
                            </c:if>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-grid-1x2-fill"></i>
                                    <span>Feedback</span>
                                </a>
                                <ul class="submenu">
                                    <c:if test="${sessionScope.staff.role.roleID == 1}">
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/manager/feedback">Feedback</a>

                                        </li>
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/manager/request">Request</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.staff.role.roleID == 7}">

                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/owner/feedback">Feedback</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/owner/request">Request</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.staff.role.roleID == 4}">
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/technical/request">Request</a>
                                        </li>
                                    </c:if>
                                    <li class="submenu-item">
                                        <a href="formfeedbackmanager">Feedback Statistics</a>
                                    </li>
                                </ul>
                            </li>
                            <c:if test="${sessionScope.staff.role.roleID == 1}">
                                <li class="sidebar-item has-sub">
                                    <a href="#" class="sidebar-link">
                                        <i class="bi bi-collection-fill"></i>
                                        <span>Statistics</span>
                                    </a>
                                    <ul class="submenu">
                                        <li class="submenu-item">
                                            <a href="extra-component-avatar.html">Parking Service</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-sweetalert.html">Public Electricity Service</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="extra-component-toastify.html">Cleaning Service</a>
                                        </li>
                                    </ul>
                                </li>
                            </c:if>

                            <li class="sidebar-title">Others</li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="fa-solid fa-envelope"></i>
                                    <span>News</span>
                                </a>
                                <ul class="submenu">
                                    <c:if test="${sessionScope.staff.role.roleID == 1}">
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/manager/managernews">Manager News</a>
                                        </li>
                                        <li class="submenu-item">
                                            <a href="<%= request.getContextPath() %>/manager/addnews">Add News</a>
                                        </li>
                                    </c:if>
                                    <li class="submenu-item">
                                        <a href="<%= request.getContextPath() %>/news">News</a>
                                    </li>
                                </ul>
                            </li>


                            <li class="sidebar-item">
                                <a href="<%= request.getContextPath() %>/chat" class="sidebar-link">
                                    <i class="bi bi-chat-dots-fill"></i>
                                    <span>Messages</span>
                                </a>
                            </li>

                            <li class="sidebar-title">Policies</li>

                            <li class="sidebar-item">
                                <a href="https://zuramai.github.io/mazer/docs" class="sidebar-link">
                                    <i class="bi bi-life-preserver"></i>
                                    <span>Building Information</span>
                                </a>
                            </li>

                            <li class="sidebar-item">
                                <a href="https://github.com/zuramai/mazer/blob/main/CONTRIBUTING.md"
                                   class="sidebar-link">
                                    <i class="bi bi-puzzle"></i>
                                    <span>Service Fee Information</span>
                                </a>
                            </li>
                            <li class="sidebar-item">
                                <a href="!#" class="sidebar-link">
                                    <i class="bi bi-puzzle"></i>
                                    <span>Regulations</span>
                                </a>
                            </li>
                            <!-- =================================Login, Logout..==================== -->
                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-person-badge-fill"></i>
                                    <span>Settings</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="profile">Information</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="login">Login</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="logout">Logout</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="forgot-password">Forgot Password</a>
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
                <a href="#" class="burger-btn d-inline-block d-xl-none kiem_button">
                    <i class="bi bi-justify fs-3"></i>
                </a>
                <!--=============================CONTENT HERE=======================-->
                <div class="page-heading">
                    <h3>Số Liệu Thống Kê</h3>
                </div>
                <div class="page-content">
                    <section class="row">
                        <div class="col-12 col-lg-9">
                            <div class="row">
                                <div class="col-6 col-lg-3 col-md-6">
                                    <div class="card">
                                        <div class="card-body px-3 py-4-5 border-cam">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="stats-icon purple">
                                                        <i class="fa-regular fa-building"></i>
                                                    </div>
                                                </div>
                                                <div class="col-md-8">
                                                    <h6 class="text-muted font-semibold">Số căn hộ</h6>
                                                    <h6 class="font-extrabold mb-0">${numberApartment}</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-lg-3 col-md-6">
                                    <div class="card">
                                        <div class="card-body px-3 py-4-5 border-cam">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="stats-icon blue">
                                                        <i class="fa-solid fa-people-roof"></i>
                                                    </div>
                                                </div>
                                                <div class="col-md-8">
                                                    <h6 class="text-muted font-semibold">Số cư dân</h6>
                                                    <h6 class="font-extrabold mb-0">${numberResident}</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-lg-3 col-md-6">
                                    <div class="card">
                                        <div class="card-body px-3 py-4-5 border-cam">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="stats-icon green">
                                                        <i class="fa-solid fa-file-invoice"></i>
                                                    </div>
                                                </div>
                                                <div class="col-md-8">
                                                    <h6 class="text-muted font-semibold">Hóa đơn</h6>
                                                    <h6 class="font-extrabold mb-0">600</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-lg-3 col-md-6">
                                    <div class="card">
                                        <div class="card-body px-3 py-4-5 border-cam">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="stats-icon red">
                                                        <i class="fa-solid fa-handshake-simple"></i>
                                                    </div>
                                                </div>
                                                <div class="col-md-8">
                                                    <h6 class="text-muted font-semibold">Hợp đồng</h6>
                                                    <h6 class="font-extrabold mb-0">112</h6>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="card border-cam">
                                        <div class="card-header">
                                            <h4>Revenue</h4>
                                        </div>
                                        <div class="card-body">
                                            <div id="chart-profile-visit" ></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12 col-xl-4">
                                    <div class="card border-cam">
                                        <div class="card-header">
                                            <h4>Số Lượng Dân Cư</h4>
                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="d-flex align-items-center">
                                                        <svg class="bi text-primary" width="32" height="32" fill="blue"
                                                             style="width:10px">
                                                        <use
                                                            xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#circle-fill" />
                                                        </svg>
                                                        <h5 class="mb-0 ms-3">Chí Thanh</h5>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <h5 class="mb-0">862</h5>
                                                </div>
                                                <div class="col-12">
                                                    <div id="chart-europe"></div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="d-flex align-items-center">
                                                        <svg class="bi text-success" width="32" height="32" fill="blue"
                                                             style="width:10px">
                                                        <use
                                                            xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#circle-fill" />
                                                        </svg>
                                                        <h5 class="mb-0 ms-3">Mường Thanh</h5>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <h5 class="mb-0">375</h5>
                                                </div>
                                                <div class="col-12">
                                                    <div id="chart-america"></div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-6">
                                                    <div class="d-flex align-items-center">
                                                        <svg class="bi text-danger" width="32" height="32" fill="blue"
                                                             style="width:10px">
                                                        <use
                                                            xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#circle-fill" />
                                                        </svg>
                                                        <h5 class="mb-0 ms-3">Hải Phong</h5>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <h5 class="mb-0">1025</h5>
                                                </div>
                                                <div class="col-12">
                                                    <div id="chart-indonesia"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-xl-8">
                                    <div class="card border-cam">
                                        <div class="card-header">
                                            <h4>Latest Comments</h4>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-hover table-lg">
                                                    <thead>
                                                        <tr>
                                                            <th>Name</th>
                                                            <th>Comment</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td class="col-3">
                                                                <div class="d-flex align-items-center">
                                                                    <div class="avatar avatar-md">
                                                                        <img src="assets/images/faces/5.jpg">
                                                                    </div>
                                                                    <p class="font-bold ms-3 mb-0">Si Cantik</p>
                                                                </div>
                                                            </td>
                                                            <td class="col-auto">
                                                                <p class=" mb-0">Congratulations on your graduation!</p>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="col-3">
                                                                <div class="d-flex align-items-center">
                                                                    <div class="avatar avatar-md">
                                                                        <img src="assets/images/faces/2.jpg">
                                                                    </div>
                                                                    <p class="font-bold ms-3 mb-0">Si Ganteng</p>
                                                                </div>
                                                            </td>
                                                            <td class="col-auto">
                                                                <p class=" mb-0">Wow amazing design! Can you make another
                                                                    tutorial for
                                                                    this design?</p>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-lg-3">
                            <div class="card">
                                <div class="card-body py-4 px-5 border-cam">
                                    <div class="d-flex align-items-center">
                                        <div class="avatar avatar-xl">
                                            <img src="<%= request.getContextPath() %>/${staff.image.imageURL}" alt="${staff.fullName}">
                                        </div>
                                        <div class="ms-3 name">
                                            <h5 class="font-bold">Chủ Căn Hộ</h5>
                                            <h6 class="text-muted mb-0">${staff.fullName}</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card border-cam">
                                <div class="card-header">
                                    <h4>Tin nhắn gần đây</h4>
                                </div>
                                <div class="card-content pb-4">
                                    <c:forEach var="user" items="${list}">
                                        <div class="recent-message d-flex px-4 py-3">
                                            <div class="avatar avatar-lg">
                                                <img src="<%= request.getContextPath() %>/${user.image.imageURL}">
                                            </div>
                                            <div class="name ms-4">
                                                <h5 class="mb-1">${user.fullName}</h5>
                                                <h6 class="text-muted mb-0">${user.email}</h6>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <div class="px-4">
                                        <button onclick="window.location.href = '<%= request.getContextPath() %>/chat'" 
                                                class='btn btn-block btn-xl btn-light-primary font-bold mt-3'>
                                            Start Conversation
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="card border-cam">
                                <div class="card-header">
                                    <h4>Số căn hộ đã thuê</h4>
                                </div>
                                <div class="card-body">
                                    <div id="chart-visitors-profile"  data-occupied="${numberApartmentOccupied}"
                                         data-available="${numberApartmentAvailable}"></div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
                <!--==============================END================================-->

            </div>
        </div>
        <script>
           var revenueData = ${listRevenue};
        </script>
        <!-- <script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script> -->
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>
