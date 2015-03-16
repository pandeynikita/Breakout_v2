package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class PauseButton extends JButton {

	private static final long serialVersionUID = 1L;
	private final String PAUSE_BUTTON_TEXT = "Pause";
	private final String CONTINUE_BUTTON_TEXT = "Play";

	private Canvas canvas;

	public PauseButton(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.setText(PAUSE_BUTTON_TEXT);
		this.addActionListener(new PauseButtonActionListener());
	}

	private void toggle() {
		if (this.getText().equals(PAUSE_BUTTON_TEXT)) {
			this.setText(CONTINUE_BUTTON_TEXT);
			canvas.setPaused(true);
		} else {
			this.setText(PAUSE_BUTTON_TEXT);
			canvas.setPaused(false);
		}
	}

	class PauseButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			toggle();
		}
	}

}
