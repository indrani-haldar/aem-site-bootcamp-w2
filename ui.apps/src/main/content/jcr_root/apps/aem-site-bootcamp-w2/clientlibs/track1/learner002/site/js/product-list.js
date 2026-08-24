let allProducts = [];
let productsContainer = null;
window.addEventListener("load", async () => {
    const container = document.querySelector(".products-list-container");
    if (!container) {
        return;
    }
    const response = await fetch("/bin/shopfast/products");
    const products = await response.json();
    allProducts = products.products;
    productsContainer = container;
    renderProducts(allProducts, productsContainer);
});

function getStarRating(rating, maxStars = 5) {
    let html = "";
    for (let i = 1; i <= maxStars; i++) {
        if (rating >= i) {
            html += "★";
        } else if (rating >= i - 0.5) {
            html += "⯪";
        } else {
            html += "☆";
        }
    }
    return html;
}

function renderProducts(allProducts, container) {
    container.innerHTML = "";
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    debugger
    allProducts.forEach(product => {
        const cartItem = cart.find(item => item.id == product.id);
        const buttonHtml = cartItem ?
            `<div class="qty-wrapper">
                <button class="qty-minus" data-id="${product.id}" aria-label="Decrease quantity">
                    -
                </button>
                <span class="qty-count">
                    ${cartItem.quantity}
                </span>
                <button class="qty-plus" data-id="${product.id}" aria-label="Increase quantity">
                    +
                </button>
            </div>`
            :
            `<button
                class="add-to-cart-btn"
                data-id="${product.id}"
                data-title="${product.title}"
                data-price="${product.price}"
                data-image="${product.thumbnail}"
                aria-label="Add to Cart"
            >
                Add To Cart
            </button>`;
        container.innerHTML += `
        <div class="product-card">
            <img src="${product.images[0]}" alt="${product.title}"/>
            <div class="product-content">
                <h3 class="product-title">
                    ${product.title}
                </h3>
                <p class="product-description">
                    ${product.description}
                </p>
                <div class="product-meta">
                    <div class="product-price">
                        ₹${Math.round(product.price * 85)}
                    </div>
                    <div class="product-rating">
                        ${getStarRating(product.rating)}
                        <span>
                            (${product.rating})
                        </span>
                    </div>
                </div>
            </div>
            ${buttonHtml}
        </div>`;
    });
}

document.addEventListener("click", async (event) => {
    const container = document.querySelector(".products-list-container");
    if (!container) {
        return;
    }
    // const response = await fetch("/bin/shopfast/products");
    // const products = await response.json();
    if (event.target.classList.contains("add-to-cart-btn")) {
        const productId = event.target.dataset.id;
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        const existing = cart.find(item => item.id == productId);
        if (existing) {
            existing.quantity++;
        } else {
            cart.push({
                id: productId,
                title: event.target.dataset.title,
                price: event.target.dataset.price,
                image: event.target.dataset.image,
                quantity: 1
            });
        }

        localStorage.setItem("cart", JSON.stringify(cart));
        renderProducts(allProducts, container);
        updateCartCount();
        renderMiniCart();
    }

    if (event.target.classList.contains("qty-plus")) {
        const productId = event.target.dataset.id;
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        const item = cart.find(p => p.id == productId);
        if (item) {
            item.quantity++;
            localStorage.setItem("cart", JSON.stringify(cart));
            renderProducts(allProducts, container);
            updateCartCount();
            renderMiniCart();
        }
    }

    if (event.target.classList.contains("qty-minus")) {
        const productId = event.target.dataset.id;
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        const item = cart.find(p => p.id == productId);
        if (item) {
            item.quantity--;
            if (item.quantity <= 0) {
                cart = cart.filter(p => p.id != productId);
            }
            localStorage.setItem("cart", JSON.stringify(cart));
            renderProducts(allProducts, container);
            updateCartCount();
            renderMiniCart();
        }
    }
});

function updateCartCount() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const total = cart.reduce((sum, item) => sum + item.quantity, 0);
    document
        .querySelectorAll(".cart-count")
        .forEach(badge => {
            badge.textContent = total;
        });
}

document.addEventListener("shopFilterChanged", (event) => {
    console.log(event.detail.categories);
    const searchText = event.detail.searchText.toLowerCase().trim();
    const selectedCategories = event.detail.categories || ["all"];
    let filteredProducts = [...allProducts];
    if (searchText) {
        filteredProducts = filteredProducts.filter(product => product.title.toLowerCase().includes(searchText) || product.description.toLowerCase().includes(searchText));
    }
    if (!selectedCategories.includes("all")) {
        filteredProducts = filteredProducts.filter(product => selectedCategories.includes(product.category.toLowerCase()));
    }
    renderProducts(filteredProducts, productsContainer);
});