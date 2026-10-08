package com.adobe.aem.bootcamp.core.learner005.models;

import com.adobe.aem.bootcamp.core.learner005.models.dto.TrendingSearchDTO;
import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
class TrendingSearchesModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private TrendingSearchesModel trendingSearchesModel;

    @BeforeEach
    void setUp() {

        context.addModelsForClasses(TrendingSearchesModel.class);

        Resource resource = context.create().resource("/content/trending-searches");

        trendingSearchesModel = resource.adaptTo(TrendingSearchesModel.class);
    }

    @Test
    void testModelAdaptation() {
        assertNotNull(trendingSearchesModel);
    }

    @Test
    void testGetTrendingSearches() {

        List<TrendingSearchDTO> searches =
                trendingSearchesModel.getTrendingSearches();

        assertNotNull(searches);
        assertEquals(5, searches.size());

        assertEquals("Laptops", searches.get(0).getLabel());
        assertEquals("laptop", searches.get(0).getQuery());

        assertEquals("Phones", searches.get(1).getLabel());
        assertEquals("phone", searches.get(1).getQuery());

        assertEquals("Shirts", searches.get(2).getLabel());
        assertEquals("shirt", searches.get(2).getQuery());

        assertEquals("Watches", searches.get(3).getLabel());
        assertEquals("watch", searches.get(3).getQuery());

        assertEquals("Jackets", searches.get(4).getLabel());
        assertEquals("jacket", searches.get(4).getQuery());
    }
}