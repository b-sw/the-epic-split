package com.theepicsplit.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.theepicsplit.models.FilterState;
import com.theepicsplit.services.StateService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ConfigDialog extends DialogWrapper {
	private static final String DIALOG_TITLE = "The Epic Split Config";
	public final PluginOnOffSwitch _pluginOnOffSwitch = new PluginOnOffSwitch();
	private final PatternField _patternField = new PatternField();

	public ConfigDialog() {
		super(true);
		super.init();

		setTitle(ConfigDialog.DIALOG_TITLE);
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		JPanel dialogPanel = new JPanel(new BorderLayout());

		dialogPanel.add(this._pluginOnOffSwitch, BorderLayout.CENTER);
		dialogPanel.add(this._patternField, BorderLayout.AFTER_LAST_LINE);
		this._setPanelElementsProps();

		return dialogPanel;
	}

	private void _setPanelElementsProps() {
		this._setOnOffSwitchProps();
		this._setPatternFieldProps();
	}

	private void _setOnOffSwitchProps() {
		FilterState filterState = StateService.getInstance().getState();

		this._pluginOnOffSwitch.setSelected(filterState.isFilterEnabled);
		this._pluginOnOffSwitch.addChangeListener(e -> this._patternField.setEnabled(this._pluginOnOffSwitch.isSelected()));
	}

	private void _setPatternFieldProps() {
		FilterState filterState = StateService.getInstance().getState();

		this._patternField.setText(filterState.filterRegex);
		this._patternField.setEnabled(this._pluginOnOffSwitch.isSelected());
	}

	@Override
	protected void doOKAction() {
		boolean pluginIsEnabled = this._pluginOnOffSwitch.isSelected();
		String pluginPattern = this._patternField.getText();
		FilterState newFilterState = new FilterState(pluginIsEnabled, pluginPattern);

		StateService.getInstance().loadState(newFilterState);
		super.doOKAction();
	}
}

