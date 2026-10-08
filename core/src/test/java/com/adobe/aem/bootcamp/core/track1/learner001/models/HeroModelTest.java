package com.adobe.aem.bootcamp.core.track1.learner001.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class HeroModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void testConfiguredValues() {
        Resource resource = context.create().resource("/content/hero",
                "heading", "Shop faster",
                "subtext", "Everything you need in one place.",
                "backgroundImage", "/content/dam/shopfast/hero.jpg",
                "ctaLink", "/content/shop.html",
                "ctaLabel", "Browse products",
                "alignment", "left");

        HeroModel model = resource.adaptTo(HeroModel.class);

        assertEquals("Shop faster", model.getHeading());
        assertEquals("Everything you need in one place.", model.getSubtext());
        assertEquals("/content/dam/shopfast/hero.jpg", model.getBackgroundImage());
        assertEquals("/content/shop.html", model.getCtaLink());
        assertEquals("Browse products", model.getCtaLabel());
        assertEquals("left", model.getAlignment());
    }

    @Test
    void testDefaultAlignment() {
        HeroModel model = context.create().resource("/content/hero").adaptTo(HeroModel.class);

        assertEquals("center", model.getAlignment());
    }
}