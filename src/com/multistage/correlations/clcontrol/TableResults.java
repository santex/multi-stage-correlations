package com.multistage.correlations.clcontrol;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.GridLayout;




public class TableResults extends JPanel {

	public static final long serialVersionUID = 412;
	
    public JTable table;
    public MyTableModel model;        


    public TableResults() {

       super(new GridLayout(1,0));



        model = new MyTableModel(); 
        table = new JTable(model); 

         table.setRowHeight(20);
         model.addColumn("Info");
         model.addColumn("Summary");

        
        TableColumn  column =null;
        column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column.setMinWidth(50);
        column.setMaxWidth(160);
        column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(90);
        column.setMinWidth(50);
        column.setMaxWidth(200);

        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }



}

