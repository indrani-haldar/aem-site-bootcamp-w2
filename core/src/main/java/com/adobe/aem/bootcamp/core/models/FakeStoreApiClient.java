package com.adobe.aem.bootcamp.core.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component(service = FakeStoreApiClient.class)
public class FakeStoreApiClient {

    private static final String API_BASE = "https://fakestoreapi.com";
    private static final Duration TIMEOUT = Duration.ofSeconds(8);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(TIMEOUT)
            .build();

    public JsonArray fetchProducts() throws IOException {
        return fetchArray("/products");
    }

    public JsonArray fetchCarts() throws IOException {
        return fetchArray("/carts");
    }

    private JsonArray fetchArray(String path) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + path))
                .timeout(TIMEOUT)
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while calling FakeStore API", e);
        }

        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new IOException("FakeStore API request failed with status " + response.statusCode());
        }

        JsonElement element = JsonParser.parseString(response.body());
        if (!element.isJsonArray()) {
            return new JsonArray();
        }
        return element.getAsJsonArray();
    }
}
