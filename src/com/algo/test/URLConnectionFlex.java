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



public class URLConnectionFlex {
	//st1oml1vp2a2j6k5c1baj1
	public static String columns = "st1oml1vp2a2j6k5c1baj1m6m8r5e9e7s7";
	public static String baseUrl = "http://finance.yahoo.com/d/quotes.csv?f=%s&s=%s";
	public static String label = "Default";
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
    	
    	if(argv.length==1){
    		
    		if(argv[0]!=null){
    
    			 
    			try {
    				///home/hagen/
    				br = FileIO.openFile(String.format(System.getProperty("user.home")+"mscorrelation/data/%s.%s",argv[0],SetEnv.extension));
    				
    				int count = 0;
    				String inputLine;
    				while ((inputLine = br.readLine()) != null){
    					
    					if(inputLine.contains("=")){
    							final String[] set =inputLine.split("=");
    							
    							if(inputLine.contains("symbols=")){
    								final String[] symbols = set[1].split(",");
    								lineHash.put(set[0],symbols);
    							}else{
    							
    								lineHash.put(set[0],set[1]);
    							}
    							
    							
    					}
    					
    					count++;
    						
    				}
    				
    				
    				
    				SetEnv.SYMBOLS  = (String[]) lineHash.get("symbols");
					SetEnv.MARKET = (String) lineHash.get("market");
					SetEnv.DESC = (String) lineHash.get("description");
					
					
					if(SetEnv.SYMBOLS!=null)
					SetEnv.DATALOG.append(String.format("symbol-file:%s\nnumber of symbols:%s\nmarket:%s\ndescription:%s\n",
    												 					   String.format("data/%s.%s",argv[0],SetEnv.extension),
    																	   SetEnv.SYMBOLS.length,
    																	   SetEnv.MARKET,
    																	   SetEnv.DESC));
					System.out.println(SetEnv.DATALOG.toString());
    			
    			
    			
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    		    
    			
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
        	
    public static HashMap exec() throws Exception {
    	
    	BufferedReader in;
    	
    	if(label != ""){
    		
    	}
    	
    	if(true)
    		return lineHash;
    	
    	in = FileIO.openFile(String.format("data/userOutFile%s",label));
    	
    	String[] line;
        String inputLine;
		
		while ((inputLine = in.readLine()) != null) {
        	
        	
        	System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"+inputLine);
        	
        		
        	
        	line = inputLine.replaceAll("\"","").split(",");
        
        	if(line!=null && line.length>=20){
        	if(!line[0].contains("N/A") &&
        	   !line[5].contains("N/A") &&
        	   !line[7].contains("N/A") &&
        	   !line[14].contains("N/A") &&
        	   !line[15].contains("N/A")){
        		
        	Double[] d;	
        	
        	if(true){
            
        	System.out.print(line[14]+" "+line[15]);
            /*
             * System.out.print(" "+line[0]);
             
        	
        	System.out.print(" "+line[2]);
        	System.out.print(" "+line[3]);
        	System.out.print(" "+line[4]);
        	System.out.print(" "+line[5]);
        	System.out.print(" "+line[6].replace("+","").replaceAll("%",""));
        	System.out.print(" "+line[7].replace("+","").replaceAll("%",""));
        	System.out.print(" "+line[8].replace("+","").replaceAll("%",""));
        	System.out.print(" "+);
        	*/
        	
        	String d50 = line[14].replace("+","").replaceAll("%","");
        	String d200 = line[15].replace("+","").replaceAll("%","");
        	
        	d = new Double[10];
        	
	        	//if(line[0]!="N/A"){

        	
        			d[5]=Double.parseDouble(line[4]);
    				d[0]=Double.parseDouble(line[5]);
    				d[1]=Double.parseDouble(line[7]);
    				//d[1]=d[1]/1000000;
    				d[1]=(d[1]*d[5])/1000000;
	        		d[2]=(d[0]/(d[1]/100));
	        		d[3]=Double.parseDouble(d50);
	        		d[4]=Double.parseDouble(d200);
	        		
	        		//d[4]=d[4]*d[3];
	        		
	        		
	        	//}
        	}
        		
        	if(line[0].trim()!=""){
        		lineHash.put(line[0].toString(),d);
        		
        	}
            
        	}
        }
        }
        in.close();
    	
        
        return lineHash;
    }

    

	

}



