package com.theepicsplit.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

public class OpenConfigAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        openConfigDialog();
    }

    private void openConfigDialog() {
        DialogWrapper configDialog = new ConfigDialog();

        configDialog.showAndGet();
    }

    @Override
    public void update(AnActionEvent event) {
        Project project = event.getProject();

        event.getPresentation().setEnabledAndVisible(project != null);
    }
}
