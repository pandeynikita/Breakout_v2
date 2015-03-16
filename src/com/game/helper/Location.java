package com.game.helper;

public class Location {
	int xPos, yPos;

	Location() {
		xPos = yPos = 0;
	}

	public Location(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Location(Location location) {
		this.xPos = location.xPos;
		this.yPos = location.yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void add(int xPos, int yPos) {
		this.xPos += xPos;
		this.yPos += yPos;
	}

	@Override
	public String toString() {
		return "Location [xPos=" + xPos + ", yPos=" + yPos + "]";
	}
}
