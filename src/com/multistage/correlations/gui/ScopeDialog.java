package com.multistage.correlations.gui;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import com.multistage.correlations.utils.ResourceLoader;
import com.multistage.correlations.utils.Util;


/**
 * About dialog
 * 
 * @author H.Geissler 18 May 2011
 * 
 */
public class ScopeDialog extends JDialog {

	
	public static JFrame frame;
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private JButton saveButton;

	private JPanel panel1;

	private JPanel panel2;

	private JPanel panel3;

	private JButton loadButton;

	public ScopeDialog() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setTitle(SetEnv.getScope());
		setModal(true);
		setResizable(true);

		// Get the system resolution
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();

		// make sure the dialog is not too big
		int xsize=(int)(0.8*res.width);
		int ysize=(int)(0.90*res.height); 
		Dimension size = new Dimension(xsize, ysize);
		// Dimension size = new Dimension(Math.min(400, res.width), Math.min(300,
		//		res.height));

		setSize(size);
		// setLocationRelativeTo(parent);

		JPanel topPanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		lowerPanel.setPreferredSize(new Dimension(400, 35));

		saveButton = new JButton();
		saveButton.setText("Close");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				setVisible(false);
				dispose();
			}
		});

		lowerPanel.add(saveButton, null);
		/*
		 * 		loadButton = new JButton();
		loadButton.setText("Load");
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				    ScopeLoad r = new ScopeLoad();
			        r.setScope(SetEnv.getScope());
			        Thread t = new Thread(r);
			        t.start();
			        
			}
		});
		
		lowerPanel.add(saveButton, null);
	*/	
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel, java.awt.BorderLayout.CENTER);
		getContentPane().add(lowerPanel, java.awt.BorderLayout.SOUTH);

		tabbedPane = new javax.swing.JTabbedPane();
		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Markets&Data", panel1);
		tabbedPane.addTab("Test&Analyse", panel2);
		tabbedPane.addTab("Feed&Export", panel3);
		topPanel.add(tabbedPane, BorderLayout.CENTER);

		//topPanel.add();
		
		
		if(SetEnv.getScope().contains("cmd=markets")){
		tabbedPane.setSelectedIndex(0);
		}else if(SetEnv.getScope().contains("cmd=test")){
			tabbedPane.setSelectedIndex(1);
		}else if(SetEnv.getScope().contains("cmd=feed")){
			tabbedPane.setSelectedIndex(2);
		}
		// set visible and put on center
		this.setVisible(true);
	}


	protected void saveScope() {
		// TODO Auto-generated method stub
		
	}


	// create about
	public void createPage1() {

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		JEditorPane epane = new JEditorPane();
		epane.setOpaque(false);
		epane.setAutoscrolls(true);
		epane.setEditable(false);
		
		panel1.add(new Scope());

	}

	// create about
	public void createPage2() {

		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JEditorPane epane = new JEditorPane();
		epane.setOpaque(false);
		epane.setAutoscrolls(true);
		epane.setEditable(false);

		

		JScrollPane jsp = new JScrollPane(epane);
		panel2.add(jsp);

	}
	public void createPage3(){

		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		JEditorPane epane = new JEditorPane();
		epane.setOpaque(false);
		epane.setAutoscrolls(true);
		epane.setEditable(false);

		

		JScrollPane jsp = new JScrollPane(epane);
		panel3.add(jsp);

	}

	

	  /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        //frame.add(new TextSamplerDemo());

        //Display the window.
        ScopeDialog sd = new ScopeDialog();
       sd.setVisible(true);
        
        
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
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
