package com.multistage.correlations.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.algo.test.ClusterImplement;
import com.algo.test.URLConnectionReader;
import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.utils.ResourceLoader;

public class ScopeLoad  implements Runnable {
   private HashMap dataFinance = new HashMap();
   private String scope = "";


	private Thread t = null;

	private String nfile = "";
	private Infox info;


	public void Start() {

		t = new Thread(this, nfile);
		t.start();

	}

	public void Stop() {
		t = null;
			
	}


	
	
	public void run() {
       try {
     	
   
    	    
    		//SetEnv.DATA=new DataHolder();
			//SetEnv.ClusCenter=new DataHolder();
			//SetEnv.SEED=new DataHolder();
			//SetEnv.PLOT =  new Plotter();
       	
		
    		/*
       		
       		info.setVisible(true);
       		System.out.println("opening:"+scope);
       	//	URLConnectionReader urlConn = new URLConnectionReader();
   			//urlConn.prepareAll(null);
   		//	dataFinance = urlConn.exec();
       		
          //     ClusterImplement cluster = new ClusterImplement(dataFinance);
            //   cluster.prepareMain();
               SetEnv.recalc=true;
               System.out.println("Recalc");
               System.out.println(ResourceLoader.userTable.getProperty("name"));
               
				//System.out.println(ResourceLoader.getString("action", "testvalue"));
				//System.out.println(ResourceLoader.getString("display", "testvalue"));
       		//m.frame.m_ClControl.go();
              */ 
               
   		
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       
       
   }

	public void setScope(String scope) {
		this.scope=scope;
		
	}
}
