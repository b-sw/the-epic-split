package com.example.theepicsplit.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;

public class AssociateFileTypeWithWindow extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        openConfigDialog();
    }

    private void openConfigDialog() {
        DialogWrapper configDialog = new ConfigDialogWrapper();
        configDialog.showAndGet();
    }
}

