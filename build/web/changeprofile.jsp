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
        <title>Trang chủ</title>

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
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/changeprofile.css" />
    </head>

    <body>
        <div id="app">
            <nav class="app-header navbar navbar-expand bg-body">
                <div class="container-fluid">
                    <!-- Navbar Links -->
                    <ul class="navbar-nav kiem_can_trai">
                        <li class="nav-item d-none d-md-block">
                            <c:choose>
                                <c:when test="${sessionScope.staff.role.roleID == 1}">
                                    <a href="${pageContext.request.contextPath}/manager/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.staff.role.roleID == 2}">
                                    <a href="${pageContext.request.contextPath}/administrative/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.staff.role.roleID == 3}">
                                    <a href="${pageContext.request.contextPath}/accountant/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.staff.role.roleID == 4}">
                                    <a href="${pageContext.request.contextPath}/technical/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.staff.role.roleID == 5}">
                                    <a href="${pageContext.request.contextPath}/service/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.resident.role.roleID == 6}">
                                    <a href="${pageContext.request.contextPath}/tenant/home" class="nav-link">Home</a>
                                </c:when>
                                <c:when test="${sessionScope.resident.role.roleID == 7}">
                                    <a href="${pageContext.request.contextPath}/owner/home" class="nav-link">Home</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/error-403" class="nav-link">Home</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li class="nav-item d-none d-md-block">
                            <a href="#" class="nav-link">Contact</a>
                        </li>
                    </ul>

                    <!-- User and Notification Dropdowns -->
                    <ul class="navbar-nav ms-auto">
                        <!-- User Menu -->
                        <c:set var="resident" value="${sessionScope.resident}" />
                        <li class="nav-item dropdown user-menu">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img src="<%= request.getContextPath() %>/${resident.image.imageURL}" class="user-image rounded-circle shadow"
                                     alt="User Image" />
                                <span class="d-none d-md-inline">${resident.fullName}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="<%= request.getContextPath() %>/${resident.image.imageURL}" class="rounded-circle shadow"
                                         alt="User Image" />
                                    <p>
                                        ${resident.fullName} - Web Developer
                                        <small>Member since Nov. 2024</small>
                                    </p>
                                </li>
                                <li class="user-footer d-flex justify-content-between">
                                    <a href="profile" class="btn btn-default btn-flat">Profile</a>
                                    <a href="#" class="btn btn-default btn-flat">Setting</a>
                                    <a href="logout" class="btn btn-default btn-flat">Logout</a>
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

                            <li class="sidebar-item active">
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

                            <li class="sidebar-item has-sub">
                                <a href="#" class="sidebar-link">
                                    <i class="fa-solid fa-envelope"></i>
                                    <span>News</span>
                                </a>
                                <ul class="submenu">
                                    <li class="submenu-item">
                                        <a href="addnews">Add News</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="news">News</a>
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
                <div class="container profile-container mt-5">
                    <h2 class="text-center mb-4">Chỉnh sửa thông tin cá nhân</h2>
                    <c:set var="staff" value="${sessionScope.staff}"/>
                    <c:set var="resident" value="${sessionScope.resident}"/>
                    <form action="update-profile" method="POST" enctype="multipart/form-data">
                        <div class="text-center mb-3">
                            <img id="preview-img" 
                                 src="<%= request.getContextPath() %>/${not empty staff ? staff.image.imageURL : (not empty resident ? resident.image.imageURL : 'Guest')}" 
                                 alt="Ảnh cá nhân" class="profile-img rounded-circle" width="150" height="150">

                            <input type="file" class="form-control mt-2" name="imgURL" id="upload-photo" accept="image/*">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fullName" class="form-label">Họ và tên</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" value="${not empty staff ? staff.fullName : (not empty resident ? resident.fullName : 'Guest')}">
                                <input type="hidden" class="form-control" id="userID" name="userID" value="${not empty staff ? staff.staffId : (not empty resident ? resident.residentId : 'Guest')}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" disabled name="email" value="${not empty staff ? staff.email : (not empty resident ? resident.email : 'N/A')}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label">Số điện thoại</label>
                                <input type="text" class="form-control" id="phone" name="phoneNumber" value="${not empty staff ? staff.phoneNumber : (not empty resident ? resident.phoneNumber : 'N/A')}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="address" class="form-label">Địa chỉ</label>
                                <input type="text" class="form-control" id="address" name="address" value="Hà Nội">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="dob" class="form-label">Ngày sinh</label>
                                <input type="date" class="form-control" id="dob" name="dob" value="${not empty staff ? staff.dob : (not empty resident ? resident.dob : 'N/A')}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="sex" class="form-label">Giới tính</label>
                                <select class="form-select" id="sex" name="sex">
                                    <option value="Male" ${((not empty staff ? staff.sex : resident.sex) == 'Male') ? 'selected' : ''}>Nam</option>
                                    <option value="Female" ${((not empty staff ? staff.sex : resident.sex) == 'Female') ? 'selected' : ''}>Nữ</option>
                                    <option value="Other" ${((not empty staff ? staff.sex : resident.sex) == 'Other') ? 'selected' : ''}>Khác</option>
                                </select>
                            </div>
                            <div class="col-md-12 mb-3">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <input type="password" class="form-control" id="password" value="*******" disabled>
                            </div>
                        </div>
                        <c:if test="${not empty errors}">
                            <div class="alert alert-danger">
                                <strong>An error has occurred:</strong>
                                <ul class="mb-0">
                                    <c:forEach var="error" items="${errors}">
                                        <li>${error}</li>
                                        </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                            <button type="reset" class="btn btn-secondary">Hủy</button>
                        </div>
                    </form>
                </div>

                <style>
                    .alert-danger {
                        padding: 10px;
                        border-radius: 5px;
                        font-size: 14px;
                    }
                    .alert-danger ul {
                        margin-bottom: 0;
                        padding-left: 20px;
                    }

                </style>
                <script>
                    document.querySelectorAll('input, select').forEach(function (input) {
                        input.addEventListener('focus', function () {
                            const errorAlert = document.querySelector('.alert-danger');
                            if (errorAlert) {
                                errorAlert.style.display = 'none';
                            }
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
        <script>
            document.getElementById("upload-photo").addEventListener("change", function (event) {
                const file = event.target.files[0];
                if (file) {
                    if (!file.type.startsWith("image/")) {
                        alert("Chỉ được chọn file ảnh!");
                        event.target.value = "";
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById("preview-img").src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            });
        </script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>

</html>
