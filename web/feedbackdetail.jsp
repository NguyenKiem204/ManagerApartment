
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Detail</title>
        <style>
            #main {
                margin-left: 300px;
                padding: 0 !important;
            }
            .back-button {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #ff9800;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                font-size: 16px;
                text-align: center;
            }
            .back-button:hover {
                background-color: #e68900;
            }
            /* Add border to description content */
            #description-content {
                border: 2px solid #ddd;
                padding: 15px;
                border-radius: 5px;
                background-color: #f9f9f9;
                margin-top: 10px;
                max-height: 200px; /* Gi?i h?n chi?u cao */
                overflow-y: auto;  /* B?t thanh cu?n d?c n?u n?i dung dài */
                word-wrap: break-word;
            }
        </style>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <a href="#" class="burger-btn d-inline-block d-xl-none kiem_button">
                <i class="bi bi-justify fs-3"></i>
            </a>
            <!--=============================CONTENT HERE=======================-->
            <div class="container mt-4 mb-4">
                <!-- Feedback Detail -->
                <div class="card">

                    <div class="card-body">
                        <h1 class="card-title" style="font-size: 45px">${feedback.title}</h1>
                        <!-- Author Information -->
                        <div class="author-info">
                            <div class="author-details">
                                <span class="author-name">For: ${feedback.staff.role.roleName}</span><br/>
                                <span class="post-date">Date: <fmt:formatDate value="${feedback.formattedDate}" pattern="dd/MM/yyyy"></fmt:formatDate></span>
                                </div>
                            </div>
                            <div class="card-text">
                                <div id="description-content">
                                ${feedback.description}
                            </div>
                        </div>
                        <%
int roleId = (session.getAttribute("roleId") != null) ? (Integer) session.getAttribute("roleId") : -1;
String homeUrl = request.getContextPath() + "/technical/home"; // M?c ??nh

if (roleId == 2) {
    homeUrl = request.getContextPath() + "/administrative/home";
} else if (roleId == 3) {
    homeUrl = request.getContextPath() + "/accountant/home";
} else if (roleId == 4) {
    homeUrl = request.getContextPath() + "/technical/home";
} else if (roleId == 5) {
    homeUrl = request.getContextPath() + "/service/home";
}
                        %>

                        <a href="<%= homeUrl %>" class="back-button">Back to Feedback</a>

                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
