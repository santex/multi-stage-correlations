package com.multistage.correlations.algorithms;

import java.util.*;

import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.cluster.Get;
import com.multistage.correlations.gui.*;
import com.multistage.correlations.utils.*;

/**
 * Carry out pairwise agglomerations. For n items, therefore there are n-1
 * agglomerations. Represent the cluster labels is an nxn cluster label matrix.
 * Column no. n will be the singleton labels, 1 to n. Column no. n-1 will have
 * n-1 unique values (or label sequence numbers). Column no. n-2 will have n-2
 * unique values. Column no. 1 will have the value 1 only, implying that all n
 * items are in one cluster.
 * <p>
 * ClustMat is our agglomeration "engine". It looks after labeling only, and is
 * independent of any agglomerative clustering criterion.
 * <p>
 * Other utility methods:
 * <p>
 * Dissim ... calculate dissimilarity matrix <br>
 * getNNs ... get nearest neighbors and associated nearest neighbor
 * dissimilarities <br>
 * getSpaces ... helping in output formating <br>
 * printMatrix ... print matrix of doubles, or integers <br>
 * printVect ... print vector of doubles, or integers
 * <p>
 * main does the following:
 * <ol>
 * <li> Calculate pairwise dissimilarities, and determines nearest neighbors and
 * corresponding dissimilarities. (Squared Euclidean distance used.)
 * <li> Determines the closest nearest neighbors.
 * <li> Carries out an agglomeration in ClustMat.
 * <li> Updates the pairwise dissimilarity matrix, and then, on the basis of
 * this, the nearest neighbors, and the nearest neighbor dissimilarities.
 * <li> Repeats while no. of clusters is greater than 2.
 * </ol>
 * Constant MAXVAL is used in, resp., dissimilarities and nearest neighbor
 * dissimilarities, to indicate when items are processed and no longer exist as
 * singletons. <br>
 * Note also how flag = 1 denotes an active observation, and flag = 0 denotes an
 * inactive one (since it has been agglomerated). It is not necessary to use the
 * flag since exceptionally high dissimilarities will signify inactive
 * observations. However the use of flag is helpful computationally.
 * <p>
 * Step 5 here determines the agglomerative clustering criterion. We are
 * currently using the minimum variance method. It is indicated in the code
 * where to change to use other agglomerative criteria.
 * <p>
 * Output cluster labels using original sequence numbers. The ordering of
 * observations is not such that a dendrogram can be directly constructed.
 * However the cluster labels do allow selections and further inter and intra
 * cluster processing of the input data.
 * <p>
 * 
 */

public class HCluster {
	public static final double MAXVAL = 1.0e12;

	private double[][] indat;

	private int[] outputPoints; // N points in each cluster

	private int[] assignment;

	private int nrow, ncol;

	private double compactness;

	private int Ierr = 0; // error statement
	// The cluster centers.

	private double[][] clusterCenters;

	private int[][] tclusters; // cluster label matrix

	private int numClusters; // number of clusters

	private double[][] diss;

	private String description;

	// cluster centers
	private DataHolder cMeans;

	// main initialisation
	public HCluster() {

		nrow = SetEnv.NRow;
		ncol = SetEnv.Dim;
		// Min=SetEnv.Min;
		// Max=SetEnv.Max;
		Ierr = 0;
		this.compactness = 0;
		this.Ierr = 0;
		this.numClusters = 0;
		this.description = "Hierarchical clustering algorithm";

		indat = new double[nrow][ncol]; // hold data
		tclusters = new int[nrow][nrow]; // cluster label matrix

		// Input array, values to be read in successively, float
		for (int i = 0; i < this.nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			for (int i2 = 0; i2 < this.ncol; i2++)
				indat[i][i2] = dp.getAttribute(i2);
		}

		// System.out.println(" No. of rows, n = " + n);
		// System.out.println(" No. of columns, m = " + m);

	}

	/**
	 * Get error: 0 looks OK
	 * 
	 * @return int
	 */
	public int getError() {
		return this.Ierr;
	}

