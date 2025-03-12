<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Invoice Manager</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />   
      
        <style>

            .active::-webkit-scrollbar {
                width: 0px;
                height: 0px;
            }

            .active {
                -ms-overflow-style: none;
                scrollbar-width: none;
            }

        </style>
    </head>

    <body>
        <%@include file="menuaccountant.jsp" %>

        <div id="main">

            <div class="container mt-5">
                <div class="card p-4 shadow-lg rounded-4" style="max-width: 1000px; margin: 0 auto;">
                    <h3 class="text-center mb-4">Invoice Details</h3>
                    <div class="mb-3">
                        <H2>Invoice code: <c:out value="${invoice.invoiceID}" /></h2> 
                    </div>

                    <div class="mb-3">
                        <strong>Apartment:</strong> <c:out value="${invoice.apartment.apartmentName}" />
                    </div>
                    <div class="mb-3">
                        <strong>Resident:</strong> <c:out value="${invoice.resident.fullName}" />
                    </div>
                    <div class="mb-3">
                        <strong>Description:</strong> <c:out value="${invoice.description}" />
                    </div>

                    <div class="mb-3">
                        <strong>Public Date:</strong> <c:out value="${invoice.publicDateft}" />
                    </div>
                    <div class="mb-3">
                        <strong>Due Date:</strong> <c:out value="${invoice.dueDateft}" />
                    </div>
                    <c:if test="${invoice.status eq 'Paid'}">


                        <div class="mb-3">
                            <strong>Pay Date:</strong> <c:out value="${invoice.paydateft}" />
                        </div>
                    </c:if>
                    <div class="mb-3">
                        <strong>Status:</strong> 
                        <span class="badge bg-${invoice.status == 'Paid' ? 'success' : 'warning'}">
                            <c:out value="${invoice.status}" />
                        </span>
                    </div>


                    <h5 class="mt-4">Invoice Details</h5>
                    <table class="table table-bordered mt-3">
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

                    <div class="mb-3">
                        <strong>late bill penalty: </strong> $<c:out value="${invoice.muon}" />
                    </div>

                    <div class="mb-3">
                        <strong>Total Amount:</strong> $<c:out value="${invoice.totalAmount}" />
                    </div>

                    <div class="d-flex justify-content-end mt-4">
                        <a href="<%= request.getContextPath() %>/accountant/InvoicesManager" class="btn btn-secondary">
                            <i class="bi bi-arrow-left"></i> Back
                        </a>
                    </div>


                </div>
            </div>

        </div>
    </div>


</body>

</html>