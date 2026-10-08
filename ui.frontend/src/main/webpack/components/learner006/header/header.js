document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll('[data-cmp-is="header"]').forEach((header) => {
    const cartCount = header.querySelector(".cart-count");

    if (cartCount) {
      cartCount.textContent = localStorage.getItem("cartCount") || 0;
    }
  });
});
