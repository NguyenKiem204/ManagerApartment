<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Tenant and Contract</title>
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
            
/* Modal content */
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
/* Dùng Flexbox để chia 2 cột */
.modal-grid {
    display: flex;
    gap: 15px; /* Khoảng cách giữa 2 cột */
}

/* Mỗi cột chiếm 50% */
.modal-column {
    flex: 1;
}

/* Định dạng input */
.modal-input,
.modal-select {
    width: 100%;
    padding: 8px;
    margin-bottom: 10px;
}

.action-btn {
    background-color: #007bff; 
    color: white; 
    border: none; 
    padding: 2px 8px; 
    font-size: 10px; 
    font-weight: bold; 
    border-radius: 5px; 
    cursor: pointer; 
    transition: all 0.3s ease-in-out; 
}

.action-btn:hover {
    background-color: #0056b3; 
    transform: scale(1.05); 
}

.action-btn:active {
    background-color: #004494; 
    transform: scale(0.98); 
}



        </style>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
                <!--=============================CONTENT HERE=======================-->
                <div class="w-90" style="display: flex;">
                    <div class="col-3"></div>
                    <div  class="col-9">
                        <h1>Tenant and Contract</h1>
                        <%-- Hiển thị thông báo nếu có --%>
                        <c:if test="${not empty mess}">
                            <div class="message">${mess}</div>
                        </c:if>
                        <!-- Nút Thêm Cư Dân -->
<div style="text-align: center; margin-bottom: 20px;">
    <button class="add-resident-button btn btn-success" id="openInsertModal">Add new tenant</button>
</div>

<!-- Modal Form -->
<div id="insertTenantModal" class="modal">
    <div class="modal-content">
        <span class="modal-close">&times;</span>
        <h2 class="modal-title">Add Tenant</h2>
    <form id="insertTenantForm">
        <div class="modal-grid">
            <div class="modal-column">
            <label for="fullName" class="modal-label">Full Name:</label>
            <input type="text" id="fullName" name="fullName" class="modal-input" required>

            <label for="phoneNumber" class="modal-label">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" class="modal-input" required maxlength="10" pattern="\d{10}">

            <label for="cccd" class="modal-label">CCCD:</label>
            <input type="text" id="cccd" name="cccd" class="modal-input" required maxlength="12" pattern="\d{12}">

            <label for="email" class="modal-label">Email:</label>
            <input type="email" id="email" name="email" class="modal-input" required>

            <label for="dob" class="modal-label">Birth Date:</label>
            <input type="text" id="dob" name="dob" class="modal-input" id="datePicker" placeholder="dd/MM/yyyy" required>
            </div>
            <div class="modal-column">
            <label for="sex" class="modal-label">Gender:</label>
            <select id="sex" name="sex" class="modal-select" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <label for="apartmentId" class="modal-label">Apartment:</label>
            <select id="apartmentId" name="apartmentId" class="modal-select" required>
                <option value="">-- Select Apartment --</option>
                <c:forEach var="apartment" items="${apartmentList}">
                    <option value="${apartment.apartmentId}">${apartment.apartmentName} - ${apartment.block}</option>
                </c:forEach>
            </select>

            <label for="leaseStartDate" class="modal-label">Lease Start Date:</label>
            <input type="text" id="leaseStartDate" name="leaseStartDate" class="modal-input" id="datePicker" placeholder="dd/MM/yyyy" required>

            <label for="leaseEndDate" class="modal-label">Lease End Date:</label>
            <input type="text" id="leaseEndDate" name="leaseEndDate" class="modal-input" id="datePicker" placeholder="dd/MM/yyyy" required>
            </div>
        </div>
            <button type="button" id="submitTenantBtn" class="modal-button">Add</button>
            
        </form>
        <div id="tenantMessage"></div>
    </div>
