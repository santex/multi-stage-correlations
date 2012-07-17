
package com.multistage.correlations.gui;

import java.awt.*;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.*;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.ui.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.annotations.*;
import org.jfree.data.xy.*;
import org.jfree.data.contour.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.axis.*;

import com.multistage.correlations.clcontrol.Global;
import com.multistage.correlations.cluster.*;

/**
 * Interface to JFreeChart
 * 
 * @author H.Geissler 18 May 2011
 * 
 */

public class Plotter extends JPanel {

	public static final long serialVersionUID = 11214;

	private  JFreeChart chart;

	private   XYSeriesCollection dataset;
	private String[] labels = null;
	private XYPlot xyplot;

	private  ChartPanel chartPanel;

	private  DefaultContourDataset dataset2D;

	public DataHolder Ndata;

	public  int iiX;

	public  int iiY;

	private  double pwidth = 1.0;

	private  double phight = 1.0;

	/** Array of data points */
	private ArrayList<XYLineAnnotation> Centers;

	private ArrayList<XYLineAnnotation> Seeds;

	// for plotter
	public  int MarkSize = 4;

	public  Color MarkColor = Color.blue;

	public  boolean MarkSelect = false;

	public  Color MarkSelectColor = new Color(0x00aaaa);

	public Plotter() {

		this.setLayout(new BorderLayout());
		dataset = new XYSeriesCollection();

		this.Centers = new ArrayList<XYLineAnnotation>();
		this.Seeds = new ArrayList<XYLineAnnotation>();

		chart = ChartFactory.createScatterPlot("Empty", // title
				"", "", dataset, // dataset
				PlotOrientation.VERTICAL, true, // legend? yes
				true, // tooltips? yes
				true // URLs? no
				);// axis
		// dataset = null;
		// xyplot = null;
		chart.setBackgroundPaint(Color.gray);
		chartPanel = new ChartPanel(chart);
		// chartPanel.setMouseZoomable(true, false);
		// chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setLayout(new BorderLayout());

		this.add(chartPanel);
		this.setVisible(true);
		
		
		

	}

	/**
	 * Clear data sets
	 * 
	 */
	public void clearData() {

		if (dataset != null) {
			if (dataset.getSeriesCount() > 0)
				dataset.removeAllSeries();
		}

		if (dataset2D != null) 
			if (dataset2D.getSeriesCount()>0) {
				dataset2D=null;
			}
		
		
	}

	/**
	 * Clear plot (final)
	 * 
	 */
	public void clearPlot() {

		if (dataset != null) {
			if (dataset.getSeriesCount() > 0)
				dataset.removeAllSeries();
		}

		
		if (dataset2D != null) 
			if (dataset2D.getSeriesCount()>0) {
				dataset2D=null;
			}
			

		
		if (chart != null) {
			chart.setTitle("Not loaded");
			chart.setBackgroundPaint(Color.gray);

			xyplot = chart.getXYPlot();
			ValueAxis xx = xyplot.getDomainAxis();
			ValueAxis yy = xyplot.getRangeAxis();
			xx.setLabel("");
			yy.setLabel("");
		}

	}

	/**
	 * draw all points for the DataHolder and raw iX and iY
	 * 
	 */
	public void drawPoints() {

		// System.out.println("Draw points");
		clearData();
		XYSeries SerData = new XYSeries("Data");

		for (int i = 0; i < SetEnv.NRow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			double x = dp.getAttribute(SetEnv.JboxX);
			double y = dp.getAttribute(SetEnv.JboxY);
			
			SerData.add(x, y);
		}
		dataset.addSeries(SerData);

		chart = ChartFactory.createScatterPlot(SetEnv.MARKET, // title /.Mtitle
				SetEnv.XYtitle[SetEnv.JboxX], SetEnv.XYtitle[SetEnv.JboxY], // axis
				// labels
				dataset, // dataset
				PlotOrientation.VERTICAL, true, // legend? yes
				true, // tooltips? yes
				true // URLs? no
				);
		xyplot = chart.getXYPlot();
		xyplot.getRenderer().setSeriesPaint(0, Color.black);
		chart.setBackgroundPaint(Color.white);
		chartPanel.setChart(chart);
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		pwidth = 10;
		phight = 20;

		ValueAxis xx = xyplot.getDomainAxis();
		ValueAxis yy = xyplot.getRangeAxis();
		pwidth = xx.getRange().getLength();
		phight = yy.getRange().getLength();
		// System.out.println(pwidth);
		// System.out.println(phight);

	}

