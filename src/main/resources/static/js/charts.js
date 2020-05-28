ticketPriorityChart(ticketPriorityCount);
ticketStatusChart(ticketStatusCount)
ticketsProjectsChart(ticketProjectCount)
userRolesChart(userRolesCount);
userProjectsChart(userProjectsCount);

function ticketPriorityChart(ticketPriorityCount) {
    const jsonArray = getChartJsonArray(ticketPriorityCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        let tempString = jsonArray[i].label;
        labelData[i] = tempString.charAt(0).toUpperCase() + tempString.slice(1);
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketPriorityChart"), {
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#00ffc9", "#edff00", "#ff0051"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                fontSize: 15,
                fontColor: "#000",
                text: 'Priorities'
            },
            responsive: true,
            maintainAspectRatio: false,
        }
    });
}

function userRolesChart(userRolesCount) {
    const jsonArray = getChartJsonArray(userRolesCount);

    const labelData = [];
    const numericData = [];

    labelData[0] = "Employee";
    labelData[1] = "Manager";
    labelData[2] = "Admin";

    for (let i = 0; i < jsonArray.length; i++) {
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("userRolesChart"), {
        type: 'doughnut',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#ff0059", "#00ffa6", "#6200ff"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                fontSize: 15,
                fontColor: "#000",
                text: 'Roles'
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
}


function ticketStatusChart(ticketStatusCount) {
    const jsonArray = getChartJsonArray(ticketStatusCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        let tempString = jsonArray[i].label;
        labelData[i] = tempString.charAt(0).toUpperCase() + tempString.slice(1);
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketStatusChart"), {
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#665b84", "#c0acf8"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                fontSize: 15,
                fontColor: "#000",
                text: 'Statuses'
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
}


function ticketsProjectsChart(ticketProjectCount) {
    const jsonArray = getChartJsonArray(ticketProjectCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        labelData[i] = jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketsProjectsChart"), {
        type: 'bar',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ['rgba(0,183,255,0.3)',
                    'rgba(0,255,146,0.3)',
                    'rgba(255,230,0,0.3)',
                    'rgba(157,0,255,0.3)',
                    'rgba(255,0,0,0.3)',
                    'rgba(255,0,213,0.3)'
                ],
                borderWidth: 1,
                borderColor: "#000000",
                barPercentage: 0.8,
                data: numericData,
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                fontSize: 15,
                fontColor: "#000",
                text: 'Open Tickets'
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    stacked: true
                }],
                yAxes: [{
                    stacked: true,
                    ticks: {
                        precision: 0,
                    },
                }]
            }
        }
    });
}

function userProjectsChart(projectUsersCount) {
    const jsonArray = getChartJsonArray(projectUsersCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        labelData[i] = jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("userProjectsChart"), {
        type: 'doughnut',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#11a7f6", "#fc5522", "#9e71de", "#f6ba6f", "#11925e", "#0036ff", "#ff00d5"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                fontSize: 15,
                fontColor: "#000",
                text: 'Projects'
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
}


function getChartJsonArray(chartData) {
    const chartDataStr = decodeHtml(chartData);
    return JSON.parse(chartDataStr);
}

function decodeHtml(html) {
    const txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}