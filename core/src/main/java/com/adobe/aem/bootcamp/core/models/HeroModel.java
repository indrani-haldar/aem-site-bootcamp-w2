package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroModel {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String subtext;

    @ValueMapValue
    private String backgroundImage;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubtext() {
        return subtext;
    }
}
