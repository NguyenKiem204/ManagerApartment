<%-- 
    Document   : sidebar
    Created on : Feb 7, 2025, 2:26:08 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />

        <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="assets/css/pages/index.css">
        <link rel="stylesheet" href="assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #FFA384;
                margin: 0;
            }
            .container {
                max-width: 600px;
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
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: #555;
            }
            input, select, textarea {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            textarea {
                resize: vertical;
            }
            button {
                background-color: #ff9800;
                color: white;
                padding: 12px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                width: 100%;
                margin-top: 15px;
            }
            button:hover {
                background-color: #e68900;
            }
            .rating {
                display: flex;
                justify-content: center;
                flex-direction: row-reverse;
            }
            .rating input {
                display: none;
            }
            .rating label {
                font-size: 30px;
                color: #ccc;
                cursor: pointer;
                transition: color 0.3s;
            }
            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: #FFD700;
            }
            .result {
                text-align: center;
                font-size: 16px;
                margin-top: 10px;
                color: #333;
            }
            .error {
                color: red;
                font-size: 14px;
                display: none;
            }
        </style>
    </head>
    <body>
        <%@include file="menuowner.jsp" %>
            

            <div id = "main">
                <div class="container" id="feedbackContainer">
                    <h2>Resident Feedback Form</h2>
                    <form action="feedback" method="POST">
                        <!--id="feedbackForm"-->
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" placeholder="Enter the title" required>
                        <span class="error" id="titleError">Please enter a title</span>

                        <label for="staff">Feedback for</label>
                        <select id="staff" name="staff">
                            <option value="" disabled selected>Select a staff member</option>
                            <c:forEach var="role" items="${listrole}" begin="1" end="4">
                                <option value="${role.roleID}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                        <span class="error" id="staffError">Please select a staff member</span>

                        <label>Rating</label>
                        <div class="rating">
                            <input type="radio" id="star5" name="rating" value="5"><label for="star5">★</label>
                            <input type="radio" id="star4" name="rating" value="4"><label for="star4">★</label>
                            <input type="radio" id="star3" name="rating" value="3"><label for="star3">★</label>
                            <input type="radio" id="star2" name="rating" value="2"><label for="star2">★</label>
                            <input type="radio" id="star1" name="rating" value="1"><label for="star1">★</label>
                        </div>
                        <p class="result">You have not rated yet</p>
                        <span class="error" id="ratingError">Please select a rating</span>

                        <label for="feedback">Feedback</label>
                        <textarea id="feedback" name="description" rows="4" placeholder="Write your feedback here..." required></textarea>
                        <span class="error" id="feedbackError">Please enter your feedback</span>

                        <button type="submit">Submit Feedback</button>
                    </form>
                </div>

                <div class="container success-message" id="successMessage">
                    <h2>Thank You!</h2>
                    <p>Your feedback has been submitted successfully.</p>
                    <button onclick="window.location.reload()">Submit Another Feedback</button>
                </div>

                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const form = document.getElementById("feedbackForm");
                        const resultText = document.querySelector(".result");
                        const ratingInputs = document.querySelectorAll(".rating input");
                        const feedbackContainer = document.getElementById("feedbackContainer");
                        const successMessage = document.getElementById("successMessage");

                        // Ẩn success message ban đầu
                        successMessage.style.display = "none";

                        // Xử lý chọn rating
                        ratingInputs.forEach(input => {
                            input.addEventListener("change", function () {
                                resultText.innerText = "You rated: " + this.value + " stars";
                            });
                        });

                        // Xử lý submit form
                        form.addEventListener("submit", function (event) {
                            event.preventDefault(); // Ngăn chặn gửi form thật

                            let isValid = true;

                            function validateField(id, errorId) {
                                const field = document.getElementById(id);
                                const error = document.getElementById(errorId);
                                if (!field.value.trim()) {
                                    error.style.display = "block";
                                    isValid = false;
                                } else {
                                    error.style.display = "none";
                                }
                            }

                            validateField("title", "titleError");
                            validateField("staff", "staffError");
                            validateField("feedback", "feedbackError");

                            // Kiểm tra xem có rating được chọn không
                            const ratingSelected = [...ratingInputs].some(input => input.checked);
                            document.getElementById("ratingError").style.display = ratingSelected ? "none" : "block";
                            isValid = isValid && ratingSelected;

                            // Nếu hợp lệ, hiển thị thông báo thành công
                            if (isValid) {
                                feedbackContainer.style.display = "none";
                                successMessage.style.display = "block";
                            }
                        });
                    });
                </script>
            </div>
            <script src="assets/js/bootstrap.bundle.min.js"></script>

            <script src="assets/vendors/apexcharts/apexcharts.js"></script>
            <script src="assets/js/pages/dashboard.js"></script>

            <script src="assets/js/main.js"></script>
    </body>
</html>
