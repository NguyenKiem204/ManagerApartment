<%-- 
    Document   : demo
    Created on : Feb 11, 2025, 12:28:03 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="menumanager.jsp" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Bootstrap CRUD Data Table for Database with Modal Form</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <style>
            body {
                color: #566787;
                background: #ffa384;
                font-family: 'Varela Round', sans-serif;
                font-size: 20px;

            }
            .pagination {
                float: right;
                margin: 0 0 5px;
            }
            .pagination li a {
                border: none;
                font-size: 13px;
                min-width: 30px;
                min-height: 30px;
                color: #999;
                margin: 0 2px;
                line-height: 30px;
                border-radius: 2px !important;
                text-align: center;
                padding: 0 6px;
            }
            .pagination li a:hover {
                color: #666;
            }
            .pagination li.active a, .pagination li.active a.page-link {
                background: #03A9F4;
            }
            .pagination li.active a:hover {
                background: #0397d6;
            }
            .pagination li.disabled i {
                color: #ccc;
            }
            .pagination li i {
                font-size: 16px;
                padding-top: 6px
            }
            .hint-text {
                float: left;
                margin-top: 10px;
                font-size: 13px;
            }

            /* Modal styles */
            .modal .modal-dialog {
                max-width: 80%; /* Tăng kích thước modal */
                width: 80%;
                margin: auto;
            }
            .modal .modal-header, .modal .modal-body, .modal .modal-footer {
                padding: 20px 30px;
            }
            .modal .modal-content {
                border-radius: 15px;
                font-size: 14px;
            }
            .modal .modal-footer {
                background: #ecf0f1;
                border-radius: 0 0 3px 3px;
            }
            .modal .modal-title {
                display: inline-block;

            }
            .modal .form-control {
                border-radius: 2px;
                box-shadow: none;
                border-color: #dddddd;
            }
            .modal textarea.form-control {
                resize: vertical;
            }
            .modal .btn {
                border-radius: 15px;
                min-width: 100px;
            }
            .modal form label {
                font-weight: normal;
                width: 50%;

            }
            .search-box {
                position: relative;
                float: right;
                gap:5px;
                display: flex;
            }
            .search-box .input-group {
                min-width: 300px;

            }
            .search-box .input-group-addon, .search-box input,.search-box select {
                border-color: #ddd;
                border-radius: 0;
            }
            .search-box input,.search-box select {
                height: 34px;
                padding-right: 35px;
                background: #f4fcfd;
                border: none;
                border-radius: 2px !important;
            }
            .search-box input:focus,.search-box select:focus {
                background: #fff;
            }
            .search-box input::placeholder {
                font-style: italic;
            }
            .search-box .input-group-addon {
                min-width: 35px;
                border: none;
                background: transparent;
                position: absolute;
                right: 0;
                z-index: 9;
                padding: 6px 0;
            }
            .search-box i {
                color: #a0a5b1;
                font-size: 19px;
                position: relative;
                top: 2px;
            }
        </style>

    </head>
    <body>
        <div class="container-xl">                                     
            <div id="addRule" class="">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="/ManagerApartment/manager/regulations" method="POST">
                            <input type="hidden" name="_method" value="POST"/>

                            <div class="modal-header">                      
                                <h4 class="modal-title">Add New Regulations</h4>
                            </div>
                            <div class="modal-body">  
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">
                                        <p>${error}</p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Regulation Name</label>
                                    <input type="text" class="form-control" name="ruleName" required>
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea id="ruleDescription" 
                                              name="ruleDescription" 
                                              class="form-control" 
                                              required
                                              ></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Public Date</label>
                                    <input 
                                        type="text" 
                                        class="form-control" 
                                        name="publicDate"
                                        id="boughtOn" 
                                        placeholder="dd/MM/yyyy"
                                        required
                                        >
                                </div>
                            </div>

                            <div class="modal-footer">
                                <input type="submit" class="btn btn-success" value="Add">
                                <a href="/ManagerApartment/manager/regulations"
                                   class="btn btn-default"
                                   >Back</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>     
    </body>
    <!--    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>-->
    <script>

        document.addEventListener("DOMContentLoaded", function () {
            if (typeof CKEDITOR !== "undefined") {
                CKEDITOR.replace("ruleDescription", {
                    language: 'vi', // Ngôn ngữ tiếng Việt
                    entities: false, // Tắt chuyển đổi ký tự đặc biệt thành thực thể HTML
                    entities_latin: false, // Tắt chuyển đổi ký tự Latin thành thực thể HTML
                    ForceSimpleAmpersand: true // Giữ nguyên ký tự &
                });
            } else {
                console.error("CKEditor not loaded");
            }
        });

        $(document).ready(function () {
            $('#ruleDescription').summernote({
                height: 300,
                tabsize: 2,
                placeholder: "Nhập nội dung bài viết..."
            });
        });

//        document.addEventListener("DOMContentLoaded", function () {
//            flatpickr("#publicDate", {
//                dateFormat: "d/m/Y", // Định dạng hiển thị là DD-MM-YYYY
//                altInput: true,
//                altFormat: "d/m/Y", // Định dạng gửi đi là YYYY-MM-DD
//                allowInput: true
//            });
//        });

        document.addEventListener("DOMContentLoaded", function () {
            var modal = document.querySelector(".modal-dialog");
            if (modal) {
                modal.style.maxWidth = "100%";
                modal.style.width = "100%";
                modal.style.margin = "auto";
                modal.style.top = "50%";
               // modal.style.transform = "translate(15%)";
            }
        });
    </script>
</html>