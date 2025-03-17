<%-- 
    Document   : demo
    Created on : Feb 11, 2025, 12:28:03 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/manager/menumanager.jsp" %>
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
        <style>
            body {
                color: #566787;
                background: #ffa384;
                font-family: 'Varela Round', sans-serif;
                font-size: 20px;
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
                background:#F44336 ;
                color: #fff;
                padding: 16px 30px;
                min-width: 100%;
                margin: -20px -25px 10px;
                border-radius: 15px;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
                color: white;
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
                
                width: 100%;
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
        <script>

            function setEditRule(type, ruleID, ruleName, ruleDescription, publicDate) {
                // event.preventDefault();
                console.log("Debug:", {type, ruleID, ruleName, ruleDescription, publicDate});
                if (type === 'edit') {
                    document.getElementById("editRuleID").value = ruleID;
                    document.getElementById("editRuleName").value = ruleName;
                    document.getElementById("editRuleDescription").value = ruleDescription;
                    document.getElementById("editPublicDate").value = publicDate;
                    document.getElementById("_method").value = 'PUT';
                    document.getElementById("editRuleTitle").innerHTML = 'Edit Rule';
                } else {
                    document.getElementById("_method").value = 'POST';
                    document.getElementById("editRuleTitle").innerHTML = 'Add Rule';
                }
                $("#editRuleModal").modal("show");
            }




            function closeEditRule() {
                event.preventDefault();
                $("#editRuleModal").modal("hide");
            }


            function showDeleteRule(ruleID) {
                event.preventDefault();
                document.getElementById("deleteRuleID").value = ruleID;
                $("#deleteRuleModal").modal("show");
            }

            function closeDeleteRule() {
                event.preventDefault();
                $("#deleteRuleModal").modal("hide");
            }

            function submitForm() {
                document.getElementById("filterAndPagination").submit();
            }

        </script>


    </script>
</head>
<body>
    <div class="container-xl">
        <div class="table-responsive">
            <div class="table-wrapper">
                <form id="filterAndPagination" 
                      action="/ManagerApartment/manager/regulations" 
                      method="POST"
                      >
                    <input type="hidden" name="_method" value="SEARCH"/>

                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2>Manage Rule</h2>
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <a href="/ManagerApartment/manager/rule/add"
                                   class="btn btn-success me-3"
                                   >
                                    <i class="material-icons">&#xE147;</i> <span>Add New Regulations</span>
                                </a>
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
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Rule Name</th>
                                <th>Rule Description</th>
                                <th>Public Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rule" items="${rulesList}" varStatus= "loop">
                                <tr>
                                    <td>${loop.index+1}</td>
                                    <td>${rule.ruleName}</td>
                                    <td>${rule.ruleDescription}</td>
                                    <td>${rule.formattedPublicDate}</td>
                                    <td>
                                        <a href="/ManagerApartment/manager/rule/edit?id=${rule.ruleID}"
                                           class="edit"
                                           >
                                            <i class="material-icons" 
                                               data-toggle="tooltip" 
                                               title="Edit"
                                               >&#xE254;
                                            </i>
                                        </a>
                                        <a href="#deleteRuleModal"
                                           onclick="showDeleteRule('${rule.ruleID}')"
                                           class="delete"
                                           data-toggle="modal">
                                            <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                        </a>
                                    </td>
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
                                               
        <!-- Delete Modal HTML -->
        <div id="deleteRuleModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="/ManagerApartment/manager/regulations" method="POST">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input
                            type="hidden"
                            name="deleteRuleID"
                            id="deleteRuleID"
                            value=""
                            />
                        <div class="modal-header">                      
                            <h4 class="modal-title">Delete Rule</h4>
                            <button type="button"
                                    class="close"
                                    data-dismiss="modal"
                                    aria-hidden="true"
                                    onclick="closeDeleteRule()"
                                    >&times;</button>
                        </div>
                        <div class="modal-body">                    
                            <p>Are you sure you want to delete these Rules?</p>
                            <p class="text-warning"><small>This action cannot be undone.</small></p>
                        </div>
                        <div class="modal-footer">
                            <input type="submit" 
                                   class="btn btn-danger" 
                                   value="Delete">
                            <input
                                type="button"
                                class="btn btn-default"
                                data-dismiss="modal"
                                value="Cancel"
                                onclick="closeDeleteRule()"
                                >
                        </div>
                    </form>
                </div>
            </div>
        </div>
</body>
</html>

