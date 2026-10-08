package com.adobe.aem.bootcamp.core.learner002.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.bootcamp.core.models.track1.learner002.ProductGridModel;

@ExtendWith(AemContextExtension.class)
class ProductGridModelTest {

    private final AemContext context = new AemContext();

    private ProductGridModel productGridModel;

    @BeforeEach
    void setUp() {

        context.addModelsForClasses(
                ProductGridModel.class);

        context.create().resource(
                "/content/productgrid",
                "title",
                "Featured Products");

        Resource resource = context.resourceResolver()
                .getResource(
                        "/content/productgrid");

        productGridModel = resource.adaptTo(
                ProductGridModel.class);
    }

    @Test
    void testGetTitle() {

        assertEquals(
                "Featured Products",
                productGridModel.getTitle());
    }
}