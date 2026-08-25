package com.adobe.aem.bootcamp.core.servlets.track1.learner003;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;

@Component(service = Servlet.class, property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/shopfast/products",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
})
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