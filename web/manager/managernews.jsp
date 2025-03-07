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
        <title>Manager News</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/managernews.css" />
    </head>

    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main" style="margin-top: -70px !important">
            <div class="card mb-4">
                <div class="card-header">
                    <form action="managernews" method="GET" class="row g-3 align-items-center">
                        <div class="col-md-4">
                            <label for="searchTitle" class="form-label">Search by Title</label>
                            <input type="text" class="form-control" id="searchTitle" name="searchTitle" 
                                   value="${param.searchTitle}" placeholder="Enter title...">
                        </div>
                        <div class="col-md-3">
                            <label for="startDate" class="form-label">Start Date</label>
                            <input type="date" class="form-control" id="startDate" name="startDate" 
                                   value="${param.startDate}">
                        </div>
                        <div class="col-md-3">
                            <label for="endDate" class="form-label">End Date</label>
                            <input type="date" class="form-control" id="endDate" name="endDate" 
                                   value="${param.endDate}">
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary me-2">Search</button>
                            <a href="managernews" class="btn btn-secondary">Reset</a>
                        </div>
                    </form>
                </div>
                <div class="card-body">
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
                            <c:forEach var="news" items="${newsList}">
                                <tr>
                                    <td>${i+1}</td>
                                    <td class="title">${news.title}</th>
                                    <td>${news.formattedDate}</td>
                                    <td><img src="<%= request.getContextPath() %>/${news.image.imageURL}" class="image-news shadow" alt="${news.title}" /></td>
                                    <td><div class="btn1">
                                            <button 
                                                data-id="${news.newsID}"
                                                data-title="${fn:escapeXml(news.title)}"
                                                data-description="${fn:escapeXml(news.description)}"
                                                data-date="${fn:escapeXml(news.formattedDate)}"
                                                data-staff="${sessionScope.staff.staffId}"
                                                onclick="updateNewsFromButton(this)">
                                                Update
                                            </button>  
                                            <button 
                                                data-id="${news.newsID}"
                                                data-title="${fn:escapeXml(news.title)}"
                                                onclick="deleteNewsFromButton(this)">
                                                Delete
                                            </button>
                                        </div></td>
                                </tr>
                                <c:set var="i" value="${i + 1}"></c:set>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="pagination">  
                    <c:set var="currentPage" value="${currentPage}" />  
                    <c:set var="totalPages" value="${totalPages}" />  
                    <c:set var="prevPage" value="${currentPage - 1}" />  
                    <c:set var="nextPage" value="${currentPage + 1}" />  

                    <c:url var="baseUrl" value="managernews">
                        <c:param name="searchTitle" value="${param.searchTitle}" />
                        <c:param name="startDate" value="${param.startDate}" />
                        <c:param name="endDate" value="${param.endDate}" />
                    </c:url>

                    <c:if test="${currentPage > 1}">  
                        <a href="${baseUrl}&page=1">First Page</a>  
                        <a href="${baseUrl}&page=${prevPage}">Previous</a>  
                    </c:if>  

                    <c:set var="startPage" value="${currentPage - 1}" />  
                    <c:set var="endPage" value="${currentPage + 1}" />  

                    <c:if test="${startPage < 1}">
                        <c:set var="startPage" value="1" />
                    </c:if>

                    <c:if test="${endPage > totalPages}">
                        <c:set var="endPage" value="${totalPages}" />
                    </c:if>

                    <c:forEach var="i" begin="${startPage}" end="${endPage}">  
                        <c:choose>  
                            <c:when test="${i == currentPage}">  
                                <strong>${i}</strong>  
                            </c:when>  
                            <c:otherwise>  
                                <a href="${baseUrl}&page=${i}">${i}</a>  
                            </c:otherwise>  
                        </c:choose>  
                    </c:forEach>  

                    <c:if test="${currentPage < totalPages}">  
                        <a href="${baseUrl}&page=${nextPage}">Next</a>  
                        <a href="${baseUrl}&page=${totalPages}">Last Page</a>  
                    </c:if>  
                </div>

            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="updateForm">
                            <form action="updatenews" method="post" enctype="multipart/form-data" onsubmit="return confirmUpdateNews()">
                                <input type="hidden" name="newsID" id="newsID">
                                <input type="hidden" name="staffId" id="staffId">
                                <input type="hidden" name="url" id="url">
                                <c:if test="${not empty sessionScope.newsID}">
                                    <c:if test="${not empty sessionScope.errors}">
                                        <div class="alert alert-danger">
                                            <strong>An error has occurred:</strong>
                                            <ul class="mb-0">
                                                <c:forEach var="error" items="${sessionScope.errors}">
                                                    <li>${error}</li>
                                                    </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                </c:if>
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
            <c:if test="${not empty sessionScope.errors}">
                <div id="errorAlert" class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>An error has occurred:</strong>
                    <ul class="mb-0">
                        <c:forEach var="error" items="${sessionScope.errors}">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="closeErrorAlert()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <c:remove var="errors" scope="session"/>
            </c:if>
        </div>
        <script type="text/javascript">
            function updateNewsFromButton(button) {
                const newsID = button.getAttribute("data-id");
                const title = button.getAttribute("data-title");
                const description = button.getAttribute("data-description");
                const date = button.getAttribute("data-date");
                const staffId = button.getAttribute("data-staff");

                updateNews(newsID, title, description, date, staffId);
            }

            function deleteNewsFromButton(button) {
                const newsID = button.getAttribute("data-id");
                const title = button.getAttribute("data-title");

                deleteNews(newsID, title);
            }
            function closeErrorAlert() {
                let errorAlert = document.getElementById("errorAlert");
                if (errorAlert) {
                    errorAlert.classList.add("fade-out");
                    setTimeout(() => {
                        errorAlert.style.display = "none";
                    }, 500);
                }
            }

            document.addEventListener("DOMContentLoaded", function () {
                let errorAlert = document.getElementById("errorAlert");
                if (errorAlert) {
                    errorAlert.style.display = "block";
                    setTimeout(() => {
                        errorAlert.classList.add("show");

                        setTimeout(() => {
                            errorAlert.classList.add("fade-out");
                            setTimeout(() => {
                                errorAlert.style.display = "none";
                            }, 500);
                        }, 5000);
                    }, 100);
                }
            });
            $(document).ready(function () {
                $('#detail').summernote({
                    height: 300,
                    tabsize: 2,
                    placeholder: "Nhập nội dung bài viết..."
                });
            });
            let titlee = "";
            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("url").value = window.location.href;
            });
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
                if (confirm("Bạn có chắc chắn xóa sản phẩm " + title + " không?")) {
                    let currentUrl = encodeURIComponent(window.location.href);
                    window.location.href = "deletenews?newsID=" + newsID + "&url=" + currentUrl;
                }
            }
            var newsElement = document.querySelector('#news');
            newsElement.classList.add("active");
            document.querySelector('.news-active').classList.add('active')
            document.querySelector('.managernews a').style.color = '#d5460d';
        </script>
    </body>

</html>

