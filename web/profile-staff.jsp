<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Staff" %>  
<%@ page import="model.Resident" %> 
<%@ page import="java.time.LocalDate" %>  
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>My Profile</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/profile.css" />
        <div id="main" style="margin-top: -50px">
            <div class="container-fluid p-5">
                <div class="row justify-content-center">
                    <div class="col-12">
                        <div class="profile-card">
                            <div class="row">
                                <c:set var="staff" value="${sessionScope.staff}" />
                                <div class="col-md-4 text-center d-flex justify-content-center align-items-center">
                                    <img src="<%= request.getContextPath() %>${staff.image.imageURL}"
                                         alt="Ảnh cá nhân" class="img-fluid rounded-circle profile-img" />
                                </div>
                                <div class="col-md-8 d-flex flex-column justify-content-center">
                                    <h2 class="user-name"> <c:out value="${staff.fullName}"></c:out></h2>
                                    <p class="user-role">${staff.role.roleName}</p>

                                    <div class="profile-info">
                                        <p><strong>Email:</strong> <c:out value="${staff.email}"></c:out></p>
                                        <p><strong>Phone:</strong> <c:out value="${staff.phoneNumber}"></c:out></p>
                                            <p><strong>Address:</strong> Hà Nội</p>
                                            <p><strong>Status:</strong> Active</p>
                                            <p><strong>Date Of Birth:</strong> 
                                            <fmt:formatDate value="${staff.formattedDate}" pattern="dd-MM-yyyy" />
                                        </p>
                                        <p><strong>Sex:</strong> <c:out value="${staff.sex}"></c:out></p>
                                    </div>
                                </div>

                                <button class="btn edit-button mt-4" onclick="window.location.href = 'changeprofile-staff'">
                                    Change Profile
                                </button>

                            </div>
                        </div>
                    </div>
                </div>
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
        <!--==============================END================================-->
    </body>

</html>