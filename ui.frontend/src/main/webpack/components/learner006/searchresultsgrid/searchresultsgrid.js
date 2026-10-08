document.addEventListener("DOMContentLoaded", function () {
    document
        .querySelectorAll('[data-cmp-is="searchresultsgrid"]')
        .forEach((component) => {
            const searchTerm = component.querySelector("#searchTerm");
            const resultsContainer = component.querySelector("#resultsContainer");
            const cartMessage = component.querySelector("#cartMessage");

            if (!resultsContainer) {
                return;
            }

            const params = new URLSearchParams(window.location.search);
            const query = params.get("q") || "";

            const resultsTitle = component.querySelector(".results-title");

            if (resultsTitle) {
                resultsTitle.textContent = `Results for "${query}"`;
            }

            fetch(`/bin/searchhub/products?q=${encodeURIComponent(query)}`)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch products");
                    }

                    return response.json();
                })
                .then((data) => {
                    console.log("API Response:", data);
                    const products = data;

                    const filteredProducts = products.filter((product) =>
                        product.title.toLowerCase().includes(query.toLowerCase()),
                    );
                    const resultsCount = component.querySelector("#resultsCount");
                    if (resultsCount) {
                        resultsCount.textContent =
                            filteredProducts.length + " results found";
                    }
                    if (!filteredProducts || filteredProducts.length === 0) {
                        resultsContainer.innerHTML = "<h3>No Products Found</h3>";

                        return;
                    }

                    let html = "";

                    filteredProducts.forEach((product) => {
                        html += `
                            <div class="product-card">

                            <img
                            src="${product.image}"
                            alt="${product.title}"
                            class="product-image">
                            <h3>${product.title}</h3>

                            <p class="product-price">
                                ₹${product.price}
                            </p>

                        <button
                                class="add-to-cart"
                                type="button"
                                data-product-title="${product.title}"
                                aria-label="Add ${product.title} to cart">
                                Add To Cart
                            </button>

        </div>
    `;
                    });

                    resultsContainer.innerHTML = html;

                    document.querySelectorAll(".add-to-cart").forEach((button) => {
                        button.addEventListener("click", function () {
                            let count = parseInt(localStorage.getItem("cartCount")) || 0;

                            count++;

                            localStorage.setItem("cartCount", count);

                            const cartElement = document.querySelector(".cart-count");

                            if (cartElement) {
                                cartElement.textContent = count;
                            }

                            const productTitle = button.getAttribute("data-product-title");

                            if (cartMessage) {
                                cartMessage.textContent = `${productTitle} successfully added to cart`;

                                setTimeout(() => {
                                    cartMessage.textContent = "";
                                }, 3000);
                            }
                        });
                    });
                })
                .catch((error) => {
                    console.error("Error:", error);

                    resultsContainer.innerHTML = "<h3>Error Loading Products</h3>";
                });
        });
});
