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
            .edit-btn {
                background-color: #2196F3;
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
            .error {
                color: red;
                padding: 10px;
                margin-bottom: 15px;
                background-color: #ffebee;
                border-left: 4px solid red;
            }
            .input-error {
                border: 1px solid red;
            }
            .invalid-feedback {
                color: red;
                font-size: 0.8em;
                margin-top: 2px;
                display: none;
            }
        </style>
        <script>
            function addUrlPatternField(formId) {
                const container = document.getElementById(formId === 'updateForm' ? 'updateUrlPatternsContainer' : 'urlPatternsContainer');
                const newField = document.createElement('div');
                newField.className = 'url-input-container';
                newField.innerHTML = '<input type="text" name="urlPatterns" placeholder="URL Pattern" required> ' +
                        '<button type="button" onclick="this.parentNode.remove()" class="delete-btn">Delete</button>';
                container.appendChild(newField);
            }
            
            function validateUrlPattern(input) {
                const pattern = input.value.trim();
                const errorSpan = input.nextElementSibling;
                
                if (errorSpan && errorSpan.className === 'invalid-feedback') {
                    errorSpan.style.display = 'none';
                }
                
                // Empty check
                if (pattern === '') {
                    input.classList.add('input-error');
                    if (errorSpan) {
                        errorSpan.textContent = 'URL pattern cannot be empty.';
                        errorSpan.style.display = 'block';
                    }
                    return false;
                }
                
                // Must start with / or *.
                if (!pattern.startsWith('/') && !pattern.startsWith('*.') && pattern !== '/*') {
                    input.classList.add('input-error');
                    if (errorSpan) {
                        errorSpan.textContent = 'URL pattern must start with `/`, `*.`, or `/*`.';
                        errorSpan.style.display = 'block';
                    }
                    return false;
                }
                if (pattern.includes('<') || pattern.includes('>') || pattern.includes('"') || 
                    pattern.includes("'") || pattern.includes('&') || pattern.includes('%')) {
                    input.classList.add('input-error');
                    if (errorSpan) {
                        errorSpan.textContent = 'URL pattern contains invalid characters.';
                        errorSpan.style.display = 'block';
                    }
                    return false;
                }
                
                // Check for relative paths
                if (pattern.includes('../') || pattern.includes('./')) {
                    input.classList.add('input-error');
                    if (errorSpan) {
                        errorSpan.textContent = 'URL pattern must not contain a relative path.';
                        errorSpan.style.display = 'block';
                    }
                    return false;
                }
                
                // Length check
                if (pattern.length > 200) {
                    input.classList.add('input-error');
                    if (errorSpan) {
                        errorSpan.textContent = 'URL pattern is too long (maximum 200 characters).';
                        errorSpan.style.display = 'block';
                    }
                    return false;
                }
                
                input.classList.remove('input-error');
                return true;
            }
            
            function validateForm(formId) {
                const form = document.getElementById(formId);
                const urlInputs = form.querySelectorAll('input[name="urlPatterns"]');
                let isValid = true;
                
                for (let i = 0; i < urlInputs.length; i++) {
                    if (!validateUrlPattern(urlInputs[i])) {
                        isValid = false;
                    }
                }
                
                return isValid;
            }
        </script>
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container" style="background-color: white">
                <h1>Filter Mapping Management</h1>

                <c:if test="${param.success == 'true'}">
                    <div class="notification">Filter mapping has been added successfully!</div>
                </c:if>

                <c:if test="${param.deleted == 'true'}">
                    <div class="notification">Filter mapping has been deleted successfully!</div>
                </c:if>
                
                <c:if test="${param.updated == 'true'}">
                    <div class="notification">Filter mapping has been updated successfully!</div>
                </c:if>

                <c:if test="${not empty sessionScope.reloadMessage}">
                    <div class="notification">${sessionScope.reloadMessage}</div>
                    <% session.removeAttribute("reloadMessage"); %>
                </c:if>
                
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty editMapping}">
                        <!-- Update Form -->
                        <h2>Manager Filter Mapping</h2>
                        <form id="updateForm" method="post" action="manage-urls" onsubmit="return validateForm('updateForm')">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="mappingId" value="${editMapping.id}">

                            <div class="form-group">
                                <label for="updateFilterName">Name Filter:</label>
                                <select id="updateFilterName" name="filterName" required>
                                    <option value="ManagerFilter" ${editMapping.filterName == 'ManagerFilter' ? 'selected' : ''}>ManagerFilter</option>
                                    <option value="AccountantFilter" ${editMapping.filterName == 'AccountantFilter' ? 'selected' : ''}>AccountantFilter</option>
                                    <option value="OwnerFilter" ${editMapping.filterName == 'OwnerFilter' ? 'selected' : ''}>OwnerFilter</option>
                                    <option value="ServiceFilter" ${editMapping.filterName == 'ServiceFilter' ? 'selected' : ''}>ServiceFilter</option>
                                    <option value="TechnicalFilter" ${editMapping.filterName == 'TechnicalFilter' ? 'selected' : ''}>TechnicalFilter</option>
                                    <option value="TenantFilter" ${editMapping.filterName == 'TenantFilter' ? 'selected' : ''}>TenantFilter</option>
                                    <option value="AdministrativeFilter" ${editMapping.filterName == 'AdministrativeFilter' ? 'selected' : ''}>AdministrativeFilter</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>URL Patterns:</label>
                                <div id="updateUrlPatternsContainer" class="url-patterns-container">
                                    <c:forEach items="${editMapping.urlPatterns}" var="pattern">
                                        <div class="url-input-container">
                                            <input type="text" name="urlPatterns" value="${pattern}" placeholder="URL Pattern" required
                                                   onblur="validateUrlPattern(this)">
                                            <span class="invalid-feedback"></span>
                                            <button type="button" onclick="this.parentNode.remove()" class="delete-btn">Delete</button>
                                        </div>
                                    </c:forEach>
                                </div>
                                <button type="button" onclick="addUrlPatternField('updateForm')" style="margin-left: 100px;">Add URL Pattern</button>
                            </div>

                            <div style="margin-left: 100px; margin-top: 15px;">
                                <button type="submit">Update Filter Mapping</button>
                                <a href="manage-urls" style="margin-left: 10px; text-decoration: none;">
                                    <button type="button" style="background-color: #757575;">Cancel</button>
                                </a>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <!-- Add Form -->
                        <form id="addForm" method="post" action="manage-urls" onsubmit="return validateForm('addForm')">
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
                                </select>
                            </div>

                            <div class="form-group">
                                <label>URL Patterns:</label>
                                <div id="urlPatternsContainer" class="url-patterns-container">
                                    <div class="url-input-container">
                                        <input type="text" name="urlPatterns" placeholder="URL Pattern" required
                                               onblur="validateUrlPattern(this)">
                                        <span class="invalid-feedback"></span>
                                    </div>
                                </div>
                                <button type="button" onclick="addUrlPatternField('addForm')" style="margin-left: 100px;">Add URL Pattern</button>
                            </div>

                            <button type="submit" style="margin-left: 100px; margin-top: 15px;">Save Filter Mapping</button>
                        </form>
                    </c:otherwise>
                </c:choose>

                <h2>Current Filter Mappings</h2>

                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name Filter</th>
                        <th>URL Patterns</th>
                        <th>Action</th>
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
                                <a href="?edit=${mapping.id}" class="edit-btn" style="display: inline-block; padding: 5px 10px; margin-right: 5px; text-decoration: none; color: white;">Update</a>
                                <form method="post" action="manage-urls" style="display: inline-block;"
                                      onsubmit="return confirm('Are you sure you want to delete this filter mapping?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="urlId" value="${mapping.id}">
                                    <button type="submit" class="delete-btn">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>