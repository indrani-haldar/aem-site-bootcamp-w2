// document.querySelectorAll(".hero").forEach((element) => {
//   const imageUrl = element.dataset.backgroundImage;
//   console.log("Background image URL:", imageUrl);

//   if (imageUrl) {
//     element.style.backgroundImage = `url("${imageUrl}")`;
//   }
// });

function submitSearch() {

  const query =
    document.getElementById("searchInput").value;

  console.log("Search query:", query);

  window.location.href =
    "/content/aem-site-bootcamp-w2/us/learner005/search-results.html?q="
    + encodeURIComponent(query);
}
document.addEventListener("DOMContentLoaded", function () {

  const button =

    document.getElementById("searchButton");
    
  if (button) {

    button.addEventListener("click", submitSearch);

  }

});