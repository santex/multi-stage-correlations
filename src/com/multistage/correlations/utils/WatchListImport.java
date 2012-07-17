/**
 * 
 */
package com.multistage.correlations.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import com.algo.test.ValueComparator;
import com.multistage.correlations.gui.Main;
import com.multistage.correlations.gui.SetEnv;

/**
 * @author hagen
 *
 */
public class WatchListImport {

	public static Statement st;
	public static Connection con;
	String header;
	static String[] rows;
	public static HashMap map = new HashMap();
	/**
	 * 
	 */
	public WatchListImport(String header, String[] split) {
		this.header  =  header;
		this.rows  =  split;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		 send();
	}
	/**
	 * @param args
	 */
	public void check() {
		
		for(String elem: this.rows){
			System.out.println(""+elem);
		}
	}

	public static void send() {

	// 
	
	try{
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://108.59.253.25:3306/signup", "hagen", "x123pass");
        //con = DriverManager.getConnection("jdbc:mysql://127.0.01:3306/signup", "root", "pass");
        st=con.createStatement();
        
        ResultSet rs=st.executeQuery("select * from login where username='"+SetEnv.USER+"'");
        
        String uname="",pass="";
        if(rs.next()){
            uname=rs.getString("username");
            pass=rs.getString("password");
        }

		//INSERT INTO stocks (stock_id, customer_id, watchlist_id, import_date, purchase_date, ticker, shares, share_price, current_val) 
		//VALUES (NULL, '1', '1', CURRENT_TIMESTAMP, CURDATE(), NULL, NULL, NULL, NULL)
    	String base ="INSERT INTO stocks (stock_id, descr,username, watchlist_id, import_date, purchase_date, ticker, shares, share_price, current_val) "+
		 " VALUES (NULL, '%s','%s', '%s', CURRENT_TIMESTAMP, CURDATE(), '%s',1,%s,%s);";
		
		
		
		for(String row:rows){
			StringBuffer query =  new StringBuffer();	
		
			if(row.contains("cluster:")){
				//cluster:   1  (24.48, 55.81) ANX LVLT
			//row=row.replaceAll(" ","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\n","").replaceAll("cluster:","");
			String[] elements = row.split("\\)");
			

			elements[0]=elements[0].trim();
			
			
			
        	elements[1]=elements[1].trim();
        	String[] info = elements[1].split(" ");
        	
        	String[] head = elements[0].split("\\(");
        	//System.out.println(elements[1]);
        	String[] cp = head[1].split(",");        	
        	String cluster = head[0].replaceAll("cluster:| ","");
        	int xc=0;
        	int gc=0;
        	double qKey=0d;
        	
        			double[] coords=new  double[cp.length];
        			int ix = 0;
        			for(String c:cp){
        				coords[ix]=Double.parseDouble(c);
        				System.out.println(coords[ix]);
        				qKey*=coords[ix]>0.0d?coords[ix]:1;
        				ix++;
        			}
        			
        			
        			if(SetEnv.RUNOPTIONS[0] && !SetEnv.RUNOPTIONS[1]){
                		qKey=coords[0];
                		
                	}else if(SetEnv.RUNOPTIONS[0] && SetEnv.RUNOPTIONS[1]){
                		qKey=coords[0]*coords[1];
                		
                	}else  {
                		coords[0]=coords[0]>0.0d?coords[0]:1;
                		coords[1]=coords[1]>0.0d?coords[1]:1;
                		coords[2]=coords[2]>0.0d?coords[2]:1;
                		
                		qKey=coords[0];
                		
                	}
                	
        			
		        	for(String elem:info){
		        		
		        		
		           //String key =  elem.replaceAll(",","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\n","");
		           SetEnv.MATRIXID=SetEnv.matrix+"-"+SetEnv.Mode+"-"+cluster+"-"+SetEnv.USER;
			       String q = String.format(base,SetEnv.matrix+"-"+SetEnv.Mode+"-"+cluster+"-"+SetEnv.USER,SetEnv.USER,cluster,elem,0,0);
			        		        				
		        			
		        	
		        	query.append(q).append("\n");
		        			
		        		
		        			xc++;
		        		
		        	}
		        	
		        	//if(coords[0] >0.0d && coords[1] >0.0d) 
		    		map.put(qKey,query.toString());
		        	    	
		        	
		        	//System.out.println(query.toString());
		        	
		        	
	        	
        	
			}
			
		}
		
		
		testProduction();
		
		
		st.close();
		con.close();
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    	
		    }

		  }
	
	

	   public static void testProduction(){

		   new UtilDemo4(map);
		   map.clear();
	   
	   }	

	   static class UtilDemo4 {

		   public static Object[] names;

		   public static Object[] salaries;
		   
		   
		   public UtilDemo4(HashMap map) {
			   this.names = map.values().toArray();
			   this.salaries = map.keySet().toArray();
			   runner();
		   }
	
			public static void main(String[] args) {
			     names = new Object[] { "A", "B", "C", "D" };
	
			     salaries = new Object[] { 2.0, 5.0, 6.0, 4.0 };
	
			     runner();
			}
			
			static public void runner(){
				List l = new ArrayList();
	
			     for (int i = 0; i < names.length; i++)
			       l.add(new MatrixCompare(names[i], salaries[i]));
	
			    // Collections.sort(l);
	
			    ListIterator liter = l.listIterator();
	
			    // while (liter.hasNext())
			    //   System.out.println(liter.next());
	
			     Collections.sort(l, new MatrixCompare.SalaryComparator());   
	
			     liter = l.listIterator();

			     
			     while (liter.hasNext()){
			    	Object o  = liter.next(); 
			    	 
			    	  if(!liter.hasNext()){
			    		  double  dd =  Double.parseDouble(o.toString());
			    		  System.out.println(">"+o+"\n"+map.get(dd).toString());
			    		  try {
			    			String[] querys  = map.get(dd).toString().split("\n"); 
							
			    			for(String q:querys) {
			    				st.execute(q);
			    			}
			    			
			    			st.execute("update statusupdate set status='update' ,statustyp='all';");
			    			
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	  	  
			    	  }else{
			    		  System.out.println(o);
			    	  }
			     }
			  // }
			 }
		
	   } 
}

