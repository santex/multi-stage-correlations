package com.multistage.correlations.algorithms;

import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.cluster.Get;
import com.multistage.correlations.gui.*;
import com.multistage.correlations.utils.*;

import Jama.*;

/**
 */

public class kMeans1 {

	public final double MAXVAL = 1.0e12;

	public final double R = 0.999; // See Spaeth, p. 103

	private double[][] indat;

	private double[][] indatstd;

	private int nrow, ncol, numClusters;

	private double compactness;

	private double[] cardinality;

	private int epochmax = 15;

	private int Ierr = 0; // error statement

	private double[][] clusterCenters;

	private int[] assignment;

	private String description;

	// cluster centers
	private DataHolder cMeans;

	// cluster centers
	private DataHolder cSeeds;

	// input: data holder and number of clusters
	public kMeans1() {

		this.nrow = SetEnv.NRow;
		this.ncol = SetEnv.Dim;
		// Min=SetEnv.Min;
		// Max=SetEnv.Max;
		Ierr = 0;
		this.numClusters = 0;
		this.compactness = 9999;
		this.Ierr = 0;
		this.description = "K-means clustering using exchange method";

		// Input array, values to be read in successively, float
		indat = new double[nrow][this.ncol];
		assignment = new int[nrow];

		for (int i = 0; i < nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			for (int i2 = 0; i2 < this.ncol; i2++)
				indat[i][i2] = dp.getAttribute(i2);
		}

		// System.out.println(" No. of rows, nrow = " + nrow);
		// System.out.println(" No. of columns, ncol = " + ncol);

		// Data preprocessing - standardization and determining correlations
		indatstd = VEC.Standardize(nrow, ncol, indat);

	} // end initialisation

	// set Max number of Epoch's
	public void setEpochMax(int N) {
		epochmax = N;
	}

	// get error statement: 0 - looks good
	public int getError() {
		return this.Ierr;
	}

	// set clusters
	public void setClusters(int Nclu) {
		this.numClusters = Nclu;
		// System.out.println(" No. of clusters, numClusters = " + numClusters);
	}

	
	/**
	 *   Get number of clusters.
	 */
	public int  getClusters() {
		
		return this.numClusters;
	}
	
	
	
	/**
	 * Returns cluster centers
	 * 
	 * @return DataHolder
	 */
	public DataHolder getCenters() {
		return cMeans;
	} // end cluster centers

	/**
	 * Returns seed centers
	 * 
	 * @return DataHolder
	 */
	public DataHolder getSeedHolder() {
		return cSeeds;
	}

	

	/**
	 * Returns the number of points in each cluster
	 * 
	 * @return int[] getNumberPoints
	 */
	public int[] getNumberPoints() {
		int[] size = new int[this.numClusters];
		for (int i = 0; i < this.numClusters; i++)
			size[i] = (int) cardinality[i];
		return size;
	} // end

	/**
	 * This method returns the Compactness and Separation measure of cluster
	 * validity (see Fuzzy Algorithms With Applications to Image Processing and
	 * Pattern Recognition, Zheru Chi, Hong Yan, Tuan Pham, World Scientific,
	 * pp. 93)
	 */
	public double getCompactness() {
		return this.compactness;
	}

