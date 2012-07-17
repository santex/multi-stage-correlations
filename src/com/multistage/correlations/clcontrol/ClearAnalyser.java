package com.multistage.correlations.clcontrol;

import com.multistage.correlations.gui.*;

/**
 * Clear analyser
 * 
 * @author H.Geissler 18 May 2011
 * 
 */
class ClearAnalyser implements Runnable {

	private Thread t = null;

	private String mess;

	ClearAnalyser(String s1) {
		mess = s1;
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

		t = new Thread(this, mess);
		t.start();

	}

	public void Stop() {
		t = null;
	}

	// clear
	public void run() {

		SetEnv.PLOT.drawPoints();
	}

} // end Thread1

