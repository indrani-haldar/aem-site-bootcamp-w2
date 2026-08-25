package com.adobe.aem.bootcamp.core.servlets;

import com.adobe.aem.bootcamp.core.models.FakeStoreApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class, property = {
        ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/shopfast/learner008/carts",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
})
public class ShopFastLearner008CartsServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private transient FakeStoreApiClient fakeStoreApiClient;

    private final Gson gson = new Gson();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        JsonArray carts = fakeStoreApiClient.fetchCarts();
        JsonArray products = fakeStoreApiClient.fetchProducts();

        Map<Integer, JsonObject> productsById = new HashMap<Integer, JsonObject>();
        for (JsonElement productElement : products) {
            JsonObject product = productElement.getAsJsonObject();
            productsById.put(product.get("id").getAsInt(), product);
        }

        JsonArray items = new JsonArray();
        double total = 0D;

        if (carts.size() > 0) {
            JsonObject firstCart = carts.get(0).getAsJsonObject();
            JsonArray cartProducts = firstCart.getAsJsonArray("products");
            for (JsonElement cartEntry : cartProducts) {
                JsonObject cartItem = cartEntry.getAsJsonObject();
                int productId = cartItem.get("productId").getAsInt();
                int quantity = cartItem.get("quantity").getAsInt();

                JsonObject product = productsById.get(productId);
                if (product == null) {
                    continue;
                }

                double price = product.get("price").getAsDouble();
                double subtotal = price * quantity;
                total += subtotal;

                JsonObject item = new JsonObject();
                item.addProperty("id", productId);
                item.addProperty("title", product.get("title").getAsString());
                item.addProperty("image", product.get("image").getAsString());
                item.addProperty("price", price);
                item.addProperty("quantity", quantity);
                item.addProperty("subtotal", subtotal);
                items.add(item);
            }
        }

        JsonObject payload = new JsonObject();
        payload.add("items", items);
        payload.addProperty("total", total);
        payload.addProperty("count", items.size());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(payload));
    }
}
