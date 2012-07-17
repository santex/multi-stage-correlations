package com.multistage.correlations.gui;

/*
 * ToolBarDemo2.java requires the following addditional files:
 * images/Back24.gif
 * images/Forward24.gif
 * images/Up24.gif
 */

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import com.algo.test.ClusterImplement;
import com.algo.test.URLConnectionReader;
import com.algo.test.focus;
import com.messingarround.SSH;
import com.multistage.correlations.utils.ClusterListImport;
import com.multistage.correlations.utils.ResourceLoader;
import com.multistage.correlations.utils.WatchListImport;
import com.multistage.correlations.clcontrol.Global;
import com.multistage.correlations.gui.SetEnv;

import java.net.URL;
import java.util.HashMap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserToolBar extends JPanel
                          implements ActionListener {
    protected JTextArea textArea;
    protected String newline = "\n";
    
    static final private String[] OPTIONS = new String[]{"Markets"
    	,"Import"
    	,"SSH",
    	 "ALL",
    	};
    private static JFrame frame;
    static final private String TEXT_ENTERED = "text";
    
    public UserToolBar() {
        super(new BorderLayout());

        //Create the toolbar.
        JToolBar toolBar = new JToolBar("Still draggable");
        addButtons(toolBar);
        //toolBar.setFloatable(false);
       // toolBar.setRollover(false);
        
        //Create the text area used for output.  Request
        //enough space for 5 rows and 30 columns.
        textArea = new JTextArea(5, 150);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Lay out the main panel.
        
        add(toolBar, BorderLayout.PAGE_START);
      //  add(scrollPane, BorderLayout.CENTER);
    }

    protected void addButtons(JToolBar toolBar) {
        JButton button = null;
        toolBar.addSeparator();
    
        int x=0;
        for(String o:OPTIONS){
        //first button
	        button = makeNavigationButton(o.toLowerCase(),
	                                      o+" controls",
	                                      o);
	        button.setMinimumSize(new java.awt.Dimension(140,42));
	        toolBar.add(button);
	        
	        x++;
	        
        }
        //separator
        toolBar.addSeparator();
        
        //fifth component is NOT a button!
        JTextField textField = new JTextField("");
     //   textField.setPreferredSize(new Dimension(30,100));
        textField.setPreferredSize(new java.awt.Dimension(90,30));
        textField.setMinimumSize(new java.awt.Dimension(60,30));
        textField.setMaximumSize(new java.awt.Dimension(120,30));
        textField.setColumns(12);
        textField.addActionListener(this);
        textField.setActionCommand(TEXT_ENTERED);
        toolBar.addSeparator();
        toolBar.add(textField);
        toolBar.addSeparator();
    }

    protected JButton makeNavigationButton(String actionCommand,
                                           String toolTipText,
                                           String altText) {
        JButton button = new JButton(altText);
        
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        

        
        
        return button;
    }

	void openScope() {

		new ScopeDialog();

	}
	
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String description = null;
        
        
       ACTIONS:
        for(String o:OPTIONS){
            //first button
        	if (o.equals(cmd)) { //first button clicked
                description = o+" <something>.";
                break ACTIONS; 
            }
    	        
        }
        
        if(TEXT_ENTERED.equals(cmd)){
        JTextField tf = (JTextField)e.getSource();
        String text = tf.getText();
        tf.setText(description);
        tf.setColumns(12);
        
        }
        SetEnv.setScope(e.paramString());
        
        
        if(cmd.equalsIgnoreCase("Import")){      	
        	WatchListImport importw = new WatchListImport(Global.Header(),Global.Header().split("\n"));
        	importw.send();
			 System.out.println(cmd+" "+Global.Header());
			 int ix=0;
			 for(int i :new int[Global.Ncluster]){
				 System.out.println(cmd+" "+SetEnv.matrix+" "+" "+SetEnv.USER+" "+Global.getLocations(ix++));
			 }
			
        }else if(cmd.contains("ssh")){      	
            new SSH().main(null);
        }else if(cmd.contains("all")){
        	ClusterListImport importw = new ClusterListImport(Global.Header(),Global.Header().split("\n"));
        	importw.send();
			 System.out.println(cmd+" "+Global.Header());
			 int ix=0;
			 for(int i :new int[Global.Ncluster]){
				 System.out.println(cmd+" "+SetEnv.matrix+" "+" "+SetEnv.USER+" "+Global.getLocations(ix++));
			 }
            //new SSH().main(null);
        }else{
        	openScope();        
        }
        
        
        displayResult("If this were a real app, it would have "
                        + description);
    }

    protected void displayResult(String actionDescription) {
        textArea.append(actionDescription + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    
    public JFrame getFrame() {
    	
    	createAndShowGUI();
    	return frame;
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("ToolBarDemo2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new java.awt.Dimension(220,32));
        
        //Add content to the window.
        frame.add(new UserToolBar(),BorderLayout.CENTER);
        

        //Display the window.
        frame.pack();
     ///   frame.setVisible(true);
    }
    
    public static void main(String[] args) {
	//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
	       	createAndShowGUI();
            }
        });
    }
}
