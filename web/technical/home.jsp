
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Trang chủ</title>
    <style>
        body {
            background-color: #FFA07A;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            margin: 0;
        }
       
        .chart-container {
            width: 50%;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            margin: 20px 0;
            margin-left: 300px;
        }

        canvas {
            width: 90% !important;
            height: 300px !important;
        }
    </style>
</head>

<body>
    <%@include file="/manager/menumanager.jsp" %>
        <div class="chart-container">
            <h3>Request Statistics</h3>
            <canvas id="requestChart"></canvas>
        </div>

        <div class="chart-container">
            <h3>Feedback Statistics</h3>
            <canvas id="feedbackChart"></canvas>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            let requestChart = null;
            let feedbackChart = null;

            function createChart(canvasId, data, label, color, chartRef) {
                let ctx = document.getElementById(canvasId).getContext("2d");

                // Hủy biểu đồ cũ nếu tồn tại
                if (chartRef) {
                    chartRef.destroy();
                }

                let monthLabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                let monthData = new Array(12).fill(0);

                // Điền dữ liệu vào đúng tháng
                data.forEach(d => {
                    monthData[d.month - 1] = d.total;
                });

                return new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: monthLabels,
                        datasets: [{
                                label: label,
                                data: monthData,
                                backgroundColor: color
                            }]
                    },
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true,
                                max: 50,
                                ticks: {
                                    stepSize: 10
                                }
                            }
                        }
                    }
                });
            }

            function fetchChartData(url, canvasId, label, color, chartRef, callback) {
                $.getJSON(url, function (data) {
                    console.log(label + " Data:", data);
                    callback(createChart(canvasId, data, label, color, chartRef));
                }).fail(function () {
                    console.error("Lỗi khi lấy dữ liệu từ " + url);
                });
            }

            // Gọi API để lấy dữ liệu và vẽ biểu đồ
            fetchChartData("/ManagerApartment/request-stats", "requestChart", "Số lượng Request", "rgba(255, 99, 132, 0.7)", requestChart, function (chart) {
                requestChart = chart;
            });

            fetchChartData("/ManagerApartment/feedback-stats", "feedbackChart", "Số lượng Feedback", "rgba(54, 162, 235, 0.7)", feedbackChart, function (chart) {
                feedbackChart = chart;
            });
        });
    </script>

</body>

</html>
