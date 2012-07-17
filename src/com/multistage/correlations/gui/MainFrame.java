package com.multistage.correlations.gui;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.algo.test.Boxed;
import com.algo.test.focus;
import com.multistage.correlations.clcontrol.*;
import com.multistage.correlations.cluster.*;
import com.multistage.correlations.utils.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;


import java.util.*;

/**
 * <p>
 * Title: Main frame
 * </p>
 * 
 * <p>
 * Description: Create main frame
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * 
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class MainFrame extends JFrame implements Constants {

	public static final long serialVersionUID = 4123;

	private JPanel contentPane;

	private BorderLayout borderLayout1;

	private JMenuBar jMenuBar1;

	private JMenu jMenuFile;

	private JMenuItem jMenuFileExit;

	private JMenuItem jMenuFileOpen;
	
	private JMenuItem jMenuFileClose;
	
	////private Indicator indicator;

	private JMenuItem jMenuChangeMarket;
	
	private JMenuItem jMenuUploadPortfolio;
	
	private JMenu jMenuHelp;

	private JMenuItem jMenuHelpAbout;

	private JToolBar jToolBar;

	private JButton jButton1;

	private JButton jButton2;

	private JButton jButton3;
	
	private JButton jButton4;

	private JButton jButton5;

	private CSelector m_CSelector;
	
//	private FileUpload fileUpload;

	
	private JFrame dataMani;
	
	
	private static int Mode_old = 1;

	private JToolBar jToolBar1;

	private JPanel jPanel2;

	
	// public jminhep.kmeans.JPanKmeans jKmeans; // cluster pad;

	public ClControl m_ClControl;
	private JPanel infoPanel;

	private TableSummary jSummary;
	private TableSummary jSummary4;

	private JPanel jSumPanel;

	public static String file_input = "";

	public static String file_open = "";

	public static String file_save = "";

	// public static weka.gui.ViewerDialog viewtable;
	public static File afile;

	public static Vector<String> textX;

	public static Vector<String> textY;

	protected Thread m_IOThread;

	private JComboBox selX;

	private JComboBox selY;

	private JComboBox selStyle;
	private JComboBox selMatrix;
	
	private ThreadShow SThX=null;
	private ThreadShow SThY=null;
	public UpdateTimer task;
	private FSelector m_FSelector;

	private JToolBar jToolBar3;

	private JPanel jPanel3;

	private TableSummary jSummary1;

	private JPanel jSumPanel1;

	private TableSummary jSummary2;

	private TableSummary jSummary3;

	private JComboBox dataManipul;

	private JComboBox dataSource;

	private Object dataSourceOptions;

	public ThreadRefresh refresh;
	
	public URLMonitorPanel pannelMonitor;

//	private IndicatorSelect indicatorSelect;


	// public JLabel LstatusBar;

	public MainFrame(String input) {
		try {
			file_input = input;
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jbInit();
			
			try {
			    task= new UpdateTimer(new URL("http://www.google.com"),new ThreadRefresh(0, MainFrame.this));
			    task.timer.schedule(task, 0L,5000L);

	    	} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Component initialization.
	 * 
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {

		SetEnv.init();

		// enviromental variables com.multistage.correlations.resources
		ResourceLoader.init("com/multistage/correlations/resources/");
		jSummary4 = new TableSummary(4);
		//fileUpload = new FileUpload();
		m_CSelector = new CSelector();
		infoPanel = new JPanel();
		jMenuFileExit = new JMenuItem();
		jMenuFileOpen = new JMenuItem();
		jMenuFileClose = new JMenuItem();
		jMenuHelp = new JMenu();
		jMenuHelpAbout = new JMenuItem();
		jMenuChangeMarket = new JMenuItem();
		jMenuUploadPortfolio = new JMenuItem();
		pannelMonitor = new URLMonitorPanel();
		jToolBar = new JToolBar(null, JToolBar.HORIZONTAL);
		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jSummary = new TableSummary(0);
		jSummary1 = new TableSummary(1);
		jSummary2 = new TableSummary(2);
		jSummary3 = new TableSummary(3);
		jSummary4 = new TableSummary(4);

		jSumPanel = new JPanel();
		jSumPanel.setLayout(new BorderLayout());
		jSumPanel.setPreferredSize(new Dimension(SetEnv.SizeB,50));
		jSumPanel.add(jSummary, java.awt.BorderLayout.NORTH);

		
		jSumPanel1 = new JPanel();
		jSumPanel1.setLayout(new BorderLayout());
		jSumPanel1.setPreferredSize(new Dimension(SetEnv.SizeB,50));
		jSumPanel1.add(jSummary1, java.awt.BorderLayout.NORTH);

		borderLayout1 = new BorderLayout();
		jMenuBar1 = new JMenuBar();
		jMenuFile = new JMenu();
		jToolBar1 = new JToolBar(null, JToolBar.VERTICAL);
		jToolBar1.setLayout(new BorderLayout());

		// jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		textX = new Vector<String>();
		textY = new Vector<String>();
		textX.addElement("not set");
		textY.addElement("not set");
//		indicator = new Indicator();
	//	indicatorSelect = new IndicatorSelect();
		
		//
		jPanel2.add(m_CSelector);

		

		String[] dataSourceOptions = { "DataSourceUS", "DataSourceUK" , "Realtime"};

		dataSource = new JComboBox(dataSourceOptions);
		// selX.setBounds(20,10,100,25);
		dataSource.setSelectedIndex(0);
		dataSource.setPreferredSize(new java.awt.Dimension(200, 50));
		dataSource.setBorder(new TitledBorder(null, "DataSource",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));
		
		
		MyItemListenerStyle actionListenerStyle = new MyItemListenerStyle();
		MyItemListenerMatrix actionListenerMatrix = new MyItemListenerMatrix();

		
		/*	
		String[] doThings = {"All", "Positiv only", "Negative only" };

		dataManipul = new JComboBox(doThings);
		// selX.setBounds(20,10,100,25);
		dataManipul.setSelectedIndex(0);
		dataManipul.setPreferredSize(new java.awt.Dimension(200, 50));
		dataManipul.setBorder(new TitledBorder(null, "DataOptions",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));
		jPanel2.add(dataManipul);*/
		
		

