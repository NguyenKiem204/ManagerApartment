<%-- 
    Document   : feedbackadministrative
    Created on : Feb 9, 2025, 2:24:20 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Assets</title>
        <link href="css/responsive.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/manageassets.css" />
    </head>
    <body>
        <%@include file="menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <div class="header-container">
                    <h1 class="header-title">Manager Asset</h1>
                    <button id="addAssetBtn" class="add-btn" onclick="window.location.href = '<%= request.getContextPath() %>/manager/addasset'">+ Add</button>
                </div>

                <div class="search-sort-container">
                    <input type="text" id="searchBox" placeholder="Search by name of asset..." value="${param.keySearch}" style="width: 40%">

                    <div class="filter-container" style="padding-left: 10px">
                        <div class="filter-row">
                            <select id="categoryFilter">
                                <option value="0">Filter by Category</option>
                                <c:forEach var="cat" items="${listcategory}">
                                    <option value="${cat.categoryId}" ${param.catId == cat.categoryId ? 'selected' : ''}>${cat.categoryName}</option>
                                </c:forEach>
                            </select>

                        </div>

                        <!--<input type="date" id="dateFilter" class="date-filter" name="date" value="${param.date}">-->
                    </div>
                    <div style="padding-left: 10px">
                        <select id="sortBox" style="width: 100%">
                            <option value="0" ${param.sort == '0' ? 'selected' : ''}>Sort by</option>
                            <option value="1" ${param.sort == '1' ? 'selected' : ''}>Sort by Name of Asset tang </option>
                            <option value="2" ${param.sort == '2' ? 'selected' : ''}>Sort by Name of Asset giam</option>
                        </select>
                    </div>
                    <label for="itemsPerPage">Show:</label>
                    <select id="itemsPerPage" style="width: 80px">
                        <option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
                        <option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
                    </select> entries
                </div>
                <div style="width: 100%; overflow-x: auto;">
                    <table style="width: 100%; border-collapse: collapse;">
                        <thead>
                            <tr>
                                <th>Asset Name</th>
                                <th>Category</th>
                                <th>Bought on</th>
                                <!--<th>Quantity</th>-->
                                <!--<th>Update At</th>-->
                                <th>Location</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody id="tableBody">
                            <c:forEach var="as" items="${listas}">
                                <tr data-asset-id="${as.assetId}">
                                    <td class="truncate">${as.assetName}</td>
                                    <td class="truncate_min">${as.category.categoryName}</td>
                                    <td style="min-width: 120px"> <fmt:formatDate pattern="dd-MM-yyyy" value="${as.boughtOn}"></fmt:formatDate> </td>
                                    <!--<td style="width: 70px">${as.quantity}</td>-->
                                    <!--<td>${as.formattedUpdatedAt}</td>-->
                                    <td class="truncate_min">${as.location}</td>
                                    <td style="min-width: 100px">${as.status.statusName}</td>
                                    <td>
                                        <div style="display: flex; gap: 10px; justify-content: center; align-items: center;">
                                            <button style="background: green; color: white; padding: 8px 16px; border: none;
                                                    cursor: pointer; border-radius: 5px; font-weight: bold;"
                                                    onclick="redirectToUpdate(${as.assetId})">
                                                Update
                                            </button>

                                            <button style="background: red; color: white; padding: 8px 16px; border: none;
                                                    cursor: pointer; border-radius: 5px; font-weight: bold;"
                                                    onclick="confirmDelete('${as.assetId}')">
                                                Delete
                                            </button>
                                        </div>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="pagination" style="justify-content: end">
                    <ul style="list-style: none; display: flex; justify-content: center; padding: 0;">
                        <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
                        <c:if test="${num > 1}">
                            <%-- Nút First --%>
                            <c:if test="${currentPage > 1}">
                                <li style="margin: 0 5px;">
                                    <a class="page-link" href="?page=1"
                                       style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                        First
                                    </a>
                                </li>
                            </c:if>

                            <%-- Nếu tổng số trang nhỏ hơn hoặc bằng 3, hiển thị tất cả --%>
                            <c:choose>
                                <c:when test="${num <= 3}">
                                    <c:forEach begin="1" end="${num}" var="i">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link ${i == currentPage ? 'active' : ''}" href="?page=${i}" 
                                               style="padding: 8px 12px; background: ${i == currentPage ? '#d35400' : '#ff9800'}; color: white; text-decoration: none; border-radius: 4px;">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <%-- Hiển thị trang trước, trang hiện tại và trang sau --%>
                                    <c:if test="${currentPage > 1}">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link" href="?page=${currentPage - 1}"
                                               style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                                ${currentPage - 1}
                                            </a>
                                        </li>
                                    </c:if>

                                    <li style="margin: 0 5px;">
                                        <a class="page-link active" href="?page=${currentPage}"
                                           style="padding: 8px 12px; background: #d35400; color: white; text-decoration: none; border-radius: 4px;">
                                            ${currentPage}
                                        </a>
                                    </li>

                                    <c:if test="${currentPage < num}">
                                        <li style="margin: 0 5px;">
                                            <a class="page-link" href="?page=${currentPage + 1}"
                                               style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                                ${currentPage + 1}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>

                            <%-- Nút Last --%>
                            <c:if test="${currentPage < num}">
                                <li style="margin: 0 5px;">
                                    <a class="page-link" href="?news=${num}"
                                       style="padding: 8px 12px; background: #ff9800; color: white; text-decoration: none; border-radius: 4px;">
                                        Last
                                    </a>
                                </li>
                            </c:if>
                        </c:if>
                    </ul>
                </div>
                <div id="updateModal" class="modal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5);">
                    <div class="modal-content" style="background: white; padding: 20px; width: 50%;margin: 1% auto auto auto; border-radius: 10px;">
                        <h2>Update Asset</h2>
                        <form id="updateForm">
                            <input type="hidden" id="assetId" name="assetId">
                            <label>Asset Name:</label>
                            <input type="text" id="assetName" name="assetName" required><br><br>

                            <label>Category:</label>
                            <select id="assetCategory" name="categoryId">
                                <c:forEach var="cat" items="${listcategory}">
                                    <option value="${cat.categoryId}">${cat.categoryName}</option>
                                </c:forEach>
                            </select><br><br>

                            <div class="form-group">
                                <div class="flex-container">
                                    <div class="flex-item">
                                        <label style="font-weight: bold; color: #333;">Quantity:</label>
                                        <input type="number" id="assetQuantity" name="quantity" required>
                                    </div>

                                    <div class="flex-item">
                                        <label style="font-weight: bold; color: #333;">Location:</label>
                                        <input type="text" id="assetLocation" name="location" required>
                                    </div>
                                </div>
                            </div>

                            <label>Status:</label>
                            <select id="assetStatus" name="status">
                                <c:forEach var="st" items="${liststatus}">
                                    <option value="${st.statusId}">${st.statusName}</option>
                                </c:forEach>
                            </select><br><br>

                            <button type="button" onclick="submitUpdate()">Save Changes</button>
                            <button type="button" onclick="closeModal()">Cancel</button>
                        </form>
                    </div>
                </div>




            </div>
        </div>

        <script>
            function redirectToUpdate(assetId) {
                window.location.href = `updateasset?assetId=` + assetId;
            }

            function confirmDelete(assetId, assetName) {
                const confirmation = confirm(`Do you want to delete the asset: ` + assetName + `?`);
                if (confirmation) {
                    // Thực hiện hành động xóa ở đây
                    window.location = 'deleteasset?assetId=' + assetId;

                    // Bạn có thể gọi một hàm để xóa sản phẩm từ danh sách hoặc từ server
                    // Sau khi xóa, chuyển hướng về trang home
                    setTimeout(() => {
                        window.location.href = 'manageassets'; // Đổi thành URL trang home của bạn
                    }, 1000); // Chờ 1 giây để đảm bảo xóa xong
                } else {
                    console.log('Hành động xóa đã bị hủy.');
                }
            }
            document.querySelectorAll("#tableBody tr").forEach(row => {
                row.addEventListener("click", function () {
                    // Kiểm tra nếu click vào button thì không mở trang chi tiết
                    if (event.target.tagName.toLowerCase() === "button") {
                        event.stopPropagation();
                        return;
                    }

                    const assetId = this.getAttribute("data-asset-id");
                    if (assetId) {
                        window.location.href = 'assetdetail?assetId=' + assetId;
                    }
                });
            });
        </script>

        <script>
            function updateFilter() {
                let params = new URLSearchParams(window.location.search);
                const searchBox = document.getElementById("searchBox").value.trim();
                const categoryFilter = document.getElementById("categoryFilter").value;
                const sortBox = document.getElementById("sortBox").value;
                const pageSize = document.getElementById("itemsPerPage").value;
                if (searchBox) {
                    params.set("keySearch", searchBox);
                } else {
                    params.delete("keySearch");
                }

                if (categoryFilter !== "0") {
                    params.set("catId", categoryFilter);
                } else {
                    params.delete("catId");
                }

                if (sortBox !== "0") {
                    params.set("sort", sortBox);
                } else {
                    params.delete("sort");
                }

                if (pageSize !== "5") {
                    params.set("pageSize", pageSize);
                } else {
                    params.delete("pageSize");
                }

                if (params.toString()) {
                    params.set("page", "1");
                } else {
                    params.delete("page");
                }

                const newUrl = params.toString() ? "?" + params.toString() : window.location.pathname;
                window.history.pushState({}, "", newUrl);
                window.location.reload();
            }

            document.getElementById("searchBox").addEventListener("input", function () {
                clearTimeout(this.delay);
                this.delay = setTimeout(updateFilter, 500);
            });
            document.getElementById("categoryFilter").addEventListener("change", updateFilter);
            document.getElementById("sortBox").addEventListener("change", updateFilter);
            document.getElementById("itemsPerPage").addEventListener("change", updateFilter);
            // Xử lý phân trang
            document.querySelectorAll(".page-link").forEach(pageLink => {
                pageLink.addEventListener("click", function (event) {
                    event.preventDefault();
                    let params = new URLSearchParams(window.location.search);
                    let pageText = this.textContent.trim().toLowerCase();
                    if (pageText === "first") {
                        params.set("page", "1"); // Nếu click vào "First" → page = 1
                    } else if (pageText === "last") {
                        params.set("page", "${num}"); // Nếu click vào "Last" → page = num
                    } else {
                        params.set("page", pageText); // Các trang số bình thường
                    }
                    window.location.search = params.toString();
                });
            });
        </script>
    </body>
</html>
