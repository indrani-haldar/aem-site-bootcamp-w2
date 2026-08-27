(function () {
    function resolveProductId() {
        var queryId = new URLSearchParams(window.location.search).get("productId");
        if (queryId && /^\d+$/.test(queryId)) {
            return queryId;
        }

        var selectorMatch = window.location.pathname.match(/\.(\d+)\.html$/);
        if (selectorMatch && selectorMatch[1]) {
            return selectorMatch[1];
        }

        return null;
    }

    function fetchProductDescription(productId) {
        if (!productId) {
            return Promise.resolve(null);
        }

        var proxyUrl = "/bin/learner009/quickcart/product.json?productId=" + encodeURIComponent(productId);
        return fetch(proxyUrl, { credentials: "same-origin" })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Proxy request failed");
                }
                return response.json();
            })
            .catch(function () {
                return fetch("https://fakestoreapi.com/products/" + encodeURIComponent(productId))
                    .then(function (response) {
                        if (!response.ok) {
                            return null;
                        }
                        return response.json();
                    })
                    .catch(function () {
                        return null;
                    });
            })
            .then(function (product) {
                if (!product || typeof product.description !== "string") {
                    return null;
                }
                return product.description;
            });
    }

    function updateSpecificationsDescription(description) {
        if (!description) {
            return;
        }

        document.querySelectorAll(".qc-accordion__item[data-question]").forEach(function (item) {
            var question = (item.getAttribute("data-question") || "").trim().toLowerCase();
            if (question !== "specifications") {
                return;
            }

            var contentNode = item.querySelector(".qc-accordion__panel-content");
            if (contentNode) {
                contentNode.textContent = description;
            }
        });
    }

    function togglePanel(trigger) {
        var panelId = trigger.getAttribute("aria-controls");
        if (!panelId) {
            return;
        }

        var panel = document.getElementById(panelId);
        if (!panel) {
            return;
        }

        var expanded = trigger.getAttribute("aria-expanded") === "true";
        trigger.setAttribute("aria-expanded", String(!expanded));
        panel.hidden = expanded;
    }

    document.addEventListener("click", function (event) {
        var trigger = event.target.closest(".qc-accordion__trigger");
        if (!trigger) {
            return;
        }
        togglePanel(trigger);
    });

    var productId = resolveProductId();
    fetchProductDescription(productId).then(updateSpecificationsDescription);
})();
