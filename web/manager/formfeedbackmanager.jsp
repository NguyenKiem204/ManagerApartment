<%-- 
    Document   : formfeedbackmanager
    Created on : Feb 25, 2025, 3:01:10 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form feedback manager</title>
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
                            <option value="0">Select Position</option>
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
                            <td id="total-feedback"></td>
                            <td id="avg-rating"></td>
                            <td id="positive-feedback"></td>
                            <td id="negative-feedback"></td>
                        </tr>
                    </table>

                    <h3>📑 Feedback Details</h3>
                    <table>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Rating (⭐)</th>
                            <th>Submission Date</th>
                        </tr>
                        <tbody id="feedback-list">
                            <!-- Data will be added here -->
                        </tbody>
                    </table>

                    <h3>📌 Manager's Comments</h3>
                    <b>💡 Strengths:</b>
                    <textarea id="strengths" placeholder="Acknowledge the strengths of the employee..."></textarea>

                    <b>⚠ Areas for Improvement:</b>
                    <textarea id="weaknesses" placeholder="Areas that need improvement or correction..."></textarea>

                    <h3>✍ Employee's Response</h3>
                    <textarea id="staff-response" placeholder="The employee can respond to the manager's comments..."></textarea>

                    <h3>📅 Next Actions</h3>
                    <p><b>Improvement Suggestions:</b> <input type="text" id="action-plan" placeholder="E.g., skill training, process improvement..."></p>
                    <p><b>Response Deadline:</b> <input type="date" id="deadline"></p>

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
//                                document.getElementById("feedback-list").innerHTML = "";
                            }
                        })
                        .catch(error => console.error('Error:', error));
            } else {
                nameInput.value = "";
                document.getElementById("total-feedback").textContent = "N/A";
                document.getElementById("avg-rating").textContent = "N/A";
                document.getElementById("positive-feedback").textContent = "N/A";
                document.getElementById("negative-feedback").textContent = "N/A";
//                document.getElementById("feedback-list").innerHTML = "";
            }

            // Nếu đã chọn cả Position & Month/Year, thì lấy Overall Rating
            if (position && monthYear) {
                fetch('${pageContext.request.contextPath}/manager/formfeedbackmanager?position=' + position + '&monthYear=' + monthYear)
                        .then(response => response.json())
                        .then(data => {
                            document.getElementById("total-feedback").textContent = data.totalFeedback ?? "N/A";
                            document.getElementById("avg-rating").textContent = data.avgRating ?? "N/A";
                            document.getElementById("positive-feedback").textContent = data.positiveFeedback ? data.positiveFeedback + "%" : "N/A";
                            document.getElementById("negative-feedback").textContent = data.negativeFeedback ? data.negativeFeedback + "%" : "N/A";

                            const feedbackListElement = document.getElementById("feedback-list");
                            feedbackListElement.innerHTML = "";//xoa du lieu cu

//                                console.log("Data received:", data);
                            console.log("Feedback list:", data.feedbackList);
//                                console.log("Type of feedbackList:", typeof data.feedbackList);
//                                console.log("Is Array:", Array.isArray(data.feedbackList));

//                                console.log(typeof data.feedbackList[0].title);  // Phải là "string"
//                                console.log(typeof data.feedbackList[0].rate);   // Phải là "number"
//                                console.log(typeof data.feedbackList[0].date);   // Phải là "string"

                            if (Array.isArray(data.feedbackList) && data.feedbackList.length > 0) {
                                console.log("Rendering feedback list...");
                            } else {
                                console.log("Feedback list is empty!");
                            }
                            console.table(data.feedbackList);

                            if (data.feedbackList && data.feedbackList.length > 0) {
                data.feedbackList.forEach((fb, index) => {
//                    console.log(`Rendering row ` + fb.title);
                    index++;
                    feedbackListElement.innerHTML +=`<tr>
                        <td>`+ index +`</td>
                        <td>`+fb.title +`</td>
                        <td>`+fb.rate+`</td>
                        <td>`+fb.date+`</td>
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
</html>
