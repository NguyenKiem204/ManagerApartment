<%-- 
    Document   : demo
    Created on : Feb 11, 2025, 12:28:03 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/manager\menumanager.jsp" %>
<!DOCTYPE html>
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
                font-size: 13px;
            }
            .table-responsive {
                margin: 30px 0;
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
            .custom-checkbox {
                position: relative;
            }
            .custom-checkbox input[type="checkbox"] {
                opacity: 0;
                position: absolute;
                margin: 5px 0 0 3px;
                z-index: 9;
            }
            .custom-checkbox label:before{
                width: 18px;
                height: 18px;
            }
            .custom-checkbox label:before {
                content: '';
                margin-right: 10px;
                display: inline-block;
                vertical-align: text-top;
                background: white;
                border: 1px solid #bbb;
                border-radius: 2px;
                box-sizing: border-box;
                z-index: 2;
            }
            .custom-checkbox input[type="checkbox"]:checked + label:after {
                content: '';
                position: absolute;
                left: 6px;
                top: 3px;
                width: 6px;
                height: 11px;
                border: solid #000;
                border-width: 0 3px 3px 0;
                transform: inherit;
                z-index: 3;
                transform: rotateZ(45deg);
            }
            .custom-checkbox input[type="checkbox"]:checked + label:before {
                border-color: #03A9F4;
                background: #03A9F4;
            }
            .custom-checkbox input[type="checkbox"]:checked + label:after {
                border-color: #fff;
            }
            .custom-checkbox input[type="checkbox"]:disabled + label:before {
                color: #b8b8b8;
                cursor: auto;
                box-shadow: none;
                background: #ddd;
            }
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
        </style>
        <script>
            $(document).ready(function () {
                // Activate tooltip
                $('[data-toggle="tooltip"]').tooltip();
                // Select/Deselect checkboxes
                var checkbox = $('table tbody input[type="checkbox"]');
                $("#selectAll").click(function () {
                    if (this.checked) {
                        checkbox.each(function () {
                            this.checked = true;
                        });
                    } else {
                        checkbox.each(function () {
                            this.checked = false;
                        });
                    }
                });
                checkbox.click(function () {
                    if (!this.checked) {
                        $("#selectAll").prop("checked", false);
                    }
                });
            });
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


        </script>


    </script>
</head>
<body>
    <div class="container-xl">
        <div class="table-responsive">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Manage Rule</h2>
                        </div>
                        <div class="col-sm-6">
                            <a href="#editRuleModal"
                               class="btn btn-success"
                               data-toggle="modal"
                               onclick="setEditRule('add', '', '', '', '')"
                               >
                                <i class="material-icons">&#xE147;</i> <span>Add New Rule</span>
                            </a>
                            <a href="delete?ruleId${rule.ruleID}" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>                    
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>
                                <span class="custom-checkbox">
                                    <input type="checkbox" id="selectAll">
                                    <label for="selectAll"></label>
                                </span>
                            </th>
                            <th>Rule Name</th>
                            <th>Rule Description</th>
                            <th>Public Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rule" items="${rulesList}">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="checkbox1" name="options[]" value="1">
                                        <label for="checkbox1"></label>
                                    </span>
                                </td>
                                <td>${rule.ruleName}</td>
                                <td>${rule.ruleDescription}</td>
                                <td>${rule.publicDate}</td>
                                <td>
                                    <a href="#editRuleModal"
                                       onclick="setEditRule('edit', '${rule.ruleID}',
                                       '${fn:replace(rule.ruleName, "'", "\\'")}',
                                       '${fn:replace(rule.ruleDescription, "'", "\\'")}',
                                       '${rule.publicDate}')"
                                       class="edit"
                                       data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
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
                <div class="clearfix">
                    <div class="hint-text">Showing
                        <b>
                            <c:choose>
                                <c:when test="${pageSize > totalRules}">
                                    ${totalRules}
                                </c:when>
                                <c:otherwise>
                                    ${pageSize}
                                </c:otherwise>
                            </c:choose>
                        </b>
                        out of <b>${totalRules}</b> entries</div>
                    <ul class="pagination">
                        <li class="page-item
                            <c:if test="${currentPage eq 1}">disabled</c:if>">
                                <a href="#" class="page-link">Previous</a>
                            </li>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item  <c:if test="${currentPage eq i}">active</c:if>">
                                <a href="?page=${i}" class="page-link">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item
                            <c:if test="${currentPage eq totalPages}">disabled</c:if>">
                                <a href="#" class="page-link">Next</a>
                            </li>


                        </ul>
                    </div>
                </div>
            </div>        
        </div>

        <div id="editAddModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="rule-management" method="POST">
                        <div class="modal-header">						
                            <h4 class="modal-title">Add Rule</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">					
                            <div class="form-group">
                                <label>Rule Name</label>
                                <input type="text" class="form-control" name="EditRuleName" required>
                            </div>
                            <div class="form-group">
                                <label>Rule Description</label>
                                <textarea name="EditRuleDescription" class="form-control" required></textarea>
                            </div>
                            <div class="form-group">
                                <label>Public Date</label>
                                <input type="date" class="form-control" name="EditPublicDate" required>
                            </div>					
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" name="_method" value="POST"/>
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-success" value="Add Rule">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Edit Modal HTML -->
        <div id="editRuleModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form 
                        action="rule-management"
                        method="POST"
                        >
                        <input id="_method" type="hidden" name="_method"/>
                        <div class="modal-header">						
                            <h4 id="editRuleTitle" class="modal-title"></h4>
                            <button type="button" 
                                    class="close" 
                                    data-dismiss="modal" 
                                    aria-hidden="true" 
                                    onclick="closeEditRule()"
                                    >
                                &times;
                            </button>
                        </div>
                        <div class="modal-body">
                            <input 
                                type="hidden"
                                id="editRuleID"
                                name="editRuleID"
                                />
                            <div class="form-group">
                                <label>Rule Name</label>
                                <input type="text" 
                                       class="form-control" 
                                       value=""
                                       id="editRuleName"
                                       name="editRuleName"
                                       required>
                            </div>
                            <div class="form-group">
                                <label>Rule Description</label>
                                <textarea 
                                    id="editRuleDescription" 
                                    name="editRuleDescription" 
                                    type="text" 
                                    class="form-control" 
                                    required
                                    ></textarea>
                            </div>
                            <div class="form-group">
                                <label>Public Date</label>
                                <input type="date" 
                                       class="form-control"
                                       value=""
                                       id="editPublicDate"
                                       name="editPublicDate"
                                       required>
                            </div>
                            <!-- TODO: add them list Staff -->
                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-info" value="Save"  >
                            <input type="button" 
                                   class="btn btn-default" 
                                   data-dismiss="modal" 
                                   onclick="closeEditRule()"
                                   value="Cancel">
                        </div>
                    </form>
                </div>
            </div>
        </div>



        <!-- Delete Mod    al HTML -->
        <div id="delete    RuleModal" class="modal fade">
            <div class=        "modal-dialog">
                <div cl            ass="modal-content">
                    <fo                rm action="rule-management" method="POST">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input
                            type="hidden"
                            name="deleteRuleID"
                            id="deleteRuleID"
                            value="DELETE"
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
                            <input type="submit" class="btn btn-danger" onclick="deleteRule(${rule.ruleID}) value ="Delete">
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
    </di                    v>
    <script>
        function deleteRule(ruleId) {
            if (confirm("Are you sure you want to delete this rule?")) {
                fetch(`/delete?ruleId=${ruleId}`, {method: 'DELETE'})
                        .then(response => {
                            if (response.ok) {
                                alert("Rule deleted successfully!");
                                location.reload(); // Reload page after deletion
                            } else {
                                alert("Failed to delete rule.");
                            }
                        })
                        .catch(error => console.error("Error:", error));
            }
        }
    </script>
</body>
</html>

