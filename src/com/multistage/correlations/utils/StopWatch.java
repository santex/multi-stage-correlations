package com.multistage.correlations.utils;

/**
 * The StopWatch class represents a very simple stop watch that can be used to
 * time events.
 */
public class StopWatch {

	private long startTime;

	private long stopTime;

	/**
	 * Construct a new StopWatch object.
	 */
	public StopWatch() {
	}

	/**
	 * Start this StopWatch object. Starting a StopWatch that was already
	 * running resets the watch.
	 */
	public void start() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Stop this StopWatch object.
	 */
	public void stop() {
		stopTime = System.currentTimeMillis();
	}

	/**
	 * Get the time recorded by this StopWatch object. The value returned if
	 * getTime is called before this StopWatch has been stopped is meaningless.
	 */
	public long getTime() {
		return stopTime - startTime;
	}
}
