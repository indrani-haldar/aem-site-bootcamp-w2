package com.adobe.aem.bootcamp.core.models;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetails {
   
    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String description;

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }
}
