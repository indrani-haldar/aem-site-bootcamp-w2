package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "aem-site-bootcamp-w2/components/learner008/footer",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Learner008FooterModel {

    @ValueMapValue
    private String brandTitle;

    @ValueMapValue
    private String tagline;

    @ValueMapValue
    private String homePath;

    @ValueMapValue
    private String shopPath;

    @ValueMapValue
    private String contactPath;

    @ValueMapValue
    private String supportEmail;

    @ValueMapValue
    private String supportPhone;

    @ValueMapValue
    private String supportHours;

    @ValueMapValue
    private String copyright;

    public String getBrandTitle() {
        return defaultIfBlank(brandTitle, "ShopFast");
    }

    public String getTagline() {
        return defaultIfBlank(tagline, "Quality essentials delivered quickly, with transparent pricing and easy returns.");
    }

    public String getHomePath() {
        return defaultIfBlank(homePath, "/content/aem-site-bootcamp-w2/us/en.html");
    }

    public String getShopPath() {
        return defaultIfBlank(shopPath, "/content/aem-site-bootcamp-w2/us/en/shop.html");
    }

    public String getContactPath() {
        return defaultIfBlank(contactPath, "/content/aem-site-bootcamp-w2/us/en/contact.html");
    }

    public String getSupportEmail() {
        return defaultIfBlank(supportEmail, "support@shopfast.example");
    }

    public String getSupportPhone() {
        return defaultIfBlank(supportPhone, "+1 (800) 555-0148");
    }

    public String getSupportHours() {
        return defaultIfBlank(supportHours, "Mon-Fri, 9:00 AM to 6:00 PM");
    }

    public String getCopyright() {
        return defaultIfBlank(copyright, "2026 ShopFast. Built for practical everyday shopping.");
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
