ticketPriorityChart(ticketPriorityCount);
ticketsProjectsChart(ticketProjectCount)
ticketsProjectsChart2(ticketProjectCount)
userRolesChart(userRolesCount);
projectUsersChart(projectUsersCount);

function ticketPriorityChart(ticketPriorityCount) {
    const jsonArray = getChartJsonArray(ticketPriorityCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        let tempString = jsonArray[i].label.toLowerCase();
        labelData[i] = tempString.charAt(0).toUpperCase() + tempString.slice(1);
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketPriorityChart"), {
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#d1db46", "#48ceb2", "#d73d54"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Open tickets Priorities'
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
                text: 'Users by Role'
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
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#8e5ea2", "#4dc6db", "#ff8da4", "#3cba9f", "#abcdef", "#11925e", "#f6ba6f"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Tickets per Project'
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
}


function ticketsProjectsChart2(ticketProjectCount) {
    const jsonArray = getChartJsonArray(ticketProjectCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        labelData[i] = jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketsProjectsChart2"), {
        type: 'bar',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ['rgba(0,183,255,0.3)',
                    'rgba(0,255,146,0.3)',
                    'rgba(255,230,0,0.3)',
                    'rgba(157,0,255,0.3)'
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
                text: 'Tickets per Project'
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    stacked: true
                }],
                yAxes: [{
                    stacked: true
                }]
            }
        }
    });
}

function projectUsersChart(projectUsersCount) {
    const jsonArray = getChartJsonArray(projectUsersCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        labelData[i] = jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("projectUsersChart"), {
        type: 'doughnut',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#9e71de", "#11925e", "#f6ba6f", "#fc5522", "#11a7f6"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Number of users assigned to each project'
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