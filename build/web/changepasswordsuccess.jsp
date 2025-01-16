<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đổi Mật Khẩu Thành Công</title>
    <meta http-equiv="refresh" content="3;url=login">  <!-- Tự động chuyển hướng sau 3 giây -->
    <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
    <script>
        window.onload = function() {
            let countdown = 3;
            const countdownElement = document.getElementById("countdown");
            const interval = setInterval(function() {
                countdownElement.textContent = countdown;
                countdown--;

                if (countdown < 0) {
                    clearInterval(interval);
                    countdownElement.textContent = 0;
                }
            }, 1000);
        };
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            text-align: center;
        }

        .container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 400px;
            width: 100%;
        }

        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
        }

        p {
            margin-bottom: 20px;
            font-size: 16px;
        }

        .redirect-message {
            font-weight: bold;
            color: #555;
        }

        .countdown {
            font-size: 20px;
            color: #d9534f;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Đổi Mật Khẩu Thành Công!</h1>
        <p class="redirect-message">Bạn sẽ được chuyển hướng đến trang đăng nhập trong <span class="countdown" id="countdown">3</span> giây.</p>
    </div>
</body>
</html>
