package com.adobe.aem.bootcamp.core.track1.learner001.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class CardTeaserModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void testConfiguredValues() {
        Resource resource = context.create().resource("/content/card",
                "imagePath", "/content/dam/shopfast/product.jpg",
                "imageAlt", "Blue product",
                "title", "Product title",
                "description", "Product description",
                "price", "$19.99",
                "productId", "42");

        CardTeaserModel model = resource.adaptTo(CardTeaserModel.class);

        assertEquals("/content/dam/shopfast/product.jpg", model.getImagePath());
        assertEquals("Blue product", model.getImageAlt());
        assertEquals("Product title", model.getTitle());
        assertEquals("Product description", model.getDescription());
        assertEquals("$19.99", model.getPrice());
        assertEquals("42", model.getProductId());
    }

    @Test
    void testDefaultValues() {
        CardTeaserModel model = context.create().resource("/content/card", "title", "Product title")
                .adaptTo(CardTeaserModel.class);

        assertEquals("Product title", model.getImageAlt());
        assertEquals("$0.00", model.getPrice());
        assertNull(model.getImagePath());
        assertNull(model.getDescription());
        assertNull(model.getProductId());
    }
}