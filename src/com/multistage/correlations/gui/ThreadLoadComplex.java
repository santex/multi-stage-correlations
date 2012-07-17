package com.multistage.correlations.gui;

/**
 * File loader
 * 
 * @author H.Geissler 18 May 2011
 */

class ThreadLoadScope implements Runnable {

	private Thread t = null;

	static String userhome = System.getProperty("user.home");

	private String nfile = userhome+"/mscorrelation/";

	ThreadLoadScope(String s1) {

		nfile = s1;

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

		t = new Thread(this, nfile);
		t.start();

	}

	public void Stop() {
		t = null;
	}

	// runs k-means

	public void run() {

		SetEnv.Load(nfile); // read input
		SetEnv.JboxX = 0;
		SetEnv.JboxY = 1;

	}

} // end Thread1

