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
class CardTeaserModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(CardTeaserModel.class);
	}

	@Test
	void shouldExposeConfiguredCardTeaserProperties() {
		Resource resource = context.create().resource("/content/card-teaser",
				"imagePath", "/content/dam/product.jpg",
				"imageAlt", "Product image",
				"title", "Premium product",
				"price", "$49.99",
				"ctaLabel", "Buy now");

		CardTeaserModel model = resource.adaptTo(CardTeaserModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertEquals("/content/dam/product.jpg", model.getImagePath()),
				() -> assertEquals("Product image", model.getImageAlt()),
				() -> assertEquals("Premium product", model.getTitle()),
				() -> assertEquals("$49.99", model.getPrice()),
				() -> assertEquals("Buy now", model.getCtaLabel()));
	}

	@Test
	void shouldAllowMissingOptionalProperties() {
		Resource resource = context.create().resource("/content/card-teaser");

		CardTeaserModel model = resource.adaptTo(CardTeaserModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertNull(model.getImagePath()),
				() -> assertNull(model.getImageAlt()),
				() -> assertNull(model.getTitle()),
				() -> assertNull(model.getPrice()),
				() -> assertNull(model.getCtaLabel()));
	}
}
