<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .forgot-password-container {
                max-width: 400px;
                margin: 100px auto;
                padding: 20px;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            .forgot-password-container h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #ff8c00;
            }
            .btn-custom {
                background-color: #ff8c00;
                color: #fff;
            }
            .btn-custom:hover {
                background-color: #e67e00;
            }
            a {
                color: #ff8c00;
            }
            a:hover {
                color: #e67e00;
            }
        </style>
    </head>
    <body>

        <div class="forgot-password-container">
            <h2>Forgot Password</h2>
            <p class="text-center">Enter your email address to reset your password.</p>
            <form action="send-reset-link" method="POST">
                <div class="mb-3">
                    <label for="email" class="form-label">Email Address</label>
                    <input type="email" class="form-control" onfocus="hideError()" id="email" name="email" placeholder="Enter your email" style="margin-bottom: 10px" required>
                    <p style="font-size: 15px; margin-top: 15px; margin-bottom: -10px; font-weight: 500; color: red;"
               class="error" id="error-message">${requestScope.error}</p>
                </div>
                <button type="submit" class="btn btn-custom w-100">Send Reset Link</button>
            </form>
            <div class="text-center mt-3">
                <a href="/login">Back to Login</a>
            </div>
        </div>
         <script type="text/javascript">
            function hideError() {
                document.getElementById('error-message').style.visibility = 'hidden';
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>