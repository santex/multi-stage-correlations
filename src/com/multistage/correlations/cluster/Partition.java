package com.multistage.correlations.cluster;

import com.multistage.correlations.algorithms.Analyse;
import com.multistage.correlations.clcontrol.*;
import com.multistage.correlations.gui.*;


/**
 * Interface class to all clustering algorithms. Use it for JMInHEP embeded
 * applications
 * 
 * @author H.Geissler 18 May 2011
 * 
 */
public class Partition {

	public Partition(DataHolder data) {

		SetEnv.DATA = data;
		SetEnv.Mtitle = data.getRelation();
		SetEnv.Dim = data.getDimention();
//		System.out.println("SetEnv.Dim " + SetEnv.Dim);
		SetEnv.NRow = data.getSize();
		data.analyseSet();
		SetEnv.Min = data.getMin();
		SetEnv.Max = data.getMax();
		SetEnv.XYtitle = new String[SetEnv.Dim];

//	System.out.println("Min and Max=");
//		SetEnv.Min.showAttributes();
//		SetEnv.Max.showAttributes();
		// for (int i = 0; i <SetEnv.Dim; i++) {
		// SetEnv.XYtitle[i]=SetEnv.DATA.GetName( i );
		// }

	}

	/**
	 * Set parameters for clustering
	 * 
	 * @param Nclusters -
	 *            number of clusters
	 * @param Eps -
	 *            precision of clustering
	 * @param Fuzzines -
	 *            fuzziness (only for cmeans algorithms, for others just dummy
	 *            constant)
	 * @param Niterations -
	 *            Max number of iterations
	 */
	public void set(int Nclusters, double Eps, double Fuzzines, int Niterations) {

		Global.Ncluster = Nclusters;
		Global.Eps = Eps;
		Global.Fuzzines = Fuzzines;
		Global.Niterations = Niterations;
	}

	/**
	 * Main method to run cluster algorithm
	 * 
	 * @param Imode -
	 *            cluster mode (int). Find the correct mode from the example or
	 *            by running JMinHEP in GUI mode. After each clustering, Imode
	 *            value is shown in the status bar.
	 */
	public void run(int Imode) {
		SetEnv.Mode = Imode;
		Analyse.Run(0);

	}

	/**
	 * Get name for this clustering
	 * 
	 * @return Description of the cluster mode
	 */
	public String getName() {
		return Global.Description;

	}

	/**
	 * Get compactness of the cluster configurations
	 * 
	 * @return compactness
	 */

	public double getCompactness() {
		return Global.Compactness;

	}

	/**
	 * Get cluster centers
	 * 
	 * @return DataHolder with cluster centers
	 */
	public DataHolder getCenters() {
		return Global.CC;

	}

	/**
	 * Get seeds
	 * 
	 * @return DataHolder with seed values (only for k-means algorithms)
	 */
	public DataHolder getSeeds() {
		return Global.SS;

	}

	/**
	 * Get number of points accosiated with clusters
	 * 
	 * @return number of points in each cluster (int[])
	 */

	public int[] getPoints() {
		return Global.nPoints;

	}

	/**
	 * Set probability for cluster accosiation
	 * 
	 * @param probability
	 *            Active only for cmeans (and only for representative purpose)
	 */
	public void setProbab(double probability) {
		Global.ShowProbab = probability;

	}

	/**
	 * get number of reconstructed clusters
	 * 
	 * @return number of reconstructed clusters
	 */
	public int getNclusters() {
		return Global.Ncluster;

	}

} // end

