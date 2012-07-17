package com.multistage.correlations.algorithms;

import java.util.Random;

import java.util.Arrays;

import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.cluster.Get;
import com.multistage.correlations.gui.*;
import com.multistage.correlations.utils.*;

/*
 * This class implements a standard k-means algorithm
 */
public class kMeans2 {

	private double[][] indat;

	private int nrow, ncol;

	private int Ierr = 0; // error statement

	// cluster centers
	private DataHolder cMeans;

	private DataPoint Min;

	private DataPoint Max;

	private String description;

	private int maxIterations, numClusters;

	// The iteration counter will be global so we can get its value on the
	// middle of the clustering process.
	private int iteration;

	// A small value, if the difference of the cluster "quality" does not
	// changes beyond this value, we consider the clustering converged.
	private double epsilon;

	// The cluster centers.
	private double[][] clusterCenters;

	// seed values
	private double[][] clusterSeed;

	// A big array with all the input data and a small one for a single pixel.
	private double[] aPixel;

	private double compactness;

	// The cluster assignment counter.
	private int[] clusterAssignmentCount;

	// A big array with the output data (cluster indexes).
	private int[] outputData;

	// A metric of clustering "quality".
	private double sumOfDistances;

//	private Random generator;

	// main initialisation
	public kMeans2() {

		nrow = SetEnv.NRow;
		ncol = SetEnv.Dim;
		Min = SetEnv.Min;
		Max = SetEnv.Max;
		Ierr = 0;
		numClusters = 0;
		maxIterations = 100;
		epsilon = 0.000001;
		compactness = 0;
		description = "kmeans algorithm";
		
//		System.out.println("No row "+nrow+ " ncol "+ncol);

		indat = new double[nrow][ncol]; // hold data
		aPixel = new double[ncol];
		outputData = new int[nrow];

		// Input array, values to be read in successively, float
		for (int i = 0; i < this.nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			for (int i2 = 0; i2 < this.ncol; i2++)
				indat[i][i2] = dp.getAttribute(i2);
		}

	}

	/**
	 * @param numClusters
	 *            Set the desired number of clusters.
	 */
	public void setClusters(int N) {

		if (N > this.nrow) {
			System.out.println("Too many clusters! Set to number of points");
			N = this.nrow;
		}
		this.numClusters = N;

	}

	/**
	 * Get number of clusters.
	 */
	public int getClusters() {

		return this.numClusters;
	}

	/**
	 * @param numClusters
	 *            the desired number of clusters.
	 */
	// set clusters
	public void GenerateSeed() {

	//	 System.out.println(" No. of rows, nrow = " + nrow);
	//     System.out.println(" No. of columns, ncol = " + ncol);
	 //    System.out.println(" No. of clusters = " + numClusters);
	     
		clusterSeed = new double[this.numClusters][this.ncol];
		Random generator = new Random();
		double[] a = new double[this.ncol];
		for (int j = 0; j < this.numClusters; j++) { // loop over clusters
			for (int m = 0; m < this.ncol; m++) { // loop over dimensions
		//		System.out.println(" print Min="+this.Min.getAttribute(m));
		//	 	System.out.println(" print Max="+this.Max.getAttribute(m));	
				a[m] = this.Min.getAttribute(m) + generator.nextDouble()
						* (this.Max.getAttribute(m) - this.Min.getAttribute(m));
		         clusterSeed[j][m] = a[m];
			 }
		}

	}

	/**
	 * @param SetSeed.
	 */
	// set clusters
	public void setSeed(double[][] seed) {

		// System.out.println(" No. of rows, nrow = " + nrow);
		// System.out.println(" No. of columns, ncol = " + ncol);
		clusterSeed = new double[this.numClusters][this.ncol];
		for (int j = 0; j < this.numClusters; j++) { // loop over clusters
			for (int m = 0; m < this.ncol; m++) { // loop over dimensions
				clusterSeed[j][m] = seed[j][m];
			}
		}

	}

	/**
	 * @param Get
	 *            seeds.
	 */
	// set clusters
	public double[][] getSeed() {

		return this.clusterSeed;

	}

	/**
	 * @param maxIterations
	 *            the maximum number of iterations.
	 * @param fuzziness
	 *            the fuzziness (a.k.a. the "m" value)
	 * @param epsilon
	 *            a small value used to verify if clustering has converged.
	 */

	// set epsilon, fuzeness
	public void setOptions(int maxIterations, double epsilon, double fuzziness) {
		this.maxIterations = maxIterations;
		this.epsilon = epsilon;
	}

	// set epsilon, fuzeness
	public void Delete() {

		this.numClusters = 0;
		this.maxIterations = 0;
		this.epsilon = 0;
		this.nrow = 0;
		this.ncol = 0;
		this.indat = null;
		this.clusterCenters = null;
	//	this.generator = null;
		this.aPixel = null;

	}

	/**
	 * Returns cluster centers
	 * 
	 * @return DataHolder
	 */
	public DataHolder getCenters() {

		cMeans = new DataHolder();

		for (int i = 0; i < this.numClusters; i++) {
			double[] a = new double[this.ncol];
			for (int j = 0; j < this.ncol; j++)
				a[j] = clusterCenters[i][j];
			DataPoint c = new DataPoint(a, this.ncol);
			// c.showAttributes();
			cMeans.add(c);
		}

		return cMeans;

	}

	/**
	 * Get Seeds in form of DataHolder
	 * 
	 */
	public DataHolder getSeedHolder() {

		DataHolder tmp = new DataHolder();

		for (int i = 0; i < this.numClusters; i++) {
			double[] a = new double[this.ncol];
			for (int j = 0; j < this.ncol; j++)
				a[j] = clusterSeed[i][j];
			DataPoint c = new DataPoint(a, this.ncol);
			tmp.add(c);
		}

		return tmp;

	}

