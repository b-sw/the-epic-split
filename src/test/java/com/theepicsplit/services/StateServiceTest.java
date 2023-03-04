package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.theepicsplit.models.FilterState;
import org.junit.After;
import org.junit.Before;

public class StateServiceTest extends BasePlatformTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		ApplicationManager.getApplication().invokeLater(() -> {
			StateService.getInstance().loadState(new FilterState());
		});
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/* should have null state on init */
	public void testShouldHaveNullStateOnInit() {
		FilterState filterState = StateService.getInstance().getState();

		assertFalse(filterState.isFilterEnabled);
		assertNull(filterState.filterRegex);
	}

	/* should load state */
	public void testShouldLoadState() {
		FilterState filterState = new FilterState(true, ".*.spec.*");

		StateService.getInstance().loadState(filterState);

		assertEquals(filterState, StateService.getInstance().getState());
	}
}
