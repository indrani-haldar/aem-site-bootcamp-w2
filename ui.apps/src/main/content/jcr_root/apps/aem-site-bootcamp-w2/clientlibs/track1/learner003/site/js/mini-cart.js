window.addEventListener("load", renderMiniCart);

function openDialog(dialog) {
    if (!dialog) {
        return;
    }
    dialog.classList.add("show");
    dialog.querySelector("button")?.focus();
}

function closeDialog(dialog) {
    dialog?.classList.remove("show");
}

function renderMiniCart() {
    const container = document.querySelector(".mini-cart-content");
    if (!container) {
        return;
    }
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    container.innerHTML = "";
    let subtotal = 0;
    cart.forEach(item => {
        subtotal += Number(item.price) * item.quantity;
        container.innerHTML +=
            `<div class="cart-item">
                <img
                    src="${item.image}"
                    alt="${item.title}"
                    class="cart-item-image"
                >
                <div class="cart-item-details">
                    <div class="cart-item-title">
                        ${item.title}
                    </div>
                    <div class="cart-qty">
                        <button
                            class="mini-cart-minus"
                            data-id="${item.id}"
                            aria-label="Decrease quantity"
                        >
                            -
                        </button>
                        <span>
                            ${item.quantity}
                        </span>
                        <button
                            class="mini-cart-plus"
                            data-id="${item.id}"
                            aria-label="Increase quantity"
                        >
                            +
                        </button>
                    </div>
                </div>
            </div>`;
    });

    const subtotalElement = document.querySelector(".cart-subtotal");
    if (subtotalElement) {
        subtotalElement.textContent = `₹${Math.round(subtotal * 85)}`;
    }

    const checkoutButton = document.querySelector(".checkout-btn");
    if (checkoutButton) {
        if (cart.length === 0 || subtotal <= 0) {
            checkoutButton.disabled = true;
            checkoutButton.classList.add("disabled");
        } else {
            checkoutButton.disabled = false;
            checkoutButton.classList.remove("disabled");
        }
    }
}

document.addEventListener("click", (event) => {
    if (event.target.classList.contains("mini-cart-plus")) {
        const productId = event.target.dataset.id;
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        const item = cart.find(product => product.id == productId);
        if (item) {
            item.quantity++;
            localStorage.setItem("cart", JSON.stringify(cart));
            renderMiniCart();
            updateCartCount();
            if (typeof renderProducts === "function") {
                renderProducts(allProducts, productsContainer);
            }
        }
    }

    if (event.target.classList.contains("mini-cart-minus")) {
        const productId = event.target.dataset.id;
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        const item = cart.find(product => product.id == productId);
        if (!item) {
            return;
        }
        item.quantity--;
        if (item.quantity <= 0) {
            cart = cart.filter(product => product.id != productId);
        }
        localStorage.setItem("cart", JSON.stringify(cart));
        renderMiniCart();
        updateCartCount();
        if (typeof renderProducts === "function") {
            renderProducts(allProducts, productsContainer);
        }
    }
});

document.addEventListener("click", (event) => {
    if (event.target.classList.contains("checkout-btn")) {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        if (cart.length === 0) {
            return;
        }
        const modal = document.querySelector(".checkout-modal");
        openDialog(modal);
        localStorage.removeItem("cart");
        updateCartCount();
        renderMiniCart();
        if (typeof renderProducts === "function") {
            renderProducts(allProducts, productsContainer);
        }
    }
});

document.addEventListener("click", (event) => {
    if (event.target.classList.contains("close-modal-btn")) {
        closeDialog(document.querySelector(".checkout-modal"));
    }
});

document.addEventListener("click", (event) => {
    if (event.target.classList.contains("close-cart-btn")) {
        closeDialog(document.querySelector(".mini-cart-overlay"));
    }
});

document.addEventListener("keydown", (event) => {
    if (event.key !== "Escape") {
        return;
    }
    closeDialog(document.querySelector(".mini-cart-overlay"));
    closeDialog(document.querySelector(".checkout-modal"));
});
