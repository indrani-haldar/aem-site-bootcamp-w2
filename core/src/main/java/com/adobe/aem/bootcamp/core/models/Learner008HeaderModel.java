package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/header",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008HeaderModel {

    @ValueMapValue
    private String siteTitle;

    @ValueMapValue
    private String homePath;

    @ValueMapValue
    private String shopPath;

    @ValueMapValue
    private String contactPath;

    @ValueMapValue
    private String cartButtonLabel;

    public String getSiteTitle() {
        return defaultIfBlank(siteTitle, "ShopFast");
    }

    public String getHomePath() {
        return defaultIfBlank(homePath, "#");
    }

    public String getShopPath() {
        return defaultIfBlank(shopPath, "#");
    }

    public String getContactPath() {
        return defaultIfBlank(contactPath, "#");
    }

    public String getCartButtonLabel() {
        return defaultIfBlank(cartButtonLabel, "Cart");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
