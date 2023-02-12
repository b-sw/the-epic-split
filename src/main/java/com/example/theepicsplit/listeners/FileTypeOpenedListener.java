package com.example.theepicsplit.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectLocator;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;


public class FileTypeOpenedListener implements FileEditorManagerListener {
    private static final String JEST_TEST_FILE_PATTERN = ".*.spec.*";
    private static final int DEFAULT_TAB_INDEX = 0;
    private static final int JEST_TAB_INDEX = 1;

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile openedFile) {
        this._tryMoveFileToTabGroup(openedFile);
    }

    private void _tryMoveFileToTabGroup(VirtualFile openedFile) {
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(openedFile);
        boolean twoTabsAreOpen = fileEditorManager.getWindows().length == 2;

        if (twoTabsAreOpen) {
            this._moveFileToTargetTabGroup(openedFile);
        }
    }

    private FileEditorManagerEx _getFileEditorManager(VirtualFile openedFile) {
        Project currentProject = ProjectLocator.getInstance().guessProjectForFile(openedFile);

        return FileEditorManagerEx.getInstanceEx(currentProject);
    }

    private void _moveFileToTargetTabGroup(VirtualFile file) {
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(file);
        EditorWindow[] windowPanes = fileEditorManager.getWindows();
        int targetWindowPaneIndex = this._isFileJestTest(file) ? JEST_TAB_INDEX : DEFAULT_TAB_INDEX;
        EditorWindow initialWindowPane = fileEditorManager.getCurrentWindow();
        EditorWindow targetWindowPane = windowPanes[targetWindowPaneIndex];

        fileEditorManager.openFileWithProviders(file, true, targetWindowPane);
        this._tryCloseFileInWrongTabGroup(file, initialWindowPane, targetWindowPane);
    }

    private void _tryCloseFileInWrongTabGroup(VirtualFile file, EditorWindow initialWindowPane, EditorWindow targetWindowPane) {
        if (initialWindowPane == targetWindowPane) {
            return;
        }

        if (!initialWindowPane.isFileOpen(file)) {
            return;
        }

        this._closeFileInTabGroup(file, initialWindowPane);
    }

    private void _closeFileInTabGroup(VirtualFile file, EditorWindow tabGroup) {
        Runnable closeFile = () -> tabGroup.closeFile(file, true, true);

        ApplicationManager.getApplication().invokeLater(closeFile);
    }

    private boolean _isFileJestTest(VirtualFile file) {
        return file.getName().matches(JEST_TEST_FILE_PATTERN);
    }
}
