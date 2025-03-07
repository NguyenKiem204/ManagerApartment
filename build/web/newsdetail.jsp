<%-- 
    Document   : newsdetail
    Created on : Feb 10, 2025, 11:41:12 PM
    Author     : nkiem
--%>

<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>${news.title}</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
    </head>

    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath() %>assets/css/newsdetail.css"/>
        <div id="main">
            <div class="container mt-4 mb-4">
                <!-- Bài viết chi tiết -->
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title fs-2 text-center fw-bold">${news.title}</h1>
                        <!-- Thông tin tác giả -->
                        <div class="author-info">
                            <div class="author-avatar">
                                <img src="<%= request.getContextPath() %>/${news.staff.image.imageURL}"
                                     alt="Avatar ${news.staff.fullName}">
                            </div>
                            <div class="author-details">
                                <span class="author-name">${news.staff.fullName}</span>
                                <span class="post-date">Create date: ${news.formattedDate}</span>
                            </div>
                        </div>
                        <div class="card-text">
                            <div id="description-content">
                                ${news.description}
                            </div>
                        </div>


                    </div>
                </div>

                <!-- Các bài báo liên quan -->
                <div class="related-news">
                    <h3>Bài viết liên quan</h3>
                    <div class="row" id="related-news-grid">
                        <!-- Bài viết liên quan 1 -->
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738936339590-ea1fc8bd9732?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 1">
                                <div class="card-body">
                                    <h5 class="card-title">Hướng dẫn sử dụng Bootstrap 5</h5>
                                    <p class="card-text">Tìm hiểu cách sử dụng Bootstrap 5 để thiết kế giao diện web hiện đại.
                                    </p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>
                        <!-- Bài viết liên quan 2 -->
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1739057736231-3577bfc1a1b9?q=80&w=3125&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 2">
                                <div class="card-body">
                                    <h5 class="card-title">Tối ưu hóa SEO cho blog</h5>
                                    <p class="card-text">Các bí quyết giúp blog của bạn đạt thứ hạng cao trên công cụ tìm kiếm.
                                    </p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>
                        <!-- Bài viết liên quan 3 -->
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738975927070-d5af82de67c1?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 3">
                                <div class="card-body">
                                    <h5 class="card-title">Cách viết content thu hút</h5>
                                    <p class="card-text">Kỹ năng viết content giúp thu hút độc giả và tăng tương tác.</p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>

                        <!-- Các bài viết liên quan khác -->
                        <div class="col-md-4 mb-3 hidden">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738975927070-d5af82de67c1?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 4">
                                <div class="card-body">
                                    <h5 class="card-title">Cách viết content thu hút</h5>
                                    <p class="card-text">Kỹ năng viết content giúp thu hút độc giả và tăng tương tác.</p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 mb-3 hidden">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738975927070-d5af82de67c1?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 5">
                                <div class="card-body">
                                    <h5 class="card-title">Cách viết content thu hút</h5>
                                    <p class="card-text">Kỹ năng viết content giúp thu hút độc giả và tăng tương tác.</p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 mb-3 hidden">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738975927070-d5af82de67c1?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 6">
                                <div class="card-body">
                                    <h5 class="card-title">Cách viết content thu hút</h5>
                                    <p class="card-text">Kỹ năng viết content giúp thu hút độc giả và tăng tương tác.</p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 mb-3 hidden">
                            <div class="card">
                                <img src="https://images.unsplash.com/photo-1738975927070-d5af82de67c1?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     class="card-img-top" alt="Ảnh bài viết liên quan 6">
                                <div class="card-body">
                                    <h5 class="card-title">Cách viết content thu hút</h5>
                                    <p class="card-text">Kỹ năng viết content giúp thu hút độc giả và tăng tương tác.</p>
                                    <a href="#" class="btn btn-primary">Xem thêm</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button id="load-more" class="btn btn-secondary mt-3">Xem thêm</button>
                </div>

                <!-- Phần bình luận -->
                <div style="border-radius: .7rem" class="comments-section mt-5 bg-light p-3">
                    <h3>Bình luận</h3>
                    <!-- Form bình luận -->
                    <form>
                        <div class="mb-3">
                            <textarea class="form-control" rows="3" placeholder="Viết bình luận của bạn..."></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Gửi bình luận</button>
                    </form>
                    <!-- Danh sách bình luận -->
                    <div class="mt-4">
                        <div class="comment">
                            <div class="comment-avatar">
                                <img src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=2960&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     alt="Avatar người dùng 1">
                            </div>
                            <div class="comment-content">
                                <strong>Người dùng 1:</strong>
                                <p>Bài viết rất hay và hữu ích. Cảm ơn tác giả!</p>
                                <small class="text-muted">10 phút trước</small>
                            </div>
                        </div>
                        <div class="comment">
                            <div class="comment-avatar">
                                <img src="https://images.unsplash.com/photo-1738956952892-7553e0327906?q=80&w=2938&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                     alt="Avatar người dùng 2">
                            </div>
                            <div class="comment-content">
                                <strong>Người dùng 2:</strong>
                                <p>Mình đã áp dụng và thấy hiệu quả rõ rệt. Cảm ơn!</p>
                                <small class="text-muted">30 phút trước</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer>
                <div class="footer clearfix mb-0 text-muted">
                    <div class="float-start">
                        <p>2025 &copy; Kiemm</p>
                    </div>
                    <div class="float-end">
                        <p>
                            Crafted with
                            <span class="text-danger"><i class="bi bi-heart"></i></span> by
                            <a href="http://ahmadsaugi.com">NguyenKiem</a>
                        </p>
                    </div>
                </div>
            </footer>
        </div>
        <script>
            $(document).ready(function () {
                $('.related-news .hidden').hide();
                $('#load-more').click(function () {
                    $('.related-news .hidden').slice(0, 3).removeClass('hidden').show();

                    if ($('.related-news .hidden').length === 0) {
                        $('#load-more').hide();
                    }
                });
            });
        </script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/vendors/apexcharts/apexcharts.js"></script>
        <script src="assets/js/pages/dashboard.js"></script>

        <script src="assets/js/main.js"></script>
    </body>

</html>