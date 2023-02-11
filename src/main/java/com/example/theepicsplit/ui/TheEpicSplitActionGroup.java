package com.example.theepicsplit.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TheEpicSplitActionGroup extends DefaultActionGroup {
    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(true);
        // Enable/disable depending on whether a user is editing
//        Editor editor = event.getData(CommonDataKeys.EDITOR);
//        event.getPresentation().setEnabled(editor != null);
        // Take this opportunity to set an icon for the group.
//        event.getPresentation().setIcon();
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        List<AnAction> children = new ArrayList<>(1);

        children.add(new AskQuestionAction());

        return children.toArray(AnAction.EMPTY_ARRAY);
    }
}
