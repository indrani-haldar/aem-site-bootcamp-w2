package com.adobe.aem.bootcamp.core.models.track1.learner007;
 
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
 
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroModel {
 

    @ValueMapValue
    private String promoblockImage;

    @ValueMapValue
    private String promoHeading;

     @ValueMapValue
    private String benefit1;

     @ValueMapValue
    private String benefit2;

      @ValueMapValue
    private String benefit3;

     @ValueMapValue
    private String benefit14;
   
 
    public String getPromoblockImage() {
      
        return promoblockImage;
    }

    public String getPromoHeading() {
      
        return bannerTitle;
    }
    public String getBenefit1() {
      
        return benefit1;
    }
    public String getBenefit2() {
      
        return benefit2;
    }

    public String getBenefit3() {
      
        return benefit3;
    }
    public String getBenefit4() {
      
        return benefit4;
    }
    
}