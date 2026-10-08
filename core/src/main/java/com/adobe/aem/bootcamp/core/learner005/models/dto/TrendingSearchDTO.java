package com.adobe.aem.bootcamp.core.learner005.models.dto;

public class TrendingSearchDTO {

    private String label;
    private String query;

    public TrendingSearchDTO(String label, String query) {
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