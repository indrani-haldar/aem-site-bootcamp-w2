(function() {
    "use strict";

    var CART_STORAGE_KEY = "shopfast-learner008-cart";
    var SEARCH_EVENT_NAME = "shopfast:product-search:changed";

    function readCart() {
        try {
            var parsed = JSON.parse(localStorage.getItem(CART_STORAGE_KEY) || "[]");
            return Array.isArray(parsed) ? parsed : [];
        } catch (e) {
            return [];
        }
    }

    function writeCart(items) {
        localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(items));
    }

    function cartCount(items) {
        return items.reduce(function(sum, item) {
            return sum + (item.quantity || 0);
        }, 0);
    }

    function cartTotal(items) {
        return items.reduce(function(sum, item) {
            return sum + ((item.price || 0) * (item.quantity || 0));
        }, 0);
    }

    function broadcastCartChange(items) {
        document.dispatchEvent(new CustomEvent("shopfast:cart:changed", {
            detail: {
                count: cartCount(items),
                total: cartTotal(items),
                items: items
            }
        }));
    }

    function addToCart(product) {
        if (!product || !product.id) {
            return;
        }

        var items = readCart();
        var existing = items.find(function(item) {
            return Number(item.id) === Number(product.id);
        });

        if (existing) {
            existing.quantity += 1;
        } else {
            items.push({
                id: Number(product.id),
                title: product.title || "Product",
                image: product.image || "",
                price: Number(product.price) || 0,
                quantity: 1
            });
        }

        writeCart(items);
        broadcastCartChange(items);
    }

    function formatPrice(value) {
        var number = Number(value) || 0;
        return "$" + number.toFixed(2);
    }

    function fetchJson(url) {
        return fetch(url, {
            headers: { "Accept": "application/json" }
        }).then(function(response) {
            if (!response.ok) {
                throw new Error("Request failed");
            }
            return response.json();
        });
    }

    function renderProducts(items, results, emptyLabel) {
        var fragment = document.createDocumentFragment();

        if (!items.length) {
            var empty = document.createElement("p");
            empty.className = "cmp-learner008-productlist__empty";
            empty.textContent = emptyLabel;
            fragment.appendChild(empty);
        }

        items.forEach(function(item) {
            var card = document.createElement("article");
            card.className = "cmp-learner008-productlist__card";

            var image = document.createElement("img");
            image.className = "cmp-learner008-productlist__image";
            image.src = item.image;
            image.alt = item.title;

            var title = document.createElement("h3");
            title.className = "cmp-learner008-productlist__title";
            title.textContent = item.title;

            var description = document.createElement("p");
            description.className = "cmp-learner008-productlist__description";
            description.textContent = item.description;

            var meta = document.createElement("p");
            meta.className = "cmp-learner008-productlist__meta";
            var rating = item.rating || {};
            var rate = rating.rate || "0";
            var count = rating.count || "0";
            meta.textContent = (item.category || "Product") + " | Rating " + rate + " (" + count + ")";

            var price = document.createElement("p");
            price.className = "cmp-learner008-productlist__price";
            price.textContent = formatPrice(item.price);

            var button = document.createElement("button");
            button.className = "cmp-learner008-productlist__action";
            button.type = "button";
            button.textContent = "Add to cart";
            button.addEventListener("click", function() {
                addToCart(item);
            });

            card.appendChild(image);
            card.appendChild(title);
            card.appendChild(description);
            card.appendChild(meta);
            card.appendChild(price);
            card.appendChild(button);
            fragment.appendChild(card);
        });

        results.innerHTML = "";
        results.appendChild(fragment);
    }

    function initProductList() {
        var blocks = document.querySelectorAll('[data-cmp-is="shopfast-productlist"]');
        blocks.forEach(function(block) {
            block.removeAttribute("data-cmp-is");

            var endpoint = block.dataset.productsEndpoint;
            var loadingLabel = block.dataset.loadingLabel || "Loading products...";
            var emptyLabel = block.dataset.emptyLabel || "No products found.";
            var status = block.querySelector('[data-cmp-hook-shopfast-productlist="status"]');
            var results = block.querySelector('[data-cmp-hook-shopfast-productlist="results"]');

            if (!endpoint || !status || !results) {
                return;
            }

            function setStatus(text) {
                status.textContent = text;
            }

            function loadProducts(query) {
                var params = new URLSearchParams();
                params.set("q", query || "");
                params.set("category", "all");

                setStatus(loadingLabel);
                fetchJson(endpoint + "?" + params.toString())
                    .then(function(payload) {
                        var items = payload.items || [];
                        renderProducts(items, results, emptyLabel);
                        setStatus(items.length + " products");
                    })
                    .catch(function() {
                        results.innerHTML = "";
                        setStatus("Unable to load products.");
                    });
            }

            document.addEventListener(SEARCH_EVENT_NAME, function(event) {
                loadProducts(event.detail ? event.detail.query : "");
            });

            loadProducts("");
        });
    }

    if (document.readyState !== "loading") {
        initProductList();
    } else {
        document.addEventListener("DOMContentLoaded", initProductList);
    }
}());
