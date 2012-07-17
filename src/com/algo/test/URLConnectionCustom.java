package com.algo.test;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

///import com.multistage.correlations.gui.ScopeDialog;
import com.multistage.correlations.gui.SetEnv;
import com.multistage.correlations.gui.models.IndicatorSelectModel;



public class URLConnectionCustom {
	public static String[] items = new String[0];
	public static ArrayList listS=new ArrayList();
	

	public static String[] columns;//,"e","j1"
	public static String[][] col;
									//
									
//		
	public static String baseUrl = "http://finance.yahoo.com/d/quotes.csv?f=%s&s=%s";
	HashMap map = new HashMap();
	public static String label = "";
	public static StringBuffer  biff = new StringBuffer(); 
    public static String syms = "";
    public static HashMap lineHash = new HashMap();
    public static ArrayList sets  = new ArrayList();
    public static CSVSimple csv;
    public static ArrayList symbols;
    
    public static IndicatorSelectModel model;		
    
    
    public URLConnectionCustom(){
    	
    	
    	columns=SetEnv.col[SetEnv.matrix];
    	model = new IndicatorSelectModel();
    	symbols=new ArrayList();
    	
    	for(Object[] xv:SetEnv.names){
			 
			 if((Boolean) xv[1]){

		        	
		        	
			 System.out.print(xv[2].toString());
			 }
		 }
		
    	
    	
    	//prepareAll(symbols);
    	try {
			exec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void prepareAll(ArrayList alternativeSymbols){
    	
    	if(!SetEnv.cached){
    	
    	if(alternativeSymbols==null){
    		csv = new CSVSimple();
    		symbols = csv.exec();
    	}else{
    		
    		if(alternativeSymbols.size()>0){
    			symbols = alternativeSymbols;
    		}
    	}
    	
    	ArrayList clean = new ArrayList();
    	for (int i = 0; i < symbols.size(); i++) {
    		if(!clean.contains(symbols.get(i).toString())){
    			clean.add(symbols.get(i).toString());
    		}
    	}
    	
    	symbols.clear();
    	symbols=clean;
            
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
        
        writeBiff();
    	}

    }
    
 
    private static void writeBiff() {
    	try {
    		if(!SetEnv.cached){
    			FileIO.stringToFile(biff.toString(),"data/userOutFileDefault.msc");
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void clean() {
    	try {
    		SetEnv.DATA.clear();
    		SetEnv.init();
    		File sfile = new File("data","userOutFileDefault.msc");
    		if(sfile.delete()){
    			System.out.println(" deleted");
    		}else{
    			System.out.println("failed deletinged");
    		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    
    }
    

    public static void getFile() throws Exception {
    	
    	
    	if(!SetEnv.cached)
    	{
	    	//System.err.print(columns.toString());
	    	String req = "";
	    	for(String col:columns){
	    		req+=col;
	    	}
	    	
	    	if(syms!=""){
	    	URL yahoo = new URL(String.format(baseUrl,req,syms));
	        URLConnection yc = yahoo.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine;
	         
	        String[] line = new String[20];
	        	String check = biff.toString();
		        while ((inputLine = in.readLine()) != null) {
		        	if(!check.contains(inputLine))
		        	biff.append(inputLine).append("\n");
		        	
		        }
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
        lineHash.clear();
        biff=new StringBuffer();
        double[] d=null;
        
        
        HashMap lookup = new HashMap();
        
        for(boolean xv:SetEnv.RUNOPTIONS)
        	if(xv){
        		SetEnv.maxdim++;
        	}
        if(SetEnv.maxdim>10){
        	SetEnv.maxdim=10;
        }
        
        
        
        
        boolean skip = false;
        DATA:
		while ((inputLine = in.readLine()) != null) {
        	
        	
			if(inputLine.toUpperCase().contains("JAVA.LANG"))
				continue DATA;
			
        		
        	
        	line = inputLine.replaceAll("\"","").split(",");
        
        	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+inputLine);
        	
        	skip=false;
        	
        	//if(!inputLine.contains("N/A")){
        			
        		 d = new double[columns.length-1];
            
        		 int ix=0;
        		 for(String xd:columns){
        			 double cap =0d;
        			 if(ix>0){
        				 if(!line[ix].contains("N/A")){
	        				 
	        				 if(xd.equalsIgnoreCase("j1")){
	        					 if((line[ix].contains("M")||line[ix].contains("B")))
	        					 {
	        					 int multi = line[ix].contains("B")?1000:1;
	        					 
	        					 if(true || SetEnv.MARKETCAP!=0){
	        					 cap = Double.parseDouble(line[ix].replace("B","").replaceAll("M",""));
	        					 cap=cap*multi;
	        				     DecimalFormat df = new DecimalFormat("#");
	        				     double capd = Double.parseDouble(df.format(cap));
	        				     if(SetEnv.RUNOPTIONS[7]){
	        				     d[ix-1]=capd;//Double.parseDouble(df.format(cap));
	        				     }
	        					 if(capd<SetEnv.MARKETCAP){
	        						 skip=true;
	        						 
	        					 }else{
	        						 skip=false;
	        					 }
	        					 }
	        					 
	        					 } else {
	        						 skip=true;
	        					 }
	        					 
	        					 if(skip) System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+cap);
	        				 }else{
	        					 d[ix-1] = Double.parseDouble(line[ix].replace("\\+","").replaceAll("%",""));
	        				 }
        				 }else{
        					// d[ix-1] = 0.0;
        				 }
        			 }
        			 
        			 ix++;
        		 }
        	
        	
	        
            double[] use = null;	 
            
            if(SetEnv.RUNOPTIONS[6]){
	           
		            if(d[0]>50)
		            	d[0]=50;
		
		            if(d[1]>50)
		            	d[1]=50;
	        }
        	switch(columns.length-1){
        		case 2:
        			use = new double[]{d[0],d[1]};
        		break;
        		case 3:
        			use = new double[]{d[0],d[1],d[2]};
            	break;
        		case 4:
        			use = new double[]{d[0],d[1],d[2],d[3]};
            	break;
        		case 5:
        			use = new double[]{d[0],d[1],d[2],d[3],d[4]};
        		break;
        		case 6:
        			use = new double[]{d[0],d[1],d[2],d[3],d[4],d[5]};
        		break;
        		case 7:
        			use = new double[]{d[0],d[1],d[2],d[3],d[4],d[5],d[6]};
        		break;
        		case 8:
        			use = new double[]{d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7]};
        		break;
        		default:
        			use = new double[]{d[0],d[1]};
        		break;
        	}
        	
        	
        	
        	
        	
        	if(SetEnv.RUNOPTIONS[0]){
        		if(d[0]<0.0 ){
        			skip=true;
        			SetEnv.SKIP[0]="\nskipp negative "+columns[0];
        		}
        	}
        	if(SetEnv.RUNOPTIONS[1]){
        		if( d[1]<0.0){
        			skip=true;
        			SetEnv.SKIP[1]="\nskipp negative "+columns[1];
        		}
        	}
        	
        	
        	
        	if(line[0].trim()!="" && skip==false && !inputLine.contains("N/A")){// && d[3]>=.0d  && d[4]>=5.0d
        	//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+inputLine);
        
        	
        	lineHash.put(line[0].toString(),use);
            //biff.append(line);
        	}
            
        	//}
        
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
    	URLConnectionCustom.items=initems;
    }

    

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
    	new URLConnectionCustom();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

            }
        });
    
        
        for(Object o : lineHash.keySet().toArray()){
        	
        	System.out.println(o.toString()+" " + show((double[])lineHash.get(o)));
        }
    }
	private static String show(double[] object) {
		String ret="";
		
	
		
		for(Object o : object){
        	
        	ret += o.toString()+" ";
        }
		return ret;
	}
}
