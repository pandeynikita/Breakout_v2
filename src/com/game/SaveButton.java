package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.game.command.Save;
import com.game.save.Savable;

public class SaveButton extends JButton {
	private static final long serialVersionUID = 1L;
	private final String SAVE_BUTTON_TEXT = "SAVE";
	
	private Canvas canvas;
	private Savable savable;

	public SaveButton(Canvas canvas,Savable savable) {
		super();
		this.canvas = canvas;
		this.savable = savable;
		this.setText(SAVE_BUTTON_TEXT);
		this.addActionListener(new SaveButtonActionListener());
	}
	
	class SaveButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			canvas.stopGame();
			Save savePanel = new Save(savable);
			savePanel.execute();
		}
	}

}
