package com.multistage.correlations.algorithms;

import com.multistage.correlations.clcontrol.*;
import com.multistage.correlations.gui.SetEnv;
import com.multistage.correlations.utils.*;


// analyse data
public class Analyse {

	
	
	/**
	 * Main method to run clustering: Imod=1 - visual mode Imod=0 - mode for
	 * batch (no graphics)
	 * 
	 * @param Imode
	 * 
	 */
	public static void Run(int Imode) {

		Runtime r = Runtime.getRuntime();

		if (Imode == 1) {
			SetEnv.statusbar.setText(String.format("Wait! Analysing.. %s %s",SetEnv.MARKET,SetEnv.DESC));
			Global.GetSettings(); // get settings
			Global.CPUtime = "running!";
			Global.SetInfoTable();
			Global.GetOptions();
			Global.mem = null;
		}

		
		Global.Compactness = 0.0;
		Global.PartitionCoefficient = 0.0;
		Global.Description = "not filled yet";
		Global.CC=null;
		Global.SS=null;
		Global.nPoints=null;
		
		
		StopWatch sw = new StopWatch();
		sw.start();
		// System.out.println(SetEnv.Mode);

		// get Kmeans2 -- standard mode ------------------------
		if (SetEnv.Mode == 111) {
			// System.out.println("Analyse Kmeans 0");
			kMeans2 km = new kMeans2();
			km.setClusters(Global.Ncluster);
			km.run();
			Global.Ncluster = km.getClusters();
			Global.nPoints=km.getNumberPoints();
			Global.Compactness = km.getCompactness();
			Global.Description = km.getName();
			Global.CC = km.getCenters();
			Global.SS = km.getSeedHolder();
		}

		// get Kmeans0 -- standard mode, but run several times
		if (SetEnv.Mode == 112) {
			kMeans2 km = new kMeans2();
			km.setClusters(Global.Ncluster);
			km.run(Global.Niterations);
			Global.Ncluster = km.getClusters();
			Global.nPoints=km.getNumberPoints();
			Global.Compactness = km.getCompactness();
			Global.CC = km.getCenters();
			Global.SS = km.getSeedHolder();
			Global.Description = km.getName();
		}

		// get Kmeans1 - exchange mode
		if (SetEnv.Mode == 113) {
			kMeans1 km = new kMeans1();
			km.runBest();
			Global.Ncluster = km.getClusters();
			Global.Description = km.getName();
			if (km.getError() == 0) {
				Global.CC = km.getCenters();
				Global.SS = km.getSeedHolder();
				Global.Compactness = km.getCompactness();
				Global.nPoints= km.getNumberPoints();

			}
			
			if (Global.ShowCenters) {
				Display.Centers();
			}
			if (Global.ShowSeeds) {
				Display.Seeds();
			}
		}

		// get Kmeans1 - exchange mode
		if (SetEnv.Mode == 114) {
			kMeans1 km = new kMeans1();
			km.setClusters(Global.Ncluster);
			km.run();
			Global.Description = km.getName();
			if (km.getError() == 0) {
				Global.CC = km.getCenters();
				Global.SS = km.getSeedHolder();
				Global.Compactness = km.getCompactness();
				Global.nPoints= km.getNumberPoints();

			}
			
			if (Global.ShowCenters) {
				Display.Centers();
			}
			if (Global.ShowSeeds) {
				Display.Seeds();
			}
		}

		// hearacity -- standard mode ------------------------
		if (SetEnv.Mode == 121) {
			// System.out.println("Analyse Kmeans HCluster");
			HCluster km = new HCluster();
			km.setClusters(Global.Ncluster);
			km.run();
			Global.Description = km.getName();
			Global.CC = km.getCenters();
			Global.Compactness = km.getCompactness();
			Global.nPoints= km.getNumberPoints();
		}
		// hearacity -- standard mode ------------------------

		if (SetEnv.Mode == 122) {
			HCluster km = new HCluster();
			km.runBest();
			Global.Ncluster = km.getClusters();
			Global.Description = km.getName();
			Global.CC = km.getCenters();
			Global.Compactness = km.getCompactness();
			Global.nPoints= km.getNumberPoints();
		}

		// run fuzzy
		if (SetEnv.Mode == 131) {
			Fuzzy km = new Fuzzy();
			Global.Description = km.getName();
			km.setClusters(Global.Ncluster);
			km.setOptions(Global.Niterations, Global.Eps, Global.Fuzzines);
			km.run();
			Global.CC = km.getCenters();
			Global.Compactness = km.getCompactness();
			Global.PartitionCoefficient = km.getPartitionCoefficient();
			Global.mem = km.Membeship();
			Global.nPoints= km.getNumberPoints();
			
			km.Delete();
		}

		// run fuzzy
		if (SetEnv.Mode == 132) {
			// System.out.println("Analyse Fuzzy best");
			// Fuzzy.init();
			Fuzzy km = new Fuzzy();
			// System.out.println(Global.Ncluster);
			km.setOptions(Global.Niterations, Global.Eps, Global.Fuzzines);
			km.runBest();
			Global.Ncluster = km.getClusters();
			Global.Description = km.getName();
			Global.CC = km.getCenters();
			Global.Compactness = km.getCompactness();
			Global.PartitionCoefficient = km.getPartitionCoefficient();
			Global.mem = km.Membeship();
			Global.nPoints= km.getNumberPoints();
			km.Delete();
		}

		
	
		
		r.gc();

		if (Imode == 1) {
			SetEnv.PLOT.clearData();
			// Display.Data();
			Display.Clusters();
			SetEnv.PLOT.showPlot();
			if (Global.ShowCenters) {
				Display.Centers();
			}
			if (Global.ShowSeeds) {
				Display.Seeds();
			}
			sw.stop();
			Global.CPUtime = Long.toString(sw.getTime()) + " ms";
			Global.SetInfoTable();
			Global.summary = Global.Summary();
			// String.format(".. %s %s",,SetEnv.DESC)
			SetEnv.statusbar.setText("Finished: "+SetEnv.MARKET+" "+
					Global.Description+" (CPU time="+Global.CPUtime+")");
		}

	}

}
