package com.algo.test;


import java.awt.EventQueue;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.multistage.correlations.cluster.*;
import com.multistage.correlations.gui.SetEnv;


public class ClusterImplementCustom {
	public HashMap[] symbolLocation;
	public static HashMap dataFinance = new HashMap();
	public DataHolder data;
	public static String NL = "\n";
	public static StringBuffer result;	
	public static String syms;
	public static String[] items = new String[3000];
	public static ArrayList listS=new ArrayList();
	public static String columns = "st1oml1vp2a2j6k5c1baj1m6m8r5e9e7s7";
	//st1oml1vp2a2j6k5c1baj1
	public static String baseUrl = "http://finance.yahoo.com/d/quotes.csv?f=%s&s=%s";
	public URLConnectionReader urlConn;
	public ArrayList symbols;
	public Information  info;
	private static final String STYLE_SHEET = "<style>body{background-image:none; background-color:#fff;color:#000000;}.left{float:left;}.right{float:right;}</style>";
	
	public ClusterImplementCustom(HashMap dataFinance2) {
		//this.dataFinance.clear();
		this.dataFinance= dataFinance2;
		

    	symbols = new ArrayList();
    	
    	for(Object o :dataFinance.keySet().toArray())
    	{
    		
    		if(!symbols.contains(o.toString())){
    			symbols.add(o.toString());
    		}
    	}
	}
	