//		dataManipul.addItemListener(new ListenerDataManipulation());
	////	dataSource.addItemListener(new ListenerDataSource());
		
		
		jPanel2.add(dataSource);

		String[] matrix = new  String[SetEnv.col.length];//{ "Points", "Density" };
		String[] matrixTitles = new  String[SetEnv.col.length];//{ "Points", "Density" };
		int xx=0;
		
		StringBuffer header = new StringBuffer();
		for(String[] colelem:SetEnv.col){
			String name="";
			header=new StringBuffer();
			header.append("Group condition\n\n");	
			for(String ele:colelem){
				name+=ele;
				
		
				
				
				header.append(SetEnv.nameNames.get(ele.toString())).append("="+ele).append("\n");
			  
				  
			}

			matrixTitles[xx]=SetEnv.namesM[xx].replaceAll("\\+","\n");//header.toString();
			matrix[xx++]=name.toString();
	     }
	    
		
		Global.matrixTitles=matrixTitles;
		Global.matrixName=matrixTitles[SetEnv.matrix];
		selMatrix = new JComboBox(matrix);
		// selX.setBounds(20,10,100,25);
		SetEnv.title=SetEnv.matrixTitles[SetEnv.matrix];
		selMatrix.setSelectedIndex(SetEnv.matrix);
		selMatrix.setPreferredSize(new java.awt.Dimension(200, 50));
		selMatrix.setBorder(new TitledBorder(null, "Matrix",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));
		
		
		selMatrix.addItemListener(actionListenerMatrix);
		jPanel2.add(selMatrix);
		String[] plots = { "Points", "Density" };
		selStyle = new JComboBox(plots);
		// selX.setBounds(20,10,100,25);
		selStyle.setSelectedIndex(0);
		selStyle.setPreferredSize(new java.awt.Dimension(200, 50));
		selStyle.setBorder(new TitledBorder(null, "Style",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));

		
		selX = new JComboBox(textX);
		// selX.setBounds(20,10,100,25);
		selX.setSelectedIndex(0);
		selX.setPreferredSize(new java.awt.Dimension(200, 50));
		selX.setBorder(new TitledBorder(null, "X:",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));
		jPanel2.add(selX);

		selY = new JComboBox(textY);
		selY.setBorder(new TitledBorder(null, "Y:",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0,
						12)));
		// selY.setBounds(20,10,100,25);
		selY.setSelectedIndex(0);
		selY.setPreferredSize(new java.awt.Dimension(200, 50));
		jPanel2.add(selY);

		selX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JComboBox cb = (JComboBox) evt.getSource();
				int nn = cb.getSelectedIndex();
				if (nn > -1 && SetEnv.JboxX != nn) {
					SetEnv.JboxX = nn;
					
					SThX = new ThreadShow(SetEnv.Style );
					if (!SThX.Alive()) SThX.Start();
					
					// redraw
					// if (SetEnv.Mode.equals("kmeans") ) jKmeans.Redraw();
				}
			}
		});

		selY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JComboBox cb = (JComboBox) evt.getSource();
				int nn = cb.getSelectedIndex();
				if (nn > -1 && SetEnv.JboxY != nn) {
					SetEnv.JboxY = nn;
					
					SThY = new ThreadShow(SetEnv.Style );
					if (!SThY.Alive()) SThY.Start();
					
					
				}
			}
		});

		
		
		jPanel2.add(selStyle);
	//	jPanel2.add(jSumPanel);
		//jPanel2.add(pannelMonitor.getFrame().getContentPane());
		
		selStyle.addItemListener(actionListenerStyle);		
		m_ClControl = new ClControl();
		//m_FinanceControl = new FinanceControl();
		/*
		 * jKmeans = new jminhep.kmeans.JPanKmeans(); jHcl = new
		 * jminhep.hcluster.JPanKmeans(); jCmeans = new
		 * jminhep.cmeans.JPanKmeans();
		 */

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(borderLayout1);
		setSize(new Dimension(SetEnv.SizeX, SetEnv.SizeY));
		setTitle("MultistageCorrelation");

		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(new javax.swing.border.EtchedBorder());

		// LstatusBar = new JLabel("MultistageCorrelation is active");
		infoPanel.add(SetEnv.statusbar, BorderLayout.WEST);

		MemoryMonitor memMon = new MemoryMonitor();
		memMon.setPreferredSize(new java.awt.Dimension(65, 24));
		memMon.setMinimumSize(new java.awt.Dimension(30, 10));
		infoPanel.add(memMon, BorderLayout.EAST);

		jMenuFile.setText("File");
		jMenuFileExit.setText("Exit");
		jMenuFileOpen.setText("Open");
		jMenuFileClose.setText("Close");
		jMenuChangeMarket.setText("Markets");
		jMenuUploadPortfolio.setText("Portfolio");
		
		
		jMenuFileExit
				.addActionListener(new MainFrame_jMenuFileExit_ActionAdapter(
						this));
	
		jMenuFileOpen
		.addActionListener(new MainFrame_jMenuFileOpen_ActionAdapter(
				this));
		
		jMenuFileClose
		.addActionListener(new MainFrame_jMenuFileClose_ActionAdapter(
				this));
		
		jMenuUploadPortfolio
		.addActionListener(new MainFrame_jMenuFileOpen_ActionAdapter(
				this));
		
		jMenuChangeMarket
		.addActionListener(new MainFrame_jMenuFileOpen_ActionAdapter(
				this));
		
		jMenuHelp.setText("Help");
		jMenuHelpAbout.setText("About");
		jMenuHelpAbout
				.addActionListener(new MainFrame_jMenuHelpAbout_ActionAdapter(
						this));
		
		jMenuBar1.add(jMenuFile);
		jMenuFile.add(jMenuFileOpen);
		jMenuFile.add(jMenuFileClose);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuFileExit);
		jMenuBar1.add(jMenuHelp);
		jMenuHelp.add(jMenuHelpAbout);
		
		setJMenuBar(jMenuBar1);

