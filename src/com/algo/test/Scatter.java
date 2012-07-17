package com.algo.test;
import java.awt.*;



import javax.swing.*;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import com.algo.test.Scatter.MarketDataModel;
import com.multistage.correlations.clcontrol.Global;
import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.gui.SetEnv;



/**
 * Demonstrates the use of a custom table model, and some other
 * configuration, for a JTable.  The program lets the user enter
 * (x,y) coordinates of some points, and it draws a simple
 * scatter plot of all the points in the table for which
 * both the x and the y coordinate is defined.  This class can
 * be run as a standalone application and has a nested class
 * that can be used to run the program as an applet.
 */
public class Scatter extends JPanel {
   
   static HashMap biff = new HashMap();
   public static ArrayList symbols;
   public static ArrayList syms;

   private JTable table;                // The JTable where the points are input.
   private CoordInputTableModel model;  // The TableModel for the table.
   private Displayx displayx;
   private Display display;
   private Information information; 
/**
    * The main routine simply opens a window that shows a ScatterPlotTableDemo panel.
    */
   public static void main(String[] args) {
	   
	  
      JFrame window = new JFrame("ScatterPlotTableDemo");
      CSVSimple csv = new CSVSimple();
      symbols = csv.exec();
 
      Scatter content = new Scatter(symbols, biff,new Information());
      window.setContentPane(content);
      window.pack();
      window.setResizable(false);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      window.setLocation( (screenSize.width - window.getWidth())/2,
            (screenSize.height - window.getHeight())/2 );
      window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      window.setVisible(true);
   }
   



/**
    * The public static class ScatterPlotTableDemo.Applet represents this program
    * as an applet.  The applet's init() method simply sets the content 
    * pane of the applet to be a ScatterPlotTableDemo.  To use the applet on
    * a web page, use code="ScatterPlotTableDemo$Applet.class" as the name of
    * the class.
    */
   public static class Applet extends JApplet {
      public void init() {
    	  HashMap biff = new HashMap();
    	  try {
    		  URLConnectionReader xxx = new URLConnectionReader();
    		  xxx.prepareAll(null);
    		  biff=xxx.exec();
    		  
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}

  		CSVSimple csv = new CSVSimple();
  	  		
  	  	//symbols = csv.exec();
  	  	symbols=(ArrayList) biff.keySet();
  	  	
        ClusterImplement cluster = new ClusterImplement(biff);
        JScrollPane xf = cluster.prepareMain();

        Scatter content = new Scatter(symbols,biff,new Information());
        setContentPane( content );
      }
   }

   

   public void testProduction(){

   	int count = 0;

   	
	    	HashMap<String,Double> xmap = biff;
	    	ValueComparator bvc =  new ValueComparator(xmap,0);
	    	TreeMap<String,Double> sorted_map = new TreeMap(bvc);
	    	sorted_map.putAll(xmap);
	    	//System.out.println("results");
	
	    	count = 0;


	    	SORT1:
	    	for (String key : sorted_map.keySet()) {
	    		//System.out.println(" symbol/value: " + key + "/"+sorted_map.get(key));
		
		    	if(count>2000){
		    		break SORT1;
		    	}
	
	
		    	count++;
	    	}
   	
   }	


   

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

   /**
    * This class defines the TableModel that is used for the JTable in this
    * program.  The table has three columns.  Column 0 simply holds the
    * row number of each row.  Column 1 holds the x-coordinates of the
    * points for the scatter plot, and Column 2 holds the y-coordinates.
    * The table has 25 rows.  No support is provided for adding more rows.
    */


