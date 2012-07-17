package com.multistage.correlations.gui;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.multistage.correlations.gui.models.IndicatorSelectModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

/** 
 * TableToolTipsDemo is just like TableDemo except that it
 * sets up tool tips for both cells and column headers.
 */
public class IndicatorSelect extends JPanel {
    private boolean DEBUG = false;
    protected String[] columnToolTips = {"Indicators compiled in your data file",
                                         "selected or not"};

    public IndicatorSelect() {
        super(new GridLayout(1,0));

        JTable table = new JTable(new IndicatorSelectModel()) {
            
            //Implement table cell tool tips.
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                 if (realColumnIndex == 0) { //Veggie column
                    TableModel model = getModel();
                    String name = (String)model.getValueAt(rowIndex,0);
                    Boolean veggie = (Boolean)model.getValueAt(rowIndex,1);
                    if (Boolean.TRUE.equals(veggie)) {
                        tip = (veggie?"checked":"unchecked") + " " + name
                              + " uses";
                    } else {
                    	tip = (veggie?"checked":"unchecked") + " " + name;
                    }
                } else { 
                    //You can omit this part if you know you don't 
                    //have any renderers that supply their own tool 
                    //tips.
                    tip = super.getToolTipText(e);
                }
                return tip;
            }

            //Implement table header tool tips. 
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        String tip = null;
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return columnToolTips[realIndex];
                    }
                };
            }
        };
     
        table.setPreferredScrollableViewportSize(new Dimension(250,400));
        table.setFillsViewportHeight(true);
    	TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(200);
		column.setMinWidth(50);
		column.setMaxWidth(260);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(40);
		column.setMinWidth(20);
		column.setMaxWidth(50);
                
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @return 
     */
    public static JFrame createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Indicators used as Demensions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new IndicatorSelect();
        //newContentPane.setOpaque(true); //content panes must be opaque
        
        frame.setContentPane(newContentPane);

        
        //Display the window.
        //frame.pack();
        //frame.setVisible(false);
        return frame;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}