	// run in the best cluster mode
	public void runBest() {

		int iter = 1 + (int) (nrow / 2);
		double[] selec = new double[iter];
		for (int j = 0; j < iter; j++)
			selec[j] = Double.MAX_VALUE;

		int[] clus = new int[iter];

		int N = 0;
		for (int j = 0; j < iter; j++) {
			int nclus = 2 + j;
			setClusters(nclus);
			run();
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
		run();
		this.description = "K-means clustering using exchange method"
				+ " for best estimate";
	}

/**
 * 
 * @author H.Geissler
 * run KT algorithm
 *
 */
	public void run() {

		Ierr = 0;
		// use Jama matrix class
		Matrix X = new Matrix(indatstd);
		clusterCenters = new double[this.numClusters][this.ncol];
		// Sums of squares and cross-products matrix
		Matrix Xprime = X.transpose();
		Matrix SSCP = Xprime.times(X);
		// Eigen decomposition
		EigenvalueDecomposition evaldec = SSCP.eig();
		Matrix evecs = evaldec.getV();

		// evecs contains the cols. ordered right to left
		// Evecs is the more natural order with cols. ordered left to right
		// So to repeat: leftmost col. of Evecs is assoc with largest Evals
		// Evecs ordered from left to right
		// reverse order of Matrix evecs into Matrix Evecs
		double[][] tempold = evecs.getArray();
		double[][] tempnew = new double[ncol][ncol];
		for (int j1 = 0; j1 < ncol; j1++) {
			for (int j2 = 0; j2 < ncol; j2++) {
				tempnew[j1][j2] = tempold[j1][ncol - j2 - 1];
			}
		}
		Matrix Evecs = new Matrix(tempnew);
		// Evecs.print(10,4);

		// Projections - for rows
		// Row projections in new space, X U Dims: (nrow x ncol) x (ncol x ncol)
		Matrix rowproj = X.times(Evecs);
		// rowproj.print(10,4);

		// -------------------------------------------------------------------
		// Now numClusters-means partitioning using exchange method

		double[] tempvec = new double[nrow];
		double[] rproj = new double[nrow];
		tempold = rowproj.getArray();
		for (int i = 0; i < nrow; i++) {
			tempvec[i] = tempold[i][0];
			rproj[i] = tempold[i][0];
		}
		VEC.inSort(tempvec); // Sort in place, modifying values.
		// VEC.printVect(tempvec, 4, 10);

		// First choose breakpoints
		int[] breakindexes = new int[numClusters + 1];
		double[] breakpoints = new double[numClusters + 1];
		breakpoints[0] = tempvec[0] - 0.1; // Offset so that strict ">" used
											// later
		breakpoints[numClusters] = tempvec[nrow - 1];
		for (int i = 1; i < numClusters; i++) {
			breakindexes[i] = i * nrow / numClusters;
			breakpoints[i] = tempvec[breakindexes[i]];
		}
		// VEC.printVect(breakpoints, 4, 10);

		double[][] gpmeans = new double[numClusters][ncol];
		cardinality = new double[numClusters];
		for (int i = 0; i < numClusters - 1; i++) {
			cardinality[i] = 0.0;
			for (int j = 0; j < ncol - 1; j++) {
				gpmeans[i][j] = 0.0;
			}
		}

		for (int icl = 0; icl < numClusters; icl++) {
			// lo, hi (resp.) are breakpoints[i], breakpoints[i+1]
			// cluster = icl, with values 0, 1, 2, ... numClusters-1
			for (int i = 0; i < nrow; i++) {
				// Not terribly efficient - fix later
				if ((rproj[i] > breakpoints[icl])
						&& (rproj[i] <= breakpoints[icl + 1])) {
					assignment[i] = icl;
					cardinality[icl] += 1.0;
					for (int j = 0; j < ncol; j++) {
						gpmeans[icl][j] += indat[i][j];
					}
				}
			}
			for (int j = 0; j < ncol; j++)
				gpmeans[icl][j] /= cardinality[icl];
		}
		for (int i = 0; i < numClusters; i++) {
			// System.out.println
			// (" Cluster number, cardinality = " + i + " " + cardinality[i]);
			if (cardinality[i] <= 1) {
				// System.out.println
				// (" A cluster has cardinality <= 1; stopping.");
				// Card = 1 in expressions below, with card - 1 will cause
				// zero divide. So use this as a condition to halt.
				Ierr = 1;
				break;
			}
		}

		// System.out.println("Initial cluster means:");
		// VEC.printMatrix(numClusters, ncol, gpmeans, 4, 10);

		cSeeds = new DataHolder();
		for (int i = 0; i < numClusters; i++) {
			double[] a = new double[ncol];
			for (int j = 0; j < ncol; j++) {
				a[j] = gpmeans[i][j];
			}
			DataPoint c = new DataPoint(a, ncol);
			// c.showAttributes();
			cSeeds.add(c);
		}
		// cSeeds.Print();

		// VEC.printVect(assignment, 4, 10);

		// -------------------------------------------------------------------
		// Having an initial partition, with group mean vectors,
		// cardinalities, and asssignments, we now proceed...

		double compac = 0.0;
		for (int i = 0; i < nrow; i++) {
			for (int j = 0; j < ncol; j++) {
				compac += Math
						.pow(indat[i][j] - gpmeans[assignment[i]][j], 2.0);
			}
		}

		// System.out.println("Compactness = " + compactness);

		int epoch = 0;
		double oldcompactness = MAXVAL;
		double prevcontrib = 0.0;
		double newcontrib = 0.0;
		double bestnewcontrib;
		int bestclus;
		int oldclus = 0;
		double eps = 0.001;
		double dissim = 0.0;
		int nochange = 1;

		while ((nochange > 0) && (compac < (oldcompactness - eps))
				&& (epoch <= epochmax)) {
			nochange = 0;
			epoch += 1;
			// System.out.println("EPOCH NUMBER IS = " + epoch);

			for (int i = 0; i < nrow; i++) {

				bestnewcontrib = MAXVAL;
				bestclus = 0;

				for (int c = 0; c < numClusters; c++) {

					dissim = 0.0;
					for (int j = 0; j < ncol; j++)
						dissim += Math.pow(indat[i][j] - gpmeans[c][j], 2.0);

					if (assignment[i] == c) { // Same cluster
						prevcontrib = (cardinality[c] / (cardinality[c] - 1.0))
								* dissim;
					} else { // Potentially new cluster
						newcontrib = (cardinality[c] / (cardinality[c] + 1.0))
								* dissim;
						if (newcontrib < bestnewcontrib) {
							bestnewcontrib = newcontrib;
							bestclus = c;
						}
					}
				} // End of c loop

				// Is bestnewcontrib better than prevcontrib?
				if (bestnewcontrib < R * prevcontrib) {
					// A change is in store
					nochange += 1;

					oldcompactness = compac;
					compac = compac + bestnewcontrib - prevcontrib;
					// System.out.println("Old compactness = " +
					// oldcompactness);
					// System.out.println("New compactness = " + compactness);

					oldclus = assignment[i];
					// bestclus is the new cluster
					for (int j = 0; j < ncol; j++) {
						gpmeans[oldclus][j] = (1.0 / (cardinality[oldclus] - 1.0))
								* (cardinality[oldclus] * gpmeans[oldclus][j] - indat[i][j]);
						gpmeans[bestclus][j] = (1.0 / (cardinality[bestclus] + 1.0))
								* (cardinality[bestclus] * gpmeans[bestclus][j] + indat[i][j]);
					}

					cardinality[oldclus] -= 1.0;
					if (cardinality[oldclus] <= 1.0) {
						// System.out.println
						// ("Cluster has become too small: stopping.");
						// System.out.println
						// ("Try a smaller number of clusters instead.");
						// System.exit(1);
						Ierr = 2;
						break;
					}
					cardinality[bestclus] += 1.0;
					assignment[i] = bestclus;

					// Check on progress
					// VEC.printVect(cardinality, 4, 10);

				}

			} // End of i loop
		} // End of while loop

		// System.out.println();
		// System.out.println("Cluster centers:");
		// VEC.printMatrix(numClusters, ncol, gpmeans, 4, 10);

		cMeans = new DataHolder();
		for (int i = 0; i < numClusters; i++) {
			double[] a = new double[ncol];
			for (int j = 0; j < ncol; j++) {
				clusterCenters[i][j] = gpmeans[i][j];
				a[j] = gpmeans[i][j];
			}
			DataPoint c = new DataPoint(a, ncol);
			// c.showAttributes();
			cMeans.add(c);
		}

		// cMeans.Print();

		// System.out.println("Final cluster cardinalities:");
		// VEC.printVect(cardinality, 4, 10);

		// System.out.println("Assignments:");
		// VEC.printVect(assignment, 1, 2);

		// fill this
		for (int i = 0; i < nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			dp.assignToCluster(assignment[i]);
			// System.out.println("clus="+i);
		}
		
//		 calculate compactness
		this.compactness = Get.compactness(indat, assignment, this.numClusters,
				clusterCenters);
		// -------------------------------------------------------------------
		// That's it.

	} // end of main

	public String getName() {
		return this.description;
	}

} //  end

