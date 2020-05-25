const links = document.querySelectorAll("#tabLinks .nav-link");

listTicketsTabs(links);

function listTicketsTabs(links) {
    const myTickets = document.getElementById("myTickets");
    const allTickets = document.getElementById("allTickets");
    const closedTickets = document.getElementById("closedTickets");

    for (let link of links) {
        link.addEventListener("click", (e) => {
            const activeTab = document.querySelector("#tabLinks .active");
            activeTab.classList.remove("active");
            e.target.classList.add("active");

            if (activeTab.textContent === "My Tickets") {
                myTickets.setAttribute("hidden", "hidden");
            } else if (activeTab.textContent === "All Tickets") {
                allTickets.setAttribute("hidden", "hidden");
            } else if (activeTab.textContent === "Closed Tickets") {
                closedTickets.setAttribute("hidden", "hidden");
            }

            if (e.target.textContent === "My Tickets") {
                myTickets.removeAttribute("hidden");
            } else if (e.target.textContent === "All Tickets") {
                allTickets.removeAttribute("hidden");
            } else if (e.target.textContent === "Closed Tickets") {
                closedTickets.removeAttribute("hidden");
            }

            e.preventDefault();
        });
    }
}