<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resident Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
     <link rel="stylesheet" href="assets/css/pages/index.css">
     <link rel="stylesheet" href="assets/css/app.css" />
     <link rel="stylesheet" href="assets/css/bootstrap.css" />
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

    </style>
</head>
<body>
    <div id="sidebar" class="active">
            <div class="sidebar-wrapper active" style="border: 2px solid orangered; border-radius: 0 20px 20px 0">
                <div class="sidebar-header">
                    <div class="d-flex justify-content-between">
                        <div class="logo">
                            <a href="home.jsp"><img src="assets/images/logo/logo.png" alt="Logo" /></a>
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
                            <a href="home.jsp" class="sidebar-link">
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
                                <span>Tài khoản</span>
                            </a>
                            <ul class="submenu">
                                <li class="submenu-item">
                                    <a href="manageResident">Cư dân</a>
                                </li>
                                <li class="submenu-item">
                                    <a href="manageStaff">Nhân viên</a>
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
    
    <div class="content">
        <div class="container">
            <h1>Danh Sách Cư Dân</h1>
            <%-- Hiển thị thông báo nếu có --%>
            <c:if test="${not empty mess}">
                <div class="message">${mess}</div>
            </c:if>
            <div style="text-align: center; margin-bottom: 20px;">
            <a href="addresident.jsp" class="add-resident-button">Thêm Cư Dân</a>
            </div>
    <div class="row mb-3">
    <!-- Cột bên trái: Bộ lọc (45%) -->
    <div class="col-md-5 d-flex gap-2">
        <form action="manageResident" method="get" class="d-flex gap-2 flex-grow-1">
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

            <button type="submit" class="btn btn-primary" style="width: 20%;">Lọc</button>
        </form>
    </div>

    <!-- Cột bên phải: Tìm kiếm (45%) -->
    <div class="col-md-5">
        <form action="manageResident" method="get" class="d-flex">
            <input type="text" name="searchKeyword" placeholder="Nhập tên hoặc email..." value="${searchKeyword}" class="form-control me-2" style="width: 70%;">
            <button type="submit" class="btn btn-primary" style="width: 30%;">Tìm</button>
        </form>
    </div>
</div>



             

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Resident ID</th>
                            <th>Họ và Tên</th>
                            <th>Số Điện Thoại</th>
                            <th>CCCD</th>
                            <th>Email</th>
                            <th>Ngày Sinh</th>
                            <th>Giới Tính</th>
                            <th>Trạng Thái</th>
                             <th>Role</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="resident" items="${listResident}">
                            <tr>
                                <td>${resident.residentId}</td>
                                <td>${resident.fullName}</td>
                                <td>${resident.phoneNumber}</td>
                                <td>${resident.cccd}</td>
                                <td>${resident.mail}</td>
                                <td>${resident.dob}</td>
                                <td>${resident.sex}</td>
                                <td>
                                    <label class="switch">
                                    <input type="checkbox" class="status-toggle" data-id="${resident.residentId}" ${resident.status == 'Active' ? 'checked' : ''}>
                                    <span class="slider round"></span>
                                    </label>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${resident.roleId == 5}">Tenant</c:when>
                                        <c:when test="${resident.roleId == 6}">Owner</c:when>
                                        <c:otherwise>Unknown</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <div class="actions">
                                        <a href="deleteResident?residentId=${resident.residentId}" onclick="return confirm('Bạn có chắc chắn muốn xóa cư dân này không?');">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
                
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    $(".status-toggle").change(function() {
        let residentId = $(this).data("id");
        let newStatus = $(this).is(":checked") ? "Active" : "Deactive";

        $.ajax({
            url: "updateResidentStatus",
            type: "POST",
            data: { residentId: residentId, status: newStatus },
            success: function(response) {
                alert(response.message);
            },
            error: function() {
                alert("Lỗi khi cập nhật trạng thái.");
            }
        });
    });
});
</script>

</body>
</html>
