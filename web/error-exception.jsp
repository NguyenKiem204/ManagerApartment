<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/error-exception.css" />
    <title>Error ${errorCode}</title>
</head>

<body>
    <section class="page_404">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-10 col-sm-offset-1 text-center">
                        <div class="four_zero_four_bg">
                            <h1 class="text-center">${errorCode}</h1>
                        </div>
                        <div class="content_box_404">
                            <h3 class="h2">Oops! Something went wrong.</h3>
                            <p><strong>Error Code:</strong> ${errorCode}</p>
                            <p><strong>Message:</strong> ${errorMessage}</p>
                            <a href="<%= request.getContextPath() %>/redirect/home" class="btn btn-primary">Go to Home</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>
