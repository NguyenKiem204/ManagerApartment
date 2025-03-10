<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff Feedback Review</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <style>
            .container {
                max-width: 800px;
                margin: auto;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2, h3 {
                color: #e67e22;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            th, td {
                border: 1px solid #e67e22;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #e67e22;
                color: white;
            }
            textarea {
                width: 100%;
                height: 80px;
                border: 1px solid #e67e22;
                border-radius: 4px;
                padding: 10px;
            }
            .submit-btn {
                background-color: #e67e22;
                color: white;
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                margin-top: 10px;
                border-radius: 4px;
            }
            .submit-btn:hover {
                background-color: #d35400;
            }

            #description-content {
                border: 2px solid #ddd;
                padding: 15px;
                border-radius: 5px;
                background-color: #f9f9f9;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>📌 Feedback Review</h2>
                <h3>👤 Employee Information</h3>
                <p><b>Full Name:</b> ${managerFb.staff.fullName}</p>
                <p><b>Position:</b> ${managerFb.staff.role.roleName}</p>

                <h3>📊 Overall Ratings</h3>
                <table>
                    <tr>
                        <th>Total Feedback</th>
                        <th>Average Rating (⭐)</th>
                        <th>Positive Feedback (%)</th>
                        <th>Negative Feedback (%)</th>
                    </tr>
                    <tr>
                        <td>${managerFb.totalFeedback}</td>
                        <td>${managerFb.avgRating}</td>
                        <td>${managerFb.positivePercentage}</td>
                        <td>${managerFb.negativePercentage}</td>
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
                    <c:forEach var="fb" items="${listFb}" varStatus="i">
                        <tr>
                            <td>${i.index + 1}</td>
                            <td>${fb.title}</td>
                            <td>${fb.rate}</td>
                            <td><fmt:formatDate value="${fb.formattedDate}" pattern="dd/MM/yyyy" /></td>
                        </tr>
                    </c:forEach>
                </table>

                <h3>📌 Manager's Comments</h3>
                <b>💡 Strengths:</b>
                <div id="description-content">
                    <p>${managerFb.strengths}</p>
                </div>

                <b>⚠ Areas for Improvement:</b>
                <div id="description-content">
                    <p>${managerFb.weaknesses}</p>
                </div>
                <h3>📅 Next Actions</h3>
                <b>Improvement Suggestions:</b>
                <div id="description-content">
                    <p>${managerFb.actionPlan}</p>
                </div>
                <b>Response Deadline:</b> ${managerFb.deadline}

                <h3>📩 Staff Response</h3>

                <c:if test="${sessionScope.staff.role.roleID != 1}">
                    <c:set var="now" value="<%= java.time.LocalDate.now() %>" />

                    <c:choose>
                        <%-- Nếu chưa phản hồi và còn trong thời hạn deadline --%>
                        <c:when test="${empty managerFb.staffResponse and now.isBefore(managerFb.deadline.plusDays(1))}">
                            <button id="toggleResponseForm" class="toggle-btn">➕ Add Response</button>
                            <div id="responseForm" style="display: none;">
                                <form action="staffresponsefeedback" method="post">
                                    <input type="hidden" name="managerFeedbackId" value="${managerFb.managerFeedbackId}" />
                                    <textarea name="staffResponse" placeholder="Write your response here..."></textarea>
                                    <button class="submit-btn" type="submit">Submit Response</button>
                                </form>
                            </div>
                        </c:when>

                        <%-- Nếu chưa phản hồi nhưng đã quá hạn deadline --%>
                        <c:when test="${empty managerFb.staffResponse and now.isAfter(managerFb.deadline)}">
                            <p class="error-message">⚠ Deadline for response has passed. You can no longer submit feedback.</p>
                        </c:when>

                        <%-- Nếu đã phản hồi trong thời hạn deadline --%>
                        <c:otherwise>
                            <p class="success-message">✅ You have already responded to this feedback.</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${sessionScope.staff.role.roleID == 1}">
                    <c:choose>
                        <c:when test="${not empty managerFb.staffResponse}">
                            <p>${managerFb.staffResponse}</p>
                        </c:when>
                        <c:otherwise>
                            <p><i>None</i></p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <script>
                    document.getElementById("toggleResponseForm")?.addEventListener("click", function () {
                        let form = document.getElementById("responseForm");
                        form.style.display = form.style.display === "none" ? "block" : "none";
                    });
                </script>
            </div>
        </div>
    </body>
</html>
