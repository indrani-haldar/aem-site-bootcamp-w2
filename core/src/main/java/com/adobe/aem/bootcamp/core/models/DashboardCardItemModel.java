package com.adobe.aem.bootcamp.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentElement;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DashboardCardItemModel {
    @Self
    private Resource resource;

    @ValueMapValue
    private String fragmentPath;

    private String title;
    private String content;
    private boolean valid = false;

    @PostConstruct
    protected void init() {
        if (fragmentPath == null || fragmentPath.isBlank()) {
            return; // fail closed
        }

        ResourceResolver resolver = resource.getResourceResolver();

        // Re-validate server-side regardless of what the dialog stored
        if (!fragmentPath.startsWith("/content/dam/")) {
            return;
        }

        Resource fragmentRes = resolver.getResource(fragmentPath);
        if (fragmentRes == null) {
            return;
        }

        ContentFragment fragment = fragmentRes.adaptTo(ContentFragment.class);
        if (fragment == null) {
            return;
        }

        this.title = safeGetElement(fragment, "title");
        this.content = safeGetElement(fragment, "content");
        this.valid = (title != null);
    }

    private String safeGetElement(ContentFragment fragment, String elementName) {
        Iterator<ContentElement> elements = fragment.getElements();
        while (elements.hasNext()) {
            ContentElement el = elements.next();
            if (elementName.equals(el.getName())) {
                return el.getContent();
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isValid() {
        return valid;
    }
}
