package com.multistage.correlations.gui.models;


import javax.swing.table.*;



/**
 * Just my table model
 * @author H.Geissler
 * 18 May 2011
 *
 */

// based on DefaultTableModel
public class MyTableModel extends DefaultTableModel {

         public static final long serialVersionUID = 14;

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

}
