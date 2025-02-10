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
            input {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
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
        </style>
    </head>
    <body>
        <%@include file="menuadministrative.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Manager Feedback Dashboard</h2>

                <input type="text" id="searchBox" placeholder="Search by name or service..." onkeyup="filterTable()">

                <table id="feedbackTable">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Feedback for</th>
                            <th>Rating</th>
                            <th>Feedback</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <c:forEach var="fb" items="${listfb}">
                            <tr>
                                <td>${fb.title}</td>
                                <td>${fb.staffID}</td>
                                <td>xxx</td>
                                <!--<td class="rating"></td>-->
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
                    const name = cells[0].innerText.toLowerCase();
                    const service = cells[2].innerText.toLowerCase();
                    row.style.display = (name.includes(query) || service.includes(query)) ? "" : "none";
                }
            }

            document.addEventListener("DOMContentLoaded", renderTable);
        </script>

    </body>
</html>
