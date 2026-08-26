package com.adobe.aem.bootcamp.core.learner004.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class TrendingSearchesModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void testTrendingSearchPojo() {
        TrendingSearchesModel.TrendingSearch search =
                new TrendingSearchesModel.TrendingSearch("Shoes", "shoes");

        assertEquals("Shoes", search.getLabel());
        assertEquals("shoes", search.getQuery());
    }

    @Test
    void testNoFolderReturnsEmptyList() {
        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "trendingsearches");

        TrendingSearchesModel fixture = resource.adaptTo(TrendingSearchesModel.class);

        assertTrue(fixture.getTrendingSearches().isEmpty());
    }

    @Test
    void testFolderWithNonFragmentChildrenReturnsEmptyList() {
        context.create().resource("/content/dam/aem-site-bootcamp-w2");
        context.create().resource("/content/dam/aem-site-bootcamp-w2/not-a-fragment");

        Page page = context.create().page("/content/mypage");
        Resource resource = context.create().resource(page, "trendingsearches");

        TrendingSearchesModel fixture = resource.adaptTo(TrendingSearchesModel.class);

        assertTrue(fixture.getTrendingSearches().isEmpty());
    }
}
