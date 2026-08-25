package com.adobe.aem.bootcamp.core.learner005.models;

import com.adobe.aem.bootcamp.core.learner005.models.dto.TrendingSearchDTO;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TrendingSearchesModel {

    private List<TrendingSearchDTO> trendingSearches;

    @PostConstruct
    protected void init() {

        trendingSearches = new ArrayList<>();

        trendingSearches.add(
                new TrendingSearchDTO(
                        "Laptops",
                        "laptop"
                )
        );

        trendingSearches.add(
                new TrendingSearchDTO(
                        "Phones",
                        "phone"
                )
        );

        trendingSearches.add(
                new TrendingSearchDTO(
                        "Shirts",
                        "shirt"
                )
        );

        trendingSearches.add(
                new TrendingSearchDTO(
                        "Watches",
                        "watch"
                )
        );

        trendingSearches.add(
                new TrendingSearchDTO(
                        "Jackets",
                        "jacket"
                )
        );

    }

    public List<TrendingSearchDTO> getTrendingSearches() {
        return trendingSearches;
    }
}