package com.adobe.aem.bootcamp.core.learner004.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CardTeaserModel {

    @ValueMapValue
    private String imagePath;

    @ValueMapValue
    private String imageAlt;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String price;

    @ValueMapValue
    private String ctaLabel;

    public String getImagePath() {
        return imagePath;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCtaLabel() {
        return ctaLabel;
    }



   
}