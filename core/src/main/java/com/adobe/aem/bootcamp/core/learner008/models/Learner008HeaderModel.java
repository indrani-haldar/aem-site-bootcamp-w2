package com.adobe.aem.bootcamp.core.learner008.models;

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
        return normalizePagePath(defaultIfBlank(homePath, "/content/aem-site-bootcamp-w2/us/en.html"));
    }

    public String getShopPath() {
        return normalizePagePath(defaultIfBlank(shopPath, "/content/aem-site-bootcamp-w2/us/en/shop.html"));
    }

    public String getContactPath() {
        return normalizePagePath(defaultIfBlank(contactPath, "/content/aem-site-bootcamp-w2/us/en/contact.html"));
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

    private String normalizePagePath(String path) {
        if (path.startsWith("/content/") && !path.endsWith(".html")) {
            return path + ".html";
        }
        return path;
    }
}
