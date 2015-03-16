package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class StartAndStopButton extends JButton {

	private static final long serialVersionUID = 1L;
	private final String STAR_BUTTON_TEXT = "Start";
	private final String STOP_BUTTON_TEXT = "Stop";

	private Canvas canvas = null;

	public StartAndStopButton() {
		super();
		this.setText(STAR_BUTTON_TEXT);
		this.addActionListener(new StartAndStopActionListener());
	}

	public StartAndStopButton(Canvas canvas) {
		this();
		this.canvas = canvas;
	}

	public void toggle() {
		if (this.getText().equals(STAR_BUTTON_TEXT)) {
			this.setText(STOP_BUTTON_TEXT);
			canvas.startGame();
		} else {
			this.setText(STAR_BUTTON_TEXT);
			canvas.stopGame();
		}
	}

	class StartAndStopActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			toggle();
		}
	}

}
