package com.multistage.correlations.gui;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;

import com.multistage.correlations.cluster.*;


public class  SetEnv {
     
	public static StringBuffer header = new StringBuffer();
	public static HashMap nameNames = new HashMap();	
	public static final String[] SKIP = new String[3];
	public static final String[] RULES = {
		//"exclude todays faller ?","volume > day avg volume",
		"AllAxes>0",
		"Change>0",
		"EstEpsCurYr>0",
		"Vol>AvgDayVol",
		"StartFeed",
		"UseCache",
		"ApplyCap",
		"MCapUsedInMatrix"};
	public static int Previous = 0;
	// size of the window
     public static  int SizeX;
     public static  int SizeY;
     public static  int SizeB;
     public static  JLabel statusbar;

     public static Locale   locale;
     public static String   LANGU="en";
     public static String   OSsys;
     public static Plotter  PLOT;
     
     public static String   MARKET  = "";
     public static String   DESC;
     public static String[]  SYMBOLS;
     public static StringBuffer  DATALOG;
     
     public static Hashtable<String, HashMap> update = new Hashtable<String,HashMap>();
// file with the settings 
    public static String INIFILE="";

    public static Font FontBold;
    public static Font FontPlain;    

// hold the data 
    public static DataHolder DATA;

// hold seeds
    public static DataHolder SEED;

// centers of clusters
    public static DataHolder ClusCenter;

// title of the data 
    public static String Mtitle;

// dimention of the data
    public static int  Dim=0;

    //  number of raw 
    public static int  NRow=0; 

    //  number of raw 
    public static String extension = "msc"; 
    
    // dimention of the data
    public static DataPoint  Min;

    //  number of raw 
    public static DataPoint  Max; 
    

// XY title of the data
    public static String[] XYtitle;

// cluster mode
    public static int  Mode;

// XY title of the data
    public static int Style;



// selection on JComboBox
    public static int JboxX=0;
    public static int JboxY=1;
	public static int LastMode;
	public static HashMap map;
	public static boolean recalc = false;
	private static String SCOPE;
	public static Object RAW = new Object();
	public static double MARKETCAP=10000d;
	
	public static boolean cached = false;
	public static boolean[] RUNOPTIONS=new boolean[11];
	public static String USER="";
	public static String[][] col = {{"s","m8","m6","p2","j1"},
									{"s","p2","m8","m6","j1"},
									{"s","p2","e","d","j1"},
									{"s","p2","m8","e","j1"},
									{"s","p2","m6","d","j1"},
									{"s","p2","m6","m8","j1"},
									{"s","p2","m8","v","j1"},
									{"s","p2","r","e","j1"},
									
									};
		//
	public static String[] namesM = new String[]{
		"Symbol+PctChgFrom50-dayMovingAvg+PctChgFrom200-dayMovingAvg+ChangePercent+MarketCapitalization",
		"Symbol+ChangePercent+PctChgFrom50-dayMovingAvg+PctChgFrom200-dayMovingAvg+MarketCapitalization",		
		"Symbol+ChangePercent+Earnings/Share+Dividend/Share+MarketCapitalization",
		"Symbol+ChangePercent+PctChgFrom50-dayMovingAvg+Earnings/Share+MarketCapitalization",
		"Symbol+ChangePercent+PctChgFrom200-dayMovingAvg+Dividend/Share+MarketCapitalization",
		"Symbol+ChangePercent+PctChgFrom200-dayMovingAvg+PctChgFrom50-dayMovingAvg+MarketCapitalization",
		"Symbol+ChangePercent+PctChgFrom50-dayMovingAvg+Volume+MarketCapitalization",
		  "Symbol+ChangePercent+P/ERatio+Earnings/Share+MarketCapitalization"
		
		};
		//"Symbol+ChangePercent+Volume+Earnings/Share+MarketCapitalization",{"s","p2","v","e","j1"},							   
	//
	 /* ,
									{"s","p2","v","j1"},
									{"s","p2","m6","j1","v"},
									{"s","m8","m6","d","j1","v"},
									{"s","m8","m6","j1","v"},
									{"s","m8","m6","e","d","e7"},
	 * {"s","m8","m6","p2","d","j1","v"},
									{"s","m8","m6","p2","d","j1"},
									{"s","m8","m6","p2","d","v","j1"},
									{"s","m8","m6","p2","e7","r5","d","j1","v"},
									{"s","m8","m6","p2","e7","r5","d"},
	 * */
	
