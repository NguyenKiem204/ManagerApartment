<%-- 
    Document   : feedbackadministrative
    Created on : Feb 9, 2025, 2:24:20 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
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
            }
        </style>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Manager Feedback Dashboard</h2>

                <div class="search-sort-container">
                    <input type="text" id="searchBox" placeholder="Search by title or staff member..." onkeyup="filterTable()">
                    <select id="sortBox" name="sortBox" onchange="sortProducts()">
                        <option value="0" hidden selected>Sort by</option>
                        <option value="1">Sort by Feedback for</option>
                        <option value="2">Sort by Rating</option>
                        <option value="3">Sort by Date</option>
                    </select>
                </div>

                <table id="feedbackTable">
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
                                <td class="rating">${"★".repeat(fb.rate)}${"☆".repeat(5 - fb.rate)}</td>
                                <td>${fb.description}</td>
                                <td>${fb.date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="pagination">
                    <button onclick="prevPage()" id="prevBtn">Previous</button>
                    <span id="pageNumber">Page 1</span>
                    <button onclick="nextPage()" id="nextBtn">Next</button>
                </div>
            </div>
        </div>

        <script>
            let currentPage = 1;
            const rowsPerPage = 5;
            let currentSort = {column: 'title', order: 'asc'};

            function renderTable() {
                const tableBody = document.getElementById("tableBody");
                const rows = tableBody.getElementsByTagName("tr");
                const totalRows = rows.length;

                for (let i = 0; i < totalRows; i++) {
                    rows[i].style.display = (i >= (currentPage - 1) * rowsPerPage && i < currentPage * rowsPerPage) ? "" : "none";
                }

                document.getElementById("pageNumber").innerText = `Page ${currentPage}`;
                document.getElementById("prevBtn").disabled = currentPage === 1;
                document.getElementById("nextBtn").disabled = currentPage * rowsPerPage >= totalRows;
            }

            function nextPage() {
                const totalRows = document.getElementById("tableBody").getElementsByTagName("tr").length;
                if ((currentPage * rowsPerPage) < totalRows) {
                    currentPage++;
                    renderTable();
                }
            }

            function prevPage() {
                if (currentPage > 1) {
                    currentPage--;
                    renderTable();
                }
            }

            function filterTable() {
                const query = document.getElementById("searchBox").value.toLowerCase();
                const rows = document.getElementById("tableBody").getElementsByTagName("tr");

                for (let row of rows) {
                    const cells = row.getElementsByTagName("td");
                    const title = cells[0].innerText.toLowerCase();
                    const staff = cells[1].innerText.toLowerCase();
                    row.style.display = (title.includes(query) || staff.includes(query)) ? "" : "none";
                }
            }

            document.addEventListener("DOMContentLoaded", () => {
                renderTable();
                updateSortIcons();
            });
            function sortProducts() {
                const sortValue = document.getElementById('sortBox').value;
                if (sortValue) {
                    // Chuyển hướng đến servlet với tham số sort
                    window.location.href = `feedback?sort=` + sortValue;
                }
            }
        </script>

    </body>
</html>
