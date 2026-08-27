(function () {
    function resolveProductId() {
        var queryId = new URLSearchParams(window.location.search).get("productId");
        if (queryId && /^\d+$/.test(queryId)) {
            return Number(queryId);
        }

        var selectorMatch = window.location.pathname.match(/\.(\d+)\.html$/);
        if (selectorMatch && selectorMatch[1]) {
            return Number(selectorMatch[1]);
        }

        return null;
    }

    function toCurrency(value) {
        var amount = Number(value) || 0;
        return "$" + amount.toFixed(2);
    }

    function sanitizeTitle(title) {
        if (!title) {
            return "Product";
        }
        return String(title).trim();
    }

    function shorten(text, max) {
        if (!text || text.length <= max) {
            return text;
        }
        return text.slice(0, max - 1) + "…";
    }

    function buildProductLink(basePath, productId) {
        var normalized = (basePath || "").replace(/\.html$/, "");
        if (!normalized) {
            return "#";
        }
        return normalized + "." + productId + ".html";
    }

    function renderCards(container, items, basePath) {
        var track = container.querySelector(".qc-related-products__track");
        var status = container.querySelector(".qc-related-products__status");
        if (!track || !status) {
            return;
        }

        track.innerHTML = "";

        if (!items.length) {
            status.textContent = "No related products found.";
            return;
        }

        status.textContent = "";

        items.forEach(function (item) {
            var card = document.createElement("a");
            card.className = "qc-related-products__card";
            card.href = buildProductLink(basePath, item.id);
            card.setAttribute("role", "listitem");
            card.innerHTML = [
                '<div class="qc-related-products__image-wrap">',
                '<img class="qc-related-products__image" src="' + (item.image || "") + '" alt="' + sanitizeTitle(item.title).replace(/"/g, "&quot;") + '">',
                '</div>',
                '<div class="qc-related-products__meta">',
                '<p class="qc-related-products__name">' + shorten(sanitizeTitle(item.title), 28) + '</p>',
                '<p class="qc-related-products__price">' + toCurrency(item.price) + '</p>',
                '</div>'
            ].join("");
            track.appendChild(card);
        });
    }

    function fetchJson(url) {
        return fetch(url, { credentials: "same-origin" })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Request failed");
                }
                return response.json();
            });
    }

    function loadRelatedProducts(container) {
        var status = container.querySelector(".qc-related-products__status");
        var configuredMax = Number(container.getAttribute("data-max-items")) || 5;
        var maxItems = Math.min(5, Math.max(1, configuredMax));
        var basePath = container.getAttribute("data-product-detail-page") || "";
        var currentId = resolveProductId();

        if (!status) {
            return;
        }

        if (!currentId) {
            status.textContent = "Open this page with a product ID to load related products.";
            return;
        }

        status.textContent = "Loading related products...";

        fetchJson("https://fakestoreapi.com/products/" + currentId)
            .then(function (product) {
                if (!product || !product.category) {
                    return [];
                }
                return fetchJson("https://fakestoreapi.com/products/category/" + encodeURIComponent(product.category))
                    .then(function (items) {
                        return (items || [])
                            .filter(function (item) { return Number(item.id) !== Number(currentId); })
                            .slice(0, maxItems);
                    });
            })
            .then(function (relatedItems) {
                renderCards(container, relatedItems, basePath);
            })
            .catch(function () {
                status.textContent = "Unable to load related products right now.";
            });
    }

    document.querySelectorAll(".qc-related-products").forEach(loadRelatedProducts);
})();
