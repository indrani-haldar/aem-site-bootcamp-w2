package com.adobe.aem.bootcamp.core.servlets;

import com.adobe.aem.bootcamp.core.learner008.models.FakeStoreApiClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
class ShopFastLearner008ProductsServletTest {

    private final ShopFastLearner008ProductsServlet servlet = new ShopFastLearner008ProductsServlet();
    private final FakeStoreApiClient fakeStoreApiClient = mock(FakeStoreApiClient.class);

    @BeforeEach
    void setUp() throws Exception {
        setField(servlet, "fakeStoreApiClient", fakeStoreApiClient);
    }

    @Test
    void doGetReturnsAllProductsAndCategories(AemContext context) throws Exception {
        when(fakeStoreApiClient.fetchProducts()).thenReturn(JsonParser.parseString("["
                + product(1, "Trail Backpack", "men's clothing", 109.95)
                + ","
                + product(2, "Silver Ring", "jewelery", 22.30)
                + "]").getAsJsonArray());

        MockSlingHttpServletResponse response = execute(context);
        JsonObject payload = JsonParser.parseString(response.getOutputAsString()).getAsJsonObject();

        assertTrue(response.getContentType().startsWith("application/json"));
        assertEquals(2, payload.get("total").getAsInt());
        assertEquals(2, payload.getAsJsonArray("items").size());
        assertEquals("men's clothing", payload.getAsJsonArray("categories").get(0).getAsString());
        assertEquals("jewelery", payload.getAsJsonArray("categories").get(1).getAsString());
        assertEquals("", payload.get("query").getAsString());
        assertEquals("all", payload.get("category").getAsString());
    }

    @Test
    void doGetFiltersProductsByQueryAndCategory(AemContext context) throws Exception {
        when(fakeStoreApiClient.fetchProducts()).thenReturn(JsonParser.parseString("["
                + product(1, "Trail Backpack", "men's clothing", 109.95)
                + ","
                + product(2, "Cotton Jacket", "men's clothing", 55.99)
                + ","
                + product(3, "Cotton Shirt", "women's clothing", 9.85)
                + "]").getAsJsonArray());

        MockSlingHttpServletResponse response = execute(context, "q", "cotton", "category", "men's clothing");
        JsonObject payload = JsonParser.parseString(response.getOutputAsString()).getAsJsonObject();

        assertEquals(1, payload.get("total").getAsInt());
        assertEquals("Cotton Jacket", payload.getAsJsonArray("items").get(0).getAsJsonObject().get("title").getAsString());
        assertEquals("cotton", payload.get("query").getAsString());
        assertEquals("men's clothing", payload.get("category").getAsString());
    }

    @Test
    void doGetTreatsBlankParametersAsDefaults(AemContext context) throws Exception {
        when(fakeStoreApiClient.fetchProducts()).thenReturn(JsonParser.parseString("["
                + product(1, "Trail Backpack", "men's clothing", 109.95)
                + "]").getAsJsonArray());

        MockSlingHttpServletResponse response = execute(context, "q", "   ", "category", "   ");
        JsonObject payload = JsonParser.parseString(response.getOutputAsString()).getAsJsonObject();

        assertEquals(1, payload.get("total").getAsInt());
        assertEquals("", payload.get("query").getAsString());
        assertEquals("all", payload.get("category").getAsString());
    }

    private MockSlingHttpServletResponse execute(AemContext context, String... parameters) throws Exception {
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        for (int i = 0; i < parameters.length; i += 2) {
            request.addRequestParameter(parameters[i], parameters[i + 1]);
        }
        servlet.doGet(request, response);
        return response;
    }

    private String product(int id, String title, String category, double price) {
        return "{\"id\":" + id
                + ",\"title\":\"" + title + "\""
                + ",\"price\":" + price
                + ",\"description\":\"Description\""
                + ",\"category\":\"" + category + "\""
                + ",\"image\":\"/content/dam/product.png\""
                + ",\"rating\":{\"rate\":4.5,\"count\":12}}";
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