	/**
	 * This method returns the Compactness and Separation measure of cluster
	 * validity (see Fuzzy Algorithms With Applications to Image Processing and
	 * Pattern Recognition, Zheru Chi, Hong Yan, Tuan Pham, World Scientific,
	 * pp. 93)
	 */
	public double getCompactness() {
		return this.compactness;
	}

	/**
	 * Sets number of clusters
	 * 
	 * @param N
	 *            int - number of clusters
	 */
	public void setClusters(int N) {

		if (N > this.nrow) {
			System.out.println("Too many clusters! Set to number of points");
			N = this.nrow;
		}

		this.numClusters = N;
		// System.out.println(" No. of clusters, k = " + k);
	}

	
	
	/**
	 *  Get number of clusters
	 *            
	 */
	public int getClusters() {

		return this.numClusters;
	}
	
	
	
	
	/**
	 * Get cluster results (should be called after run method)
	 * 
	 */

	public void getResult() {

		assignment = new int[nrow]; // cluster label matrix
		clusterCenters = new double[this.numClusters][this.ncol];

		if (this.numClusters == 0) {
			System.out.println("Number of clusters was set to 0");
			System.exit(0);
		}

		// Create the set
		Set<Integer> set = new HashSet<Integer>();

		// For convenience, transpose the cluster labels
		for (int i1 = 0; i1 < nrow; i1++) {
			int ii = tclusters[this.numClusters - 1][i1];
			// System.out.println(" add="+ii);
			set.add(new Integer(ii));
		}

		// Get number of elements in set
		// int size = set.size(); // 2
		// System.out.println(size);

		// calculate assignements
		int N = 0;
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			int itake = ((Integer) itr.next()).intValue();
			for (int i1 = 0; i1 < nrow; i1++) {
				if (tclusters[this.numClusters - 1][i1] == itake)
					assignment[i1] = N;
			}
			N++;
		}

		// fill assignments
		for (int i = 0; i < nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			dp.assignToCluster(assignment[i]);
			// System.out.println("clus="+i);
		}

		outputPoints = new int[this.numClusters]; // cluster label matrix
		for (int j = 0; j < this.numClusters; j++) {
			for (int i = 0; i < ncol; i++)
				clusterCenters[j][i] = 0;
		}

		for (int j = 0; j < this.numClusters; j++) {
			outputPoints[j] = 0;
			for (int i = 0; i < nrow; i++) {
				if (assignment[i] == j) {
					outputPoints[j]++;
					for (int m = 0; m < ncol; m++)
						clusterCenters[j][m] = clusterCenters[j][m]
								+ indat[i][m];
				}
				// System.out.println("clus="+i);
			}
		}

		for (int j = 0; j < this.numClusters; j++) {
			for (int m = 0; m < this.ncol; m++)
				clusterCenters[j][m] = clusterCenters[j][m]
						/ (double) outputPoints[j];
		}

