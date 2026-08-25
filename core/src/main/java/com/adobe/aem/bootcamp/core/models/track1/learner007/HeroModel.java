package com.adobe.aem.bootcamp.core.models.track1.learner007;
 
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
 
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroModel {
 

    @ValueMapValue
    private String bannerImage;

    @ValueMapValue
    private String bannerTitle;

     @ValueMapValue
    private String bannerDescription;

     @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String bannerAlt;
   
 
    public String getBannerImage() {
      
        return bannerImage;
    }

    public String getBannerTitle() {
      
        return bannerTitle;
    }
    public String getBannerDescription() {
      
        return bannerDescription;
    }
    public String getButtonText() {
      
        return buttonText;
    }
     public String getBannerAlt() {
      
        return bannerAlt;
    }
    
}