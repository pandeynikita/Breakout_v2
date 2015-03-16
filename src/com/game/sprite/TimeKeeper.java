package com.game.sprite;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.game.helper.Location;
import com.game.time.TimeHistory;
import com.game.time.TimeObservable;
import com.game.time.TimeObserver;

/**
 * Keeps track of Timer and responsible for updating its @TimeObserver with new
 * timer.
 */
public class TimeKeeper implements TimeObservable, Sprite {

	private long difference;
	private long pauseTimeDiff = 0;
	private Date now;

	private LinkedList<TimeHistory> history;

	@Override
	public void deRegister(TimeObserver timeObserver) {
		observers.remove(timeObserver);
	}

	@Override
	public void register(TimeObserver timeObserver) {
		observers.add(timeObserver);
	}

	/*
	 * Helper method to notify all observers regarding time change.
	 * 
	 * @param difference
	 */
	private void notifyObservers(TimeHistory timehistory) {
		for (TimeObserver timeObserver : observers) {
			timeObserver.ticktock(timehistory);
		}
	}

	public void resetTime() {
		baseDate = new Date();
	}

	@Override
	public void tick() {
		now = new Date();
		TimeHistory timehistory = new TimeHistory();
		difference = now.getTime() - baseDate.getTime() - pauseTimeDiff;
		long seconds = difference / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		minutes = minutes % 60;
		seconds = seconds % 60;
		timehistory.setHours(hours);
		timehistory.setMinutes(minutes);
		timehistory.setSeconds(seconds);
		history.add(timehistory);
		notifyObservers(timehistory);
	}

	public void pauseTime() {
		now = new Date();
		pauseTimeDiff = now.getTime() - baseDate.getTime() - difference;
	}

	public TimeKeeper() {
		resetTime();
		observers = new ArrayList<>();
		this.history = new LinkedList<>();

	}

	// Keeps track of base time for timer reference.
	private Date baseDate;
	// List of observers that are notified with every timer update.
	private List<TimeObserver> observers;

	@Override
	public void draw(Graphics2D g) {
	
	}

	@Override
	public void undo() {
		if (history.size() > 0)
			notifyObservers(history.removeLast());

	}

	@Override
	public void replay(int stage) {
		if (history.size() > 0)
			notifyObservers(history.get(stage));

	}

	@Override
	public void update(Location location) {
		//clock is updated using tick method

	}

	@Override
	public int getXPos() {
	
		return 0;
	}

	@Override
	public void setXPos(int xPos) {
	
		
	}

	@Override
	public int getYPos() {
	
		return 0;
	}

	@Override
	public void setYPos(int yPos) {
	
		
	}

}
