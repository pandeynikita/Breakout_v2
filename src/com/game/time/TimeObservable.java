package com.game.time;

/**
 * Interface specification that allows @TimeObserver to register or de-register
 * for time updates.
 */
public interface TimeObservable {
	/*
	 * Allows @TimeObserver to register for time updates.
	 */
	public void register(TimeObserver timeObserver);

	/*
	 * Allows @TimeObserver to de-register for time updates.
	 */
	public void deRegister(TimeObserver timeObserver);
}