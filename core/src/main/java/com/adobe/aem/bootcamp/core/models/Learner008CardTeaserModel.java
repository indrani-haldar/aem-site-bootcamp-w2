package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Locale;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/cardteaser",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008CardTeaserModel {

    @ValueMapValue
    private String productId;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private Double price;

    @ValueMapValue
    private String imagePath;

    @ValueMapValue
    private String addToCartLabel;

    public String getProductId() {
        return defaultIfBlank(productId, "");
    }

    public String getTitle() {
        return defaultIfBlank(title, "Product");
    }

    public String getDescription() {
        return defaultIfBlank(description, "Product description");
    }

    public double getPrice() {
        return price == null ? 0.0D : price;
    }

    public String getFormattedPrice() {
        return String.format(Locale.US, "$%.2f", getPrice());
    }

    public String getImagePath() {
        return defaultIfBlank(imagePath, "");
    }

    public String getAddToCartLabel() {
        return defaultIfBlank(addToCartLabel, "Add to cart");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
