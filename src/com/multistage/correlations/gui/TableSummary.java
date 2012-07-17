package com.multistage.correlations.gui;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import com.algo.test.CoordInputTableModel;
import com.multistage.correlations.gui.models.DataManipulation;
import com.multistage.correlations.gui.models.IndicatorSelectModel;
import com.multistage.correlations.gui.models.MarketDataModel;
import com.multistage.correlations.gui.models.MyTableModel;


import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;




public class TableSummary extends JPanel {
	
	public static final long serialVersionUID = 12342144;


    public JTable table;
    public MarketDataModel model;        
    public MyTableModel modelx;
    public CoordInputTableModel modely;
    private IndicatorSelectModel modelz;
    private DataManipulation modeld;
    
    

	private JTable tablex;


	private JTable tabley;


	private JTable intable;

	private JTable tabled;

	private JTable tablez;


	
    
    //JTable jt = new JTable(new MarketDataModel(5));
    public  TableSummary(int i) {

        super(new GridLayout(1,0));

        model = new MarketDataModel(5);//
        
        modely = new CoordInputTableModel();

        modelz = new IndicatorSelectModel();
        
        modeld = new DataManipulation();      
        
        table = new JTable(model); 
        tabley = new JTable(modely);
        
        table.setRowHeight(18);
         /*
         
         */
        

 

        
        if(i==0)
        {
        	intable=table;
        }else if(i==1){
        	
        	tablez = new JTable(modelz);
        	intable=tablez;
        	
        }else if(i==4){
        	  
            tabled = new JTable(modeld);
        	intable=tabled;
        }else{
        	modelx = new MyTableModel();
        	modelx.addColumn("Variable");
            modelx.addColumn("Mean/Variance");
            tablex = new JTable(modelx);
            
        	intable=tablex;
        	
        }
        
		int hh = (int) (0.15 * SetEnv.SizeY / 5.69);
		table.setRowHeight(hh);

		TableColumn column = null;
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(110);
		column.setMinWidth(50);
		column.setMaxWidth(320);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(90);
		column.setMinWidth(50);
		column.setMaxWidth(300);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(intable);
//        new JPanel().add(new Button("Reset"))
  //      scrollPane.add();
        scrollPane.setPreferredSize(new Dimension(SetEnv.SizeB,190));
        //Add the scroll pane to this panel.
        add(scrollPane);
    }



}

