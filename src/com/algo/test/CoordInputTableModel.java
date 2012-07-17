package com.algo.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

public class CoordInputTableModel extends AbstractTableModel {
    
    private Double[] xCoord;// = new Double[10000];  // Data for Column 1, initially all null.
    private Double[] yCoord;// = new Double[10000];  // Data for Column 2, initially all null.
    ArrayList xsymbols = new ArrayList();
    private Double[] vCoord;
    public void setTablexData(ArrayList symbols) {
  	  
  	  xsymbols =  symbols;  
  	  //if(xsymbols !=null)
  	  setupdata();
    }

    
	public void setupdata() {

	  vCoord = new Double[xsymbols.size()];  // Da
	  xCoord = new Double[xsymbols.size()];  // Data for Column 1, initially all null.
    yCoord = new Double[xsymbols.size()];
		
	}

	public int getColumnCount() {  // Tells caller how many columns there are.
       return 4;
    }

    public int getRowCount() {  // Tells caller how many rows there are.
       return xCoord.length;
    }

    public Object getValueAt(int row, int col) {  // Get the data for one cell.
  	  
  	  
  	  if (row<900 && col == 0)
            return xsymbols.get(row).toString();   // Column 0 holds the row number.
  	  else if (col == 1)
            return vCoord[row];   // Column 0 holds the row number.
        else if (col == 2)
          return xCoord[row];    // Column 1 holds the x-coordinates.
        else if(col == 3)
          return yCoord[row];    // column 2 holds the y-coordinates.
        else 
            return null;

    }

    public Class<?> getColumnClass(int col) {  // Get data type of column.
  	 if(col==0)
  		 return String.class;
  	 else if (col == 1)
          return Double.class;
  	 else if (col == 2)
           return Double.class;
  	 else if (col == 3)
           return Double.class;
		return null;
   	 
    }

    public String getColumnName(int col) {  // Returns a name for column header.
  	 if (col == 0)
  		 return "symbol";
  	 else if (col == 1)
          return "Mill V.";
       else if (col == 2)
          return "X";
       else if(col == 3 )
          return "Y";
       else
      	 return null;
    }

    public boolean isCellEditable(int row, int col) { // Can user edit cell?
       return col > 0;
    }
    
    public void setValueAt(Object obj, int row, int col) { // Changes cell value.
          // (This method is called by the system if the value of the cell
          // needs to be changed because the user has edited the current value.
          // It can also be called to change the value programmatically.
          // In this case, only columns 1 and 2 can be modified, and the data
          // type for obj must be Double.  The method fireTableCellUpdated()
          // has to be called to send a TableModelEvent to registered listeners.)
  	  if (col == 1)
          vCoord[row] = (Double)obj;
  	  else if (col == 2) 
          xCoord[row] = (Double)obj;
       else if (col == 3)
          yCoord[row] = (Double)obj;
       fireTableCellUpdated(row, col);
    }
    
 }  // end nested class CoordInputTableModel
 


