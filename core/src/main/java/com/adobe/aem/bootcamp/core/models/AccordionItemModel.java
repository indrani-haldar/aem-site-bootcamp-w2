package com.adobe.aem.bootcamp.core.models;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionItemModel {

    @Self
    private Resource resource;

    @ValueMapValue
    private String question;

    @ValueMapValue
    private String answer;

    @ValueMapValue
    private String policyFragmentPath;

    private String resolvedAnswer;

    @PostConstruct
    protected void init() {
        resolvedAnswer = answer;

        if (policyFragmentPath == null || policyFragmentPath.isBlank()) {
            return;
        }

        Resource fragmentResource = resource.getResourceResolver().getResource(policyFragmentPath);
        if (fragmentResource == null) {
            return;
        }

        ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
        if (fragment == null) {
            return;
        }

        String fragmentBody = getElement(fragment, "body");
        if (fragmentBody != null && !fragmentBody.isBlank()) {
            resolvedAnswer = fragmentBody;
        }
    }

    private String getElement(ContentFragment fragment, String elementName) {
        Iterator<ContentElement> elements = fragment.getElements();
        while (elements.hasNext()) {
            ContentElement element = elements.next();
            if (elementName.equals(element.getName())) {
                return element.getContent();
            }
        }
        return null;
    }

    public String getQuestion() {
        return question;
    }

    public String getResolvedAnswer() {
        return resolvedAnswer;
    }

    public boolean isValid() {
        return question != null && !question.isBlank() && resolvedAnswer != null && !resolvedAnswer.isBlank();
    }
}
