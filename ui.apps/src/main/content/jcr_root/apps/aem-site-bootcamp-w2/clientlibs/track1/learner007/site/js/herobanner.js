document.addEventListener('DOMContentLoaded', () => {

    const heroBanner = document.querySelector('.hero-banner');

    if (heroBanner) {
        heroBanner.style.backgroundImage =
            `linear-gradient(
                129deg,
                rgb(7, 138, 187),
                rgba(28, 120, 234, 0.4)
            ),
            url("${heroBanner.dataset.bannerImage}")`;

        heroBanner.style.backgroundRepeat = 'no-repeat';
        heroBanner.style.backgroundSize = 'cover';
        heroBanner.style.backgroundPosition = 'center';
    }

});