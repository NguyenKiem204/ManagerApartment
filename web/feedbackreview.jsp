<%-- 
    Document   : feedbackadministrative
    Created on : Feb 9, 2025, 2:24:20 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback review</title>
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
                justify-content: end;
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
            th .sort-icon {
                margin-left: 5px;
                font-size: 12px;
            }
            .rating {
                color: #FFD700;
                font-size: 20px;
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
            tbody tr:hover {
                background-color: #ffe0b2;
                cursor: pointer;
            }
            .truncate {
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 200px; /* Adjust the width as needed */
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Feedback Overview</h2>

                <div class="search-sort-container">

                    <div style="padding-left: 10px">
                        <select id="sortBox" style="width: 100%">
                            <option value="0" ${param.sort == '0' ? 'selected' : ''}>Sort by</option>
                            <option value="1" ${param.sort == '1' ? 'selected' : ''}>Sort by monthYear new</option>
                            <option value="2" ${param.sort == '2' ? 'selected' : ''}>Sort by monthYear old</option>
                        </select>
                    </div>
                    <label for="itemsPerPage">Show:</label>
                    <select id="itemsPerPage" style="width: 80px">
                        <option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
                        <option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
                    </select> entries
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Total Feedback</th>
                            <th>Positive</th>
                            <th>Negative</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <c:forEach var="fb" items="${listfb}">
                            <tr data-feedback-id="${fb.managerFeedbackId}">
                                <td class="truncate"><fmt:formatDate value="${fb.formattedMonthYear}" pattern="dd/MM/yyyy" /></td>
                                <td>${fb.totalFeedback}</td>
                                <td>${fb.positivePercentage}</td>
                                <td>${fb.negativePercentage}</td>
                                
                                <td><button>View detail</button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

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
                                    <a class="page-link" href="?news=${num}"
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
            function updateFilter() {
                let params = new URLSearchParams(window.location.search);

                const sortBox = document.getElementById("sortBox").value;
                const pageSize = document.getElementById("itemsPerPage").value;

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
            document.querySelectorAll("#tableBody tr").forEach(row => {
                row.addEventListener("click", function () {
                    const feedbackId = this.getAttribute("data-feedback-id");
                    if (feedbackId) {
                        window.location.href = 'feedbackreviewdetail?managerFeedbackId=' + feedbackId;
                    }
                });
            });
        </script>
    </body>
</html>
