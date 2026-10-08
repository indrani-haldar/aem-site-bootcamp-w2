package com.adobe.aem.bootcamp.core.learner006.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrendingSearchesModel {

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<TrendingSearchItem> searches;

    @PostConstruct
    protected void init() {

        searches = new ArrayList<>();

        Resource folder = resourceResolver.getResource(
                "/content/dam/aem-site-bootcamp-w2");

        if (folder == null) {
            return;
        }

        for (Resource child : folder.getChildren()) {

            Resource data = child.getChild("jcr:content/data/master");

            if (data == null) {
                continue;
            }

            ValueMap vm = data.getValueMap();

            String label = vm.get("label", String.class);

            String query = vm.get("query", String.class);

            if (label != null && query != null) {

                searches.add(
                        new TrendingSearchItem(
                                label,
                                query));
            }
        }
    }

    public List<TrendingSearchItem> getSearches() {
        return searches;
    }
}