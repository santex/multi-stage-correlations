package com.multistage.correlations.gui;

import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class UpdateTimer extends TimerTask {

	
	Timer timer = new Timer();
	  public interface URLUpdate {
	    public void isAlive(boolean b);
	  }

	  URL url;

	  URLUpdate updater;
	  ThreadRefresh up;
	  public UpdateTimer(URL url,ThreadRefresh up) {
	    //this(url, null);

		  this.up=up;
	  }


	public void run() {
	    if (System.currentTimeMillis() - scheduledExecutionTime() > 10000) {
	      // Let the next task do it
	      return;
	    }
	    try {
	    	

		up.run();
		
	      if (updater != null)
	        updater.isAlive(true);
	    } catch (Exception e) {
	      if (updater != null)
	        updater.isAlive(false);
	    }
	  }




	    public static void main(String[] a){
	    	try {
			    //UpdateTimer task = new UpdateTimer(new URL("http://www.google.com"));
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}


	           
	         