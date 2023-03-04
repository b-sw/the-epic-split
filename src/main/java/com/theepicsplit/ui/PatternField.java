package com.theepicsplit.ui;

import javax.swing.*;
import java.awt.*;

public class PatternField extends JPanel {
	private static final String LABEL_TEXT = "Pattern:";

	private final JLabel _label = new JLabel(LABEL_TEXT);
	private final JTextField _textField = new JTextField();

	public PatternField() {
		super();
		this.add(this._label, BorderLayout.LINE_START);
		this.add(this._textField, BorderLayout.LINE_END);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	}

	public String getText() {
		return this._textField.getText();
	}

	public void setText(String text) {
		this._textField.setText(text);
	}
}
