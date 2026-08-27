package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class DashboardCardModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void testNoFragmentsChildReturnsEmptyItems() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "dashboardcard");

        DashboardCardModel fixture = resource.adaptTo(DashboardCardModel.class);

        assertTrue(fixture.getItems().isEmpty());
    }

    @Test
    void testInvalidFragmentChildrenAreExcluded() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "dashboardcard");
        Resource fragments = context.create().resource(resource, "fragments");
        context.create().resource(fragments, "item1", "fragmentPath", "/content/dam/does-not-exist");

        DashboardCardModel fixture = resource.adaptTo(DashboardCardModel.class);

        assertTrue(fixture.getItems().isEmpty());
    }
}
