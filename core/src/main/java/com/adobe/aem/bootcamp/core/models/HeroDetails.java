package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroDetails {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String subtext;

    @ValueMapValue
    private String backgroundImage;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private String ctaLabel;

    public String getHeading() {
        return heading;
    }

    public String getSubtext() {
        return subtext;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public String getCtaLabel() {
        return ctaLabel;
    }
}