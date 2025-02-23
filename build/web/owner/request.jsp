<%-- 
    Document   : request
    Created on : Feb 11, 2025, 2:48:04 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resident Feedback Form</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
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
            .note-btn-group {
                display: flex !important;
            }
            .note-toolbar{
                display: flex !important;
            }

            /*CSS cho gợi ý tên department*/
            .suggestions {
                border: 1px solid #ccc;
                max-height: 200px;
                overflow-y: auto;
                list-style: none;
                padding: 0;
                position: absolute;
                background: white;
                width: calc(100% - 20px);
                z-index: 1000;
            }
            .suggestions li {
                padding: 10px;
                cursor: pointer;
            }
            .suggestions li:hover {
                background: #f0f0f0;
            }
        </style>
    </head>
    <body>
        <%@include file="menuowner.jsp" %>
        <div id = "main">
            <div class="container">
                <h2>Resident Request Form</h2>
                <form action="request" method="POST">
                    <label for="apartment">Apartment</label>
                    <input type="text" id="apartment" name="apartment" placeholder="Enter your apartment" required>
                    <span class="error" id="apartmentError">Please enter your apartment</span>

                    <ul id="apartmentSuggestions" class="suggestions"></ul>

                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" placeholder="Enter the title" required>
                    <span class="error" id="titleError">Please enter a title</span>

                    <label for="service">Service Type</label>
                    <select id="service" name="service">
                        <option value="" disabled selected>Select a service type</option>
                        <c:forEach var="typerq" items="${listtyperq}">
                            <option value="${typerq.typeRqID}">${typerq.typeName}</option>
                        </c:forEach>
                    </select>
                    <span class="error" id="serviceError">Please select a service type</span>

                    <div class="mb-3">
                        <label for="detail" class="form-label"><i class="fas fa-file-alt"></i> Request</label>
                        <textarea id="request" name="description" class="form-control"></textarea>
                    </div>

                    <span class="error" id="feedbackError">Please enter your feedback</span>
                    <c:if test="${not empty msg}">
                        <p style="color: green">${msg}</p>
                    </c:if>
                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>

                    <button type="submit">Submit Request</button>
                </form>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("feedbackForm");
                const resultText = document.querySelector(".result");
                const ratingInputs = document.querySelectorAll(".rating input");

                // Form validation
                form.addEventListener("submit", function (event) {
                    event.preventDefault();
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

                    validateField("apartment", "apartmentError");
                    validateField("title", "titleError");
                    validateField("service", "serviceError");
                    validateField("feedback", "feedbackError");

                    if (isValid) {
                        alert("Feedback submitted successfully!");
                        form.reset();
                        resultText.innerText = "You have not rated yet";
                    }
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                $('#request').summernote({
                    height: 300,
                    tabsize: 2,
                    placeholder: "Write your request here..."
                });
            });
        </script>

        <script>
            $(document).ready(function () {
                $("#apartment").on("input", function () {
                    let query = $(this).val().trim();
                    if (query.length >= 1) {
                        $.ajax({
                            url: "GetApartmentServlet",
                            method: "GET",
                            data: {search: query},
                            success: function (data) {
                                console.log(data);
                                let suggestions = $("#apartmentSuggestions");
                                suggestions.empty(); // Xóa gợi ý cũ
                                data.forEach(function (apartment) {
                                    suggestions.append(`<li>${apartment}</li>`);
                                });

                                error: function (xhr, status, error) {
                                    console.error("AJAX error: " + status + " - " + error);
                                };
                                $(".suggestions li").click(function () {
                                    $("#apartment").val($(this).text());
                                    suggestions.empty(); // Ẩn danh sách sau khi chọn
                                });
                            }
                        });
                    } else {
                        $("#apartmentSuggestions").empty();
                    }
                });

                $(document).click(function (e) {
                    if (!$(e.target).closest("#apartmentSuggestions, #apartment").length) {
                        $("#apartmentSuggestions").empty();
                    }
                });
            });
        </script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>
</html>
