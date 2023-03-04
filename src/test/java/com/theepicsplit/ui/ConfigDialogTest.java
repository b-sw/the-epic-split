package com.theepicsplit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.Before;

public class ConfigDialogTest extends BasePlatformTestCase {
	private DialogWrapper _dialogWrapper;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this._dialogWrapper = new ConfigDialog();
	}

	/* should open dialog */
	public void testShouldOpenDialog() {
		assertNotNull(this._dialogWrapper);
	}
}
