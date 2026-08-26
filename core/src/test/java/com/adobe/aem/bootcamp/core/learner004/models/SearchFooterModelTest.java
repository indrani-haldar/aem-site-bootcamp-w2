package com.adobe.aem.bootcamp.core.learner004.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SearchFooterModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(SearchFooterModel.class);
	}

	@Test
	void shouldExposeConfiguredSearchFooterProperties() {
		Resource resource = context.create().resource("/content/search-footer",
				"brandTitle", "SearchHub",
				"navigationLabel", "Navigation",
				"homeLink", "/content/site/home.html",
				"resourcesLabel", "Resources",
				"helpLink", "/content/site/help.html",
				"connectLabel", "Connect",
				"linkedInLink", "https://www.linkedin.com/company/searchhub");

		SearchFooterModel model = resource.adaptTo(SearchFooterModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertEquals("SearchHub", model.getBrandTitle()),
				() -> assertEquals("Navigation", model.getNavigationLabel()),
				() -> assertEquals("/content/site/home.html", model.getHomeLink()),
				() -> assertEquals("Resources", model.getResourcesLabel()),
				() -> assertEquals("/content/site/help.html", model.getHelpLink()),
				() -> assertEquals("Connect", model.getConnectLabel()),
				() -> assertEquals("https://www.linkedin.com/company/searchhub", model.getLinkedInLink()));
	}

	@Test
	void shouldAllowMissingOptionalProperties() {
		Resource resource = context.create().resource("/content/search-footer");

		SearchFooterModel model = resource.adaptTo(SearchFooterModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertNull(model.getBrandTitle()),
				() -> assertNull(model.getNavigationLabel()),
				() -> assertNull(model.getHomeLink()),
				() -> assertNull(model.getResourcesLabel()),
				() -> assertNull(model.getHelpLink()),
				() -> assertNull(model.getConnectLabel()),
				() -> assertNull(model.getLinkedInLink()));
	}
}
