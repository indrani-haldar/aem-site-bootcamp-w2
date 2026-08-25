package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/minicartdrawer",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008MiniCartDrawerModel {

    @ValueMapValue
    private String openButtonLabel;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String emptyLabel;

    @ValueMapValue
    private String checkoutLabel;

    public String getOpenButtonLabel() {
        return defaultIfBlank(openButtonLabel, "Open Cart");
    }

    public String getTitle() {
        return defaultIfBlank(title, "Your Cart");
    }

    public String getEmptyLabel() {
        return defaultIfBlank(emptyLabel, "Your cart is empty");
    }

    public String getCheckoutLabel() {
        return defaultIfBlank(checkoutLabel, "Checkout");
    }

    public String getCartsEndpoint() {
        return "/bin/shopfast/learner008/carts";
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
