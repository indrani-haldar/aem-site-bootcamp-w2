package com.adobe.aem.bootcamp.core.learner005.services.impl;
import com.adobe.aem.bootcamp.core.learner005.models.dto.TrendingSearchDTO;
import com.adobe.aem.bootcamp.core.learner005.services.TrendingSearchService;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component(service = TrendingSearchService.class)
public class TrendingSearchServiceImpl
        implements TrendingSearchService {

    private static final String CF_FOLDER_PATH =
            "/content/dam/aem-site-bootcamp-w2/content-fragments/trending-searches";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<TrendingSearchDTO> getTrendingSearches() {

        List<TrendingSearchDTO> trendingSearches =
                new ArrayList<>();

        try (ResourceResolver resolver =
                     resourceResolverFactory.getServiceResourceResolver(null)) {

            Resource folder =
                    resolver.getResource(CF_FOLDER_PATH);

            if (folder == null) {
                return trendingSearches;
            }

            for (Resource fragment : folder.getChildren()) {

                Resource masterNode =
                        fragment.getChild("jcr:content/data/master");

                if (masterNode == null) {
                    continue;
                }

                String label =
                        masterNode.getValueMap().get(
                                "label", "");

                String query =
                        masterNode.getValueMap().get(
                                "query", "");                

                    trendingSearches.add(
                            new TrendingSearchDTO(
                                    label,
                                    query
                            )
                    );
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trendingSearches;
    }
}