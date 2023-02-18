package com.theepicsplit.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.FileEditorManagerTestCase;
import com.intellij.util.messages.MessageBusConnection;
import org.junit.After;
import org.junit.Before;

public class FileTypeOpenedListenerTest extends FileEditorManagerTestCase {
    private static final String TEST_FILE_NAME = "file-stub.spec.ts";
    private static final String NON_TEST_FILE_NAME = "file-stub.ts";
    private static final String FILE_CONTENT_STUB = "Content stub";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MessageBusConnection messageBusConnection = getProject().getMessageBus().connect();
        messageBusConnection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileTypeOpenedListener());
    }

    @After
    public void tearDown() {
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                super.tearDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /* should open non-test file in one tab group */
    public void testShouldOpenNonTestFileInOneTabGroup() {
        this._shouldOpenFileInOneTabGroup(NON_TEST_FILE_NAME);
    }

    /* should open test file in one tab group */
    public void testShouldOpenTestFileInOneTabGroup() {
        this._shouldOpenFileInOneTabGroup(TEST_FILE_NAME);
    }

    private void _shouldOpenFileInOneTabGroup(String fileName) {
        VirtualFile tsFile = super.myFixture.createFile(fileName, FILE_CONTENT_STUB);

        super.myFixture.openFileInEditor(tsFile);

        assertEquals(1, this._getEditorWindows().length);
    }


    /* should open both files in one window for one tab group open */
    public void testShouldOpenBothFilesInOneTabGroupForOneTabGroupOpen() {
        VirtualFile nonTestFile = super.myFixture.createFile(NON_TEST_FILE_NAME, FILE_CONTENT_STUB);
        VirtualFile testFile = super.myFixture.createFile(TEST_FILE_NAME, FILE_CONTENT_STUB);

        super.myFixture.openFileInEditor(nonTestFile);
        super.myFixture.openFileInEditor(testFile);

        assertEquals(1, this._getEditorWindows().length);
    }

    /* should open test file in correct tab for two tab groups open */
    public void testShouldOpenTestFileInCorrectTabGroupForTwoTabGroupsOpen() {
        VirtualFile newTestFile = super.myFixture.createFile("new-file-stub.spec.ts", FILE_CONTENT_STUB);
        this._openInitialFilesInTwoTabGroups();

        super.myFixture.openFileInEditor(newTestFile);

        assertEquals(2, this._getEditorWindows().length);
        assertEquals(1, this._getEditorWindows()[0].getFiles().length);
        assertEquals(2, this._getEditorWindows()[1].getFiles().length);
    }

    /* should open non-test file in correct tab for two tab groups open */
    public void testShouldOpenNonTestFileInCorrectTabGroupForTwoTabGroupsOpen() {
        VirtualFile newNonTestFile = super.myFixture.createFile("new-file-stub.ts", FILE_CONTENT_STUB);
        this._openInitialFilesInTwoTabGroups();

        super.myFixture.openFileInEditor(newNonTestFile);

        assertEquals(2, this._getEditorWindows().length);
        assertEquals(2, this._getEditorWindows()[0].getFiles().length);
        assertEquals(1, this._getEditorWindows()[1].getFiles().length);
    }

    private void _openInitialFilesInTwoTabGroups() {
        VirtualFile nonTestFile = super.myFixture.createFile(NON_TEST_FILE_NAME, FILE_CONTENT_STUB);
        VirtualFile testFile = super.myFixture.createFile(TEST_FILE_NAME, FILE_CONTENT_STUB);
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager();

        super.myFixture.openFileInEditor(nonTestFile);
        super.myFixture.openFileInEditor(testFile);
        fileEditorManager.createSplitter(0, fileEditorManager.getCurrentWindow());
        fileEditorManager.getWindows()[0].closeFile(testFile);
    }

    private FileEditorManagerEx _getFileEditorManager() {
        return FileEditorManagerEx.getInstanceEx(getProject());
    }

    private EditorWindow[] _getEditorWindows() {
        return this._getFileEditorManager().getWindows();
    }

    private void log(String message) {
        System.out.println(message);
    }
}
