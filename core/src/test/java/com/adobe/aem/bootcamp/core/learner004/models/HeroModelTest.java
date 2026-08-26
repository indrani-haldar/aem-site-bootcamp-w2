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
class HeroModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(HeroModel.class);
	}

	@Test
	void shouldExposeConfiguredHeroProperties() {
		Resource resource = context.create().resource("/content/hero",
				"heading", "Build your future",
				"subtext", "Explore our latest products",
				"backgroundImage", "/content/dam/hero.jpg",
				"ctaLabel", "Explore now",
				"ctaLink", "/content/site/products.html");

		HeroModel model = resource.adaptTo(HeroModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertEquals("Build your future", model.getHeading()),
				() -> assertEquals("Explore our latest products", model.getSubtext()),
				() -> assertEquals("/content/dam/hero.jpg", model.getBackgroundImage()),
				() -> assertEquals("Explore now", model.getCtaLabel()),
				() -> assertEquals("/content/site/products.html", model.getCtaLink()));
	}

	@Test
	void shouldAllowMissingOptionalProperties() {
		Resource resource = context.create().resource("/content/hero");

		HeroModel model = resource.adaptTo(HeroModel.class);

		assertNotNull(model);
		assertAll(
				() -> assertNull(model.getHeading()),
				() -> assertNull(model.getSubtext()),
				() -> assertNull(model.getBackgroundImage()),
				() -> assertNull(model.getCtaLabel()),
				() -> assertNull(model.getCtaLink()));
	}
}
