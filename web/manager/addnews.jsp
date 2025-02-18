<%-- 
    Document   : addnews.jsp
    Created on : Feb 10, 2025, 9:25:09 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Add News</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/pages/index.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />-->
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>-->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />

        <style>
            body {
                background-color: #f8f9fa;
            }
            .border-orange {
                border-color: orange;
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
                                <img src="<%= request.getContextPath() %>/${staff.image.imageURL}" class="user-image rounded-circle shadow"
                                     alt="User Image" />
                                <span class="d-none d-md-inline">${staff.fullName}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="<%= request.getContextPath() %>/${staff.image.imageURL}" class="rounded-circle shadow"
                                         alt="User Image" />
                                    <p>
                                        ${staff.fullName} - Web Developer
                                        <small>Member since Nov. 2024</small>
                                    </p>
                                </li>
                                <li class="user-footer d-flex justify-content-between">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
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
                                            <img src="<%= request.getContextPath() %>/assets/images/faces/nguyenkiem.jpg"
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
                                            <img src="<%= request.getContextPath() %>/assets/images/faces/nguyenkiem.jpg"
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
                                            <img src="<%= request.getContextPath() %>/assets/images/faces/nguyenkiem.jpg"
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

                            <li class="sidebar-item">
                                <c:choose>
                                    <c:when test="${sessionScope.staff.role.roleID == 1}">
                                        <a href="${pageContext.request.contextPath}/manager/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.staff.role.roleID == 2}">
                                        <a href="${pageContext.request.contextPath}/administrative/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.staff.role.roleID == 3}">
                                        <a href="${pageContext.request.contextPath}/accountant/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.staff.role.roleID == 4}">
                                        <a href="${pageContext.request.contextPath}/technical/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.staff.role.roleID == 5}">
                                        <a href="${pageContext.request.contextPath}/service/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.resident.role.roleID == 6}">
                                        <a href="${pageContext.request.contextPath}/tenant/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:when test="${sessionScope.resident.role.roleID == 7}">
                                        <a href="${pageContext.request.contextPath}/owner/home" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/error-403" class="sidebar-link"><i class="bi bi-grid-fill"></i>
                                            <span>Home</span></a>
                                        </c:otherwise>
                                    </c:choose>
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
                                        <a href="#!" style="text-decoration: underline;">Add News</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="<%= request.getContextPath() %>/news" style="text-decoration: none;">News</a>
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
                <div class="container mt-5">
                    <div class="text-center mb-4">
                        <h1 class="text-primary" style="font-family: 'Playball', cursive; font-size: 36px">
                            <i class="fas fa-plus-circle"></i> Tạo mới bài viết
                        </h1>
                        <hr class="w-25 mx-auto border-3 border-warning" />
                    </div>

                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-md-10">
                            <form id="Create" action="addnews" method="POST" class="p-4 border rounded shadow bg-white" enctype="multipart/form-data">
                                <input type="hidden" value="${sessionScope.staff.staffId}" name="staffId"/>
                                <div class="alert alert-danger mb-3 d-none">Có lỗi xảy ra!</div>

                                <div class="mb-3">
                                    <label for="title" class="form-label"><i class="fas fa-heading"></i> Tên bài viết</label>
                                    <input type="text" name="title" id="title" class="form-control border-orange" />
                                </div>

                                <div class="mb-3">
                                    <label for="category" class="form-label"><i class="fas fa-list-alt"></i> Danh mục</label>
                                    <select id="category" class="form-select border-orange">
                                        <option value="0" selected>Chọn danh mục</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="image" class="form-label">Chọn Ảnh</label>
                                    <div class="input-group">
                                        <input type="file" name="imageURL" class="form-control" id="image">
                                    </div>
                                </div>


                                <div class="mb-3">
                                    <label for="detail" class="form-label"><i class="fas fa-file-alt"></i> Chi tiết</label>
                                    <textarea id="detail" name="description" class="form-control"></textarea>
                                </div>

                                <div class="text-center">
                                    <button type="submit" class="btn btn-success px-4">
                                        <i class="fas fa-save"></i> Tạo mới
                                    </button>
                                </div>
                            </form>

                            <div class="text-center mt-3">
                                <a href="#" id="Index" class="btn btn-outline-danger"><i class="fas fa-arrow-left"></i> Quay lại</a>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    $(document).ready(function () {
                        $('#detail').summernote({
                            height: 300,
                            tabsize: 2,
                            placeholder: "Nhập nội dung bài viết..."
                        });
                    });
                </script>
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
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>
