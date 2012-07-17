package com.multistage.correlations.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.algo.test.SimpleDialog;
import com.algo.test.URLConnectionReader;
import com.algo.test.focus;
import com.multistage.correlations.gui.NewData;
import com.multistage.correlations.gui.ProgressBarDemo;
import com.multistage.correlations.gui.SetEnv;


public class BaseUtil {
	
	
    protected static final String TAG = "BaseUtil";
	private static double MEMORY_BUFFER_LIMIT_FOR_RESTART;
    private ArrayList<String> directoryEntries = new ArrayList<String>();
    public static boolean logging=true;
	public static String md5(String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();
	        
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	




        	
	public synchronized static void progress(int i) {
		 
		
        new Thread(new Runnable() {
            public void run() {
                try {
                	//new ProgressBarDemo();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        
		

		
	}


	public synchronized static void focus(int i) {
		 
		
        new Thread(new Runnable() {
            public void run() {
                try {
                	///new focus();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        
		

		
	}


	public static String toComaString(List<String> inlist) {
		String ret="";
        for (String s : inlist) {
        	if(!ret.contains(s)){
        	ret += s+",";
        	}
        }
        return ret;
	}






	public static void changeSymbols(String string) {
		
		System.out.println(string);
		SetEnv.MARKET=string;
		
        new Thread(new Runnable() {
            public void run() {
                try {
                	new NewData().main(new String[]{SetEnv.MARKET});
                	//URLConnectionReader url = new URLConnectionReader();
                	//url.prepareAll(new ArrayList());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();		
	}

}