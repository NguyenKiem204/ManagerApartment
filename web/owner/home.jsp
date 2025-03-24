<%-- 
    Document   : menu.jsp
    Created on : Feb 8, 2025, 2:54:18 PM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu of Owner</title>
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main" style="margin-top: -60px">
            <div class="page-heading">
                <h3>Statistics</h3>
            </div>
            <div class="page-content">
                <section class="row">
                    <div class="col-12 col-lg-9">
                        <!--                        <div class="row">
                                                    <div class="col-6 col-lg-6 col-md-6">
                                                        <div class="card">
                                                            <div class="card-body px-3 py-4-5 border-cam">
                                                                <div class="row">
                                                                    <div class="col-md-4">
                                                                        <div class="stats-icon purple">
                                                                            <i class="fa-regular fa-building"></i>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-8">
                                                                        <h6 class="text-muted font-semibold">Apartments</h6>
                                                                        <h6 class="font-extrabold mb-0">100</h6>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-6 col-lg-6 col-md-6">
                                                        <div class="card">
                                                            <div class="card-body px-3 py-4-5 border-cam">
                                                                <div class="row">
                                                                    <div class="col-md-4">
                                                                        <div class="stats-icon blue">
                                                                            <i class="fa-solid fa-people-roof"></i>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-8">
                                                                        <h6 class="text-muted font-semibold">Residents</h6>
                                                                        <h6 class="font-extrabold mb-0">300</h6>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                        
                        
                                                </div>-->

                        <!-- Danh sách 5 request gần nhất -->
                        <div class="card border-cam">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4>Recent Requests</h4>
                                <a href="listrequest" class="btn btn-primary btn-sm">View More</a>
                            </div>
                            <div class="card-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Title</th>
                                            <th>Date Submitted</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="r" items="${listrequest}" varStatus="x">
                                            <tr onclick="goToRequestDetail(${r.requestID})" style="cursor: pointer;">
                                                <td>${x.index + 1}</td>
                                                <td>${r.title}</td>
                                                <td><fmt:formatDate value="${r.formattedDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <style>
                        .comment-cursor{
                            cursor: pointer;
                        }

                    </style>
                    <script>
                        function goToRequestDetail(requestID) {
                            var basePath = "${pageContext.request.contextPath}";
                            window.location.href = basePath + "/requestdetail?requestId=" + requestID;
                        }
                    </script>

                    <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            var commentElements = document.querySelectorAll(".comment-cursor");

                            commentElements.forEach(function (commentElement) {
                                commentElement.addEventListener("click", function () {
                                    var newsId = this.getAttribute("newsId");
                                    var commentId = this.getAttribute("commentId");
                                    window.location.href = "<%= request.getContextPath() %>/news-detail?newsId=" + newsId + "&commentId=" + commentId;
                                });
                            });
                        });
                    </script>

                    <div class="col-12 col-lg-3">
                        <div class="card">
                            <div class="card-body py-4 px-5 border-cam">
                                <div class="d-flex align-items-center">
                                    <div class="avatar avatar-xl">
                                        <img src="<%= request.getContextPath() %>/${resident.image.imageURL}" alt="<c:out value="${resident.fullName}"></c:out>">
                                        </div>
                                        <div class="ms-3 name">
                                            <h5 class="font-bold">Owner</h5>
                                            <h6 class="text-muted mb-0"><c:out value="${resident.fullName}"></c:out></h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card border-cam">
                                <div class="card-header">
                                    <h4>Recent Messages</h4>
                                </div>
                                <div class="card-content pb-4">
                                <c:forEach var="user" items="${list}">
                                    <div class="recent-message d-flex px-4 py-3">
                                        <div class="avatar avatar-lg">
                                            <img src="<%= request.getContextPath() %>/${user.image.imageURL}">
                                        </div>
                                        <div class="name ms-4">
                                            <h5 class="mb-1 text-truncation"><c:out value="${user.fullName}"></c:out></h5>
                                            <h6 class="text-muted mb-0 text-truncation"> <c:out value="${user.email}"></c:out></h6>
                                            </div>
                                        </div>
                                </c:forEach>

                                <div class="px-4">
                                    <button onclick="window.location.href = '<%= request.getContextPath() %>/chat'" 
                                            class='btn btn-block btn-xl btn-light-primary font-bold mt-3'>
                                        Start Conversation
                                    </button>
                                </div>
                            </div>
                        </div>

                    </div>
                </section>
                <div class="card-body">
                    <div class="container my-5" style="background: #fff;">
                        <div class="row">
                            <c:forEach var="news" items="${newsList}">
                                <div class="col-md-4 news-card">
                                    <div class="card news-item">
                                        <img src="<%= request.getContextPath() %>/${news.image.imageURL}" class="card-img-top img-fluid" style="height: 300px" alt="News Image">
                                        <div class="card-body">
                                            <h5 class="card-title truncated-text" style="height: 65px"><c:out value="${news.title}"></c:out></h5>
                                            <p class="text-muted">Create date: <c:out value="${news.formattedDate}"></c:out></p>
                                            <a href="<%= request.getContextPath() %>/news-detail?newsId=${news.newsID}" class="btn btn-primary mt-1">Đọc thêm</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                var revenueData = ${listRevenue};
                var chartDates = ${chartDates};
                var chartData = ${chartData};

                var optionsRequests = {
                    series: [
                        {
                            name: "Request Count",
                            data: chartData,
                        },
                    ],
                    chart: {
                        height: 80,
                        type: "area",
                        toolbar: {
                            show: false,
                        },
                    },
                    colors: ["#5350e9"],
                    stroke: {
                        width: 2,
                    },
                    grid: {
                        show: false,
                    },
                    dataLabels: {
                        enabled: false,
                    },
                    xaxis: {
                        type: "datetime",
                        categories: chartDates,
                        axisBorder: {
                            show: false,
                        },
                        axisTicks: {
                            show: false,
                        },
                        labels: {
                            show: false,
                        },
                    },
                    yaxis: {
                        labels: {
                            show: false,
                        },
                    },
                    tooltip: {
                        y: {
                            formatter: function (value) {
                                return value.toFixed(0);
                            }
                        },
                        x: {
                            format: "dd/MM/yy",
                        },
                    },

                    stroke: {
                        curve: 'smooth',
                        width: 2,
                    },
                    markers: {
                        size: 4,
                        hover: {
                            size: 6
                        }
                    }
                };
                var chartRequests = new ApexCharts(document.querySelector("#chart-requests"), optionsRequests);
                chartRequests.render();
            </script>
    </body>
</body>

</html>
