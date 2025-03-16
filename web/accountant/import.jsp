<%-- 
    Document   : import
    Created on : Mar 12, 2025, 10:18:53 PM
    Author     : nkiem
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/accountant/header.jsp">
    <jsp:param name="activePage" value="meter-reading-import" />
</jsp:include>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-file-earmark-arrow-up me-2"></i>Import Chỉ số Điện Nước</h2>
        <div>
            <a href="${pageContext.request.contextPath}/meter-reading/template" class="btn btn-outline-primary">
                <i class="bi bi-file-earmark-excel me-1"></i>Tải Template
            </a>
            <a href="${pageContext.request.contextPath}/meter-reading" class="btn btn-secondary ms-2">
                <i class="bi bi-arrow-left me-1"></i>Quay lại
            </a>
        </div>
    </div>
    
    <div class="row">
        <div class="col-lg-6">
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Tải lên file Excel</h5>
                    <p class="card-text">Vui lòng sử dụng template Excel chuẩn để tránh lỗi khi import.</p>
                    
                    <form method="post" action="${pageContext.request.contextPath}/meter-reading/import" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="month" class="form-label">Tháng</label>
                            <select class="form-select" id="month" name="month" required>
                                <c:forEach var="i" begin="1" end="12">
                                    <option value="${i}" ${currentMonth == i ? 'selected' : ''}>${i}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="year" class="form-label">Năm</label>
                            <select class="form-select" id="year" name="year" required>
                                <c:forEach var="i" begin="2020" end="2030">
                                    <option value="${i}" ${currentYear == i ? 'selected' : ''}>${i}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="excelFile" class="form-label">File Excel</label>
                            <input type="file" class="form-control" id="excelFile" name="excelFile" accept=".xlsx" required>
                            <div class="form-text">Chỉ hỗ trợ định dạng .xlsx (Excel 2007+)</div>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="overwrite" name="overwrite">
                            <label class="form-check-label" for="overwrite">Ghi đè dữ liệu đã tồn tại</label>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-upload me-1"></i>Tải lên và Import
                        </button>
                    </form>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Hướng dẫn</h5>
                    <ol>
                        <li>Tải xuống template Excel bằng cách nhấn vào nút <strong>Tải Template</strong>.</li>
                        <li>Điền thông tin chỉ số vào các ô tương ứng trong file Excel.</li>
                        <li>Chỉ điền vào các ô màu trắng, không sửa các ô màu xám.</li>
                        <li>Chỉ số mới phải lớn hơn hoặc bằng chỉ số cũ.</li>
                        <li>Không được để trống các ô bắt buộc.</li>
                        <li>Lưu file và tải lên hệ thống.</li>
                    </ol>
                </div>
            </div>
        </div>
        
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Cấu trúc file Excel</h5>
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead class="table-light">
                                <tr>
                                    <th>Cột</th>
                                    <th>Mô tả</th>
                                    <th>Định dạng</th>
                                    <th>Bắt buộc</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>A</td>
                                    <td>Số đồng hồ</td>
                                    <td>Văn bản</td>
                                    <td class="text-center"><i class="bi bi-check-circle text-success"></i></td>
                                </tr>
                                <tr>
                                    <td>B</td>
                                    <td>Loại đồng hồ</td>
                                    <td>Electricity hoặc Water</td>
                                    <td class="text-center"><i class="bi bi-check-circle text-success"></i></td>
                                </tr>
                                <tr>
                                    <td>C</td>
                                    <td>Chỉ số cũ</td>
                                    <td>Số (có thể có phần thập phân)</td>
                                    <td class="text-center"><i class="bi bi-check-circle text-success"></i></td>
                                </tr>
                                <tr>
                                    <td>D</td>
                                    <td>Chỉ số mới</td>
                                    <td>Số (có thể có phần thập phân)</td>
                                    <td class="text-center"><i class="bi bi-check-circle text-success"></i></td>
                                </tr>
                                <tr>
                                    <td>E</td>
                                    <td>Tiêu thụ</td>
                                    <td>Tự động tính (Mới - Cũ)</td>
                                    <td class="text-center"><i class="bi bi-dash text-secondary"></i></td>
                                </tr>
                                <tr>
                                    <td>F</td>
                                    <td>Ngày đọc</td>
                                    <td>DD/MM/YYYY</td>
                                    <td class="text-center"><i class="bi bi-check-circle text-success"></i></td>
                                </tr>
                                <tr>
                                    <td>G</td>
                                    <td>Ghi chú</td>
                                    <td>Văn bản</td>
                                    <td class="text-center"><i class="bi bi-dash text-secondary"></i></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <!-- Import results -->
            <c:if test="${not empty importResults}">
                <div class="card mt-4">
                    <div class="card-header bg-primary text-white">
                        <h5 class="card-title mb-0">Kết quả Import</h5>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-info">
                            <strong>Tổng số bản ghi:</strong> ${importResults.totalRecords}<br>
                            <strong>Thành công:</strong> ${importResults.successCount}<br>
                            <strong>Thất bại:</strong> ${importResults.errorCount}
                        </div>
                        
                        <c:if test="${importResults.errorCount > 0}">
                            <h6>Danh sách lỗi:</h6>
                            <div class="table-responsive">
                                <table class="table table-sm table-bordered">
                                    <thead class="table-danger">
                                        <tr>
                                            <th>Dòng</th>
                                            <th>Nội dung lỗi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="error" items="${importResults.errors}">
                                            <tr>
                                                <td>${error.rowNumber}</td>
                                                <td>${error.message}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />