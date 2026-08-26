
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".promo-block").forEach((banner) => {
        const imageUrl = banner.dataset.backgroundImage;
        if (imageUrl) {
            banner.style.backgroundImage = `
                linear-gradient(
                    389deg,
                    rgb(19 14 14 / 20%),
                    rgb(61 63 64 / 40%)
                ),
                url("${imageUrl}")
            `;
        }
    });

});
