<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Feedback Overview</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #fff3e0;
            }
            .container {
                width: 80%;
                margin: 20px auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                color: #e67e22;
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #e67e22;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #fdf2e9;
            }
            .btn {
                display: inline-block;
                padding: 8px 16px;
                margin-top: 10px;
                background-color: #e67e22;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            }
            .btn:hover {
                background-color: #d35400;
            }
        </style>
    </head>
    <body>
        <%@include file="home.jsp" %>
        <div id="main">
            <div class="container">
                <h2>Feedback Overview</h2>
                <table>
                    <tr>
                        <th>Month</th>
                        <th>Total Feedback</th>
                        <th>Positive</th>
                        <th>Negative</th>
                        <th>Action</th>
                    </tr>
                    <tr>
                        <td>January 2025</td>
                        <td>20</td>
                        <td>15</td>
                        <td>5</td>
                        <td><a href="#" class="btn">View Details</a></td>
                    </tr>
                    <tr>
                        <td>February 2025</td>
                        <td>25</td>
                        <td>18</td>
                        <td>7</td>
                        <td><a href="#" class="btn">View Details</a></td>
                    </tr>
                    <tr>
                        <td>March 2025</td>
                        <td>30</td>
                        <td>22</td>
                        <td>8</td>
                        <td><a href="#" class="btn">View Details</a></td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>
