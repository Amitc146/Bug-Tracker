const tabs = document.querySelector("ul.card-header-tabs").children;

// Adding click listener to the tabs
for (let link of tabs) {
    link.addEventListener("click", switchTab);
}

function switchTab(e) {
    const nextTab = e.target;
    const prevTab = document.querySelector("a.active");

    nextTab.classList.toggle("active");
    prevTab.classList.toggle("active");

    const usersList = document.getElementById("users");
    const ticketsList = document.getElementById("tickets");

    // Switching from tickets tab to users tab
    if (nextTab.id === "show-users") {
        usersList.removeAttribute("hidden");
        ticketsList.setAttribute("hidden", "hidden");
    }

    // Switching from users tab to tickets tab
    else {
        ticketsList.removeAttribute("hidden");
        usersList.setAttribute("hidden", "hidden");
    }
}