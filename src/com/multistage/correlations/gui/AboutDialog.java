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
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private JButton closeButton;

	private JPanel panel1;

	private JPanel panel2;

	public AboutDialog(MainFrame win) {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setTitle("About");
		setModal(true);
		setResizable(true);

		// Get the system resolution
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();

		// make sure the dialog is not too big
		int xsize=(int)(0.90*res.width);
		int ysize=(int)(0.95*res.height); 
		Dimension size = new Dimension(xsize, ysize);
		// Dimension size = new Dimension(Math.min(400, res.width), Math.min(300,
		//		res.height));

		setSize(size);
		// setLocationRelativeTo(parent);

		JPanel topPanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		lowerPanel.setPreferredSize(new Dimension(400, 35));

		closeButton = new JButton();
		closeButton.setText("Exit");
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});

		lowerPanel.add(closeButton, null);
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel, java.awt.BorderLayout.CENTER);
		getContentPane().add(lowerPanel, java.awt.BorderLayout.SOUTH);

		tabbedPane = new javax.swing.JTabbedPane();
		// Create the tab pages
		createPage1();
		createPage2();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("About", panel1);
		tabbedPane.addTab("License", panel2);
		topPanel.add(tabbedPane, BorderLayout.CENTER);

		Util.centreWithin(win, this);

		// set visible and put on center
		this.setVisible(true);
	}

	// create about
	public void createPage1() {

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		JEditorPane epane = new JEditorPane();
		epane.setOpaque(false);
		epane.setContentType("text/html;charset=ISO-8859-1");
		epane.setAutoscrolls(true);
		epane.setEditable(false);
		String hstr = ResourceLoader.getCommand("html.about");
		URL page = ResourceLoader.getResourceURL("#standards/" + hstr);

		try {
			epane.setPage(page);
		} catch (Exception e) {
			System.err.println("Couldn't create URL: " + hstr);
			epane.setContentType("text/plain");
		}

		JScrollPane jsp = new JScrollPane(epane);
		panel1.add(jsp);

	}

	// create about
	public void createPage2() {

		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JEditorPane epane = new JEditorPane();
		epane.setOpaque(false);
		epane.setContentType("text/html;charset=ISO-8859-1");
		epane.setAutoscrolls(true);
		epane.setEditable(false);

		String hstr = ResourceLoader.getCommand("html.license");
		URL page = ResourceLoader.getResourceURL("#standards/" + hstr);

		try {
			epane.setPage(page);
		} catch (Exception e) {
			System.err.println("Couldn't create URL: " + hstr);
			epane.setContentType("text/plain");
		}

		JScrollPane jsp = new JScrollPane(epane);
		panel2.add(jsp);

	}

}
