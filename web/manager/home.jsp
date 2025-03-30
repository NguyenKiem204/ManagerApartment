<%-- 
    Document   : home1
    Created on : Feb 11, 2025, 2:12:16 AM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Home</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <style>
            .text-truncation{
                white-space: nowrap !important;
                overflow: hidden !important;
                width: 85%!important;
                text-overflow: ellipsis !important;
            }
        </style>
    </head>

    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main" style="margin-top: -60px">
            <div class="page-heading">
                <h3>Statistics</h3>
            </div>
            <div class="page-content">
                <section class="row">
                    <div class="col-12 col-lg-9">
                        <div class="row">
                            <div class="col-6 col-lg-3 col-md-6">
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
                                                <h6 class="font-extrabold mb-0">${numberApartment}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-lg-3 col-md-6">
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
                                                <h6 class="font-extrabold mb-0">${numberResident}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-lg-3 col-md-6">
                                <div class="card">
                                    <div class="card-body px-3 py-4-5 border-cam">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="stats-icon green">
                                                    <i class="fa-solid fa-file-invoice"></i>
                                                </div>
                                            </div>
                                            <div class="col-md-8">
                                                <h6 class="text-muted font-semibold">Request</h6>
                                                <h6 class="font-extrabold mb-0">${numberRequest}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-lg-3 col-md-6">
                                <div class="card">
                                    <div class="card-body px-3 py-4-5 border-cam">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="stats-icon red">
                                                    <i class="fa-solid fa-handshake-simple"></i>
                                                </div>
                                            </div>
                                            <div class="col-md-8">
                                                <h6 class="text-muted font-semibold">Unpaid Invoice</h6>
                                                <h6 class="font-extrabold mb-0">${numberUnpaidInvoice}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="card border-cam">
                                    <div class="card-header">
                                        <h4>Revenue</h4>
                                    </div>
                                    <div class="card-body">
                                        <div id="chart-profile-visit" ></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12 col-xl-4">
                                <div class="card border-cam">
                                    <div class="card-header">
                                        <h4>Request Last 7days</h4>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="d-flex align-items-center">
                                                    <svg class="bi text-primary" width="32" height="32" fill="blue" style="width:10px">
                                                    <use xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#circle-fill" />
                                                    </svg>
                                                    <h5 class="mb-0 ms-3">Total</h5>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <c:set var="totalRequests" value="0" />
                                                <c:forEach items="${requests}" var="entry">
                                                    <c:set var="totalRequests" value="${totalRequests + entry.value}" />
                                                </c:forEach>
                                                <h5 class="mb-0">${totalRequests}</h5>
                                            </div>
                                            <div class="col-12">
                                                <div id="chart-requests"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-xl-8">
                                <div class="card border-cam">
                                    <div class="card-header">
                                        <h4>Latest Commentsss</h4>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover table-lg">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Comment</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="comment" items="${requestScope.comments}">
                                                        <tr class="comment-cursor" newsId='${comment.newsId}' commentId='${comment.commentId}'>
                                                            <td class="col-3">
                                                                <div class="d-flex align-items-center">
                                                                    <div class="avatar avatar-md">
                                                                        <img src="${pageContext.request.contextPath}/${not empty comment.userAvatar ? comment.userAvatar : 'assets/images/avatar/original.jpg'}">
                                                                    </div>
                                                                    <p class="font-bold ms-3 mb-0">${comment.lastName}</p>
                                                                </div>
                                                            </td>
                                                            <td class="col-auto">
                                                                <p class=" mb-0"><c:out value="${comment.content}"/>
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <style>
                        .comment-cursor{
                            cursor: pointer;
                        }

                    </style>
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
                                        <img src="<%= request.getContextPath() %>/${not empty staff.image.imageURL ? staff.image.imageURL : '/assets/images/avatar/original.jpg'}" alt="<c:out value="${staff.fullName}"></c:out>">
                                        </div>
                                        <div class="ms-3 name">
                                            <h5 class="font-bold">Admin</h5>
                                            <h6 class="text-muted mb-0"><c:out value="${staff.fullName}"></c:out></h6>
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
                                            <img src="<%= request.getContextPath() %>/${not empty user.image.imageURL ? user.image.imageURL : '/assets/images/avatar/original.jpg'}">
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
                        <div class="card border-cam">
                            <div class="card-header">
                                <h4>Số căn hộ đã thuê</h4>
                            </div>
                            <div class="card-body">
                                <div id="chart-visitors-profile"  data-occupied="${numberApartmentOccupied}"
                                     data-available="${numberApartmentAvailable}"></div>
                            </div>
                        </div>
                    </div>
                </section>
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
</html>
