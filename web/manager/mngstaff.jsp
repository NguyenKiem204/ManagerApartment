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
        <title>Manage Staff</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
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
                <%@include file="menumanager.jsp" %>
                <!--=============================CONTENT HERE=======================-->
                <div class="w-90" style="display: flex;">
                    <div class="col-3"></div>
                    <div  class="col-9">
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
            <input type="text" id="dob" name="dob" class="modal-input" id="datePicker" placeholder="dd/MM/yyyy" required>

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
                                    </div>
                </div>
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
    </body>

</html>
