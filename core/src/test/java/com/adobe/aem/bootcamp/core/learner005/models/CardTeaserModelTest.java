package com.adobe.aem.bootcamp.core.learner005.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
class CardTeaserModelTest {

    private final AemContext context = new AemContext();

    private CardTeaserModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(CardTeaserModel.class);

        Resource resource = context.create().resource(
                "/content/card",
                "imagePath", "/content/dam/images/card.jpg",
                "imageAlt", "Card Image",
                "title", "Premium Plan",
                "price", "$99",
                "ctaLabel", "Buy Now"
        );

        model = resource.adaptTo(CardTeaserModel.class);
    }

    @Test
    void testGetImagePath() {
        assertEquals("/content/dam/images/card.jpg", model.getImagePath());
    }

    @Test
    void testGetImageAlt() {
        assertEquals("Card Image", model.getImageAlt());
    }

    @Test
    void testGetTitle() {
        assertEquals("Premium Plan", model.getTitle());
    }

    @Test
    void testGetPrice() {
        assertEquals("$99", model.getPrice());
    }

    @Test
    void testGetCtaLabel() {
        assertEquals("Buy Now", model.getCtaLabel());
    }

    @Test
    void testAllProperties() {
        assertEquals("/content/dam/images/card.jpg", model.getImagePath());
        assertEquals("Card Image", model.getImageAlt());
        assertEquals("Premium Plan", model.getTitle());
        assertEquals("$99", model.getPrice());
        assertEquals("Buy Now", model.getCtaLabel());
    }

    @Test
    void testOptionalProperties() {
        Resource resource = context.create().resource("/content/empty-card");

        CardTeaserModel emptyModel = resource.adaptTo(CardTeaserModel.class);

        assertNull(emptyModel.getImagePath());
        assertNull(emptyModel.getImageAlt());
        assertNull(emptyModel.getTitle());
        assertNull(emptyModel.getPrice());
        assertNull(emptyModel.getCtaLabel());
    }
}
