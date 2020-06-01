toggleDemoLogin();

function toggleDemoLogin() {
    const demoLoginLink = document.getElementById("demoLoginLink");
    demoLoginLink.addEventListener("click", (e) => {
        const demoLoginSection = document.getElementById("demoLogin");
        demoLoginSection.removeAttribute("hidden");
        e.preventDefault();
    });

}