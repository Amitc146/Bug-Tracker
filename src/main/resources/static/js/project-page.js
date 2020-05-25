const links = document.querySelectorAll("#tabLinks .nav-link");
projectPageTabs(links);

function projectPageTabs(links) {
    const openTickets = document.getElementById("openTickets");
    const closedTickets = document.getElementById("closedTickets");

    for (let link of links) {
        link.addEventListener("click", (e) => {
            const activeTab = document.querySelector("#tabLinks .active");
            activeTab.classList.remove("active");
            e.target.classList.add("active");

            if (activeTab.textContent === "Open") {
                openTickets.setAttribute("hidden", "hidden");
            } else if (activeTab.textContent === "Closed") {
                closedTickets.setAttribute("hidden", "hidden");
            }

            if (e.target.textContent === "Open") {
                openTickets.removeAttribute("hidden");
            } else if (e.target.textContent === "Closed") {
                closedTickets.removeAttribute("hidden");
            }

            e.preventDefault();
        });
    }
}
