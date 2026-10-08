package com.adobe.aem.bootcamp.core.learner006.models;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class HeaderModel {
    @ValueMapValue
    private String homeText;

    @ValueMapValue
    private String homeLink;

    public String getHomeText() {
        return homeText;
    }

    public String getHomeLink() {
        return homeLink;
    }
}
