package com.game.command;

import com.game.sprite.Sprite;


public class Tick implements Command {

	private Sprite sprite;

	public Tick(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void execute() {
		sprite.tick();
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}