package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class DashboardCardModel {
    @ChildResource
    private Resource fragments;
    private final List<DashboardCardItemModel> items = new ArrayList<>();

    @PostConstruct
    protected void init() {

        if (fragments == null) {
            return;
        }

        for (Resource child : fragments.getChildren()) {
            DashboardCardItemModel item = child.adaptTo(DashboardCardItemModel.class);
            if (item != null && item.isValid()) {
                items.add(item);
            }
        }
    }

    public List<DashboardCardItemModel> getItems() {
        // Return a defensive copy so callers can't mutate internal state.

        return Collections.unmodifiableList(new ArrayList<>(items));

    }

}
