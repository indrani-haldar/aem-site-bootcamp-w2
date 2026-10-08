package com.adobe.aem.bootcamp.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailHeroModel {

    private static final String PROXY_PATH = "/bin/learner009/quickcart/product.json";
    private static final String DIRECT_API_BASE = "https://fakestoreapi.com/products/";

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String addToCartLabel;

    @ValueMapValue
    private String fallbackProductId;

    private String productId;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
    private String errorMessage;

    @PostConstruct
    protected void init() {
        productId = resolveProductId();

        if (productId == null) {
            errorMessage = "Product ID is missing. Add ?productId=1 or a numeric selector like .1.html.";
            return;
        }

        String endpoint = buildServletUrl(productId);
        String payload = fetchPayload(endpoint);

        // In local author, unauthenticated server-to-server calls to /bin endpoints can return 401.
        // Fallback to FakeStore directly so authoring still works.
        if (payload == null || payload.isBlank()) {
            payload = fetchPayload(DIRECT_API_BASE + productId);
        }

        if (payload == null || payload.isBlank()) {
            errorMessage = "Unable to load product details.";
            return;
        }

        try {
            JSONObject json = new JSONObject(payload);
            title = json.optString("title");
            description = json.optString("description");
            image = json.optString("image");
            category = json.optString("category");
            price = json.optDouble("price", 0d);

            if (title == null || title.isBlank()) {
                errorMessage = "Product payload did not include a title.";
            }
        } catch (JSONException ex) {
            errorMessage = "Invalid product data returned by proxy servlet.";
        }
    }

    private String resolveProductId() {
        String fromQuery = request.getParameter("productId");
        if (isNumeric(fromQuery)) {
            return fromQuery;
        }

        String[] selectors = request.getRequestPathInfo().getSelectors();
        if (selectors != null) {
            for (String selector : selectors) {
                if (isNumeric(selector)) {
                    return selector;
                }
            }
        }

        if (isNumeric(fallbackProductId)) {
            return fallbackProductId;
        }

        return null;
    }

    private boolean isNumeric(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        for (int i = 0; i < value.length(); i += 1) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String buildServletUrl(String id) {
        String encodedId = URLEncoder.encode(id, StandardCharsets.UTF_8);
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + PROXY_PATH + "?productId=" + encodedId;
    }

    private String fetchPayload(String endpoint) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            StringBuilder payload = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    payload.append(line);
                }
            }
            return payload.toString();
        } catch (IOException ex) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceLabel() {
        return String.format("$%.2f", price);
    }

    public String getAddToCartLabel() {
        if (addToCartLabel == null || addToCartLabel.isBlank()) {
            return "Add to Cart";
        }
        return addToCartLabel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid() {
        return errorMessage == null;
    }
}
