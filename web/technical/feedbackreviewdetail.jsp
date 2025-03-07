<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <h2>📌 Feedback Review</h2>
                <h3>👤 Employee Information</h3>
                <p><b>Full Name:</b> nhan vien A</p>
                <p><b>Position:</b> accountant</p>

                <h3>📊 Overall Ratings</h3>
                <table>
                    <tr>
                        <th>Total Feedback</th>
                        <th>Average Rating (⭐)</th>
                        <th>Positive Feedback (%)</th>
                        <th>Negative Feedback (%)</th>
                    </tr>
                    <tr>
                        <td>10</td>
                        <td>4</td>
                        <td>80</td>
                        <td>20</td>
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
                    <tr>
                        <td>1</td>
                        <td>aaaa</td>
                        <td>4</td>
                        <td>06/03</td>
                    </tr>
                </table>

                <h3>📌 Manager's Comments</h3>
                <b>💡 Strengths:</b>
                <p>good</p>

                <b>⚠ Areas for Improvement:</b>
                <p>improvement</p>

                <h3>📩 Staff Response</h3>
                <form action="SubmitStaffResponseServlet" method="post">
                    <input type="hidden" name="staffId" value="3" />
                    <textarea name="staffResponse" placeholder="Write your response here..."></textarea>
                    <button class="submit-btn" type="submit">Submit Response</button>
                </form>
            </div>
        </div>
    </body>
</html>