/*		jButton1.setIcon(OpeN);
		jButton1.setToolTipText("Open File");
		jToolBar.add(jButton1);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OpenFile();
			}
		});

		jButton2.setIcon(ClosE);
		jButton2.setToolTipText("Close File");
		jToolBar.add(jButton2);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeFile();
			}
		});

			
		jButton4.setIcon(MarketS);
		jButton4.setToolTipText("Markets");
		jToolBar.add(jButton4);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							// UIManager.setLookAndFeel(UIManager.
							// getSystemLookAndFeelClassName());
							UIManager.setLookAndFeel(UIManager
									.getCrossPlatformLookAndFeelClassName());

						} catch (Exception exception) {
							exception.printStackTrace();
						}


						new Boxed();

					}
				});

			}
		});

		
		jButton5.setIcon(PortF);
		jButton5.setToolTipText("Portfolio");
		jToolBar.add(jButton5);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							// UIManager.setLookAndFeel(UIManager.
							// getSystemLookAndFeelClassName());
							UIManager.setLookAndFeel(UIManager
									.getCrossPlatformLookAndFeelClassName());

						} catch (Exception exception) {
							exception.printStackTrace();
						}

						BaseUtil.progress(0);

					}
				});
			}
		});

		
*/	
		jButton3.setIcon(HelP);
		jButton3.setToolTipText("Help");
		jToolBar.add(jButton3);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openHelp();
			}
		});
		jToolBar.add(new UserToolBar().getFrame().getContentPane());
		contentPane.add(infoPanel, BorderLayout.SOUTH);
		contentPane.add(jToolBar, BorderLayout.NORTH);
		contentPane.add(jToolBar1, BorderLayout.EAST);

		
		// Analyse panel
		jToolBar1.add(m_CSelector, BorderLayout.NORTH);
		jToolBar1.add(jPanel2, BorderLayout.CENTER);

		///jToolBar3.add(m_FSelector, BorderLayout.NORTH);
		//jToolBar3.add(jPanel3, BorderLayout.CENTER);
		
		
		jToolBar1.add(m_CSelector, BorderLayout.NORTH);
		jToolBar1.add(jPanel2, BorderLayout.CENTER);		
		jPanel2.setPreferredSize(new Dimension(SetEnv.SizeB, SetEnv.SizeY));
		
		m_ClControl.setPreferredSize(new Dimension(SetEnv.SizeB, SetEnv.SizeY));

		// lisener tree
		m_CSelector.m_tree
				.addTreeSelectionListener(new TreeSelectionListener() {
					
					

					

					public void valueChanged(TreeSelectionEvent e) {
						// Get all nodes whose selection status has changed
						TreePath path = e.getPath();
						Object[] nodes = path.getPath();
						String oid = "";
						for (int k = 0; k < nodes.length; k++) {
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[k];
							OidNode nd = (OidNode) node.getUserObject();
							oid += nd.getId();
							SetEnv.Previous=SetEnv.LastMode;
							SetEnv.LastMode=nd.getId();
							System.out.print(oid);
						}

						SetEnv.Mode = Integer.parseInt(oid);
						
						SetEnv.statusbar.setText("Partition mode "
								+ SetEnv.Mode + " initiated");
						// set analysers : only if there is a change!
						
						if (SetEnv.Mode == 1) {
							if (Mode_old != 1) {
								jToolBar1.remove(m_ClControl);
								jToolBar1.remove(jSumPanel1);
					//			jToolBar1.remove(indicatorSelect);
							///	jToolBar1.remove(fileUpload.getContentPane());
		
								jToolBar1.add(jPanel2, BorderLayout.CENTER);
								Mode_old = SetEnv.Mode;
								jToolBar1.updateUI();
							}
						}  else {

							 if(SetEnv.Mode == 11000){
								 jToolBar1.remove(jPanel2);
								jToolBar1.remove(jSumPanel1);
								 jToolBar1.remove(m_ClControl);
								 jToolBar1.remove(jPanel2);
									
							///	 jToolBar1.add(indicatorSelect,BorderLayout.LINE_END);
								 
								 ////jSumPanel1.add(jSummary4);
								 //jToolBar1.add();
								
								 //// jSumPanel1.show(true);
								 
								 
								    
								/// jToolBar1.add(fileUpload.getContentPane(),BorderLayout.SOUTH);
								 
								 
								 //jToolBar1.add(jButton2);						 
								 Mode_old = SetEnv.Mode;
								 jToolBar1.updateUI(); 
							 }else{
								jToolBar1.remove(m_ClControl);
								jToolBar1.remove(jSumPanel1);
					//				jToolBar1.remove(indicatorSelect);
								jToolBar1.remove(jPanel2);
							///	jToolBar1.remove(fileUpload.getContentPane());

								jToolBar1.add(m_ClControl, BorderLayout.CENTER);
								//jToolBar1.remove(m_FinanceControl, BorderLayout.CENTER);
								Mode_old = SetEnv.Mode;
								jToolBar1.updateUI();
							 }
						}

						// System.out.println("Selected="+SetEnv.Mode);
						// m_display.setText(oid);
					}
				});


		contentPane.add(SetEnv.PLOT, java.awt.BorderLayout.CENTER);

		// read msc file from prompt
		if (file_input.length() > 1) {

			// read msc file
			LoadFile(file_input);

		}

	}

	
	/**
	 * File | Exit action performed.
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 */
	void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
		JOptionPane dialog = new JOptionPane();
		int ask = JOptionPane.showConfirmDialog(dialog,
				"Exit MultistageCorrelation?", "Question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		if (ask == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
	
	}

	
	/**
	 * Open action performed.
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 */
	void jMenuFileOpen_actionPerformed(ActionEvent actionEvent) {
		
		OpenFile();
	
	}
	
	/**
	 * Open action performed.
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 */
	void jMenuFileClose_actionPerformed(ActionEvent actionEvent) {
		
		closeFile();
	
	}
	
	
	
	
	
	
	
	
	void closeFile() {

		JOptionPane dialog = new JOptionPane();

		int ask = JOptionPane.showConfirmDialog(dialog,
				"Close current msc file?", "Question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		if (ask == JOptionPane.YES_OPTION) {
			SetEnv.PLOT.clearPlot();
			SetEnv.DATA = null;
			SetEnv.Dim = 0;
			SetEnv.NRow = 0;
		}

	}

	void openHelp() {

		new AboutDialog(this);

	}
	


	// set options and plot
	void SetOptions(int iX, int iY) {

		// set instances
		selX.removeAllItems();
		selY.removeAllItems();

		SetEnv.JboxX = iX;
		SetEnv.JboxY = iY;

		for (int i = 0; i < SetEnv.Dim; i++) {
			String a = SetEnv.DATA.getName(i);
			selX.addItem(a);
			selY.addItem(a);
			// System.out.println( a );
		}

		// draw ponts
		if (SetEnv.Dim > 1) {
			selX.setSelectedIndex(iX);
			selY.setSelectedIndex(iY);
			SetEnv.JboxX = iX;
			SetEnv.JboxY = iY;
			if (SetEnv.Style == 0) {
				ThreadShow STh1 = new ThreadShow(SetEnv.Style);
			    if (!STh1.Alive()) STh1.Start();     	
			}
			}
			
	}


	// set summary table
	void SetTableSummary() {

		//if (jSummary.model.getRowCount() > 0)
			//jSummary.model.setRowCount(0);

		// Input array, values to be read in successively, float
		int n = SetEnv.NRow;
		int m = SetEnv.Dim;
		double[][] indat = new double[n][m];

		for (int i = 0; i < n; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			for (int i2 = 0; i2 < m; i2++)
				indat[i][i2] = dp.getAttribute(i2);
		}

		double[][] sum = VEC.GetSummaryData(n, m, indat);
		NumberFormat formatter = new DecimalFormat("0.00E00");

		for (int i = 0; i < m; i++) {
			String s0 = SetEnv.DATA.getName(i);
			String s1 = formatter.format(sum[i][0]);
			String s2 = formatter.format(sum[i][1]);
//			jSummary.model.addRow(new Object[] { new String(s0),
	//				new String(s1 + "/" + s2) });
		}

	}

	// show status
	void ShowStatus(String s) {

		SetEnv.statusbar.setText(s);

	}

	void OpenFile() {
		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new Filtermsc());
    
		int returnVal = fc.showOpenDialog(this);
        
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			afile = fc.getSelectedFile();
			file_open = afile.getAbsolutePath().trim();

			// read msc file
			LoadFile(file_open);

		}
		;
	} // end open file

	/**
	 * Help | About action performed.
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 */
	void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {

		openHelp();

	}

	
	
	
	
	
	
	/**
	 * Load a file
	 * @param sfile
	 */
	public void LoadFile(String sfile) {

		
		SetEnv.Load(sfile); // read input
		SetEnv.JboxX = 0;
		SetEnv.JboxY = 1;
		SetOptions(SetEnv.JboxX, SetEnv.JboxY);
		ShowStatus("File " + sfile + " loaded");
		
		
	
	
		
	}

	
	/**
	 * Load file in thread
	 * @author H.Geissler
	 * 18 May 2011
	 *
	 */
	
    class LoadCombo extends Thread {
       
    	private String name;
    	public LoadCombo(String str) {
    		super(str);
    		name=str;
    		
        }
        public void run() {
    
        	SetEnv.Load(name); // read input
			SetEnv.JboxX = 0;
			SetEnv.JboxY = 1;
			SetOptions(SetEnv.JboxX, SetEnv.JboxY);
			ShowStatus("File " + name + " loaded");
    	
        }
    }


	
	
	
	
	

    /**
     * File selector
     * @author H.Geissler
     * 18 May 2011
     *
     */
   class Filtermsc extends javax.swing.filechooser.FileFilter {
      public boolean accept(File f) {
          if (f.isDirectory()) {
              return true;
          }
          else if (f.getName().endsWith(".msc") || 
        		   f.getName().endsWith(".msc")) {
              return true;
          }
          return false;
      }
          public String getDescription() {
          return "*.msc"; }  
          }



