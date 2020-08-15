const tickets = document.querySelectorAll(".tickets-list");

for (let ticket of tickets) {
    let priority = ticket.querySelector(".ticket-priority");

    if (priority.textContent === "High") {
        priority.classList.add("high-priority-ticket");
    }
}