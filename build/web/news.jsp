<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>News</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="assets/css/pages/index.css" />
        <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/menu.css" />
        <style>
            .news-card {
                margin-bottom: 20px;
            }
            .truncated-text {
                display: -webkit-box;
                line-clamp: 2;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .card-img-top {
                height: 200px; 
                object-fit: cover;
            }
        </style>
    </head>

    <body>
        <div id="app">
            <nav class="app-header navbar navbar-expand bg-body">
                <div class="container-fluid">
                    <!-- Navbar Links -->
                    <ul class="navbar-nav kiem_can_trai">
                        <li class="nav-item d-none d-md-block">
                            <a href="home" class="nav-link">Home</a>
                        </li>
                        <li class="nav-item d-none d-md-block">
                            <a href="#" class="nav-link">Contact</a>
                        </li>
                    </ul>

                    <!-- User and Notification Dropdowns -->
                    <ul class="navbar-nav ms-auto">
                        <!-- User Menu -->
                        <c:set var="staff" value="${sessionScope.staff}" />
                        <li class="nav-item dropdown user-menu">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img src="${staff.imageURL}" class="user-image rounded-circle shadow"
                                     alt="User Image" />
                                <span class="d-none d-md-inline">${staff.fullName}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="${staff.imageURL}" class="rounded-circle shadow"
                                         alt="User Image" />
                                    <p>
                                        ${staff.fullName} - Web Developer
                                        <small>Member since Nov. 2024</small>
                                    </p>
                                </li>
                                <li class="user-footer d-flex justify-content-between">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                    <a href="#" class="btn btn-default btn-flat">Setting</a>
                                    <a href="#" class="btn btn-default btn-flat">Logout</a>
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
                                <a href="menumanager.jsp"><img src="assets/images/logo/logo1.png"
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

                            <li class="sidebar-item">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-grid-fill"></i>
                                    <span>Home</span>
                                </a>
                            </li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-stack"></i>
                                    <span>Apartment</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="component-alert.html">Building</a>
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
                                    <i class="bi bi-grid-1x2-fill"></i>
                                    <span>Feedback</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="layout-default.html">Send Notification</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-vertical-1-column.html">Residents</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-vertical-navbar.html">Accounting Department</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-horizontal.html">Security Department</a>
                                    </li>
                                </ul>
                            </li>

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

                            <li class="sidebar-title">Others</li>

                            <li class="sidebar-item has-sub active">
                                <a href="#" class="sidebar-link">
                                    <i class="fa-solid fa-envelope"></i>
                                    <span>News</span>
                                </a>
                                <ul class="submenu active">
                                    <li class="submenu-item">
                                        <a href="addnews">Add News</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="news" style="text-decoration: underline;">News</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="sidebar-item">
                                <a href="application-chat.html" class="sidebar-link">
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
                                        <a href="auth-login.html">Information</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="auth-login.html">Login</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="auth-login.html">Logout</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="forget-password.jsp">Forgot Password</a>
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
                <div class="text-center mb-4">
                    <h1 class="text-d" style="font-family: 'Playball', cursive; font-size: 36px">
                        <i class="fa-solid fa-fire"></i>Breaking News
                    </h1>
                    <hr class="w-25 mx-auto border-3 border-warning" />
                </div>
                <div class="container my-5">

                    <!-- News Section -->
                    <div class="row">
                        <c:forEach var="news" items="${newsList}">
                            <div class="col-md-4 news-card">
                                <div class="card">
                                    <img src="${news.imageURL}" class="card-img-top img-fluid" alt="News Image">
                                    <div class="card-body">
                                        <h5 class="card-title truncated-text" style="height: 65px">${news.title}</h5>
                                        <p class="text-muted">Ngày đăng: ${news.sentDate}</p>
                                        <a href="news-detail?newsId=${news.newsID}" class="btn btn-primary mt-1">Đọc thêm</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Pagination -->
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
                <!--==============================END================================-->

                <footer>
                    <div class="footer clearfix mb-0 text-muted">
                        <div class="float-start">
                            <p>2025 &copy; Kiemm</p>
                        </div>
                        <div class="float-end">
                            <p>
                                Crafted with
                                <span class="text-danger"><i class="bi bi-heart"></i></span> by
                                <a href="http://ahmadsaugi.com">NguyenKiem</a>
                            </p>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <!-- <script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script> -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>

</html>