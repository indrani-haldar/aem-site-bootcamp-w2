(function () {
    var STORAGE_KEYS = ["quickcart.items", "quickcart"];
    var FALLBACK_IMAGE = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='56' height='56' viewBox='0 0 56 56'%3E%3Crect width='56' height='56' fill='%23eef2f9'/%3E%3Cpath d='M6 42 19 29l8 8 12-15 11 20H6Z' fill='%23d0d8e6'/%3E%3Ccircle cx='20' cy='18' r='4' fill='%23d0d8e6'/%3E%3C/svg%3E";

    function isPdpPage() {
        if (document.querySelector(".qc-product-detail-hero")) {
            return true;
        }

        var params = new URLSearchParams(window.location.search);
        if (params.get("productId")) {
            return true;
        }

        return /\.(\d+)\.html$/.test(window.location.pathname);
    }

    function normalizeItems(data) {
        if (!data) {
            return [];
        }

        if (Array.isArray(data)) {
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

    function shorten(text, maxLength) {
        if (!text || text.length <= maxLength) {
            return text;
        }
        return text.slice(0, maxLength - 1) + "…";
    }

    function renderMiniCart(header) {
        var panel = header.querySelector(".cmp-quickcart-header__mini-cart");
        var list = header.querySelector(".cmp-quickcart-header__mini-cart-list");
        var emptyNode = header.querySelector(".cmp-quickcart-header__mini-cart-empty");
        var subtotalNode = header.querySelector(".cmp-quickcart-header__mini-cart-subtotal-value");
        var countNode = header.querySelector(".cmp-quickcart-header__cart-count");

        if (!panel || !list || !emptyNode || !subtotalNode || !countNode) {
            return;
        }

        var items = getItems();
        list.innerHTML = "";

        var count = items.reduce(function (total, item) {
            return total + Math.max(Number(item.quantity) || 1, 1);
        }, 0);
        countNode.textContent = String(count);

        if (!items.length) {
            emptyNode.hidden = false;
            subtotalNode.textContent = toPrice(0);
            return;
        }

        emptyNode.hidden = true;
        var subtotal = 0;

        items.forEach(function (item) {
            var qty = Math.max(Number(item.quantity) || 1, 1);
            var price = Number(item.price) || 0;
            var linePrice = qty * price;
            subtotal += linePrice;

            var li = document.createElement("li");
            li.className = "cmp-quickcart-header__mini-cart-item";
            li.innerHTML = [
                '<img class="cmp-quickcart-header__mini-cart-image" src="' + (item.image || FALLBACK_IMAGE) + '" alt="">',
                '<div>',
                '<p class="cmp-quickcart-header__mini-cart-name">' + escapeHtml(shorten(item.title || "Product", 46)) + '</p>',
                '<p class="cmp-quickcart-header__mini-cart-meta">Qty: ' + qty + '</p>',
                '</div>',
                '<span class="cmp-quickcart-header__mini-cart-line-price">' + toPrice(linePrice) + '</span>'
            ].join("");
            list.appendChild(li);
        });

        subtotalNode.textContent = toPrice(subtotal);
    }

    function openMiniCart(header, trigger, panel) {
        renderMiniCart(header);
        panel.hidden = false;
        trigger.setAttribute("aria-expanded", "true");
    }

    function closeMiniCart(trigger, panel) {
        panel.hidden = true;
        trigger.setAttribute("aria-expanded", "false");
    }

    document.querySelectorAll(".cmp-quickcart-header").forEach(function (header) {
        var trigger = header.querySelector(".cmp-quickcart-header__cart-action[data-action='toggle-mini-cart']");
        var panel = header.querySelector(".cmp-quickcart-header__mini-cart");

        if (!trigger || !panel) {
            return;
        }

        renderMiniCart(header);

        trigger.addEventListener("click", function (event) {
            if (!isPdpPage()) {
                return;
            }

            event.preventDefault();

            var isOpen = trigger.getAttribute("aria-expanded") === "true";
            if (isOpen) {
                closeMiniCart(trigger, panel);
            } else {
                openMiniCart(header, trigger, panel);
            }
        });

        document.addEventListener("click", function (event) {
            if (panel.hidden) {
                return;
            }

            if (header.contains(event.target)) {
                return;
            }

            closeMiniCart(trigger, panel);
        });

        document.addEventListener("keydown", function (event) {
            if (event.key === "Escape" && !panel.hidden) {
                closeMiniCart(trigger, panel);
                trigger.focus();
            }
        });

        window.addEventListener("storage", function () {
            renderMiniCart(header);
        });

        window.addEventListener("quickcart:updated", function () {
            renderMiniCart(header);
        });
    });
})();
