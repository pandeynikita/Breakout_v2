package com.game.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import com.game.Canvas;
import com.game.helper.Constants;
import com.game.helper.Location;
import com.game.helper.Sound;

public class Paddle implements Sprite, Constants {

	private Location location;
	private int lives;
	private LinkedList<Location> history;
	private LinkedList<Boolean> soundHistory;
	private Sound sound = new Sound();

	public Paddle(int xPos) {
		this.location = new Location(xPos, PADDLE_Y);
		history = new LinkedList<>();
		soundHistory = new LinkedList<>();
		lives = 1;
	}

	public void setXPos(int xPos) {

		this.location.setxPos(xPos);

		if (xPos < 0) // make sure its not negative value
		{
			xPos = 0;
			this.location.setxPos(xPos);
		}
		if (xPos > (Canvas.WINDOW_WIDTH - PADDLE_WIDTH)) // ensure there is
															// enough
		// space for
		// paddle to be drawn without
		// exceeding canvas boundary
		{
			xPos = (Canvas.WINDOW_WIDTH - PADDLE_WIDTH);
			this.location.setxPos(xPos);
		}

	}

	public int getXPos() {
		return this.location.getxPos();
	}

	public void draw(Graphics2D g) {
		g.setColor(PADDLE_COLOR);
		g.fillRect(location.getxPos(), location.getyPos(), PADDLE_WIDTH,
				PADDLE_HEIGHT);
		g.setColor(Color.gray);
		g.drawRect(location.getxPos(), location.getyPos(), PADDLE_WIDTH,
				PADDLE_HEIGHT);
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean hitPaddle(Ball b) {
		if (b.getXPos() <= getXPos() + (PADDLE_WIDTH + 15)) {
			if (b.getXPos() >= getXPos() - 10) {
				if ((b.getYPos() + (Ball.BALL_DIAMETER - 1)) >= (PADDLE_Y)) {
					if ((b.getYPos() + (Ball.BALL_DIAMETER - 1)) <= (PADDLE_Y + (PADDLE_HEIGHT - 5))) {

						soundHistory.add(true);

						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void update(Location location) {
		setXPos(location.getxPos());
		history.add(new Location(this.location));
		soundHistory.add(false);

	}

	@Override
	public void undo() {
		if (soundHistory.size() > 0) {

			if (soundHistory.removeLast() == true)
				sound.playAudio();
		}
		if (history.size() > 0) {
			this.location = history.removeLast();
			setXPos(location.getxPos());
		}

	}

	@Override
	public void replay(int stage) {
		if (soundHistory.size() > stage) {

			if (soundHistory.get(stage) == true)
				sound.playAudio();
		}
		if (history.size() > stage) {
			location = history.get(stage);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public int getYPos() {
		return 0;
	}

	@Override
	public void setYPos(int yPos) {
		// Not allowed
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
