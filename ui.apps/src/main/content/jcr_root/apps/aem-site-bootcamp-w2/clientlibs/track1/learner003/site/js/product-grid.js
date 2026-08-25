function updateCartCount() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const total = cart.reduce((sum, item) => sum + item.quantity, 0);
    document
        .querySelectorAll(".cart-count")
        .forEach(badge => {
            badge.textContent = total;
        });
}

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

window.addEventListener("load", async () => {
    const container = document.querySelector(".products-container");
    if (!container) {
        return;
    }
    try {
        const response = await fetch("/bin/shopfast/products");
        const products = await response.json();
        debugger
        const productLists = products.products;
        console.log(productLists)
        const randomProducts = productLists.slice(0, 20);
        let currentIndex = 0;
        const cardsPerView = 4;
        function renderProducts() {
            container.innerHTML = "";
            const visibleProducts = randomProducts.slice(currentIndex, currentIndex + cardsPerView);
            const cart = JSON.parse(localStorage.getItem("cart")) || [];
            visibleProducts.forEach(product => {
                const cartItem = cart.find(item => item.id == product.id);
                const buttonHtml = cartItem ?
                    `<div class="qty-wrapper">
                        <button class="qty-minus"data-id="${product.id}" aria-label="Decrease quantity">
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
                    </div>
                `;
            });
        }
        renderProducts();
        updateCartCount();
        document.querySelector(".next-btn").addEventListener("click", () => {
            if (currentIndex + cardsPerView < randomProducts.length) {
                currentIndex += cardsPerView;
                renderProducts();
            }
        });

        document.querySelector(".prev-btn").addEventListener("click", () => {
            if (currentIndex > 0) {
                currentIndex -= cardsPerView;
                renderProducts();
            }
        });

        document.addEventListener("click", (event) => {
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
                renderProducts();
                updateCartCount();
            }

            if (event.target.classList.contains("qty-plus")) {
                const productId = event.target.dataset.id;
                let cart = JSON.parse(localStorage.getItem("cart")) || [];
                const item = cart.find(product => product.id == productId);
                if (item) {
                    item.quantity++;
                    localStorage.setItem("cart", JSON.stringify(cart));
                    renderProducts();
                    updateCartCount();
                }
            }

            if (event.target.classList.contains("qty-minus")) {
                const productId = event.target.dataset.id;
                let cart = JSON.parse(localStorage.getItem("cart")) || [];
                const item = cart.find(product => product.id == productId);
                if (item) {
                    item.quantity--;
                    if (item.quantity <= 0) {
                        cart = cart.filter(product => product.id != productId);
                    }
                    localStorage.setItem("cart", JSON.stringify(cart));
                    renderProducts();
                    updateCartCount();
                }
            }
        });
    }
    catch (error) {
        container.innerHTML = "<p>Unable to load products.</p>";
        console.error(error);
    }
});