package com.example.theepicsplit.listeners;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectLocator;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileTypeOpenedListener implements FileEditorManagerListener {
    private static final String JEST_TEST_FILE_PATTERN = ".*.spec.*";
    private static final ArrayList<VirtualFile> filesToDelete = new ArrayList<>();
    private static boolean isSorting = false;

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile openedFile) {
        FileEditorManagerListener.super.fileOpened(source, openedFile);

        if (!FileTypeOpenedListener.isSorting) {
            FileTypeOpenedListener.isSorting = true;
            this._tryReorganizeTabGroups(openedFile);
            FileTypeOpenedListener.isSorting = false;
        }
    }

    private void _tryReorganizeTabGroups(VirtualFile openedFile) {
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(openedFile);
        boolean filesAreSorted = this._areFilesSorted(fileEditorManager);

        if (!filesAreSorted) {
            this._sortFiles(openedFile);
        }
    }

    private FileEditorManagerEx _getFileEditorManager(VirtualFile openedFile) {
        Project currentProject = ProjectLocator.getInstance().guessProjectForFile(openedFile);

        return FileEditorManagerEx.getInstanceEx(currentProject);
    }

    private boolean _areFilesSorted(FileEditorManagerEx fileEditorManager) {
        EditorWindow[] windowPanes = fileEditorManager.getWindows();

        if (windowPanes.length < 2) {
            return true;
        }

        return this._areTabGroupsSorted(windowPanes);
    }

    private boolean _areTabGroupsSorted(EditorWindow[] windowPanes) {
        VirtualFile[] firstWindowFiles = windowPanes[0].getFiles();
        VirtualFile[] secondWindowFiles = windowPanes[1].getFiles();
        boolean firstWindowIsSorted = Arrays.stream(firstWindowFiles).noneMatch(this::_isFileJestTest);
        boolean secondWindowIsSorted = Arrays.stream(secondWindowFiles).allMatch(this::_isFileJestTest);

        return firstWindowIsSorted && secondWindowIsSorted;
    }

    private boolean _isFileJestTest(VirtualFile file) {
        return file.getName().matches(JEST_TEST_FILE_PATTERN);
    }

    private void _sortFiles(VirtualFile newlyOpenedFile) {
        System.out.println("reorganizing tab groups");
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(newlyOpenedFile);
        VirtualFile[] openFiles = fileEditorManager.getOpenFiles();

        for (VirtualFile openedFile : openFiles) {
            fileEditorManager.closeFile(openedFile);
            this._openFileInProperTabGroup(openedFile);
        }

        System.out.println("closing files");
        FileTypeOpenedListener.filesToDelete.forEach(this::_tryCloseFile);
        FileTypeOpenedListener.filesToDelete.clear();
    }

    private void _openFileInProperTabGroup(VirtualFile file) {
        System.out.println("opening file in proper tab group");
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(file);
        EditorWindow[] windowPanes = fileEditorManager.getWindows();

        if (file.getName().matches(JEST_TEST_FILE_PATTERN)) {
            if (windowPanes.length == 2) {
                fileEditorManager.openFileWithProviders(file, false, windowPanes[1]);
            } else {
                fileEditorManager.createSplitter(SwingConstants.VERTICAL, windowPanes[0]);
                VirtualFile activeFile = fileEditorManager.getCurrentFile();
                FileTypeOpenedListener.filesToDelete.add(activeFile);
                fileEditorManager.openFileWithProviders(file, false, windowPanes[1]);
            }
        } else {
            fileEditorManager.openFileWithProviders(file, false, windowPanes[0]);
        }
    }

    private void _tryCloseFile(VirtualFile file) {
        FileEditorManagerEx fileEditorManager = this._getFileEditorManager(file);
        boolean fileIsOpen = fileEditorManager.isFileOpen(file);

        if (fileIsOpen) {
            fileEditorManager.closeFile(file);
        }
    }
}
