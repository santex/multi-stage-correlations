package com.multistage.correlations.gui;

// Defines application wide constants

import javax.swing.Icon;

import com.multistage.correlations.utils.*;

import java.awt.Font;
import java.net.*;

/**
 * Set main constants
 * @author H.Geissler
 * 18 May 2011
 *
 */

public interface Constants {

	final static String fileSep = System.getProperty("file.separator");

	final static String javaApp = new String("Docs" + fileSep + "Untitled.java");

	final static String javaApplet = new String("Docs" + fileSep
			+ "Untitled1.java");

	final static String SYSTEM_ERROR = new String(
			"An application error has occured, please see error log. Tools > Error log");

	final static Font SYSTEM_FONT = new Font("Arial", Font.BOLD, 12);

	final static String License = ResourceLoader.getCommand("html.license");

	final static URL UrlLicense = ResourceLoader.getResourceURL("#standards/"+ License);

	final static String Readme = ResourceLoader.getCommand("html.about");

	final static URL UrlReadme = ResourceLoader.getResourceURL("#standards/"+ Readme);

	final static String ErrorLog = new String("Docs" + fileSep + "errorlog"+ fileSep + "errorlog.txt");

	final static String newline = "\n";

	final static Icon OpeN = ResourceLoader.getImageIcon("Open16");

	final static Icon ClosE = ResourceLoader.getImageIcon("Close16");

	final static Icon runjava = ResourceLoader.getImageIcon("runjava");

	final static Icon HelP = ResourceLoader.getImageIcon("help16");
	
	final static Icon MarketS = ResourceLoader.getImageIcon("Markets16");

	final static Icon PortF = ResourceLoader.getImageIcon("Portfolio16");


}
