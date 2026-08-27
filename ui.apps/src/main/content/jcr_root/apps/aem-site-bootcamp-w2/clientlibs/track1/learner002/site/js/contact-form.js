document.addEventListener("submit", (event) => {
    if (!event.target.classList.contains("contact-form")) {
        return;
    }
    event.preventDefault();
    const name = event.target.querySelector('input[type="text"]').value.trim();
    const emailInput = document.querySelector(".email-input");
    const emailError = document.querySelector(".email-error");
    const message = event.target.querySelector("textarea").value.trim();
    const email = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    emailError.textContent = "";
    emailInput.classList.remove("input-error");
    if (!name) {
        alert("Please enter your name.");
        return;
    }
    if (!emailRegex.test(email)) {
        emailError.textContent = "Please enter a valid email address.";
        emailInput.classList.add("input-error");
        return;
    }
    if (!message) {
        alert("Please enter your message.");
        return;
    }

    document.querySelector(".contact-success-modal").classList.add("show");
    event.target.reset();
});

document.addEventListener("click", (event) => {
    if (event.target.classList.contains("close-contact-modal")) {
        document.querySelector(".contact-success-modal").classList.remove("show");
    }
});