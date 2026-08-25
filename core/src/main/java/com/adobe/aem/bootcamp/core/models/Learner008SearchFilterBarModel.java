package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/searchfilterbar",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008SearchFilterBarModel {

    @ValueMapValue
    private String searchLabel;

    @ValueMapValue
    private String searchPlaceholder;

    @ValueMapValue
    private String allCategoryLabel;

    @ValueMapValue
    private String displayMode;

    @ValueMapValue
    private Integer featuredLimit;

    public String getSearchLabel() {
        return defaultIfBlank(searchLabel, "Search products");
    }

    public String getSearchPlaceholder() {
        return defaultIfBlank(searchPlaceholder, "Type product name");
    }

    public String getAllCategoryLabel() {
        return defaultIfBlank(allCategoryLabel, "All");
    }

    public String getDisplayMode() {
        String value = defaultIfBlank(displayMode, "featured").toLowerCase();
        return "featured".equals(value) ? "featured" : "catalog";
    }

    public int getFeaturedLimit() {
        int fallback = 4;
        if (featuredLimit == null || featuredLimit.intValue() <= 0) {
            return fallback;
        }
        return featuredLimit.intValue();
    }

    public boolean isFeaturedMode() {
        return "featured".equals(getDisplayMode());
    }

    public String getProductsEndpoint() {
        return "/bin/shopfast/learner008/products";
    }

    public String getInputId() {
        return "learner008-shopfast-search";
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
