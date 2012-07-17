package com.multistage.correlations.gui;

import java.awt.Container;
import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.algo.test.ClusterImplement;
import com.algo.test.ClusterImplementCustom;
import com.algo.test.URLConnectionCustom;
import com.algo.test.URLConnectionReader;
import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.utils.ResourceLoader;

/**
 * Show points in thread
 * 
 * @author H.Geissler 18 May 2011 update plot showing centers and seeds
 */

public class ThreadRefresh implements Runnable {
	
	private Thread t = null;

	private int whattoshow;
	private MainFrame frame;

	ThreadRefresh(int s1,MainFrame fm) {

		whattoshow = s1;
		frame=fm;

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

	
	
		if (whattoshow==0) 	System.out.println("do:"+whattoshow);//SetEnv.PLOT.drawPoints();
		if (whattoshow==1) 	System.out.println("do:"+whattoshow);
		if (whattoshow==2)  System.out.println("init:"+whattoshow);
     

		
		if(SetEnv.recalc) {
			SetEnv.PLOT.clearData();
			SetEnv.DESC+=SetEnv.SKIP[0]+"\n"+SetEnv.SKIP[1]+"\n"+SetEnv.SKIP[2];
			frame.m_ClControl.go();
			SetEnv.recalc = false;
			
			
		}
		
		if(SetEnv.RAW.toString()!=""){
			try {
				
			
				
				Infox info = new Infox();
	    	   	info.setVisible(true);
	    	   	System.out.println(SetEnv.RAW.toString());
				ArrayList symbols = new ArrayList();
				String[] s = SetEnv.RAW.toString().split(",");
				for(String n:s){
					
					symbols.add(n.toString());
				}

				
		        URLConnectionCustom urlConn = new URLConnectionCustom();
				if(!SetEnv.cached){
					urlConn.prepareAll(symbols);
				}
				HashMap dataFinance = urlConn.exec();
				 ClusterImplementCustom cluster = new ClusterImplementCustom(dataFinance);
	               cluster.prepareMain();
	               System.out.println("Recalc");
	               System.out.println(ResourceLoader.userTable.getProperty("name"));
	            
	            //SetEnv.SYMBOLS=new String[0];
				SetEnv.RAW="";
				SetEnv.recalc = true;
				File sfile = new File("data","userOutFileDefault.msc");
	    		sfile.delete();
	    		info.setVisible(false);

				
	    		
	    		
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.err.println("scope:"+SetEnv.getScope());
		frame.repaint();
		
		
		}
		


} // end Thread1

