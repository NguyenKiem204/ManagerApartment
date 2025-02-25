<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Administrative</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;

            }
            .container {
                max-width: 900px;
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
            .search-sort-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }
            input, select {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            input {
                width: 70%;
            }
            select {
                width: 28%;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: #ff9800;
                color: white;
                cursor: pointer;
                position: relative;
            }
            .pagination {
                text-align: center;
                margin-top: 10px;
            }
            .pagination button {
                background-color: #ff9800;
                color: white;
                border: none;
                padding: 8px 15px;
                margin: 5px;
                cursor: pointer;
                border-radius: 4px;
            }
            .pagination button:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }
            #pageNumber {
                color: black;
                font-weight: bold;
                margin-top: auto;
                margin-bottom: auto;
            }
            .status {
                cursor: pointer;
                padding: 6px 12px;
                border-radius: 6px;
                font-weight: bold;
                display: inline-block;
                text-align: center;
                min-width: 100px;
                color: white;
            }

            .status[data-status-id="1"] { /* Pending */
                background-color: #FF5722; /* Cam đậm */
                border: 2px solid #E64A19;
                box-shadow: 0 0 5px rgba(255, 87, 34, 0.5);
            }

            .status[data-status-id="2"] { /* Processing */
                background-color: #03A9F4; /* Xanh dương sáng */
                border: 2px solid #0288D1;
                box-shadow: 0 0 5px rgba(3, 169, 244, 0.5);
            }

            .status[data-status-id="3"] { /* Resolved */
                background-color: #4CAF50; /* Xanh lá đậm */
                border: 2px solid #388E3C;
                box-shadow: 0 0 5px rgba(76, 175, 80, 0.5);
            }

            .status[data-status-id="4"] { /* Canceled */
                background-color: #B0BEC5; /* Màu xám cho trạng thái hủy */
                border: 2px solid #78909C;
                box-shadow: 0 0 5px rgba(176, 190, 197, 0.5);
            }

            .filter-container {
                display: flex;
                flex-direction: column;
                gap: 8px;
            }

            .filter-row {
                display: flex;
                gap: 10px;
            }

            .filter-row select, .filter-row input {
                flex: 1;
            }

            .date-filter {
                width: 100%;
            }

            .action-buttons {
                display: flex;
                gap: 10px;
                margin-top: 5px;
            }

            .action-buttons button {
                padding: 8px 16px;
                border-radius: 5px;
                border: none;
                font-weight: bold;
                cursor: pointer;
            }

            .approve-btn {
                background-color: #4CAF50; /* Xanh lá */
                color: white;
            }

            .reject-btn {
                background-color: #FF5722; /* Cam đậm */
                color: white;
            }

            .approve-btn:hover, .reject-btn:hover {
                opacity: 0.8;
            }

            .approve-btn:focus, .reject-btn:focus {
                outline: none;
            }

            .approve-btn:disabled, .reject-btn:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }

        </style>
    </head>
    <body>
        <%@include file="home.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Resident Requests Dashboard</h2>

                <div class="search-sort-container">
                    <input type="text" id="searchBox" placeholder="Search by apartment or resident name..." value="${param.keySearch}" style="width: 40%">

                    <div class="filter-container" style="padding-left: 10px">
                        <div class="filter-row">
                            <select id="staffFilter">
                                <option value="0">Filter by Type Request</option>
                                <c:forEach var="typerq" items="${listTypeRq}" begin="2" end="3">
                                    <option value="${typerq.typeRqID}" ${param.typeRequestID == typerq.typeRqID ? 'selected' : ''}>${typerq.typeName}</option>
                                </c:forEach>
                            </select>

                            <select id="statusFilter">
                                <option value="0">Filter by Status</option>
                                <c:forEach var="status" items="${listStatus}">
                                    <option value="${status.statusID}" ${param.status == status.statusID ? 'selected' : ''}>${status.statusName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <input type="date" id="dateFilter" class="date-filter" name="date" value="${param.date}">
                    </div>
                    <div style="padding-left: 10px">
                        <select id="sortBox" style="width: 100%">
                            <option value="0" ${param.sort == '0' ? 'selected' : ''}>Sort by</option>
                            <option value="1" ${param.sort == '1' ? 'selected' : ''}>Sort by Apartment</option>
                            <option value="2" ${param.sort == '2' ? 'selected' : ''}>Sort by Date</option>
                        </select>
                    </div>
                    <label for="itemsPerPage">Show:</label>
                    <select id="itemsPerPage" style="width: 80px">
                        <option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
                        <option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
                    </select> entries
                </div>

                <table id="requestTable">
                    <thead>
                        <tr>
                            <th>Resident Name</th>
                            <th>Apartment</th>
                            <th>Service</th>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <c:forEach var="rq" items="${listrq}">
                            <tr>
                                <td>${rq.resident.fullName}</td>
                                <td>${rq.apartment.apartmentName}</td>
                                <td>${rq.typeRq.typeName}</td>
                                <td><fmt:formatDate value="${rq.formattedDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                    <td>
                                        <span class="status" data-id="${rq.requestID}" data-status-id="${rq.status.statusID}" onclick="updateStatus(this)">
                                        ${rq.status.statusName}
                                    </span>
                                    <div class="action-buttons" id="actionButtons-${rq.requestID}" style="display: none;">
                                        <button class="approve-btn" onclick="approveRequest(${rq.requestID})">Approve</button>
                                        <button class="reject-btn" onclick="rejectRequest(${rq.requestID})">Reject</button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <div class="pagination" style="justify-content: end">
                    <ul style="list-style: none; display: flex; justify-content: center; padding: 0;">
                        <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
                        <c:if test="${num > 1}">
                            <%-- Nút First --%>
                            <c:if test="${currentPage > 1}">
                                <li style="margin: 0 5px;">
                                    <a class="page-link" href="?page=1"
                                       style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                        First
                                    </a>
                                </li>
                            </c:if>

                            <%-- Nếu tổng số trang nhỏ hơn hoặc bằng 3, hiển thị tất cả --%>
                            <c:choose>
                                <c:when test="${num <= 3}">
                                    <c:forEach begin="1" end="${num}" var="i">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link ${i == currentPage ? 'active' : ''}" href="?page=${i}" 
                                               style="padding: 8px 12px; background: ${i == currentPage ? '#d35400' : '#ff9800'}; color: white; text-decoration: none; border-radius: 4px;">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <%-- Hiển thị trang trước, trang hiện tại và trang sau --%>
                                    <c:if test="${currentPage > 1}">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link" href="?page=${currentPage - 1}"
                                               style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                                ${currentPage - 1}
                                            </a>
                                        </li>
                                    </c:if>

                                    <li style="margin: 0 5px;">
                                        <a class="page-link active" href="?page=${currentPage}"
                                           style="padding: 8px 12px; background: #d35400; color: white; text-decoration: none; border-radius: 4px;">
                                            ${currentPage}
                                        </a>
                                    </li>

                                    <c:if test="${currentPage < num}">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link" href="?page=${currentPage + 1}"
                                               style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                                ${currentPage + 1}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>

                            <%-- Nút Last --%>
                            <c:if test="${currentPage < num}">
                                <li style="margin: 0 5px;">
                                    <a class="page-link" href="?page=${num}"
                                       style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                        Last
                                    </a>
                                </li>
                            </c:if>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".status").forEach(item => {
                    item.addEventListener("click", function () {
                        const requestID = this.getAttribute("data-id");
                        let statusID = parseInt(this.getAttribute("data-status-id"));

                        if (statusID === 2) { // Nếu đang là Processing
                            updateStatusToResolved(requestID, this);
                        }
                    });
                });
            });

            function updateStatusToResolved(requestID, element) {
                fetch('${pageContext.request.contextPath}/manager/updateRequestStatus', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({id: requestID, statusID: 3}) // Chuyển thành Resolved
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                element.setAttribute("data-status-id", 3);
                                element.innerText = "Resolved";
                                element.style.backgroundColor = "#4CAF50";
                                element.style.border = "2px solid #388E3C";
                                element.style.boxShadow = "0 0 5px rgba(76, 175, 80, 0.5)";
                            } else {
                                alert("Lỗi cập nhật trạng thái!");
                            }
                        })
                        .catch(error => console.error('Lỗi:', error));
            }

            function updateStatusDisplay(element, statusID) {
                if (statusID === 2) {
                    element.innerText = "Processing";
                    element.style.backgroundColor = "#03A9F4";
                } else if (statusID === 3) {
                    element.innerText = "Resolved";
                    element.style.backgroundColor = "#4CAF50";
                }
            }
        </script>
        <script>
            function updateFilter() {
                let params = new URLSearchParams(window.location.search);

                const searchBox = document.getElementById("searchBox").value.trim();
                const staffFilter = document.getElementById("staffFilter").value;
                const statusFilter = document.getElementById("statusFilter").value;
                const dateFilter = document.getElementById("dateFilter").value;
                const sortBox = document.getElementById("sortBox").value;
                const pageSize = document.getElementById("itemsPerPage").value;

                if (searchBox) {
                    params.set("keySearch", searchBox);
                } else {
                    params.delete("keySearch");
                }

                if (staffFilter !== "0") {
                    params.set("typeRequestID", staffFilter);
                } else {
                    params.delete("typeRequestID");
                }

                if (statusFilter !== "0") {
                    params.set("status", statusFilter);
                } else {
                    params.delete("status");
                }

                if (dateFilter) {
                    params.set("date", dateFilter);
                } else {
                    params.delete("date");
                }

                if (sortBox !== "0") {
                    params.set("sort", sortBox);
                } else {
                    params.delete("sort");
                }

                if (pageSize !== "5") {
                    params.set("pageSize", pageSize);
                } else {
                    params.delete("pageSize");
                }

                if (params.toString()) {
                    params.set("page", "1");
                } else {
                    params.delete("page");
                }

                const newUrl = params.toString() ? "?" + params.toString() : window.location.pathname;
                window.history.pushState({}, "", newUrl);
                window.location.reload();
            }

            document.getElementById("searchBox").addEventListener("input", function () {
                clearTimeout(this.delay);
                this.delay = setTimeout(updateFilter, 500);
            });

            document.getElementById("staffFilter").addEventListener("change", updateFilter);
            document.getElementById("statusFilter").addEventListener("change", updateFilter);
            document.getElementById("dateFilter").addEventListener("change", updateFilter);
            document.getElementById("sortBox").addEventListener("change", updateFilter);
            document.getElementById("itemsPerPage").addEventListener("change", updateFilter);

            // Xử lý phân trang
            document.querySelectorAll(".page-link").forEach(pageLink => {
                pageLink.addEventListener("click", function (event) {
                    event.preventDefault();
                    let params = new URLSearchParams(window.location.search);
                    let pageText = this.textContent.trim().toLowerCase();
                    if (pageText === "first") {
                        params.set("page", "1");  // Nếu click vào "First" → page = 1
                    } else if (pageText === "last") {
                        params.set("page", "${num}"); // Nếu click vào "Last" → page = num
                    } else {
                        params.set("page", pageText); // Các trang số bình thường
                    }
                    window.location.search = params.toString();
                });
            });
        </script>

    </body>
</html>
