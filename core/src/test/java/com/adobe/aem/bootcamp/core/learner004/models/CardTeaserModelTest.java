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
class CardTeaserModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private CardTeaserModel fixture;

    @BeforeEach
    void setup() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "cardteaser",
                "imagePath", "/content/dam/image.png",
                "imageAlt", "alt text",
                "title", "Product Title",
                "price", "$19.99",
                "ctaLabel", "Buy Now");

        fixture = resource.adaptTo(CardTeaserModel.class);
    }

    @Test
    void testGetters() {
        assertEquals("/content/dam/image.png", fixture.getImagePath());
        assertEquals("alt text", fixture.getImageAlt());
        assertEquals("Product Title", fixture.getTitle());
        assertEquals("$19.99", fixture.getPrice());
        assertEquals("Buy Now", fixture.getCtaLabel());
    }
}
