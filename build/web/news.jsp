<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>News</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
    </head>

    <body>
        
        <%@include file="/manager/menumanager.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/news.css" />
        <div id="main">
            <div class="card">
                <div class="card-header">
                    <div class="text-center mb-4">
                        <h1 class="text-d" style="font-family: 'Playball', cursive; font-size: 36px">
                            <i class="fa-solid fa-fire"></i>Breaking News
                        </h1>
                        <hr class="w-25 mx-auto border-3 border-warning" />
                    </div>
                </div>
                <div class="card-body">
                    <div class="container my-5">

                        <!-- News Section -->
                        <div class="row">
                            <c:forEach var="news" items="${newsList}">
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
                    </div>
                </div>
                <div class="card-footer">
                    <!-- Pagination -->
                    <div class="pagination">  
                        <c:set var="currentPage" value="${currentPage}" />  
                        <c:set var="totalPages" value="${totalPages}" />  
                        <c:set var="prevPage" value="${currentPage - 1}" />  
                        <c:set var="nextPage" value="${currentPage + 1}" />  

                        <c:url var="baseUrl" value="news">
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

            </div>

            <footer>
                <div class="footer clearfix mb-0 text-muted">
                    <div class="float-start"> 
                    </div>
                </div>
            </footer>
        </div>
        <script>
            var newsElement = document.querySelector('#news');
            newsElement.classList.add("active");
            document.querySelector('.news-active').classList.add('active')
            document.querySelector('.news-itemm a').style.color = '#d5460d';
        </script>
    </body>

</html>