	/**
	 * Returns the number of points in each cluster
	 * 
	 * @return int[] getNumberPoints
	 */
	public int[] getNumberPoints() {

		return this.clusterAssignmentCount;
	} // end

	/**
	 * Classic K-Means clustering algorithm:
	 */
	public void run() {
		GenerateSeed();
		runKM();
		description = "kmeans algorithm fixed cluster mode";
	}

	/**
	 * Classic K-Means clustering algorithm:
	 */
	public void runKM() {

		iteration = 0;
		clusterCenters = new double[this.numClusters][this.ncol];
		clusterAssignmentCount = new int[numClusters];

		for (int j = 0; j < this.numClusters; j++) { // loop over clusters
			for (int m = 0; m < this.ncol; m++) { // loop over dimensions
				clusterCenters[j][m] = clusterSeed[j][m];
			}
		}

		// System.out.println("random:");
		// VEC.printMatrix(numClusters,ncol,clusterCenters,5,5);

		double lastSumOfDistances = 0;
		iterations: // Label for main loop.

		for (iteration = 0; iteration < maxIterations; iteration++) {
			// 0 - Clean the cluster assignment vector.
			Arrays.fill(clusterAssignmentCount, 0);
			// 1 - Scan the image and calculate the assignment vector.
			for (int h = 0; h < this.nrow; h++) {
				// Gets the class for this pixel.
				for (int b = 0; b < ncol; b++)
					aPixel[b] = indat[h][b];
				int aClass = getClassFor(aPixel);
				outputData[h] = aClass;
				clusterAssignmentCount[aClass]++;
			}

			// 2 - Scan the assignment vector and recalculate the cluster
			// centers.
			for (int cluster = 0; cluster < numClusters; cluster++)
				Arrays.fill(clusterCenters[cluster], 0f);
			// Update the position index.
			for (int h = 0; h < this.nrow; h++) {
				for (int b = 0; b < ncol; b++) {
					int theCluster = outputData[h];
					clusterCenters[theCluster][b] += indat[h][b];
				}
				// Update the position index.
			}
			// 2a - Recalculate the centers.
			for (int cluster = 0; cluster < numClusters; cluster++)
				if (clusterAssignmentCount[cluster] > 0)
					for (int b = 0; b < ncol; b++)
						clusterCenters[cluster][b] /= clusterAssignmentCount[cluster];

			sumOfDistances = 0;
			for (int h = 0; h < this.nrow; h++) {
				// To which class does this pixel belong ?
				int pixelsClass = outputData[h];
				// Calculate the distance between this pixel's values and its
				// assigned cluster center values.
				double distance = 0;
				for (int b = 0; b < this.ncol; b++) {
					double e1 = indat[h][b];
					double e2 = clusterCenters[pixelsClass][b];
					double diff = (e1 - e2) * (e1 - e2);
					distance += diff;
				}
				distance = Math.sqrt(distance);
				sumOfDistances += distance;
			}
			// Is it converging ?
			if (iteration > 0)
				if (Math.abs(lastSumOfDistances - sumOfDistances) < epsilon)
					break iterations;
			lastSumOfDistances = sumOfDistances;
		} // end of the iterations loop.

		// VEC.printMatrix(nrow,numClusters,membership,5,5);
		// VEC.printMatrix(numClusters,ncol,clusterCenters,5,5);

		// VEC.printVect(outputData, 5, 5);
		// VEC.printVect(clusterAssignmentCount, 5, 5);
		// fill this

		for (int i = 0; i < this.nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			dp.assignToCluster(outputData[i]);
		}

	}

	/**
	 * This method returns the Compactness
	 * 
	 * @return compactness
	 */
	public double getCompactness() {
		this.compactness = Get.compactness(indat, outputData, this.numClusters,
				clusterCenters);
		return this.compactness;
	}

	/**
	 * Runs the k-means algorithm for Iterations
	 */
	public void run(int Iter) {

		double[] Sum = new double[Iter];
		double[][][] seeds = new double[Iter][this.numClusters][this.ncol];

		for (int j = 0; j < Iter; j++) {
			GenerateSeed();
			runKM();
			seeds[j] = getSeed();
			Sum[j] = Get.compactness(indat, outputData, this.numClusters,
					clusterCenters);
		}

		// int[] Npoints=getNumberPoints();
		// find smallest compactness
		int ibest = ArrayOps.findSmallest(Sum, Iter);
		// run k-means
		setSeed(seeds[ibest]);
		runKM();
		this.description = "kmeans algorithm for multiple iterations";
	}

	/**
	 * This auxiliary method gets the class for a pixel vector.
	 * 
	 * @return the class (cluster index) for a pixel.
	 */
	private int getClassFor(double[] pixel) {
		// Let's compare this pixel data with all the cluster centers.
		float closestSoFar = Float.MAX_VALUE;
		int classSoFar = 0;
		for (short cluster = 0; cluster < numClusters; cluster++) {
			// Calculate the (quick) distance from this pixel to that cluster
			// center.
			float distance = 0f;
			for (int b = 0; b < this.ncol; b++)
				distance += Math.abs(clusterCenters[cluster][b] - pixel[b]);
			if (distance < closestSoFar) {
				closestSoFar = distance;
				classSoFar = cluster;
			}
		}
		return classSoFar;
	}

	// get error statement: 0 - looks good
	public int getError() {
		return this.Ierr;
	}

	/**
	 * This method returns the estimated size (steps) for this task.
	 * 
	 */
	public long getSize() {
		return (long) maxIterations;
	}

	public String getName() {
		return description;
	}

}