	public void drawContour() {

		clearData();
		chart = null;

		int BinX = 30; // number of bins (excluding underflow and overflow
		// bins)
		int BinY = 30; // number of bins (excluding underflow and overflow
		// bins)

		Double Mi[] = new Double[SetEnv.Dim];
		Double Ma[] = new Double[SetEnv.Dim];

		for (int m = 0; m < SetEnv.Dim; m++) {
			Mi[m] = SetEnv.Min.getAttribute(m);
			Ma[m] = SetEnv.Max.getAttribute(m);
		}

		double MinX = Mi[SetEnv.JboxX]; // and the lower edge of bin i
		double MinY = Mi[SetEnv.JboxY]; // and the lower edge of bin i

		double MaxX = Ma[SetEnv.JboxX]; // and the lower edge of bin i
		double MaxY = Ma[SetEnv.JboxY]; // and the lower edge of bin i

		double BinWidthX = (MaxX - MinX) / (double) BinX; // and its width
		double BinWidthY = (MaxY - MinY) / (double) BinY; // and its width

		int AllMax = BinX * BinY;
		Double xValues[] = new Double[AllMax];
		Double yValues[] = new Double[AllMax];
		Double zValues[] = new Double[AllMax];

		int nn = 0;
		for (int i = 0; i < BinX; i++) {
			for (int j = 0; j < BinY; j++) {

				double dX = MinX + BinWidthX * i;
				double dY = MinY + BinWidthY * j;

				xValues[nn] = dX + 0.5 * BinWidthX;
				yValues[nn] = dY + 0.5 * BinWidthY;

				int kk = 0;
				for (int m = 0; m < SetEnv.NRow; m++) {
					DataPoint dp = SetEnv.DATA.getRaw(m);
					double x = dp.getAttribute(SetEnv.JboxX);
					double y = dp.getAttribute(SetEnv.JboxY);
					if (x > dX && x < dX + BinWidthX && y > dY
							&& y < dY + BinWidthY)
						kk++;
				}
				zValues[nn] = (double) kk;
				nn++;
			}
		}

		// for (int m = 0; m<nn; m++) {
		// System.out.println(zValues[m]);
		// }

		dataset2D = new DefaultContourDataset();
		dataset2D.initialize(xValues, yValues, zValues);

		// make plot
		ColorBar Cbar = new ColorBar("Density");

		NumberAxis dAxis = new NumberAxis(SetEnv.XYtitle[SetEnv.JboxX]);
		NumberAxis vAxis = new NumberAxis(SetEnv.XYtitle[SetEnv.JboxY]);
		dAxis.setLowerBound(MinX);
		dAxis.setUpperBound(MaxX);

		vAxis.setLowerBound(MinY);
		vAxis.setUpperBound(MaxY);

		ContourPlot xyzplot = new ContourPlot(dataset2D, dAxis, vAxis, Cbar);
		xyzplot.setColorBarLocation(RectangleEdge.RIGHT);
		chart = new JFreeChart(SetEnv.Mtitle, JFreeChart.DEFAULT_TITLE_FONT,
				xyzplot, false);
		chart.setBackgroundPaint(Color.white);
		chartPanel.setChart(chart);
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);

	}

	/**
	 * Add series
	 * 
	 */
	public void addSet(String name, DataHolder hold) {

		XYSeries SerData = new XYSeries(name);

		labels =new String[hold.getSize()];
		for (int i = 0; i < hold.getSize(); i++) {
			DataPoint dp = hold.getRaw(i);
			double x = dp.getAttribute(SetEnv.JboxX);
			double y = dp.getAttribute(SetEnv.JboxY);
			labels[i]=dp.getLabel();
			SerData.add(x, y);
		}
		dataset.addSeries(SerData);

	}

	/**
	 * Remove centers
	 * 
	 */
	public void removeCenters() {

		xyplot = chart.getXYPlot();
		for (int i = 0; i < Centers.size(); i++) {
			// Object = Centers.get(i)
			xyplot.removeAnnotation(Centers.get(i));
		}
		Centers.clear();
	}

	/**
	 * Remove seeds
	 * 
	 */
	public void removeSeeds() {

		xyplot = chart.getXYPlot();
		for (int i = 0; i < Seeds.size(); i++) {
			// Object = Centers.get(i)
			xyplot.removeAnnotation(Seeds.get(i));
		}
		Seeds.clear();
	}

	/**
	 * Add series
	 * 
	 */
	public void showCenters(String name, DataHolder hold) {

		xyplot = chart.getXYPlot();
		for (int i = 0; i < hold.getSize(); i++) {
			DataPoint dp = hold.getRaw(i);
			double x = dp.getAttribute(SetEnv.JboxX);
			double y = dp.getAttribute(SetEnv.JboxY);
			double r = 0.015 * pwidth;
			double rr = r * phight / pwidth; // correct to take into account
												// aspect
			//Ellipse2D e = new Ellipse2D.Double(x-r,y-rr, 2*r, 2*rr);
			 //Rectangle2D e= new Rectangle2D.Double(x-r, y-r,2*r, 2*r);
			//XYShapeAnnotation pointer = new XYShapeAnnotation(e, new
			// BasicStroke(2.0f), Color.red);
			XYLineAnnotation a1 = new XYLineAnnotation(x - r, y, x + r, y,
					new BasicStroke(5.0f), Color.red);
			XYLineAnnotation a2 = new XYLineAnnotation(x, y - rr, x, y + rr,
					new BasicStroke(5.0f), Color.red);
			//a1.setToolTipText(name + Integer.toString(i + 1));
			//a2.setToolTipText(name + Integer.toString(i + 1));
			a1.setToolTipText(name + Integer.toString(i + 1));
			a2.setToolTipText(name + Integer.toString(i + 1));
			Centers.add(a1);
			Centers.add(a2);
			xyplot.addAnnotation(a1);
			xyplot.addAnnotation(a2);

			XYPointerAnnotation pointer = new XYPointerAnnotation(name,x,y,5);
			pointer.setTipRadius(5.0);
			String location = Global.getLocations(i);
			String[] test = location.split(" ");
			//for(String xxx: ()){}
		//pointer.setArrowStroke(new BasicStroke(5.0f));
			
			if(test!=null)
				if(test.length>1){
				
			pointer.setText(String.format("%s / +%s more",test[1],(test.length-2)));
			}
			pointer.setBaseRadius(35.0);
			pointer.setPaint(Color.red);
			xyplot.addAnnotation(pointer);
		}
		
		try {
			createChart(xyplot,"out.png","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Add series
	 * 
	 */
	public void showSeeds(String name, DataHolder hold) {

		xyplot = chart.getXYPlot();
		for (int i = 0; i < hold.getSize(); i++) {
			DataPoint dp = hold.getRaw(i);
			double x = dp.getAttribute(SetEnv.JboxX);
			double y = dp.getAttribute(SetEnv.JboxY);

			double r = 0.015 * pwidth;
			double rr = r * phight / pwidth; // correct to take into account
												// aspect
			// Ellipse2D e = new Ellipse2D.Double(x-r,y-r, 2*r, 2*r);
			// Rectangle2D e= new Rectangle2D.Double(x-r, y-r,2*r, 2*r);
			// XYShapeAnnotation pointer = new XYShapeAnnotation(e, new
			// BasicStroke(2.0f), Color.red);
			XYLineAnnotation a1 = new XYLineAnnotation(x - r, y, x + r, y,
					new BasicStroke(2.0f), Color.green);
			XYLineAnnotation a2 = new XYLineAnnotation(x, y - rr, x, y + rr,
					new BasicStroke(2.0f), Color.green);
			a1.setToolTipText(name + Integer.toString(i + 1));
			a2.setToolTipText(name + Integer.toString(i + 1));
			Seeds.add(a1);
			Seeds.add(a2);
			xyplot.addAnnotation(a1);
			xyplot.addAnnotation(a2);

			// XYPointerAnnotation pointer = new
			// XYPointerAnnotation(name,x,y,0);
			// pointer.setTipRadius(10.0);
			// pointer.setBaseRadius(35.0);
			// pointer.setPaint(Color.red);
			// xyplot.addAnnotation(pointer);
		}

	}

	public void showPlot() {

		chart = ChartFactory.createScatterPlot(SetEnv.MARKET, // title
				SetEnv.XYtitle[SetEnv.JboxX], 
				SetEnv.XYtitle[SetEnv.JboxY], // axis
				// labels
				dataset, // dataset
				PlotOrientation.VERTICAL, true, // legend? yes
				true, // tooltips? yes
				true // URLs? no
				);

		xyplot = chart.getXYPlot();
		XYItemRenderer renderer = xyplot.getRenderer();
		renderer.setSeriesPaint(0, Color.black);
		renderer.setSeriesPaint(1, Color.green);
		renderer.setSeriesPaint(2, Color.blue);
		renderer.setSeriesPaint(3, Color.magenta);
		renderer.setSeriesPaint(4, Color.cyan);
		renderer.setSeriesPaint(5, Color.pink);
		renderer.setSeriesPaint(6, Color.orange);
		renderer.setSeriesPaint(7, Color.darkGray);
		
		chart.setBackgroundPaint(Color.white);

		chartPanel.setChart(chart);
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		
	

		try {
			createChart(xyplot,SetEnv.MATRIXID.equalsIgnoreCase("")?"out.png":SetEnv.MATRIXID+".png","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		//chart.getBackgroundImage().getGraphics().getClipRect()

	}
	
	 private void createChart(XYPlot plot, String fileName, String caption) throws IOException {
		   Date dNow = new Date();

		    /* Simple, Java 1.0 date printing */
		    System.out.println("It is now " + dNow.toString());

		 // Use a SimpleDateFormat to print the date our way.
		    SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
		    //System.out.println("It is " + formatter.format(dNow));
		 // Use a SimpleDateFormat to print the date our way.
		    SimpleDateFormat formatterx = new SimpleDateFormat("yyyy-MM-dd");
		    //System.out.println("It is " + formatter.format(dNow));
		    
		 JFreeChart chart = new JFreeChart(caption, plot);
	      chart.addSubtitle(new TextTitle(formatter.format(dNow)+"-"+SetEnv.MATRIXID+"-"+SetEnv.MARKET));
	      File file = new File(System.getProperty("user.home")+File.separator+"mscorrelation",formatterx.format(dNow)+"_"+fileName);
	      file.delete();
	      ChartUtilities.saveChartAsPNG(file, chart,1100,560);
	   }

}
