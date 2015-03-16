package com.game.command;

import com.game.helper.Location;
import com.game.sprite.Sprite;

public class Move implements Command {

	private Sprite sprite;
	private Location location;

	public Move(Sprite sprite, Location location) {
		this.sprite = sprite;
		this.location = location;
	}

	public Move(Sprite sprite)
	{
		this.sprite =sprite;
	}
	
	@Override
	public void execute() {
		sprite.update(location);
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		sprite.undo();
	}
	
	

}
