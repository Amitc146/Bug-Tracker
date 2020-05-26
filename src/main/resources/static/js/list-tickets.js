const links = document.querySelectorAll("#tabLinks .nav-link");

listTicketsTabs(links);

function listTicketsTabs(links) {
    const openTickets = document.getElementById("openTickets");
    const closedTickets = document.getElementById("closedTickets");

    for (let link of links) {
        link.addEventListener("click", (e) => {
            const activeTab = document.querySelector("#tabLinks .active");
            activeTab.classList.remove("active");
            e.target.classList.add("active");

            if (activeTab.textContent === "Open Tickets") {
                openTickets.setAttribute("hidden", "hidden");
            } else if (activeTab.textContent === "Closed Tickets") {
                closedTickets.setAttribute("hidden", "hidden");
            }

            if (e.target.textContent === "Open Tickets") {
                openTickets.removeAttribute("hidden");
            } else if (e.target.textContent === "Closed Tickets") {
                closedTickets.removeAttribute("hidden");
            }

            e.preventDefault();
        });
    }
}