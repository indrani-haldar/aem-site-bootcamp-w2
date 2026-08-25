package com.adobe.aem.bootcamp.core.models.track1.learner003;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductGridModel {

    @Inject
    private String title;

    public String getTitle() {
        if (title == null || title.isEmpty()) {
            return "Featured Products";
        }
        return title;
    }
}