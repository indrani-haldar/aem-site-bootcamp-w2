package com.adobe.aem.bootcamp.core.learner008.models;

import com.adobe.aem.bootcamp.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
class Learner008ModelsTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(
                Learner008ContactFormModel.class,
                Learner008FeaturedProductsModel.class,
                Learner008FooterModel.class,
                Learner008HeaderModel.class,
                Learner008HeroModel.class,
                Learner008MiniCartDrawerModel.class,
                Learner008ProductListModel.class,
                Learner008SearchBarModel.class);
    }

    @Test
    void headerUsesDefaultValuesAndNormalizesAuthoredPaths() {
        Learner008HeaderModel defaultModel = modelFor("headerDefaults",
                "aem-site-bootcamp-w2/components/learner008/header", Learner008HeaderModel.class);

        assertEquals("ShopFast", defaultModel.getSiteTitle());
        assertEquals("/content/aem-site-bootcamp-w2/us/en.html", defaultModel.getHomePath());
        assertEquals("/content/aem-site-bootcamp-w2/us/en/shop.html", defaultModel.getShopPath());
        assertEquals("/content/aem-site-bootcamp-w2/us/en/contact.html", defaultModel.getContactPath());
        assertEquals("Cart", defaultModel.getCartButtonLabel());

        Learner008HeaderModel authoredModel = modelFor("headerAuthored",
                "aem-site-bootcamp-w2/components/learner008/header", Learner008HeaderModel.class,
                "siteTitle", "Demo Store",
                "homePath", "/content/site/home",
                "shopPath", "/content/site/shop.html",
                "contactPath", "https://example.com/contact",
                "cartButtonLabel", "Basket");

        assertEquals("Demo Store", authoredModel.getSiteTitle());
        assertEquals("/content/site/home.html", authoredModel.getHomePath());
        assertEquals("/content/site/shop.html", authoredModel.getShopPath());
        assertEquals("https://example.com/contact", authoredModel.getContactPath());
        assertEquals("Basket", authoredModel.getCartButtonLabel());
    }

    @Test
    void heroUsesDefaultValuesAndNormalizesAuthoredCtaPath() {
        Learner008HeroModel defaultModel = modelFor("heroDefaults",
                "aem-site-bootcamp-w2/components/learner008/hero", Learner008HeroModel.class);

        assertEquals("Welcome to ShopFast", defaultModel.getHeading());
        assertEquals("Find products quickly and add them to your cart.", defaultModel.getSubtext());
        assertEquals("Start Shopping", defaultModel.getCtaLabel());
        assertEquals("/content/aem-site-bootcamp-w2/us/en/shop.html", defaultModel.getCtaLink());

        Learner008HeroModel authoredModel = modelFor("heroAuthored",
                "aem-site-bootcamp-w2/components/learner008/hero", Learner008HeroModel.class,
                "heading", "Fast Deals",
                "subtext", "Fresh picks every day",
                "ctaLabel", "Shop Now",
                "ctaLink", "/content/site/shop");

        assertEquals("Fast Deals", authoredModel.getHeading());
        assertEquals("Fresh picks every day", authoredModel.getSubtext());
        assertEquals("Shop Now", authoredModel.getCtaLabel());
        assertEquals("/content/site/shop.html", authoredModel.getCtaLink());
    }

    @Test
    void footerUsesDefaultValuesAndNormalizesAuthoredPaths() {
        Learner008FooterModel defaultModel = modelFor("footerDefaults",
                "aem-site-bootcamp-w2/components/learner008/footer", Learner008FooterModel.class);

        assertEquals("ShopFast", defaultModel.getBrandTitle());
        assertEquals("Quality essentials delivered quickly, with transparent pricing and easy returns.", defaultModel.getTagline());
        assertEquals("/content/aem-site-bootcamp-w2/us/en.html", defaultModel.getHomePath());
        assertEquals("/content/aem-site-bootcamp-w2/us/en/shop.html", defaultModel.getShopPath());
        assertEquals("/content/aem-site-bootcamp-w2/us/en/contact.html", defaultModel.getContactPath());
        assertEquals("support@shopfast.example", defaultModel.getSupportEmail());
        assertEquals("+1 (800) 555-0148", defaultModel.getSupportPhone());
        assertEquals("Mon-Fri, 9:00 AM to 6:00 PM", defaultModel.getSupportHours());
        assertEquals("2026 ShopFast. Built for practical everyday shopping.", defaultModel.getCopyright());

        Learner008FooterModel authoredModel = modelFor("footerAuthored",
                "aem-site-bootcamp-w2/components/learner008/footer", Learner008FooterModel.class,
                "brandTitle", "ShopFast Outlet",
                "tagline", "Useful products shipped fast",
                "homePath", "/content/site/home",
                "shopPath", "/content/site/shop",
                "contactPath", "/content/site/contact.html",
                "supportEmail", "help@example.com",
                "supportPhone", "555-0100",
                "supportHours", "Always open",
                "copyright", "2026 Demo");

        assertEquals("ShopFast Outlet", authoredModel.getBrandTitle());
        assertEquals("Useful products shipped fast", authoredModel.getTagline());
        assertEquals("/content/site/home.html", authoredModel.getHomePath());
        assertEquals("/content/site/shop.html", authoredModel.getShopPath());
        assertEquals("/content/site/contact.html", authoredModel.getContactPath());
        assertEquals("help@example.com", authoredModel.getSupportEmail());
        assertEquals("555-0100", authoredModel.getSupportPhone());
        assertEquals("Always open", authoredModel.getSupportHours());
        assertEquals("2026 Demo", authoredModel.getCopyright());
    }

    @Test
    void featuredProductsUsesDefaultLimitAndAuthoredLimit() {
        Learner008FeaturedProductsModel defaultModel = modelFor("featuredDefaults",
                "aem-site-bootcamp-w2/components/learner008/featuredproducts", Learner008FeaturedProductsModel.class);
        Learner008FeaturedProductsModel invalidModel = modelFor("featuredInvalid",
                "aem-site-bootcamp-w2/components/learner008/featuredproducts", Learner008FeaturedProductsModel.class,
                "featuredLimit", 0);
        Learner008FeaturedProductsModel authoredModel = modelFor("featuredAuthored",
                "aem-site-bootcamp-w2/components/learner008/featuredproducts", Learner008FeaturedProductsModel.class,
                "featuredLimit", 6);

        assertEquals("/bin/shopfast/learner008/products", defaultModel.getProductsEndpoint());
        assertEquals(4, defaultModel.getFeaturedLimit());
        assertEquals(4, invalidModel.getFeaturedLimit());
        assertEquals(6, authoredModel.getFeaturedLimit());
    }

    @Test
    void searchBarUsesDefaultsAndAuthoredValues() {
        Learner008SearchBarModel defaultModel = modelFor("searchDefaults",
                "aem-site-bootcamp-w2/components/learner008/searchbar", Learner008SearchBarModel.class);
        Learner008SearchBarModel authoredModel = modelFor("searchAuthored",
                "aem-site-bootcamp-w2/components/learner008/searchbar", Learner008SearchBarModel.class,
                "searchLabel", "Find products",
                "searchPlaceholder", "Type a product title");

        assertEquals("Search products", defaultModel.getSearchLabel());
        assertEquals("Search by product name", defaultModel.getSearchPlaceholder());
        assertEquals("learner008-product-search", defaultModel.getInputId());
        assertEquals("Find products", authoredModel.getSearchLabel());
        assertEquals("Type a product title", authoredModel.getSearchPlaceholder());
    }

    @Test
    void productListUsesDefaultsAndAuthoredValues() {
        Learner008ProductListModel defaultModel = modelFor("productListDefaults",
                "aem-site-bootcamp-w2/components/learner008/productlist", Learner008ProductListModel.class);
        Learner008ProductListModel authoredModel = modelFor("productListAuthored",
                "aem-site-bootcamp-w2/components/learner008/productlist", Learner008ProductListModel.class,
                "loadingLabel", "Please wait",
                "emptyLabel", "Nothing matched");

        assertEquals("/bin/shopfast/learner008/products", defaultModel.getProductsEndpoint());
        assertEquals("Loading products...", defaultModel.getLoadingLabel());
        assertEquals("No products found.", defaultModel.getEmptyLabel());
        assertEquals("Please wait", authoredModel.getLoadingLabel());
        assertEquals("Nothing matched", authoredModel.getEmptyLabel());
    }

    @Test
    void miniCartDrawerUsesDefaultsAndAuthoredValues() {
        Learner008MiniCartDrawerModel defaultModel = modelFor("cartDefaults",
                "aem-site-bootcamp-w2/components/learner008/minicartdrawer", Learner008MiniCartDrawerModel.class);
        Learner008MiniCartDrawerModel authoredModel = modelFor("cartAuthored",
                "aem-site-bootcamp-w2/components/learner008/minicartdrawer", Learner008MiniCartDrawerModel.class,
                "openButtonLabel", "Open Basket",
                "title", "Basket",
                "emptyLabel", "Basket is empty",
                "checkoutLabel", "Pay now");

        assertEquals("Open Cart", defaultModel.getOpenButtonLabel());
        assertEquals("Your Cart", defaultModel.getTitle());
        assertEquals("Your cart is empty", defaultModel.getEmptyLabel());
        assertEquals("Checkout", defaultModel.getCheckoutLabel());
        assertEquals("/bin/shopfast/learner008/carts", defaultModel.getCartsEndpoint());
        assertEquals("Open Basket", authoredModel.getOpenButtonLabel());
        assertEquals("Basket", authoredModel.getTitle());
        assertEquals("Basket is empty", authoredModel.getEmptyLabel());
        assertEquals("Pay now", authoredModel.getCheckoutLabel());
    }

    @Test
    void contactFormUsesDefaultsAndAuthoredValues() {
        Learner008ContactFormModel defaultModel = modelFor("contactDefaults",
                "aem-site-bootcamp-w2/components/learner008/contactform", Learner008ContactFormModel.class);
        Learner008ContactFormModel authoredModel = modelFor("contactAuthored",
                "aem-site-bootcamp-w2/components/learner008/contactform", Learner008ContactFormModel.class,
                "heading", "Talk to us",
                "description", "We are ready to help",
                "nameLabel", "Name",
                "emailLabel", "Email",
                "messageLabel", "Question",
                "buttonLabel", "Send",
                "successMessage", "Sent");

        assertEquals("Contact ShopFast", defaultModel.getHeading());
        assertEquals("Have a question about products, orders, or shipping? Send us a message.", defaultModel.getDescription());
        assertEquals("Full Name", defaultModel.getNameLabel());
        assertEquals("Email Address", defaultModel.getEmailLabel());
        assertEquals("Message", defaultModel.getMessageLabel());
        assertEquals("Send Message", defaultModel.getButtonLabel());
        assertEquals("Thanks! Your message has been sent.", defaultModel.getSuccessMessage());
        assertEquals("Talk to us", authoredModel.getHeading());
        assertEquals("We are ready to help", authoredModel.getDescription());
        assertEquals("Name", authoredModel.getNameLabel());
        assertEquals("Email", authoredModel.getEmailLabel());
        assertEquals("Question", authoredModel.getMessageLabel());
        assertEquals("Send", authoredModel.getButtonLabel());
        assertEquals("Sent", authoredModel.getSuccessMessage());
    }

    private <T> T modelFor(String resourceName, String resourceType, Class<T> modelClass, Object... properties) {
        Object[] resourceProperties = new Object[properties.length + 2];
        resourceProperties[0] = "sling:resourceType";
        resourceProperties[1] = resourceType;
        System.arraycopy(properties, 0, resourceProperties, 2, properties.length);

        Resource resource = context.create().resource("/content/learner008/" + resourceName, resourceProperties);
        T model = resource.adaptTo(modelClass);
        assertNotNull(model);
        return model;
    }
}
