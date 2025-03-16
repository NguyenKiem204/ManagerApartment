<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Asset</title>

        <!-- Bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <!-- FontAwesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/manageassets.css" />

        <style>
            .btn-orange {
                background-color: #FFA500; /* Màu cam */
                color: white;
                border: none;
            }

            .btn-orange:hover {
                background-color: #E69500; /* Màu cam đậm hơn khi hover */
            }
        </style>
    </head>
    <body>

        <%@include file="menumanager.jsp" %>
        <div id="main" style="padding-top: 0">
            <div class="container" style="background-color: #f4f4f4; position: relative;">
                <a href="manageassets" class="btn btn-secondary" style="position: absolute; top: 10px; left: 10px;">
                    <i class="fa fa-arrow-left"></i> Back
                </a>

                <div class="form-container">
                    <h3 class="text-center text-orange">Add new asset</h3>
                    <form action="addasset" method="post">
                        <div class="mb-3">
                            <label for="assetName" class="form-label">Name asset</label>
                            <input type="text" class="form-control" id="assetName" name="assetName" required>
                        </div>
                        <div class="mb-3">
                            <label for="assetCategory" class="form-label">Category:</label>
                            <select class="form-select" id="assetCategory" name="categoryId" required>
                                <c:forEach var="cat" items="${listcategory}">
                                    <option value="${cat.categoryId}">${cat.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="quantity" class="form-label">Quantity:</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" required>
                            </div>
                            <div class="col-md-6">
                                <label for="location" class="form-label">Location:</label>
                                <input type="text" class="form-control" id="location" name="location">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="purchaseDate" class="form-label">Bought On:</label>
                                <input type="text" class="form-control" id="boughtOn" name="boughtOn" placeholder="dd-MM-yyyy" required>
                            </div>
                            <div class="col-md-6">
                                <label for="statusId" class="form-label">Status:</label>
                                <select class="form-select" id="statusId" name="statusId" required>
                                    <c:forEach var="stat" items="${liststatus}">
                                        <option value="${stat.statusId}">${stat.statusName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <c:if test="${not empty error}">
                            <p style="color: red;">${error}</p>
                        </c:if>
                        <button type="submit" class="btn btn-orange w-100">Add asset</button>
                    </form>
                </div>
            </div>
        </div>


        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