   private class Displayx extends JPanel {
      public void paintComponent(Graphics g) {  
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         double min = -0.5;  // Minimum of the range of values displayed.
         double max = 5;     // Maximum of the range of value displayed.
         int count = model.getRowCount();
         for (int i = 0; i < count; i++) {
            Double x = (Double)model.getValueAt(i, 2);  // (Return type of getValue() is Object.)
            Double y = (Double)model.getValueAt(i, 3);
            if (x != null && y != null) {
               // Adjust max and min to include x and y.
               if (x < min)
                  min = x - 0.5;
               if (x > max)
                  max = x + 0.5;
               if (y < min)
                  min = y - 0.5;
               if (y > max)
                  max = y + 0.5;
            }
         }

         /* Apply a translation so that the drawing coordinates on the display
               correspond to the range of values that I want to show. */

         g2.translate(getWidth()/2,getHeight()/2);
         g2.scale(getWidth()/(max-min), -getHeight()/(max-min));
         g2.translate(-(max+min)/2, -(max+min)/2);

         /* I want to be able to draw lines that are a certain number of pixels
               long.  Unfortunately, the unit of length is no longer equal to the
               size of a pixel, so I have to figure out how big a pixel is in the
               new coordinates.  Also, horizontal and vertical size can be different. */

         double pixelWidth = (max-min)/getWidth();    // Horizontal size of a pixel in new coords.
         double pixelHeight = (max-min)/getHeight();  // Vertical size of a pixel in new coord.

         /* When the thickness of a BasicStroke is set to 0, the actual width of
               the stroke will be as small as possible, that is, one pixel wide. */

         g2.setStroke(new BasicStroke(0));

         /* Draw x and y axes with tick marks to mark the integers (but don't draw
               the tick marks if there would be more than 100 of them. */

         g2.setColor(Color.BLUE);
         g2.draw( new Line2D.Double(min,0,max+50,0));
         g2.draw( new Line2D.Double(0,min,0,max+50));
         if (max - min < 100) {
            int tick = (int)min;
            while (tick <= max) {
               g2.draw(new Line2D.Double(tick,0,tick,3*pixelHeight));
               g2.draw(new Line2D.Double(0,tick,3*pixelWidth,tick));
               tick++;
            }
         }

         /* Draw a small crosshair at each point from the table. */

         g2.setColor(Color.RED);
         for (int i = 0; i < count; i++) {
            Double x = (Double)model.getValueAt(i, 2);
            Double y = (Double)model.getValueAt(i, 3);
            if (x != null && y != null) {
               g2.draw(new Line2D.Double(x-3*pixelWidth,y,x+3*pixelWidth,y));
               g2.draw(new Line2D.Double(x,y-3*pixelHeight,x,y+3*pixelHeight));
            }
         }

      }
   } // end nested class Display
   
   /**
    * Defines the display area where a scatter plot of the points
    * in the table is drawn.  The range of values shown in the plot
    * is adjusted to make sure that all the points are visible.
    * Note that only points for which both coordinates are
    * defined are drawn.
    */
   private class Display extends JPanel {

       double ew = 10 / 2;
       double eh = 10 / 2;
       Ellipse2D.Double circle, circle1;
       DataHolder Centers;
       DataHolder sets;
      public void paintComponent(Graphics g) {  
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;
         Graphics g3 = (Graphics)g;

         double min = -0.5;  // Minimum of the range of values displayed.
         double max = 5;     // Maximum of the range of value displayed.
         int count = model.getRowCount();
         
         for (int i = 0; i < count; i++) {
        	if(i==0){
        		
        	}
            Double x = (Double)model.getValueAt(i, 2);  // (Return type of getValue() is Object.)
            Double y = (Double)model.getValueAt(i, 3);
            if (x != null && y != null) {
               // Adjust max and min to include x and y.
               if (x < min)
                  min = x - 0.5;
               if (x > max)
                  max = x + 0.5;
               if (y < min)
                  min = y - 0.5;
               if (y > max)
                  max = y + 0.5;
            }
         }

         /* Apply a translation so that the drawing coordinates on the display
               correspond to the range of values that I want to show. */

         g2.translate(getWidth()/2,getHeight()/2);
         g2.scale(getWidth()/(max-min), -getHeight()/(max-min));
         g2.translate(-(max+min)/2, -(max+min)/2);

         /* I want to be able to draw lines that are a certain number of pixels
               long.  Unfortunately, the unit of length is no longer equal to the
               size of a pixel, so I have to figure out how big a pixel is in the
               new coordinates.  Also, horizontal and vertical size can be different. */

         double pixelWidth = (max-min)/getWidth();    // Horizontal size of a pixel in new coords.
         double pixelHeight = (max-min)/getHeight();  // Vertical size of a pixel in new coord.

         /* When the thickness of a BasicStroke is set to 0, the actual width of
               the stroke will be as small as possible, that is, one pixel wide. */

         g2.setStroke(new BasicStroke(0));

         /* Draw x and y axes with tick marks to mark the integers (but don't draw
               the tick marks if there would be more than 100 of them. */

         g2.setColor(Color.BLUE);
         g2.draw( new Line2D.Double(min,0,max,0));
         g2.draw( new Line2D.Double(0,min,0,max));
         if (max - min < 100) {
            int tick = (int)min;
            while (tick <= max) {
               g2.draw(new Line2D.Double(tick,0,tick,3*pixelHeight));
               g2.draw(new Line2D.Double(0,tick,3*pixelWidth,tick));
               tick++;
            }
         }

         /* Draw a small crosshair at each point from the table. */
         
	if(information != null){
			Centers= information.part.getCenters();
         	sets  = information.data;
         	//int x = 0;
         	if(SetEnv.Mode==121)
			for(Object o :Centers.getArrayList().toArray())
			{

				 
					DataPoint out = (DataPoint) o;
				    circle = new Ellipse2D.Double(out.getAttribute(0),out.getAttribute(1), 6.0, 6.0);
				    g2.setColor(Color.green);
				    g2.fill(circle);
				    
				//System.out.println(out.toString()+" <<<<<<<<<<<<<<<<<");
			}
					
					
	         /* Draw a small crosshair at each point from the table. */

	         g2.setColor(Color.RED);
	         for (int i = 0; i < count; i++) {
	            Double x = (Double)model.getValueAt(i, 2);
	            Double y = (Double)model.getValueAt(i, 3);
	            if (x != null && y != null) {
	               g2.draw(new Line2D.Double(x-3*pixelWidth,y,x+3*pixelWidth,y));
	               g2.draw(new Line2D.Double(x,y-3*pixelHeight,x,y+3*pixelHeight));
	            }
	         }
	         
         g2.setColor(Color.RED);
         DataPoint[] element = sets.getArray();
         for (int i = 0; i < element.length; i++) {
        	
            Double x = (Double)model.getValueAt(i, 2);//element[i].getAttribute(0);//
            Double y = (Double)model.getValueAt(i, 3);
            
           // System.out.println(x+" "+y);
            //if (x != null && y != null) {
            	circle = new Ellipse2D.Double(x,y, 0.25,0.25);
			    
               double y1 = y;
               double x1 = x;
               double y2 = y+1;
               double x2 = x+1;
               boolean raised=true;
               g.setColor(Color.RED);
               //g2.fill3DRect(300,300,3,3,true);
            }
         }

      }
   } // end nested class Display
   
