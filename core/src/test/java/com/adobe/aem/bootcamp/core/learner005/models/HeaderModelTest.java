package com.adobe.aem.bootcamp.core.learner005.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class HeaderModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(HeaderModel.class);
    }

    @Test
    void testHeaderModelProperties() {
        Resource resource = context.create().resource(
                "/content/header",
                "title", "Adobe Bootcamp",
                "navPath", "/content/adobe/us/en"
        );

        HeaderModel model = resource.adaptTo(HeaderModel.class);

        assertEquals("Adobe Bootcamp", model.getTitle());
        assertEquals("/content/adobe/us/en", model.getNavPath());
    }

    @Test
    void testOptionalProperties() {
        Resource resource = context.create().resource("/content/empty-header");

        HeaderModel model = resource.adaptTo(HeaderModel.class);

        assertNull(model.getTitle());
        assertNull(model.getNavPath());
    }
}