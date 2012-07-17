package com.multistage.correlations.gui.models;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import com.multistage.correlations.gui.FileUpload;


public class DataManipulation extends AbstractTableModel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
    private String[] columnNames = {"DataManipulation",
                                    "use"};
    private Object[][] data = {
        {"exclude negative",
          new Boolean(false)},
     
    };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    

    public void actionPerformed(ActionEvent e) {
	    //ListModel model = getModel();
    	 int n = getRowCount();
         int numCols = getColumnCount();

	    for (int i = 0; i < n; i++) {
	      CheckableItem item = (CheckableItem)getValueAt(0, n);
	      if (item.isSelected()) {
//	        textArea.append(item.toString());
	//        textArea.append(System.getProperty("line.separator"));
	      }
	    }
	  }


    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
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
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}

