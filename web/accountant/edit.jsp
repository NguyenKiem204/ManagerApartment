<%-- 
    Document   : edit
    Created on : Mar 12, 2025, 10:15:40 PM
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
        <h2>
            <i class="bi bi-clipboard-data me-2"></i>
            ${meterReading == null ? 'Thêm chỉ số mới' : 'Cập nhật chỉ số'}
        </h2>
        <a href="${pageContext.request.contextPath}/meter-reading" class="btn btn-secondary">
            <i class="bi bi-arrow-left me-1"></i>Quay lại
        </a>
    </div>
    
    <div class="card">
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/meter-reading${meterReading == null ? '' : '/update'}">
                <c:if test="${meterReading != null}">
                    <input type="hidden" name="readingId" value="${meterReading.readingId}">
                </c:if>
                
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="meterId" class="form-label">Đồng hồ <span class="text-danger">*</span></label>
                        <select class="form-select" id="meterId" name="meterId" required ${meterReading != null ? 'disabled' : ''}>
                            <option value="" disabled selected>Chọn đồng hồ</option>
                            <c:forEach var="meter" items="${meters}">
                                <option value="${meter.meterId}" 
                                    ${meterReading != null && meterReading.meterId == meter.meterId ? 'selected' : ''}>
                                    ${meter.meterNumber} - ${meter.meterType} - ${meter.apartmentName}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${meterReading != null}">
                            <input type="hidden" name="meterId" value="${meterReading.meterId}">
                        </c:if>
                    </div>
                    <div class="col-md-6">
                        <label for="readingDate" class="form-label">Ngày đọc <span class="text-danger">*</span></label>
                        <input type="datetime-local" class="form-control" id="readingDate" name="readingDate" required
                               value="<c:if test="${meterReading != null}"><fmt:formatDate value="${meterReading.readingDate}" pattern="yyyy-MM-dd'T'HH:mm" /></c:if>">
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="previousReading" class="form-label">Chỉ số cũ <span class="text-danger">*</span></label>
                        <input type="number" step="0.01" class="form-control" id="previousReading" name="previousReading" required
                               value="${meterReading != null ? meterReading.previousReading : ''}">
                    </div>
                    <div class="col-md-6">
                        <label for="currentReading" class="form-label">Chỉ số mới <span class="text-danger">*</span></label>
                        <input type="number" step="0.01" class="form-control" id="currentReading" name="currentReading" required
                               value="${meterReading != null ? meterReading.currentReading : ''}">
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-12">
                        <label for="consumptionPreview" class="form-label">Tiêu thụ (tự động tính)</label>
                        <input type="text" class="form-control" id="consumptionPreview" readonly
                               value="${meterReading != null ? meterReading.consumption : ''}">
                    </div>
                </div>
                
                <div class="text-end">
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/meter-reading'">
                        Hủy
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save me-1"></i>${meterReading == null ? 'Lưu' : 'Cập nhật'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Calculate consumption when current or previous reading changes
    document.addEventListener('DOMContentLoaded', function() {
        const previousReading = document.getElementById('previousReading');
        const currentReading = document.getElementById('currentReading');
        const consumptionPreview = document.getElementById('consumptionPreview');
        
        function updateConsumption() {
            if (previousReading.value && currentReading.value) {
                const prev = parseFloat(previousReading.value);
                const curr = parseFloat(currentReading.value);
                const consumption = curr - prev;
                
                consumptionPreview.value = consumption.toFixed(2);
                
                // Add validation feedback
                if (consumption < 0) {
                    currentReading.classList.add('is-invalid');
                    consumptionPreview.classList.add('is-invalid');
                } else {
                    currentReading.classList.remove('is-invalid');
                    consumptionPreview.classList.remove('is-invalid');
                }
            } else {
                consumptionPreview.value = '';
            }
        }
        
        previousReading.addEventListener('input', updateConsumption);
        currentReading.addEventListener('input', updateConsumption);
        
        // Initial calculation
        updateConsumption();
    });
</script>

<jsp:include page="/accountant/footer.jsp" />