		// get compactness
		this.compactness = Get.compactness(indat, assignment, this.numClusters,
				clusterCenters);

	};

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
	} // end cluster centers

	/**
	 * Returns the number of points in each cluster
	 * 
	 * @return int[] getNumberPoints
	 */
	public int[] getNumberPoints() {
		return outputPoints;
	} // end

	/**
	 * Method Dissim, calculates dissimilarity n x n array
	 * 
	 * @param nrow
	 *            integer row dimension
	 * @param ncol
	 *            integer column dimension
	 * @param A
	 *            floating row/column matrix
	 * @return Adiss floating n x n dissimilarity array
	 */
	public static double[][] Dissim(int nrow, int ncol, double[] mass,
			double[][] A) {
		double[][] Adiss = new double[nrow][nrow];

		// Initialize to 0. Caters for the diagonal which stays 0.
		for (int i1 = 0; i1 < nrow; i1++) {
			for (int i2 = 0; i2 < nrow; i2++)
				Adiss[i1][i2] = 0.0;
		}
		for (int i1 = 0; i1 < nrow; i1++) {
			// All dissimilarity we are dealing with are symmetric
			// so just calculate for half the array and fill in later
			for (int i2 = 0; i2 < i1; i2++) {
				for (int j = 0; j < ncol; j++) {
					// We are happy with the squared dissimilarity
					// 0.5 term since this equals
					// (clcard[i1]*clcard[i2])/(clcard[i1]+clcard[i2])
					// for minimum variance criterion
					Adiss[i1][i2] += 0.5 * Math.pow(A[i1][j] - A[i2][j], 2.0);
				}
				// Adiss[i1][i2] += ((mass[i1]*mass[i2])/(mass[i1]+mass[i2]))*
				// Math.pow( A[i1][j] - A[i2][j], 2.0 );
				// Fill the other half of the array
				Adiss[i2][i1] = Adiss[i1][i2];
			}
		}
		return Adiss;
	} // Dissim

	/**
	 * Method getNNs, determine NNs and NN dissimilarities
	 * 
	 * @param nrow
	 *            row dimension or number of observations (input)
	 * @param flag
	 *            =1 for active observation, = 0 for inactive one (input)
	 * @param diss
	 *            dissimilarity matrix (input)
	 * @param nn
	 *            nearest neighbor sequence number (calculated)
	 * @param nndiss
	 *            nearest neigbor dissimilarity (calculated)
	 */
	public static void getNNs(int nrow, int[] flag, double[][] diss, int[] nn,
			double[] nndiss) {
		int minobs;
		double mindist;
		for (int i1 = 0; i1 < nrow; i1++) {
			if (flag[i1] == 1) {
				minobs = -1;
				mindist = MAXVAL;
				for (int i2 = 0; i2 < nrow; i2++) {
					if ((diss[i1][i2] < mindist) && (i1 != i2)) {
						mindist = diss[i1][i2];
						minobs = i2;
					}
				}
				nn[i1] = minobs + 1;
				nndiss[i1] = mindist;
			}
		}
		// Return type void => no return stmt
	} // getNNs

	/**
	 * Method ClustMat, updates cluster structure matrix following an
	 * agglomeration
	 * 
	 * @param nrow
	 *            row dimension or number of observations (input)
	 * @param clusters
	 *            list of agglomerations, stored as array of pairs of cluster
	 *            sequence numbers (input, and updated)
	 * @param clust1
	 *            first agglomerand (input)
	 * @param clust2
	 *            second agglomerand (input)
	 * @param ncl
	 *            number of clusters remaining (input)
	 */
	public static void ClustMat(int nrow, int[][] clusters, int clust1,
			int clust2, int ncl) {
		// If either clust* is not initialized, then we must init. clusters
		if ((clust1 == 0) || (clust2 == 0)) {
			for (int j = 0; j < nrow; j++) {
				for (int i = 0; i < nrow; i++) {
					clusters[i][j] = 0;
				}
			}
			// Adjust for 0-sequencing in extreme right-hand col values.
			for (int i = 0; i < nrow; i++)
				clusters[i][ncl - 1] = i + 1;
			return;
		}

		// For some agglomeration, we are told that label clust1 and
		// label clust2, among all labels in col. ncl-1 (ncl ranges over
		// 0 thru nrow-1) are to be agglomerated
		// We have arranged that order is such that clust1 < clust2
		// Note: clust1 and clust2 are 1-sequenced and not 0-sequenced

		int ncl1;
		ncl1 = ncl - 1;
		for (int i = 0; i < nrow; i++) {
			clusters[i][ncl1] = clusters[i][ncl];
			if (clusters[i][ncl1] == clust2)
				clusters[i][ncl1] = clust1;
		}
		// Return type void => no return stmt
	} // ClustMat

	// Little method for helping in output formating
	public static String getSpaces(int n) {
		StringBuffer sb = new StringBuffer(n);
		for (int i = 0; i < n; i++)
			sb.append(' ');
		return sb.toString();
	}

	// main method to run HC algorithm for fixed number of clusters
	public void run() {

		runHC();
		getResult();

	}

	// main method for variable number of clusters
	public void runBest() {

		runHC();
		int iter = 1 + (int) (nrow / 2);
		double[] selec = new double[iter];
		for (int j = 0; j < iter; j++)
			selec[j] = Double.MAX_VALUE;

		int[] clus = new int[iter];

		int N = 0;
		for (int j = 0; j < iter; j++) {
			int nclus = 2 + j;
			setClusters(nclus);
			getResult();
			selec[j] = this.compactness;
			clus[j] = nclus;
			// stop if it's increasing
			N++;
			if (j > 5) {
				if (selec[j - 1] < selec[j] && selec[j - 2] < selec[j - 1]) {
					break;

				}
			}
			;
		}

		// VEC.printVect(selec, 4, 10);
		int ibest = ArrayOps.findSmallest(selec, N);
		setClusters(clus[ibest]);
		getResult();
		this.description = "Hierarchical clustering algorithm, best estimate";
	}

	/**
	 * Run HCL
	 * 
	 */
	private void runHC() {

		int[][] clusters = new int[nrow][nrow]; // cluster label matrix
		int[] nn = new int[nrow]; // cluster nearest neigh's
		int[] flag = new int[nrow]; // flag of active obs
		double[] nndiss = new double[nrow]; // nearest neigh diss's
		double[] clcard = new double[nrow]; // cluster cardinality
		double[] mass = new double[nrow]; // observation masses

		int minobs;
		double mindist;
		int ncl; // Initial number of clusters
		ncl = nrow;

		for (int i = 0; i < nrow; i++) {
			flag[i] = 1;
			clcard[i] = 1.0;
			mass[i] = 1.0;
		}

		// We may wish to normalize out input data,
		// and create mass and cpoids, resp. row and col. masses
		// In the following: row/col. masses are row/col sums div. by total
		// double tot = 0.0;
		// for (int i = 0; i < nrow; i++) {
		// for (int j = 0; j < ncol; j++) {
		// mass[i] += indat[i][j];
		// cpoids[j] += indat[i][j];
		// tot += indat[i][j];
		// }
		// }
		// And then we det. fij <- xij/fi*sqrt(fj)
		// where fi and fj are, resp., row and col. masses
		// for (int j = 0; j < ncol; j++) {
		// cpoids[j] = Math.sqrt(cpoids[j]/tot);
		// }
		// for (int i = 0; i < nrow; i++) {
		// mass[i] /= tot;
		// for (int j = 0; j < ncol; j++) {
		// indat[i][j] /= (tot*mass[i]*cpoids[j]);
		// }
		// }
		// System.out.println("Normalized array to be analyzed:");
		// printMatrix(nrow, ncol, indat, 4, 10);
		// System.out.println("Row masses:");
		// printVect(mass, 4, 10);

		// Alternative normalizations include:
		// Centre row vectors to zero mean, and reduce to unit std. dev.
		// We'll use no normalization. We'll take masses as equal to
		// cluster cardinalities.

		// Get dissimilarities
		diss = new double[nrow][nrow];
		diss = Dissim(nrow, ncol, mass, indat);
		// Print it out
		// System.out.println("Dissimilarity matrix for analysis:");
		// VEC.printMatrix(nrow, nrow, diss, 4, 10);

		// Get nearest neighbors and nearest neighbor dissimilarities
		getNNs(nrow, flag, diss, nn, nndiss);
		// System.out.println("Nearest neighbors:");
		// printVect(nn, 4, 10);
		// System.out.println("Nearest neighbors dissimilarities:");
		// printVect(nndiss, 4, 10);

		// Get closest neighbors, using nndiss, followed by nn
		int clust1 = 0;
		int clust2 = 0;
		int cl1 = 0;
		int cl2 = 0;
		// Call to initialize
		ClustMat(nrow, clusters, clust1, clust2, ncl);
		// System.out.println("No. of clusters: " + ncl);
		// System.out.println(" Cluster label matrix:");
		// printMatrix(nrow, nrow, clusters, 4, 10);

		// Loop to carry out series of nrow-1 agglomerations
		do {

			minobs = -1;
			mindist = MAXVAL;
			for (int i = 0; i < nrow; i++) {
				if (flag[i] == 1) {
					if (nndiss[i] < mindist) {
						mindist = nndiss[i];
						minobs = i;
					}
				}
			}
			// minobs is one cluster label, the other is nn[minobs]
			// Adjust for 0-sequencing
			if (minobs < nn[minobs]) {
				clust1 = minobs + 1;
				clust2 = nn[minobs];
			}
			if (minobs > nn[minobs]) {
				clust2 = minobs + 1;
				clust1 = nn[minobs];
			}
			// Now we have clust1 < clust2, and we'll agglomerate

			/*
			 * System.out.println(" clus#1: " + clust1 + "; clus#2: " + clust2 + ";
			 * new card: " + (clcard[clust1-1]+ clcard[clust2-1]) + "; # clus
			 * left: " + ncl + "; mindiss: " + mindist);
			 */

			// Now we will carry out an agglomeration
			ncl = ncl - 1;
			ClustMat(nrow, clusters, clust1, clust2, ncl);
			// System.out.println("#clusters left: " + ncl +
			// "; cluster label matrix: ");
			// printMatrix(nrow, nrow, clusters, 4, 10);

			// Update the following: diss, nndiss
			// Strategy:
			// nn[clust2] ceases to exist; similarly nndiss[clust2]
			// ... for all occurrences of clust2
			// nn[clust1] must be updated, as must nndiss[clust1]
			// Only other change is for any nn[i] such that nn[i] =
			// ... clust1 or clust2; this must be updated.

			// First, update diss

			cl1 = clust1 - 1;
			cl2 = clust2 - 1;
			for (int i = 0; i < nrow; i++) {
				// The following test is important:
				if ((i != cl1) && (i != cl2) && (flag[i] == 1)) {
					// Minimum variance criterion
					diss[cl1][i] = (mass[cl1] + mass[i])
							/ (mass[cl1] + mass[cl2] + mass[i]) * diss[cl1][i]
							+ (mass[cl2] + mass[i])
							/ (mass[cl1] + mass[cl2] + mass[i]) * diss[cl2][i]
							- (mass[i]) / (mass[cl1] + mass[cl2] + mass[i])
							* diss[cl1][cl2];
					// For other alternative agglomeration criteria,
					// e.g. single or complete linkage, see the update
					// formulas in F. Murtagh, "Multidimensional
					// Clustering Algorithms", Physica-Verlag, 1985, p. 68.
					// ( (clcard[cl1]*clcard[cl2])/(clcard[cl1]+clcard[cl2]) )*
					// (diss[cl1][i] - diss[cl2][i]);
					diss[i][cl1] = diss[cl1][i];
				}
			}
			clcard[cl1] = clcard[cl1] + clcard[cl2];
			mass[cl1] = mass[cl1] + mass[cl2];
			// Cluster label clust2 is knocked out
			for (int i = 0; i < nrow; i++) {
				diss[cl2][i] = MAXVAL;
				diss[i][cl2] = diss[cl2][i];
				flag[cl2] = 0;
				nndiss[cl2] = MAXVAL;
				mass[cl2] = 0;
			}

			// Get nearest neighbors and nearest neighbor dissimilarities
			getNNs(nrow, flag, diss, nn, nndiss);
			// System.out.println("Nearest neighbors of items 1, 2, ... n:");
			// printVect(nn, 4, 10);
			// System.out.println
			// ("Corresponding nearest neighbors dissimilarities:");
			// printVect(nndiss, 4, 10);

		} // End of agglomerations loop
		while (ncl > 1);

		// For convenience, transpose the cluster labels
		for (int i1 = 0; i1 < nrow; i1++) {
			for (int i2 = 0; i2 < nrow; i2++) {
				tclusters[i2][i1] = clusters[i1][i2];
			}
		}

		// Row nrow: nrow clusters
		// printMatrix(nrow, nrow, clusters, 4, 4);

		// Row 1: just one cluster
		// Row 2: two clusters
		// Row 3: three clusters
		// ...
		// Row nrow: nrow clusters
		// VEC.printMatrix(nrow, nrow, tclusters, 4, 4);

	} // End of run

	/**
	 * Get description
	 * 
	 * @return String
	 */

	public String getName() {
		return this.description;
	}

} // End of class HCL

