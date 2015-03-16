package com.game.time;

/**
 * External interfaces for all types of Time Observers. Time observers typically
 * register for updates from @TimeObservables to get timing related data.
 */
public interface TimeObserver {
	/*
	 * Routine is called to update the time.
	 * 
	 * @param difference signifies number of miliseconds that has been passed
	 * since counter started.
	 */
	public void ticktock(TimeHistory timehistory);
}
