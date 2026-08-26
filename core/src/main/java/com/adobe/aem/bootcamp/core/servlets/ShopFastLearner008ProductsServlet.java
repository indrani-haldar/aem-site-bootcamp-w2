package com.adobe.aem.bootcamp.core.servlets;

import com.adobe.aem.bootcamp.core.learner008.models.FakeStoreApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

@Component(service = Servlet.class, property = {
        ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/shopfast/learner008/products",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
})
public class ShopFastLearner008ProductsServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private transient FakeStoreApiClient fakeStoreApiClient;

    private final Gson gson = new Gson();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String query = parameterOrDefault(request.getParameter("q"), "").toLowerCase(Locale.ROOT);
        String category = parameterOrDefault(request.getParameter("category"), "all").toLowerCase(Locale.ROOT);

        JsonArray allProducts = fakeStoreApiClient.fetchProducts();
        JsonArray items = new JsonArray();
        Set<String> categories = new LinkedHashSet<String>();

        for (int i = 0; i < allProducts.size(); i++) {
            JsonObject product = allProducts.get(i).getAsJsonObject();
            String title = getAsString(product, "title").toLowerCase(Locale.ROOT);
            String productCategory = getAsString(product, "category").toLowerCase(Locale.ROOT);
            categories.add(productCategory);

            boolean queryMatch = query.isEmpty() || title.contains(query);
            boolean categoryMatch = "all".equals(category) || productCategory.equals(category);

            if (queryMatch && categoryMatch) {
                items.add(product);
            }
        }

        JsonArray categoryArray = new JsonArray();
        for (String value : categories) {
            categoryArray.add(value);
        }

        JsonObject payload = new JsonObject();
        payload.add("items", items);
        payload.add("categories", categoryArray);
        payload.addProperty("total", items.size());
        payload.addProperty("query", query);
        payload.addProperty("category", category);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(payload));
    }

    private String parameterOrDefault(String value, String fallback) {
        if (value == null) {
            return fallback;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? fallback : trimmed;
    }

    private String getAsString(JsonObject object, String key) {
        if (!object.has(key) || object.get(key).isJsonNull()) {
            return "";
        }
        return object.get(key).getAsString();
    }
}
