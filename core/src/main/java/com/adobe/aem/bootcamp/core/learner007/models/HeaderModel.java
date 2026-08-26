package com.adobe.aem.bootcamp.core.learner007.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModel {

    @ValueMapValue
    private String companyLogo;

    @ValueMapValue
    private String shopfastLogoAlt;

    @ValueMapValue
    private String homeCtaLink;

    @ValueMapValue
    private String shopCtaLink;

    @ValueMapValue
    private String contactCtaLink;

    public String getCompanyLogo() {
        return companyLogo;
    }

    public String getShopfastLogoAlt() {
        return shopfastLogoAlt;
    }

    public String getHomeCtaLink() {
        return homeCtaLink;
    }

    public String getShopCtaLink() {
        return shopCtaLink;
    }

    public String getContactCtaLink() {
        return contactCtaLink;
    }
}
