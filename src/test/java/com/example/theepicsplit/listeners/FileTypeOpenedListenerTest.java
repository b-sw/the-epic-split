package com.example.theepicsplit.listeners;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.Before;
import org.junit.Test;

class FileTypeOpenedListenerTest extends BasePlatformTestCase {
    private FileEditorManagerListener listener;
    private FileEditorManager fileEditorManager;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        fileEditorManager = new FileEditorManagerImpl(getProject());
        listener = new FileTypeOpenedListener();
        fileEditorManager.addFileEditorManagerListener(listener);
    }

    @Test
    void shouldOpenOnlyFileInOneTabGroup() {
        // given no files are opened in intellij editor
        // when a file is opened
        // then the file is opened in one tab group

    }
}
