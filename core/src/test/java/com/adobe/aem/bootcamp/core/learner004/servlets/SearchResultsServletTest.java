package com.adobe.aem.bootcamp.core.learner004.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class SearchResultsServletTest {

    private static final String PRODUCTS_JSON = "["
            + "{\"id\":1,\"title\":\"Fjallraven Backpack\"},"
            + "{\"id\":2,\"title\":\"Mens Casual Jacket\"}"
            + "]";

    private final SearchResultsServlet fixture = new SearchResultsServlet() {
        @Override
        protected String fetchProductsJson() {
            return PRODUCTS_JSON;
        }
    };

    @Test
    void doGetWithoutQueryReturnsAllProducts(AemContext context) throws ServletException, IOException {
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertTrue(response.getOutputAsString().contains("Fjallraven Backpack"));
        assertTrue(response.getOutputAsString().contains("Mens Casual Jacket"));
    }

    @Test
    void doGetWithBlankQueryReturnsAllProducts(AemContext context) throws ServletException, IOException {
        MockSlingHttpServletRequest request = context.request();
        request.setParameterMap(java.util.Collections.singletonMap("q", "  "));
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertTrue(response.getOutputAsString().contains("Fjallraven Backpack"));
        assertTrue(response.getOutputAsString().contains("Mens Casual Jacket"));
    }

    @Test
    void doGetWithMatchingQueryReturnsFilteredProducts(AemContext context) throws ServletException, IOException {
        MockSlingHttpServletRequest request = context.request();
        request.setParameterMap(java.util.Collections.singletonMap("q", "jacket"));
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        String output = response.getOutputAsString();
        assertTrue(output.contains("Mens Casual Jacket"));
        assertEquals(false, output.contains("Fjallraven Backpack"));
    }

    @Test
    void doGetWithNonMatchingQueryReturnsNoProducts(AemContext context) throws ServletException, IOException {
        MockSlingHttpServletRequest request = context.request();
        request.setParameterMap(java.util.Collections.singletonMap("q", "nonexistent"));
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertEquals("[]", response.getOutputAsString());
    }
}
