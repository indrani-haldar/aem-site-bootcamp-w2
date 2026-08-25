
console.log("addtocartbadge.js loaded");
console.log("addToCartBtn:", document.getElementById("addToCartBtn"));
function addToCart(productId) {

    let addedProducts =
        JSON.parse(
            sessionStorage.getItem("addedProducts")
        ) || [];
    console.log("addedProducts:", addedProducts);

    if (addedProducts.includes(productId)) {

        console.log(
            "Product already added to cart"
        );

        return;
    }

    addedProducts.push(productId);

    sessionStorage.setItem(
        "addedProducts",
        JSON.stringify(addedProducts)
    );

    let count =
        parseInt(
            sessionStorage.getItem("cartCount") || 0
        );

    count++;

    sessionStorage.setItem(
        "cartCount",
        count
    );

    updateCartBadge();
};

function updateCartBadge() {

    const badge =
        document.getElementById("cartCount");

    if (badge) {

        badge.innerText =
            sessionStorage.getItem("cartCount") || 0;
    }
}


document.addEventListener(
    "DOMContentLoaded",
    updateCartBadge
);