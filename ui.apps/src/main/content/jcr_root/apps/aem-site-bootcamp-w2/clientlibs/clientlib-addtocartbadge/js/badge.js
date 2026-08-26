// document.addEventListener("DOMContentLoaded", function () {

//     console.log("badge.js loaded");

//     const badge = document.getElementById("cart-count");
//     console.log("badge =", badge);
    

//     if (!badge || !button) {
//         return;
//     }

//     button.addEventListener("click", function () {

//         console.log("button clicked");

//         let count =
//             Number(localStorage.getItem("cartCount") || 0);

//         count++;

//         localStorage.setItem("cartCount", count);

//         badge.innerText = count;

//         console.log("new count =", count);
//     });

// });




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
        Number(
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
        const badge = document.getElementById("cart-count");

    if (badge) {

        badge.innerText =
            sessionStorage.getItem("cartCount") || 0;
    }
}


document.addEventListener(
    "DOMContentLoaded",
    updateCartBadge
);