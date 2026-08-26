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
class SearchHeaderModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private SearchHeaderModel fixture;

    @BeforeEach
    void setup() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "searchheader",
                "heading", "Search Everything",
                "ctaLabel", "Get Started",
                "ctaLink", "/content/start");

        fixture = resource.adaptTo(SearchHeaderModel.class);
    }

    @Test
    void testGetters() {
        assertEquals("Search Everything", fixture.getHeading());
        assertEquals("Get Started", fixture.getCtaLabel());
        assertEquals("/content/start", fixture.getCtaLink());
    }
}
