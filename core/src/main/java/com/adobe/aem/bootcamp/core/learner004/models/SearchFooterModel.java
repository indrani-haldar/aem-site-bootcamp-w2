package com.adobe.aem.bootcamp.core.learner004.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SearchFooterModel {

    @ValueMapValue
    private String brandTitle;

    @ValueMapValue
    private String navigationLabel;

    @ValueMapValue
    private String homeLink;

    @ValueMapValue
    private String resourcesLabel;

    @ValueMapValue
    private String helpLink;

    @ValueMapValue
    private String connectLabel;

    @ValueMapValue
    private String linkedInLink;

    

    public String getBrandTitle() {
        return brandTitle;
    }

    public String getNavigationLabel() {
        return navigationLabel;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public String getResourcesLabel() {
        return resourcesLabel;
    }

    public String getHelpLink() {
        return helpLink;
    }

    public String getConnectLabel() {
        return connectLabel;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }
}