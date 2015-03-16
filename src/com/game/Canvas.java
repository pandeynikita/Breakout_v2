package com.game;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.game.command.Move;
import com.game.command.Replay;
import com.game.command.Tick;
import com.game.command.Undo;
import com.game.helper.Constants;
import com.game.helper.Location;
import com.game.helper.Sound;
import com.game.save.Savable;
import com.game.sprite.Ball;
import com.game.sprite.Brick;
import com.game.sprite.Paddle;
import com.game.sprite.TimeKeeper;
import com.game.time.Clock;
import java.awt.Event;
import java.io.*;
import sun.audio.*;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements ActionListener, KeyListener,
		Runnable, Constants, Savable {

	public Clock clock = new Clock();

	private int index = 0;
	private BufferedImage image;
	private Graphics2D bufferedGraphics;

	public static FlowLayout flowLayout = new FlowLayout();
	public static BorderLayout borderLayout = new BorderLayout();

	public TimeKeeper timekeeper = new TimeKeeper();
	public Paddle paddle;
	public Ball ball;
	ArrayList<ArrayList<Brick>> brickArray;

	private Sound sound = new Sound();

	/* JButton startAndStopButton = new StartAndStopButton(); */
	/*
	 * JButton pauseButton = new PauseButton();
	 * 
	 * JButton replay = new JButton("Replay"); JButton undo = new
	 * JButton("Undo");
	 */

	Thread thread = new Thread(this);
	private boolean gameNotStopped = true;
	private Object gameNotStoppedLock = new Object();

	private boolean isPaused = false;
	private Object isPausedLock = new Object();

	private boolean isReplayEnabled = false;
	private Object isReplayEnabledLock = new Object();

	private boolean isGameNotStopped() {
		synchronized (gameNotStoppedLock) {
			return gameNotStopped;
		}
	}

	private void setGameNotStopped(boolean gameNotStopped) {
		synchronized (gameNotStoppedLock) {
			this.gameNotStopped = gameNotStopped;
		}
	}

	public boolean isPaused() {
		synchronized (isPausedLock) {
			return isPaused;
		}
	}

	public void setPaused(boolean paused) {
		synchronized (isPausedLock) {
			this.isPaused = paused;
		}
	}

	/**
	 * Prepares the screen, centers the paddle and the ball. The ball will be
	 * located in the center of the paddle, and the paddle will be located on
	 * the center of the screen Sunde The Bricks are displayed in columns across
	 * the screen with the screen being split based on the width of an
	 * individual Brick. Each Brick is stored in a temporary ArrayList, which is
	 * added to the classes ArrayList which contains all of the Bricks.
	 */
	public Canvas() {
		super();
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = image.createGraphics();

		init();

		/*
		 * this.add(startAndStopButton); this.add(pauseButton); this.add(undo);
		 * this.add(replay);
		 */
		this.validate();

	}

	private void init() {
		timekeeper = new TimeKeeper();
		timekeeper.register(clock);
		paddle = new Paddle((WINDOW_WIDTH / 2) - (Paddle.PADDLE_WIDTH / 2));
		ball = new Ball(
				((paddle.getXPos() + (Paddle.PADDLE_WIDTH / 2)) - (Ball.BALL_DIAMETER / 2)),
				(Paddle.PADDLE_Y - (Ball.BALL_DIAMETER + 10)), -5, -5);
		index = 0;

		brickArray = new ArrayList<ArrayList<Brick>>();

		for (int i = 0; i < 2; ++i) {
			ArrayList<Brick> temp = new ArrayList<Brick>();

			for (int j = 0; j < NO_OF_BRICKS; ++j) {
				Brick tempBrick = new Brick((j * Brick.BRICK_WIDTH),
						((i + 2) * Brick.BRICK_HEIGHT));
				temp.add(tempBrick);
			}
			brickArray.add(temp);

		}

		// addMouseMotionListener(this);
		addKeyListener(this);
		requestFocus();

		/*
		 * undo.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) {
		 * setPaused(true); Undo undoBall = new Undo(ball); Undo undoTick = new
		 * Undo(timekeeper); undoBall.execute(); undoTick.execute(); Undo
		 * undoPaddle = new Undo(paddle); undoPaddle.execute();
		 * 
		 * for (ArrayList<Brick> bricks : brickArray) { for (Brick brick :
		 * bricks) { Undo brickUndo = new Undo(brick); brickUndo.execute(); } }
		 * 
		 * repaint(); } });
		 */

		/*
		 * replay.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * setPaused(true); setReplayEnabled(true); } });
		 */

	}

	public void startGame() {
		if (thread != null) {
			thread = new Thread(this);
		}
		init();
		this.setGameNotStopped(true);
		thread.start();
	}

	public void stopGame() {
		this.setGameNotStopped(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * Checks for any collisions, if the ball hits the upper wall, or the side
	 * walls it changes direction. If the ball goes below the paddle, the
	 * position of the ball gets reset and the player loses a life
	 */
	private void checkCollisions() {
		if (paddle.hitPaddle(ball)) {
			sound.playAudio();
			ball.setySpeed(ball.getySpeed() * -1);
			return;
		}
		// first check if ball hit any walls
		if (ball.checkRightWallCollisions(ball)) {
			sound.playAudio();
			ball.setxSpeed(ball.getxSpeed() * -1);
		}
		if (ball.getYPos() > (Paddle.PADDLE_Y + Paddle.PADDLE_HEIGHT + 10)) {
			resetBall();
		}
		if (ball.checkLeftWallCollisions(ball)) {
			sound.playAudio();
			ball.setySpeed(ball.getySpeed() * -1);
		}

		// next handle collisions between Bricks

		for (ArrayList<Brick> bricks : brickArray) {
			for (Brick brick : bricks) {
				if (brick.hitBy(ball)) {
					sound.playAudio();
					brick.makeInvisible();
				}
			}
		}
	}

	/**
	 * Sets the balls position to approximately the center of the screen, and
	 * deducts a point from the user. If necessary, ends the game
	 */
	private void resetBall() {
		if (gameOver()) {
			this.stopGame();
			return;
		}
		ball.setXPos(WINDOW_WIDTH / 2);
		ball.setySpeed((WINDOW_HEIGHT / 2) + 80);
		paddle.setLives(paddle.getLives() - 1);
	}

	private boolean gameOver() {
		if (paddle.getLives() <= 1) {
			return true;
		}
		return false;
	}

	/**
	 * Draws the screen for the game, first sets the screen up (clears it) and
	 * then it begins by setting the entire screen to be white. Finally it draws
	 * all of the Bricks, the players paddle, and the ball on the screen
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		bufferedGraphics.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		bufferedGraphics.setColor(Color.WHITE);
		bufferedGraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		paddle.draw(bufferedGraphics);
		ball.draw(bufferedGraphics);

		for (ArrayList<Brick> row : brickArray) {
			for (Brick b : row) {
				if (b.isVisible()) {
					b.draw(bufferedGraphics);
				}
			}
		}

		if (gameOver() && ball.getYPos() >= paddle.PADDLE_Y + 10) {
			bufferedGraphics.setColor(Color.black);
			bufferedGraphics.setFont(GAME_END_ANNOUNCEMENT_FONT);
			bufferedGraphics.drawString("Game Over!", (WINDOW_WIDTH / 2) - 85,
					(WINDOW_HEIGHT / 2));

		}
		if (empty()) {
			bufferedGraphics.setColor(Color.black);
			bufferedGraphics.setFont(GAME_END_ANNOUNCEMENT_FONT);
			bufferedGraphics.drawString("You won!", (WINDOW_WIDTH / 2) - 85,
					(WINDOW_HEIGHT / 2));
		}
		g.drawImage(image, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
	}

	private boolean empty() {
		for (ArrayList<Brick> bricks : brickArray) {
			for (Brick brick : bricks) {
				if (brick.isVisible()) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * @Override public void mouseMoved(MouseEvent e) { Location location = new
	 * Location(e.getX(), 0); Move move = new Move(paddle, location);
	 * move.execute(); }
	 * 
	 * @Override public void mouseDragged(MouseEvent e) {
	 * 
	 * }
	 */

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Canvas c = new Canvas();
		ButtonPanel buttonPanel = new ButtonPanel(c);
		frame.add(c.clock, BorderLayout.WEST);
		frame.add(c, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void run() {
		while (this.isGameNotStopped()) {

			try {

				if (this.isPaused() == false) {
					checkCollisions();
					Tick tickMove = new Tick(timekeeper);
					tickMove.execute();

					Move ballMove = new Move(ball, null);
					ballMove.execute();

					Location location = new Location(paddle.getXPos(), 0);
					Move paddleMove = new Move(paddle, location);
					paddleMove.execute();

					repaint();
				} else if (isReplayEnabled()) {
					try {

						Replay replayBall = new Replay(ball, index);
						replayBall.execute();

						Replay replayTick = new Replay(timekeeper, index);
						replayTick.execute();

						Replay replayPaddle = new Replay(paddle, index);
						replayPaddle.execute();

						for (ArrayList<Brick> bricks : brickArray) {
							for (Brick brick : bricks) {
								Replay replayBrick = new Replay(brick, index);
								replayBrick.execute();
							}
						}

						index++;
						repaint();
					} catch (Exception e) {
						setReplayEnabled(false);
						index = 0;
					}
				}

				if (this.isPaused() == true) {
					timekeeper.pauseTime();
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isReplayEnabled() {
		synchronized (isReplayEnabledLock) {
			return isReplayEnabled;
		}

	}

	public void setReplayEnabled(boolean isReplayEnabled) {
		synchronized (isReplayEnabledLock) {
			this.isReplayEnabled = isReplayEnabled;
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keycode = arg0.getKeyCode();

		switch (keycode) {
		case KeyEvent.VK_LEFT:
			Location location = new Location(
					(paddle.getXPos() - Constants.KEY_SHIFT), 0);
			Move move = new Move(paddle, location);
			move.execute();
			break;
		case KeyEvent.VK_RIGHT:
			Location location_shift = new Location(
					(paddle.getXPos() + Constants.KEY_SHIFT), 0);
			Move move_shift = new Move(paddle, location_shift);
			move_shift.execute();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {

	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}
}
