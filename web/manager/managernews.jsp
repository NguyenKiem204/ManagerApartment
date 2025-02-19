<%-- 
    Document   : managernews
    Created on : Feb 18, 2025, 6:26:37 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Trang chủ</title>

        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />

        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/iconly/bold.css" />

        <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/pages/index.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />
        <style>
            .text-des {
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .image-news{
                width: 130px;
                height: 130px;
                object-fit: cover;
                object-position: center;
            }
            .btn a{
                display: inline-block !important;
                border-radius: 5px !important;
                width: 75px !important;
                height: 35px!important;
                text-decoration: none!important;
                padding: 5px 10px !important;
                border: 1px solid #ccc !important;

            }
            .btn a:first-child{
                background-color: #4CAF50 !important;
                color: #fff !important;
            }
            .btn a:last-child{
                background-color: #f44336 !important;
                color: #fff !important;
            }
            .btn-add{
                display: inline-block !important;
                width: 70px !important;
                padding: 5px 10px !important;
                border-radius: 8px !important;
            }

            .btn1 {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
                width: 100%;
                box-shadow: none !important;
                border-radius: none !important;
            }

            .btn1 button {
                display: inline-block;
                border-radius: 8px;
                width: 70px;
                height: 40px;
                text-decoration: none;
                padding: 5px 10px;
                border: 2px solid #ccc;
                position: relative;
                overflow: hidden;
                transition: color 0.4s, background-color 0.4s;
            }

            .btn1 button:first-child {
                background-color: #4CAF50;
                color: #fff;
                margin-right: 5px;
            }

            .btn1 button:last-child {
                background-color: #f44336;
                color: #fff;
            }

            .btn1 button:first-child:hover {
                background-color: #fff;
                color: #4CAF50;
            }

            .btn1 button:last-child:hover {
                background-color: #fff;
                color: #f44336;
            }

            .btn1 button:hover::before {
                content: '';
                position: absolute;
                top: 50%;
                left: 50%;
                width: 300%;
                height: 300%;
                background: rgba(255, 255, 255, 0.3);
                transform: translate(-50%, -50%) scale(0);
                border-radius: 50%;
                animation: ripple 0.6s linear;
            }

            .updateForm {
                position: fixed;
                width: 1050px;
                top: 50%;
                left: 50%;
                transform: translate(-40%, -50%);
                display: none;
                z-index: 10;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .updateForm.active {
                display: block;
            }

            #close {
                position: absolute;
                top: -25px;
                right: -25px;
                font-weight: 600;
                cursor: pointer;
                z-index: 11;
            }

            #close i {
                font-size: 1.8rem;
                color: #f44336;
                background: #fff;
                border: 2px solid #333;
                border-radius: 50%;
            }

            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                display: none;
                z-index: 5;
            }

            .overlay.active {
                display: block;
            }
            .title{
                /*font-size: 20px;*/
                font-weight: 550;
            }
            .title-xl{
                font-size: 20px !important;
                font-weight: 600 !important;
            }
        </style>
    </head>

    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div>
                <table class="table bg-light table-striped table-hover table-bordered caption-top table-responsive-md">
                    <thead>
                        <tr class="table-dark">
                            <th class="col-1">STT</th>
                            <th class="col-4">Title</th>
                            <th class="col-2">SentDate</th>
                            <th class="col-2">Image</th>
                            <th class="col-2">Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="i" value="0"></c:set>
                        <c:forEach var="news" items="${list}">
                            <tr>
                                <td>${i+1}</td>
                                <td class="title">${news.title}</th>
                                <td>${news.sentDate}</td>
                                <td><img src="<%= request.getContextPath() %>/${news.image.imageURL}" class="image-news shadow" alt="${news.title}" /></td>
                                <td><div class="btn1">  
                                        <button onclick="updateNews(
                                                        '${news.newsID}',
                                                        '${fn:escapeXml(news.title)}',
                                                        '${fn:escapeXml(news.description)}',
                                                        '${news.sentDate}',
                                                        '${sessionScope.staff.staffId}')">Update</button>  
                                        <button onclick="deleteNews('${news.newsID}', '${fn:escapeXml(news.title)}')">Delete</button>  
                                    </div></td>
                            </tr>
                            <c:set var="i" value="${i + 1}"></c:set>
                        </c:forEach>
                    </tbody>
                </table>  
            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="updateForm">
                            <form action="updatenews" method="post" enctype="multipart/form-data" onsubmit="return confirmUpdateNews()">
                                <input type="hidden" name="newsID" id="newsID">
                                <input type="hidden" name="staffId" id="staffId">
                                <div class="form-group">
                                    <label for="name">Title</label>
                                    <input type="text" class="form-control title-xl" id="title" name="title" placeholder="Enter Title">
                                </div>
                                <div class="form-group">
                                    <label for="detail">Description</label>
                                    <textarea id="detail" name="description" class="form-control"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="price">SentDate</label>
                                    <input type="text" class="form-control" id="sentDate" name="sentDate" placeholder="Enter Date Create">
                                </div>

                                <div class="form-group">
                                    <label for="imgURL">Image URL</label>
                                    <input type="file" class="form-control" id="imgURL" name="imageURL">
                                </div>

                                <button id="submit" type="submit" class="btn btn-primary mt-3 submit">Update Product</button>
                            </form>
                            <div id="close" onclick="closeForm()"><i class="fa-solid fa-circle-xmark"></i></div>
                        </div>
                    </div>
                </div>
            </div>
            <div onclick="closeForm()" class="overlay"></div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#detail').summernote({
                    height: 300,
                    tabsize: 2,
                    placeholder: "Nhập nội dung bài viết..."
                });
            });
            let titlee = "";
            function updateNews(newsID, title, description, sentDate, staffId) {
                titlee = title;
                document.getElementById('newsID').value = newsID;
                document.getElementById('title').value = title;
                $('#detail').summernote('code', description);
                document.getElementById('sentDate').value = sentDate;
                document.getElementById('staffId').value = staffId;
                document.getElementById("imgURL").value = '';
                document.querySelector(".updateForm").classList.add("active");
                document.querySelector(".overlay").classList.add("active");
            }
            function confirmUpdateNews() {
                return confirm("Bạn có chắc chắn cập nhật " + titlee + " không?");
            }
            function closeForm() {
                document.querySelector(".updateForm").classList.remove("active");
                document.querySelector(".overlay").classList.remove("active");
                document.querySelector(".form-container").classList.remove("active");
            }
            function deleteNews(newsID, title) {
                if (confirm("Bạn có chắc chắn xóa sản phẩm  " + title + " không?")) {
                    window.location.href = "deletenews?newsID=" + newsID;
                }
            }

        </script>

        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>

</html>

