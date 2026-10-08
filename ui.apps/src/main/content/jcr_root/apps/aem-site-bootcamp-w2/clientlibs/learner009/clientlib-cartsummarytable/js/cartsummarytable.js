(function () {
    var STORAGE_KEYS = ["quickcart.items", "quickcart"];
    var PRODUCTS_API_BASE = "https://fakestoreapi.com/products";
    var FALLBACK_IMAGE = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='56' height='56' viewBox='0 0 56 56'%3E%3Crect width='56' height='56' fill='%23eef2f9'/%3E%3Cpath d='M6 42 19 29l8 8 12-15 11 20H6Z' fill='%23d0d8e6'/%3E%3Ccircle cx='20' cy='18' r='4' fill='%23d0d8e6'/%3E%3C/svg%3E";

    function normalizeItems(data) {
        if (!data) {
            return [];
        }

        if (Array.isArray(data)) {
            if (data.length && data[0] && Array.isArray(data[0].products)) {
                return data[0].products.map(function (product) {
                    return {
                        id: product.productId,
                        quantity: Number(product.quantity) || 1
                    };
                });
            }
            return data;
        }

        if (Array.isArray(data.items)) {
            return data.items;
        }

        if (Array.isArray(data.products)) {
            return data.products.map(function (product) {
                return {
                    id: product.productId,
                    quantity: Number(product.quantity) || 1
                };
            });
        }

        return [];
    }

    function getItems() {
        for (var i = 0; i < STORAGE_KEYS.length; i += 1) {
            var raw = window.localStorage.getItem(STORAGE_KEYS[i]);
            if (!raw) {
                continue;
            }
            try {
                var parsed = JSON.parse(raw);
                return normalizeItems(parsed);
            } catch (e) {
                return [];
            }
        }
        return [];
    }

    function setItems(items) {
        var normalized = items.map(function (item) {
            var productId = Number(item.productId || item.id);
            return {
                id: productId,
                productId: productId,
                title: item.title || "Product",
                image: item.image || "",
                price: Number(item.price) || 0,
                quantity: Math.max(Number(item.quantity) || 1, 1)
            };
        });

        window.localStorage.setItem("quickcart.items", JSON.stringify(normalized));
        window.localStorage.setItem("quickcart", JSON.stringify({ items: normalized }));
    }

    function toPrice(value) {
        var price = Number(value) || 0;
        return "$" + price.toFixed(2);
    }

    function escapeHtml(text) {
        return String(text || "")
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/\"/g, "&quot;")
            .replace(/'/g, "&#39;");
    }

    function hydrateItems(items) {
        var requests = items.map(function (item) {
            if (item.title && item.price != null) {
                return Promise.resolve(item);
            }

            var productId = item.productId || item.id;
            if (!productId) {
                return Promise.resolve(item);
            }

            return fetch(PRODUCTS_API_BASE + "/" + productId)
                .then(function (response) {
                    if (!response.ok) {
                        return item;
                    }
                    return response.json();
                })
                .then(function (product) {
                    return {
                        id: product.id || productId,
                        productId: product.id || productId,
                        title: product.title || item.title || "Product",
                        image: product.image || item.image || "",
                        price: Number(product.price) || Number(item.price) || 0,
                        quantity: Number(item.quantity) || 1
                    };
                })
                .catch(function () {
                    return item;
                });
        });

        return Promise.all(requests);
    }

    function renderTable(root) {
        var body = root.querySelector(".qc-cart-summary__body");
        var subtotalNode = root.querySelector(".qc-cart-summary__subtotal-value");
        var emptyNode = root.querySelector(".qc-cart-summary__empty");

        if (!body || !subtotalNode || !emptyNode) {
            return;
        }

        var items = getItems();
        body.innerHTML = "";

        if (!items.length) {
            emptyNode.hidden = false;
            subtotalNode.textContent = toPrice(0);
            return;
        }

        hydrateItems(items).then(function (hydratedItems) {
            setItems(hydratedItems);
            emptyNode.hidden = true;

            var subtotal = 0;

            hydratedItems.forEach(function (item, index) {
                var qty = Math.max(Number(item.quantity) || 1, 1);
                var price = Number(item.price) || 0;
                subtotal += qty * price;

                var row = document.createElement("tr");
                row.innerHTML = [
                    '<td><div class="qc-cart-summary__product">'
                        + '<img class="qc-cart-summary__thumb" src="' + (item.image || FALLBACK_IMAGE) + '" alt="">'
                        + '<span class="qc-cart-summary__name">' + escapeHtml(item.title || "Product") + '</span>'
                        + '</div></td>',
                    '<td><div class="qc-cart-summary__qty-control">'
                        + '<button type="button" class="qc-cart-summary__qty-button" data-action="decrement" data-index="' + index + '" aria-label="Decrease quantity">-</button>'
                        + '<span class="qc-cart-summary__qty-value">' + qty + '</span>'
                        + '<button type="button" class="qc-cart-summary__qty-button" data-action="increment" data-index="' + index + '" aria-label="Increase quantity">+</button>'
                        + '</div></td>',
                    '<td><span class="qc-cart-summary__price">' + toPrice(price * qty) + '</span></td>',
                    '<td><button type="button" class="qc-cart-summary__remove" data-action="remove" data-index="' + index + '">Remove</button></td>'
                ].join("");
                body.appendChild(row);
            });

            subtotalNode.textContent = toPrice(subtotal);
        });
    }

    function handleClick(event) {
        var actionTarget = event.target.closest("[data-action]");
        if (!actionTarget) {
            return;
        }

        var container = event.currentTarget;
        var index = Number(actionTarget.getAttribute("data-index"));
        var action = actionTarget.getAttribute("data-action");
        var items = getItems();

        if (!items[index]) {
            return;
        }

        if (action === "increment") {
            items[index].quantity = (Number(items[index].quantity) || 1) + 1;
        } else if (action === "decrement") {
            items[index].quantity = Math.max((Number(items[index].quantity) || 1) - 1, 1);
        } else if (action === "remove") {
            items.splice(index, 1);
        }

        setItems(items);
        renderTable(container);
    }

    document.querySelectorAll(".qc-cart-summary").forEach(function (container) {
        renderTable(container);
        container.addEventListener("click", handleClick);
    });
})();
