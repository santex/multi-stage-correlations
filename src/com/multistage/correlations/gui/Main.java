package com.multistage.correlations.gui;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.algo.test.FileIO;
import com.multistage.correlations.utils.ResourceLoader;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Main {
	
	static String userhome = System.getProperty("user.home");
	static String HOME =userhome+File.separator+"mscorrelation"+File.separator+"data"+File.separator+"userDefaultraw.msc";
	boolean packFrame = false;
	public static StringBuffer xbuf = new StringBuffer();
	public static String input_file = userhome+File.separator+"mscorrelation"+File.separator+"data"+File.separator+"userDefaultraw.msc";

	public MainFrame frame;
	
	/**	
	 * Construct and show the application.
	 */
	public Main() {
		
		try {
			FileIO.checkForConfig();
			FileIO.writeSetings();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		SetEnv.SizeFrame((int) (0.80 * screenSize.width),
				(int)(0.80 * screenSize.height));

		frame = new MainFrame(input_file);
		// Validate frames that have preset sizes
		// Pack frames that have useful preferred size info, e.g. from their
		// layout
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}

		// Center the window
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(false);
	}

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		 
		int count = 0;
		
		for (int i = 0; i < args.length; i++) {
		      xbuf.append(String.valueOf(count));
		      xbuf.append("+");
		      count++;
	    }

		
		System.out.println(xbuf.toString()+""+count);
		
		
		input_file = userhome+File.separator+"mscorrelation"+File.separator+"data"+File.separator+"userDefaultraw.msc";
		
		//stringToFile(String text, String fileName);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel(UIManager.
					// getSystemLookAndFeelClassName());
					
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());

			    

				ResourceLoader.init(System.getProperty("user.home")+File.separator+"mscorrelation"+File.separator+"data"+File.separator);
				new Main(); 
				
				//
				//ResourceLoader.
				
				System.out.println(SetEnv.getUpdate("COMP","pct"));
		
				System.out.println(ResourceLoader.getString("action", "testvalue"));
				System.out.println(ResourceLoader.getString("display", "testvalue"));
				
				} catch (Exception exception) {
					exception.printStackTrace();
				}				
				
			}
		});
	}
}
