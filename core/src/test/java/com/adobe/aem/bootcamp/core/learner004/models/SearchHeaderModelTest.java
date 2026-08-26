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
class SearchHeaderModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(SearchHeaderModel.class);
	}

	@Test
	void shouldExposeConfiguredSearchHeaderProperties() {
		Resource resource = context.create().resource("/content/search-header",
				"heading", "Shop our products",
				"ctaLabel", "Search",
				"ctaLink", "/content/site/search.html");

		SearchHeaderModel model = resource.adaptTo(SearchHeaderModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertEquals("Shop our products", model.getHeading()),
				() -> assertEquals("Search", model.getCtaLabel()),
				() -> assertEquals("/content/site/search.html", model.getCtaLink()));
	}

	@Test
	void shouldAllowMissingOptionalProperties() {
		Resource resource = context.create().resource("/content/search-header");

		SearchHeaderModel model = resource.adaptTo(SearchHeaderModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertNull(model.getHeading()),
				() -> assertNull(model.getCtaLabel()),
				() -> assertNull(model.getCtaLink()));
	}
}
