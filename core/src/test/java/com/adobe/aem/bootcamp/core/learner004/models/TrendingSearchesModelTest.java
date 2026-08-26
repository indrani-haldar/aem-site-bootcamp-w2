package com.adobe.aem.bootcamp.core.learner004.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;

class TrendingSearchesModelTest {

	@Test
	void shouldReturnEmptyListWhenTrendingSearchesFolderDoesNotExist() {
		TrendingSearchesModel model = new TrendingSearchesModel();
		setResourceResolver(model, mock(ResourceResolver.class));

		ResourceResolver resolver = getResourceResolver(model);
		when(resolver.getResource("/content/dam/aem-site-bootcamp-w2")).thenReturn(null);

		model.init();

		assertNotNull(model.getTrendingSearches());
		assertEquals(0, model.getTrendingSearches().size());
	}

	@Test
	void shouldLoadTrendingSearchesFromContentFragments() {
		ResourceResolver resolver = mock(ResourceResolver.class);
		Resource folder = mock(Resource.class);
		Resource validChild = mock(Resource.class);
		Resource incompleteChild = mock(Resource.class);
		ContentFragment validFragment = mock(ContentFragment.class);
		ContentFragment incompleteFragment = mock(ContentFragment.class);
		ContentElement labelElement = mock(ContentElement.class);
		ContentElement queryElement = mock(ContentElement.class);

		when(resolver.getResource("/content/dam/aem-site-bootcamp-w2")).thenReturn(folder);
		when(folder.getChildren()).thenReturn(Arrays.asList(validChild, incompleteChild));
		when(validChild.adaptTo(ContentFragment.class)).thenReturn(validFragment);
		when(incompleteChild.adaptTo(ContentFragment.class)).thenReturn(incompleteFragment);
		when(validFragment.getElement("label")).thenReturn(labelElement);
		when(validFragment.getElement("query")).thenReturn(queryElement);
		when(labelElement.getContent()).thenReturn("Laptops");
		when(queryElement.getContent()).thenReturn("laptops");
		when(incompleteFragment.getElement("label")).thenReturn(null);

		TrendingSearchesModel model = new TrendingSearchesModel();
		setResourceResolver(model, resolver);
		model.init();

		List<TrendingSearchesModel.TrendingSearch> searches = model.getTrendingSearches();
		assertEquals(1, searches.size());
		assertEquals("Laptops", searches.get(0).getLabel());
		assertEquals("laptops", searches.get(0).getQuery());
	}

	@Test
	void shouldReturnUnmodifiableTrendingSearchesList() {
		ResourceResolver resolver = mock(ResourceResolver.class);
		Resource folder = mock(Resource.class);
		when(resolver.getResource("/content/dam/aem-site-bootcamp-w2")).thenReturn(folder);
		when(folder.getChildren()).thenReturn(List.of());

		TrendingSearchesModel model = new TrendingSearchesModel();
		setResourceResolver(model, resolver);
		model.init();

		assertThrows(UnsupportedOperationException.class,
				() -> model.getTrendingSearches().add(
						new TrendingSearchesModel.TrendingSearch("Phones", "phones")));
	}

	private static void setResourceResolver(TrendingSearchesModel model, ResourceResolver resolver) {
		try {
			Field field = TrendingSearchesModel.class.getDeclaredField("resourceResolver");
			field.setAccessible(true);
			field.set(model, resolver);
		} catch (ReflectiveOperationException exception) {
			throw new AssertionError("Unable to inject test resource resolver", exception);
		}
	}

	private static ResourceResolver getResourceResolver(TrendingSearchesModel model) {
		try {
			Field field = TrendingSearchesModel.class.getDeclaredField("resourceResolver");
			field.setAccessible(true);
			return (ResourceResolver) field.get(model);
		} catch (ReflectiveOperationException exception) {
			throw new AssertionError("Unable to read test resource resolver", exception);
		}
	}
}
