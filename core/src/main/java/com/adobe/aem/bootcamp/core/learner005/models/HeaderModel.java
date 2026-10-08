package com.adobe.aem.bootcamp.core.learner005.models;
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
    private String title;

    @ValueMapValue
    private String navPath;

    public String getTitle() {
        return title;
    }   

    public String getNavPath() {
        return navPath;
    }

}