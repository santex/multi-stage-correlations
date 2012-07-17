package com.messingarround;

import java.io.File;
import java.io.IOException;

import com.algo.test.FileIO;
import com.multistage.correlations.clcontrol.Global;
import com.multistage.correlations.clcontrol.SummaryPad;
import com.multistage.correlations.gui.SetEnv;

public class SSH {
	  public static void main(String args[]) {
	  Runtime r = Runtime.getRuntime();
	  Process p = null;
	  //if(args==null){
		  args=new String[1];
		  args[0]="out.png";
		  
	  //}
	  try {
		FileIO.stringToFile(Global.summary.trim(),"today.html");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  String cmd[] = { "scp",System.getProperty("user.home")+File.separator+"mscorrelation"+File.separator+args[0],"root@108.59.253.25:/var/www/vhosts/algoservice.com/httpdocs/qt/images/"};
	  try {
	      p = r.exec(cmd);
	      
	      String  cmd1[] = { "scp",System.getProperty("user.home")+File.separator+"mscorrelation"+File.separator+"today.html","root@108.59.253.25:/var/www/vhosts/algoservice.com/httpdocs/"};
	      
	      p = r.exec(cmd1);
	      
	  } catch (Exception e) {
	      System.out.println("error executing " + cmd[0]);
	  }
	  }
	}

