
const homeLink =
    document.querySelector(".cmp-search-header-nav");

const currentPath =
    window.location.pathname;
console.log("currentPath:", currentPath);
if (currentPath.includes("home-page")) {

    homeLink.classList.add("active");

} else {

    homeLink.classList.remove("active");
}