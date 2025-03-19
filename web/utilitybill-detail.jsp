<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Utility Bill Display</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <style>
            :root {
                --primary-color: #3a86ff;
                --secondary-color: #6366f1;
                --success-color: #10b981;
                --danger-color: #ef4444;
                --warning-color: #f59e0b;
                --light-color: #f9fafb;
                --dark-color: #1f2937;
                --border-radius: 12px;
                --box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
                --transition: all 0.3s ease;
            }

            body {
                background-color: #f3f4f6;
                font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                color: #374151;
                line-height: 1.6;
            }

            .bill-container {
                max-width: 800px;
                margin: 40px auto;
                background: #fff;
                border-radius: var(--border-radius);
                box-shadow: var(--box-shadow);
                overflow: hidden;
                position: relative;
            }

            .bill-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                padding: 30px;
                position: relative;
            }

            .bill-logo {
                margin-bottom: 15px;
                font-size: 1.75rem;
                font-weight: 700;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .bill-status {
                position: absolute;
                top: 30px;
                right: 30px;
                padding: 6px 16px;
                border-radius: 30px;
                font-weight: 600;
                font-size: 0.85rem;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .status-paid {
                background-color: var(--success-color);
                color: white;
            }

            .status-unpaid {
                background-color: var(--danger-color);
                color: white;
            }

            .status-pending {
                background-color: var(--warning-color);
                color: var(--dark-color);
            }

            .bill-meta {
                display: flex;
                justify-content: space-between;
                align-items: flex-end;
            }

            .bill-section {
                padding: 25px 30px;
                border-bottom: 1px solid #e5e7eb;
                transition: var(--transition);
            }

            .bill-section:hover {
                background-color: #f9fafb;
            }

            .section-title {
                color: var(--primary-color);
                font-weight: 700;
                margin-bottom: 20px;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .section-title i {
                background-color: rgba(58, 134, 255, 0.1);
                padding: 8px;
                border-radius: 8px;
                color: var(--primary-color);
            }

            .info-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px;
            }

            .info-item {
                margin-bottom: 15px;
            }

            .info-label {
                font-size: 0.85rem;
                color: #6b7280;
                margin-bottom: 5px;
            }

            .info-value {
                font-weight: 600;
                color: #1f2937;
            }

            .consumption-chart {
                height: 200px;
                margin: 25px 0;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            }

            .utility-icons {
                display: flex;
                justify-content: center;
                margin: 20px 0;
            }

            .utility-icon {
                width: 70px;
                height: 70px;
                background-color: rgba(58, 134, 255, 0.1);
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0 10px;
                color: var(--primary-color);
                font-size: 1.75rem;
                box-shadow: 0 5px 15px rgba(58, 134, 255, 0.15);
                transition: var(--transition);
            }

            .utility-icon:hover {
                transform: scale(1.05);
                box-shadow: 0 8px 25px rgba(58, 134, 255, 0.25);
            }

            .cost-breakdown {
                display: flex;
                justify-content: space-between;
                padding: 12px 0;
                border-bottom: 1px dashed #e5e7eb;
            }

            .cost-breakdown:last-child {
                border-bottom: none;
            }

            .cost-label {
                color: #4b5563;
                font-weight: 500;
            }

            .cost-value {
                font-weight: 600;
                color: #1f2937;
            }

            .total-amount {
                background-color: #f3f4f6;
                padding: 20px;
                border-radius: 10px;
                margin-top: 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            }

            .total-amount-label {
                font-weight: 600;
                font-size: 1.1rem;
            }

            .total-amount-value {
                font-size: 1.75rem;
                font-weight: 700;
                color: var(--primary-color);
            }

            .payment-actions {
                display: flex;
                gap: 15px;
                margin-top: 25px;
            }

            .btn-action {
                padding: 12px 20px;
                border-radius: 8px;
                font-weight: 600;
                transition: var(--transition);
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
                flex: 1;
            }

            .btn-primary {
                background: linear-gradient(135deg, var(--primary-color), #4f46e5);
                border: none;
                color: white;
                box-shadow: 0 4px 10px rgba(79, 70, 229, 0.3);
            }

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 15px rgba(79, 70, 229, 0.4);
            }

            .btn-outline {
                border: 2px solid #e5e7eb;
                background: transparent;
                color: #4b5563;
            }

            .btn-outline:hover {
                border-color: var(--primary-color);
                color: var(--primary-color);
            }

            .bill-footer {
                text-align: center;
                color: #6b7280;
                font-size: 0.85rem;
                padding: 20px;
                background-color: #f9fafb;
                border-top: 1px solid #e5e7eb;
            }

            .qr-code {
                display: flex;
                justify-content: center;
                margin: 20px 0;
            }

            .qr-code img {
                width: 120px;
                height: 120px;
                border: 1px solid #e5e7eb;
                padding: 5px;
                border-radius: 8px;
            }

            .bill-actions {
                position: fixed;
                bottom: 30px;
                right: 30px;
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .action-button {
                width: 60px;
                height: 60px;
                border-radius: 30px;
                background: white;
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
                display: flex;
                align-items: center;
                justify-content: center;
                color: var(--primary-color);
                font-size: 1.5rem;
                cursor: pointer;
                transition: var(--transition);
            }

            .action-button:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
            }

            .action-tooltip {
                position: absolute;
                right: 70px;
                background: #1f2937;
                color: white;
                padding: 5px 10px;
                border-radius: 5px;
                font-size: 0.8rem;
                visibility: hidden;
                opacity: 0;
                transition: var(--transition);
                white-space: nowrap;
            }

            .action-button:hover .action-tooltip {
                visibility: visible;
                opacity: 1;
            }
            .consumption-chart {
                height: 200px;
                margin: 25px 0;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            }
            @media (max-width: 768px) {
                .bill-container {
                    margin: 20px 15px;
                }

                .info-grid {
                    grid-template-columns: 1fr;
                }

                .bill-meta {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .bill-meta-right {
                    margin-top: 15px;
                }

                .bill-status {
                    position: static;
                    display: inline-block;
                    margin-bottom: 15px;
                }

                .action-button {
                    width: 50px;
                    height: 50px;
                    font-size: 1.2rem;
                }
            }

            /* Print styles for PDF export */
            @media print {
                body {
                    background-color: white;
                }

                .bill-container {
                    box-shadow: none;
                    margin: 0;
                    max-width: 100%;
                }

                .bill-actions,
                .payment-actions .btn-primary,
                .action-button {
                    display: none !important;
                }

                .section-title i {
                    background-color: transparent;
                }
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div class="container">
            <div class="bill-container" id="bill-container">
                <div class="bill-header">
                    <div id="billStatus" class="bill-status status-unpaid">Unpaid</div>
                    <div class="bill-logo">
                        <i class="fas fa-building"></i> SMART LIVING
                    </div>
                    <div class="bill-meta">
                        <div class="bill-meta-left">
                            <h3>Utility Bill</h3>
                            <h6>Billing Period: <span id="billingPeriod">Month ${utilityBill.billingMonth}/${utilityBill.billingYear}</span></h6>
                        </div>
                        <div class="bill-meta-right">
                            <div>Bill ID: <strong id="billId">BL-${utilityBill.billId}</strong></div>
                            <div>Issue Date: <span id="generatedDate">${utilityBill.formattedGeneratedDate}</span></div>
                        </div>
                    </div>
                </div>

                <div class="bill-section">
                    <h5 class="section-title"><i class="fas fa-user"></i> Customer Information</h5>
                    <div class="info-grid">
                        <div class="info-item">
                            <div class="info-label">Building</div>
                            <div class="info-value" id="buildingName">Golden Heights</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Apartment</div>
                            <div class="info-value" id="apartmentName">${utilityBill.apartment.apartmentName}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Owner</div>
                            <div class="info-value" id="ownerName">${utilityBill.owner.fullName}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Apartment ID</div>
                            <div class="info-value" id="apartmentId">${utilityBill.apartment.apartmentId}</div>
                        </div>
                    </div>
                </div>

                <div class="bill-section">
                    <h5 class="section-title"><i class="fas fa-calendar-alt"></i> Billing Period</h5>
                    <div class="info-grid">
                        <div class="info-item">
                            <div class="info-label">Billing Period</div>
                            <div class="info-value" id="billingMonthYear">Month ${utilityBill.billingMonth}/${utilityBill.billingYear}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">From Date</div>
                            <div class="info-value" id="periodStart">${utilityBill.formattedBillingPeriodStart}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">To Date</div>
                            <div class="info-value" id="periodEnd">${utilityBill.formattedBillingPeriodEnd}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Due Date</div>
                            <div class="info-value" id="dueDate">${utilityBill.formattedDueDate}</div>
                        </div>
                    </div>
                </div>

                <div class="bill-section">
                    <h5 class="section-title"><i class="fas fa-bolt"></i> Utility Consumption</h5>

                    <h6 class="mt-4">Electricity Usage</h6>
                    <div class="cost-breakdown">
                        <div class="cost-label">Consumption</div>
                        <div class="cost-value" id="electricityConsumption">${utilityBill.electricityConsumption} kWh</div>
                    </div>
                    <div class="cost-breakdown">
                        <div class="cost-label">Rate</div>
                        <div class="cost-value">${electricity.formattedUnitPrice}/kWh</div>
                    </div>
                    <div class="cost-breakdown">
                        <div class="cost-label">Total Electricity Cost</div>
                        <div class="cost-value" id="electricityCost">${utilityBill.formattedElectricityCost}</div>
                    </div>

                    <h6 class="mt-4">Water Usage</h6>
                    <div class="cost-breakdown">
                        <div class="cost-label">Consumption</div>
                        <div class="cost-value" id="waterConsumption">${utilityBill.waterConsumption} m³</div>
                    </div>
                    <div class="cost-breakdown">
                        <div class="cost-label">Rate</div>
                        <div class="cost-value">${water.formattedUnitPrice}/m³</div>
                    </div>
                    <div class="cost-breakdown">
                        <div class="cost-label">Total Water Cost</div>
                        <div class="cost-value" id="waterCost">${utilityBill.formattedWaterCost}</div>
                    </div>
                </div>

                <div class="bill-section">
                    <h5 class="section-title"><i class="fas fa-receipt"></i> Payment Summary</h5>
                    <div class="cost-breakdown">
                        <div class="cost-label">Electricity Cost</div>
                        <div class="cost-value">${utilityBill.formattedElectricityCost}</div>
                    </div>
                    <div class="cost-breakdown">
                        <div class="cost-label">Water Cost</div>
                        <div class="cost-value">${utilityBill.formattedWaterCost}</div>
                    </div>
                    <div class="total-amount">
                        <div class="total-amount-label">Total Amount Due</div>
                        <div class="total-amount-value" id="totalAmount">${utilityBill.formattedTotalAmount}</div>
                    </div>

                    <div class="payment-actions">
                        <button class="btn-action btn-primary" 
                                data-id="${utilityBill.invoiceId}" 
                                onclick="location.href = '<%= request.getContextPath() %>/owner/PaymentServlet?invoiceID=${utilityBill.invoiceId}'">
                            <i class="fas fa-credit-card"></i> Pay Now
                        </button>

                        <button class="btn-action btn-outline">
                            <i class="fas fa-history"></i> Payment History
                        </button>
                    </div>
                </div>

                <div class="bill-footer">
                    <p>For any inquiries, please contact: building@gmail.com | Hotline: 1-800-123-4567</p>
                    <p>© 2025 Smart Living - Intelligent Building Management System</p>
                </div>
            </div>

            <div class="bill-actions">
                <div class="action-button">
                    <i class="fas fa-print"></i>
                    <span class="action-tooltip">Print Bill</span>
                </div>
                <div class="action-button">
                    <i class="fas fa-download"></i>
                    <span class="action-tooltip">Download PDF</span>
                </div>
                <div class="action-button">
                    <i class="fas fa-share-alt"></i>
                    <span class="action-tooltip">Share</span>
                </div>
            </div>
        </div>

        <script>
            // Update status color based on payment status
            const status = "${utilityBill.status}";
            const statusElement = document.getElementById('billStatus');

            if (status === 'PAID') {
                statusElement.className = 'bill-status status-paid';
                statusElement.textContent = 'Paid';
            } else if (status === 'PENDING') {
                statusElement.className = 'bill-status status-pending';
                statusElement.textContent = 'Pending';
            } else {
                statusElement.className = 'bill-status status-unpaid';
                statusElement.textContent = 'Unpaid';
            }

            // Print functionality
            document.querySelector('.action-button:nth-child(1)').addEventListener('click', function () {
                window.print();
            });

            // Download PDF functionality
            document.querySelector('.action-button:nth-child(2)').addEventListener('click', function () {
                // This would typically trigger a server-side PDF generation
                alert('PDF download functionality would be implemented here');
            });

            // Share functionality
            document.querySelector('.action-button:nth-child(3)').addEventListener('click', function () {
                if (navigator.share) {
                    navigator.share({
                        title: 'My Utility Bill',
                        text: 'Check out my utility bill for ${utilityBill.billingMonth}/${utilityBill.billingYear}',
                        url: window.location.href,
                    })
                            .catch((error) => console.log('Error sharing', error));
                } else {
                    alert('Web Share API not supported in your browser');
                }
            });
        </script>
    </body>
</html>