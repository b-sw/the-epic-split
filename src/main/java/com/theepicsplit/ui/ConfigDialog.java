package com.theepicsplit.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.theepicsplit.infrastructure.FilterStateChangedNotifier;
import com.theepicsplit.services.PersistsStateService;
import com.theepicsplit.state.FilterState;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ConfigDialog extends DialogWrapper {
    private static final String DIALOG_TITLE = "The Epic Split Config";
    private final FilterStateChangedNotifier _filterStateChangedNotifier;
    private final PluginOnOffSwitch _pluginOnOffSwitch = new PluginOnOffSwitch();
    private final PatternField _patternField = new PatternField();

    public ConfigDialog() {
        super(true);
        super.init();

        this._filterStateChangedNotifier = ApplicationManager.getApplication().getMessageBus().syncPublisher(FilterStateChangedNotifier.TOPIC);
        setTitle(ConfigDialog.DIALOG_TITLE);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        dialogPanel.add(this._pluginOnOffSwitch, BorderLayout.LINE_START);
        dialogPanel.add(this._patternField, BorderLayout.LINE_END);
        this._setPanelElementsProps();

        return dialogPanel;
    }

    private void _setPanelElementsProps() {
        this._setOnOffSwitchProps();
        this._setPatternFieldProps();
    }

    private void _setOnOffSwitchProps() {
        FilterState filterState = PersistsStateService.getInstance().getState();

        this._pluginOnOffSwitch.setSelected(filterState.isFilteringEnabled);
        this._pluginOnOffSwitch.addChangeListener(e -> this._patternField.setEnabled(this._pluginOnOffSwitch.isSelected()));
    }

    private void _setPatternFieldProps() {
        FilterState filterState = PersistsStateService.getInstance().getState();

        this._patternField.setEnabled(this._pluginOnOffSwitch.isSelected());
        this._patternField.setText(filterState.filteringPattern);
    }

    @Override
    protected void doOKAction() {
        boolean pluginIsEnabled = this._pluginOnOffSwitch.isSelected();
        String pluginPattern = this._patternField.getText();
        FilterState newFilterState = new FilterState(pluginIsEnabled, pluginPattern);

        this._filterStateChangedNotifier.execute(newFilterState);
        super.doOKAction();
    }
}

