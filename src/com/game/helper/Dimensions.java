package com.game.helper;

import java.awt.Color;
import java.awt.Font;

public interface Dimensions {
	/*
	 * Commons class data members declares all the constants to be used
	 * throughout the project
	 */
	static final int WINDOW_WIDTH = 720;
	static final int WINDOW_HEIGHT = 600;
	
	static final int PADDLE_Y = WINDOW_HEIGHT - 30;
	static final int PADDLE_WIDTH = 150;
	static final int PADDLE_HEIGHT = 10;
	static final Color PADDLE_COLOR = Color.black;
	static final int PADDLE_SPEED = 5;
	
	static final int BALL_DIAMETER = 16;
	static final Color BALL_COLOR = Color.black;

	static final int BRICK_WIDTH = 60;
	static final int BRICK_HEIGHT = 20;
	static final Color BRICK_COLOR = Color.GREEN;
	static final int NO_OF_BRICKS = WINDOW_WIDTH / BRICK_WIDTH;
	static final int KEY_SHIFT = 2;
	
	static final String panelCoordinates = "panelCoordinates";
	static final String buttons          = "buttons";

	static final Font GAME_END_ANNOUNCEMENT_FONT = new Font(Font.SANS_SERIF,
			Font.BOLD, 20);
}