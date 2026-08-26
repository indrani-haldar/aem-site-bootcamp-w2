package com.adobe.aem.bootcamp.core.servlets.track1.learner002;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.servlets.HttpConstants;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = {
        "aem-site-bootcamp-w2/components/track1/learner002/product-grid",
        "aem-site-bootcamp-w2/components/track1/learner002/product-list",
        "aem-site-bootcamp-w2/components/track1/learner002/search-filter"
}, selectors = "product-results", extensions = "json", methods = HttpConstants.METHOD_GET)

public class ProductServlet extends SlingSafeMethodsServlet {

        @Override
        protected void doGet(
                        SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws IOException {

                URL url = new URL("https://dummyjson.com/products");
                String json = IOUtils.toString(
                                url.openStream(),
                                "UTF-8");

                response.setContentType(
                                "application/json");

                response.getWriter().write(json);
        }
}