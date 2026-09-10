(function () {
    var STORAGE_KEY = "quickcart.items";

    function getItems() {
        var raw = window.localStorage.getItem(STORAGE_KEY);
        if (!raw) {
            return [];
        }

        try {
            var parsed = JSON.parse(raw);
            return Array.isArray(parsed) ? parsed : [];
        } catch (e) {
            return [];
        }
    }

    function setItems(items) {
        window.localStorage.setItem(STORAGE_KEY, JSON.stringify(items));
        window.dispatchEvent(new CustomEvent("quickcart:updated", { detail: { items: items } }));
    }

    function parseQuantity(input) {
        var qty = Number(input.value);
        if (!Number.isFinite(qty) || qty < 1) {
            return 1;
        }
        return Math.floor(qty);
    }

    function updateQuantity(input, delta) {
        var next = Math.max(parseQuantity(input) + delta, 1);
        input.value = String(next);
    }

    function addToCart(container, quantityInput, statusNode) {
        var productId = container.getAttribute("data-product-id");
        var title = container.getAttribute("data-product-title") || "Product";
        var price = Number(container.getAttribute("data-product-price")) || 0;
        var image = container.getAttribute("data-product-image") || "";
        var quantity = parseQuantity(quantityInput);

        if (!productId) {
            statusNode.textContent = "Unable to add item: missing product id.";
            return;
        }

        var items = getItems();
        var existing = items.find(function (item) {
            return String(item.id) === String(productId);
        });

        if (existing) {
            existing.quantity = (Number(existing.quantity) || 0) + quantity;
        } else {
            items.push({
                id: productId,
                title: title,
                price: price,
                image: image,
                quantity: quantity
            });
        }

        setItems(items);
        statusNode.textContent = quantity + " item(s) added to cart.";
    }

    document.querySelectorAll(".qc-product-detail-hero__purchase").forEach(function (purchaseContainer) {
        var quantityInput = purchaseContainer.querySelector(".qc-product-detail-hero__qty-input");
        var statusNode = purchaseContainer.querySelector(".qc-product-detail-hero__status");

        if (!quantityInput || !statusNode) {
            return;
        }

        purchaseContainer.addEventListener("click", function (event) {
            var actionTarget = event.target.closest("[data-action]");
            if (!actionTarget) {
                return;
            }

            var action = actionTarget.getAttribute("data-action");
            if (action === "increment") {
                updateQuantity(quantityInput, 1);
            } else if (action === "decrement") {
                updateQuantity(quantityInput, -1);
            } else if (action === "add-to-cart") {
                addToCart(purchaseContainer, quantityInput, statusNode);
            }
        });
    });
})();
