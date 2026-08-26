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
class SearchFooterModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private SearchFooterModel fixture;

    @BeforeEach
    void setup() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "searchfooter",
                "brandTitle", "Search Hub",
                "navigationLabel", "Navigation",
                "homeLink", "/content/home",
                "resourcesLabel", "Resources",
                "helpLink", "/content/help",
                "connectLabel", "Connect",
                "linkedInLink", "https://linkedin.com/company/test");

        fixture = resource.adaptTo(SearchFooterModel.class);
    }

    @Test
    void testGetters() {
        assertEquals("Search Hub", fixture.getBrandTitle());
        assertEquals("Navigation", fixture.getNavigationLabel());
        assertEquals("/content/home", fixture.getHomeLink());
        assertEquals("Resources", fixture.getResourcesLabel());
        assertEquals("/content/help", fixture.getHelpLink());
        assertEquals("Connect", fixture.getConnectLabel());
        assertEquals("https://linkedin.com/company/test", fixture.getLinkedInLink());
    }
}
