    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Log Management System</title>
             <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png"
                        type="image/x-icon" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
        </head>

        <body>
            <%@include file="menumanager.jsp" %>
            <div id="main">
            <div class="container-fluid py-4">
                <div class="row">
                    <div class="col-12">
                        <h1 class="text-center mb-4">Log Management System</h1>

                        <div class="card">
                            <div class="card-header">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <div class="btn-group" role="group">
                                            <button id="exportLogsBtn" class="btn btn-primary">Export Logs</button>
                                            <button id="importLogsBtn" class="btn btn-outline-primary">Import
                                                Logs</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-md-4">
                                        <input type="text" id="searchInput" class="form-control"
                                            placeholder="Search by filename or staff name">
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" id="dateRangePicker" class="form-control"
                                            placeholder="Select Month range">
                                    </div>
                                    <div class="col-md-4">
                                        <select id="sortSelect" class="form-select">
                                            <option value="">Sort By</option>
                                            <option value="date_asc">Date (Ascending)</option>
                                            <option value="date_desc">Date (Descending)</option>
                                            <option value="records_asc">Records (Ascending)</option>
                                            <option value="records_desc">Records (Descending)</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="table-responsive">
                                    <table id="logsTable" class="table table-striped table-hover">
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID</th>
                                                <th>Staff Name</th>
                                                <th>Filename</th>
                                                <th>Date</th>
                                                <th>Records Count</th>
                                                <th>Type/Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Dynamic content will be inserted here -->
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>

            <div class="modal fade" id="logDetailsModal" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Log Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body" id="logDetailsContent">
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>