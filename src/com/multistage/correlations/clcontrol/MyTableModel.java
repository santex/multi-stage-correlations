package com.multistage.correlations.clcontrol;
import javax.swing.table.*;



// define table
class MarketDataModel extends AbstractTableModel implements Runnable {
	  Thread runner;
	  public MarketDataModel(int initialDelay) {
	    Thread runner = new Thread(this);
	    runner.start();
	  }
	  double[] data = new double[]{1,2,3,4,5};
	  
	  String[] headers = { "Symbol", "Price", "Change", "Last updated" };

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
	      updateStocks();

	      
	    	  fireTableRowsUpdated(0, data.length - 1);
	      
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException ie) {
	      }
	    }
	  }
	}

// based on DefaultTableModel
class MyTableModel extends DefaultTableModel {

         public static final long serialVersionUID = 4;

         private boolean DEBUG = true; // for debuging this table




        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }


        /*
         * Don't need to implement this method unless your table's
         * editable.
         */

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            // set 0 to some value
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

/*

         private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + getValueAt(i,j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
*/
}
