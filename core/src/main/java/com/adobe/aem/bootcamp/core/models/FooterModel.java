package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class FooterModel {
    @ValueMapValue
    private String navigateText;

    @ValueMapValue
    private String navigateLink;

    @ValueMapValue
    private String navigateHeading;

    @ValueMapValue
    private String resourceText;

    @ValueMapValue
    private String resourceLink;

    @ValueMapValue
    private String resourceHeading;

    @ValueMapValue
    private String connectText;

    @ValueMapValue
    private String connectLink;

    @ValueMapValue
    private String connectHeading;

    public String getNavigateText() {
        return navigateText;
    }

    public String getNavigateLink() {
        return navigateLink;
    }

    public String getNavigateHeading() {
        return navigateHeading;
    }

    public String getResourceText() {
        return resourceText;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public String getResourceHeading() {
        return resourceHeading;
    }

    public String getConnectText() {
        return connectText;
    }

    public String getConnectLink() {
        return connectLink;
    }

    public String getConnectHeading() {
        return connectHeading;
    }

}
