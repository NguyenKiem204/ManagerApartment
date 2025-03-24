<%-- 
    Document   : formfeedbackmanager
    Created on : Feb 25, 2025, 3:01:10 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form feedback manager</title>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>-->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .container {
                max-width: 900px;
                background-color: #fff;
                padding: 20px;
                margin: auto;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2, h3 {
                color: #e67e22; /* Orange color */
            }
            #feedback-container {
                max-height: 400px; /* Giới hạn chiều cao */
                overflow-y: auto;  /* Kích hoạt thanh cuộn dọc */
                border: 1px solid #e67e22; /* Viền giống bảng */
                padding: 5px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            table, th, td {
                border: 1px solid #e67e22; /* Orange color */
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #e67e22; /* Orange color */
                color: white;
            }
            textarea {
                width: 100%;
                height: 80px;
                margin-top: 5px;
                border: 1px solid #e67e22; /* Orange color */
                border-radius: 4px;
                padding: 10px;
            }
            input[type="text"], input[type="date"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #e67e22; /* Orange color */
                border-radius: 4px;
            }
            .submit-btn {
                background-color: #e67e22; /* Orange color */
                color: white;
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                margin-top: 10px;
                border-radius: 4px;
            }
            .submit-btn:hover {
                background-color: #d35400; /* Darker orange color */
            }
        </style>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container" style="background-color: #f4f4f4;">
                <form action="formfeedbackmanager" method="Post">
                    <h2>📌 Employee Feedback Statistics</h2>
                    <p><b>Select Month & Year:</b>
                        <input type="month" id="feedback-month" name="feedback-month" onchange="loadNamesAndRatings()">
                    </p>

                    <h3>👤 Employee Information</h3>
                    <p><b>Position:</b> 
                        <select id="position" name="position" onchange="loadNamesAndRatings()">
                            <option value="0" selected="">Select Position</option>
                            <c:forEach var="role" items="${listrole}" begin="1" end="4">
                                <option value="${role.roleID}" ${param.position == role.roleID ? 'selected' : ''}>${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <div>
                        <p><b>Full Name:</b>
                            <input type="text" id="full-name" readonly>
                        </p>
                    </div>

                    <h3>📊 Overall Ratings</h3>
                    <table>
                        <tr>
                            <th>Total Feedback</th>
                            <th>Average Rating (⭐)</th>
                            <th>Positive Feedback (%)</th>
                            <th>Negative Feedback (%)</th>
                        </tr>
                        <tr>
                            <td id="total-feedback" name="totalFeedback"></td>
                            <td id="avg-rating" name="avgRating"></td>
                            <td id="positive-feedback" name="positivePercentage"></td>
                            <td id="negative-feedback" name="negativePercentage"></td>
                        </tr>
                    </table>

                    <h3>📑 Feedback Details</h3>
                    <div id="feedback-container">
                        <table>
                            <tr>
                                <th>#</th>
                                <th>Title</th>
                                <th>Rating (⭐)</th>
                                <th>Submission Date</th>
                            </tr>
                            <tbody id="feedback-list">
                                <!-- Feedback sẽ được thêm vào đây bằng JavaScript -->
                            </tbody>
                        </table>
                    </div>

                    <!--                    <div class="pagination" style="justify-content: end">
                                            <ul style="list-style: none; display: flex; justify-content: center; padding: 0;">
                    <c:set var="currentPage" value="${currentPage}" />
                    <c:set var="totalPages" value="${totalPages}" />
                    <p>Debug: Current Page = ${currentPage}, Total Pages = ${totalPages}</p>

                    <c:if test="${totalPages > 1}">
                        <%-- Nút First --%>
                        <c:if test="${currentPage > 1}">
                            <li><a href="?page=1&pageSize=${pageSize}" class="page-link">First</a></li>
                        </c:if>

                        <%-- Hiển thị các trang --%>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li>
                                <a href="?page=${i}&pageSize=${pageSize}" 
                                   class="page-link ${i == currentPage ? 'active' : ''}">
                            ${i}
                        </a>
                    </li>
                        </c:forEach>

                        <%-- Nút Last --%>
                        <c:if test="${currentPage < totalPages}">
                            <li><a href="?page=${totalPages}&pageSize=${pageSize}" class="page-link">Last</a></li>
                        </c:if>
                    </c:if>
            </ul>
        </div>-->

                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li><a href="?page=1&pageSize=${pageSize}" class="page-link">First</a></li>
                            <li><a href="?page=${currentPage - 1}&pageSize=${pageSize}" class="page-link">Previous</a></li>
                            </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li><a href="?page=${i}&pageSize=${pageSize}" class="page-link ${i == currentPage ? 'active' : ''}">${i}</a></li>
                            </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li><a href="?page=${currentPage + 1}&pageSize=${pageSize}" class="page-link">Next</a></li>
                            <li><a href="?page=${totalPages}&pageSize=${pageSize}" class="page-link">Last</a></li>
                            </c:if>
                    </ul>

                    <h3>📌 Manager's Comments</h3>
                    <b>💡 Strengths:</b>
                    <textarea id="strengths" name="strengths"></textarea>

                    <b>⚠ Areas for Improvement:</b>
                    <textarea id="weaknesses" name="weaknesses"></textarea>

                    <h3>📅 Next Actions</h3>
                    <p><b>Improvement Suggestions:</b> <input type="text" id="action-plan" name="actionPlan" placeholder="E.g., skill training, process improvement..."></p>
                    <p><b>Response Deadline:</b> <input type="text" id="deadline" name="deadline" placeholder="dd/MM/yyyy"></p>

                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>

                    <button class="submit-btn">📩 Submit Report</button>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        function loadNamesAndRatings() {
            const position = document.getElementById("position").value;
            const monthYear = document.getElementById("feedback-month").value;
            const nameInput = document.getElementById("full-name");

            if (position) {
                fetch('${pageContext.request.contextPath}/manager/formfeedbackmanager?position=' + position)
                        .then(response => response.json())
                        .then(data => {
                            if (data.staffList.length > 0) {
                                nameInput.value = data.staffList[0].fullName;
                            } else {
                                nameInput.value = "No staff available";
                            }

                            // Nếu chưa chọn Month/Year, hiển thị N/A cho Overall Rating
                            if (!monthYear) {
                                document.getElementById("total-feedback").textContent = "N/A";
                                document.getElementById("avg-rating").textContent = "N/A";
                                document.getElementById("positive-feedback").textContent = "N/A";
                                document.getElementById("negative-feedback").textContent = "N/A";
//                                document.getElementById("feedback-list").innerHTML = "N/A";
                                document.getElementById("feedback-list").innerHTML = `<tr><td colspan="4" style="text-align:center;">No feedback available</td></tr>`;
                            }
                        })
                        .catch(error => console.error('Error:', error));
            } else {
                nameInput.value = "";
                document.getElementById("total-feedback").textContent = "N/A";
                document.getElementById("avg-rating").textContent = "N/A";
                document.getElementById("positive-feedback").textContent = "N/A";
                document.getElementById("negative-feedback").textContent = "N/A";
                document.getElementById("feedback-list").innerHTML = `<tr><td colspan="4" style="text-align:center;">No feedback available</td></tr>`;
            }

            // Nếu đã chọn cả Position & Month/Year, thì lấy Overall Rating
            if (position && monthYear) {
                fetch('${pageContext.request.contextPath}/manager/formfeedbackmanager?position=' + position + '&monthYear=' + monthYear)
                        .then(response => response.json())
                        .then(data => {
                            document.getElementById("total-feedback").textContent = data.totalFeedback ?? "N/A";
                            document.getElementById("avg-rating").textContent = data.avgRating ?? "N/A";
                            document.getElementById("positive-feedback").textContent = data.positiveFeedback ?? "N/A";
                            document.getElementById("negative-feedback").textContent = data.negativeFeedback ?? "N/A";

                            const feedbackListElement = document.getElementById("feedback-list");
                            feedbackListElement.innerHTML = "";//xoa du lieu cu

                            if (data.feedbackList && data.feedbackList.length > 0) {
                                data.feedbackList.forEach((fb, index) => {
//                    console.log(`Rendering row ` + fb.title);
                                    index++;

//FORMAT DATE
                                    let formattedDate = "N/A"; // Mặc định nếu ngày không hợp lệ

                                    if (fb.date) {  // Đảm bảo fb.date không null
                                        let dateObj = new Date(fb.date + "T00:00:00"); // Chuyển LocalDate thành Date

                                        if (!isNaN(dateObj.getTime())) { // Kiểm tra ngày hợp lệ
                                            formattedDate = dateObj.toLocaleDateString('vi-VN', {
                                                day: '2-digit',
                                                month: '2-digit',
                                                year: 'numeric'
                                            });
                                        }
                                    }

                                    feedbackListElement.innerHTML += `<tr>
                                                                            <td>` + index + `</td>
                                                                            <td>` + fb.title + `</td>
                                                                            <td>` + fb.rate + `</td>
                                                                            <td>` + formattedDate + `</td>
                                                                      </tr>`;
                                });
                            } else {
                                feedbackListElement.innerHTML = `<tr><td colspan="4" style="text-align:center;">No feedback available</td></tr>`;
                            }// 🛠 In từng phần tử ra console

                        })
                        .catch(error => console.error('Error:', error));
            }
        }

    </script>

    <script>
        $(document).ready(function () {
            $('#strengths').summernote({
                height: 300,
                tabsize: 2,
                placeholder: "Acknowledge the strengths of the employee..."
            });
        });
        $(document).ready(function () {
            $('#weaknesses').summernote({
                height: 300,
                tabsize: 2,
                placeholder: "Areas that need improvement or correction..."
            });
        });
    </script>

    <script>
        function updateFilter() {
            let params = new URLSearchParams(window.location.search);

            const pageSize = document.getElementById("itemsPerPage").value;

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

            window.location.href = "?" + params.toString();
        }

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
                window.location.href = "?" + params.toString();
            });
        });
    </script>
    <script>
        function updatePageSize() {
            let pageSize = document.getElementById("itemsPerPage").value;
            window.location.href = "?page=1&pageSize=" + pageSize;
        }
        
    </script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>

    <script src="assets/vendors/apexcharts/apexcharts.js"></script>
    <script src="assets/js/pages/dashboard.js"></script>

    <script src="assets/js/main.js"></script>
</html>
