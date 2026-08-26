package com.adobe.aem.bootcamp.core.models.track1.learner003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ProductGridModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void testConfiguredTitle() {
        Resource resource = context.create().resource("/content/product-grid",
                "sling:resourceType", "aem-site-bootcamp-w2/components/track1/learner003/product-grid",
                "title", "Recommended for you");

        ProductGridModel productGrid = resource.adaptTo(ProductGridModel.class);

        assertNotNull(productGrid);
        assertEquals("Recommended for you", productGrid.getTitle());
    }

    @Test
    void testDefaultTitleWhenTitleIsMissing() {
        Resource resource = context.create().resource("/content/product-grid-missing",
                "sling:resourceType", "aem-site-bootcamp-w2/components/track1/learner003/product-grid");

        ProductGridModel productGrid = resource.adaptTo(ProductGridModel.class);

        assertNotNull(productGrid);
        assertEquals("Featured Products", productGrid.getTitle());
    }

    @Test
    void testDefaultTitleWhenTitleIsEmpty() {
        Resource resource = context.create().resource("/content/product-grid-empty",
                "sling:resourceType", "aem-site-bootcamp-w2/components/track1/learner003/product-grid",
                "title", "");

        ProductGridModel productGrid = resource.adaptTo(ProductGridModel.class);

        assertNotNull(productGrid);
        assertEquals("Featured Products", productGrid.getTitle());
    }
}
