<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Electricity and Water Meter Management</title>
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
            .statistic-card {
                border-left: 4px solid;
                transition: transform 0.3s;
            }
            .statistic-card:hover {
                transform: translateY(-5px);
            }
            .electricity-card {
                border-left-color: #fd7e14;
            }
            .water-card {
                border-left-color: #0dcaf0;
            }
            .total-card {
                border-left-color: #0d6efd;
            }
            .form-control, .form-select {
                border-radius: 10px;
            }
            .btn {
                border-radius: 10px;
            }
            .table {
                vertical-align: middle;
            }
            .table th {
                background-color: #f1f8ff !important;
            }
            .badge {
                font-size: 0.85em;
                padding: 0.5em 0.85em;
                border-radius: 8px;
            }
            .label-ew{
                width: 81px;
            }
            .actions-btn {
                margin: 0 2px;
            }
            #toast-container {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 9999;
            }
            .highlight-row {
                animation: highlight 2s ease-in-out;
            }
            .select2-container--bootstrap-5 .select2-selection__arrow {
                display: none !important;
            }

            .select2-container--bootstrap-5 .select2-selection {
                position: relative;
                min-height: calc(2.25rem + 2px) !important;
                border: 1px solid #ced4da !important;
                border-radius: 0.375rem !important;
                background-color: #fff !important;
                display: flex !important;
                align-items: center !important;
                padding: 0.375rem 1rem !important;
            }

            .select2-container--bootstrap-5 .select2-selection::after {
                content: "\F282";
                font-family: "Bootstrap-icons";
                font-size: 1rem;
                color: #6c757d;
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
            }

            .select2-container--bootstrap-5 .select2-selection__rendered {
                line-height: normal !important;
                display: flex !important;
                align-items: center !important;
            }
            .select2-container--bootstrap-5 .select2-dropdown {
                max-height: 230px !important;
                overflow-y: auto !important;
            }

            @keyframes highlight {
                0%, 100% {
                    background-color: transparent;
                }
                50% {
                    background-color: rgba(13, 110, 253, 0.2);
                }
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>

        <!-- Toast container for notifications -->
        <div id="toast-container" class="toast-container"></div>

        <div id="main">
            <div class="container-fluid py-4">
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body d-flex justify-content-between align-items-center">
                                <h2 class="card-title mb-0">
                                    <i class="bi bi-clipboard-data text-primary me-2"></i>Electricity and Water Meter Management
                                </h2>

                            </div>
                        </div>
                    </div>
                </div>

                <!-- Statistics Section -->
                <div class="row mb-4">
                    <div class="col-md-4">
                        <div class="card statistic-card electricity-card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="text-muted">Total Electricity Consumption</h6>
                                        <h3>
                                            <fmt:formatNumber value="${stats.totalElectricityConsumption}" pattern="#,##0.00" /> kWh
                                        </h3>
                                        <p class="mb-0 text-muted">Average: 
                                            <fmt:formatNumber value="${stats.avgElectricityConsumption}" pattern="#,##0.00" /> kWh
                                        </p>
                                    </div>
                                    <div class="align-self-center">
                                        <i style="color: #F9A825; font-size: 35px" class="fa-solid fa-lightbulb"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card statistic-card water-card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="text-muted">Total Water Consumption</h6>
                                        <h3>
                                            <fmt:formatNumber value="${stats.totalWaterConsumption}" pattern="#,##0.00" /> m³
                                        </h3>
                                        <p class="mb-0 text-muted">Average: 
                                            <fmt:formatNumber value="${stats.avgWaterConsumption}" pattern="#,##0.00" /> m³
                                        </p>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="bi bi-droplet-fill text-info fs-1"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card statistic-card total-card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="text-muted">Total Number of Readings</h6>
                                        <h3>${stats.totalReadings}</h3>
                                        <p class="mb-0 text-muted">Electricity: ${stats.electricityCount} | Water: ${stats.waterCount}</p>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="bi bi-graph-up-arrow text-primary fs-1"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Alert for import errors/success -->
                <c:if test="${not empty importError || not empty importErrors || not empty importSuccess}">
                    <div class="row mb-4">
                        <div class="col-12">
                            <c:if test="${not empty importError}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill me-2"></i>${importError}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>

                            <c:if test="${not empty importErrors}">
                                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                    <i class="bi bi-exclamation-circle-fill me-2"></i>File Import Error:
                                    <ul class="mb-0 mt-2">
                                        <c:forEach var="error" items="${importErrors}">
                                            <li>${error}</li>
                                            </c:forEach>
                                    </ul>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>

                            <c:if test="${not empty importSuccess}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <i class="bi bi-check-circle-fill me-2"></i>${importSuccess}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty param.success || not empty param.error}">
                    <div class="row mb-4">
                        <div class="col-12">
                            <c:if test="${not empty param.error}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill me-2"></i>${param.error}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>

                            <c:if test="${not empty param.success}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <i class="bi bi-check-circle-fill me-2"></i>${param.success}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>


                <!-- Filter controls -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="bi bi-funnel-fill me-2"></i> Filter & Data Management</h5>
                    </div>
                    <div class="card-body">
                        <form method="get" action="manager-meter-reading" class="row g-3" id="filterForm">
                            <div class="col-md-3">
                                <label for="month" class="form-label">Month</label>
                                <select class="form-select" id="month" name="month">
                                    <c:forEach var="i" begin="1" end="12">
                                        <option value="${i}" ${currentMonth == i ? 'selected' : ''}>${i}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="year" class="form-label">Year</label>
                                <select class="form-select" id="year" name="year">
                                    <c:forEach var="i" begin="2020" end="2030">
                                        <option value="${i}" ${currentYear == i ? 'selected' : ''}>${i}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="apartment" class="form-label">Apartment</label>
                                <select class="form-select" id="apartment" name="apartment">
                                    <option value="" <c:if test="${empty param.apartment}">selected</c:if>>All Apartment</option>
                                    <c:forEach var="apartment" items="${apartments}">
                                        <option value="${apartment.apartmentId}" <c:if test="${param.apartment == apartment.apartmentId}">selected</c:if>>
                                            ${apartment.apartmentName} - ${empty apartment.owner.fullName ? 'N/A' : apartment.owner.fullName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary w-100">
                                    <i class="bi bi-search me-1"></i> Filter
                                </button>
                            </div>
                        </form>

                        <hr>

                        <div class="row">
                            <div class="col-md-6 d-flex flex-column">
                                <form method="post" action="manager-meter-reading" enctype="multipart/form-data" id="importForm" class="d-flex flex-column h-100">
                                    <input type="hidden" name="action" value="import">
                                    <input type="hidden" name="importMonth" value="${currentMonth}">
                                    <input type="hidden" name="importYear" value="${currentYear}">
                                    <label for="excelFile" class="form-label">Select Excel File</label>
                                    <input type="file" class="form-control mb-2" id="excelFile" name="excelFile" accept=".xlsx" required>
                                    <div class="form-text">Only .xlsx files are supported</div>
                                    <div class="mt-auto">
                                        <button type="submit" class="btn btn-success w-100">
                                            <i class="bi bi-cloud-upload me-1"></i> Import Data
                                        </button>
                                        <a href="manager-meter-reading?action=template" class="btn btn-outline-primary w-100 mt-2">
                                            <i class="bi bi-download me-1"></i> Download Template
                                        </a>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 d-flex flex-column">
                                <form method="get" action="manager-meter-reading" id="exportForm" class="d-flex flex-column h-100">
                                    <input type="hidden" name="action" value="export">
                                    <input type="hidden" name="month" value="${currentMonth}">
                                    <input type="hidden" name="year" value="${currentYear}">
                                    <input type="hidden" name="apartment" value="${param.apartment}">
                                    <label class="form-label">Export Report</label>
                                    <div class="form-text mb-3">
                                        Export Monthly Electricity and Water Report ${currentMonth}/${currentYear}
                                        <c:if test="${not empty param.apartment}"> for selected apartment</c:if>
                                        </div>
                                        <div class="mt-auto">
                                            <button type="submit" class="btn btn-primary w-100">
                                                <i class="bi bi-file-earmark-excel me-1"></i> Export to Excel
                                            </button>
                                            <button type="button" class="btn btn-outline-secondary w-100 mt-2" id="generateBillsBtn">
                                                <i class="bi bi-receipt me-1"></i> Generate Invoice
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Meter Readings Table -->
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">
                                        <i class="bi bi-table me-2"></i>Monthly Electricity and Water Meter Reading ${currentMonth}/${currentYear}
                                </h5>
                                <span class="badge bg-primary">${meterReadings.size()} Meter Reading</span>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover" id="meterReadingsTable">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Apartment</th>
                                                <th>Meter Type</th>
                                                <th>Meter Number</th>
                                                <th>Reading Date</th>
                                                <th>Previous Reading</th>
                                                <th>Current Reading</th>
                                                <th>Consumption</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="reading" items="${meterReadings}">
                                                <tr id="reading-${reading.readingId}">
                                                    <td>${reading.readingId}</td>
                                                    <td>${reading.apartmentName}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${reading.meterType eq 'Electricity'}">
                                                                <span class="label-ew badge bg-warning text-dark">Electricity</span>
                                                            </c:when>
                                                            <c:when test="${reading.meterType eq 'Water'}">
                                                                <span class="label-ew badge bg-info text-dark">Water</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">${reading.meterType}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${reading.meterNumber}</td>
                                                    <td>
                                                        ${reading.formattedDate}
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${reading.previousReading}" pattern="#,##0.##" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${reading.currentReading}" pattern="#,##0.##" />
                                                    </td>
                                                    <td class="fw-bold">
                                                        <fmt:formatNumber value="${reading.consumption}" pattern="#,##0.##" /> 
                                                        <span class="text-muted">
                                                            ${reading.meterType eq 'Electricity' ? 'kWh' : 'm³'}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <div class="d-flex">
                                                            <a href="edit-meter-reading?id=${reading.readingId}" class="btn btn-sm btn-outline-primary actions-btn" title="Update">
                                                                <i class="bi bi-pencil-square"></i>
                                                            </a>
                                                            <button type="button" class="btn btn-sm btn-outline-danger actions-btn delete-reading" 
                                                                    data-id="${reading.readingId}" title="Delete">
                                                                <i class="bi bi-trash"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <!-- Empty state -->
                                <c:if test="${empty meterReadings}">
                                    <div class="text-center p-5">
                                        <i class="bi bi-clipboard-x text-muted fs-1"></i>
                                        <p class="mt-3 mb-0">No electricity and water meter data for this month</p>
                                        <p class="text-muted">Please enter data or select a different month</p>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this reading?</p>
                        <p class="text-danger">Note: This action cannot be undone.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <form id="deleteForm" method="get" action="delete-meter-reading">
                            <input type="hidden" id="deleteReadingId" name="readingId" value="">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Generate Bills Modal -->
        <div class="modal fade" id="generateBillsModal" tabindex="-1" aria-labelledby="generateBillsModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="generateBillsModalLabel">
                            <i class="bi bi-receipt-cutoff me-2"></i>Generate Electricity and Water Bill
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="generateBillsForm" method="post" action="${pageContext.request.contextPath}/accountant/generate-utility-bills">
                            <input type="hidden" name="month" value="${currentMonth}">
                            <input type="hidden" name="year" value="${currentYear}">
                            <input type="hidden" name="apartment" value="${param.apartment}">

                            <div class="alert alert-info">
                                <i class="bi bi-info-circle me-2"></i>
                                You are about to generate the electricity and water bill for the month <strong>${currentMonth}/${currentYear}</strong>
                            </div>

                            <div class="mb-3">
                                <label for="paymentDueDate" class="form-label">Payment Due Date</label>
                                <input type="date" class="form-control" id="datePicker-future" name="paymentDueDate" placeholder="dd/MM/yyyy" required>
                                <div class="form-text">Customers must make payment before this date</div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Billing Scope</label>
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="radio" name="billScope" id="allApartments" value="all" checked>
                                    <label class="form-check-label" for="allApartments">
                                        <i class="bi bi-buildings me-1"></i>All Apartments
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="billScope" id="selectedApartment" value="selected" 
                                           ${not empty param.apartment ? 'checked' : ''} ${empty param.apartment ? 'disabled' : ''}>
                                    <label class="form-check-label" for="selectedApartment">
                                        <i class="bi bi-building me-1"></i>Selected Apartments Only
                                        <c:if test="${not empty param.apartment}">
                                            <span class="badge bg-primary ms-1">
                                                <c:set var="apartmentId" value="${param.apartment}" />
                                                <c:forEach var="a" items="${apartments}">
                                                    <c:if test="${a.apartmentId eq apartmentId}">
                                                        ${a.apartmentName}
                                                    </c:if>
                                                </c:forEach>
                                            </span>
                                        </c:if>
                                    </label>
                                    <c:if test="${empty param.apartment}">
                                        <div class="form-text text-muted">Select a specific apartment to enable this option</div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="alert alert-warning">
                                <i class="bi bi-exclamation-triangle me-2"></i>
                                Note: The system will skip apartments that already have a bill for this month
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="bi bi-x-circle me-1"></i>Cancel
                        </button>
                        <button type="submit" form="generateBillsForm" class="btn btn-primary">
                            <i class="bi bi-receipt me-1"></i>Generate Bill
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('#apartment').select2({
                    placeholder: false,
                    theme: 'bootstrap-5',
                    allowClear: false,
                    width: '100%',
                    templateResult: function (state) {
                        return $('<span>' + state.text + '</span>');
                    },
                    templateSelection: function (state) {
                        return $('<span>' + state.text + '</span>');
                    }
                });
            });

            document.addEventListener('DOMContentLoaded', function () {
                function setDefaultDueDate() {
                    const today = new Date();
                    const nextMonth = new Date(today.getFullYear(), today.getMonth() + 2, 0);

                    const yyyy = nextMonth.getFullYear();
                    const mm = String(nextMonth.getMonth() + 1).padStart(2, '0');
                    const dd = String(nextMonth.getDate()).padStart(2, '0');
                    const formattedDate = `${yyyy}-${mm}-${dd}`;

                                const paymentDueDateInput = document.getElementById('paymentDueDate');
                                if (paymentDueDateInput) {
                                    paymentDueDateInput.value = formattedDate;
                                    paymentDueDateInput.min = formattedDate;
                                }
                            }
                            setDefaultDueDate();

                            const generateBillsForm = document.getElementById('generateBillsForm');
                            if (generateBillsForm) {
                                generateBillsForm.addEventListener('submit', function (event) {
                                    const submitButton = generateBillsForm.querySelector('button[type="submit"]');
                                    if (submitButton) {
                                        submitButton.innerHTML = '<i class="bi bi-hourglass-split me-1"></i>Generating Bill...';
                                        submitButton.disabled = true;
                                    }
                                    setTimeout(() => {
                                        generateBillsForm.submit();
                                    }, 500);
                                });
                            }

                            const generateBillsModal = document.getElementById('generateBillsModal');
                            if (generateBillsModal) {
                                generateBillsModal.addEventListener('show.bs.modal', function () {
                                    setDefaultDueDate();
                                    const submitButton = generateBillsForm.querySelector('button[type="submit"]');
                                    if (submitButton) {
                                        submitButton.innerHTML = '<i class="bi bi-receipt me-1"></i>Generating Bill';
                                        submitButton.disabled = false;
                                    }
                                });
                            }
                        });

                        document.addEventListener('DOMContentLoaded', function () {
                            document.getElementById('filterForm').addEventListener('submit', function () {
                                document.getElementById('importMonth').value = document.getElementById('month').value;
                                document.getElementById('importYear').value = document.getElementById('year').value;
                            });

                            document.getElementById('month').addEventListener('change', function () {
                                document.getElementById('exportMonth').value = this.value;
                            });

                            document.getElementById('year').addEventListener('change', function () {
                                document.getElementById('exportYear').value = this.value;
                            });

                            document.getElementById('apartment').addEventListener('change', function () {
                                document.getElementById('exportApartment').value = this.value;
                            });

                            // Set default payment due date to end of next month
//                            const today = new Date();
//                            const nextMonth = new Date(today.getFullYear(), today.getMonth() + 2, 0);
//                            document.getElementById('paymentDueDate').valueAsDate = nextMonth;

                            const deleteButtons = document.querySelectorAll('.delete-reading');
                            const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));

                            deleteButtons.forEach(button => {
                                button.addEventListener('click', function () {
                                    const readingId = this.getAttribute('data-id');
                                    document.getElementById('deleteReadingId').value = readingId;
                                    deleteModal.show();
                                });
                            });
                            document.getElementById('generateBillsBtn').addEventListener('click', function () {
                                const generateBillsModal = new bootstrap.Modal(document.getElementById('generateBillsModal'));
                                generateBillsModal.show();
                                const apartmentValue = document.getElementById('apartment').value;
                                if (apartmentValue) {
                                    document.getElementById('selectedApartment').checked = true;
                                }
                            });
                            window.showToast = function (message, type = 'success') {
                                const toastContainer = document.getElementById('toast-container');
                                const toastId = 'toast-' + Date.now();
                                const bgClass = type === 'success' ? 'bg-success' : type === 'danger' ? 'bg-danger' : 'bg-warning';
                                const iconClass = type === 'success' ? 'bi-check-circle-fill' : type === 'danger' ? 'bi-exclamation-circle-fill' : 'bi-exclamation-triangle-fill';

                                const toastHtml = `
                        <div id="${toastId}" class="toast align-items-center text-white ${bgClass} border-0" role="alert" aria-live="assertive" aria-atomic="true">
                            <div class="d-flex">
                                <div class="toast-body">
                                    <i class="bi ${iconClass} me-2"></i>${message}
                                </div>
                                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                            </div>
                        </div>
                    `;

                                toastContainer.insertAdjacentHTML('beforeend', toastHtml);
                                const toastElement = document.getElementById(toastId);
                                const toast = new bootstrap.Toast(toastElement, {autohide: true, delay: 3000});
                                toast.show();
                                toastElement.addEventListener('hidden.bs.toast', function () {
                                    toastElement.remove();
                                });
                            };
                            const urlParams = new URLSearchParams(window.location.search);
                            const successMsg = urlParams.get('success');
                            const errorMsg = urlParams.get('error');

                            if (successMsg) {
                                window.showToast(decodeURIComponent(successMsg), 'success');
                            }

                            if (errorMsg) {
                                window.showToast(decodeURIComponent(errorMsg), 'danger');
                            }
                            const highlightReadingId = urlParams.get('highlight');
                            if (highlightReadingId) {
                                const row = document.getElementById('reading-' + highlightReadingId);
                                if (row) {
                                    row.classList.add('highlight-row');
                                    row.scrollIntoView({behavior: 'smooth', block: 'center'});
                                }
                            }
                        });
        </script>
    </body>
</html>