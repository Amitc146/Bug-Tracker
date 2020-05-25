const tickets = document.querySelectorAll(".tickets-list");

setTicketsStyles(tickets);

function setTicketsStyles(tickets) {
    for (let ticket of tickets) {
        let priority = ticket.querySelector(".ticket-priority");
        let status = ticket.querySelector(".ticket-status");

        if (priority.textContent === "High" && status.textContent !== "Closed") {
            priority.classList.add("mild-red");
        }

        if (status.textContent === "Closed") {
            ticket.classList.add("closed-ticket");
        }
    }
}
