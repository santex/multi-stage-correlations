package com.algo.test;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;



public class URLConnectionReader {
	public static String[] items = new String[300];
	public static ArrayList listS=new ArrayList();
	public static String columns = "st1oml1vp2a2j6k5c1baj1m6m8r5e9e7s7";
	//st1oml1vp2a2j6k5c1baj1
	public static String baseUrl = "http://finance.yahoo.com/d/quotes.csv?f=%s&s=%s";
	HashMap map = new HashMap();
	public static String label = "";
	public static StringBuffer  biff = new StringBuffer(); 
    public static String syms = "";
    public static HashMap lineHash = new HashMap();
    public static ArrayList sets  = new ArrayList();
    public static CSVSimple csv;
    public static ArrayList symbols;
    
    public static void prepareAll(ArrayList alternativeSymbols){
    	
    	
    	if(alternativeSymbols==null){
    		csv = new CSVSimple();
    		symbols = csv.exec();
    	}else{
    		
    		if(alternativeSymbols.size()>0){
    			symbols = alternativeSymbols;
    		}
    	}
            
        int ix=0;
        int allc=1;
        syms="";
        for (int i = 0; i < symbols.size(); i++) {
        	syms += symbols.get(i)+"+"; 

        	if(ix==190 || symbols.size()==allc){
        		sets.add(syms);
        		ix=0;        		
        		items=syms.split("\\+");
        		try {
        			getFile();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		syms="";
        	}
        	
        	ix++;
        	allc++;
        }
        
        for (int i = 0; i < sets.size(); i++) {
        	syms += sets.get(i); 
        }
        
        if(syms.contains("++")){
        	System.out.println("hallo");
        }
        syms.replaceAll("\\+\\+","\\+");
        items=syms.split("\\+");
        try {
			FileIO.stringToFile(biff.toString(),"data/userOutFileDefault.msc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static void main(String argv[]) {
    	
    
    }
    
    

    public static void getFile() throws Exception {
    	
    	
    	if(syms!=""){
        URL yahoo = new URL(String.format(baseUrl,columns,syms));
        URLConnection yc = yahoo.openConnection();
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
    
    	
    	in = FileIO.openFile("data/userOutFileDefault.msc");
    	
    	String[] line;
        String inputLine;
        biff=new StringBuffer();
        lineHash=new HashMap();
		while ((inputLine = in.readLine()) != null) {
        	
        	
			if(!inputLine.toLowerCase().contains("java.lang")){
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
            
        	//System.out.print(line[14]+" "+line[15]);
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
        		
        	if(line[0].trim()!=""){// && d[3]>=.0d  && d[4]>=5.0d
        	lineHash.put(line[0].toString(),d);
            biff.append(line);
        	}
            
        	}}
        }
        }
        in.close();
    	
        
        return lineHash;
    }
    

    public void setSymbolsLabel(String label){
    	
    	this.label=label.replaceAll(" ","");
    	
    }
    public String getSymbolsLabel(){
    	return this.label;
    }

    public String[] getItems(){

        for (int i = 0; i < sets.size(); i++) {
        	syms += sets.get(i); 
        }
        syms.replaceAll("\\+\\+","\\+");
    	return items;
    }
    public  void setItems(String[] initems){
    	URLConnectionReader.items=initems;
    }

}
