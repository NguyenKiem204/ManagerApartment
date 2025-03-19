<%-- 
    Document   : edit-meter-reading
    Created on : Mar 16, 2025, 4:03:22 AM
    Author     : nkiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${pageTitle} - Electricity & Water Meter Management</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon/favicon.png" type="image/x-icon" />

        <style>
            .card {
                border-radius: 15px;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.05);
                margin-bottom: 1.5rem;
            }
            .card-header {
                background-color: #f8f9fa;
                border-bottom: none;
                padding: 1rem 1.5rem;
            }
            .form-control, .form-select {
                border-radius: 10px;
            }
            .btn {
                border-radius: 10px;
            }
            .required-field::after {
                content: "*";
                color: red;
                margin-left: 4px;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>

        <div id="main">
            <div class="container-fluid py-4">
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body d-flex justify-content-between align-items-center">
                                <h2 class="card-title mb-0">
                                    <i class="bi bi-pencil-square text-primary me-2"></i>${pageTitle}
                                </h2>
                                <a href="${pageContext.request.contextPath}/manager-meter-reading" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left me-1"></i>Back to List
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Error alert if any -->
                <c:if test="${not empty errorMessage}">
                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty param.errorMessage}">
                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i> ${param.errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                </c:if>

                <!-- Edit form -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="mb-0">
                                    <i class="bi bi-clipboard-data me-2"></i>Meter Reading Information
                                </h5>
                            </div>
                            <div class="card-body">
                                <form method="post" action="edit-meter-reading" id="editMeterReadingForm">
                                    <!-- Hidden fields -->
                                    <input type="hidden" name="readingId" value="${reading.readingId}">

                                    <div class="row g-3">
                                        <!-- Meter selection -->
                                        <div class="col-md-6">
                                            <label for="meterId" class="form-label required-field">Meter</label>
                                            <select class="form-select" id="meterId" disabled>
                                                <option value="${reading.meterId}" selected>
                                                    ${meterNumber} - ${meterType} - ${apartmentName}
                                                </option>
                                            </select>
                                            <input type="hidden" name="meterId" value="${reading.meterId}">
                                            <input type="hidden" name="previousPage" value="${previousPage}">
                                        </div>

                                        <!-- Reading Month and Year -->
                                        <div class="col-md-3">
                                            <label for="readingMonth" class="form-label required-field">Month</label>
                                            <select class="form-select" id="readingMonth" name="readingMonth" required>
                                                <c:forEach var="month" begin="1" end="12">
                                                    <option value="${month}" ${reading.readingMonth == month ? 'selected' : ''}>
                                                        ${month}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="col-md-3">
                                            <label for="readingYear" class="form-label required-field">Year</label>
                                            <select class="form-select" id="readingYear" name="readingYear" required>
                                                <c:set var="currentYear" value="<%= java.time.LocalDate.now().getYear() %>" />
                                                <c:forEach var="year" begin="${currentYear-2}" end="${currentYear+1}">
                                                    <option value="${year}" ${reading.readingYear == year ? 'selected' : currentYear == year ? 'selected' : ''}>
                                                        ${year}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <!-- Reading Date -->
                                        <div class="col-md-6">
                                            <label for="readingDate" class="form-label required-field">Meter Reading Date</label>
                                            <input type="datetime" class="form-control" id="readingDate" name="readingDate" 
                                                   value="${isEdit ? reading.formattedDate : currentDateTime}" disabled required>
                                        </div>

                                        <!-- Meter Info Display -->
                                        <div class="col-md-6">
                                            <label class="form-label">Meter Information</label>
                                            <div class="p-3 bg-light rounded">
                                                <div id="meterInfoDisplay">
                                                    <c:if test="${isEdit}">
                                                        <p><strong>Meter Number:</strong> <span class="meter-number">${meterNumber}</span></p>
                                                        <p><strong>Apartment:</strong> <span class="apartment-name">${apartmentName}</span></p>
                                                        <p><strong>Meter Type:</strong> <span class="meter-type">${meterType}</span></p>
                                                        </c:if>
                                                        <c:if test="${!isEdit}">
                                                        <p class="text-muted">Select a meter to display information</p>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Previous Reading -->
                                        <div class="col-md-4">
                                            <label for="previousReading" class="form-label required-field">Previous reading</label>
                                            <input type="number" step="0.01" class="form-control" id="previousReading" name="previousReading"
                                                   value="${isEdit ? reading.previousReading : ''}" required>
                                        </div>

                                        <!-- Current Reading -->
                                        <div class="col-md-4">
                                            <label for="currentReading" class="form-label required-field">Current reading</label>
                                            <input type="number" step="0.01" class="form-control" id="currentReading" name="currentReading"
                                                   value="${isEdit ? reading.currentReading : ''}" >
                                        </div>

                                        <!-- Consumption (calculated) -->
                                        <div class="col-md-4">
                                            <label for="consumption" class="form-label">Consumption</label>
                                            <input type="text" class="form-control" id="consumptionDisplay" readonly
                                                   value="${isEdit ? reading.consumption : '0.00'}">
                                        </div>

                                        <!-- Status -->
                                        <div class="col-md-6">
                                            <label for="status" class="form-label">Status</label>
                                            <select class="form-select" id="status" name="status">
                                                <option value="Active" ${reading.status == 'Active' || reading.status == null ? 'selected' : ''}>Active</option>
                                                <option value="Inactive" ${reading.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                <option value="Pending" ${reading.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                            </select>
                                        </div>

                                        <!-- Submit/Cancel buttons -->
                                        <div class="col-12 mt-4">
                                            <div class="d-flex justify-content-end">
                                                <a href="${pageContext.request.contextPath}/accountant/manager-meter-reading" class="btn btn-secondary me-2">
                                                    <i class="bi bi-x-circle me-1"></i>Cancel
                                                </a>
                                                <button type="submit" class="btn btn-primary">
                                                    <i class="bi bi-check-circle me-1"></i>${isEdit ? 'Update' : 'Add'}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap & jQuery JS -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

        <script>
            function calculateConsumption() {
                const previousReading = parseFloat($("#previousReading").val()) || 0;
                const currentReading = parseFloat($("#currentReading").val()) || 0;

                let consumption = 0;
                if (currentReading >= previousReading) {
                    consumption = currentReading - previousReading;
                }
                $("#consumptionDisplay").val(consumption.toFixed(2));
            }
            function updateMeterInfo() {
                const selectedOption = $("#meterId option:selected");

                if (selectedOption.val()) {
                    const meterNumber = selectedOption.data("meter-number");
                    const apartmentName = selectedOption.data("apartment-name");
                    const meterType = selectedOption.data("meter-type");
                    const previousReading = selectedOption.data("previous-reading") || 0;

                    $("#meterInfoDisplay").html(`
                        <p><strong>Meter Number:</strong> <span class="meter-number">${meterNumber}</span></p>
                        <p><strong>Apartment:</strong> <span class="apartment-name">${apartmentName}</span></p>
                        <p><strong>Meter Type:</strong> <span class="meter-type">${meterType}</span></p>
                    `);
                    if (!$("#previousReading").val()) {
                        $("#previousReading").val(previousReading);
                    }
                } else {
                    $("#meterInfoDisplay").html('<p class="text-muted">Select a meter to display information</p>');
                }
            }

            $(document).ready(function () {
                updateMeterInfo();

                $("#meterId").change(updateMeterInfo);
                $("#previousReading, #currentReading").on("input", calculateConsumption);

                $("#editMeterReadingForm").submit(function (event) {
                    const previousReading = parseFloat($("#previousReading").val()) || 0;
                    const currentReading = parseFloat($("#currentReading").val()) || 0;

                    if (currentReading < previousReading) {
                        alert("The current reading cannot be less than the previous reading.");
                        event.preventDefault();
                    }
                });
            });
            document.addEventListener('DOMContentLoaded', function () {
                const editForm = document.getElementById('editMeterReadingForm');

                if (editForm) {
                    editForm.addEventListener('submit', function (event) {
                        let isValid = true;
                        const errorMessages = [];
                        const meterId = document.getElementById('meterId');
                        const previousReading = document.getElementById('previousReading');
                        const currentReading = document.getElementById('currentReading');
                        const readingMonth = document.getElementById('readingMonth');
                        const readingYear = document.getElementById('readingYear');

                        document.querySelectorAll('.error-message').forEach(el => el.remove());
                        document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

                        if (!meterId || meterId.value === '') {
                            isValid = false;
                            addErrorTo(meterId, 'Please select a meter');
                        }

                        if (!previousReading || previousReading.value === '') {
                            isValid = false;
                            addErrorTo(previousReading, 'Please enter the previous reading');
                        }

                        if (!currentReading || currentReading.value === '') {
                            isValid = false;
                            addErrorTo(currentReading, 'Please enter the current reading');
                        }

                        if (!readingMonth || readingMonth.value === '') {
                            isValid = false;
                            addErrorTo(readingMonth, 'Please select a month');
                        }

                        if (!readingYear || readingYear.value === '') {
                            isValid = false;
                            addErrorTo(readingYear, 'Please select a year');
                        }
                        if (previousReading && previousReading.value !== '') {
                            if (isNaN(parseFloat(previousReading.value)) || parseFloat(previousReading.value) < 0) {
                                isValid = false;
                                addErrorTo(previousReading, 'The reading must be a positive number');
                            }
                        }

                        if (currentReading && currentReading.value !== '') {
                            if (isNaN(parseFloat(currentReading.value)) || parseFloat(currentReading.value) < 0) {
                                isValid = false;
                                addErrorTo(currentReading, 'The reading must be a positive number');
                            }
                        }

                        if (previousReading && currentReading &&
                                previousReading.value !== '' && currentReading.value !== '') {
                            if (parseFloat(currentReading.value) < parseFloat(previousReading.value)) {
                                isValid = false;
                                addErrorTo(currentReading, 'The current reading cannot be less than the previous reading');
                            }
                        }

                        if (!isValid) {
                            event.preventDefault();
                            if (errorMessages.length > 0) {
                                const alert = document.createElement('div');
                                alert.className = 'alert alert-danger mt-3 error-shake';
                                alert.innerHTML = '<strong>Please correct the following errors:</strong><ul>' +
                                        errorMessages.map(msg => '<li>' + msg + '</li>').join('') + '</ul>';

                                const cardBody = editForm.closest('.card-body');
                                cardBody.insertBefore(alert, editForm);

                                window.scrollTo({
                                    top: cardBody.offsetTop - 20,
                                    behavior: 'smooth'
                                });
                            }
                        } else {
                            const submitBtn = editForm.querySelector('button[type="submit"]');
                            submitBtn.disabled = true;
                            submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Processing...';
                        }
                    });
                }
                function addErrorTo(element, message) {
                    if (!element)
                        return;

                    element.classList.add('is-invalid');

                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'invalid-feedback error-message';
                    errorDiv.textContent = message;

                    element.parentNode.appendChild(errorDiv);
                    errorMessages.push(message);
                }
                const previousReading = document.getElementById('previousReading');
                const currentReading = document.getElementById('currentReading');
                const consumptionDisplay = document.getElementById('consumptionDisplay');

                if (previousReading && currentReading && consumptionDisplay) {
                    const calculateConsumption = function () {
                        const prev = parseFloat(previousReading.value) || 0;
                        const curr = parseFloat(currentReading.value) || 0;

                        if (!isNaN(prev) && !isNaN(curr)) {
                            const consumption = Math.max(0, curr - prev);
                            consumptionDisplay.value = consumption.toFixed(2);
                            const consumptionWarningEl = document.getElementById('consumptionWarning');
                            if (consumptionWarningEl) {
                                if (consumption > 200) {
                                    consumptionWarningEl.textContent = 'Unusual consumption. Please check again.';
                                    consumptionWarningEl.classList.remove('d-none');
                                } else {
                                    consumptionWarningEl.classList.add('d-none');
                                }
                            } else {
                                const parentElement = consumptionDisplay.parentElement;
                                const warningDiv = document.createElement('div');
                                warningDiv.id = 'consumptionWarning';
                                warningDiv.className = consumption > 100 ? 'text-danger mt-1 small' : 'd-none';
                                warningDiv.textContent = consumption > 100 ? 'Abnormal consumption. Please check again.' : '';
                                parentElement.appendChild(warningDiv);
                            }
                        }
                    };

                    previousReading.addEventListener('input', calculateConsumption);
                    currentReading.addEventListener('input', calculateConsumption);
                    calculateConsumption();
                }
                const meterSelect = document.getElementById('meterId');
                if (meterSelect) {
                    meterSelect.addEventListener('change', function () {
                        updateMeterInfo();

                        const meterId = this.value;
                        if (meterId) {
                            const prevReading = document.getElementById('previousReading');
                            if (!prevReading.value || prevReading.value === '0') {
                                const selectedOption = meterSelect.options[meterSelect.selectedIndex];
                                const lastReading = selectedOption.getAttribute('data-previous-reading');

                                if (lastReading) {
                                    prevReading.value = lastReading;
                                    document.getElementById('currentReading').focus();
                                    if (typeof calculateConsumption === 'function') {
                                        calculateConsumption();
                                    }
                                }
                            }
                        }
                    });
                    if (meterSelect.value) {
                        updateMeterInfo();
                    }
                }
                function updateMeterInfo() {
                    const meterSelect = document.getElementById('meterId');
                    const meterInfoDisplay = document.getElementById('meterInfoDisplay');

                    if (!meterSelect || !meterInfoDisplay)
                        return;

                    if (meterSelect.value) {
                        const selectedOption = meterSelect.options[meterSelect.selectedIndex];
                        const meterNumber = selectedOption.getAttribute('data-meter-number');
                        const apartmentName = selectedOption.getAttribute('data-apartment-name');
                        const meterType = selectedOption.getAttribute('data-meter-type');
                        const meterClass = meterType === 'Electricity' ? 'text-warning' : 'text-info';
                        const meterIcon = meterType === 'Electricity' ? 'bi-lightning-charge-fill' : 'bi-droplet-fill';

                        meterInfoDisplay.innerHTML = `
                <div class="alert alert-light border">
                    <div class="d-flex align-items-center">
                        <i class="bi ${meterIcon} ${meterClass} me-3 fs-4"></i>
                        <div>
                            <div class="fw-bold">${meterNumber}</div>
                            <div>${apartmentName}</div>
                            <div class="small ${meterClass}">
                                <i class="bi ${meterIcon} me-1"></i>
            <c:choose>
                <c:when test="${meterType == 'Electricity'}">Điện</c:when>
                <c:otherwise>Nước</c:otherwise>
            </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
            `;
                    } else {
                        meterInfoDisplay.innerHTML = '<p class="text-muted">Select a meter to display the information</p>';
                    }
                }
                const validateFields = {
                    'previousReading': function (el) {
                        if (el.value === '') {
                            return {valid: false, message: 'Please enter the previous reading.'};
                        }
                        if (isNaN(parseFloat(el.value)) || parseFloat(el.value) < 0) {
                            return {valid: false, message: 'The reading must be a positive number'};
                        }
                        return {valid: true};
                    },
                    'currentReading': function (el) {
                        if (el.value === '') {
                            return {valid: false, message: 'Please enter the current reading'};
                        }
                        if (isNaN(parseFloat(el.value)) || parseFloat(el.value) < 0) {
                            return {valid: false, message: 'The reading must be a positive number'};
                        }

                        const prevReading = document.getElementById('previousReading');
                        if (prevReading && prevReading.value !== '' &&
                                parseFloat(el.value) < parseFloat(prevReading.value)) {
                            return {valid: false, message: 'The current reading cannot be less than the previous reading'};
                        }

                        return {valid: true};
                    },
                    'readingDate': function (el) {
                        if (el.value === '') {
                            return {valid: false, message: 'Please select the reading date'};
                        }

                        const selectedDate = new Date(el.value);
                        const today = new Date();
                        if (selectedDate > today) {
                            return {valid: false, message: 'The reading date cannot be in the future.'};
                        }

                        return {valid: true};
                    }
                };
                for (const fieldId in validateFields) {
                    const field = document.getElementById(fieldId);
                    if (field) {
                        field.addEventListener('blur', function () {
                            validateField(this, validateFields[fieldId]);
                        });

                        field.addEventListener('input', function () {
                            this.classList.remove('is-invalid');
                            const errorEl = this.parentNode.querySelector('.invalid-feedback');
                            if (errorEl)
                                errorEl.remove();
                        });
                    }
                }

                function validateField(element, validationFn) {
                    const result = validationFn(element);
                    const existingError = element.parentNode.querySelector('.invalid-feedback');
                    if (existingError)
                        existingError.remove();

                    element.classList.remove('is-invalid', 'is-valid');

                    if (!result.valid) {
                        element.classList.add('is-invalid');

                        const errorDiv = document.createElement('div');
                        errorDiv.className = 'invalid-feedback';
                        errorDiv.textContent = result.message;
                        element.parentNode.appendChild(errorDiv);
                    } else {
                        element.classList.add('is-valid');
                    }
                }
            });
        </script>
    </body>
</html>