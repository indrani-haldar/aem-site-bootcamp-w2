package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/contactform",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008ContactFormModel {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String nameLabel;

    @ValueMapValue
    private String emailLabel;

    @ValueMapValue
    private String messageLabel;

    @ValueMapValue
    private String buttonLabel;

    @ValueMapValue
    private String successMessage;

    public String getHeading() {
        return defaultIfBlank(heading, "Contact ShopFast");
    }

    public String getDescription() {
        return defaultIfBlank(description, "Have a question about products, orders, or shipping? Send us a message.");
    }

    public String getNameLabel() {
        return defaultIfBlank(nameLabel, "Full Name");
    }

    public String getEmailLabel() {
        return defaultIfBlank(emailLabel, "Email Address");
    }

    public String getMessageLabel() {
        return defaultIfBlank(messageLabel, "Message");
    }

    public String getButtonLabel() {
        return defaultIfBlank(buttonLabel, "Send Message");
    }

    public String getSuccessMessage() {
        return defaultIfBlank(successMessage, "Thanks! Your message has been sent.");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
