package com.adobe.aem.bootcamp.core.learner004.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "aem-site-bootcamp-w2/components/learner004/searchresultsgrid", selectors = "searchresults", extensions = "json", methods = HttpConstants.METHOD_GET)
public class SearchResultsServlet extends SlingSafeMethodsServlet {

        private static final long serialVersionUID = 1L;

        private static final String API_URL = "https://fakestoreapi.com/products";

        @Override
        protected void doGet(
                        SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws IOException {

                String query = request.getParameter("q");

                HttpURLConnection connection = openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(10000);

                BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                                connection.getInputStream()));

                StringBuilder apiResponse = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                        apiResponse.append(line);
                }

                reader.close();

                ObjectMapper mapper = new ObjectMapper();

                JsonNode products = mapper.readTree(
                                apiResponse.toString());

                ArrayNode filteredProducts = mapper.createArrayNode();

                for (JsonNode product : products) {

                        String title = product.path("title")
                                        .asText();

                        if (query == null
                                        || query.isBlank()
                                        || title.toLowerCase()
                                                        .contains(query.toLowerCase())) {

                                filteredProducts.add(product);
                        }
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter()
                                .write(filteredProducts.toString());
        }

        protected HttpURLConnection openConnection() throws IOException {
                URL url = URI.create(API_URL).toURL();
                return (HttpURLConnection) url.openConnection();
        }
}
