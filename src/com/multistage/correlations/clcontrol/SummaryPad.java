package com.multistage.correlations.clcontrol;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.multistage.correlations.gui.FontChooser;
import com.multistage.correlations.gui.SetEnv;

import java.io.*;


/**
 * Editor with summary
 * 
 * @author H.Geissler 18 May 2011
 * 
 */
public class SummaryPad extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// -- Components
	public padPanel pad;

	private JFileChooser mFileChooser = new JFileChooser(".");

	public JTextArea mEditArea;

	// -- Actions
	private Action mOpenAction;

	private Action mSaveAction;

	private Action mExitAction;

	private Action mFonts;

	private AbstractAction mExportAction;

	private AbstractAction mExportPerformanceAction;

	public String x = "";

	// =====================================================================
	// main
	public static void main(String[] args) {
		new SummaryPad().setVisible(true);
		
	}// end main

	// ==============================================================
	// constructor
	public SummaryPad() {
		createActions();
		pad = new padPanel();
		this.setContentPane(pad);
		this.setJMenuBar(createMenuBar());
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Export summary");
		this.setVisible(false);
		
		// this.setSize(400, 200);
		// Center the window
		/*
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * Dimension frameSize = this.getSize(); if (frameSize.height >
		 * screenSize.height) { frameSize.height = screenSize.height; } if
		 * (frameSize.width > screenSize.width) { frameSize.width =
		 * screenSize.width; } this.setLocation((screenSize.width -
		 * frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		 */

		this.pack();
	}// end constructor

	// not show
	public void NotShow() {

		this.setVisible(false);
	}

	// not show
	public void Show() {

		this.setVisible(true);
	}

	// set text
	public void Set(String s) {

		// mEditArea.setText(s);
		
		mEditArea.setText(s);

	}

	/*
	 * //Overridden so we can exit when window is closed protected void
	 * processWindowEvent(WindowEvent e) { super.processWindowEvent(e); if
	 * (e.getID() == WindowEvent.WINDOW_CLOSING) { NotShow(); } }
	 */

	// ///////////////////////////////////
	// ////////////////// class contentPanel
	public class padPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// ==========================================================
		// constructor
		padPanel() {
			// -- Create components.
			mEditArea = new JTextArea(40, 40);
			mEditArea.setFont(SetEnv.FontBold);
			mEditArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			// mEditArea.setFont(new Font("monospaced", Font.PLAIN, 14));
			JScrollPane scrollingText = new JScrollPane(mEditArea);
			Set(x+"Summary is not filled yet");
			
			// -- Do layout
			this.setLayout(new BorderLayout());
			this.add(scrollingText, BorderLayout.CENTER);
		}// end constructor
	}// end class contentPanel

	// ============================================================
	// createMenuBar
	/** Utility function to create a menubar. */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu fileMenu = menuBar.add(new JMenu("File"));
		JMenu exportMenu =menuBar.add(new JMenu("Export"));
		  exportMenu.add(mExportPerformanceAction); // Note use of actions, not text.
		  exportMenu.add(mExportAction);
	
		fileMenu.add(mOpenAction); // Note use of actions, not text.
		fileMenu.add(mSaveAction);
		fileMenu.add(mFonts);
		fileMenu.addSeparator();
		fileMenu.add(mExitAction);
		return menuBar;
	}// end createMenuBar

	// ============================================================
	// createActions
	/** Utility function to define actions. */
	private void createActions() {
		mOpenAction = new AbstractAction("Open...") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int retval = mFileChooser.showOpenDialog(SummaryPad.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File f = mFileChooser.getSelectedFile();
					try {
						FileReader reader = new FileReader(f);
						mEditArea.read(reader, ""); // Use TextComponent read
					} catch (IOException ioex) {
						System.out.println(e);
						// System.exit(1);
					}
				}
			}
		};

		mSaveAction = new AbstractAction("Save") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int retval = mFileChooser.showSaveDialog(SummaryPad.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File f = mFileChooser.getSelectedFile();
					try {
						FileWriter writer = new FileWriter(f);
						mEditArea.write(writer); // Use TextComponent write
					} catch (IOException ioex) {
						System.out.println(e);
						// System.exit(1);
					}
				}
			}
		};

		mExitAction = new AbstractAction("Exit") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				NotShow();
			}
		};

		mFonts = new AbstractAction("Fonts") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// NotShow();
				Font f = FontChooser.showDialog(SummaryPad.this, "Choose font",
						mEditArea.getFont());
				if (f != null) {
					mEditArea.setFont(f);
				}

			}
		};


		mExportAction = new AbstractAction("Export cluster flow") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int retval = mFileChooser.showSaveDialog(SummaryPad.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File f = mFileChooser.getSelectedFile();
					try {
						FileWriter writer = new FileWriter(f);
						mEditArea.write(writer); // Use TextComponent write
					} catch (IOException ioex) {
						System.out.println(e);
						// System.exit(1);
					}
				}
			}
		};

		mExportPerformanceAction = new AbstractAction("Export cluster performance") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int retval = mFileChooser.showSaveDialog(SummaryPad.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File f = mFileChooser.getSelectedFile();
					try {
						FileWriter writer = new FileWriter(f);
						mEditArea.write(writer); // Use TextComponent write
					} catch (IOException ioex) {
						System.out.println(e);
						// System.exit(1);
					}
				}
			}
		};

	}// end createActions
}// end class NutPad

