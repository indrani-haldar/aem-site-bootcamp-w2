const MODAL_CONTENT = {
    contactSuccess: {
        icon: '✓',
        iconColor:'#ffffff',
        iconBg:'#146c43',
        title: 'Thank You!',
        description:
            'Your message has been submitted successfully. Our team will contact you shortly.',
        buttonText: 'Close'
    },

    checkout: {
        icon: '🛒',
        iconColor:'#ffffff',
        iconBg:'#4d4d4d',
        title: 'Ready to Checkout?',
        description:
            'You are just one step away from completing your purchase. Please review your order before proceeding to checkout.',
        buttonText: 'Got It'
    }
};
const successModal = document.getElementById('successModal');
const closeModalBtn = document.getElementById('closeModal');

function getCart() {
    return JSON.parse(localStorage.getItem('cart')) || [];
}

function saveCart(cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
}

function updateCartCount() {
    const countElement = document.querySelector('[name="cart-count"]');
    if (!countElement) return;
    countElement.textContent = getCart().length;
}

function renderCart() {
    const cartItems = document.querySelector('.cart-items');
    const cartItems_count = document.querySelector('[name="product-count"]');
    if (!cartItems) return;
    const cart = getCart();
    cartItems_count.textContent = `(${cart.length})`;
    const cartFooter = document.querySelector('.cart-footer');
    cartFooter.style.display = cart.length ? 'block' : 'none';
    if (!cart.length) {
        cartItems.innerHTML = `
        <div class="empty-cart">
        <img src='/content/dam/aem-site-bootcamp-w2/learner007/ShopFast_Empty_Cart_.svg' alt="EmptyCart">
            <span>Your cart is empty.</span>
        </div>
    `;
        return;
    }
    
    cartItems.innerHTML = cart.map(product => `
        <div class="cart-items-list">
                <div class="imagebox"> <img src="${product.images}" alt="${product.title}"/></div>
                <div class="cart-product">
                    <div class="title">${product.title}</div>
                    <div class="action-area" aria-label="Quantity controls for ${product.title}">
                        <button class="action-btn" data-product-minus="${product.id}" aria-label="Decrease quantity of ${product.title}"  ${product.quantity <= 1 ? 'disabled' : ''}>-</button>
                        <span class="qty" aria-label="Current quantity" aria-label="Quantity ${product.quantity}">${product.quantity}</span>
                        <button class="action-btn" data-product-plus="${product.id}" aria-label="Increase quantity of ${product.title}">+</button>
                        <button class="remove_action" data-remove-item-Id=${product.id} aria-label="Remove ${product.title} from cart">Remove</button>
                    </div>
                </div>
            </div>
        `).join('');

    const cart_total_amount = document.querySelector('[name="cart-total-amount"]');
    if (cart_total_amount) {
        cart_total_amount.textContent = `$${(calculateTotal() || 0).toFixed(2)}`;
    }
}

function removeFromCart(productID) {
    let cart = getCart();
    cart = cart.filter((item) => item.id !== productID);
    saveCart(cart);
    updateCartCount();
    renderCart();
}

function increaseQuantity(productID) {
    let cart = getCart();

    const product = cart.find((item) => item.id === productID);

    if (product) {
        product.quantity += 1;
    }

    saveCart(cart);
    updateCartCount();
    renderCart();
}

function decreaseQuantity(productID) {
    let cart = getCart();

    const product = cart.find((item) => item.id === productID);

    if (product && product.quantity > 1) {
        product.quantity -= 1;
    }

    saveCart(cart);
    updateCartCount();
    renderCart();
}


function calculateTotal() {
    const cart = getCart();
    return cart.reduce((total, item) => total + (item.price * item.quantity), 0);
}
function checkout(cartPopup, overlay) {
     cartPopup.classList.add('hidden');
     overlay.classList.remove('active');

   openModal(MODAL_CONTENT.checkout);
}




function openModal(modalData) {
    const modalIcon = document.getElementById('modalIcon');
    modalIcon.textContent=modalData.icon;
    modalIcon.style.color=modalData.iconColor;
    modalIcon.style.backgroundColor = modalData.iconBg;
    document.getElementById('modalTitle').textContent = modalData.title;
    document.getElementById('modalDescription').textContent = modalData.description;
    closeModalBtn.textContent = modalData.buttonText;

    successModal.classList.add('active');
    successModal.setAttribute('aria-hidden', 'false');

    closeModalBtn.focus();
}

function closeModal() {
    successModal.classList.remove('active');
    successModal.setAttribute('aria-hidden', 'true');
}

closeModalBtn?.addEventListener('click', closeModal);

document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape') {
        closeModal();
    }
});

successModal?.addEventListener('click', (event) => {
    if (event.target === successModal) {
        closeModal();
    }
});