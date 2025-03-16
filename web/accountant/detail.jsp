<%-- 
    Document   : detail
    Created on : Mar 12, 2025, 10:16:42 PM
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
        <h2><i class="bi bi-clipboard-data me-2"></i>Chi tiết Chỉ số</h2>
        <div>
            <a href="${pageContext.request.contextPath}/meter-reading/edit?id=${meterReading.readingId}" class="btn btn-primary">
                <i class="bi bi-pencil me-1"></i>Sửa
            </a>
            <a href="${pageContext.request.contextPath}/meter-reading" class="btn btn-secondary ms-2">
                <i class="bi bi-arrow-left me-1"></i>Quay lại
            </a>
        </div>
    </div>
    
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <h5 class="card-title">Thông tin chỉ số</h5>
                    <table class="table table-borderless">
                        <tr>
                            <th width="30%">Căn hộ:</th>
                            <td>${meterReading.apartmentName}</td>
                        </tr>
                        <tr>
                            <th>Chủ hộ:</th>
                            <td>${meterReading.ownerName}</td>
                        </tr>
                        <tr>
                            <th>Loại đồng hồ:</th>
                            <td>
                                <span class="badge ${meterReading.meterType == 'Electricity' ? 'bg-warning' : 'bg-info'}">
                                    ${meterReading.meterType == 'Electricity' ? 'Điện' : 'Nước'}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>Số đồng hồ:</th>
                            <td>${meterReading.meterNumber}</td>
                        </tr>
                        <tr>
                            <th>Chỉ số cũ:</th>
                            <td><fmt:formatNumber value="${meterReading.previousReading}" pattern="#,##0.00" /></td>
                        </tr>
                        <tr>
                            <th>Chỉ số mới:</th>
                            <td><fmt:formatNumber value="${meterReading.currentReading}" pattern="#,##0.00" /></td>
                        </tr>
                        <tr>
                            <th>Tiêu thụ:</th>
                            <td class="fw-bold"><fmt:formatNumber value="${meterReading.consumption}" pattern="#,##0.00" /></td>
                        </tr>
                    </table>
                </div>
                <div class="col-md-6">
                    <h5 class="card-title">Thông tin ghi nhận</h5>
                    <table class="table table-borderless">
                        <tr>
                            <th width="30%">Ngày đọc:</th>
                            <td><fmt:formatDate value="${meterReading.readingDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>Tháng/Năm:</th>
                            <td>${meterReading.readingMonth}/${meterReading.readingYear}</td>
                        </tr>
                        <tr>
                            <th>Trạng thái:</th>
                            <td>
                                <span class="badge ${meterReading.status == 'Active' ? 'bg-success' : 'bg-danger'}">
                                    ${meterReading.status == 'Active' ? 'Hoạt động' : 'Đã xóa'}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>Nhân viên:</th>
                            <td>${meterReading.staffName}</td>
                        </tr>
                        <tr>
                            <th>Ghi chú:</th>
                            <td>${empty meterReading.notes ? 'Không có' : meterReading.notes}</td>
                        </tr>
                    </table>
                </div>
            </div>
            
            <c:if test="${meterReading.meterType == 'Electricity'}">
                <div class="mt-4">
                    <h5 class="card-title">Tính toán tiền điện</h5>
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>Tiêu thụ (kWh)</th>
                                <th>Đơn giá (VNĐ)</th>
                                <th>Thành tiền (VNĐ)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><fmt:formatNumber value="${meterReading.consumption}" pattern="#,##0.00" /></td>
                                <td>3,500</td>
                                <td class="fw-bold text-end">
                                    <fmt:formatNumber value="${meterReading.consumption * 3500}" pattern="#,##0" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
            
            <c:if test="${meterReading.meterType == 'Water'}">
                <div class="mt-4">
                    <h5 class="card-title">Tính toán tiền nước</h5>
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>Tiêu thụ (m³)</th>
                                <th>Đơn giá (VNĐ)</th>
                                <th>Thành tiền (VNĐ)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><fmt:formatNumber value="${meterReading.consumption}" pattern="#,##0.00" /></td>
                                <td>15,000</td>
                                <td class="fw-bold text-end">
                                    <fmt:formatNumber value="${meterReading.consumption * 15000}" pattern="#,##0" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/accountant/footer.jsp" />
