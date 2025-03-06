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
            .news-item{
                border: 2px solid #E64A19;
            }
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 20px 0;
            }
            .pagination a,
            .pagination strong {
                text-decoration: none;
                color: #007bff;
                padding: 8px 12px;
                margin: 0 5px;
                border: 1px solid #007bff;
                border-radius: 4px;
                transition: background-color 0.3s, color 0.3s;
            }
            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }

            .pagination strong {
                background-color: #007bff;
                color: white;
                border: none;
            }
            .card {
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .form-label {
                font-weight: 500;
            }

            .btn-primary {
                background-color: #435ebe;
                border-color: #435ebe;
            }

            .btn-primary:hover {
                background-color: #364b98;
                border-color: #364b98;
            }

            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #545b62;
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
                        <c:set var="staff" value="${sessionScope.staff}" />
                        <c:set var="resident" value="${sessionScope.resident}" />
                        <li class="nav-item dropdown user-menu">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img src="<%= request.getContextPath() %>/${not empty staff ? staff.image.imageURL : (not empty resident ? resident.image.imageURL : 'Guest')}" class="user-image rounded-circle shadow" alt="User Image" />
                                <span class="d-none d-md-inline">${not empty staff ? staff.fullName : (not empty resident ? resident.fullName : 'Guest')}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="<%= request.getContextPath() %>/${not empty staff ? staff.image.imageURL : (not empty resident ? resident.image.imageURL : 'Guest')}" class="rounded-circle shadow" alt="User Image" />
                                    <p>
                                        ${not empty staff ? staff.fullName : (not empty resident ? resident.fullName : 'Guest')} - Web Developer
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
                                <a href="<%= request.getContextPath() %>/home" class="sidebar-link">
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

                            <c:choose>
                                <c:when test="${sessionScope.staff.role.roleID == 1}">
                                    <!-- Nếu là role 1, hiển thị submenu như cũ -->
                                    <li class="sidebar-item has-sub active">
                                        <a href="#" class="sidebar-link">
                                            <i class="fa-solid fa-envelope"></i>
                                            <span>News</span>
                                        </a>
                                        <ul class="submenu active">
                                            <li class="submenu-item">
                                                <a href="manager/addnews">Add News</a>
                                            </li>
                                            <li class="submenu-item">
                                                <a href="news" style="text-decoration: underline;">News</a>
                                            </li>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <!-- Nếu không phải role 1, chỉ hiển thị liên kết News -->
                                    <li class="sidebar-item active">
                                        <a href="<%= request.getContextPath() %>/news" class="sidebar-link">
                                            <i class="fa-solid fa-envelope"></i>
                                            <span>News</span>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>


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
                                        <a href="<%= request.getContextPath() %>/logout">Logout</a>
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
                <div class="card">
                    <div class="card-header">
                        <div class="text-center mb-4">
                            <h1 class="text-d" style="font-family: 'Playball', cursive; font-size: 36px">
                                <i class="fa-solid fa-fire"></i>Breaking News
                            </h1>
                            <hr class="w-25 mx-auto border-3 border-warning" />
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="container my-5">

                            <!-- News Section -->
                            <div class="row">
                                <c:forEach var="news" items="${newsList}">
                                    <div class="col-md-4 news-card">
                                        <div class="card news-item">
                                            <img src="<%= request.getContextPath() %>/${news.image.imageURL}" class="card-img-top img-fluid" alt="News Image">
                                            <div class="card-body">
                                                <h5 class="card-title truncated-text" style="height: 65px">${news.title}</h5>
                                                <p class="text-muted">Ngày đăng: ${news.formattedDate}</p>
                                                <a href="news-detail?newsId=${news.newsID}" class="btn btn-primary mt-1">Đọc thêm</a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <!-- Pagination -->
                        <div class="pagination">  
                            <c:set var="currentPage" value="${currentPage}" />  
                            <c:set var="totalPages" value="${totalPages}" />  
                            <c:set var="prevPage" value="${currentPage - 1}" />  
                            <c:set var="nextPage" value="${currentPage + 1}" />  

                            <c:url var="baseUrl" value="news">
                                <c:param name="searchTitle" value="${param.searchTitle}" />
                                <c:param name="startDate" value="${param.startDate}" />
                                <c:param name="endDate" value="${param.endDate}" />
                            </c:url>

                            <c:if test="${currentPage > 1}">  
                                <a href="${baseUrl}&page=1">First Page</a>  
                                <a href="${baseUrl}&page=${prevPage}">Previous</a>  
                            </c:if>  

                            <c:set var="startPage" value="${currentPage - 1}" />  
                            <c:set var="endPage" value="${currentPage + 1}" />  

                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1" />
                            </c:if>

                            <c:if test="${endPage > totalPages}">
                                <c:set var="endPage" value="${totalPages}" />
                            </c:if>

                            <c:forEach var="i" begin="${startPage}" end="${endPage}">  
                                <c:choose>  
                                    <c:when test="${i == currentPage}">  
                                        <strong>${i}</strong>  
                                    </c:when>  
                                    <c:otherwise>  
                                        <a href="${baseUrl}&page=${i}">${i}</a>  
                                    </c:otherwise>  
                                </c:choose>  
                            </c:forEach>  

                            <c:if test="${currentPage < totalPages}">  
                                <a href="${baseUrl}&page=${nextPage}">Next</a>  
                                <a href="${baseUrl}&page=${totalPages}">Last Page</a>  
                            </c:if>  
                        </div>
                    </div>

                </div>
                <!--==============================END================================-->

                <footer>
                    <div class="footer clearfix mb-0 text-muted">
                        <div class="float-start"> 
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