package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.theepicsplit.models.FilterState;
import org.junit.After;
import org.junit.Before;

public class StateServiceTest extends BasePlatformTestCase {
	private CodeInsightTestFixture fixture;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.fixture = super.myFixture;
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

	/* should persist state after restart*/
	public void testShouldPersistState() throws Exception {
		FilterState filterState = new FilterState(true, ".*.spec.*");
		StateService.getInstance().loadState(filterState);

		ApplicationManager.getApplication().exit(false, true, true);

		FilterState persistedFilterState = StateService.getInstance().getState();
		assertEquals(filterState.filterRegex, persistedFilterState.filterRegex);
		assertEquals(filterState.isFilterEnabled, persistedFilterState.isFilterEnabled);
	}
}
