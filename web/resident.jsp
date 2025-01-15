<%-- 
    Document   : resident
    Created on : Jan 16, 2025, 3:13:40 AM
    Author     : nkiem
--%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Trang ch?</title>

    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
        rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/bootstrap.css" />

    <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

    <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
    <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
    <link rel="stylesheet" href="assets/css/app.css" />
    <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
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
                                <span>Trang Ch?</span>
                            </a>
                        </li>

                        <li class="sidebar-item has-sub">
                            <a href="#" class="sidebar-link">
                                <i class="bi bi-stack"></i>
                                <span>Chung C?</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="component-alert.html">Tòa Nhà</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="component-badge.html">T?ng</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="component-breadcrumb.html">C?n H?</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="component-button.html">Ti?n Ích</a>
                                </li>
                            </ul>
                        </li>

                        <li class="sidebar-item has-sub">
                            <a href="#" class="sidebar-link">
                                <i class="bi bi-collection-fill"></i>
                                <span>C? Dân</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="extra-component-avatar.html">Danh Sách</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-sweetalert.html">Ph??ng Ti?n</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-toastify.html">Thông Báo</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-rating.html">Khi?u N?i</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-divider.html">Divider</a>
                                </li>
                            </ul>
                        </li>

                        <li class="sidebar-item has-sub">
                            <a href="#" class="sidebar-link">
                                <i class="bi bi-grid-1x2-fill"></i>
                                <span>Ph?n h?i</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="layout-default.html">G?i thông báo</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="layout-vertical-1-column.html">C? dân</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="layout-vertical-navbar.html">Ban k? toán</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="layout-horizontal.html">Ban b?o v?</a>
                                </li>
                            </ul>
                        </li>

                        <li class="sidebar-item has-sub">
                            <a href="#" class="sidebar-link">
                                <i class="bi bi-collection-fill"></i>
                                <span>Th?ng kê</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="extra-component-avatar.html">D?ch v? l?t xe</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-sweetalert.html">D?ch v? ?i?n công c?ng</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="extra-component-toastify.html">D?ch v? v? sinh</a>
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
                                <span>Nh?n tin</span>
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
                                <span>Thông tin phí d?ch v?</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a href="!#" class="sidebar-link">
                                <i class="bi bi-puzzle"></i>
                                <span>Quy ??nh</span>
                            </a>
                        </li>
                        <!-- =================================??ng nh?p, log out..==================== -->
                        <li class="sidebar-item has-sub">
                            <a href="#" class="sidebar-link">
                                <i class="bi bi-person-badge-fill"></i>
                                <span>Cài ??t</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="auth-login.html">Thông tin cá nhân</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="auth-login.html">Login</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="auth-login.html">Logout</a>
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
        <div id="main">
            <header class="mb-3">
                <a href="#" class="burger-btn d-block d-xl-none">
                    <i class="bi bi-justify fs-3"></i>
                </a>
            </header>
             <main  id="content">
                <div class="container mt-2">
                    <table class="table table-striped table-hover table-bordered caption-top table-responsive-md">
                        <caption>Danh Sách Ng??i Dùng</caption>
                        <tr class="table-dark">
                            <th class="col">STT</th>
                            <th class="col-1">FirstName</th>
                            <th class="col-1">LastName</th>
                            <th class="col-1">Email</th>
                            <th class="col-1">PhoneNumber</th>
                            <th class="col-1">Address</th>
                            <th class="col">Role</th>
                            <th class="col-3">ImgURL</th>
                            <th class="col-2">Edit</th>
                        </tr>
                        <c:set var="i" value="0"></c:set>
                        <c:forEach items="${requestScope.data}" var="user">
                            <tr>
                                <td>${i+1}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.phoneNumber}</td>
                                <td>${user.address}</td>
                                <td>${user.role}</td>
                                <td>${user.imgURL}</td>
                                <td class="btn">
                                    <a onclick="openForm(event, '${user.userID}', '${user.firstName}', '${user.lastName}', '${user.email}', '${user.phoneNumber}', '${user.address}', '${user.role}', '${user.imgURL}')" href="#!">Update</a>&nbsp;&nbsp;&nbsp;
                                    <a href="#!" onclick="deleteUser('${user.userID}', '${user.firstName} ${user.lastName}')">Delete</a>
                                </td>
                            </tr>
                            <c:set var="i" value="${i + 1}"></c:set>
                        </c:forEach>
                    </table>
                </div>

                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-md-6">
                            <div class="updateForm">
                                <form action="updateUser" method="post" enctype="multipart/form-data" onsubmit="return confirmUpdateUser()">
                                    <input type="hidden" name="id" id="userID">

                                    <div class="form-group">
                                        <label for="firstname">First Name</label>
                                        <input type="text" class="form-control" id="firstname" name="firstname" placeholder="Enter first name">
                                    </div>
                                    <div class="form-group">
                                        <label for="lastname">Last Name</label>
                                        <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Enter last name">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                                    </div>
                                    <div class="form-group">
                                        <label for="phonenumber">Phone Number</label>
                                        <input type="text" class="form-control" id="phonenumber" name="phonenumber" placeholder="Enter phone number">
                                    </div>
                                    <div class="form-group">
                                        <label for="address">Address</label>
                                        <input type="text" class="form-control" id="address" name="address" placeholder="Enter address">
                                    </div>
                                    <div class="form-group">
                                        <label for="role">Role</label>
                                        <input type="text" class="form-control" id="role" name="role" placeholder="Enter role">
                                    </div>
                                    <div class="form-group">
                                        <label for="imgURL">Image Profile</label>
                                        <input type="file" class="form-control" id="imgURL" name="imgURL">
                                    </div>

                                    <button id="submit" type="submit" class="btn btn-primary mt-3 submit">Update User</button>
                                </form>
                                <div id="close" onclick="closeForm()"><i class="fa-solid fa-circle-xmark"></i></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div onclick="closeForm()" class="overlay"></div>
            </main>

           
            
            <footer>
                <div class="footer clearfix mb-0 text-muted">
                    <div class="float-start">
                        <p>2021 &copy; Mazer</p>
                    </div>
                    <div class="float-end">
                        <p>
                            Crafted with
                            <span class="text-danger"><i class="bi bi-heart"></i></span> by
                            <a href="http://ahmadsaugi.com">A. Saugi</a>
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
