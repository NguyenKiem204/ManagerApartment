<%-- 
    Document   : mngapartment
    Created on : Mar 2, 2025, 2:17:47 AM
    Author     : fptshop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Manage Apartment</title>
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
            .ct{
                margin-left: 350px;
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
            .add-apartment-button {
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

            .add-apartment-button:hover {
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



        </style>
    </head>

    <body>
        <%@include file="menumanager.jsp" %>
        <!--=============================CONTENT HERE=======================-->
        <div class="w-100" style="display: flex;">
            
            <div  class="col-9 ct">
                <h1>Apartments List</h1>
                <%-- Hiển thị thông báo nếu có --%>
                <c:if test="${not empty mess}">
                    <div class="message">${mess}</div>
                </c:if>
                <!-- Nút Thêm Cư Dân -->
                <div style="text-align: center; margin-bottom: 20px;">
                    <button class="add-apartment-button btn btn-success" id="openInsertModal">Add new apartment</button>
                </div>

                <!-- Modal Form -->
                <div id="insertApartmentModal" class="modal">
                    <div class="modal-content">
                        <span class="modal-close">&times;</span>
                        <h2 class="modal-title">Add apartment</h2>
                        <form id="insertApartmentForm">
                            <label for="apartmentName" class="modal-label">Apartment Name:</label>
                            <input type="text" id="apartmentName" name="apartmentName" class="modal-input" required>

                            <label for="block" class="modal-label">Block:</label>
                            <select id="block" name="block" class="modal-select" required>
                                <option value="Block A">Block A</option>
                                <option value="Block B">Block B</option>
                                <option value="Block C">Block C</option>
                            </select>

                            <label for="status" class="modal-label">Status:</label>
                            <select id="status" name="status" class="modal-select" required>
                                <option value="Available">Available</option>
                                <option value="Occupied">Occupied</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>

                            <label for="type" class="modal-label">Type:</label>
                            <select id="type" name="type" class="modal-select" required>
                                <option value="1 Bedroom">1 Bedroom</option>
                                <option value="2 Bedrooms">2 Bedrooms</option>
                                <option value="3 Bedrooms">3 Bedrooms</option>
                            </select>

                            <label for="ownerId" class="modal-label">OwnerId:</label>
                            <input type="text" id="ownerId" name="ownerId" class="modal-input" pattern="\d{4}" required>

                            <button type="button" id="submitBtn" class="modal-button">Add</button>
                        </form>
                        <div id="message"></div>
                    </div>
                </div>
                <div class=" row mb-3">
                    <!-- Cột bên trái: Bộ lọc (45%) -->
                    <div class="d-flex gap-2">
                        <form action="manageApartment" method="get" class="d-flex gap-2 flex-grow-1">
                            <select name="type" id="typeFilter" class="form-select" style="width: 100%;">
                                <option value="">AllTypes</option>
                                <option value="1 Bedroom" ${selectedType == '1 Bedroom' ? 'selected' : ''}>1 Bedroom</option>
                                <option value="2 Bedrooms" ${selectedType == '2 Bedrooms' ? 'selected' : ''}>2 Bedrooms</option>
                                <option value="3 Bedrooms" ${selectedType == '3 Bedrooms' ? 'selected' : ''}>3 Bedrooms</option>

                            </select>

                            <select name="status" id="statusFilter" class="form-select" style="width: 100%;">
                                <option value="">AllStatus</option>
                                <option value="Available" ${selectedStatus == 'Available' ? 'selected' : ''}>Available</option>
                                <option value="Occupied" ${selectedStatus == 'Occupied' ? 'selected' : ''}>Occupied</option>
                                <option value="Occupied" ${selectedStatus == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                            </select>
                            <select name="block" id="blockFilter" class="form-select" style="width: 100%;">
                                <option value="">AllBlocks</option>
                                <option value="Block A" ${selectedBlock == 'Block A' ? 'selected' : ''}>Block A</option>
                                <option value="Block B" ${selectedBlock == 'Block B' ? 'selected' : ''}>Block B</option>
                                <option value="Block C" ${selectedBlock == 'Block C' ? 'selected' : ''}>Block C</option>
                            </select>
                            <button type="submit" class="btn btn-primary" style="width: 20%;">Filter</button>
                        </form>
                    </div>

                    <!-- Cột bên phải: Tìm kiếm (45%) -->
                    <div class="col-md-6">
                        <form action="manageApartment" method="get" class="d-flex">
                            <input type="text" name="name" placeholder="Enter an apartment name..." value="${selectedName}" class="form-control me-2" style="width: 70%;">
                            <button type="submit" class="btn btn-primary" style="width: 30%;">Search</button>
                        </form>
                    </div>
                    <div class="col-md-5">
                        <form action="manageApartment" method="get" class="d-flex">
                            <input type="text" name="ownerId" value="${selectedOwnerId == 0 ? '' : selectedOwnerId}" 
                                    placeholder="Enter an ownerId..." class="form-control me-2" style="width: 70%;">
                            <button type="submit" class="btn btn-primary" style="width: 30%;">Search</button>
                        </form>
                    </div>
                </div>





                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Apartment Name</th>
                                <th>Block</th>
                                <th>Status</th>
                                <th>Type</th>
                                <th>Owner</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="apartment" items="${listApartment}">
                                <tr>
                                    <td>${apartment.apartmentId}</td>
                                    <td>${apartment.apartmentName}</td>
                                    <td>${apartment.block}</td>
                                    <td>${apartment.status}</td>
                                    <td>${apartment.type}</td>
                                    <td>${apartment.owner.fullName}</td>
                                    <td>
                                        <button class="btn btn-primary btn-edit" data-id="${apartment.apartmentId}">Edit</button>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal fade" id="editModal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg" role="document"> <!-- Thêm modal-lg hoặc modal-xl -->
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Apartment</h5>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <!-- Nội dung AJAX sẽ load vào đây -->
                            </div>
                        </div>
                    </div>
                </div>


                <ul class="pagination justify-content-center">
                    <!-- Nút First -->
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=1
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${selectedOwnerId != -1}">&ownerId=${selectedOwnerId}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">First</a>
                            </li>
                    </c:if>

                    <!-- Nút Previous -->
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${selectedOwnerId != -1}">&ownerId=${selectedOwnerId}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">Previous</a>
                            </li>
                    </c:if>

                    <!-- Xác định phạm vi trang hiển thị -->
                    <c:set var="startPage" value="${currentPage - 1}" />
                    <c:set var="endPage" value="${currentPage + 1}" />

                    <c:if test="${startPage < 1}">
                        <c:set var="startPage" value="1" />
                        <c:set var="endPage" value="3" />
                    </c:if>

                    <c:if test="${endPage > totalPages}">
                        <c:set var="endPage" value="${totalPages}" />
                        <c:set var="startPage" value="${totalPages - 2}" />
                    </c:if>

                    <c:if test="${startPage < 1}">
                        <c:set var="startPage" value="1" />
                    </c:if>

                    <!-- Hiển thị 3 trang liên tiếp -->
                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${selectedOwnerId != -1}">&ownerId=${selectedOwnerId}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                               ">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Next -->
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${selectedOwnerId != -1}">&ownerId=${selectedOwnerId}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">Next</a>
                            </li>
                    </c:if>

                    <!-- Nút Last -->
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${totalPages}
                               <c:if test="${not empty selectedName}">&name=${selectedName}</c:if>
                               <c:if test="${selectedOwnerId != -1}">&ownerId=${selectedOwnerId}</c:if>
                               <c:if test="${not empty selectedType}">&type=${selectedType}</c:if>
                               <c:if test="${not empty selectedStatus}">&status=${selectedStatus}</c:if>
                               <c:if test="${not empty selectedBlock}">&block=${selectedBlock}</c:if>
                                   ">Last</a>
                            </li>
                    </c:if>
                </ul>

                <c:if test="${totalPages == 0}">
                    <p class="text-center text-muted">No apartments found.</p>
                </c:if>

            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function () {
                $(".btn-edit").click(function () {
                    var apartmentId = $(this).data("id");

                    $.ajax({
                        url: "/ManagerApartment/manager/updateApartment",
                        type: "GET",
                        data: {apartmentId: apartmentId},
                        success: function (data) {
                            if (data.trim() === "") {
                                alert("No data returned. Please check your server.");
                            } else {
                                $("#editModal .modal-body").html(data); // Hiển thị dữ liệu vào modal
                                $("#editModal").modal("show"); // Mở modal
                            }
                        },
                        error: function (xhr, status, error) {
                            console.error("GET Error:", xhr.responseText);
                            alert("Error loading apartment data: " + xhr.responseText);
                        }
                    });
                });

// Xử lý lưu cập nhật
                $(document).on("click", "#saveUpdate", function () {
                    var formData = $("#updateApartmentForm").serialize();

                    $.ajax({
                        url: "/ManagerApartment/manager/updateApartment",
                        type: "POST",
                        data: formData,
                        success: function (response) {
                            $("#editModal").modal("hide"); // Đóng modal
                            alert("Apartment updated successfully!"); // Thông báo thành công
                            location.reload(); // Refresh danh sách
                        },
                        error: function (xhr, status, error) {
                            console.error("POST Error:", xhr.responseText);
                            alert("Error updating apartment: " + xhr.responseText);
                        }
                    });
                });

// Xử lý sự kiện bấm vào dấu "X"
                $(document).on("click", ".close", function () {
                    $("#editModal").modal("hide"); // Đóng modal khi bấm dấu "X"
                });

// Xử lý sự kiện bấm ra ngoài modal để đóng
                $(document).on("click", function (event) {
                    if ($(event.target).is("#editModal")) {
                        $("#editModal").modal("hide"); // Đóng modal nếu bấm ra ngoài modal
                    }
                });
            });

        </script>
        <script>
            $(document).ready(function () {

                // Mở Modal
                $("#openInsertModal").click(function () {
                    $("#insertApartmentModal").show();
                });

                // Đóng Modal khi nhấn dấu X
                $(".modal-close").click(function () {
                    $("#insertApartmentModal").hide();
                });

                // Đóng Modal khi click ra ngoài
                $(window).click(function (event) {
                    if (event.target.id === "insertApartmentModal") {
                        $("#insertApartmentModal").hide();
                    }
                });

                // Gửi form bằng AJAX
                $("#submitBtn").click(function () {
                    let formData = $("#insertApartmentForm").serialize();
                    $.ajax({
                        type: "POST",
                        url: "manageApartment",
                        data: formData,
                        dataType: "json",
                        success: function (response) {
                            if (response.success) {
                                $("#message").html("<span style='color: green;'>" + response.message + "</span>");
                                $("#insertApartmentForm")[0].reset(); // Reset form
                                setTimeout(() => {
                                    $("#insertApartmentModal").hide();
                                    location.reload();
                                }, 1500);
                            } else {
                                $("#message").html("<span style='color: red;'>" + response.message + "</span>");
                            }
                        },
                        error: function () {
                            $("#message").html("<span style='color: red;'>Lỗi khi gửi dữ liệu!</span>");
                        }
                    });
                });
            });
        </script>
    </body>

</html>

