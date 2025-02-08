<%-- 
    Document   : managefeedback
    Created on : Feb 7, 2025, 9:56:08 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <div class="container">
            <h2>Manager Feedback Dashboard</h2>

            <input type="text" id="searchBox" placeholder="Search by name or service..." onkeyup="filterTable()">

            <table id="feedbackTable">
                <thead>
                    <tr>
                        <th>Resident Name</th>
                        <th>Apartment</th>
                        <th>Service</th>
                        <th>Rating</th>
                        <th>Feedback</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!-- Data will be added dynamically -->
                </tbody>
            </table>

            <div class="pagination">
                <button onclick="prevPage()" id="prevBtn">Previous</button>
                <span id="pageNumber">Page 1</span>
                <button onclick="nextPage()" id="nextBtn">Next</button>
            </div>
        </div>

        <script>
            const feedbackData = [
                {name: "John Doe", apartment: "A12", service: "Electricity", rating: 5, feedback: "Great service!", date: "2025-01-01"},
                {name: "Jane Smith", apartment: "B20", service: "Water", rating: 4, feedback: "Water supply is stable.", date: "2025-01-02"},
                {name: "Robert Brown", apartment: "C5", service: "Maintenance", rating: 2, feedback: "Slow response time.", date: "2025-01-03"},
                {name: "Emily Davis", apartment: "D8", service: "Security", rating: 3, feedback: "Guards need to be more alert.", date: "2025-01-04"},
                {name: "Michael Wilson", apartment: "E10", service: "Other", rating: 1, feedback: "Service needs improvement!", date: "2025-01-05"},
                {name: "Sarah Taylor", apartment: "F22", service: "Electricity", rating: 4, feedback: "Reliable service overall.", date: "2025-01-06"},
                {name: "David Johnson", apartment: "G3", service: "Water", rating: 5, feedback: "Very satisfied with water quality.", date: "2025-01-07"},
                {name: "Jessica White", apartment: "H7", service: "Maintenance", rating: 3, feedback: "Needs better coordination.", date: "2025-01-08"},
                {name: "Daniel Martinez", apartment: "I15", service: "Security", rating: 2, feedback: "Security is not up to mark.", date: "2025-01-09"},
                {name: "Laura Anderson", apartment: "J9", service: "Other", rating: 4, feedback: "Good communication from management.", date: "2025-01-10"}
            ];

            let currentPage = 1;
            const rowsPerPage = 5;

            function renderTable() {
                const tableBody = document.getElementById("tableBody");
                tableBody.innerHTML = "";

                const start = (currentPage - 1) * rowsPerPage;
                const end = start + rowsPerPage;
                const pageData = feedbackData.slice(start, end);

                pageData.forEach(feedback => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${feedback.name}</td>
                        <td>${feedback.apartment}</td>
                        <td>${feedback.service}</td>
                        <td class="rating">${"★".repeat(feedback.rating)}${"☆".repeat(5 - feedback.rating)}</td>
                        <td>${feedback.feedback}</td>
                        <td>${feedback.date}</td>
                    `;
                    tableBody.appendChild(row);
                });

                document.getElementById("pageNumber").innerText = `Page ${currentPage}`;
                document.getElementById("prevBtn").disabled = currentPage === 1;
                document.getElementById("nextBtn").disabled = end >= feedbackData.length;
            }

            function nextPage() {
                if ((currentPage * rowsPerPage) < feedbackData.length) {
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
                const filteredData = feedbackData.filter(feedback =>
                    feedback.name.toLowerCase().includes(query) ||
                            feedback.service.toLowerCase().includes(query)
                );

                document.getElementById("tableBody").innerHTML = "";
                filteredData.forEach(feedback => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${feedback.name}</td>
                        <td>${feedback.apartment}</td>
                        <td>${feedback.service}</td>
                        <td class="rating">${"★".repeat(feedback.rating)}${"☆".repeat(5 - feedback.rating)}</td>
                        <td>${feedback.feedback}</td>
                        <td>${feedback.date}</td>
                    `;
                    document.getElementById("tableBody").appendChild(row);
                });
            }

            document.addEventListener("DOMContentLoaded", renderTable);
        </script>
    </body>
</html>
