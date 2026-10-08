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
class FooterModelTest {

    private final AemContext context = new AemContext();

    private FooterModel footerModel;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(FooterModel.class);

        Resource resource = context.create().resource(
                "/content/footer",
                "brandTitle", "Adobe",
                "navigationLabel", "Navigation",
                "homeLink", "/content/home",
                "resourcesLabel", "Resources",
                "helpLink", "/content/help",
                "connectLabel", "Connect With Us",
                "linkedInLink", "https://www.linkedin.com/company/adobe"
        );

        footerModel = resource.adaptTo(FooterModel.class);
    }

    @Test
    void testGetBrandTitle() {
        assertEquals("Adobe", footerModel.getBrandTitle());
    }

    @Test
    void testGetNavigationLabel() {
        assertEquals("Navigation", footerModel.getNavigationLabel());
    }

    @Test
    void testGetHomeLink() {
        assertEquals("/content/home", footerModel.getHomeLink());
    }

    @Test
    void testGetResourcesLabel() {
        assertEquals("Resources", footerModel.getResourcesLabel());
    }

    @Test
    void testGetHelpLink() {
        assertEquals("/content/help", footerModel.getHelpLink());
    }

    @Test
    void testGetConnectLabel() {
        assertEquals("Connect With Us", footerModel.getConnectLabel());
    }

    @Test
    void testGetLinkedInLink() {
        assertEquals(
                "https://www.linkedin.com/company/adobe",
                footerModel.getLinkedInLink()
        );
    }

    @Test
    void testAllProperties() {
        assertEquals("Adobe", footerModel.getBrandTitle());
        assertEquals("Navigation", footerModel.getNavigationLabel());
        assertEquals("/content/home", footerModel.getHomeLink());
        assertEquals("Resources", footerModel.getResourcesLabel());
        assertEquals("/content/help", footerModel.getHelpLink());
        assertEquals("Connect With Us", footerModel.getConnectLabel());
        assertEquals(
                "https://www.linkedin.com/company/adobe",
                footerModel.getLinkedInLink()
        );
    }

    @Test
    void testOptionalProperties() {
        Resource resource = context.create().resource("/content/empty-footer");

        FooterModel emptyModel = resource.adaptTo(FooterModel.class);

        assertNull(emptyModel.getBrandTitle());
        assertNull(emptyModel.getNavigationLabel());
        assertNull(emptyModel.getHomeLink());
        assertNull(emptyModel.getResourcesLabel());
        assertNull(emptyModel.getHelpLink());
        assertNull(emptyModel.getConnectLabel());
        assertNull(emptyModel.getLinkedInLink());
    }
}