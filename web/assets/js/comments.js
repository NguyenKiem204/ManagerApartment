$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const highlightCommentId = urlParams.get('commentId');
    loadComments(0, highlightCommentId);
    $("#comment-form").off("submit").on("submit", function (e) {
        e.preventDefault();
        const newsId = $("#newsId").val();
        const userId = $("#userId").val();
        const userType = $("#userType").val();
        const content = $("#comment-content").val();
        const userName = $("#userName").val();
        const userAvatar = $("#userAvatar").val();

        if (!content.trim()) {
            showAlert("Vui lòng nhập nội dung bình luận", "danger");
            return;
        }

        $.ajax({
            url: "comment",
            type: "POST",
            data: {
                newsId: newsId,
                userId: userId,
                userType: userType,
                content: content,
                userName: userName,
                userAvatar: userAvatar
            },
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    addCommentToDOM(response, true);
                    $("#comment-content").val("");
                    showAlert("Bình luận đã được đăng thành công!", "success");
                } else {
                    showAlert("Lỗi khi đăng bình luận: " + response.message, "danger");
                }
            },
            error: function (xhr, status, error) {
                showAlert("Lỗi khi đăng bình luận: " + error, "danger");
            }
        });
    });

    $("#load-more-comments").on("click", function () {
        const offset = $(".comment").length;
        loadComments(offset);
    });
});

function loadComments(offset = 0, highlightCommentId = null) {
    const newsId = $("#newsId").val();
    const limit = 4;
    const commentIdParam = (offset === 0 && highlightCommentId) ? highlightCommentId : null;

    $.ajax({
        url: "comment",
        type: "GET",
        data: {
            newsId: newsId,
            offset: offset,
            limit: limit,
            commentId: commentIdParam
        },
        dataType: "json",
        success: function (response) {
            if (offset === 0) {
                $("#comments-list").empty();
            }

            if (response.length > 0) {
                response.forEach(function (comment) {
                    const isHighlighted = (highlightCommentId && comment.commentId == highlightCommentId);
                    addCommentToDOM(comment, false, isHighlighted);
                });

                if (response.length < limit) {
                    $("#load-more-comments").hide();
                } else {
                    $("#load-more-comments").show();
                }
            } else if (offset === 0) {
                $("#comments-list").html("<p class='text-muted'>Chưa có bình luận nào. Hãy là người đầu tiên bình luận!</p>");
                $("#load-more-comments").hide();
            } else {
                $("#load-more-comments").hide();
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi tải bình luận:", error);
            if (offset === 0) {
                $("#comments-list").html("<p class='text-danger'>Lỗi khi tải bình luận. Vui lòng thử lại sau.</p>");
            }
            showAlert("Lỗi khi tải bình luận: " + error, "danger");
        }
    });
}

function escapeHtml(unsafe) {
    return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
}

function addCommentToDOM(comment, isNew, isHighlighted = false) {
    const contextPath = window.location.pathname.split('/')[1];
    let fixedAvatarPath = comment.userAvatar;
    if (fixedAvatarPath && !fixedAvatarPath.startsWith(`/${contextPath}`)) {
        if (fixedAvatarPath.startsWith('/')) {
            fixedAvatarPath = `/${contextPath}${fixedAvatarPath}`;
        } else {
            fixedAvatarPath = `/${contextPath}/${fixedAvatarPath}`;
        }
    }
    const highlightClass = isHighlighted ? 'highlighted-comment' : '';

    const commentHtml = `
    <div id="comment-${comment.commentId}" class="comment mb-3 ${highlightClass}">
        <div class="comment-avatar">
            <img src="${fixedAvatarPath}" alt="Avatar">
        </div>
        <div class="comment-content">
            <strong>${escapeHtml(comment.userName)}:</strong>
            <p>${escapeHtml(comment.content)}</p>
            <small class="text-muted" title="${escapeHtml(comment.formattedDate)}">
                ${escapeHtml(comment.date) || 'Just now'}
            </small>
        </div>
    </div>
`;


    if (isNew) {
        $("#comments-list").prepend(commentHtml);
    } else {
        $("#comments-list").append(commentHtml);
    }
    if (isHighlighted) {
        setTimeout(() => {
            const element = document.getElementById(`comment-${comment.commentId}`);
            if (element) {
                element.scrollIntoView({behavior: 'smooth', block: 'center'});
            }
        }, 500);
}
}
function showAlert(message, type) {
    const alertHtml = `
        <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;

    $("#comment-alerts").html(alertHtml);
    setTimeout(function () {
        $(".alert").alert('close');
    }, 3000);
}