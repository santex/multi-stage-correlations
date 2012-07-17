// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.multistage.correlations.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.multistage.correlations.gui.SetEnv;

/**
 * Resource loader
 */


public class ResourceLoader {



	private ResourceLoader() {
	}

	public static void setTokens(String as[][]) {
		substTokens = as;
	}

	public static void addResourcePath(String s) {
		if (s != null) {
			if (!s.equals("") && !s.endsWith("/"))
				s = s + "/";
			resPaths.add(s);
		}
	}

	private static ResourceBundle openBundle(String s) {
		ResourceBundle resourcebundle = null;
		for (Iterator iterator = resPaths.iterator(); iterator.hasNext()
				&& resourcebundle == null;) {

			String s0 = "en";SetEnv.locale.getLanguage();
			
			System.out.println("Debug="+s0);
			String s1 = (String) iterator.next() + "bundles/" + s0 + "/" + s;
			try {
				resourcebundle = ResourceBundle.getBundle(s1, SetEnv.locale);
			} catch (Exception exception) {
			}
		}

		if (resourcebundle == null)
			System.err.println("*** MISSING RESOURCE BUNDLE *** : " + s);
		bundleTable.put(s, resourcebundle);
		return resourcebundle;
	}

	public static String getString(String s, String s1) {
		if (s1 == null)
			return "";
		ResourceBundle resourcebundle = (ResourceBundle) bundleTable.get(s);
		if (resourcebundle == null)
			resourcebundle = openBundle(s);
		String s2;
		try {
			s2 = resourcebundle.getString(s1);
		} catch (Exception exception) {
			return "FIXME";
		}
		return s2;
	}

	public static String getCommand(String s) {
		return getString("action", s);
	}

	public static String getDisplay(String s) {
		return getString("display", s);
	}

	public static String getMessage(String s) {
		return getString("message", s);
	}

	public static String codeOrRealMsg(String s) {
		String s1 = getString("message", s);
		if (s1.equals("FIXME"))
			s1 = s;
		return s1;
	}

	public static String codeOrRealDisplay(String s) {
		String s1 = getString("display", s);
		if (s1.equals("FIXME"))
			s1 = s;
		return s1;
	}

	public static void init(String s) {
		addResourcePath(s);
		getString("action", "testvalue");
		getString("display", "testvalue");
		imageTable = new Properties();
		userTable = new Properties();
		userWatchList = new Properties();
		userPortfolioTable = new Properties();
		userPortfolioSecondTable = new Properties();
		try {
			imageTable.load(getResourceStream("#images/imagemap.properties"));
			userTable.load(getResourceStream("#user/user.properties"));
			userWatchList.load(getResourceStream("#user/watchlist.properties"));
			userPortfolioTable.load(getResourceStream("#user/portfolio.properties"));
			userPortfolioSecondTable.load(getResourceStream("#user/portfolio.secondary.properties"));
			
			///MHeapx/src/com/multistage/correlations/resources/user/portfolio.properties
		} catch (Exception exception) {
			System.err
					.println("*** MISSING RESOURCE *** : properties\r\n");
		}

		// defaultFailImage = getImageURL("default_fail");
	}

	public static URL getImageURL(String s) {

		String s1 = imageTable.getProperty(s);
		if (s1 == null) {
			System.err.println("*** missing image association: " + s);
			return null;
		} else {
			return getResourceURL(s1);
		}
	}

	public static ImageIcon getImageIcon(String s) {
		URL url = getImageURL(s);
		if (url == null)
			url = defaultFailImage;
		return url != null ? new ImageIcon(url) : null;
	}

	public static ImageIcon getDefaultImageIcon(String s, String s1) {
		ImageIcon imageicon = (ImageIcon) UIManager.getIcon(s);
		if (imageicon == null)
			imageicon = getImageIcon(s1);
		return imageicon;
	}

	public static InputStream getResourceStream(String s) throws IOException {
		URL url;
		if ((url = getResourceURL(s)) == null)
			return null;
		else
			return url.openStream();
	}

	public static URL getResourceURL(String s) {
		URL url = null;
		if (s == null)
			throw new IllegalArgumentException("path = null");
		if (s.startsWith("#")) {
			for (int i = 0; i < resPaths.size() && url == null; i++) {
				String s1 = (String) resPaths.get(i) + s.substring(1);
				url = (ResourceLoader.class).getClassLoader().getResource(s1);
			}

		} else {
			url = (ResourceLoader.class).getClassLoader().getResource(s);
		}
		if (url == null) {
			System.err.println("*** failed locating resource: " + s);
			return null;
		} else {
			return url;
		}
	}

	public static String[] getResourcePaths() {
		Object aobj[] = resPaths.toArray();
		String as[] = new String[aobj.length];
		for (int i = 0; i < aobj.length; i++){
			System.out.println("***  " + (String) aobj[i]);
			as[i] = (String) aobj[i];
		}

		return as;
	}

	public static String substTokens[][];

	private static List<String> resPaths = new LinkedList<String>();

	private static Hashtable<String, ResourceBundle> bundleTable = new Hashtable<String, ResourceBundle>();
	
	private static Hashtable<String, ResourceBundle> userBundle = new Hashtable<String, ResourceBundle>();
	
	

	private static Properties imageTable;

	private static URL defaultFailImage;

	public static Properties userTable;
	

	private static Properties userWatchList;

	private static Properties userPortfolioTable;

	private static Properties userPortfolioSecondTable;

}
