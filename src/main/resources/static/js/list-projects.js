const links = document.querySelectorAll("#tabLinks .nav-link");
listProjectTabs(links);

function listProjectTabs(links) {
    const myProjects = document.getElementById("myProjects");
    const allProjects = document.getElementById("allProjects");

    for (let link of links) {
        link.addEventListener("click", (e) => {
            const activeTab = document.querySelector("#tabLinks .active");
            activeTab.classList.remove("active");
            e.target.classList.add("active");

            if (activeTab.textContent === "My Projects") {
                myProjects.setAttribute("hidden", "hidden");
            } else if (activeTab.textContent === "All Projects") {
                allProjects.setAttribute("hidden", "hidden");
            }

            if (e.target.textContent === "My Projects") {
                myProjects.removeAttribute("hidden");
            } else if (e.target.textContent === "All Projects") {
                allProjects.removeAttribute("hidden");
            }

            e.preventDefault();
        });
    }
}