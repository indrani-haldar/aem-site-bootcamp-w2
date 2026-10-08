package com.adobe.aem.bootcamp.core.learner008.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/productlist",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008ProductListModel {

    @ValueMapValue
    private String loadingLabel;

    @ValueMapValue
    private String emptyLabel;

    public String getProductsEndpoint() {
        return "/bin/shopfast/learner008/products";
    }

    public String getLoadingLabel() {
        return defaultIfBlank(loadingLabel, "Loading products...");
    }

    public String getEmptyLabel() {
        return defaultIfBlank(emptyLabel, "No products found.");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
