<%-- 
    Document   : sidebar
    Created on : Feb 7, 2025, 2:26:08 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>

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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #FFA384;
                margin: 0;
                padding: 20px;
            }
            .container {
                max-width: 600px;
                background-color: #fff;
                padding: 20px;
                margin: auto;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #ff9800;
            }
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: #555;
            }
            input, select, textarea {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            textarea {
                resize: vertical;
            }
            button {
                background-color: #ff9800;
                color: white;
                padding: 12px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                width: 100%;
                margin-top: 15px;
            }
            button:hover {
                background-color: #e68900;
            }
            .rating {
                display: flex;
                justify-content: center;
                flex-direction: row-reverse;
            }
            .rating input {
                display: none;
            }
            .rating label {
                font-size: 30px;
                color: #ccc;
                cursor: pointer;
                transition: color 0.3s;
            }
            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: #FFD700;
            }
            .result {
                text-align: center;
                font-size: 16px;
                margin-top: 10px;
                color: #333;
            }
            .error {
                color: red;
                font-size: 14px;
                display: none;
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
                                        <a href="auth-login.html">Thông tin cá nhân</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="#!">Login</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="login">Logout</a>
                                    </li>
                                    <li class="submenu-item">
                                        <a href="auth-forgot-password.html">Forgot Password</a>
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

            <div id = "main">
                <div class="container">
                    <h2>Thank You!</h2>
                    <p>Your feedback has been submitted successfully.</p>
                    <button onclick="window.location.href = 'feedback.jsp'">Submit Another Feedback</button>
                </div>
            </div>
            <script src="assets/js/bootstrap.bundle.min.js"></script>

            <script src="assets/vendors/apexcharts/apexcharts.js"></script>
            <script src="assets/js/pages/dashboard.js"></script>

            <script src="assets/js/main.js"></script>
    </body>
</html>
