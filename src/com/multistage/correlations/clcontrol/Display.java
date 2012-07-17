package com.multistage.correlations.clcontrol;

import com.multistage.correlations.cluster.*;
import com.multistage.correlations.gui.*;


/**
 * All methods to display the data
 * 
 * @author H.Geissler 18 May 2011
 * 
 */

public class Display {

	/**
	 * Clear the plot
	 * 
	 */
	public static void ClearAll() {
		
		SetEnv.PLOT.clearData();
	}

	/**
	 * Display only data points
	 * 
	 */
	public static void Data() {

		SetEnv.PLOT.addSet("Data", SetEnv.DATA);

	}

	/**
	 * Fill clusters to dataset to be shown latter
	 * 
	 */
	public static void Clusters() {

		DataHolder[] clu = new DataHolder[Global.Ncluster + 1];
		for (int i = 0; i < Global.Ncluster + 1; i++) {
			clu[i] = new DataHolder();
		}

		for (int m = 0; m < SetEnv.NRow; m++) {
			DataPoint dp = SetEnv.DATA.getRaw(m);
			int i = dp.getClusterNumber();
			// System.out.println("point="+i);
			if (i >= 0) {
				clu[i + 1].add(dp);
			} else {
				clu[0].add(dp);
			} // show the rest of the data

			// System.out.println("clus="+i);
		}

		// draw clusters
		SetEnv.PLOT.clearData();
		SetEnv.PLOT.addSet("Data", clu[0]);
		for (int m = 1; m < Global.Ncluster + 1; m++) {
			
			String[] syms  = Global.getLocations(m-1).split(" ");
			SetEnv.PLOT.addSet(Integer.toString(m)+"/"+Integer.toString(syms.length-1), clu[m]);
		}

	}

	/**
	 * Show cluster centers
	 * 
	 */
	public static void Centers() {

		if (Global.CC != null) {
			SetEnv.PLOT.showCenters("Center ", Global.CC);
		}
		;

	}

	/**
	 * Remove cluster centers
	 * 
	 */
	public static void RemoveCenters() {

		SetEnv.PLOT.removeCenters();

	}

	/**
	 * Show seeds
	 * 
	 */
	public static void Seeds() {

		if (Global.SS != null) {			 
			SetEnv.PLOT.showSeeds("Seed ", Global.SS);
		}
 		;

	}

	/**
	 * Remove seeds
	 * 
	 */
	public static void RemoveSeeds() {

		SetEnv.PLOT.removeSeeds();

	}

}