	public void runner() {

		try {
			
	
			
        result =  new StringBuffer("<html><head><link rel='stylesheet' type='text/css' href='http://localhost/qt/css/bizsol3.css' />"+STYLE_SHEET+"</head><body  background='http://localhost/qt/images/dna.png'><span color='#fff'>");
		

		result.append("<h2>Correlation</h2><div class='right'><br/><pre>");
		
		
		items = urlConn.items;
		
		
		data = new DataHolder(" ");

		if(dataFinance==null)
			return;
	
		if(dataFinance.size()==0)
			return;
		
// fill some data 
		//double[] a = new double[4];
		listS = new ArrayList();
		Random r = new Random();
		int xx=1;
	      Object[] keys = dataFinance.keySet().toArray();
	      for  (int i = 0; i < dataFinance.size(); i++) {  // Fill first 6 rows with random values.
	    	  //se = biff.get(i);
	    	  
	    	  	double[] dd = (double[]) dataFinance.get(keys[i]);
	    	  	DataPoint dp = new DataPoint(dd);
				dp.setLabel(keys[i].toString());
				data.add(dp);
/*
	    	  	if(dd[3]!=null && dd[4]!=null && dd[3]!="N/A" && dd[4]!="N/A"){
	    	 	a[0] = (double) dd[1];//r.nextDouble()*100*xx;
				a[1] = (double) dd[2];//r.nextDouble()*100*xx;
				//System.out.println(String.format("%s %s",a[0],a[1]));
				//a[2] = (Double) dd[0];//r.nextDouble()*100*xx;
				//a[3] = (Double) dd[1];
				listS.add(a[0]);
				listS.add(a[1]);
				//listS.add(a[2]);
				
				//data.setName(i,i+"_"+keys[i]);
	}			*/
	
	      }
		
		double[] dd = MathHelper.minmax(listS);


		//for(int x:new int[listS.size()]){
		//	result.append(NL).append(dd.length+"="+x).append(dd[0]).append(NL).append(dd[1]).append(NL);
		//}
		
		result.append(NL).append("range (max:"+dd[1]+" min:"+dd[0]).append(NL);
		result.append(NL).append("Dimension=" + data.getDimention() + NL+"variables="+(data.getDimention()==3?"</pre>mvg. avg<br /><ul><li>50day</li>,<li>200day</li>,<li>Avg$Vol</li></ul><pre>":""));
		result.append(NL).append("No symbols=" + data.getSize());

// check loaded data (print!)
		//data.print();
		///data.setName(, title)

		Partition pat = new Partition(data);

// set No clusters, precision, fuzziness (dummy if non-applicable),
// max number of iterations
		pat.set(2, 0.001, 0.7,20);

// probability for membership (only for Fuzzy algorithm)
               // pat.setProbab(0.68);

// define types of cluster analysis
		int[] mode = new int[1];
		mode[0] = 114;
		/*
		mode[0] = 111;
		
		mode[2] = 113;
		mode[3] = 114;
		mode[4] = 121;
		mode[5] = 122;
		mode[6] = 131;
		mode[7] = 132;
		*/
		
// run over all clustering modes 
		for (int i = 0; i < mode.length; i++) {
			pat.run(mode[i]);
			result.append(NL).append("\nalgorithm: " + pat.getName());
			result.append(NL).append("Compactness: " + pat.getCompactness());
			result.append(NL).append("No of final performance clusters: " + pat.getNclusters());
			
			DataHolder Centers = pat.getCenters();
			
			//if(i==0)Centers.print();
			
// show cluster association
			int m1 = 0;
			String centers  ="";
			StringBuffer xresult = new StringBuffer();
		
			result.append(NL).append(NL+"</pre><br /><b>Positions of symbols:</b><br />"+NL+xresult.toString());
			result.append("");
			xresult = new StringBuffer();
			xresult.append(NL);
			xresult.append(NL+"<textarea rows=50 cols=100>");
			xresult.append(NL);
			
			symbolLocation = new HashMap[pat.getNclusters()];
			for (int m = 0; m < pat.getNclusters(); m++) 
			{
				symbolLocation[m] = new HashMap();
				
			}
			
			for (int m = 0; m < data.getSize(); m++) 
			{
				
			    
			    
					
				DataPoint dp = data.getRaw(m);
		    	 int k = dp.getClusterNumber();
		    ///	 String symbol=items[m].toUpperCase();
		    	 //if(symbolLocation[k]==null){
		    //		 //symbolLocation[k] = new HashMap();
		    	// }
		    	 
		    	 //if(symbolLocation!=null)
		   // 		 if(symbolLocation[k]!=null)
		    			 
		    //			 if(items[m]!=null){
		    	 symbolLocation[k].put(dp.getLabel(),dp);
		    	 
		    	 xresult.append(k+"~").append(dp.getLabel()+"@point="+m+" associated with cluster:"+k).append("");
		    	 xresult.append(NL);
		    	//		 }
			     
				
			}
			
			xresult.append("</textarea><br/><div class='left'><h4>Exchange</h4><img src='http://localhost/qt/images/g.png' alt='Cluster exchange' /></div><br /><b>Positions of cluster centers:</b>"+NL);
			
			result.append(NL).append(xresult.toString());
			xresult = new StringBuffer();
			
			DataHolder xCenters = pat.getCenters();
			
			if(xCenters.getSize()>0){
			for(DataPoint o :xCenters.getArray())
			{
				
			   
			    

			
		    int k = o.getClusterNumber();
			   
			 xresult.append("<br /><small>"+(o.getAttribute(0)+" "+o.getAttribute(1)+" "+o.getAttribute(2)+" "+k+" ")+"</small>");	
				
			}
			result.append(NL).append(xresult.toString());
			//result.append(NL).append("Positions of seeds: ");
		    	//for (int m = 0; m < data.getSize(); m++) {
		    	 
		    	
		    	//}
		    	
		    	
			// applicable for not every clustering
			// dataHolder Seeds = pat.getSeeds();
			// result.append(NL).append("Positions of seeds: ");
			// Seeds.Print();
		}

		}//symbolLocation[k].put(symbol,dp);
		info = new Information(pat,result,data,symbolLocation);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	} // end of main
	
	
	  // This inner class avoids a really obscure race condition.
	  // See http://java.sun.com/developer/JDCTechTips/2003/tt1208.html#1
	  private static class FrameShower implements Runnable {

	    private final Frame frame;

	    FrameShower(Frame frame) {
	      this.frame = frame;
	    }

	    public void run() {
	     frame.setVisible(true);
	    }

	  }
	  
	  public void setInformation(Information info)
	  {
		  
	  }
	  public Information getInformation(){
		  return info;
	  }
	  public JScrollPane prepareMain() {

		  	  this.runner();   
		  	  
		  	  result.append(NL).append("</pre><ol>");

		     long f1 = 0;
		     long f2 = 1;

		     for (int i = 0; i < 0; i++) {
		       result.append(NL).append("<li>");
		       result.append(NL).append(f1);
		       long temp = f2;
		       f2 = f1 + f2;
		       f1 = temp;
		     }

		    // Math.max();
		     result.append(NL).append("</ol></div>"+NL+"</body></html>");

		     JEditorPane jep = new JEditorPane("text/html", result.toString());
		     jep.setEditable(false);

		     
		   //  new FibonocciRectangles().execute();
		     JScrollPane scrollPane = new JScrollPane(jep);
		     JFrame f = new JFrame("Correlations");
		     f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		     f.setContentPane(scrollPane);
		     f.setSize(1000,800);
		     
		     return scrollPane;
		     //EventQueue.invokeLater(new FrameShower(f));

		  }


	  
		public static void prepare() {
			
			
		} 
		
	
}