<%-- 
    Document   : list
    Created on : Mar 12, 2025, 10:13:56 PM
    Author     : nkiem
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/accountant/header.jsp">
    <jsp:param name="activePage" value="meter-reading" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-clipboard-data me-2"></i>Quản lý Chỉ số Điện Nước</h2>
        <a href="${pageContext.request.contextPath}/meter-reading/edit" class="btn btn-primary">
            <i class="bi bi-plus-circle me-1"></i>Thêm chỉ số mới
        </a>
    </div>
    
    <!-- Filter controls -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="${pageContext.request.contextPath}/meter-reading" class="row g-3">
                <div class="col-md-3">
                    <label for="month" class="form-label">Tháng</label>
                    <select class="form-select" id="month" name="month">
                        <c:forEach var="i" begin="1" end="12">
                            <option value="${i}" ${currentMonth == i ? 'selected' : ''}>${i}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="year" class="form-label">Năm</label>
                    <select class="form-select" id="year" name="year">
                        <c:forEach var="i" begin="2020" end="2030">
                            <option value="${i}" ${currentYear == i ? 'selected' : ''}>${i}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="apartment" class="form-label">Căn hộ</label>
                    <select class="form-select" id="apartment" name="apartment">
                        <option value="">Tất cả căn hộ</option>
                        <c:forEach var="apartment" items="${apartments}">
                            <option value="${apartment.apartmentId}" ${param.apartment == apartment.apartmentId ? 'selected' : ''}>
                                ${apartment.apartmentName} - ${apartment.ownerName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-search me-1"></i>Lọc
                    </button>
                </div>
            </form>
        </div>
    </div>
    
    <!-- Import/Export buttons -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/meter-reading/import" class="btn btn-success">
                    <i class="bi bi-file-earmark-arrow-up me-1"></i>Import Excel
                </a>
                <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#exportModal">
                    <i class="bi bi-file-earmark-arrow-down me-1"></i>Export Excel
                </button>
                <a href="${pageContext.request.contextPath}/meter-reading/template" class="btn btn-outline-secondary">
                    <i class="bi bi-file-earmark-excel me-1"></i>Tải Template
                </a>
            </div>
        </div>
    </div>
    
    <!-- Readings table -->
    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${empty meterReadings}">
                    <div class="alert alert-info text-center">
                        <i class="bi bi-info-circle me-2"></i>Không có dữ liệu chỉ số cho tháng ${currentMonth}/${currentYear}
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-primary">
                                <tr>
                                    <th>Căn hộ</th>
                                    <th>Loại đồng hồ</th>
                                    <th>Số đồng hồ</th>
                                    <th>Chỉ số cũ</th>
                                    <th>Chỉ số mới</th>
                                    <th>Tiêu thụ</th>
                                    <th>Ngày đọc</th>
                                    <th>Nhân viên</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reading" items="${meterReadings}">
                                    <tr>
                                        <td>${reading.apartmentName}</td>
                                        <td>
                                            <span class="badge ${reading.meterType == 'Electricity' ? 'bg-warning' : 'bg-info'}">
                                                ${reading.meterType == 'Electricity' ? 'Điện' : 'Nước'}
                                            </span>
                                        </td>
                                        <td>${reading.meterNumber}</td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${reading.previousReading}" pattern="#,##0.00" />
                                        </td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${reading.currentReading}" pattern="#,##0.00" />
                                        </td>
                                        <td class="text-end fw-bold">
                                            <fmt:formatNumber value="${reading.consumption}" pattern="#,##0.00" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${reading.readingDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>${reading.staffName}</td>
                                        <td>
                                            <span class="badge ${reading.status == 'Active' ? 'bg-success' : 'bg-danger'}">
                                                ${reading.status == 'Active' ? 'Hoạt động' : 'Đã xóa'}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/meter-reading/details?id=${reading.readingId}" 
                                                   class="btn btn-outline-info" data-bs-toggle="tooltip" title="Chi tiết">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/meter-reading/edit?id=${reading.readingId}" 
                                                   class="btn btn-outline-primary" data-bs-toggle="tooltip" title="Sửa">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" 
                                                        data-bs-target="#deleteModal${reading.readingId}" title="Xóa">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </div>
                                            
                                            <!-- Delete Confirmation Modal -->
                                            <div class="modal fade" id="deleteModal${reading.readingId}" tabindex="-1">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Xác nhận xóa</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>Bạn có chắc chắn muốn xóa chỉ số này không?</p>
                                                            <p><strong>Căn hộ:</strong> ${reading.apartmentName}</p>
                                                            <p><strong>Đồng hồ:</strong> ${reading.meterNumber} (${reading.meterType == 'Electricity' ? 'Điện' : 'Nước'})</p>
                                                            <p><strong>Chỉ số:</strong> <fmt:formatNumber value="${reading.previousReading}" pattern="#,##0.00" /> → 
                                                                <fmt:formatNumber value="${reading.currentReading}" pattern="#,##0.00" /></p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                            <a href="${pageContext.request.contextPath}/meter-reading/delete?id=${reading.readingId}" 
                                                               class="btn btn-danger">Xóa</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/accountant/footer.jsp" />
