<%-- Document : login Created on : Jan 16, 2025, 1:36:33 AM Author : nkiem --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <title>Login Apartment Building</title>

            <link rel="preconnect" href="https://fonts.gstatic.com" />
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
            <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap"
                rel="stylesheet" />
            <link rel="shortcut icon" href="assets/images/favicon/favicon.png" type="image/x-icon" />
            <link rel="stylesheet" href="./assets/css/login.css" />
            
        </head>

        <body>
            <div class="video-background">
                <!-- <video autoplay loop id="background-video">
              <source src="./assets/background/background.mp4" type="video/mp4">
            Your browser does not support the video tag.
          </video> -->
            </div>
            <form action="login" method="POST">
                <h3>Login Here</h3>
<c:set var="cookie" value="${pageContext.request.cookies}"></c:set>
                <label for="email">Email</label>
                <input class="email" onfocus="hideError()" value="${cookie.email.value != null ? cookie.email.value : requestScope.email}"   type="text" placeholder="Email or Phone" id="email"
                    name="email" />

                <label for="password">Password</label>
                <input class="password" onfocus="hideError()" value="${cookie.password.value != null ? cookie.password.value : requestScope.password}"  type="password" placeholder="Password" id="password"
                    name="password" />
                <p style="font-size: 15px; margin-top: 15px; margin-bottom: -20px; font-weight: 500; color: red;" class="error" id="error-message">${requestScope.error}</p>
                <div class="remember-forget">
                    <label class="remember">
                        <input  ${(cookie.remember.value!=null?'checked':'')} type="checkbox" id="remember_me" name="remember_me" /> Remember
                        Me
                    </label>
                    <a href="#" class="forget-password">Forget Password?</a>
                </div>

                <button type="submit">Log In</button>
                <div class="social">
                    <div class="go"><i class="fab fa-google"></i> Google</div>
                    <div class="fb"><i class="fab fa-facebook"></i> Facebook</div>
                </div>
            </form>

                <script type="text/javascript">
                    function hideError(){
                        document.getElementById('error-message').style.visibility = 'hidden'; // Ẩn thẻ p
                         }
            </script>
        </body>
        </body>

        </html>