package com.adobe.aem.bootcamp.core.learner007.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeroModel {

    @ValueMapValue
    private String bannerTitle;

    @ValueMapValue
    private String bannerDescription;

    @ValueMapValue
    private String bannerImage;

    @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private String bannerAlt;

    public String getBannerTitle() {
        return bannerTitle;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getCtaLink() {
        return ctaLink;
    }
    public String getBannerAlt() {
        return bannerAlt;
    }
}