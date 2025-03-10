<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Detail</title>
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />-->
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <style>
            /* 🌟 Reset mặc định */
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                /*                margin: 0;
                                padding: 20px;*/
            }

            /* 📌 Container chính */
            .container {
                max-width: 700px;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            /* 🏷️ Tiêu đề */
            h2 {
                text-align: center;
                color: #ff9800;
                font-weight: bold;
                margin-bottom: 20px;
            }

            /* 🔹 Định dạng hàng chi tiết */
            .detail-row, .detail-col {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px;
                border-bottom: 1px solid #ddd;
            }

            /* 🔹 Định dạng cột chi tiết */
            .detail-col {
                flex-direction: column;
                align-items: flex-start;
            }

            /* 🎯 Bỏ đường gạch cuối cùng */
            .detail-row:last-child, .detail-col:last-child {
                border-bottom: none;
            }

            /* 🏷️ Label */
            .label {
                font-weight: bold;
                color: #555;
            }

            /* 📜 Giá trị */
            .value {
                color: #333;
                font-size: 16px;
                word-wrap: break-word;
            }

            /* 📜 Description textarea */
            .value[readonly] {
                width: 100%;
                min-height: 80px;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px;
                background: #f8f9fa;
                resize: none;
                font-size: 16px;
            }

            /* 🚀 Trạng thái yêu cầu */
            .status {
                cursor: pointer;
                padding: 8px 14px;
                border-radius: 6px;
                font-weight: bold;
                text-align: center;
                min-width: 120px;
                color: white;
                text-transform: uppercase;
                box-shadow: 0 0 8px rgba(0, 0, 0, 0.15);
            }

            /* 🎨 Màu sắc trạng thái */
            .status[data-status-id="1"] {
                background-color: #FF5722;
            } /* Pending */
            .status[data-status-id="2"] {
                background-color: #FFC107;
            } /* Assigned */
            .status[data-status-id="3"] {
                background-color: #03A9F4;
            } /* In Progress */
            .status[data-status-id="4"] {
                background-color: #4CAF50;
            } /* Completed */
            .status[data-status-id="5"] {
                background-color: #9E9E9E;
            } /* Expired */
            .status[data-status-id="6"] {
                background-color: #E91E63;
            } /* Reopened */
            .status[data-status-id="7"] {
                background-color: #673AB7;
            } /* Resolved */
            .status[data-status-id="8"] {
                background-color: #B0BEC5;
            } /* Cancel */
            .status[data-status-id="0"] {
                background-color: #607D8B;
            } /* Unknown */

            /* 🌟 Nút duyệt và từ chối */
            .action-buttons {
                display: flex;
                gap: 10px;
                margin-top: 10px;
            }

            .approve-btn, .reject-btn {
                padding: 8px 14px;
                font-size: 14px;
                font-weight: bold;
                border: none;
                border-radius: 5px;
                color: white;
                cursor: pointer;
                transition: 0.3s;
            }

            .approve-btn {
                background-color: #4CAF50;
            } /* Xanh lá */
            .reject-btn {
                background-color: #FF5722;
            } /* Cam đậm */

            .approve-btn:hover {
                background-color: #388E3C;
            }
            .reject-btn:hover {
                background-color: #E64A19;
            }

            .approve-btn:disabled, .reject-btn:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }

            /* 🔙 Nút quay lại */
            .btn-back {
                display: block;
                width: 100%;
                text-align: center;
                padding: 12px;
                font-size: 16px;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                text-decoration: none;
                margin-top: 20px;
                transition: 0.3s;
            }

            .btn-back:hover {
                background-color: #0056b3;
            }

        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <c:set var="rq" value="${request}"></c:set>
            <div id = "main">
                <div class="container" style="background-color: white">
                    <h2>Request Detail</h2>
                    <div class="detail-row">
                        <span class="label">Request ID:</span> 
                        <span class="value">${rq.requestID}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Apartment:</span> 
                    <span class="value">${rq.apartment.apartmentName}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Title:</span> 
                    <span class="value">${rq.title}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Service Type:</span> 
                    <span class="value">${rq.typeRq.typeName}</span>
                </div>
                <div class="detail-col">
                    <span class="label">Description:</span>
                    <textarea readonly class="value">${rq.description}</textarea>
                </div>
                <div class="detail-row">
                    <span class="label">Status:</span>
                    <span class="status" data-id="${rq.requestID}" data-status-id="${rq.status.statusID}" onclick="updateStatus(this)">
                        ${rq.status.statusName}
                    </span>
                    <!--chỉ manager update được accept-->
                    <c:if test="${sessionScope.staff.role.roleID == 1}">
                        <div class="action-buttons" id="actionButtons-${rq.requestID}" style="display: none;">
                            <button class="approve-btn" onclick="approveRequest(${rq.requestID})">Approve</button>
                            <button class="reject-btn" onclick="rejectRequest(${rq.requestID})">Reject</button>
                        </div>
                    </c:if>
                </div>
                <c:if test="${sessionScope.staff.role.roleID == 1}">
                    <a href="<%= request.getContextPath() %>/manager/request" class="btn-back">Back to List</a>
                </c:if>
                <c:if test="${sessionScope.staff.role.roleID != 1}">
                    <a href="<%= request.getContextPath() %>/requeststaff" class="btn-back">Back to List</a>
                </c:if>
            </div>
    </body>
    <script>
        function updateStatus(element) {
            const requestID = element.getAttribute("data-id");
            let statusID = parseInt(element.getAttribute("data-status-id"));

            // Nếu trạng thái là Pending, hiển thị các nút Duyệt và Không Duyệt
            if (statusID === 1) {
                document.getElementById("actionButtons-" + requestID).style.display = "block";
                return; // Không tiếp tục xử lý vì đã có hành động ở đây
            }

            // Logic chuyển trạng thái cho các trạng thái khác
            if (statusID === 6) {
                statusID = 2; // Reopened → Assigned
            } else {
                return; // Nếu đã là Resolved (3), không làm gì cả
            }

            // Gửi AJAX cập nhật trạng thái
            fetch('${pageContext.request.contextPath}/manager/updateRequestStatus', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id: requestID, statusID: statusID})
            })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            element.setAttribute("data-status-id", statusID);
                            updateStatusDisplay(element, statusID);
                        } else {
                            alert("Lỗi cập nhật trạng thái!");
                        }
                    })
                    .catch(error => console.error('Lỗi:', error));
        }
        function approveRequest(requestID) {
            // Cập nhật trạng thái thành Processing khi "Duyệt"
            updateRequestStatus(requestID, 2);
        }

        function rejectRequest(requestID) {
            // Cập nhật trạng thái thành Cancel khi "Không Duyệt"
            updateRequestStatus(requestID, 8); // Giả sử 8 là trạng thái Cancel
        }

        function updateRequestStatus(requestID, statusID) {
            fetch('${pageContext.request.contextPath}/manager/updateRequestStatus', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id: requestID, statusID: statusID})
            })
                    .then(response => response.json())
                    .then(data => {
                        console.log("Response Data:", data); // Debugging
                        if (data.success) {
                            window.location.reload();
                            const statusElement = document.querySelector(`[data-id='${requestID}']`);
                            statusElement.setAttribute("data-status-id", statusID);
                            updateStatusDisplay(statusElement, statusID);
                            document.getElementById("actionButtons-" + requestID).style.display = "none"; // Ẩn các nút sau khi lựa chọn

                        } else {
                            alert("Failed to update status: ");
                        }
                    })
                    .catch(error => console.error('Lỗi:', error));
        }

        function updateStatusDisplay(element, statusID) {

            switch (statusID) {
                case 1:
                    element.innerText = "Pending";
                    element.style.backgroundColor = "#FF5722";// Orange
                    break;
                case 2:
                    element.innerText = "Assigned";
                    element.style.backgroundColor = "#FFC107"; // Amber
                    break;
                case 3:
                    element.innerText = "In Progress";
                    element.style.backgroundColor = "#03A9F4"; // Blue
                    break;
                case 4:
                    element.innerText = "Completed";
                    element.style.backgroundColor = "#4CAF50"; // Green
                    break;
                case 5:
                    element.innerText = "Expired";
                    element.style.backgroundColor = "#9E9E9E"; // Gray
                    break;
                case 6:
                    element.innerText = "Reopened";
                    element.style.backgroundColor = "#E91E63"; // Pink
                    break;
                case 7:
                    element.innerText = "Resolved";
                    element.style.backgroundColor = "#673AB7"; // Purple
                    break;
                case 8:
                    element.innerText = "Cancel";
                    element.style.backgroundColor = "#B0BEC5"; // Purple
                    break;
                default:
                    element.innerText = "Unknown";
                    element.style.backgroundColor = "#607D8B"; // Default: Blue Gray
            }
        }

    </script>
</html>
