package com.game.sprite;

import java.awt.Graphics2D;

import com.game.helper.Location;

public interface Sprite {
	public void draw(Graphics2D g);

	public void update(Location location);
	
	public void tick();

	public void undo();

	public void replay(int stage);

	public int getXPos();

	public void setXPos(int xPos);

	public int getYPos();

	public void setYPos(int yPos);

}