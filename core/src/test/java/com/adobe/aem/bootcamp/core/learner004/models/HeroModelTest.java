package com.adobe.aem.bootcamp.core.learner004.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class HeroModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private HeroModel fixture;

    @BeforeEach
    void setup() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "hero",
                "heading", "Welcome",
                "subtext", "Find what you need",
                "backgroundImage", "/content/dam/hero.png",
                "ctaLabel", "Shop Now",
                "ctaLink", "/content/shop");

        fixture = resource.adaptTo(HeroModel.class);
    }

    @Test
    void testGetters() {
        assertEquals("Welcome", fixture.getHeading());
        assertEquals("Find what you need", fixture.getSubtext());
        assertEquals("/content/dam/hero.png", fixture.getBackgroundImage());
        assertEquals("Shop Now", fixture.getCtaLabel());
        assertEquals("/content/shop", fixture.getCtaLink());
    }
}