</div>
                        <div class="row mb-3">
                            <!-- Cột bên trái: Bộ lọc (45%) -->
                            <div class="col-md-5 d-flex gap-2">
                                <form action="manageContract" method="get" class="d-flex gap-2 flex-grow-1">
                                    <select name="sex" id="sexFilter" class="form-select" style="width: 100%;">
                                        <option value="">AllGenders</option>
                                        <option value="Male" ${sex == 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${sex == 'Female' ? 'selected' : ''}>Female</option>
                                    </select>

                                    <select name="status" id="statusFilter" class="form-select" style="width: 100%;">
                                        <option value="">AllStatus</option>
                                        <option value="Active" ${status == 'Active' ? 'selected' : ''}>Active</option>
                                        <option value="Deactive" ${status == 'Deactive' ? 'selected' : ''}>Deactive</option>
                                    </select>

                                    <button type="submit" class="btn btn-primary" style="width: 20%;">Filter:</button>
                                </form>
                            </div>

                            <!-- Cột bên phải: Tìm kiếm (45%) -->
                            <div class="col-md-5">
                                <form action="manageContract" method="get" class="d-flex">
                                    <input type="text" name="search" placeholder="Enter name or email..." value="${search}" class="form-control me-2" style="width: 70%;">
                                    <button type="submit" class="btn btn-primary" style="width: 30%;">Search</button>
                                </form>
                            </div>
                        </div>





                        <div class="table-container">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Resident ID</th>
                                        <th>FullName</th>
                                        <th>PhoneNumber</th>
                                        <th>Email</th>
                                        <th>CCCD</th>
                                        <th>DOB</th>
                                        <th>Gender</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="tenant" items="${listTenant}">
                                        <tr>
                                            <td>${tenant.residentId}</td>
                                            <td>${tenant.fullName}</td>
                                            <td>${tenant.phoneNumber}</td>
                                            <td>${tenant.email}</td>
                                            <td>${tenant.cccd}</td>
                                            <td><fmt:formatDate value="${tenant.formattedDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                            <td>${tenant.sex}</td>
                                            <td>
                                                <label class="switch">
                                                    <input type="checkbox" class="status-toggle" data-id="${tenant.residentId}" ${tenant.status eq 'Active' ? 'checked' : ''}>

                                                    <span class="slider round"></span>
                                                </label>
                                            </td>
                                            
                                             <td>
                                                <button class="action-btn" onclick="showContract(${tenant.residentId})">View Contract</button>
                                                <button class="action-btn" onclick="return confirmDelete(${tenant.residentId})">Delete</button>
                                             </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    
                                    <ul class="pagination">
    <c:if test="${currentPage > 1}">
        <li class="page-item">
            <a class="page-link" href="manageContract?page=${currentPage - 1}&search=${param.search}&sex=${param.sex}&status=${param.status}">Previous</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="manageContract?page=${i}&search=${param.search}&sex=${param.sex}&status=${param.status}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <li class="page-item">
            <a class="page-link" href="manageContract?page=${currentPage + 1}&search=${param.search}&sex=${param.sex}&status=${param.status}">Next</a>
        </li>
    </c:if>
</ul>

</div>
                                    
</div>


                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script>
                                                        $(document).ready(function () {
                                                            $(".status-toggle").change(function () {
                                                                let residentId = $(this).data("id");
                                                                let newStatus = $(this).is(":checked") ? "Active" : "Deactive";

                                                                $.ajax({
                                                                    url: "/ManagerApartment/owner/updateStatusTenant",
                                                                    type: "POST",
                                                                    data: {residentId: residentId, status: newStatus},
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
                    function showContract(tenantId) {
                        window.location.href = "viewContract?tenantId="+tenantId;
                    }
                </script>
                <script>
                    function confirmDelete(residentId) {
                        if (confirm("Are you sure to delete this tenant?")) {
                            window.location.href = "deleteTenant?tenantId=" + residentId;
                        }
                    }
                </script>
    <script>
    $(document).ready(function () {
        
        // Mở Modal
        $("#openInsertModal").click(function () {
            $("#insertTenantModal").show();
        });

        // Đóng Modal khi nhấn dấu X
        $(".modal-close").click(function () {
            $("#insertTenantModal").hide();
        });

        // Đóng Modal khi click ra ngoài
        $(window).click(function (event) {
            if (event.target.id === "insertTenantModal") {
                $("#insertTenantModal").hide();
            }
        });

        // Gửi form bằng AJAX
        $("#submitTenantBtn").click(function () {
            let formData = $("#insertTenantForm").serialize();
            $.ajax({
                type: "POST",
                url: "manageContract", // Cập nhật URL phù hợp với Servlet thêm Tenant + Contract
                data: formData,
                dataType: "json",
                success: function (response) {
                    if (response.success) {
                        $("#tenantMessage").html("<span style='color: green;'>" + response.message + "</span>");
                        $("#insertTenantForm")[0].reset(); // Reset form sau khi thêm thành công
                        setTimeout(() => { 
                            $("#insertTenantModal").hide(); 
                            location.reload(); // Load lại trang sau khi thêm thành công
                        }, 1500);
                    } else {
                        $("#tenantMessage").html("<span style='color: red;'>" + response.message + "</span>");
                    }
                },
                error: function () {
                    $("#tenantMessage").html("<span style='color: red;'>Lỗi khi gửi dữ liệu!</span>");
                }
            });
        });
    });
</script>

    </body>

</html>


