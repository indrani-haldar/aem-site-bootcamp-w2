package com.adobe.aem.bootcamp.core.learner004.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
class SearchResultsServletTest {

	private static final String PRODUCTS_JSON = "["
			+ "{\"id\":1,\"title\":\"Phone case\"},"
			+ "{\"id\":2,\"title\":\"Laptop stand\"}"
			+ "]";

	@Test
	void doGetFiltersProductsByQuery(AemContext context) throws IOException {
		HttpURLConnection connection = mockConnection();
		SearchResultsServlet fixture = new SearchResultsServlet() {
			@Override
			protected HttpURLConnection openConnection() {
				return connection;
			}
		};
		MockSlingHttpServletRequest request = context.request();
		request.setParameterMap(Collections.singletonMap("q", "PHONE"));
		MockSlingHttpServletResponse response = context.response();

		fixture.doGet(request, response);

		assertEquals("application/json;charset=UTF-8", response.getContentType());
		assertEquals("UTF-8", response.getCharacterEncoding());
		assertEquals("[{\"id\":1,\"title\":\"Phone case\"}]", response.getOutputAsString());
		verify(connection).setRequestMethod("GET");
	}

	@Test
	void doGetReturnsAllProductsWhenQueryIsBlank(AemContext context) throws IOException {
		HttpURLConnection connection = mockConnection();
		SearchResultsServlet fixture = new SearchResultsServlet() {
			@Override
			protected HttpURLConnection openConnection() {
				return connection;
			}
		};
		MockSlingHttpServletRequest request = context.request();
		request.setParameterMap(Collections.singletonMap("q", " "));
		MockSlingHttpServletResponse response = context.response();

		fixture.doGet(request, response);

		assertEquals(PRODUCTS_JSON, response.getOutputAsString());
	}

	private HttpURLConnection mockConnection() throws IOException {
		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(
				PRODUCTS_JSON.getBytes(StandardCharsets.UTF_8)));
		return connection;
	}
}
