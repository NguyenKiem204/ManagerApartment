<%-- 
    Document   : addnews.jsp
    Created on : Feb 10, 2025, 9:25:09 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Add News</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/menu.css" />

        <style>
            body {
                background-color: #f8f9fa;
            }
            .border-orange {
                border-color: orange;
            }
        </style>
    </head>

    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container mt-5">
                <div class="text-center mb-4">
                    <h1 class="text-primary" style="font-family: 'Playball', cursive; font-size: 36px">
                        <i class="fas fa-plus-circle"></i> Tạo mới bài viết
                    </h1>
                    <hr class="w-25 mx-auto border-3 border-warning" />
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-8 col-md-10">
                        <form id="Create" action="addnews" method="POST" class="p-4 border rounded shadow bg-white" enctype="multipart/form-data">
                            <input type="hidden" value="${sessionScope.staff.staffId}" name="staffId"/>
                            <c:if test="${not empty errors}">
                                <div class="alert alert-danger">
                                    <strong>An error has occurred:</strong>
                                    <ul class="mb-0">
                                        <c:forEach var="error" items="${errors}">
                                            <li>${error}</li>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <div class="mb-3">
                                <label for="title" class="form-label"><i class="fas fa-heading"></i> Tên bài viết</label>
                                <input type="text" name="title" id="title" class="form-control border-orange" />
                            </div>

                            <div class="mb-3">
                                <label for="image" class="form-label">Chọn Ảnh</label>
                                <div class="input-group">
                                    <input type="file" name="imageURL" class="form-control" id="image">
                                </div>
                            </div>


                            <div class="mb-3">
                                <label for="detail" class="form-label"><i class="fas fa-file-alt"></i> Chi tiết</label>
                                <textarea id="detail" name="description" class="form-control"></textarea>
                            </div>

                            <div class="text-center">
                                <button type="submit" class="btn btn-success px-4">
                                    <i class="fas fa-save"></i> Tạo mới
                                </button>
                            </div>
                        </form>

                        <div class="text-center mt-3">
                            <a href="#" id="Index" class="btn btn-outline-danger"><i class="fas fa-arrow-left"></i> Quay lại</a>
                        </div>
                    </div>
                </div>
            </div>

            <script type="text/javascript">
                document.querySelectorAll('input, textarea').forEach(function (input) {
                    input.addEventListener('focus', function () {
                        const errorAlert = document.querySelector('.alert-danger');
                        if (errorAlert) {
                            errorAlert.style.display = 'none';
                        }
                    });
                });
                $(document).ready(function () {
                    $('#detail').summernote({
                        height: 300,
                        tabsize: 2,
                        placeholder: "Nhập nội dung bài viết..."
                    });
                });
                var newsElement = document.querySelector('#news');
                newsElement.classList.add("active");
                document.querySelector('.news-active').classList.add('active')
                document.querySelector('.addnews a').style.color = '#d5460d';
            </script>
            <!--==============================END================================-->

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
    </body>

</html>
