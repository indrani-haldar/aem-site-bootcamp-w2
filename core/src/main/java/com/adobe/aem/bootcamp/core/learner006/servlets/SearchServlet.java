package com.adobe.aem.bootcamp.core.learner006.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = {
                "sling.servlet.paths=/bin/searchhub/products",
                "sling.servlet.methods=GET"
})
public class SearchServlet extends SlingSafeMethodsServlet {

        @Override
        protected void doGet(
                        SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws IOException {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                try {

                        URL url = new URL("https://fakestoreapi.com/products");

                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                        connection.setRequestMethod("GET");

                        BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                        connection.getInputStream()));

                        StringBuilder jsonResponse = new StringBuilder();

                        String line;

                        while ((line = reader.readLine()) != null) {
                                jsonResponse.append(line);
                        }

                        reader.close();
                        connection.disconnect();

                        response.getWriter().write(
                                        jsonResponse.toString());

                } catch (Exception e) {

                        e.printStackTrace();

                        response.setStatus(500);

                        response.getWriter().write(
                                        "{\"error\":\"" +
                                                        e.getMessage() +
                                                        "\"}");
                }
        }
}