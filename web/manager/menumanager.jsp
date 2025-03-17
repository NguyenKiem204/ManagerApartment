<%-- Document : home1 Created on : Feb 11, 2025, 2:12:16 AM Author : nkiem --%>
<%-- Document : menu.jsp Created on : Feb 8, 2025, 2:54:18 PM Author : nkiem
--%> <%@page contentType="text/html" pageEncoding="UTF-8" %> <%@taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Menu</title>

    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/bootstrap.css"
    />

    <link rel="stylesheet" href="assets/vendors/iconly/bold.css" />

    <!-- <link rel="stylesheet" href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css" /> -->
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/pages/index.css"
    />
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/vendors/bootstrap-icons/bootstrap-icons.css"
    />
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/app.css"
    />
    <link
      rel="shortcut icon"
      href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png"
      type="image/x-icon"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.13/flatpickr.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
      integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/menu.css"
    />
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css"
      rel="stylesheet"
    />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <style>
      #notificationContainer {
        position: relative;
        display: inline-block;
      }

      #notificationBell {
        cursor: pointer;
        font-size: 24px;
        position: relative;
      }

      #notificationCount {
        display: none;
        position: absolute;
        top: -5px;
        right: -5px;
        background: red;
        color: white;
        font-size: 12px;
        font-weight: bold;
        width: 18px;
        height: 18px;
        text-align: center;
        line-height: 18px;
        border-radius: 50%;
      }

      #notificationList {
        display: none;
        position: absolute;
        right: 10px;
        top: 35px;
        background: white;
        border: 1px solid #ddd;
        width: 300px;
        max-height: 400px;
        overflow-y: auto;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
        list-style: none;
        padding: 10px;
        z-index: 1000;
        border-radius: 5px;
      }

      #notificationList li {
        padding: 10px;
        border-bottom: 1px solid #eee;
        cursor: pointer;
        transition: background 0.2s, font-weight 0.2s;
        position: relative;
      }

      #notificationList li.unread {
        font-weight: bold;
        background-color: #f9f9f9;
      }

      #notificationList li.read {
        font-weight: normal;
        background-color: white;
      }

      #notificationList li:hover {
        background: #f5f5f5;
      }

      #notificationList li:last-child {
        border-bottom: none;
      }

      .notif-header {
        font-weight: bold;
        color: #333;
      }

      .notif-time {
        display: block;
        font-size: 12px;
        color: gray;
        margin-top: 5px;
        text-align: right;
      }

      .notification-item {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 10px;
        border-bottom: 1px solid #ddd;
      }

      .notification-message {
        font-size: 14px;
        color: #333;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        /* Hiển thị tối đa 2 dòng */
        line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
        max-height: 3em;
        /* Đảm bảo không vượt quá 2 dòng */
        word-wrap: break-word;
      }

      .notification-date {
        font-size: 12px;
        color: gray;
        text-align: right;
        /*margin-top: 5px;*/
      }
    </style>
  </head>

  <body>
    <div id="app">
      <nav class="app-header navbar navbar-expand bg-body">
        <div class="container-fluid">
          <!-- Navbar Links -->
          <ul class="navbar-nav kiem_can_trai">
            <li class="nav-item d-none d-md-block">
              <a
                href="<%= request.getContextPath() %>/redirect/home"
                class="nav-link"
                >Home</a
              >
            </li>
            <li class="nav-item d-none d-md-block">
              <a href="#" class="nav-link">Contact</a>
            </li>
          </ul>

          <!-- User and Notification Dropdowns -->
          <ul class="navbar-nav ms-auto">
            <!-- User Menu -->
            <c:choose>
              <c:when test="${not empty sessionScope.staff}">
                <c:set var="user" value="${sessionScope.staff}" />
                <c:set var="role" value="staff" />
              </c:when>
              <c:when test="${not empty sessionScope.resident}">
                <c:set var="user" value="${sessionScope.resident}" />
                <c:set var="role" value="resident" />
              </c:when>
            </c:choose>

            <li class="nav-item dropdown user-menu">
              <a
                href="#"
                class="nav-link dropdown-toggle"
                data-bs-toggle="dropdown"
              >
                <img
                  src="<%= request.getContextPath() %>/${user.image.imageURL}"
                  class="user-image rounded-circle shadow"
                  alt="User Image"
                />
                <span class="d-none d-md-inline"
                  ><c:out value="${user.fullName}"></c:out
                ></span>
              </a>
              <ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
                <li class="user-header text-bg-primary img-drop">
                  <img
                    src="<%= request.getContextPath() %>/${user.image.imageURL}"
                    class="rounded-circle shadow"
                    alt="User Image"
                  />
                  <p>
                    <c:out value="${user.fullName}"></c:out> - ${role}
                    <small>Member since Nov. 2024</small>
                  </p>
                </li>
                <li class="user-footer d-flex justify-content-between">
                  <a
                    href="<%= request.getContextPath() %>/profile-${role}"
                    class="btn btn-default btn-flat"
                    >Profile</a
                  >
                  <a href="#" class="btn btn-default btn-flat">Setting</a>
                  <a
                    href="<%= request.getContextPath() %>/logout"
                    class="btn btn-default btn-flat"
                    >Logout</a
                  >
                </li>
              </ul>
            </li>
          </ul>
          <div id="notificationContainer">
            <div id="notificationBell" class="bell">
              🔔
              <span id="notificationCount" class="count-badge">0</span>
            </div>
            <ul id="notificationList"></ul>
          </div>
        </div>
      </nav>
      <div id="sidebar" class="active">
        <div class="sidebar-wrapper active" style="border: 2px solid orangered">
          <div class="sidebar-header">
            <div class="d-flex justify-content-between">
              <div class="logo">
                <a href="menumanager.jsp"
                  ><img
                    src="<%= request.getContextPath() %>/assets/images/logo/logo1.png"
                    alt="Logo"
                /></a>
              </div>
              <div class="toggler">
                <a href="#" class="sidebar-hide d-xl-none d-block"
                  ><i class="bi bi-x bi-middle"></i
                ></a>
              </div>
            </div>
          </div>
          <div class="sidebar-menu">
            <ul class="menu">
              <li class="sidebar-title">Menu</li>

              <li class="sidebar-item">
                <a
                  href="<%= request.getContextPath() %>/redirect/home"
                  class="sidebar-link"
                >
                  <i class="bi bi-grid-fill"></i>
                  <span>Home</span>
                </a>
              </li>
              <c:if test="${sessionScope.staff.role.roleID == 1}">
                <li class="sidebar-item has-sub">
                  <a href="#" class="sidebar-link">
                    <i class="bi bi-stack"></i>
                    <span>Apartment</span>
                  </a>
                  <ul class="submenu">
                    <li class="submenu-item">
                      <a href="manageApartment">Apartment</a>
                    </li>
                    <li class="submenu-item">
                      <a href="component-button.html">Utilities</a>
                    </li>
                  </ul>
                </li>

                <li class="sidebar-item has-sub">
                  <a href="#" class="sidebar-link">
                    <i class="fa-solid fa-users-gear"></i>
                    <span>Account</span>
                  </a>
                  <ul class="submenu resident-active">
                    <li class="submenu-item">
                      <a href="manageResident">Resident</a>
                    </li>
                    <li class="submenu-item">
                      <a href="manageStaff">Staff</a>
                    </li>
                  </ul>
                </li>
              </c:if>
              <c:if test="${sessionScope.staff.role.roleID == 3}">
                <li class="sidebar-item">
                  <a
                    href="<%= request.getContextPath() %>/accountant/InvoicesManager"
                    class="sidebar-link"
                  >
                    <i class="bi bi-receipt-cutoff"></i>
                    <span>Invoice Management</span>
                  </a>
                </li>
              </c:if>
              <c:if test="${sessionScope.resident.role.roleID == 7}">
                <li class="sidebar-item">
                  <a
                    href="<%= request.getContextPath() %>/owner/ViewInvoice"
                    class="sidebar-link"
                  >
                    <i class="bi bi-receipt-cutoff"></i>
                    <span>Invoice Management</span>
                  </a>
                </li>
                <li class="sidebar-item">
                  <a
                    href="<%= request.getContextPath() %>/owner/manageContract"
                    class="sidebar-link"
                  >
                    <i class="fa-solid fa-user"></i>
                    <span>Tenant and Contract</span>
                  </a>
                </li>
                <li class="sidebar-item">
                  <a
                    href="<%= request.getContextPath() %>/owner/manageOwnerApartment"
                    class="sidebar-link"
                  >
                    <i class="fa-solid fa-house"></i>
                    <span>Apartment</span>
                  </a>
                </li>
              </c:if>
              <li class="sidebar-item has-sub">
                <a href="#" class="sidebar-link">
                  <i class="fa-solid fa-envelope"></i>
                  <span>Utility Management</span>
                </a>
                <ul class="submenu">
                  <c:if test="${sessionScope.staff.role.roleID == 3}">
                    <li class="submenu-item managernews">
                      <a
                        href="<%= request.getContextPath() %>/accountant/manager-meter-reading"
                        >Manager EW</a
                      >
                    </li>
                  </c:if>
                </ul>
              </li>
              <li class="sidebar-item has-sub">
                <a href="#" class="sidebar-link">
                  <i class="bi bi-grid-1x2-fill"></i>
                  <span>Feedback</span>
                </a>
                <ul class="submenu">
                  <c:if test="${sessionScope.staff.role.roleID == 1}">
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/manager/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/manager/request"
                        >Request</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/manager/formfeedbackmanager"
                        >Feedback Statistics</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.staff.role.roleID == 2}">
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/administative/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/administative/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.staff.role.roleID == 3}">
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/technical/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/technical/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.staff.role.roleID == 4}">
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/technical/feedbackreview"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/technical/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.staff.role.roleID == 5}">
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/service/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/service/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.resident.role.roleID == 7}">
                    <li class="submenu-item">
                      <a
                        href="<%= request.getContextPath() %>/owner/ViewInvoice"
                        >Invoices</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/owner/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/owner/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                  <c:if test="${sessionScope.resident.role.roleID == 6}">
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/tenant/feedback"
                        >Feedback</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="<%= request.getContextPath() %>/tenant/request"
                        >Request</a
                      >
                    </li>
                  </c:if>
                </ul>
              </li>
              <c:if test="${sessionScope.staff.role.roleID == 1}">
                <li class="sidebar-item has-sub">
                  <a href="#" class="sidebar-link">
                    <i class="bi bi-collection-fill"></i>
                    <span>Statistics</span>
                  </a>
                  <ul class="submenu">
                    <li class="submenu-item">
                      <a href="extra-component-avatar.html">Parking Service</a>
                    </li>
                    <li class="submenu-item">
                      <a href="extra-component-sweetalert.html"
                        >Public Electricity Service</a
                      >
                    </li>
                    <li class="submenu-item">
                      <a href="extra-component-toastify.html"
                        >Cleaning Service</a
                      >
                    </li>
                  </ul>
                </li>
              </c:if>

              <li class="sidebar-title">Others</li>

              <li class="sidebar-item has-sub news-active">
                <a href="#" class="sidebar-link">
                  <i class="fa-solid fa-envelope"></i>
                  <span>News</span>
                </a>
                <ul id="news" class="submenu">
                  <c:if test="${sessionScope.staff.role.roleID == 1}">
                    <li class="submenu-item managernews">
                      <a
                        href="<%= request.getContextPath() %>/manager/managernews"
                        >Manager News</a
                      >
                    </li>
                    <li class="submenu-item addnews">
                      <a href="<%= request.getContextPath() %>/manager/addnews"
                        >Add News</a
                      >
                    </li>
                  </c:if>
                  <li class="submenu-item news-itemm">
                    <a href="<%= request.getContextPath() %>/news">News</a>
                  </li>
                </ul>
              </li>

              <li class="sidebar-item">
                <a
                  href="<%= request.getContextPath() %>/chat"
                  class="sidebar-link"
                >
                  <i class="bi bi-chat-dots-fill"></i>
                  <span>Messages</span>
                </a>
              </li>

              <li class="sidebar-title">Policies</li>

              <li class="sidebar-item">
                <a
                  href="https://zuramai.github.io/mazer/docs"
                  class="sidebar-link"
                >
                  <i class="bi bi-life-preserver"></i>
                  <span>Building Information</span>
                </a>
              </li>

              <li class="sidebar-item">
                <a
                  href="https://github.com/zuramai/mazer/blob/main/CONTRIBUTING.md"
                  class="sidebar-link"
                >
                  <i class="bi bi-puzzle"></i>
                  <span>Service Fee Information</span>
                </a>
              </li>
              <li class="sidebar-item">
                <a href="!#" class="sidebar-link">
                  <i class="bi bi-puzzle"></i>
                  <span>Regulations</span>
                </a>
              </li>
              <!-- =================================Login, Logout..==================== -->
              <li class="sidebar-item has-sub">
                <a href="#" class="sidebar-link">
                  <i class="bi bi-person-badge-fill"></i>
                  <span>Settings</span>
                </a>
                <ul class="submenu">
                  <li class="submenu-item">
                    <a href="profile">Information</a>
                  </li>
                  <li class="submenu-item">
                    <a href="login">Login</a>
                  </li>
                  <li class="submenu-item">
                    <a href="<%= request.getContextPath() %>/logout">Logout</a>
                  </li>
                  <li class="submenu-item">
                    <a href="forgot-password">Forgot Password</a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
          <button class="sidebar-toggler btn x">
            <i data-feather="x"></i>
          </button>
        </div>
      </div>
      <div id="main">
        <a href="#" class="burger-btn d-inline-block d-xl-none kiem_button">
          <i class="bi bi-justify fs-3"></i>
        </a>
      </div>
    </div>

    <script>
      $(document).ready(function () {
        $("#notificationBell").click(function (event) {
          event.stopPropagation();
          $("#notificationList").toggle();
          $("#notificationCount").hide();
        });

        $(document).click(function (event) {
          if (!$(event.target).closest("#notificationContainer").length) {
            $("#notificationList").hide();
          }
        });

        function checkNotifications() {
          let roleId = "<c:out value='${sessionScope.roleId}' />";
          let residentId =
            "<c:out value='${sessionScope.resident.residentId}' />";
          let staffId = "<c:out value='${sessionScope.staff.staffId}' />";
          $.ajax({
            url: "<%= request.getContextPath() %>/GetNotifications",
            type: "GET",
            data: { roleId: roleId, residentId: residentId, staffId: staffId },
            success: function (response) {
              console.log("Dữ liệu từ server:", response);

              if (response.length > 0) {
                $("#notificationBell").addClass("active");
                $("#notificationList").html("");

                response.forEach(function (notif) {
                  let liClass = notif.isRead ? "read" : "unread";
                  let dateObj = new Date(notif.createdAt);
                  let formattedDate =
                    dateObj.getDate().toString().padStart(2, "0") +
                    "/" +
                    (dateObj.getMonth() + 1).toString().padStart(2, "0");
                  // Xác định URL dựa trên referenceTable
                  let notificationUrl = getNotificationUrl(notif);

                  $("#notificationList").append(
                    `<li class="notification-item ` +
                      liClass +
                      `" data-id="` +
                      notif.notificationId +
                      `"><a href="` +
                      notificationUrl +
                      `">
            <div class="notification-message">` +
                      notif.message +
                      `</div>
            <div class="notification-date">` +
                      formattedDate +
                      `</div>
                </a></li>`
                  );
                });

                // Hiển thị số lượng thông báo chưa đọc
                let unreadCount = response.filter((n) => !n.isRead).length;
                if (unreadCount > 0) {
                  $("#notificationCount").text(unreadCount).show();
                } else {
                  $("#notificationCount").hide();
                }
              } else {
                $("#notificationList").html(
                  "<li class='no-notifications'>There are no notifications yet.</li>"
                );
                $("#notificationCount").hide();
              }
            },
          });
        }

        // Hàm lấy URL tùy theo referenceTable
        function getNotificationUrl(notif) {
          let baseUrl = "<%= request.getContextPath() %>";
          switch (notif.referenceTable) {
            case "ManagerFeedback":
              return (
                baseUrl +
                `/feedbackreviewdetail?managerFeedbackId=` +
                notif.referenceId
              );
            case "Request":
              return baseUrl + `/requestdetail?requestId=` + notif.referenceId;
            case "UtilityBill":
              return baseUrl + `/utility-detail?utilityId=` + notif.referenceId;;
            default:
              return "#"; // Nếu không xác định được loại, đặt về #
          }
        }

        // Khi click vào thông báo, đổi màu và update trạng thái đọc
        $("#notificationList").on("click", "li", function () {
          let notificationId = $(this).data("id");
          $(this).removeClass("unread").addClass("read");

          $.ajax({
            url: "<%= request.getContextPath() %>/MarkAsRead",
            type: "POST",
            data: { notificationId: notificationId },
            success: function () {
              console.log("Thông báo đã được đánh dấu là đã đọc!");
            },
          });
        });

        // Kiểm tra thông báo mới mỗi 0.3 giây
        setInterval(checkNotifications, 500);
      });
      $(function () {
        // Cấu hình cơ bản cho date picker với định dạng dd/MM/yyyy
        const datePickerConfig = {
          dateFormat: "d/m/Y",
          locale: "en",
          allowInput: true,
          maxDate: null,
          disableMobile: "true",
        };
        // Khởi tạo Date Picker đơn giản
        flatpickr("#datePicker", {
          ...datePickerConfig,
          maxDate: null,
        });
        flatpickr("#dateRangePicker", {
          ...datePickerConfig,
          mode: "range",
          maxDate: null,
        });
        flatpickr("#deadline", {
          ...datePickerConfig,
          maxDate: null,
        });

        flatpickr("#boughtOn", {
          ...datePickerConfig,
          maxDate: "today",
        });
        const birthdayPicker = flatpickr("#birthdayPicker", {
          ...datePickerConfig,
          onChange: function (selectedDates, dateStr) {
            if (selectedDates.length > 0) {
              const birthDate = selectedDates[0];
              const today = new Date();
              let age = today.getFullYear() - birthDate.getFullYear();
              const monthDiff = today.getMonth() - birthDate.getMonth();
              if (
                monthDiff < 0 ||
                (monthDiff === 0 && today.getDate() < birthDate.getDate())
              ) {
                age--;
              }

              document.getElementById(
                "ageInfo"
              ).textContent = `Tuổi của bạn: ${age} tuổi`;
            }
          },
        });
      });
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.13/flatpickr.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.13/l10n/vn.min.js"></script>
    <!-- <script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script> -->
    <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>

    <script src="<%= request.getContextPath() %>/assets/vendors/apexcharts/apexcharts.js"></script>
    <script src="<%= request.getContextPath() %>/assets/js/pages/dashboard.js"></script>

    <script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
  </body>
</html>
