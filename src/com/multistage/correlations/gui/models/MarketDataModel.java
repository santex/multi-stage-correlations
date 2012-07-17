package com.multistage.correlations.gui.models;

import javax.swing.table.AbstractTableModel;

import com.multistage.correlations.gui.SetEnv;

public class MarketDataModel extends AbstractTableModel implements Runnable {
	  Thread runner;
	  public String[] next;
	private int initialDelay;
	  public MarketDataModel(int initialDelay) {
		this.initialDelay=initialDelay;
	    Thread runner = new Thread(this);
	    runner.start();
	  }
	  double[] data = new double[]{1,2,3,4,5};
	  String[] strData=new String[]{"","","","","",};
	  String[] headers = {"symbol", "Price", "Change", "Last updated" };

	  public int getRowCount() {
	    return 1;
	  }

	  public int getColumnCount() {
	    return headers.length;
	  }

	  public String getColumnName(int c) {
	    return headers[c];
	  }

	  public Object getValueAt(int r, int c) {
		  
	    return data[c];
	  }


	  public void updateStocks() {
	    for (int i = 0; i < data.length; i++) {
	    	
	      data[i] = Math.random();
	    }
	  }

	  public void run() {
	    while (true) {
	    	
	    	next = SetEnv.RAW.toString().split(",");
	    	///System.out.println(next.length);
	    	strData=next;
	    	int row=0;
	    	if(next!=null){
	    		for(String x:next){
	    		updateStocks();

	      		fireTableRowsUpdated(row, next.length - 1);
	      		row++;
	    		}
	    	}
	      try {
	        Thread.sleep(1000+initialDelay);
	      } catch (InterruptedException ie) {
	      }
	    }
	  }

	public void fireTableCellInsert(String rowk, int i) {
		// TODO Auto-generated method stub
		
	}
	}
