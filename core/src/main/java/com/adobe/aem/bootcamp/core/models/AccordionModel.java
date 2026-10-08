package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionModel {

    @ChildResource
    private Resource items;

    @Self
    private Resource resource;

    private final List<AccordionItemModel> accordionItems = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (items == null) {
            return;
        }

        for (Resource child : items.getChildren()) {
            AccordionItemModel accordionItem = child.adaptTo(AccordionItemModel.class);
            if (accordionItem != null && accordionItem.isValid()) {
                accordionItems.add(accordionItem);
            }
        }
    }

    public List<AccordionItemModel> getAccordionItems() {
        return accordionItems;
    }

    public String getIdPrefix() {
        if (resource == null) {
            return "accordion";
        }
        return "accordion-" + Math.abs(resource.getPath().hashCode());
    }
}