// exit
class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuFileExit_actionPerformed(actionEvent);
	}
}

// open
class MainFrame_jMenuFileOpen_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuFileOpen_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuFileOpen_actionPerformed(actionEvent);
	}
}



// close
class MainFrame_jMenuFileClose_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuFileClose_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuFileClose_actionPerformed(actionEvent);
	}
}






class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
	MainFrame adaptee;

	MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
	}
}

class MyItemListenerMatrix implements ItemListener {
	// This method is called only if a new item has been selected.
	public void itemStateChanged(ItemEvent evt) {
		JComboBox cb = (JComboBox) evt.getSource();
		// Object item = evt.getItem();
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			
			
			int nn = cb.getSelectedIndex();
			SetEnv.matrix = nn;
			Global.matrixName=Global.matrixTitles[SetEnv.matrix];
			
		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}
}





class MyItemListenerStyle implements ItemListener {
	// This method is called only if a new item has been selected.
	public void itemStateChanged(ItemEvent evt) {
		JComboBox cb = (JComboBox) evt.getSource();
		// Object item = evt.getItem();
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			
			
			int nn = cb.getSelectedIndex();
			if (nn == 0) SetEnv.Style = 0;
			if (nn == 1) SetEnv.Style = 1;
				
			ThreadShow STh3 = new ThreadShow(SetEnv.Style );
		    if (!STh3.Alive()) STh3.Start();
			     	

		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}



  }


	
	
	class ListenerDataManipulation implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();
			// Object item = evt.getItem();
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				
				
				int nn = cb.getSelectedIndex();
				if (nn == 0) SetEnv.Style = 0;
				if (nn == 1) SetEnv.Style = 1;
					
				refresh = new ThreadRefresh(nn,MainFrame.this);
				if (!refresh.Alive()) refresh.Start();
				     	
			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
				// Item is no longer selected
			}
		}
	}

	class ListenerDataSource implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();
			// Object item = evt.getItem();
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				
				
				int nn = cb.getSelectedIndex();
				if (nn == 0) SetEnv.Style = 0;
				if (nn == 1) SetEnv.Style = 1;
					
				refresh = new ThreadRefresh(nn,MainFrame.this);
				if (!refresh.Alive()) refresh.Start();
				     	

			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
				// Item is no longer selected
			}
		}



	 

  }

} // end main class
