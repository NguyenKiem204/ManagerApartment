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

        <style>
            body {
                color: #566787;
                background: #ffa384;
                font-family: 'Varela Round', sans-serif;
                font-size: 13px;
            }
            .table-responsive {
                margin: 30px 0;
                width: 120%;
            }
            .table-wrapper {
                background: #fff;
                padding: 20px 25px;
                border-radius: 15px;
                min-width: 1000px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {
                padding-bottom: 15px;
                background:#6c757d ;
                color: #fff;
                padding: 16px 30px;
                min-width: 100%;
                margin: -20px -25px 10px;
                border-radius: 15px;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
            }
            .table-title .btn-group {
                float: right;
            }
            .table-title .btn {
                color: #fff;
                float: right;
                font-size: 13px;
                border: none;
                min-width: 50px;
                border-radius: 15px;
                border: none;
                outline: none !important;
                margin-left: 10px;
            }
            .table-title .btn i {
                float: left;
                font-size: 21px;
                margin-right: 5px;
            }
            .table-title .btn span {
                float: left;
                margin-top: 2px;
            }
            table.table tr th, table.table tr td {
                border-color: #e9e9e9;
                padding: 12px 15px;
                vertical-align: middle;
            }
            table.table tr th:first-child {
                width: 60px;
            }
            table.table tr th:last-child {
                width: 100px;
            }
            table.table-striped tbody tr:nth-of-type(odd) {
                background-color: #fcfcfc;
            }
            table.table-striped.table-hover tbody tr:hover {
                background: #f5f5f5;
            }
            table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }
            table.table td:last-child i {
                opacity: 0.9;
                font-size: 22px;
                margin: 0 5px;
            }
            table.table td a {
                font-weight: bold;
                color: #566787;
                display: inline-block;
                text-decoration: none;
                outline: none !important;
            }
            table.table td a:hover {
                color: #2196F3;
            }
            table.table td a.edit {
                color: #FFC107;
            }
            table.table td a.delete {
                color: #F44336;
            }
            table.table td i {
                font-size: 19px;
            }
            table.table .avatar {
                border-radius: 50%;
                vertical-align: middle;
                margin-right: 10px;
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
            /* Custom checkbox */

            /* Modal styles */
            .modal .modal-dialog {
                max-width: 1000px; /* Mặc định là 400px, tăng lên 600px */
                width: 80%;
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
                        <form action="/ManagerApartment/manager/rule" method="POST">
                            <input type="hidden" name="_method" value="POST"/>

                            <div class="modal-header">                      
                                <h4 class="modal-title">Add New Rule</h4>
                            </div>
                            <div class="modal-body">                    
                                <div class="form-group">
                                    <label>Rule Name</label>
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
                                    <input type="date" class="form-control" name="publicDate" required>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <input type="submit" class="btn btn-success" value="Add">
                                <a href="/ManagerApartment/manager/rule"
                                   class="btn btn-default"
                                   >Back</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>     
    </body>
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
    </script>
</html>

