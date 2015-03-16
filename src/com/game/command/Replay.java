package com.game.command;

import com.game.sprite.Sprite;

public class Replay implements Command {

	private Sprite sprite;
	private int stage;

	public Replay(Sprite sprite, int stage) {
		this.sprite = sprite;
		this.stage = stage;
	}

	public void execute() {
		sprite.replay(stage);
	}

	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}
