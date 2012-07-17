
package com.multistage.correlations.gui;

/*
 * A application that requires the following files:
 *   ConversionPanel.java
 *   ConverterRangeModel.java
 *   FollowerRangeModel.java
 *   Unit.java
 */

import javax.swing.*;
import javax.swing.event.*;

import com.multistage.correlations.gui.models.ConverterRangeModel;
import com.multistage.correlations.gui.models.FollowerRangeModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Converter {
    ConversionPanel metricPanel, usaPanel;
    Unit[] metricDistances = new Unit[1];
    Unit[] usaDistances = new Unit[1];
    final static boolean MULTICOLORED = false;
    int i=0;
    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;

    ConverterRangeModel dataModel = new ConverterRangeModel();
    JPanel mainPane;

    /**
     * Create the ConversionPanels (one for metric, another for U.S.).
     * I used "U.S." because although Imperial and U.S. distance
     * measurements are the same, this program could be extended to
     * include volume measurements, which aren't the same.
     * @param i 
     */
    public Converter() {
       
    }

    
    public void render(){
    	 //Create Unit objects for metric distances, and then
        //instantiate a ConversionPanel with these Units.

        //Create Unit objects for U.S. distances, and then
        //instantiate a ConversionPanel with these Units.
    	if(i==0){
        usaDistances[0] = new Unit("min Market Cap M. USD",1d);
    	}else{
        usaDistances[0] = new Unit("max Market Cap M. USD",1d);
    	}
     //   usaDistances[1] = new Unit("Avg day Volume",100);
     //   usaDistances[2] = new Unit("Volume",100);
        
    	FollowerRangeModel fr = new FollowerRangeModel(dataModel);
    	fr.setDoubleValue(SetEnv.MARKETCAP);
    	usaPanel = new ConversionPanel(this, "Apply generic filter",usaDistances,fr);
        
        //usaPanel.sliderModel.setMultiplier();
        //usaPanel.controller.resetMaxValues(false);
        //usaPanel.sliderModel.setDoubleValue(SetEnv.MARKETCAP);
        
        //Create a JPanel, and add the ConversionPanels to it.
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        if (MULTICOLORED) {
            mainPane.setOpaque(true);
            mainPane.setBackground(new Color(255, 0, 0));
        }
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));

        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPane.add(usaPanel);
        mainPane.add(Box.createGlue());
        
    }
    public void resetMaxValues(boolean resetCurrentValues) {

        double usaMultiplier = usaPanel.getMultiplier();
        int maximum = ConversionPanel.MAX;


        dataModel.setMaximum(maximum);

        if (resetCurrentValues) {
            dataModel.setDoubleValue(maximum);
        }
        
        
    }

    private static void initLookAndFeel() {
        String lookAndFeel = null;

        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Converter converter = new Converter();
        converter.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(converter.mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

	public void setI(int j) {
		this.i=j;
		
		
	}

}
