<%-- 
    Document   : manage-urls
    Created on : Mar 2, 2025, 4:13:39 PM
    Author     : nkiem
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Filter Mapping</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
            }
            form {
                margin-bottom: 20px;
                padding: 15px;
                border: 1px solid #ddd;
                background-color: #f9f9f9;
            }
            .form-group {
                margin-bottom: 10px;
            }
            label {
                display: inline-block;
                width: 100px;
            }
            input[type="text"] {
                padding: 5px;
                width: 300px;
            }
            button {
                padding: 5px 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
            }
            .delete-btn {
                background-color: #f44336;
            }
            .url-input-container {
                margin-bottom: 10px;
            }
            .url-patterns-container {
                margin-left: 100px;
            }
            .generate-btn {
                background-color: #2196F3;
                margin-top: 20px;
            }
            .reload-btn {
                background-color: #FF9800;
                margin-top: 20px;
            }
            .notification {
                color: green;
                padding: 10px;
                margin-bottom: 15px;
                background-color: #e8f5e9;
                border-left: 4px solid green;
            }
        </style>
        <script>
            function addUrlPatternField() {
                const container = document.getElementById('urlPatternsContainer');
                const newField = document.createElement('div');
                newField.className = 'url-input-container';
                newField.innerHTML = '<input type="text" name="urlPatterns" placeholder="URL Pattern" required> ' +
                        '<button type="button" onclick="this.parentNode.remove()" class="delete-btn">Xóa</button>';
                container.appendChild(newField);
            }
        </script>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container" style="background-color: white">
                <h1>Quản lý Filter Mapping</h1>

                <c:if test="${param.success == 'true'}">
                    <div class="notification">Filter mapping đã được thêm thành công!</div>
                </c:if>

                <c:if test="${param.deleted == 'true'}">
                    <div class="notification">Filter mapping đã được xóa thành công!</div>
                </c:if>

                <c:if test="${not empty sessionScope.reloadMessage}">
                    <div class="notification">${sessionScope.reloadMessage}</div>
                    <% session.removeAttribute("reloadMessage"); %>
                </c:if>

                <form method="post" action="manage-urls">
                    <input type="hidden" name="action" value="add">

                    <div class="form-group">
                        <label for="filterName">Tên Filter:</label>
                        <select id="filterName" name="filterName" required>
                            <option value="ManagerFilter">ManagerFilter</option>
                            <option value="AccountantFilter">AccountantFilter</option>
                            <option value="OwnerFilter">OwnerFilter</option>
                            <option value="ServiceFilter">ServiceFilter</option>
                            <option value="TechnicalFilter">TechnicalFilter</option>
                            <option value="TenantFilter">TenantFilter</option>
                            <option value="AdministrativeFilter">AdministrativeFilter</option>
                            <!--<option value="BlockJspHtmlFilter">BlockJspHtmlFilter</option>-->
                        </select>
                    </div>

                    <div class="form-group">
                        <label>URL Patterns:</label>
                        <div id="urlPatternsContainer" class="url-patterns-container">
                            <div class="url-input-container">
                                <input type="text" name="urlPatterns" placeholder="URL Pattern" required>
                            </div>
                        </div>
                        <button type="button" onclick="addUrlPatternField()" style="margin-left: 100px;">Thêm URL Pattern</button>
                    </div>

                    <button type="submit" style="margin-left: 100px; margin-top: 15px;">Lưu Filter Mapping</button>
                </form>

                <h2>Filter Mappings Hiện Tại</h2>

                <table>
                    <tr>
                        <th>ID</th>
                        <th>Tên Filter</th>
                        <th>URL Patterns</th>
                        <th>Thao tác</th>
                    </tr>

                    <c:forEach items="${filterMappings}" var="mapping">
                        <tr>
                            <td>${mapping.id}</td>
                            <td>${mapping.filterName}</td>
                            <td>
                                <ul>
                                    <c:forEach items="${mapping.urlPatterns}" var="pattern">
                                        <li>${pattern}</li>
                                        </c:forEach>
                                </ul>
                            </td>
                            <td>
                                <form method="post" action="manage-urls"
                                      onsubmit="return confirm('Bạn có chắc chắn muốn xóa filter mapping này?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="urlId" value="${mapping.id}">
                                    <button type="submit" class="delete-btn">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>

</html>