
// Ticket priority data for pie chart
const ticketPriorityJsonArray = getChartJsonArray(ticketPriorityCount);

const ticketPriorityNumericData = [];
const ticketPriorityLabelData = [];

for (let i = 0; i < ticketPriorityJsonArray.length; i++) {
    ticketPriorityNumericData[i] = ticketPriorityJsonArray[i].value;
    ticketPriorityLabelData[i] = ticketPriorityJsonArray[i].label;
}

// Pie chart for ticket priorities
new Chart(document.getElementById("ticketPriorityChart"), {
    type: 'pie',
    data: {
        labels: ticketPriorityLabelData,
        datasets: [{
            backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f"],
            data: ticketPriorityNumericData
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


// Project tickets data for pie chart
const ticketProjectJsonArray = getChartJsonArray(ticketProjectCount);

const ticketProjectsNumericData = [];
const ticketProjectsLabelData = [];

for (let i = 0; i < ticketProjectJsonArray.length; i++) {
    ticketProjectsNumericData[i] = ticketProjectJsonArray[i].value;
    ticketProjectsLabelData[i] = 'Project ' + ticketProjectJsonArray[i].label;
}

// Pie chart for project tickets
new Chart(document.getElementById("ticketsProjectsChart"), {
    type: 'pie',
    data: {
        labels: ticketProjectsLabelData,
        datasets: [{
            backgroundColor: ["#34c7e0", "#ff8da4", "#6524c4", "#ad591c", "#abcdef", "#af42af", "#11925e", "#f6ba6f"],
            data: ticketProjectsNumericData
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

function getChartJsonArray(chartData) {
    const chartDataStr = decodeHtml(chartData);
    return JSON.parse(chartDataStr);
}

function decodeHtml(html) {
    const txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}