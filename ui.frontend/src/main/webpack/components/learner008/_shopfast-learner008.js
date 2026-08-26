(function() {
    "use strict";

    var STORAGE_KEY = "shopfast-learner008-cart";

    function readCart() {
        try {
            var parsed = JSON.parse(localStorage.getItem(STORAGE_KEY) || "[]");
            return Array.isArray(parsed) ? parsed : [];
        } catch (e) {
            return [];
        }
    }

    function writeCart(items) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(items));
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

    function formatPrice(value) {
        var number = Number(value) || 0;
        return "$" + number.toFixed(2);
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

    function initHeader() {
        var header = document.querySelector('[data-cmp-is="shopfast-header"]');
        if (!header) {
            return;
        }

        header.removeAttribute("data-cmp-is");

        var badge = header.querySelector('[data-cmp-hook-shopfast-header="badge"]');
        var openCartButton = header.querySelector('[data-cmp-hook-shopfast-header="open-cart"]');
        var menuButton = header.querySelector('[data-cmp-hook-shopfast-header="menu"]');
        var navLinks = header.querySelectorAll('[data-cmp-hook-shopfast-header="nav-link"]');

        if (menuButton) {
            menuButton.addEventListener("click", function() {
                var open = !header.classList.contains("is-menu-open");
                header.classList.toggle("is-menu-open", open);
                menuButton.setAttribute("aria-expanded", open ? "true" : "false");
                menuButton.setAttribute("aria-label", open ? "Close navigation" : "Open navigation");
            });
        }

        function normalizePath(path) {
            var contentIndex = path.indexOf("/content/");
            var normalized = contentIndex >= 0 ? path.substring(contentIndex) : path;
            normalized = normalized.replace(/\.html$/, "");
            normalized = normalized.replace(/\/$/, "");
            return normalized || "/";
        }

        var currentPath = normalizePath(window.location.pathname);

        navLinks.forEach(function(link) {
            var linkUrl = new URL(link.getAttribute("href") || link.dataset.href || "#", window.location.href);
            var isCurrent = normalizePath(linkUrl.pathname) === currentPath;
            link.classList.toggle("is-active", isCurrent);
            if (isCurrent) {
                link.setAttribute("aria-current", "page");
            }

            link.addEventListener("click", function(event) {
                var target = link.getAttribute("href") || link.dataset.href;
                if (!target) {
                    return;
                }

                event.preventDefault();
                if (menuButton) {
                    header.classList.remove("is-menu-open");
                    menuButton.setAttribute("aria-expanded", "false");
                    menuButton.setAttribute("aria-label", "Open navigation");
                }
                window.location.href = target;
            });
        });

        if (openCartButton) {
            openCartButton.addEventListener("click", function() {
                document.dispatchEvent(new CustomEvent("shopfast:cart:open"));
            });
        }

        document.addEventListener("shopfast:cart:changed", function(event) {
            if (!badge) {
                return;
            }
            var count = event.detail ? event.detail.count : 0;
            badge.textContent = String(count);
            badge.setAttribute("aria-label", "Cart items: " + count);
        });

        broadcastCartChange(readCart());
    }

    function initHero() {
        var buttons = document.querySelectorAll('[data-cmp-hook-shopfast-hero="cta"]');
        buttons.forEach(function(button) {
            button.addEventListener("click", function() {
                var target = button.dataset.href;
                if (target) {
                    window.location.href = target;
                }
            });
        });
    }

    function renderFeaturedProducts(items, target) {
        if (!target) {
            return;
        }

        var fragment = document.createDocumentFragment();

        if (!items.length) {
            var empty = document.createElement("p");
            empty.className = "cmp-learner008-featured__empty";
            empty.textContent = "No products found.";
            fragment.appendChild(empty);
        }

        items.forEach(function(item) {
            var card = document.createElement("article");
            card.className = "cmp-learner008-featured__card";

            var image = document.createElement("img");
            image.className = "cmp-learner008-featured__image";
            image.src = item.image;
            image.alt = item.title;

            var title = document.createElement("h3");
            title.className = "cmp-learner008-featured__product-title";
            title.textContent = item.title;

            var description = document.createElement("p");
            description.className = "cmp-learner008-featured__description";
            description.textContent = item.description;

            var price = document.createElement("p");
            price.className = "cmp-learner008-featured__price";
            price.textContent = formatPrice(item.price);

            var button = document.createElement("button");
            button.className = "cmp-learner008-featured__action";
            button.type = "button";
            button.textContent = "Add to cart";
            button.addEventListener("click", function() {
                addToCart(item);
            });

            card.appendChild(image);
            card.appendChild(title);
            card.appendChild(description);
            card.appendChild(price);
            card.appendChild(button);
            fragment.appendChild(card);
        });

        target.innerHTML = "";
        target.appendChild(fragment);
    }

    function initFeaturedProducts() {
        var blocks = document.querySelectorAll('[data-cmp-is="shopfast-featuredproducts"]');
        if (!blocks.length) {
            return;
        }

        blocks.forEach(function(block) {
            block.removeAttribute("data-cmp-is");

            var endpoint = block.dataset.productsEndpoint;
            var featuredLimit = Number(block.dataset.featuredLimit) || 4;
            var status = block.querySelector('[data-cmp-hook-shopfast-featured="status"]');
            var results = block.querySelector('[data-cmp-hook-shopfast-featured="results"]');

            function updateStatus(text) {
                if (status) {
                    status.textContent = text;
                }
            }

            function getRate(item) {
                if (!item || !item.rating) {
                    return 0;
                }
                return Number(item.rating.rate) || 0;
            }

            function getRateCount(item) {
                if (!item || !item.rating) {
                    return 0;
                }
                return Number(item.rating.count) || 0;
            }

            function loadFeaturedProducts() {
                updateStatus("Loading featured products...");

                fetchJson(endpoint + "?q=&category=all")
                    .then(function(payload) {
                        var items = payload.items || [];
                        items.sort(function(a, b) {
                            var rateDiff = getRate(b) - getRate(a);
                            if (rateDiff !== 0) {
                                return rateDiff;
                            }
                            return getRateCount(b) - getRateCount(a);
                        });

                        var topItems = items.slice(0, featuredLimit);
                        renderFeaturedProducts(topItems, results);
                        updateStatus("Top " + topItems.length + " rated products");
                    })
                    .catch(function() {
                        updateStatus("Unable to load products.");
                        if (results) {
                            results.innerHTML = "";
                        }
                    });
            }

            loadFeaturedProducts();
        });
    }

    function initMiniCartDrawer() {
        var block = document.querySelector('[data-cmp-is="shopfast-cart"]');
        if (!block) {
            return;
        }

        block.removeAttribute("data-cmp-is");

        var cartsEndpoint = block.dataset.cartsEndpoint;
        var openButton = block.querySelector('[data-cmp-hook-shopfast-cart="open"]');
        var headerCartButton = document.querySelector('[data-cmp-hook-shopfast-header="open-cart"]');
        var overlay = block.querySelector('[data-cmp-hook-shopfast-cart="overlay"]');
        var drawer = block.querySelector('[data-cmp-hook-shopfast-cart="drawer"]');
        var closeButton = block.querySelector('[data-cmp-hook-shopfast-cart="close"]');
        var itemsRoot = block.querySelector('[data-cmp-hook-shopfast-cart="items"]');
        var emptyRoot = block.querySelector('[data-cmp-hook-shopfast-cart="empty"]');
        var totalRoot = block.querySelector('[data-cmp-hook-shopfast-cart="total"]');

        function openDrawer() {
            overlay.hidden = false;
            drawer.hidden = false;
            closeButton.focus();
        }

        function closeDrawer() {
            overlay.hidden = true;
            drawer.hidden = true;
            if (headerCartButton) {
                headerCartButton.focus();
            }
        }

        function updateQuantity(id, delta) {
            var items = readCart();
            items = items.map(function(item) {
                if (Number(item.id) === Number(id)) {
                    item.quantity += delta;
                }
                return item;
            }).filter(function(item) {
                return item.quantity > 0;
            });

            writeCart(items);
            broadcastCartChange(items);
        }

        function renderCart(items) {
            itemsRoot.innerHTML = "";

            items.forEach(function(item) {
                var row = document.createElement("div");
                row.className = "cmp-learner008-cart__item";

                var image = document.createElement("img");
                image.src = item.image;
                image.alt = item.title;

                var title = document.createElement("p");
                title.textContent = item.title;

                var price = document.createElement("p");
                price.textContent = formatPrice(item.price);

                var controls = document.createElement("div");
                controls.className = "cmp-learner008-cart__controls";

                var minus = document.createElement("button");
                minus.type = "button";
                minus.textContent = "-";
                minus.addEventListener("click", function() {
                    updateQuantity(item.id, -1);
                });

                var count = document.createElement("span");
                count.textContent = String(item.quantity);

                var plus = document.createElement("button");
                plus.type = "button";
                plus.textContent = "+";
                plus.addEventListener("click", function() {
                    updateQuantity(item.id, 1);
                });

                var remove = document.createElement("button");
                remove.type = "button";
                remove.textContent = "Remove";
                remove.addEventListener("click", function() {
                    updateQuantity(item.id, -item.quantity);
                });

                controls.appendChild(minus);
                controls.appendChild(count);
                controls.appendChild(plus);
                controls.appendChild(remove);

                row.appendChild(image);
                row.appendChild(title);
                row.appendChild(price);
                row.appendChild(controls);
                itemsRoot.appendChild(row);
            });

            emptyRoot.hidden = items.length > 0;
            totalRoot.textContent = formatPrice(cartTotal(items));
        }

        function preloadFromApi() {
            if (readCart().length > 0) {
                broadcastCartChange(readCart());
                return;
            }

            fetchJson(cartsEndpoint)
                .then(function(payload) {
                    var apiItems = payload.items || [];
                    if (!apiItems.length) {
                        broadcastCartChange([]);
                        return;
                    }

                    var normalized = apiItems.map(function(item) {
                        return {
                            id: item.id,
                            title: item.title,
                            image: item.image,
                            price: Number(item.price) || 0,
                            quantity: Number(item.quantity) || 1
                        };
                    });

                    writeCart(normalized);
                    broadcastCartChange(normalized);
                })
                .catch(function() {
                    broadcastCartChange(readCart());
                });
        }

        if (openButton) {
            openButton.addEventListener("click", openDrawer);
        }
        closeButton.addEventListener("click", closeDrawer);
        overlay.addEventListener("click", closeDrawer);

        document.addEventListener("shopfast:cart:open", openDrawer);
        document.addEventListener("shopfast:cart:changed", function(event) {
            renderCart(event.detail ? event.detail.items : []);
        });

        document.addEventListener("keydown", function(event) {
            if (event.key === "Escape" && !drawer.hidden) {
                closeDrawer();
            }
        });

        preloadFromApi();
    }

    function initContactForm() {
        var blocks = document.querySelectorAll('[data-cmp-is="shopfast-contactform"]');
        if (!blocks.length) {
            return;
        }

        blocks.forEach(function(block) {
            block.removeAttribute("data-cmp-is");

            var successMessage = block.dataset.successMessage || "Thanks! Your message has been sent.";
            var form = block.querySelector("form");
            var nameInput = block.querySelector('[data-cmp-hook-shopfast-contact="name"]');
            var emailInput = block.querySelector('[data-cmp-hook-shopfast-contact="email"]');
            var messageInput = block.querySelector('[data-cmp-hook-shopfast-contact="message"]');
            var status = block.querySelector('[data-cmp-hook-shopfast-contact="status"]');

            if (!form || !nameInput || !emailInput || !messageInput || !status) {
                return;
            }

            function setStatus(text, isError) {
                status.textContent = text;
                status.classList.toggle("is-error", Boolean(isError));
            }

            function isValidEmail(email) {
                return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
            }

            form.addEventListener("submit", function(event) {
                event.preventDefault();

                var name = (nameInput.value || "").trim();
                var email = (emailInput.value || "").trim();
                var message = (messageInput.value || "").trim();

                if (name.length < 2) {
                    setStatus("Please enter your name.", true);
                    nameInput.focus();
                    return;
                }

                if (!isValidEmail(email)) {
                    setStatus("Please enter a valid email address.", true);
                    emailInput.focus();
                    return;
                }

                if (message.length < 10) {
                    setStatus("Please enter at least 10 characters in your message.", true);
                    messageInput.focus();
                    return;
                }

                setStatus("Message ready to send.", false);
                alert(successMessage);
                form.reset();
            });
        });
    }

    function init() {
        initHeader();
        initHero();
        initFeaturedProducts();
        initMiniCartDrawer();
        initContactForm();
    }

    if (document.readyState !== "loading") {
        init();
    } else {
        document.addEventListener("DOMContentLoaded", init);
    }
}());
