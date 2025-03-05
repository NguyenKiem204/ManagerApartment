<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Trang chủ</title>

        <link rel="preconnect" href="<%= request.getContextPath() %>/https://fonts.gstatic.com" />
        <link href="<%= request.getContextPath() %>/https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/iconly/bold.css" />

         <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> 
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/pages/index.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #ffe6cc;
            }

            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px;
            }

            h1 {
                color: #e65c00;
                margin-bottom: 20px;
            }

            .table-container {
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                width: 100%;
            }

            table {
                border-collapse: collapse;
                width: 100%;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #ff944d;
                color: white;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            .actions {
                display: flex;
                gap: 10px;
            }

            .actions button {
                background-color: #ff944d;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .actions button:hover {
                background-color: #e65c00;
            }

            .sidebar {
                position: fixed;
                width: 250px;
                height: 100%;
                background-color: #ff944d;
                padding: 20px;
                color: white;
            }

            .sidebar ul {
                list-style-type: none;
                padding: 0;
            }

            .sidebar ul li {
                margin: 20px 0;
            }

            .sidebar ul li a {
                color: white;
                text-decoration: none;
                font-size: 18px;
            }

            .sidebar ul li a:hover {
                text-decoration: underline;
            }

            .content {
                margin-left: 270px;
                padding: 20px;
            }
            .message {
                color: green;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .add-resident-button {
                display: inline-block;
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 8px;
                font-size: 16px;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .add-resident-button:hover {
                background-color: #45a049;
            }
            .switch {
                position: relative;
                display: inline-block;
                width: 34px;
                height: 20px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 34px;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 14px;
                width: 14px;
                left: 3px;
                bottom: 3px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
            }

            input:checked + .slider {
                background-color: #4CAF50;
            }

            input:checked + .slider:before {
                transform: translateX(14px);
            }
             /* Modal background */
        .modal {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* Làm mờ nền */
    padding-left: 600px;
    padding-top: 30px;
}

.modal-content {
    width: 40%;  /* Chiếm 40% màn hình */
    max-width: 400px; /* Giới hạn tối đa */
    max-height: 700px;

    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    position: relative;
}


            .modal-title {
                text-align: center;
                color: #333;
            }

            .modal-label {
                display: block;
                margin-top: 5px;
            }

            .modal-input,
            .modal-select,
            .modal-button {
                width: 100%;
                padding: 10px;
                margin-top: 0px;
                margin-bottom: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .modal-button {
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 14px;
            }

            .modal-button:hover {
                background-color: #45a049;
            }
            
            .modal-close {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 20px;
                cursor: pointer;
            }

            .modal-close:hover {
                color: red;
            }

            .modal-back-button {
                display: block;
                text-align: center;
                margin-top: 15px;
                text-decoration: none;
                color: #333;
                font-size: 14px;
            }

            .modal-back-button:hover {
                text-decoration: underline;
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
                                <img src="${staff.image.imageURL}" class="user-image rounded-circle shadow"
                                     alt="User Image" />
                                <span class="d-none d-md-inline">${staff.fullName}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                                <li class="user-header text-bg-primary img-drop">
                                    <img src="${staff.image.imageURL}" class="rounded-circle shadow"
                                         alt="User Image" />
                                    <p>
                                        ${staff.fullName} - Web Developer
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

                            <li class="sidebar-item active">
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
                <div>
                    <div>
                        <h1>Staff List</h1>
                        <%-- Hiển thị thông báo nếu có --%>
                        <c:if test="${not empty mess}">
                            <div class="message">${mess}</div>
                        </c:if>
                        <div style="text-align: center; margin-bottom: 20px;">
    <button class="add-resident-button btn btn-success" id="openInsertModal">Add new staff account</button>
</div>

<!-- Modal Form -->
<div id="insertStaffModal" class="modal">
    <div class="modal-content">
        <span class="modal-close">&times;</span>
        <h2 class="modal-title">Add Staff</h2>
        <form id="insertStaffForm">
            <label for="fullName" class="modal-label">Full Name:</label>
            <input type="text" id="fullName" name="fullName" class="modal-input" required>

            <label for="phoneNumber" class="modal-label">Phone Number: </label>
            <input type="text" id="phoneNumber" name="phoneNumber" class="modal-input" required maxlength="10" pattern="\d{10}">

            <label for="cccd" class="modal-label">CCCD:</label>
            <input type="text" id="cccd" name="cccd" class="modal-input" required maxlength="12" pattern="\d{12}">

            <label for="email" class="modal-label">Email:</label>
            <input type="email" id="email" name="email" class="modal-input" required>

            <label for="dob" class="modal-label">Birth Date:</label>
            <input type="date" id="dob" name="dob" class="modal-input" required>

            <label for="sex" class="modal-label">Gender:</label>
            <select id="sex" name="sex" class="modal-select" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <label for="roleId" class="modal-label">Role:</label>
            <select id="roleId" name="roleId" class="modal-select" required>
                <option value="1">Manager</option>
                <option value="2">Administrative Staff</option>
                <option value="3">Accountant</option>
                <option value="4">Technical Board</option>
                <option value="5">Service Provider</option>
            </select>

            <button type="button" id="submitBtn" class="modal-button">Add</button>
        </form>
        <div id="message"></div>
    </div>
</div>

                        <div class="row mb-3">

                            <div class="col-md-5 d-flex gap-2">
                                <form action="manageStaff" method="get" class="d-flex gap-2 flex-grow-1">
                                    <select name="sex" id="sexFilter" class="form-select" style="width: 100%;">
                                        <option value="">AllGenders</option>
                                        <option value="Male" ${selectedSex == 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${selectedSex == 'Female' ? 'selected' : ''}>Female</option>
                                    </select>

                                    <select name="status" id="statusFilter" class="form-select" style="width: 100%;">
                                        <option value="">AllStatus</option>
                                        <option value="Active" ${selectedStatus == 'Active' ? 'selected' : ''}>Active</option>
                                        <option value="Deactive" ${selectedStatus == 'Deactive' ? 'selected' : ''}>Deactive</option>
                                    </select>

                                    <button type="submit" class="btn btn-primary" style="width: 20%;">Filter</button>
                                </form>
                            </div>


                            <div class="col-md-5">
                                <form action="manageStaff" method="get" class="d-flex">
                                    <input type="text" name="searchKeyword" placeholder="Enter name or email..." value="${searchKeyword != null ? searchKeyword : ''}" class="form-control me-2" style="width: 70%;">

                                    <button type="submit" class="btn btn-primary" style="width: 30%;">Search</button>
                                </form>
                            </div>
                        </div>





                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Staff ID</th>
                                        <th>Full Name</th>
                                        <th>Phone Number</th>
                                        <th>CCCD</th>
                                        <th>Email</th>
                                        <th>Birth Date</th>
                                        <th>Gender</th>
                                        <th>Status</th>
                                        <th>Role</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="staff" items="${listStaff}">
                                        <tr>
                                            <td>${staff.staffId}</td>
                                            <td>${staff.fullName}</td>
                                            <td>${staff.phoneNumber}</td>
                                            <td>${staff.cccd}</td>
                                            <td>${staff.email}</td>
                                            <td><fmt:formatDate value="${staff.formattedDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                            <td>${staff.sex}</td>
                                            <td>
                                                <label class="switch">
                                                    <input type="checkbox" class="status-toggle" data-id="${staff.staffId}" ${staff.status eq 'Active' ? 'checked' : ''}>
                                                    <span class="slider round"></span>
                                                </label>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${staff.role.getRoleID() == 1}">Manager</c:when>
                                                    <c:when test="${staff.role.getRoleID() == 2}">Administrative Staff</c:when>
                                                    <c:when test="${staff.role.getRoleID() == 3}">Accountant</c:when>
                                                    <c:when test="${staff.role.getRoleID() == 4}">Technical Board</c:when>
                                                    <c:when test="${staff.role.getRoleID() == 5}">Service Provider</c:when>
                                                    <c:otherwise>Unknown</c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <div class="actions">
                                                    <a href="deleteStaff?staffId=${staff.staffId}" onclick="return confirm('Are you sure to delete this staff?');">Delete</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                                    <ul class="pagination">
    <c:if test="${currentPage > 1}">
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage - 1}&searchKeyword=${searchKeyword}&sex=${selectedSex}&status=${selectedStatus}&block=${selectedBlock}">Previous</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="?page=${i}&searchKeyword=${searchKeyword}&sex=${selectedSex}&status=${selectedStatus}&block=${selectedBlock}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <li class="page-item">
            <a class="page-link" href="?page=${currentPage + 1}&searchKeyword=${searchKeyword}&sex=${selectedSex}&status=${selectedStatus}&block=${selectedBlock}">Next</a>
        </li>
    </c:if>
</ul>
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script>
                                                        $(document).ready(function () {
                                                            $(".status-toggle").change(function () {
                                                                let staffId = $(this).data("id");
                                                                let newStatus = $(this).is(":checked") ? "Active" : "Deactive";

                                                                $.ajax({
                                                                    url: "/ManagerApartment/manager/updateStaffStatus",
                                                                    type: "POST",
                                                                    data: {staffId: staffId, status: newStatus},
                                                                    success: function (response) {
                                                                        alert(response.message);
                                                                    },
                                                                    error: function () {
                                                                        alert("Lỗi khi cập nhật trạng thái.");
                                                                    }
                                                                });
                                                            });
                                                        });
                </script>
                <script>
    $(document).ready(function () {
        // Mở Modal
        $("#openInsertModal").click(function () {
            $("#insertStaffModal").show();
        });

        // Đóng Modal khi nhấn dấu X
        $(".modal-close").click(function () {
            $("#insertStaffModal").hide();
        });

        // Đóng Modal khi click ra ngoài
        $(window).click(function (event) {
            if (event.target.id === "insertStaffModal") {
                $("#insertStaffModal").hide();
            }
        });

        // Gửi form bằng AJAX
        $("#submitBtn").click(function () {
            let formData = $("#insertStaffForm").serialize();
            $.ajax({
                type: "POST",
                url: "manageStaff",
                data: formData,
                dataType: "json",
                success: function (response) {
                    if (response.success) {
                        $("#message").html("<span style='color: green;'>" + response.message + "</span>");
                        $("#insertStaffForm")[0].reset(); // Reset form
                        setTimeout(() => { $("#insertStaffModal").hide(); location.reload(); }, 1500);
                    } else {
                        $("#message").html("<span style='color: red;'>" + response.message + "</span>");
                    }
                },
                error: function () {
                    $("#message").html("<span style='color: red;'>Error of sending data</span>");
                }
            });
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
         <script src="<%= request.getContextPath() %>/assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>
