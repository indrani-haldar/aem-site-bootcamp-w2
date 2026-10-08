package com.adobe.aem.bootcamp.core.learner005.services;
import com.adobe.aem.bootcamp.core.learner005.models.dto.TrendingSearchDTO;

import java.util.List;

public interface TrendingSearchService {

    List<TrendingSearchDTO> getTrendingSearches();
}