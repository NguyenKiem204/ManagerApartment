<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Staff" %>  
<%@ page import="model.Resident" %> 
<%@ page import="java.time.LocalDate" %>  
<%@ page import="java.time.format.DateTimeFormatter" %>
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
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/profile.css" />
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
                                        <a href="<%= request.getContextPath() %>/profile">Information</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="<%= request.getContextPath() %>/login">Login</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="<%= request.getContextPath() %>/logout">Logout</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="<%= request.getContextPath() %>/forgot-password">Forgot Password</a>
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
                                        <img src="<%= request.getContextPath() %>${not empty staff ? staff.image.imageURL : (not empty resident ? resident.image.imageURL : 'Guest')}"
                                             alt="Ảnh cá nhân" class="img-fluid rounded-circle profile-img" />
                                    </div>
                                    <div class="col-md-8 d-flex flex-column justify-content-center">
                                        <!-- Hiển thị tên, ưu tiên staff trước, nếu không có thì lấy resident -->
                                        <h2 class="user-name">
                                            ${not empty staff ? staff.fullName : (not empty resident ? resident.fullName : 'Guest')}
                                        </h2>

                                        <!-- Hiển thị vai trò nếu có -->
                                        <p class="user-role"> ${not empty staff ? staff.role.roleName : (not empty resident ? resident.role.roleName : 'Guest')}</p>
                                       
                                        <!-- Hiển thị thông tin -->
                                        <div class="profile-info">
                                            <p><strong>Email:</strong> ${not empty staff ? staff.email : (not empty resident ? resident.email : 'N/A')}</p>
                                            <p><strong>Phone:</strong> ${not empty staff ? staff.phoneNumber : (not empty resident ? resident.phoneNumber : 'N/A')}</p>
                                            <p><strong>Address:</strong> Hà Nội</p>
                                            <p><strong>Status: </strong>Active</p>
                                            <%  
                                              LocalDate dob = null;
                                              Resident resident = (Resident) session.getAttribute("resident");
                                              if (resident != null) {  
                                                  dob = resident.getDob();
                                              } 
                                              String dobString = (dob != null) ? dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "N/A";   
                                            %>   

                                            <p><strong>Date Of Birth:</strong> 
                                                <%= dobString %>  
                                            </p>
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
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>