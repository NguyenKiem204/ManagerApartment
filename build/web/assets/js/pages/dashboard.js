document.addEventListener("DOMContentLoaded", function () {
  var optionsProfileVisit = {
    annotations: {
      position: "back",
    },
    dataLabels: {
      enabled: false,
    },
    chart: {
      type: "bar",
      height: 300,
    },
    fill: {
      opacity: 1,
    },
    plotOptions: {},
    series: [
      {
        name: "Revenue",
        data: revenueData,
      },
    ],
    colors: "#d5460d",
    xaxis: {
      categories: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
      ],
    },
    yaxis: {
      labels: {
        formatter: function (value) {
          return new Intl.NumberFormat("vi-VN").format(value) + " VND";
        },
      },
    },
  };
  var chartProfileVisit = new ApexCharts(document.querySelector("#chart-profile-visit"), optionsProfileVisit);
  chartProfileVisit.render();

  let chartElement = document.getElementById("chart-visitors-profile");
  let occupied = chartElement.getAttribute("data-occupied");
  let available = chartElement.getAttribute("data-available");

  let optionsVisitorsProfile = {
    series: [parseInt(occupied), parseInt(available)],
    labels: ["Được thuê", "Trống"],
    colors: ["#d5460d", "#55c6e8"],
    chart: {
      type: "donut",
      width: "100%",
      height: "350px",
    },
    legend: {
      position: "bottom",
    },
    plotOptions: {
      pie: {
        donut: {
          size: "30%",
        },
      },
    },
  };
  var chartVisitorsProfile = new ApexCharts(chartElement, optionsVisitorsProfile);
  chartVisitorsProfile.render();

  var optionsEurope = {
    series: [
      {
        name: "series1",
        data: [310, 800, 600, 430, 540, 340, 605, 805, 430, 540, 340, 605],
      },
    ],
    chart: {
      height: 80,
      type: "area",
      toolbar: {
        show: false,
      },
    },
    colors: ["#5350e9"],
    stroke: {
      width: 2,
    },
    grid: {
      show: false,
    },
    dataLabels: {
      enabled: false,
    },
    xaxis: {
      type: "datetime",
      categories: [
        "2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z",
        "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z",
        "2018-09-19T06:30:00.000Z", "2018-09-19T07:30:00.000Z", "2018-09-19T08:30:00.000Z",
        "2018-09-19T09:30:00.000Z", "2018-09-19T10:30:00.000Z", "2018-09-19T11:30:00.000Z",
      ],
      axisBorder: {
        show: false,
      },
      axisTicks: {
        show: false,
      },
      labels: {
        show: false,
      },
    },
    yaxis: {
      labels: {
        show: false,
      },
    },
    tooltip: {
      x: {
        format: "dd/MM/yy HH:mm",
      },
    },
  };

  let optionsAmerica = { ...optionsEurope, colors: ["#008b75"] };
  let optionsIndonesia = { ...optionsEurope, colors: ["#dc3545"] };

  var chartEurope = new ApexCharts(document.querySelector("#chart-europe"), optionsEurope);
  var chartAmerica = new ApexCharts(document.querySelector("#chart-america"), optionsAmerica);
  var chartIndonesia = new ApexCharts(document.querySelector("#chart-indonesia"), optionsIndonesia);

  chartEurope.render();
  chartAmerica.render();
  chartIndonesia.render();
});
