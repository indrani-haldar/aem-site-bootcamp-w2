document.addEventListener('DOMContentLoaded', () => {
    const currentPath = window.location.pathname.toLowerCase();
    document.querySelectorAll('.shopfast-xf-header__nav a, .shopfast-xf-header__nav .cmp-navigation__item-link').forEach((link) => {
        const href = link.getAttribute('href');
        if (!href) {return;}
        link.classList.toggle('is-active', currentPath.includes(href.split('/').pop().replace('.html', '')));
    });
    document.querySelectorAll('[data-shopfast-header-count]').forEach((element) => {
        try {
            const cart = JSON.parse(sessionStorage.getItem('shopfast-cart-learner001-Tania')) || [];
            element.textContent = cart.reduce((total, item) => total + item.quantity, 0);
        } catch (error) {
            element.textContent = '0';
        }
    });
    document.querySelectorAll('[data-shopfast-cart-trigger]').forEach((button) => {
        button.addEventListener('click', () => {
            if (!document.querySelector('.shopfast__cart')) {window.location.href = button.dataset.shopfastCartTarget;}
        });
    });
});
