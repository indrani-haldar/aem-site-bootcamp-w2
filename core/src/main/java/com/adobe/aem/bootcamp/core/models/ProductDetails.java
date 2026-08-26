package com.adobe.aem.bootcamp.core.models;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetails {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDetails.class);
    private static final String CARTS_API_URL = "https://fakestoreapi.com/carts";
    private static final String PRODUCTS_API_URL = "https://fakestoreapi.com/products";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String description;

    private Map<Integer, Product> productsById = Collections.emptyMap();
    private List<Cart> carts = Collections.emptyList();

    @PostConstruct
    protected void init() {
        productsById = fetchArray(PRODUCTS_API_URL, this::toProduct, Product::getId);
        Map<Integer, Cart> cartsById = fetchArray(CARTS_API_URL, this::toCart, Cart::getId);
        carts = new ArrayList<>(cartsById.values());
    }

    private <T> Map<Integer, T> fetchArray(String url, java.util.function.Function<JsonObject, T> mapper,
            java.util.function.Function<T, Integer> keyExtractor) {
        Map<Integer, T> result = new HashMap<>();
        String body = fetchJson(url);
        if (body == null) {
            return result;
        }
        try (JsonReader jsonReader = Json.createReader(new StringReader(body))) {
            JsonArray array = jsonReader.readArray();
            for (JsonValue value : array) {
                T item = mapper.apply(value.asJsonObject());
                result.put(keyExtractor.apply(item), item);
            }
        }
        return result;
    }

    private String fetchJson(String url) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(TIMEOUT)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }
            LOG.warn("Unexpected status code {} fetching {}", response.statusCode(), url);
        } catch (Exception e) {
            LOG.error("Failed to fetch {}", url, e);
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

    private Cart toCart(JsonObject cartObject) {
        List<CartProduct> products = new ArrayList<>();
        JsonArray productsArray = cartObject.getJsonArray("products");
        if (productsArray != null) {
            for (JsonValue productValue : productsArray) {
                products.add(toCartProduct(productValue.asJsonObject()));
            }
        }
        return new Cart(
                cartObject.getInt("id", 0),
                cartObject.getInt("userId", 0),
                cartObject.getString("date", ""),
                products);
    }

    private CartProduct toCartProduct(JsonObject productObject) {
        int productId = productObject.getInt("productId", 0);
        int quantity = productObject.getInt("quantity", 0);
        return new CartProduct(productId, quantity, productsById.get(productId));
    }

    private Product toProduct(JsonObject productObject) {
        return new Product(
                productObject.getInt("id", 0),
                productObject.getString("title", ""),
                getDouble(productObject, "price", 0.0),
                productObject.getString("category", ""),
                productObject.getString("image", ""));
    }

    private double getDouble(JsonObject object, String key, double defaultValue) {
        return object.containsKey(key) && !object.isNull(key) ? object.getJsonNumber(key).doubleValue() : defaultValue;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public static class Cart {

        private final int id;
        private final int userId;
        private final String date;
        private final List<CartProduct> products;

        public Cart(int id, int userId, String date, List<CartProduct> products) {
            this.id = id;
            this.userId = userId;
            this.date = date;
            this.products = products;
        }

        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
        }

        public String getDate() {
            return date;
        }

        public List<CartProduct> getProducts() {
            return products;
        }
    }

    public static class CartProduct {

        private final int productId;
        private final int quantity;
        private final Product product;

        public CartProduct(int productId, int quantity, Product product) {
            this.productId = productId;
            this.quantity = quantity;
            this.product = product;
        }

        public int getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public Product getProduct() {
            return product;
        }
    }

    public static class Product {

        private final int id;
        private final String title;
        private final double price;
        private final String category;
        private final String image;

        public Product(int id, String title, double price, String category, String image) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.category = category;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public String getImage() {
            return image;
        }
    }
}
