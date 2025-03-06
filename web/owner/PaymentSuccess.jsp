<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment Success</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.css" />
        <style>
            body {
                background-color: #f8f9fa;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .success-container {
                text-align: center;
                padding: 40px;
                background: white;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <h1>Payment Successful!</h1>
        <a href="<%= request.getContextPath() %>/ViewInvoice">Back to Invoices</a>

        <script>
            let timeLeft = 5;
            let countdownElement = document.getElementById("countdown");

            function updateCountdown() {
                countdownElement.textContent = timeLeft;
                if (timeLeft > 0) {
                    timeLeft--;
                    setTimeout(updateCountdown, 1000);
                } else {
                    window.location.href = "<%= request.getContextPath() %>/InvoicesManager";
                }
            }

            updateCountdown();
        </script>
        <%-- Xóa thông báo khỏi session sau khi hiển thị --%>
        <% session.removeAttribute("paymentSuccess"); %>
    </body>
</html>