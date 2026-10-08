(function() {
    "use strict";

    var EVENT_NAME = "shopfast:product-search:changed";

    function initSearchBar() {
        var blocks = document.querySelectorAll('[data-cmp-is="shopfast-searchbar"]');
        blocks.forEach(function(block) {
            block.removeAttribute("data-cmp-is");

            var input = block.querySelector('[data-cmp-hook-shopfast-searchbar="input"]');
            if (!input) {
                return;
            }

            input.addEventListener("input", function() {
                document.dispatchEvent(new CustomEvent(EVENT_NAME, {
                    detail: {
                        query: input.value || ""
                    }
                }));
            });
        });
    }

    if (document.readyState !== "loading") {
        initSearchBar();
    } else {
        document.addEventListener("DOMContentLoaded", initSearchBar);
    }
}());
