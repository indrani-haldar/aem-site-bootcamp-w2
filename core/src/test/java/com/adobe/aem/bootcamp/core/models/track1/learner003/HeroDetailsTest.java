package com.adobe.aem.bootcamp.core.models.track1.learner003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class HeroDetailsTest {

    private final AemContext context = AppAemContext.newAemContext();

    private HeroDetails heroDetails;

    @BeforeEach
    void setup() {
        Resource resource = context.create().resource("/content/hero",
                "sling:resourceType", "aem-site-bootcamp-w2/components/track1/learner003/hero-details",
                "heading", "ShopFast",
                "subtext", "Everything you need, delivered fast.",
                "backgroundImage", "/content/dam/shopfast/hero.jpg",
                "ctaLink", "/content/shop.html",
                "ctaLabel", "Shop now");

        heroDetails = resource.adaptTo(HeroDetails.class);
    }

    @Test
    void testGetters() {
        assertNotNull(heroDetails);
        assertEquals("ShopFast", heroDetails.getHeading());
        assertEquals("Everything you need, delivered fast.", heroDetails.getSubtext());
        assertEquals("/content/dam/shopfast/hero.jpg", heroDetails.getBackgroundImage());
        assertEquals("/content/shop.html", heroDetails.getCtaLink());
        assertEquals("Shop now", heroDetails.getCtaLabel());
    }
}
