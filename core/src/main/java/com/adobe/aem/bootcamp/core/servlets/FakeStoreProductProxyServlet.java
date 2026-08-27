package com.adobe.aem.bootcamp.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component(service = {Servlet.class}, property = {
    "sling.servlet.paths=/bin/learner009/quickcart/product",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.extensions=json"
})
@ServiceDescription("FakeStore Product Proxy Servlet")
public class FakeStoreProductProxyServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final String PRODUCT_API_BASE = "https://fakestoreapi.com/products/";

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {

        String productIdParam = request.getParameter("productId");
        int productId = parseProductId(productIdParam);
        if (productId <= 0) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing or invalid productId\"}");
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(PRODUCT_API_BASE + productId);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod(HttpConstants.METHOD_GET);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int upstreamStatus = connection.getResponseCode();
            if (upstreamStatus != HttpURLConnection.HTTP_OK) {
                response.setStatus(SlingHttpServletResponse.SC_BAD_GATEWAY);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Unable to fetch product from FakeStoreAPI\"}");
                return;
            }

            String payload = readResponse(connection);
            response.setStatus(SlingHttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(payload);
        } catch (IOException ex) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_GATEWAY);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"FakeStoreAPI request failed\"}");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private int parseProductId(String value) {
        if (value == null || value.isBlank()) {
            return -1;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder payload = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payload.append(line);
            }
        }
        return payload.toString();
    }
}
