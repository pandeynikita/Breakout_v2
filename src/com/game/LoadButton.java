package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


import com.game.command.Save;
import com.game.save.Savable;

public class LoadButton extends JButton {
	private static final long serialVersionUID = 1L;
	private final String LOAD_BUTTON_TEXT = "Load";
	
	private Canvas canvas;
	private Savable savable;

	public LoadButton(Canvas canvas,Savable savable) {
		super();
		this.canvas = canvas;
		this.savable = savable;
		this.setText(LOAD_BUTTON_TEXT);
		this.addActionListener(new LoadButtonActionListener());
	}
	
	
	class LoadButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.stopGame();
			Save LoadPanel = new Save(savable);
			LoadPanel.unexecute();
			
		}
	}

}
