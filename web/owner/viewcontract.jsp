<%-- 
    Document   : viewcontract
    Created on : Mar 10, 2025, 10:33:44 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.Contract" %>

<jsp:useBean id="contract" scope="request" type="model.Contract" />

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <title>Thông Tin Hợp Đồng</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #F4A08C;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .contract-container {
                max-width: 800px; /* Tăng từ 500px lên 800px */
                width: 90%; /* Đảm bảo hiển thị tốt trên các màn hình khác nhau */
                background: #fff;
                padding: 30px; /* Tăng padding để nội dung thoáng hơn */
                border-radius: 10px;
                box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
                text-align: center;
            }


            .contract-title {
                font-size: 24px;
                font-weight: bold;
                color: #333;
                margin-bottom: 20px;
            }

            .contract-details {
                font-size: 16px;
                color: #555;
                text-align: left;
                line-height: 1.6;
            }

            .contract-details p {
                margin: 8px 0;
                padding: 10px;
                background: #f9f9f9;
                border-radius: 5px;
            }

            .no-contract {
                font-size: 18px;
                color: red;
                font-weight: bold;
            }

            .btn {
                margin-top: 20px;
                padding: 10px 20px;
                font-size: 16px;
                color: #fff;
                background: #007bff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: 0.3s;
            }

            .btn:hover {
                background: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="contract-container">
            <c:if test="${not empty contract}">
                <h2 class="contract-title">Contract Information</h2>
                <div class="contract-details">
                    <p><strong>Tenant Name:</strong> ${contract.resident.fullName}</p>
                    <p><strong>Room Name:</strong> ${contract.apartment.apartmentName}</p>
                    <p><strong>Lease Start Date:</strong> 
                        <fmt:formatDate value="${contract.formattedDate1}" pattern="dd/MM/yyyy"></fmt:formatDate>
                        </p>
                        <p><strong>Lease End Date:</strong> 
                        <fmt:formatDate value="${contract.formattedDate2}" pattern="dd/MM/yyyy"></fmt:formatDate>
                        </p>
                    </div>
            </c:if>

            <c:if test="${empty contract}">
                <p class="no-contract">Can not find any contract of this tenant.</p>
            </c:if>
            <c:if test="${not empty contract}">
                <button class="btn" data-toggle="modal" data-target="#renewModal${contract.resident.residentId}">Renew</button>
            </c:if>
            <button class="btn" onclick="window.history.back()">Back</button>
        </div>
        <!-- Modal Gia Hạn -->
        <div class="modal fade" id="renewModal${contract.resident.residentId}" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Renew Contract of Tenant: ${contract.resident.fullName}</h5>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div id="error-message" class="alert alert-danger" style="display:none;"></div> <!-- Hiển thị lỗi ở đây -->
                        <form id="renewForm">
                            <input type="hidden" name="tenantId" value="${contract.resident.residentId}">
                            <label>New End Date:</label>
                            <input type="text" name="newEndDate" required placeholder="dd/MM/yyyy">
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success">Confirm</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- JavaScript xử lý AJAX -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                $(document).ready(function () {
                    $("#renewForm").submit(function (event) {
                        event.preventDefault(); // Chặn form submit mặc định

                        $.ajax({
                            type: "POST",
                            url: "viewContract",
                            data: $(this).serialize(),
                            dataType: "json",
                            success: function (response) {
                                if (response.success) {
                                    alert(response.message);
                                    location.reload(); // Refresh trang nếu thành công
                                } else {
                                    $("#error-message").text(response.message).show(); // Hiển thị lỗi trong modal
                                }
                            },
                            error: function () {
                                $("#error-message").text("Server error. Please try again.").show();
                            }
                        });
                    });
                });
        </script>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