               // The panel where the scatter plot is drawn.
   
   public Scatter(ArrayList symbols2, HashMap inbiff, Information information) {
      
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

      this.information =information;
      
      HashMap dataFinance = new HashMap();
      if(symbols2!=null){
    		  symbols2.clear();
      }
	try {
/*		URLConnectionReader urlConn = new URLConnectionReader();
		//urlConn.prepareAll();
		dataFinance = urlConn.exec();
		
		for(Object s :dataFinance.keySet()){
		//	if(symbols2.size()<80){
				symbols2.add(s.toString());
			//}
		}
*/
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	

		
	
      this.symbols = symbols2;
      biff = dataFinance;
      inbiff=dataFinance;
      //prepareData();
      MarketDataModel modelx = new MarketDataModel(10);
      model = new CoordInputTableModel();
      model.setupdata();
      model.setTablexData(this.symbols);
      table = new JTable(model);
      table.setRowHeight(25);
      table.setShowGrid(true);
      table.setGridColor(Color.BLACK);
      table.setPreferredScrollableViewportSize(new Dimension(240, 300));
      table.getColumnModel().getColumn(0).setPreferredWidth(60);
      table.getColumnModel().getColumn(1).setPreferredWidth(60);
      table.getColumnModel().getColumn(2).setPreferredWidth(60);
      table.getColumnModel().getColumn(3).setPreferredWidth(60);
      table.getTableHeader().setReorderingAllowed(true);
      add(new JScrollPane(table), BorderLayout.WEST);
      
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);

      table.setRowSorter(sorter);

      
      Object[] keys = dataFinance.keySet().toArray();
      for  (int i = 0; i < dataFinance.size(); i++) {  // Fill first 6 rows with random values.
    	  //se = biff.get(i);
    	  
    	  Object[] dd = (Object[]) dataFinance.get(keys[i]);
    	  

    	 //model.setValueAt( (int)(1150*Math.random())/100.0, i, 1 );
    	// System.out.println(dd.length+" "); 
         ///if(se!=null){
    		 
    		 //model.setValueAt( (int)(1150*Math.random())/100.0, i, 2 );
    		 //model.setValueAt( (int)(1150*Math.random())/100.0, i, 3 );
    		 

		 	 model.setValueAt(dd[1], i, 1 );
			 model.setValueAt(dd[3], i, 2 );
    		 model.setValueAt(dd[4], i, 3 );
    	 //}
      }
      display = new Display();
     // displayx = new Displayx();
      display.setPreferredSize(new Dimension(250,200));
      //displayx.setPreferredSize(new Dimension(450,200));
      display.setBackground(Color.WHITE);
      add(display, BorderLayout.CENTER);
      //add(displayx, BorderLayout.CENTER);
      display.repaint();
      model.addTableModelListener(new TableModelListener() {
            // Install a TableModelListener that will respond to any
            // changes in the model's data by redrawing the display that
            // shows the scatter plot.
         public void tableChanged(TableModelEvent e) {
            display.repaint();
            ///displayx.repaint();
         }
      });
   }


}
