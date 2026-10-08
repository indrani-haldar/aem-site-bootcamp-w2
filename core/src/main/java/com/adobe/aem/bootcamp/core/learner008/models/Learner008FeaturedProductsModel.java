package com.adobe.aem.bootcamp.core.learner008.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/featuredproducts",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008FeaturedProductsModel {

    @ValueMapValue
    private Integer featuredLimit;

    public String getProductsEndpoint() {
        return "/bin/shopfast/learner008/products";
    }

    public int getFeaturedLimit() {
        int fallback = 4;
        if (featuredLimit == null || featuredLimit.intValue() <= 0) {
            return fallback;
        }
        return featuredLimit.intValue();
    }
}
