document.querySelectorAll(".hero").forEach((element) => {
  const imageUrl = element.dataset.backgroundImage;
  console.log("Background image URL:", imageUrl);
 
  if (imageUrl) {
    element.style.backgroundImage = `url("${imageUrl}")`;
  }
});


function submitSearch() {

    const query =
        document.getElementById("searchInput").value;

        console.log("Search query:", query);

    window.location.href =
        "search-results-page.html?q="
        + encodeURIComponent(query);
}
document.getElementById("searchButton").addEventListener("click", submitSearch);
