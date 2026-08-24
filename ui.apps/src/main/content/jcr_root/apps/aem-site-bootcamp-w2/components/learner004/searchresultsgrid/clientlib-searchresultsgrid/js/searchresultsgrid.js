
const params =
    new URLSearchParams(
        window.location.search);

const query =
    params.get("q");

loadResults(query);

function loadResults(query) {

    fetch(
        "/bin/searchhub/results?q="
        + encodeURIComponent(query))

        .then(response => response.json())

        .then(products => {
            console.log("products:", products);
            document.getElementById(
                "loading"
            ).style.display = "none";

            renderResults(
                products,
                query);
        });
}

function renderResults(products, query) {

    const container =
        document.getElementById(
            "resultsContainer");

    if (products.length === 0) {

        document.getElementById(
            "emptyState")
            .innerHTML =
            "No results found for '" +
            query + "'";

        document.getElementById(
            "emptyState")
            .style.display = "block";

        return;
    }

    let resultsHeader=document.getElementById("resultsHeader");
    resultsHeader.innerHTML=`
        <h2>Results for '${query}'</h2>
        <p>${products.length} results found</p>
        `;

    let html = ``;

    products.forEach(product => {

        html += `
            <div class="cmp-search-card">
                <img src="${product.image}" alt="${product.title}">
                <h3 class="cmp-search-card-title">${product.title}</h3>
                <p class="cmp-search-card-price">$${product.price}</p>
                <div class="cmp-search-card-content">
                <button class="cmp-add-to-cart-btn" id="addToCartBtn" onclick="addToCart(${product.id})">
                        Add To Cart
                </button>
                </div>
            </div>
        `;
    });

    container.innerHTML = html;
}