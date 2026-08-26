document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".hero-banner").forEach((banner) => {
        const imageUrl = banner.dataset.backgroundImage;

        if (imageUrl) {
            banner.style.backgroundImage = `
                linear-gradient(
                    129deg,
                    rgb(7, 138, 187),
                    rgba(28, 120, 234, 0.4)
                ),
                url("${imageUrl}")
            `;
        }
    });

    const heroButton = document.getElementById("heroBtn");

    if (heroButton) { 
        heroButton.addEventListener("click", () => {
            const targetPage = heroButton.dataset.link;

            console.log("Hero CTA clicked:", targetPage);

            if (targetPage) {
                window.location.href = targetPage;
            }
        });
    }

});
