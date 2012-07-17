package com.algo.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import com.multistage.correlations.gui.SetEnv;



public class URLConnectionWatchlist {
	//st1oml1vp2a2j6k5c1baj1
	public static String columns = "st1oml1vp2a2j6k5c1baj1m6m8r5e9e7s7";
	public static String baseUrl = "http://marketdata.nyse.com/JTic?app=QUOT&id=--SYMBOLS--&rf=JS&type=NYQUOTE";
	public static String label = "Default";
	public static String[] symbolsx = "APAC,ARIA,AXP,BAS,BIDU,BPI,CAT,CHA,COP,DCM,DD,ELN,FCX,GLBC,GLNG,GMCR,GSVC,HDB,HK,JVA,KFT,KONA,MMM,MPEL,NVO,OXY,REDF,SLB,SODA,SUG,UTX,VHI,VRUS,ZAGG".split(",");
	public static String syms;
    public static CSVSimple csv;
    public static StringBuffer biff = new StringBuffer();
    public static ArrayList symbols  = new ArrayList();
    public static HashMap lineHash  = new HashMap();
    private static BufferedReader br;
    

	public static void prepareAll(){
    	
    	
    	
    	try {
		//	FileIO.stringToFile(biff.toString(),String.format("lib/outFile_%s",label));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static void main(String argv[]) {
    	
    	String syms = "";
    	if(argv.length>=1){
    		
    	}else{
    		argv=symbolsx;
    	}
    		
    		if(argv[0]!=null){
    
    			 
    			try {
    			
    				for(String arg:argv){
    					syms+=arg+",";
    				}	
					   			
    			

        			baseUrl  = baseUrl.replace("--SYMBOLS--",syms);
        			
        			URL yahoo = new URL(baseUrl);
        	        URLConnection yc = yahoo.openConnection();
        	        yc.setReadTimeout(99000);
        	        BufferedReader in = new BufferedReader(
        	                                new InputStreamReader(
        	                                yc.getInputStream()));
        	        String inputLine;
        	         
        	        String[] line = new String[40];
        		    
        		        while ((inputLine = in.readLine()) != null) {
        		        	
        		        	
        		        	String[] row = inputLine.split("\",\"");
        		        	try{
        		        	if(!row[6].isEmpty() && !row[14].isEmpty()) {
        		        		System.out.println(row[6]+" "+row[10]+" "+row[36]+" "+row[14]);
        		        		biff.append(inputLine).append("\n");
        		        	}
        		        	}catch(ArrayIndexOutOfBoundsException e){
        		        		
        		        	}
        		        }
        		        
        		        
        		      System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"+biff.toString());
        		        
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    		    
    		}
    		
    	
    	
    }
    
    

    public static void getFile() throws Exception {
    	
    	
    	if(syms!=""){
        URL yahoo = new URL(String.format(baseUrl,columns,syms));
        URLConnection yc = yahoo.openConnection();
        yc.setReadTimeout(99000);
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
         
        String[] line = new String[20];
	    
	        while ((inputLine = in.readLine()) != null) {
	        	biff.append(inputLine).append("\n");
	        	
	        }
    	}
    }
            

	

}



