package com.example.theepicsplit.actionGroups;

import com.example.theepicsplit.actions.PopupDialogAction;
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
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        List<AnAction> children = new ArrayList<>(1);

        children.add(new PopupDialogAction());

        return children.toArray(AnAction.EMPTY_ARRAY);
    }
}
