document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll('[data-cmp-is="hero"]').forEach((hero) => {
    const imageUrl = hero.dataset.backgroundImage;

    if (imageUrl) {
      hero.style.backgroundImage = `url("${imageUrl}")`;

      hero.style.backgroundSize = "cover";
      hero.style.backgroundPosition = "center";
      hero.style.backgroundRepeat = "no-repeat";
    }

    const searchButton = hero.querySelector("#searchButton");

    const searchInput = hero.querySelector("#searchQuery");

    function performSearch() {
      const query = searchInput.value;

      if (query && query.trim() !== "") {
        window.location.href =
          "/content/aem-site-bootcamp-w2/us/Learner006/searchresults.html?q=" +
          encodeURIComponent(query);
      }
    }

    if (searchButton && searchInput) {
      searchButton.addEventListener("click", performSearch);

      searchInput.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
          performSearch();
        }
      });
    }
  });
});
