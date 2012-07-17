package com.multistage.correlations.clcontrol;

/**
 * 
 * @author H.Geissler 18 May 2011 update plot showing centers and seeds
 */

class Thread1 implements Runnable {

	private Thread t = null;

	private String mess;

	Thread1(String s1) {

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

	// runs k-means

	public void run() {

		if (Global.ShowCenters) {
			Display.Centers();
		} else {
			Display.RemoveCenters();
		}

		if (Global.ShowSeeds) {
			Display.Seeds();
		} else {
			Display.RemoveSeeds();
		}
	}

} // end Thread1

