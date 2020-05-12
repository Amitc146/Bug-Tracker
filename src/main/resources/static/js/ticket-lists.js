const tickets = Array.from(document.querySelectorAll("#tickets"));

toggleClosedTickets(tickets);
togglePriorityColors(tickets);

// Low opacity for closed tickets
function toggleClosedTickets(tickets) {
    for (let ticket of tickets) {
        let status = ticket.querySelector("#ticketStatus").textContent;
        if (status === "Closed") {
            ticket.classList.add("closed-ticket");
        }
    }
}

// Red color for high priority tickets
function togglePriorityColors(tickets) {
    for (let ticket of tickets) {
        let priority = ticket.querySelector("#ticketPriority");

        if (priority.textContent === "High") {
            priority.classList.add("mild-red");
        }
    }
}
