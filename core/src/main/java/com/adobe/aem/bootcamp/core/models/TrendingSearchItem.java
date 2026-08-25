package com.adobe.aem.bootcamp.core.models;

public class TrendingSearchItem {

    private final String label;
    private final String query;

    public TrendingSearchItem(
            String label,
            String query) {

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