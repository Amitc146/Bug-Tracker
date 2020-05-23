toggleClosedTickets();
togglePriorityColors();
setTabs();

// Low opacity for closed tickets
function toggleClosedTickets() {
    const closedTickets = Array.from(document.querySelectorAll("#closedTickets .tickets-list"));
    for (let ticket of closedTickets) {
        let status = ticket.querySelector(".ticket-status").textContent;
        if (status === "Closed") {
            ticket.classList.add("closed-ticket");
        }
    }
}

// Red color for high priority tickets
function togglePriorityColors() {
    const openTickets = Array.from(document.querySelectorAll("#openTickets .tickets-list"));
    for (let ticket of openTickets) {
        let priority = ticket.querySelector(".ticket-priority");
        if (priority.textContent === "High") {
            priority.classList.add("mild-red");
        }
    }
}


// Switching ticket tabs (open/closed)
function setTabs() {
    const openButton = document.getElementById("openTicketsLink");
    const closedButton = document.getElementById("closedTicketsLink");

    const openTickets = document.getElementById("openTickets");
    const closedTickets = document.getElementById("closedTickets");

    openButton.addEventListener("click", (e) => {
        switchTab(closedTickets, openTickets);
        switchActive(closedButton, openButton);
        e.preventDefault();
    });

    closedButton.addEventListener("click", (e) => {
        switchTab(openTickets, closedTickets);
        switchActive(openButton, closedButton);
        e.preventDefault();
    });
}

function switchActive(from, to) {
    from.classList.remove("active");
    to.classList.add("active");
}

function switchTab(from, to) {
    from.setAttribute("hidden", "hidden");
    to.removeAttribute("hidden");
}