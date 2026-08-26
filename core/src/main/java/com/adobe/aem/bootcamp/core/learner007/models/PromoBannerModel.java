package com.adobe.aem.bootcamp.core.learner007.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PromoBannerModel {

    @ValueMapValue
    private String promoblockImage;

    @ValueMapValue
    private String promoAltText;

    @ValueMapValue
    private String promoHeading;

    @ValueMapValue
    private String benefit1;

    @ValueMapValue
    private String benefit2;

    @ValueMapValue
    private String benefit3;
    @ValueMapValue
    private String benefit4;

    public String getPromoblockImage() {return promoblockImage;}   
    public String getPromoHeading() {return promoHeading;}
    public String getBenefit1() {return benefit1;}
    public String getBenefit2() {return benefit2;}
    public String getBenefit3() {return benefit3;}
     public String getBenefit4() {return benefit4;}
}
