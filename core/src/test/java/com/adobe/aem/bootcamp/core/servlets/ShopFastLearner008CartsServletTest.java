package com.adobe.aem.bootcamp.core.servlets;

import com.adobe.aem.bootcamp.core.learner008.models.FakeStoreApiClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
class ShopFastLearner008CartsServletTest {

    private final ShopFastLearner008CartsServlet servlet = new ShopFastLearner008CartsServlet();
    private final FakeStoreApiClient fakeStoreApiClient = mock(FakeStoreApiClient.class);

    @BeforeEach
    void setUp() throws Exception {
        setField(servlet, "fakeStoreApiClient", fakeStoreApiClient);
    }

    @Test
    void doGetEnrichesCartItemsWithProductData(AemContext context) throws Exception {
        when(fakeStoreApiClient.fetchCarts()).thenReturn(JsonParser.parseString("["
                + "{\"id\":1,\"products\":[{\"productId\":1,\"quantity\":2},{\"productId\":99,\"quantity\":1}]}"
                + "]").getAsJsonArray());
        when(fakeStoreApiClient.fetchProducts()).thenReturn(JsonParser.parseString("["
                + product(1, "Trail Backpack", 109.95)
                + "]").getAsJsonArray());

        MockSlingHttpServletResponse response = context.response();
        servlet.doGet(context.request(), response);
        JsonObject payload = JsonParser.parseString(response.getOutputAsString()).getAsJsonObject();
        JsonObject item = payload.getAsJsonArray("items").get(0).getAsJsonObject();

        assertTrue(response.getContentType().startsWith("application/json"));
        assertEquals(1, payload.get("count").getAsInt());
        assertEquals(219.90D, payload.get("total").getAsDouble(), 0.001D);
        assertEquals(1, item.get("id").getAsInt());
        assertEquals("Trail Backpack", item.get("title").getAsString());
        assertEquals("/content/dam/backpack.png", item.get("image").getAsString());
        assertEquals(109.95D, item.get("price").getAsDouble(), 0.001D);
        assertEquals(2, item.get("quantity").getAsInt());
        assertEquals(219.90D, item.get("subtotal").getAsDouble(), 0.001D);
    }

    @Test
    void doGetReturnsEmptyCartWhenApiHasNoCarts(AemContext context) throws Exception {
        when(fakeStoreApiClient.fetchCarts()).thenReturn(JsonParser.parseString("[]").getAsJsonArray());
        when(fakeStoreApiClient.fetchProducts()).thenReturn(JsonParser.parseString("["
                + product(1, "Trail Backpack", 109.95)
                + "]").getAsJsonArray());

        MockSlingHttpServletResponse response = context.response();
        servlet.doGet(context.request(), response);
        JsonObject payload = JsonParser.parseString(response.getOutputAsString()).getAsJsonObject();

        assertEquals(0, payload.get("count").getAsInt());
        assertEquals(0, payload.get("total").getAsDouble(), 0.001D);
        assertEquals(0, payload.getAsJsonArray("items").size());
    }

    private String product(int id, String title, double price) {
        return "{\"id\":" + id
                + ",\"title\":\"" + title + "\""
                + ",\"price\":" + price
                + ",\"description\":\"Description\""
                + ",\"category\":\"men's clothing\""
                + ",\"image\":\"/content/dam/backpack.png\""
                + ",\"rating\":{\"rate\":4.5,\"count\":12}}";
    }

    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
