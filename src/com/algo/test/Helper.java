package com.algo.test;


import java.awt.EventQueue;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.cluster.Partition;



public class Helper {
	public HashMap dataFinance;
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
	private static final String STYLE_SHEET = "<style>h2 {font-size:1.0em;font-weight:normal;} " +
    "a {color:#6688cc;} ol {padding-left:1.2em;} blockquote {margin-left:0em;} " +
    ".interProject, .noprint {display:none;} " +
    "li, blockquote {margin-top:0.5em;margin-bottom:0.5em;}</style>";
	
	public Helper(HashMap dataFinance2) {
		this.dataFinance= dataFinance2;
		

    	symbols = new ArrayList();
    	
    	for(Object o :dataFinance.keySet().toArray())
    	{
    		symbols.add(o.toString());
    	}
	}
	
	public void runner() {

		try {
			
	
			
        result =  new StringBuffer("<html><head><link rel='stylesheet' type='text/css' href='http://localhost/qt/css/bizsol3.css' />"+STYLE_SHEET+"</head><body  background='http://localhost/qt/images/dna.png'><span color='#fff'>");
		

		result.append("<h1>Correlation</h1><div class='left' width='400px'></div><div class='right'><br/><pre>");
		
		
		items = urlConn.items;
		

		data = new DataHolder(NL+"Example");
// fill some data 
		double[] a = new double[2];
		listS = new ArrayList();
		Random r = new Random();
		int xx=1;
	      Object[] keys = dataFinance.keySet().toArray();
	      for  (int i = 0; i < dataFinance.size(); i++) {  // Fill first 6 rows with random values.
	    	  //se = biff.get(i);
	    	  
	    	  	Object[] dd = (Object[]) dataFinance.get(keys[i]);


	    	 	a[0] = (Double) dd[3];//r.nextDouble()*100*xx;
				a[1] = (Double) dd[4];//r.nextDouble()*100*xx;
				listS.add(a[0]);
				listS.add(a[1]);
				DataPoint dp = new DataPoint(a);
				
				data.add(dp);
				data.setName(i,keys[i].toString());
				
	      }
		
		double[] dd = MathHelper.minmax(listS);


		//for(int x:new int[listS.size()]){
		//	result.append(NL).append(dd.length+"="+x).append(dd[0]).append(NL).append(dd[1]).append(NL);
		//}
		
		result.append(NL).append("range (max:"+dd[1]+""+NL+"min:"+dd[0]).append(")").append(NL);
		result.append(NL).append("Dimension=" + data.getDimention() + " used variables"+(data.getDimention()==2?"mvg. avg (50day,200day)":""));
		result.append(NL).append("No symbols=" + data.getSize()+"");

// check loaded data (print!)
		data.print();
		///data.setName(, title)

		Partition pat = new Partition(data);

// set No clusters, precision, fuzziness (dummy if non-applicable),
// max number of iterations
		pat.set(50, 0.001, 0.7,500);

// probability for membership (only for Fuzzy algorithm)
               // pat.setProbab(0.68);

// define types of cluster analysis
		int[] mode = new int[1];
		mode[0] = 121;
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
			result.append(NL).append("No of final performance clusters: " + pat.getNclusters()+"</pre>");
			
			DataHolder Centers = pat.getCenters();
			
			//if(i==0)Centers.print();
			
// show cluster association
			int m1 = 0;
			String centers  ="";
			StringBuffer xresult = new StringBuffer();
			for(Object o :Centers.getArrayList().toArray())
			{
				
			    
			    

			    
				xresult.append("<option>"+(o.toString().replaceAll(",","\t").replaceAll("\\)[0]",""))+"</option>"+NL);
				//xresult.append("<option>"+(o.toString().replaceAll(",","\t"))+ ((DataPoint)o).getClusterNumber()+"</option>");	
				
			}
			result.append(NL).append(NL+"<pre>Positions of centers:<br />"+NL+"</pre>"+NL+"<select name='centers'>"+xresult.toString()+"</select>"+NL);
			result.append("");
			xresult = new StringBuffer();
			xresult.append(NL);
			xresult.append(NL+"<select name='order'>");
			xresult.append(NL);
			
			String symbol;
			for (int m = 0; m < data.getSize(); m++) 
			{
				
			    
			    
					
				DataPoint dp = data.getRaw(m);
		    	 int k = dp.getClusterNumber();
		    	 
		    	 ///symbol = data.getName(m).toUpperCase();
		    	 xresult.append(NL).append(NL+"<option>").append("symbol:"+data.getName(m).toUpperCase()+"@point="+m+" associated with cluster:"+k).append("</option>");
		    	 xresult.append(NL);
			     
				
			}
			
			xresult.append("</select>"+NL);
			
			result.append(NL).append(xresult.toString());
			//result.append(NL).append("Positions of seeds: ");
		    	//for (int m = 0; m < data.getSize(); m++) {
		    	 
		    	
		    	//}
		    	
		    	
			// applicable for not every clustering
			// dataHolder Seeds = pat.getSeeds();
			// result.append(NL).append("Positions of seeds: ");
			// Seeds.Print();
		}

		
		info = new Information(pat,result,data);
		
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