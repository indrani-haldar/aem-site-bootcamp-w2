document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('[data-shopfast-search-filter]').forEach((filterBar) => {
        const search = filterBar.querySelector('.shopfast__search');
        const emitFilters = () => {
            document.dispatchEvent(new CustomEvent('shopfast:filterschange', {
                detail: {
                    category: filterBar.querySelector('[data-category].is-active')?.dataset.category || '',
                    searchTerm: search?.value.trim().toLowerCase() || ''
                }
            }));
        };

        search?.addEventListener('input', emitFilters);
        filterBar.querySelectorAll('[data-category]').forEach((button) => {
            button.addEventListener('click', () => {
                filterBar.querySelectorAll('[data-category]').forEach((item) => {
                    item.classList.toggle('is-active', item === button);
                    item.setAttribute('aria-pressed', item === button ? 'true' : 'false');
                });
                emitFilters();
            });
        });
    });
});
