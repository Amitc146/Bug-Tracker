ticketPriorityChart(ticketPriorityCount);
ticketsProjectsChart(ticketProjectCount)
userRolesChart(userRolesCount);

function ticketPriorityChart(ticketPriorityCount) {
    const jsonArray = getChartJsonArray(ticketPriorityCount);

    const labelData = [];
    const numericData = [];

    for (let i = 0; i < jsonArray.length; i++) {
        labelData[i] = jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketPriorityChart"), {
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f"],
                data: numericData
            }]
        },

        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Ticket Priorities'
            },
            responsive: true,
            maintainAspectRatio: false
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
        labelData[i] = 'Project ' + jsonArray[i].label;
        numericData[i] = jsonArray[i].value;
    }

    const myChart = new Chart(document.getElementById("ticketsProjectsChart"), {
        type: 'pie',
        data: {
            labels: labelData,
            datasets: [{
                backgroundColor: ["#34c7e0", "#ff8da4", "#6524c4", "#ad591c", "#abcdef", "#af42af", "#11925e", "#f6ba6f"],
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




function getChartJsonArray(chartData) {
    const chartDataStr = decodeHtml(chartData);
    return JSON.parse(chartDataStr);
}

function decodeHtml(html) {
    const txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}