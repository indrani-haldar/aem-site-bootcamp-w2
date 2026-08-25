package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/hero",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008HeroModel {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String subtext;

    @ValueMapValue
    private String ctaLabel;

    @ValueMapValue
    private String ctaLink;

    public String getHeading() {
        return defaultIfBlank(heading, "Welcome to ShopFast");
    }

    public String getSubtext() {
        return defaultIfBlank(subtext, "Find products quickly and add them to your cart.");
    }

    public String getCtaLabel() {
        return defaultIfBlank(ctaLabel, "Start Shopping");
    }

    public String getCtaLink() {
        return defaultIfBlank(ctaLink, "#");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
