package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(AemContextExtension.class)
class DashboardCardItemModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private DashboardCardItemModel adapt(String fragmentPath) {
        Page page = context.create().page("/content/mypage");
        Resource resource;
        if (fragmentPath == null) {
            resource = context.create().resource(page, "item");
        } else {
            resource = context.create().resource(page, "item", "fragmentPath", fragmentPath);
        }
        return resource.adaptTo(DashboardCardItemModel.class);
    }

    @Test
    void testNullFragmentPathIsInvalid() {
        DashboardCardItemModel fixture = adapt(null);

        assertFalse(fixture.isValid());
        assertNull(fixture.getTitle());
    }

    @Test
    void testBlankFragmentPathIsInvalid() {
        DashboardCardItemModel fixture = adapt("   ");

        assertFalse(fixture.isValid());
    }

    @Test
    void testFragmentPathOutsideDamIsInvalid() {
        DashboardCardItemModel fixture = adapt("/content/not-dam/fragment");

        assertFalse(fixture.isValid());
    }

    @Test
    void testMissingFragmentResourceIsInvalid() {
        DashboardCardItemModel fixture = adapt("/content/dam/does-not-exist");

        assertFalse(fixture.isValid());
    }

    @Test
    void testFragmentResourceNotAdaptableIsInvalid() {
        context.create().resource("/content/dam/aem-site-bootcamp-w2/fragment");

        DashboardCardItemModel fixture = adapt("/content/dam/aem-site-bootcamp-w2/fragment");

        assertFalse(fixture.isValid());
        assertNull(fixture.getTitle());
        assertNull(fixture.getContent());
    }
}
