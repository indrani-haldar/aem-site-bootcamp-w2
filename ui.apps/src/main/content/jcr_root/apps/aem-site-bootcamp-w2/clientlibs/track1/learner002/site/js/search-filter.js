document.addEventListener("DOMContentLoaded", async () => {
    const searchInput = document.querySelector(".search-input");
    const filterContainer = document.querySelector(".category-filters");
    let selectedCategories = ["all"];
    searchInput?.addEventListener("input", () => {
        document.dispatchEvent(new CustomEvent("shopFilterChanged", {
            detail: {
                searchText: searchInput.value,
                categories: selectedCategories
            }
        }));
    });

    try {
        const response = await fetch("/bin/shopfast/products");
        const data = await response.json();
        const products = data.products || [];
        const categories = [...new Set(products.map(product => product.category))];
        categories.forEach(category => {
            const displayName = category.split("-").map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(" ");
            filterContainer.innerHTML +=
                `<button
                    class="filter-chip"
                    data-category="${category}"
                    aria-label="Filter Option"
                >
                    ${displayName}
                </button>`;
        });
    } catch (error) {
        console.error("Unable to load categories", error);
    }

    filterContainer.addEventListener("click", (event) => {
        const button = event.target.closest(".filter-chip");
        if (!button) {
            return;
        }
        const category = button.dataset.category;
        const allButton = document.querySelector('[data-category="all"]');
        const filterButtons = document.querySelectorAll(".filter-chip");
        if (category === "all") {
            filterButtons.forEach(btn => btn.classList.remove("active"));
            allButton.classList.add("active");
            selectedCategories = ["all"];
        } else {
            allButton.classList.remove("active");
            button.classList.toggle("active");
            selectedCategories = [...document.querySelectorAll(".filter-chip.active")].map(btn => btn.dataset.category);
            if (selectedCategories.length === 0) {
                allButton.classList.add("active");
                selectedCategories = ["all"];
            }
        }
        document.dispatchEvent(new CustomEvent("shopFilterChanged", {
            detail: {
                searchText: searchInput?.value || "",
                categories: selectedCategories
            }
        }));
    });
});