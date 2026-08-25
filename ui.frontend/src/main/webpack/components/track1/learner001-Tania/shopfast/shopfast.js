class ShopFastCatalog {
    constructor(root) {
        this.root = root;
        this.mode = root.dataset.shopfastCatalog || 'shop';
        this.products = [];
        this.cart = this.loadCart();
        this.category = '';
        this.searchTerm = '';
        this.grid = root.querySelector('[data-product-grid]');
        this.status = root.querySelector('.shopfast__status');
        this.cartPanel = document.querySelector('[data-shopfast-cart]');
        this.previousFocus = null;
        this.bindEvents();
        this.renderCart();
        this.loadProducts();
    }

    bindEvents() {
        const search = document.querySelector('.shopfast__search');
        search?.addEventListener('input', (event) => {
            this.searchTerm = event.target.value.trim().toLowerCase();
            this.renderProducts();
        });

        document.querySelectorAll('[data-category]').forEach((button) => {
            button.addEventListener('click', () => {
                this.category = button.dataset.category;
                document.querySelectorAll('[data-category]').forEach((item) => {
                    item.classList.toggle('is-active', item === button);
                    item.setAttribute('aria-pressed', item === button ? 'true' : 'false');
                });
                this.renderProducts();
            });
        });

        this.root.querySelector('.shopfast__cart-toggle')?.addEventListener('click', () => this.openCart());
        document.querySelectorAll('[data-shopfast-cart-trigger]').forEach((button) => button.addEventListener('click', () => this.openCart()));
        this.cartPanel?.querySelector('.shopfast__cart-close')?.addEventListener('click', () => this.closeCart());
        document.querySelector('[data-cart-overlay]')?.addEventListener('click', () => this.closeCart());
        document.addEventListener('click', (event) => {
            const addButton = event.target.closest('[data-add-product]');
            if (addButton) {
                this.addToCart(Number(addButton.dataset.addProduct));
            }
            const cartButton = event.target.closest('[data-cart-action]');
            if (cartButton) {
                this.updateCart(Number(cartButton.dataset.cartId), cartButton.dataset.cartAction);
            }
        });
        document.addEventListener('keydown', (event) => {
            if (this.cartPanel && event.key === 'Escape' && this.cartPanel.getAttribute('aria-hidden') === 'false') this.closeCart();
            if (this.cartPanel && event.key === 'Tab' && this.cartPanel.getAttribute('aria-hidden') === 'false') this.trapFocus(event);
        });
    }

    async loadProducts() {
        try {
            const response = await fetch('https://dummyjson.com/products?limit=0');
            if (!response.ok) throw new Error(`Products request failed: ${response.status}`);
            const payload = await response.json();
            this.products = Array.isArray(payload.products) ? payload.products : [];
            if (!this.products.length) throw new Error('Products response was empty');
            this.renderProducts();
            this.renderCart();
            if (this.cartPanel && this.mode === 'shop') this.openCart();
        } catch (error) {
            this.status.textContent = 'Products are temporarily unavailable.';
            this.grid.innerHTML = '<p class="shopfast__message">Please try again shortly.</p>';
            console.error('ShopFast catalog error', error);
        }
    }

    renderProducts() {
        const filtered = this.products.filter((product) => {
            const searchable = `${product.title} ${product.description}`.toLowerCase();
            return (!this.category || product.category === this.category) && searchable.includes(this.searchTerm);
        });
        const visibleProducts = this.mode === 'featured' ? filtered.slice(0, 3) : filtered;
        this.status.textContent = `${visibleProducts.length} product${visibleProducts.length === 1 ? '' : 's'}`;
        this.grid.innerHTML = visibleProducts.length ? visibleProducts.map((product) => {
            const image = product.thumbnail || product.images?.[0] || product.image || '';
            return `
            <article class="shopfast-card">
                <div class="shopfast-card__image-wrap"><img src="${this.escape(image)}" alt="${this.escape(product.title)}" loading="lazy"></div>
                <div class="shopfast-card__body">
                    <h2>${this.escape(product.title)}</h2>
                    <strong>$${Number(product.price).toFixed(2)}</strong>
                    <button type="button" data-add-product="${product.id}">Add to cart</button>
                </div>
            </article>`;
        }).join('') : '<p class="shopfast__message">No products match these filters.</p>';
    }

    renderCart() {
        const count = this.cart.reduce((total, item) => total + item.quantity, 0);
        const total = this.cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
        document.querySelectorAll('.shopfast__cart-count, [data-cart-count], [data-shopfast-header-count]').forEach((element) => { element.textContent = count; });
        if (!this.cartPanel) return;
        this.cartPanel.querySelector('[data-cart-total]').textContent = `$${total.toFixed(2)}`;
        this.cartPanel.querySelector('[data-cart-items]').innerHTML = this.cart.length ? this.cart.map((item) => {
            const image = item.thumbnail || item.images?.[0] || item.image || '';
            return `
            <div class="shopfast-cart-item">
                <img src="${this.escape(image)}" alt="${this.escape(item.title)}">
                <div><strong>${this.escape(item.title)}</strong><span>$${Number(item.price).toFixed(2)}</span>
                    <div class="shopfast-cart-item__controls">
                        <button type="button" data-cart-action="decrease" data-cart-id="${item.id}" aria-label="Decrease ${this.escape(item.title)} quantity">-</button>
                        <span>${item.quantity}</span>
                        <button type="button" data-cart-action="increase" data-cart-id="${item.id}" aria-label="Increase ${this.escape(item.title)} quantity">+</button>
                        <button type="button" data-cart-action="remove" data-cart-id="${item.id}" aria-label="Remove ${this.escape(item.title)} from cart">Remove</button>
                    </div>
                </div>
            </div>`;
        }).join('') : '<p class="shopfast__message">Your cart is empty.</p>';
    }

    addToCart(id) {
        const product = this.products.find((item) => item.id === id);
        if (!product) return;
        const item = this.cart.find((line) => line.id === id);
        if (item) item.quantity += 1;
        else this.cart.push({ ...product, quantity: 1 });
        this.saveCart();
        this.renderCart();
        this.openCart();
    }

    updateCart(id, action) {
        const item = this.cart.find((line) => line.id === id);
        if (!item) return;
        if (action === 'increase') item.quantity += 1;
        if (action === 'decrease') item.quantity -= 1;
        if (action === 'remove' || item.quantity < 1) this.cart = this.cart.filter((line) => line.id !== id);
        this.saveCart();
        this.renderCart();
    }

    openCart() {
        this.previousFocus = document.activeElement;
        if (!this.cartPanel) return;
        this.cartPanel.setAttribute('aria-hidden', 'false');
        document.querySelectorAll('[data-shopfast-cart-trigger], .shopfast__cart-toggle').forEach((button) => button.setAttribute('aria-expanded', 'true'));
        this.root.querySelector('.shopfast__cart-close')?.focus();
    }

    closeCart() {
        if (!this.cartPanel) return;
        this.cartPanel.setAttribute('aria-hidden', 'true');
        document.querySelectorAll('[data-shopfast-cart-trigger], .shopfast__cart-toggle').forEach((button) => button.setAttribute('aria-expanded', 'false'));
        if (this.previousFocus) this.previousFocus.focus();
    }

    trapFocus(event) {
        const focusable = this.cartPanel.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
        const first = focusable[0];
        const last = focusable[focusable.length - 1];
        if (event.shiftKey && document.activeElement === first) {
            last.focus();
            event.preventDefault();
        } else if (!event.shiftKey && document.activeElement === last) {
            first.focus();
            event.preventDefault();
        }
    }

    loadCart() {
        try { return JSON.parse(sessionStorage.getItem('shopfast-cart-learner001-Tania')) || []; } catch (error) { return []; }
    }

    saveCart() { sessionStorage.setItem('shopfast-cart-learner001-Tania', JSON.stringify(this.cart)); }

    escape(value) {
        const element = document.createElement('div');
        element.textContent = value == null ? '' : String(value);
        return element.innerHTML;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('submit', (event) => {
        const form = event.target.closest('form.cmp-form');
        if (!form || !window.location.pathname.includes('/contact')) return;
        event.preventDefault();
        if (!form.reportValidity()) return;
        let status = form.querySelector('[data-shopfast-form-status]');
        if (!status) {
            status = document.createElement('p');
            status.dataset.shopfastFormStatus = 'true';
            status.setAttribute('role', 'status');
            status.setAttribute('aria-live', 'polite');
            form.prepend(status);
        }
        status.textContent = 'Your message was successfully submitted.';
        form.reset();
    }, true);
    document.querySelectorAll('form.cmp-form .cmp-form-text').forEach((field) => {
        const input = field.querySelector('input, textarea');
        const labels = { name: 'Name', email: 'Email', message: 'Message' };
        const label = field.querySelector('label');
        if (input && label && labels[input.name]) label.textContent = labels[input.name];
    });
    const submit = document.querySelector('form.cmp-form .cmp-form-button');
    if (submit) submit.textContent = 'Send Message';
    const currentPath = window.location.pathname.toLowerCase();
    document.querySelectorAll('.shopfast-xf-header__nav a, .shopfast-xf-header__nav .cmp-navigation__item-link').forEach((link) => {
        if (!link.getAttribute('href')) return;
        link.addEventListener('click', (event) => {
            event.preventDefault();
            window.location.assign(link.href);
        });
        link.classList.toggle('is-active', currentPath.includes(link.getAttribute('href').split('/').pop().replace('.html', '')));
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
            if (!document.querySelector('.shopfast__cart')) window.location.href = button.dataset.shopfastCartTarget;
        });
    });
    document.querySelectorAll('[data-shopfast-catalog]').forEach((root) => new ShopFastCatalog(root));
});
