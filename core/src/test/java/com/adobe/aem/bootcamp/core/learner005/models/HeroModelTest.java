package com.adobe.aem.bootcamp.core.learner005.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
class HeroModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private HeroModel heroModel;
    private Resource resource;

    @BeforeEach
    void setup() {

        context.addModelsForClasses(HeroModel.class);

        resource = context.create().resource(
                "/content/hero",
                "heading", "Welcome to AEM",
                "subtext", "Build amazing experiences",
                "backgroundImage", "/content/dam/hero-banner.jpg",
                "ctaLink", "/content/site/en/home",
                "ctaLabel", "Learn More",
                "alignment", "center"
        );

        heroModel = resource.adaptTo(HeroModel.class);
    }

    @Test
    void testModelAdaptation() {
        assertNotNull(heroModel);
    }

    @Test
    void testGetHeading() {
        assertEquals("Welcome to AEM", heroModel.getHeading());
    }

    @Test
    void testGetSubtext() {
        assertEquals("Build amazing experiences", heroModel.getSubtext());
    }

    @Test
    void testGetBackgroundImage() {
        assertEquals("/content/dam/hero-banner.jpg", heroModel.getBackgroundImage());
    }

    @Test
    void testGetCtaLink() {
        assertEquals("/content/site/en/home", heroModel.getCtaLink());
    }

    @Test
    void testGetCtaLabel() {
        assertEquals("Learn More", heroModel.getCtaLabel());
    }

    @Test
    void testGetAlignment() {
        assertEquals("center", heroModel.getAlignment());
    }
}