	public static Object[][] names = {{"PctChgFrom50-dayMovingAvg",new Boolean(true),"m8"},
									 {"PctChgFrom200-dayMovingAvg",new Boolean(true),"m6"},
									 {"ChangePercent",new Boolean(true),"p2"},
									 {"EPSEst.CurrentYr",new Boolean(true),"e7"},
									 {"EPSEst.NextYear",new Boolean(false),"e8"},
						    		 {"EPSEst.NextQuarter",new Boolean(false),"e9"},
						    		 {"Price/EPSEst.NextYr",new Boolean(false),"r7"},
									 {"Volume",new Boolean(false),"v"},
									 {"AverageDailyVolume",new Boolean(false),"a2"},
									 {"MarketCapitalization",new Boolean(false),"j1"},
									 {"Holdings Gain Percent",new Boolean(false),"g5"},
									 {"Holdings Gain",new Boolean(false),"g4"},
									 {"AnnualizedGain",new Boolean(false),"g1"},
									 {"PEGRatio",new Boolean(false),"r5"},
									 {"Earnings/Share",new Boolean(false),"e"},
									 {"Dividend/Share",new Boolean(false),"d"},
									 {"P/ERatio",new Boolean(false),"r"},
									 {"Symbol",new Boolean(true),"s"}};
	public static int maxdim=0;
	
	public static String[] matrixTitles = new  String[SetEnv.names.length];//{ "Points", "Density" };
	public static String FILE="";
	public static int matrix=0;
	public static String title="";
	public static boolean cap = false;
	public static String MATRIXID="";


    public static void SizeFrame( int X, int Y) {
      SizeX=X;
      SizeY=Y;
      SizeB= (int)(0.22*SizeX); // size of the tool bar
    }






    public static void init() { 


// send default local
     LANGU="en";
    
     locale = new Locale(LANGU);
     
     Locale.setDefault(locale);
     statusbar = new JLabel("MultistageCorrelation initiated");
     
     for(Object[] n :names){
    	 nameNames.put(n[2].toString(),n[0].toString());
     }
     
     
     
// initialise data holder
//    DATA = new DataHolder();
     Mtitle="";
// initialize seeds 
     SEED = new DataHolder();
// centers of clusters
     ClusCenter = new DataHolder();
// main plot
     PLOT =  new Plotter();
     //
     DATA = new DataHolder();
     MARKET="";
     SYMBOLS = new String[0];
     DATALOG = new StringBuffer();
     map = new HashMap();
     DESC  = "";
     SCOPE  = "default";
     
// style
    Style=0;
    Mode=0;

     String OS = System.getProperty("os.name").toLowerCase();

  if (OS.indexOf("windows") > -1 || OS.indexOf("nt") > -1) {
     OSsys="windows";
     INIFILE=System.getProperty("user.home")+File.separator+".mscorrelation"+File.separator+"mscorrelation.ini";
  }  else {
     // linux/unix/BS-based/mac
     OSsys="linux";
     INIFILE=System.getProperty("user.home")+File.separator+".mscorrelation"+File.separator+"mscorrelation.ini";
    }

     FontBold= new Font("Lucida Sans", Font.BOLD, 10); 
     FontPlain= new Font("Lucida Sans", 0, 10);

     
 
     } 
    
    public static void setScope(String scope){
    	SCOPE=scope;
    }
    
    public static String getScope(){
    	return SCOPE;
    }
  
// load data from file  
       public static void Load( String filename ) { 
    	  
    	 DATA = new DataHolder();
         DATA.read( filename.trim() ); // read input
         Mtitle=DATA.getRelation();
         Dim= DATA.getDimention();
         NRow=DATA.getSize();
         Min=DATA.getMin();
         Max=DATA.getMax();
         XYtitle = new String[Dim];
          for (int i = 0; i <Dim; i++) {
           XYtitle[i]=SetEnv.DATA.getName( i );
           }

       }






	public static void setUpdate(String string, String string2, String string3) {
		
		HashMap xmap;
		if(update.get(string)==null){
			xmap = new HashMap();
		
		}else{
			xmap = update.get(string);			
		}
		xmap.put(string2,string3);
		update.put(string,xmap);
		
		
	}






	public static String getUpdate(String symbol, String string) {
		
		//System.out.print(((HashMap)update.get(string)).keySet().size());
		
		for(Object o :update.keySet().toArray()){
		
			HashMap m = ((HashMap)update.get(o));
			if(m!=null){
				Object[] kset = m.keySet().toArray();
				for(Object oo :kset){
					
					
					//System.out.println(o.toString()+" "+oo.toString()+" "+m.get(oo));
					
					if(oo.toString()==string && o.toString()==symbol)
						return (String) update.get(symbol).get(string);
			}
				}
		}
		
		return "-";
		
	} 
     
   }
