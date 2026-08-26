package com.adobe.aem.bootcamp.core.learner004.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrendingSearchesModel {

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<TrendingSearch> trendingSearches;

    public static class TrendingSearch {

        private final String label;
        private final String query;

        public TrendingSearch(String label, String query) {
            this.label = label;
            this.query = query;
        }

        public String getLabel() {
            return label;
        }

        public String getQuery() {
            return query;
        }
    }

    @PostConstruct
    protected void init() {
        trendingSearches = new ArrayList<>();

        Resource folderResource = resourceResolver.getResource(
                "/content/dam/aem-site-bootcamp-w2");

        if (folderResource == null) {
            return;
        }

        for (Resource child : folderResource.getChildren()) {

            ContentFragment cf = child.adaptTo(ContentFragment.class);
            if (cf != null) {

                ContentElement labelElement = cf.getElement("label");

                ContentElement queryElement = cf.getElement("query");

                if (labelElement != null
                        && queryElement != null) {

                    trendingSearches.add(
                            new TrendingSearch(
                                    labelElement.getContent(),
                                    queryElement.getContent()));
                }
            }
        }
    }

    public List<TrendingSearch> getTrendingSearches() {
        return Collections.unmodifiableList(new ArrayList<>(trendingSearches));
    }

}
