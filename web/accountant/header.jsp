<%-- 
    Document   : header
    Created on : Mar 12, 2025, 10:12:58 PM
    Author     : nkiem
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Điện Nước</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <style>
            .sidebar {
                min-height: calc(100vh - 56px);
                background-color: #f8f9fa;
                padding-top: 20px;
            }
            .content {
                padding: 20px;
            }
            .nav-link {
                color: #495057;
            }
            .nav-link:hover {
                background-color: #e9ecef;
            }
            .nav-link.active {
                background-color: #0d6efd;
                color: white;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
                    <i class="bi bi-water me-2"></i>Quản lý Điện Nước
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                                <i class="bi bi-speedometer2 me-1"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/meter-reading">
                                <i class="bi bi-clipboard-data me-1"></i>Chỉ số Điện Nước
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/apartment">
                                <i class="bi bi-building me-1"></i>Căn hộ
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/meter">
                                <i class="bi bi-disc me-1"></i>Đồng hồ
                            </a>
                        </li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle me-1"></i>${sessionScope.staffName}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i class="bi bi-person me-1"></i>Hồ sơ</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-1"></i>Đăng xuất</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 sidebar">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link ${param.activePage == 'dashboard' ? 'active' : ''}" href="${pageContext.request.contextPath}/dashboard">
                                <i class="bi bi-speedometer2 me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.activePage == 'meter-reading' ? 'active' : ''}" href="${pageContext.request.contextPath}/meter-reading">
                                <i class="bi bi-clipboard-data me-2"></i>Chỉ số Điện Nước
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.activePage == 'meter-reading-import' ? 'active' : ''}" href="${pageContext.request.contextPath}/meter-reading/import">
                                <i class="bi bi-file-earmark-arrow-up me-2"></i>Import Excel
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.activePage == 'meter-reading-export' ? 'active' : ''}" href="#" data-bs-toggle="modal" data-bs-target="#exportModal">
                                <i class="bi bi-file-earmark-arrow-down me-2"></i>Export Excel
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.activePage == 'logs' ? 'active' : ''}" href="${pageContext.request.contextPath}/logs">
                                <i class="bi bi-journal-text me-2"></i>Log Nhập/Xuất
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-10 content">
                    <!-- Export Modal -->
                    <div class="modal fade" id="exportModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Export Excel</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="${pageContext.request.contextPath}/meter-reading/export" method="get">
                                        <div class="mb-3">
                                            <label for="exportMonth" class="form-label">Tháng</label>
                                            <select class="form-select" id="exportMonth" name="month" required>
                                                <c:forEach var="i" begin="1" end="12">
                                                    <option value="${i}" ${currentMonth == i ? 'selected' : ''}>${i}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="exportYear" class="form-label">Năm</label>
                                            <select class="form-select" id="exportYear" name="year" required>
                                                <c:forEach var="i" begin="2020" end="2030">
                                                    <option value="${i}" ${currentYear == i ? 'selected' : ''}>${i}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="exportType" class="form-label">Loại báo cáo</label>
                                            <select class="form-select" id="exportType" name="type" required>
                                                <option value="readings">Chỉ số đồng hồ</option>
                                                <option value="report">Báo cáo tiêu thụ</option>
                                            </select>
                                        </div>
                                        <div class="text-end">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                            <button type="submit" class="btn btn-primary">Xuất Excel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            // Add any common JavaScript functions here

            // Initialize tooltips
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl)
            })

            // Highlight active sidebar item
            document.addEventListener('DOMContentLoaded', function () {
                // Get current page URL
                var path = window.location.pathname;

                // Find matching sidebar link and add active class
                document.querySelectorAll('.sidebar .nav-link').forEach(function (link) {
                    if (path.includes(link.getAttribute('href'))) {
                        link.classList.add('active');
                    }
                });
            });
        </script>
    </body>
</html>
