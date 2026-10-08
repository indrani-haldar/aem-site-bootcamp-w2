package com.adobe.aem.bootcamp.core.learner008.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/searchbar",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008SearchBarModel {

    @ValueMapValue
    private String searchLabel;

    @ValueMapValue
    private String searchPlaceholder;

    public String getSearchLabel() {
        return defaultIfBlank(searchLabel, "Search products");
    }

    public String getSearchPlaceholder() {
        return defaultIfBlank(searchPlaceholder, "Search by product name");
    }

    public String getInputId() {
        return "learner008-product-search";
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
