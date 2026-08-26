package com.adobe.aem.bootcamp.core.learner007.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterModel {

    @ValueMapValue
    private String companyLogo;

    @ValueMapValue
    private String home;

    @ValueMapValue
    private String shop;

    @ValueMapValue
    private String resource;

    @ValueMapValue
    private String linkedin;
      @ValueMapValue
    private String copyright;


     @ValueMapValue
    private String homeCtaLink;

     @ValueMapValue
    private String shopCtaLink;

     @ValueMapValue
    private String resourceCtaLink;

    @ValueMapValue
    private String linkedinCTALink;
    
   

    public String getCompanyLogo() {
        return companyLogo;
    }
     public String getHome() {
        return home;
    }
    

    public String getShop() {
        return shop;
    }

    public String getResource() {
        return resource;
    }

    public String getLinkedin() {
        return linkedin;
    }
    public String getCopyright(){
        return copyright;
    }
     public String getHomeCtaLink(){return homeCtaLink;}
     public String getShopCtaLink(){return shopCtaLink;}
     public String getResourceCtaLink(){return resourceCtaLink;}
     public String getLinkedinCTALink(){return linkedinCTALink;}
}
