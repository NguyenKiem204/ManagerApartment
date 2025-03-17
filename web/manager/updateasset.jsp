<%-- 
    Document   : updateasset
    Created on : Mar 17, 2025, 3:40:40 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/manageassets.css" />
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main" style="padding-top: 0">
            <div class="container" style="background-color: #f4f4f4; position: relative;">
                <a href="manageassets" class="btn btn-secondary" style="position: absolute; top: 10px; left: 10px;">
                    <i class="fa fa-arrow-left"></i> Back
                </a>
                <div class="form-container">
                    <h3 class="text-center text-orange">Update Asset</h3>
                    <form action="updateasset" method="post">
                        <input type="hidden" id="assetId" name="assetId" value="${asset.assetId}">
                        <div class="mb-3">
                            <label for="assetName" class="form-label">Asset Name</label>
                            <input type="text" class="form-control" id="assetName" name="assetName" value="${asset.assetName}" required>
                        </div>
                        <div class="mb-3">
                            <label for="assetCategory" class="form-label">Category:</label>
                            <select class="form-select" id="assetCategory" name="categoryId" required>
                                <c:forEach var="cat" items="${listcategory}">
                                    <option value="${cat.categoryId}" ${cat.categoryId == asset.category.categoryId ? 'selected' : ''}>${cat.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="quantity" class="form-label">Quantity:</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" value="${asset.quantity}" required>
                            </div>
                            <div class="col-md-6">
                                <label for="location" class="form-label">Location:</label>
                                <input type="text" class="form-control" id="location" name="location" value="${asset.location}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="statusId" class="form-label">Status:</label>
                            <select class="form-select" id="statusId" name="statusId" required>
                                <c:forEach var="stat" items="${liststatus}">
                                    <option value="${stat.statusId}" ${stat.statusId == asset.status.statusId ? 'selected' : ''}>${stat.statusName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <c:if test="${not empty error}">
                            <p style="color: red;">${error}</p>
                        </c:if>
                        <button type="submit" class="btn btn-orange w-100">Update Asset</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
