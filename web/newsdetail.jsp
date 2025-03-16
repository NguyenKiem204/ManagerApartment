<%-- 
    Document   : newsdetail
    Created on : Feb 10, 2025, 11:41:12 PM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>${news.title}</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/newsdetail.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/news.css"/>
        <div id="main">
            <div class="container mt-4 mb-4">
                <!-- Bài viết chi tiết -->
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title fs-2 text-center fw-bold"><c:out value="${news.title}"></c:out></h1>
                        <!-- Thông tin tác giả -->
                        <div class="author-info">
                            <div class="author-avatar">
                                <img src="<%= request.getContextPath() %>/${news.staff.image.imageURL}"
                                 alt="Avatar <c:out value="${news.staff.fullName}"></c:out>">
                            </div>
                            <div class="author-details">
                                <span class="author-name"><c:out value="${news.staff.fullName}"></c:out></span>
                                <span class="post-date">Create date: <c:out value="${news.formattedDate}"></c:out></span>
                            </div>
                        </div>
                        <div class="card-text">
                            <div id="description-content">
                                ${news.description}
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Các bài báo liên quan -->
                <div class="related-news">
                    <h3>Bài viết liên quan</h3>
                    <div class="row" id="related-news-grid">
                       <c:forEach var="news" items="${listRelatedNews}">
                                <div class="col-md-4 news-card">
                                    <div class="card news-item">
                                        <img src="<%= request.getContextPath() %>/${news.image.imageURL}" class="card-img-top img-fluid" alt="News Image">
                                        <div class="card-body">
                                            <h5 class="card-title truncated-text" style="height: 65px">${news.title}</h5>
                                            <p class="text-muted">Create date: ${news.formattedDate}</p>
                                            <a href="news-detail?newsId=${news.newsID}" class="btn btn-primary mt-1">Đọc thêm</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                    </div>
                    <button id="load-more" class="btn btn-secondary mt-3">Xem thêm</button>
                </div>

                <!-- Phần bình luận với AJAX -->
                <div style="border-radius: .7rem" class="comments-section mt-5 bg-light p-3">
                    <h3>Bình luận</h3>
                    
                    <!-- Alert container for feedback messages -->
                    <div id="comment-alerts"></div>
                    
                    <!-- Form bình luận -->
                    <form id="comment-form" method="post">
                        <input type="hidden" id="newsId" name="newsId" value="${news.newsID}">
                        
                        <c:choose>
                            <c:when test="${sessionScope.staff != null}">
                                <input type="hidden" id="userId" name="userId" value="${sessionScope.staff.staffId}">
                                <input type="hidden" id="userType" name="userType" value="Staff">
                                <input type="hidden" id="userName" name="userName" value="<c:out value="${sessionScope.staff.fullName}"></c:out>">
                                <input type="hidden" id="userAvatar" name="userAvatar" value="${pageContext.request.contextPath}/${sessionScope.staff.image.imageURL}">
                            </c:when>
                            <c:when test="${sessionScope.resident != null}">
                                <input type="hidden" id="userId" name="userId" value="${sessionScope.resident.residentId}">
                                <input type="hidden" id="userType" name="userType" value="Resident">
                                <input type="hidden" id="userName" name="userName" value="<c:out value="${sessionScope.resident.fullName}"></c:out>">
                                <input type="hidden" id="userAvatar" name="userAvatar" value="${pageContext.request.contextPath}/${sessionScope.resident.image.imageURL}">
                            </c:when>
                            <c:otherwise>
                                <!-- Not logged in message -->
                                <div class="alert alert-info">
                                    Vui lòng đăng nhập để bình luận
                                </div>
                            </c:otherwise>
                        </c:choose>
                        
                        <div class="mb-3">
                            <textarea class="form-control" id="comment-content" name="content" rows="3" 
                                      placeholder="Viết bình luận của bạn..."
                                      <c:if test="${sessionScope.staff == null && sessionScope.resident == null}">disabled</c:if>></textarea>
                        </div>
                        
                        <button type="submit" class="btn btn-primary" 
                                <c:if test="${sessionScope.staff == null && sessionScope.resident == null}">disabled</c:if>>
                            Gửi bình luận
                        </button>
                    </form>
                    
                    <!-- Danh sách bình luận -->
                    <div class="mt-4">
                        <div id="comments-list">
                            <div class="text-center">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden">Loading...</span>
                                </div>
                                <p>Đang tải bình luận...</p>
                            </div>
                        </div>
                        
                        <!-- Load more comments button, initially hidden -->
                        <button id="load-more-comments" class="btn btn-outline-primary mt-3" style="display: none;">
                            Tải thêm bình luận
                        </button>
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
        <script>
            $(document).ready(function () {
                $('.related-news .hidden').hide();
                $('#load-more').click(function () {
                    $('.related-news .hidden').slice(0, 3).removeClass('hidden').show();

                    if ($('.related-news .hidden').length === 0) {
                        $('#load-more').hide();
                    }
                });
            });
            
             var newsElement = document.querySelector('#news');
            newsElement.classList.add("active");
            document.querySelector('.news-active').classList.add('active')
            document.querySelector('.news-itemm a').style.color = '#d5460d';
        </script>
        
        <script src="<%= request.getContextPath() %>/assets/js/comments.js"></script>
    </body>

</html>