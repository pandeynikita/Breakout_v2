package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class ReplayButton extends JButton {

	private static final long serialVersionUID = 1L;
	private final String REPLAY_BUTTON_TEXT = "REPLAY";
	
	private Canvas canvas;

	public ReplayButton(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.setText(REPLAY_BUTTON_TEXT);
		this.addActionListener(new ReplayButtonActionListener());
	}
	
	/*public void setPaused(boolean paused) {
		synchronized (isPausedLock) {
			this.isPaused = paused;
		}
	}

	public void setReplayEnabled(boolean isReplayEnabled) {
		synchronized (isReplayEnabledLock) {
			this.isReplayEnabled = isReplayEnabled;
		}

	}*/
	
	class ReplayButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.setPaused(true);
			canvas.setReplayEnabled(true);
			
			
		}
	}

}
