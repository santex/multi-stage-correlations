package com.multistage.correlations.clcontrol;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import com.multistage.correlations.gui.*;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;


/**
 * Class to create a nice table
 * @author H.Geissler
 * 18 May 2011
 *
 */

public class TableSettings extends JPanel {

	public static final long serialVersionUID = 4121;

	private boolean DEBUG = false;

	protected String[] columnToolTips = { "test1", null };

	private String[]  tip1 = { "Number of clusters","Fuzziness (only for c-means)",
			                             "Max number of iterations","Precision for clustering","Only for c-means" };
	private String[]  tip2 = { "from 2 to inf","from 1 to 2",
            ">100","<0.1","from 0.5 to 0.99" };

	
	
	public JTable table;

	public TableSettings() {
		super(new GridLayout(1, 0));

		table = new JTable(new MyTableModel()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);

				if (realColumnIndex == 0) { // Sport column
					tip = "Set: "+tip1[rowIndex];
						// 	+ getValueAt(rowIndex, colIndex);
				} else if (realColumnIndex == 1) {
					tip = "Recomended: "+tip2[rowIndex];
					// 	+ getValueAt(rowIndex, colIndex);
				 }
				 
				return tip;
			}

			// Implement table header tool tips.
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index)
								.getModelIndex();
						return columnToolTips[realIndex];
					}
				};
			}
		};

		// table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		// int hh=22;
		int hh = (int) (0.15 * SetEnv.SizeY / 5.69);
		table.setRowHeight(hh);

		TableColumn column = null;
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(110);
		column.setMinWidth(50);
		column.setMaxWidth(120);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(90);
		column.setMinWidth(50);
		column.setMaxWidth(200);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	class MyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] columnNames = { "Name", "Settings" };

		private Object[][] data = { { "No clusters", new Integer(2) },
				{ "Fuzziness", new Double(1.1) },
				{ "Max interations", new Integer(20) },
				{ "Precision", new Double(0.001) },
				{ "Prob. Clusters", new Double(0.68) } };

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

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 1) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value + " (an instance of "
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

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("TableToolTipsDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		//JComponent newContentPane = new TableOptions();
		//newContentPane.setOpaque(true); // content panes must be opaque
		//frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
