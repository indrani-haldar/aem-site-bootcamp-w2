
const currentPage = window.location.pathname.split('/').pop();

document.querySelectorAll('.site-nav a').forEach(link => {
    const href = link.getAttribute('href');
    if (!href) return;

    const linkPage = href.split('/').pop();

    if (linkPage === currentPage) {
        link.parentElement.classList.add('active');
    }
});

document.addEventListener('click', (e) => {

    const cartButton = e.target.closest('.cart');
    const closeButton = e.target.closest('.close-btn');
    const cartPopup = document.querySelector('.cart-popup');
    const overlay = document.querySelector('.cart-overlay');

    if (cartButton) {
        cartPopup.classList.toggle('hidden');
        overlay.classList.toggle('active');
        renderCart();
        return;
    }
    if (closeButton) {
        cartPopup.classList.add('hidden');
        overlay.classList.remove('active');
        return;
    }


    if (
        cartPopup &&
        !cartPopup.classList.contains('hidden') &&
        !e.target.closest('.cart-popup')
    ) {
        cartPopup.classList.add('hidden');
        overlay.classList.remove('active');
    }
});

document.addEventListener('click', (e) => {
    const removeButton = e.target.closest('[data-remove-item-id]');
    const plusButton = e.target.closest('[data-product-plus]');
    const minusButton = e.target.closest('[data-product-minus]');
    const checkoutButton = e.target.closest('.checkout-btn');

     const cartPopup = document.querySelector('.cart-popup');
    const overlay = document.querySelector('.cart-overlay');

    if (removeButton) {
        removeFromCart(Number(removeButton.dataset.removeItemId));
        return;
    }

    if (plusButton) {
        increaseQuantity(Number(plusButton.dataset.productPlus));
        return;
    }

    if (minusButton) {
        decreaseQuantity(Number(minusButton.dataset.productMinus));
        return;
    }
     if (checkoutButton) {
        checkout(cartPopup,overlay);
    }
});



document.addEventListener('DOMContentLoaded',()=>{
    updateCartCount();
})