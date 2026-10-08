const categoryButtons = document.querySelectorAll('.shop-category-btn');
categoryButtons.forEach((btn) => {
    btn.addEventListener('click', (e) => {
        categoryButtons.forEach((button)=>{
            button.classList.remove('active');
        })
        e.currentTarget.classList.add('active')
        const category = e.target.dataset.category;
        renderProducts(
            category === 'All'
                ? products
                : products.filter(item => item.category === category)
        );
    });
});

let searchValue = '';
function handleInputSearch(e) {
    const searchValue = e.target.value.trim().toLowerCase();

    if (!searchValue) {
        renderProducts(products);
        return;
    }

    const filteredProducts = products.filter((item) =>
        item.title.toLowerCase().includes(searchValue)
    );
     if (filteredProducts.length === 0) {
        document.querySelector('.featured-Products-grid').innerHTML =
            '<div class="col-12 text-center"><p class="no-products">No products found.</p></div>';
        return;
    }

    renderProducts(filteredProducts);
}