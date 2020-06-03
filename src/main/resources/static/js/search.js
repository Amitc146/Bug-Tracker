const tickets = document.querySelector(".tickets-list");
const projects = document.querySelector(".projects-list");

if (projects) {
    document.getElementById("projects").removeAttribute("hidden");
}

if (tickets) {
    document.getElementById("tickets").removeAttribute("hidden");
}