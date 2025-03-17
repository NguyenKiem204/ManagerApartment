<%-- 
    Document   : Regulations
    Created on : Mar 5, 2025, 2:49:30 PM
    Author     : Hoang-Tran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/tenant/menutenant.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Bootstrap Table with Search Column Feature</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            body {
                color: #566787;
                background: #FFA384;
                font-family: 'Roboto', sans-serif;
            }
            .table-responsive {
                margin: 30px 0;
                overflow-x: visible !important;
                width: 120%;
            }
            .table-wrapper {
                min-width: 1000px;
                background: #fff;
                padding: 20px 25px;
                border-radius: 3px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {
                color: #fff;
                background: #E34724;
                padding: 16px 25px;
                margin: -20px -25px 10px;
                border-radius: 3px 3px 0 0;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
                color: #f4fcfd;
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
            table.table {
                table-layout: fixed;
                margin-top: 15px;
            }
            table.table tr th, table.table tr td {
                border-color: #e9e9e9;
            }
            table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }
            table.table th:first-child {
                width: 60px;
            }
            table.table th:last-child {
                width: 120px;
            }
            table.table td a {
                color: #a0a5b1;
                display: inline-block;
                margin: 0 5px;
            }
            table.table td a.view {
                color: #03A9F4;
            }
            table.table td a.edit {
                color: #FFC107;
            }
            table.table td a.delete {
                color: #E34724;
            }
            table.table td i {
                font-size: 19px;
            }
            .wrapper{
                justify-content: center;
                align-items: center;
            }

            .page-link {
                position: relative;
                display: block;
                color: #673AB7 !important;
                text-decoration: none;
                background-color: #fff;
                border: 1px solid #673AB7 !important;
            }


            .page-link:hover {
                z-index: 2;
                color: #fff !important;
                background-color: #673ab7;
                border-color: #024dbc;
            }


            .page-link:focus {
                z-index: 3;
                outline: 0;
                box-shadow: none;
            }

            .show-on-top {
                position: absolute;
                transform: translateY(-100%); /* Sổ lên thay vì xuống */
                right: 0; /* Canh phải */
                z-index: 1050; /* Đảm bảo hiển thị trên các phần khác */
                min-width: 150px; /* Định kích thước tối thiểu */
            }
        </style>
        <script>
            function submitForm() {
                document.getElementById("filterAndPagination").submit();
            }

        </script>
    </head>
    <body>
        <div class="container-lg">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <form id="filterAndPagination" 
                          action="/ManagerApartment/tenant/viewregulations" 
                          method="POST" 
                          class=""
                          >
                        <div class="table-title">
                            <div class="row">
                                <div class="col-sm-6">
                                    <h2><b>Regulations</b></h2>
                                </div>
                                <div class="col-sm-6  d-flex justify-content-end">

                                    <div class="search-box">
                                        <div class="input-group">								
                                            <input type="text" 
                                                   id="search"
                                                   name="search"
                                                   class="form-control" 
                                                   value="${searchName}"
                                                   placeholder="Search"
                                                   />
                                            <button type="submit" class="input-group-addon">
                                                <i class="material-icons">&#xE8B6;</i>
                                            </button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th style="width: 20%;">Regulations Name</th>
                                    <th style="width: 60%;">Description</th>
                                    <th>Public Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="rule" items = "${rulesList}" varStatus= "loop">

                                    <tr>
                                        <td>${loop.index+1}</td>
                                        <td>${rule.ruleName}</td>
                                        <td>${rule.ruleDescription}</td>
                                        <td>${rule.formattedPublicDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- Pagination -->
                        <div class="d-flex justify-content-end mt-3">
                            <select name="page" 
                                    id="pageSelector" 
                                    class="form-control w-auto d-inline" 
                                    onchange="submitForm()">
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <option value="${i}" ${pageNumber eq i ? 'selected' : ''}>
                                        Page ${i}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>          
</body>
</html>