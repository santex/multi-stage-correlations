package com.multistage.correlations.clcontrol;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.multistage.correlations.cluster.*;
import com.multistage.correlations.gui.*;


public class ClControl extends JPanel {

	public static final long serialVersionUID = 2103;

	private JButton runKM;

	private JButton clearKM;

	private JButton showResult;

	private JPanel jInfoPanel;

	private JPanel jSetPanel;

	private JPanel jShowPanel;

	private JComponent jFinancePanel;
	
	public SummaryPad m_SummaryPad;

	private ClearAnalyser m_ClearAnalyser = null;

	private RunAnalyser m_RunAnalyser = null;
	
	public ActionListener runAction;


	/**
	 * Control panel for clustering
	 * 
	 */
	public ClControl() {

		this.setLayout(new FlowLayout());
		Global.init();

		m_SummaryPad = new SummaryPad();

		// Create a new table instance
		jInfoPanel = new JPanel();
		jInfoPanel.setLayout(new BorderLayout());
		jInfoPanel.setPreferredSize(new Dimension(((int)(SetEnv.SizeB+0.1)),(int) (0.15 * SetEnv.SizeY)));

		// Create a new table instance
		jFinancePanel = new JPanel();
		jFinancePanel.setLayout(new BorderLayout());
		jFinancePanel.setPreferredSize(new Dimension(SetEnv.SizeB,(int) (0.08 * SetEnv.SizeY)));

		// Create a new table instance
		jSetPanel = new JPanel();
		jSetPanel.setLayout(new BorderLayout());
		jSetPanel.setPreferredSize(new Dimension(SetEnv.SizeB,(int) (0.15 * SetEnv.SizeY)));

		// Create a new table instance
		jShowPanel = new JPanel();
		jShowPanel.setLayout(new BorderLayout());
		jShowPanel.setPreferredSize(new Dimension(SetEnv.SizeB,
				(int) (0.08 * SetEnv.SizeY)));

		// put table
		jSetPanel.add(Global.jSetting, java.awt.BorderLayout.CENTER);

		// put table
		jShowPanel.add(Global.jShowOption, java.awt.BorderLayout.CENTER);

		// put table
		jInfoPanel.add(Global.jInfo, java.awt.BorderLayout.CENTER);
		// put table
		jFinancePanel.add(Global.jFinanceOption, java.awt.BorderLayout.CENTER);

		runKM = new JButton("Run");
		runKM.setToolTipText("Run Kmeans for one pass");
		runKM.setFont(SetEnv.FontBold);
		runKM.setPreferredSize(new java.awt.Dimension(
				(int) (0.48 * SetEnv.SizeB), 25));
		this.add(runKM);

		ActionListener runAction = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					ClControl.this.go();
			}
		};
		// show jet settings
		runKM.addActionListener((java.awt.event.ActionListener) runAction);

		// clear
		clearKM = new JButton("Clear");
		clearKM.setToolTipText("Clear all clusters");
		clearKM.setFont(SetEnv.FontBold);
		clearKM.setPreferredSize(new java.awt.Dimension(
				(int) (0.48 * SetEnv.SizeB), 25));
		this.add(clearKM);

		// show jet settings
		clearKM.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				
				if ( SetEnv.DATA != null ) {
				m_ClearAnalyser = new ClearAnalyser("ClearAnalyser thread");
				if (!m_ClearAnalyser.Alive()) {
					m_ClearAnalyser.Start();
				}

				} else {
					 JOptionPane dialogError = new JOptionPane();
				     JOptionPane.showMessageDialog(dialogError,"Data not loaded yet!","Error",
				     JOptionPane.ERROR_MESSAGE);
	
				}
				
				
			}
		});

		this.add(jSetPanel);
		this.add(jShowPanel);
		this.add(jInfoPanel);
		//this.add(jFinancePanel);
		

		// results
		showResult = new JButton("show result");
		showResult.setToolTipText("Open a window with final results");
		showResult.setFont(SetEnv.FontBold);
		showResult.setPreferredSize(new java.awt.Dimension(SetEnv.SizeB, 25));
		this.add(showResult);
		// show jet settings
		showResult.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {


				if ( SetEnv.DATA != null ) {
					m_SummaryPad.setVisible(true);
				} else {
					 JOptionPane dialogError = new JOptionPane();
				     JOptionPane.showMessageDialog(dialogError,"Data not loaded yet!","Error",
				     JOptionPane.ERROR_MESSAGE);
	
				}
				

			}
		});

		this.setVisible(true);
	}

	
	public void go() {
		
		if ( SetEnv.DATA != null ) {
			
			SetEnv.DESC=SetEnv.DESC.replaceAll("null","").replaceAll("\n\n","\n")+"\n";
			
			m_RunAnalyser = new RunAnalyser("RunAnalyser thread");
			if (!m_RunAnalyser.Alive()) {
				m_RunAnalyser.Start();
			}

			if (m_RunAnalyser.Joint()) {
				m_SummaryPad.setVisible(false);
				Global.doneAnalysis=true;
				if(SetEnv.RUNOPTIONS[0] && !SetEnv.MARKET.toLowerCase().contains("option")){
					SetEnv.MARKET=SetEnv.MARKET+" option=+ONLY".toUpperCase();
				}
				m_SummaryPad.Set(SetEnv.DESC+SetEnv.MARKET+Global.summary.trim());
			}
			
			
			
			
		} else {
			 JOptionPane dialogError = new JOptionPane();
		     JOptionPane.showMessageDialog(dialogError,"Data not loaded yet!","Error",
		     JOptionPane.ERROR_MESSAGE);

		}

	}


	
	
	/**
	 * Trigger some updates
	 *
	 */
	public void UpdateSettings() {

		Global.Niterations = 1000;
		Global.Fuzzines = 1.7;
		Global.Eps = 0.001;
		Global.SetSettings(Global.Ncluster, Global.Fuzzines,
				Global.Niterations, Global.Eps);
		if (SetEnv.Mode == 132)
			Global.SetSettings(-999, -999.0, -999, -999.);

	}

	

} // end class
