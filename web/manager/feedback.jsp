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
        <title>Manage Feedback</title>
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
        </style>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Manager Feedback Dashboard</h2>

                <div class="search-sort-container">
                    <input type="text" id="searchBox" placeholder="Search by name of staff or title..." value="${param.keySearch}" style="width: 40%">

                    <div class="filter-container" style="padding-left: 10px">
                        <div class="filter-row">
                            <select id="staffFilter">
                                <option value="0">Filter by Staff</option>
                                <c:forEach var="role" items="${listrole}" begin="1" end="4">
                                    <option value="${role.roleID}" ${param.roleID == role.roleID ? 'selected' : ''}>${role.roleName}</option>
                                </c:forEach>
                            </select>

                            <select id="ratingFilter">
                                <option value="0">Filter by Rating</option>
                                <option value="1" ${param.rating == '1' ? 'selected' : ''}>1 Star</option>
                                <option value="2" ${param.rating == '2' ? 'selected' : ''}>2 Stars</option>
                                <option value="3" ${param.rating == '3' ? 'selected' : ''}>3 Stars</option>
                                <option value="4" ${param.rating == '4' ? 'selected' : ''}>4 Stars</option>
                                <option value="5" ${param.rating == '5' ? 'selected' : ''}>5 Stars</option>
                            </select>
                        </div>

                        <input type="date" id="dateFilter" class="date-filter" name="date" value="${param.date}">
                    </div>
                    <div style="padding-left: 10px">
                        <select id="sortBox" style="width: 100%">
                            <option value="0" ${param.sort == '0' ? 'selected' : ''}>Sort by</option>
                            <option value="1" ${param.sort == '1' ? 'selected' : ''}>Sort by Feedback for</option>
                            <option value="2" ${param.sort == '2' ? 'selected' : ''}>Sort by Rating</option>
                            <option value="3" ${param.sort == '3' ? 'selected' : ''}>Sort by Date</option>
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
                            <th>Title</th>
                            <th>Feedback for</th>
                            <th>Staff</th>
                            <th>Rating</th>
                            <th>Feedback</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <c:forEach var="fb" items="${listfb}">
                            <tr>
                                <td>${fb.title}</td>
                                <td>${fb.staff.fullName}</td>
                                <td>${fb.staff.role.roleName}</td>
                                <td>${fb.rate} Stars</td>
                                <td>${fb.description}</td>
                                <td>${fb.date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <div class="pagination" style="justify-content: end">
                    <ul style="list-style: none; display: flex; justify-content: center; padding: 0;">
                        <c:forEach begin="1" end="${num}" var="i">
                            <li style="margin: 0 5px;">
                                <a class="page-link" href="" 
                                   style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                    ${i}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <script>
            function updateFilter() {
                let params = new URLSearchParams(window.location.search);

                const searchBox = document.getElementById("searchBox").value.trim();
                const staffFilter = document.getElementById("staffFilter").value;
                const ratingFilter = document.getElementById("ratingFilter").value;
                const dateFilter = document.getElementById("dateFilter").value;
                const sortBox = document.getElementById("sortBox").value;
                const pageSize = document.getElementById("itemsPerPage").value;
                const currentPage = params.get("page") || 1;

                params.set("keySearch", searchBox || "");
                params.set("roleID", staffFilter || "");
                params.set("rating", ratingFilter || "");
                params.set("date", dateFilter || "");
                params.set("sort", sortBox || "");
                params.set("pageSize", pageSize || "");
                params.set("page", currentPage);

                window.location.search = params.toString();
            }

            document.getElementById("searchBox").addEventListener("input", function () {
                clearTimeout(this.delay);
                this.delay = setTimeout(updateFilter, 500);
            });

            document.getElementById("staffFilter").addEventListener("change", updateFilter);
            document.getElementById("ratingFilter").addEventListener("change", updateFilter);
            document.getElementById("dateFilter").addEventListener("change", updateFilter);
            document.getElementById("sortBox").addEventListener("change", updateFilter);
            document.getElementById("itemsPerPage").addEventListener("change", updateFilter);

            // Xử lý phân trang
            document.querySelectorAll(".page-link").forEach(pageLink => {
                pageLink.addEventListener("click", function (event) {
                    event.preventDefault();
                    let params = new URLSearchParams(window.location.search);
                    params.set("page", this.textContent.trim()); // Cập nhật số trang
                    window.location.search = params.toString();
                });
            });
        </script>
    </body>
</html>
