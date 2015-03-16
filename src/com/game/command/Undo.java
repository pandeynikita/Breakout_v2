package com.game.command;

import com.game.sprite.Sprite;

public class Undo implements Command {

	private Sprite sprite;

	public Undo(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void execute() {
		sprite.undo();
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}
