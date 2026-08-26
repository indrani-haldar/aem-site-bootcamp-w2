function updateCartCount() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const total = cart.reduce((sum, item) => sum + item.quantity, 0);
    document.querySelectorAll(".cart-count").forEach(badge => { badge.textContent = total; });
}

document.addEventListener("DOMContentLoaded", updateCartCount);
window.addEventListener("load", updateCartCount);

document.addEventListener("click", (event) => {
    if (event.target.closest(".cart-btn")) {
        const miniCart = document.querySelector(".mini-cart-overlay");
        if (typeof openDialog === "function") {
            openDialog(miniCart);
        } else {
            miniCart?.classList.add("show");
        }
        if (typeof renderMiniCart === "function") {
            renderMiniCart();
        }
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll(".header-nav a");
    navLinks.forEach(link => {
        const href = link.getAttribute("href");
        if (currentPath === href) {
            link.classList.add("active");
        }
    });
});