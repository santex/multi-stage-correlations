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
 * TableToolTipsDemo is just like TableDemo except that it sets up tool tips for
 * both cells and column headers.
 */
public class TableOptions extends JPanel {

	public static final long serialVersionUID = 45132;

	
//	public SelectionListener listener;

	private boolean DEBUG = false;

	protected String[] columnToolTips = { "Show centers / seeds", null };

	public JTable table;

	public TableOptions() {
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
	//			int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);

				if (realColumnIndex == 0) { // Sport column
					tip = "Show centers seeds";
					// + "participate in is: "
					// + getValueAt(rowIndex, colIndex);
				}
				/*
				 * } else if (realColumnIndex == 1) { //Veggie column TableModel
				 * model = getModel(); String firstName =
				 * (String)model.getValueAt(rowIndex,0); tip = firstName + " " + "
				 * is a vegetarian"; } else { //You can omit this part if you
				 * know you don't //have any renderers that supply their own
				 * tool //tips. tip = super.getToolTipText(e); }
				 */
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
						// String tip = null;
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
		// table.getModel().addTableModelListener(new
		// MyTableModelListener(table));

		int hh = (int) (0.069 * SetEnv.SizeY / 2.6);
		table.setRowHeight(hh);
		// listener = new SelectionListener(table);
		// table.getSelectionModel().addListSelectionListener(listener);

		TableColumn column = null;
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(130);
		column.setMinWidth(100);
		column.setMaxWidth(500);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(40);
		column.setMinWidth(50);
		column.setMaxWidth(500);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	class MyTableModel extends AbstractTableModel {

		public static final long serialVersionUID = 41313;

		private String[] columnNames = { "Display options", "Set" };

		private Object[][] data = { { "Show centers", new Boolean(true) },
				{ "Show seeds", new Boolean(false) } };

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
	//	@SuppressWarnings("unchecked")
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
	
			
			if (Global.doneAnalysis) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value + " (an instance of "
						+ value.getClass() + ")");
			}

			data[row][col] = value;
			fireTableCellUpdated(row, col);
		
			Global.ShowCenters=(Boolean)data[0][1];
			Global.ShowSeeds=(Boolean)data[1][1];
		  //  System.out.println(Global.ShowCenters  );
			Thread1 run = new Thread1("Update plot");
			if (!run.Alive()) {
				run.Start();
			}
			
			
			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
			
		} // done analysis
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
		JComponent newContentPane = new TableOptions();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

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

	
	
/*	
	public class SelectionListener implements ListSelectionListener {
        JTable table;
    
        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source
        SelectionListener(JTable table) {
            this.table = table;
        }
        public void valueChanged(ListSelectionEvent e) {
            // If cell selection is enabled, both row and column change events are fired
            if (e.getSource() == table.getSelectionModel()
                  && table.getRowSelectionAllowed()) {
                // Column selection changed
                int first = e.getFirstIndex();
                int last = e.getLastIndex();
             System.out.println("Global.ShowCenters 1: "+Global.ShowCenters);
            } else if (e.getSource() == table.getColumnModel().getSelectionModel()
                   && table.getColumnSelectionAllowed() ){
                // Row selection changed
                int first = e.getFirstIndex();
                int last = e.getLastIndex();
               // System.out.println("Global.ShowCenters: "+Global.ShowCenters);
            }
    
            if (e.getValueIsAdjusting()) {
               System.out.println("Global.ShowCenters 2: "+Global.ShowCenters);
            	// The mouse button has not yet been released
            }
        }
    }

 */
	
	
	

}
