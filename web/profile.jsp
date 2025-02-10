<%-- 
    Document   : profile.jsp
    Created on : Feb 8, 2025, 3:12:35 PM
    Author     : nkiem
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : menu.jsp
    Created on : Feb 8, 2025, 2:54:18 PM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="assets/css/pages/index.css">
        <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="assets/css/menu.css" />
        <link rel="stylesheet" href="assets/css/profile.css" />
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
                        <c:set var="staff" value="${sessionScope.staff}"/>
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
                                            <strong>Đình Tùng</strong> đã nhắc bạn trong một bình luận
                                            trong nhóm Spring-boot Java web Việt Nam. đã nhắc bạn
                                            trong một bình luận trong nhóm Spring-boot Java web Việt
                                            Nam đã nhắc bạn trong một bình luận trong nhóm Spring-boot
                                            Java web Việt Nam
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
                                            <strong>Đình Tùng</strong> đã nhắc bạn trong một bình luận
                                            trong nhóm Spring-boot Java web Việt Nam.
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
                                            <strong>Đình Tùng</strong> đã nhắc bạn trong một bình luận
                                            trong nhóm Spring-boot Java web Việt Nam.
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
                                <a href="index.html"><img src="assets/images/logo/logo1.png" alt="Logo" /></a>
                            </div>
                            <div class="toggler">
                                <a href="#" class="sidebar-hide d-xl-none d-block"><i class="bi bi-x bi-middle"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="sidebar-menu">
                        <ul class="menu">
                            <li class="sidebar-title">Menu</li>

                            <li class="sidebar-item active">
                                <a href="index.html" class="sidebar-link">
                                    <i class="bi bi-grid-fill"></i>
                                    <span>Trang Chủ</span>
                                </a>
                            </li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-stack"></i>
                                    <span>Chung Cư</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="component-alert.html">Tòa Nhà</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="component-badge.html">Tầng</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="component-breadcrumb.html">Căn Hộ</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="component-button.html">Tiện Ích</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-collection-fill"></i>
                                    <span>Cư Dân</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="extra-component-avatar.html">Danh Sách</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-sweetalert.html">Phương Tiện</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-toastify.html">Thông Báo</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-rating.html">Khiếu Nại</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-divider.html">Divider</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-grid-1x2-fill"></i>
                                    <span>Phản hồi</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="layout-default.html">Gửi thông báo</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-vertical-1-column.html">Cư dân</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-vertical-navbar.html">Ban kế toán</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="layout-horizontal.html">Ban bảo vệ</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-collection-fill"></i>
                                    <span>Thống kê</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="extra-component-avatar.html">Dịch vụ lốt xe</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-sweetalert.html">Dịch vụ điện công cộng</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="extra-component-toastify.html">Dịch vụ vệ sinh</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="sidebar-title">Khác</li>

                            <li class="sidebar-item">
                                <a href="application-email.html" class="sidebar-link">
                                    <i class="bi bi-envelope-fill"></i>
                                    <span>Blogs</span>
                                </a>
                            </li>

                            <li class="sidebar-item">
                                <a href="application-chat.html" class="sidebar-link">
                                    <i class="bi bi-chat-dots-fill"></i>
                                    <span>Nhắn tin</span>
                                </a>
                            </li>

                            <li class="sidebar-title">Chính sách</li>

                            <li class="sidebar-item">
                                <a href="https://zuramai.github.io/mazer/docs" class="sidebar-link">
                                    <i class="bi bi-life-preserver"></i>
                                    <span>Thông tin tòa nhà</span>
                                </a>
                            </li>

                            <li class="sidebar-item">
                                <a href="https://github.com/zuramai/mazer/blob/main/CONTRIBUTING.md" class="sidebar-link">
                                    <i class="bi bi-puzzle"></i>
                                    <span>Thông tin phí dịch vụ</span>
                                </a>
                            </li>
                            <li class="sidebar-item">
                                <a href="!#" class="sidebar-link">
                                    <i class="bi bi-puzzle"></i>
                                    <span>Quy định</span>
                                </a>
                            </li>
                            <!-- =================================đăng nhập, log out..==================== -->
                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="bi bi-person-badge-fill"></i>
                                    <span>Cài đặt</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="auth-login.html">Thông tin</a>
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
                <div class="container-fluid p-5">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <div class="profile-card">
                                <div class="row">
                                    <c:set var="staff" value="${sessionScope.staff}"/>
                                        <c:set var="resident" value="${sessionScope.resident}"/>
                                    <div class="col-md-4 text-center d-flex justify-content-center align-items-center">
                                        <img src="${not empty staff ? staff.imageURL : (not empty resident ? resident.imageURL : 'Guest')}"
                                             alt="Ảnh cá nhân" class="img-fluid rounded-circle profile-img" />
                                    </div>
                                    <div class="col-md-8 d-flex flex-column justify-content-center">
                                        <!-- Hiển thị tên, ưu tiên staff trước, nếu không có thì lấy resident -->
                                        <h2 class="user-name">
                                            ${not empty staff ? staff.fullName : (not empty resident ? resident.fullName : 'Guest')}
                                        </h2>

                                        <!-- Hiển thị vai trò nếu có -->
                                        <p class="user-role"> ${not empty staff ? staff.roleName : (not empty resident ? resident.roleName : 'Guest')}</p>
                                       
                                        <!-- Hiển thị thông tin -->
                                        <div class="profile-info">
                                            <p><strong>Email:</strong> ${not empty staff ? staff.email : (not empty resident ? resident.email : 'N/A')}</p>
                                            <p><strong>Phone:</strong> ${not empty staff ? staff.phoneNumber : (not empty resident ? resident.phoneNumber : 'N/A')}</p>
                                            <p><strong>Address:</strong> Hà Nội</p>
                                            <p><strong>Status: </strong>Active</p>
                                            <p><strong>Date Of Birth:</strong> ${not empty staff ? staff.dob : (not empty resident ? resident.dob : 'N/A')}</p>
                                            <p><strong>Sex:</strong> ${not empty staff ? staff.sex : (not empty resident ? resident.sex : 'N/A')}</p>
                                        </div>
                                    </div>

                                    <button class="btn edit-button mt-4" onclick="window.location.href = 'changeprofile'">
                                        Chỉnh sửa thông tin
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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

