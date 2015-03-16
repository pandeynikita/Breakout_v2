package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import com.game.command.Move;
import com.game.command.Undo;
import com.game.sprite.Brick;

class UndoButton extends JButton {

	private static final long serialVersionUID = 1L;
	private final String UNDO_BUTTON_TEXT = "Undo";

	private Canvas canvas = null;

	public UndoButton(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.setText(UNDO_BUTTON_TEXT);
		this.addActionListener(new StartAndStopActionListener());
	}

	class StartAndStopActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.setPaused(true);
			Move undoBall = new Move(canvas.ball);
			Move undoTick = new Move(canvas.timekeeper);
			undoBall.unexecute();
			undoTick.unexecute();
			Move undoPaddle = new Move(canvas.paddle);
			undoPaddle.unexecute();

			for (ArrayList<Brick> bricks : canvas.brickArray) {
				for (Brick brick : bricks) {
					Move brickUndo = new Move(brick);
					brickUndo.unexecute();
				}
			}

			canvas.repaint();
		}
	}

}
