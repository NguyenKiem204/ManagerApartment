<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Online Payment</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css" />
        <style>
            body {
                background-color: #f8f9fa;
            }
            .main-content {
                max-width: 1200px;
                margin: auto;
                padding: 20px;
            }
            .payment-container {
                display: flex;
                flex-wrap: wrap;
                gap: 30px;
                margin-top: 50px;
            }
            .card {
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                padding: 25px;
                background: white;
                flex: 1;
            }
            .qr-code img {
                width: 100%;
                max-width: 300px;
                display: block;
                margin: auto;
            }
            .countdown {
                font-size: 20px;
                color: red;
                text-align: center;
            }
            table {
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div class="main-content">
            <h2 class="text-center mt-4">Online Payment</h2>
            <div class="payment-container">
                <div class="col-md-6 text-center">

                    <c:if test="${not empty paymentUrl}">
                        <img src="${paymentUrl}" alt="QR Code" class="img-fluid">
                    </c:if>
                    <c:if test="${empty paymentUrl}">
                        <div class="alert alert-danger">Failed to generate payment URL.</div>
                    </c:if>
                    <p value=""><strong>Account bank number:</strong> 686868922004</p>
                    <p value=""><strong>Transaction Code:</strong> ${transactionId}</p>
                </div>
                <div class="card invoice-details">
                    <h3 class="text-center">Invoice Details</h3>
                    <p><strong>Invoice Code:</strong> <c:out value="${invoice.invoiceID}" /></p>
                    <p><strong>Apartment:</strong> <c:out value="${invoice.apartment.apartmentName}" /></p>
                    <p><strong>Resident:</strong> <c:out value="${invoice.resident.fullName}" /></p>
                    <p><strong>Description:</strong> <c:out value="${invoice.description}" /></p>
                    <p><strong>Public Date:</strong> <c:out value="${invoice.publicDateft}" /></p>
                    <p><strong>Due Date:</strong> <c:out value="${invoice.dueDateft}" /></p>
                    <c:if test="${invoice.status eq 'Paid'}">
                        <p><strong>Pay Date:</strong> <c:out value="${invoice.paydateft}" /></p>
                    </c:if>
                    <p><strong>Status:</strong> 
                        <span class="badge bg-${invoice.status == 'Paid' ? 'success' : 'warning'}">
                            <c:out value="${invoice.status}" />
                        </span>
                    </p>
                    <h5 class="mt-4">Invoice Items</h5>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Description</th>
                                <th>Amount</th>
                                <th>Type Bill</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="detail" items="${invoice.details}" varStatus="loop">
                                <tr>
                                    <td><c:out value="${loop.index + 1}" /></td>
                                    <td><c:out value="${detail.description}" /></td>
                                    <td><c:out value="${detail.amount}" /></td>
                                    <td><c:out value="${detail.billType}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <p><strong>Late Bill Penalty:</strong> $<c:out value="${invoice.muon}" /></p>
                    <p><strong>Total Amount:</strong> $<c:out value="${invoice.totalAmount}" /></p>
                    <div class="d-flex justify-content-end mt-4">
                        <a href="<%= request.getContextPath() %>/owner/ViewInvoice" class="btn btn-secondary">
                            <i class="fa fa-arrow-left"></i> Cancel
                        </a>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="transactionNotFoundModal" tabindex="-1" aria-labelledby="transactionNotFoundModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="transactionNotFoundModalLabel">Thông báo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Mã giao dịch không tồn tại hoặc đã bị xóa. Bạn sẽ được chuyển hướng về trang danh sách hóa đơn.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>

        <script>

            function checkPaymentStatus() {
                const transactionId = "${transactionId}";
                const invoiceID = "${invoice.invoiceID}";

                fetch('<%= request.getContextPath() %>/owner/CheckPaymentStatusServlet?transactionId=' + transactionId + '&invoiceID=' + invoiceID)
                        .then(response => response.json())
                        .then(data => {
                            if (data.status === "completed") {
                                window.location.href = "<%= request.getContextPath() %>/owner/paymentSuccess?invoiceID=" + invoiceID;
                            } else if (data.status === "not_found") {
                                // Hiển thị modal thông báo
                                const modal = new bootstrap.Modal(document.getElementById('transactionNotFoundModal'));
                                modal.show();

                                // Chuyển hướng về trang ViewInvoice sau khi đóng modal
                                document.getElementById('transactionNotFoundModal').addEventListener('hidden.bs.modal', function () {
                                    window.location.href = "<%= request.getContextPath() %>/owner/ViewInvoice";
                                });
                            } else {
                                setTimeout(checkPaymentStatus, 1000); // Tiếp tục kiểm tra trạng thái
                            }
                        })
                        .catch(error => console.error('Lỗi kiểm tra trạng thái thanh toán:', error));
            }

            document.addEventListener("DOMContentLoaded", function () {
                checkPaymentStatus();
            });
        </script>

        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
    </body>
</html>