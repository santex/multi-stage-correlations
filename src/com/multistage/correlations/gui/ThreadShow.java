package com.multistage.correlations.gui;

/**
 * Show points in thread
 * 
 * @author H.Geissler 18 May 2011 update plot showing centers and seeds
 */

public class ThreadShow implements Runnable {

	private Thread t = null;

	private int whattoshow;

	public ThreadShow(int s1) {

		whattoshow = s1;

	}

	public boolean Alive() {

		boolean tt = false;
		if (t != null) {
			if (t.isAlive())
				tt = true;
		}
		return tt;
	}

	public boolean Joint() {

		boolean tt = false;
		try {
			t.join();
			return true; // finished

		} catch (InterruptedException e) {
			// Thread was interrupted
		}

		return tt;
	}

	public void Start() {

		t = new Thread(this, "show points");
		t.start();

	}

	public void Stop() {
		t = null;
	}


	public void run() {

	
		if (whattoshow==0) 	SetEnv.PLOT.drawPoints();
		if (whattoshow==1) 	SetEnv.PLOT.drawContour();
		
     
		
		}
		


} // end Thread1

