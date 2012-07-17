package com.algo.test;

import java.util.HashMap;

import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.Partition;
import com.multistage.correlations.gui.SetEnv;

public class Information {
	  public Partition part = null;
	  public StringBuffer result = null;
	  public DataHolder data = null;
	  public String description = "";
	  public String plotFile = "";
	  public HashMap[] locations;
	  public StringBuffer biff = new StringBuffer();

	public  Information(Partition part,StringBuffer result,DataHolder data, String fileData){
			
			if(fileData!=null){
			  this.plotFile=fileData;
			}
		  this.part = part;
		  this.result = result;
		  this.data = data;
		  this.description = SetEnv.DESC;
		  System.out.println(toString());
	  }

	


	public Information() {
		
	}




	public Information(Partition pat, StringBuffer result2, DataHolder data2) {
		
		  this.part = pat;
		  this.result = result2;
		  this.data = data2;
		  this.description = SetEnv.DESC;
		  System.out.println(toString());
	}




	public Information(Partition pat, StringBuffer result2, DataHolder data2,HashMap[] symbolLocation) {
		  this.part = pat;
		  this.result = result2;
		  this.data = data2;
		  this.locations = symbolLocation;
		  this.description = SetEnv.DESC;
		  
		  int i=symbolLocation.length;
		  
		  for(HashMap map : locations){
			  	
			  	//System.out.print(map);
			    System.out.println("cluster:");
			  	System.out.println(map.keySet());
			  	biff.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n").append("cluster="+i).append(" ").append(map.keySet().toString());
			  	//i++;
		  }
		  
		  System.out.println(toString());

	}




	public String toString() {
		biff = new StringBuffer();
		
		biff.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n").append(result);
		biff.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n").append(description);
		biff.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n").append(plotFile);
		
		
		return biff.toString();
	}




	public void setLocations(HashMap[] symbolLocation) {
		this.locations=symbolLocation;
		
		int i = 0;
		for(Object map : locations){
			biff.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@\n").append("cluster="+i).append("\n").append(locations[i].keySet().size());
		}
		
		
	}
	
}
