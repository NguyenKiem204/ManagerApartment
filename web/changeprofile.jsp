<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Change Profile</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/changeprofile.css" />
        <div id="main">
            <div class="container profile-container mt-5">
                <h2 class="text-center mb-4">Change Profile</h2>
                <c:set var="resident" value="${sessionScope.resident}"/>

                <form action="update-profile" method="POST" enctype="multipart/form-data">
                    <div class="text-center mb-3">
                        <img id="preview-img" 
                             src="<%= request.getContextPath() %>/${not empty resident.image.imageURL ? resident.image.imageURL : '/assets/images/avatar/original.jpg'}"
                             alt="Profile Picture" class="profile-img rounded-circle" width="150" height="150">

                        <input type="file" class="form-control mt-2" name="imgURL" id="upload-photo" accept="image/*">
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="fullName" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="fullName" name="fullName" value="<c:out value='${resident.fullName}'/>">
                            <input type="hidden" class="form-control" id="userID" name="userID" value="<c:out value='${resident.residentId}'/>">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" disabled name="email" value="<c:out value='${resident.email}'/>">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="phone" class="form-label">Phone</label>
                            <input type="text" class="form-control" id="phone" name="phoneNumber" value="<c:out value='${resident.phoneNumber}'/>">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address" value="Hanoi">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="dob" class="form-label">Date Of Birth</label>
                            <input type="text" class="form-control" name="dob" value="<fmt:formatDate value="${resident.formattedDate}" pattern="dd/MM/yyyy"/>" id="datePicker" placeholder="dd/MM/yyyy">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="sex" class="form-label">Sex</label>
                            <select class="form-select" id="sex" name="sex">
                                <option value="Male" ${resident.sex == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${resident.sex == 'Female' ? 'selected' : ''}>Female</option>
                                <option value="Other" ${resident.sex == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" value="*******" disabled>
                        </div>
                    </div>

                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger">
                            <strong>An error has occurred:</strong>
                            <ul class="mb-0">
                                <c:forEach var="error" items="${errors}">
                                    <li><c:out value='${error}'/></li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <div class="d-flex justify-content-between">
                        <button type="submit" class="btn btn-primary">Save</button>
                        <button type="reset" class="btn btn-secondary">Cancel</button>
                    </div>
                </form>
            </div>
            <footer>
                <div class="footer clearfix mb-0 text-muted">
                    <div class="float-start">
                        <p>2025 &copy; Kiemm</p>
                    </div>
                    <div class="float-end">
                        <p>
                            Crafted with
                            <span class="text-danger"><i class="bi bi-heart"></i></span> by
                            <a href="http://ahmadsaugi.com">NguyenKiem</a>
                        </p>
                    </div>
                </div>
            </footer>
        </div>

        <style>
            .alert-danger {
                padding: 10px;
                border-radius: 5px;
                font-size: 14px;
            }
            .alert-danger ul {
                margin-bottom: 0;
                padding-left: 20px;
            }

        </style>
        <script>
            document.querySelectorAll('input, select').forEach(function (input) {
                input.addEventListener('focus', function () {
                    const errorAlert = document.querySelector('.alert-danger');
                    if (errorAlert) {
                        errorAlert.style.display = 'none';
                    }
                });
            });
        </script>

        <script>
            document.getElementById("upload-photo").addEventListener("change", function (event) {
                const file = event.target.files[0];
                if (file) {
                    if (!file.type.startsWith("image/")) {
                        alert("Chỉ được chọn file ảnh!");
                        event.target.value = "";
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById("preview-img").src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            });
        </script>
    </body>

</html>
