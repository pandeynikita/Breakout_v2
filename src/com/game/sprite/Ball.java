package com.game.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import com.game.helper.Constants;
import com.game.helper.Location;
import com.game.helper.Sound;

public class Ball implements Sprite, Constants {

	private Location location;
	private int xSpeed, ySpeed;
	private LinkedList<Location> history;
	private LinkedList<Boolean> soundHistory;
	private Sound sound = new Sound();

	public Ball(int xPos, int yPos, int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.location = new Location(xPos, yPos);
		this.history = new LinkedList<>();
		this.soundHistory = new LinkedList<>();
	}

	public int getXPos() {
		return this.location.getxPos();
	}

	public void setXPos(int xPos) {
		this.location.setxPos(xPos);
	}

	public int getYPos() {
		return location.getyPos();
	}

	public void setYPos(int yPos) {
		this.location.setyPos(yPos);
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void move() {
		history.add(location);
		this.location.add(xSpeed, ySpeed);
	}

	public void draw(Graphics2D g) {

		g.setColor(BALL_COLOR);
		g.fillOval(location.getxPos(), this.location.getyPos(), BALL_DIAMETER,
				BALL_DIAMETER);
		g.setColor(Color.gray);
		g.drawOval(location.getxPos(), this.location.getyPos(), BALL_DIAMETER,
				BALL_DIAMETER);
	}

	public boolean checkRightWallCollisions(Ball ball) {
		if (ball.getXPos() >= (WINDOW_WIDTH - Ball.BALL_DIAMETER)
				|| ball.getXPos() <= 0) {
			soundHistory.add(true);
			return true;
		}

		return false;

	}

	public boolean checkLeftWallCollisions(Ball ball) {
		if (ball.getYPos() <= 0) {
			soundHistory.add(true);
			return true;
		}
		return false;
	}

	@Override
	public void update(Location location) {
		this.location.add(xSpeed, ySpeed);
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
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
