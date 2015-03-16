package com.game.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import com.game.helper.Constants;
import com.game.helper.Location;
import com.game.helper.Sound;

public class Brick implements Sprite, Constants {

	private Location location;
	private boolean isVisible = true;
	private LinkedList<Boolean> history;
	private LinkedList<Boolean> soundHistory;
	private Sound sound = new Sound();

	public Brick(int xPos, int yPos) {
		this.location = new Location(xPos, yPos);
		this.history = new LinkedList<>();
		this.soundHistory = new LinkedList<>();
	}

	public int getXPos() {
		return location.getxPos();
	}

	public int getYPos() {
		return location.getyPos();
	}

	public boolean hitBy(Ball b) {

		if (isVisible()) {
			boolean isHit = false;

			// first check if it hits from the bottom or top
			if (b.getXPos() <= (getXPos() + BRICK_WIDTH)
					&& b.getXPos() >= getXPos()) {
				// hit bottom
				if (b.getYPos() <= (getYPos() + BRICK_HEIGHT)
						&& b.getYPos() >= (getYPos() + (BRICK_HEIGHT / 2))) {
					b.setySpeed(b.getySpeed() * -1);

					isHit = true;
				}
				// hit top
				else if (b.getYPos() >= (getYPos() - Ball.BALL_DIAMETER)
						&& b.getYPos() < (getYPos() + (Ball.BALL_DIAMETER / 3))) {
					b.setySpeed(b.getySpeed() * -1);

					isHit = true;
				}
			}
			// determines if it from a side
			else if (b.getYPos() <= (getYPos() + BRICK_HEIGHT)
					&& b.getYPos() >= getYPos()) {
				// hit right
				if (b.getXPos() <= (getXPos() + BRICK_WIDTH)
						&& b.getXPos() > (getXPos() + (BRICK_WIDTH - (Ball.BALL_DIAMETER / 2)))) {
					b.setxSpeed(b.getxSpeed() * -1);

					isHit = true;
				}
				// hit left
				else if (b.getXPos() >= (getXPos() - Ball.BALL_DIAMETER)
						&& b.getXPos() < (getXPos() + (Ball.BALL_DIAMETER / 2))) {
					b.setxSpeed(b.getxSpeed() * -1);

					isHit = true;
				}
			}
			soundHistory.add(isHit);
			history.add(Boolean.valueOf(!isHit));
			return isHit;
		} else {
			soundHistory.add(false);
			history.add(Boolean.valueOf(isVisible));
			return isVisible;
		}
	}

	public void makeInvisible() {
		isVisible = false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public void draw(Graphics2D g) {
		if (isVisible) {
			g.setColor(Color.white);
			g.fillRect(getXPos(), getYPos(), BRICK_WIDTH, BRICK_HEIGHT);
			g.setColor(BRICK_COLOR);
			g.fillRect((getXPos() + 2), (getYPos() + 2), BRICK_WIDTH - 4,
					BRICK_HEIGHT - 4);
		}
	}

	@Override
	public void update(Location location) {
		// Brick doesn't move.
	}

	@Override
	public void undo() {
		if (soundHistory.size() > 0) {

			if (soundHistory.removeLast() == true)
				sound.playAudio();
		}
		if (history.size() > 0) {
			isVisible = history.removeLast();
		}
	}

	@Override
	public void replay(int stage) {
		if (soundHistory.size() > stage) {

			if (soundHistory.get(stage) == true)
				sound.playAudio();
		}
		isVisible = history.get(stage);
	}

	@Override
	public void setXPos(int xPos) {
		// set are not allowed
	}

	@Override
	public void setYPos(int yPos) {
		// set are not allowed
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}