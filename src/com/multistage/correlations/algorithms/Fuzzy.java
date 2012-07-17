package com.multistage.correlations.algorithms;


import java.util.Random;

import com.multistage.correlations.clcontrol.Global;
import com.multistage.correlations.cluster.DataHolder;
import com.multistage.correlations.cluster.DataPoint;
import com.multistage.correlations.cluster.Get;
import com.multistage.correlations.gui.*;
import com.multistage.correlations.utils.*;

/*
 * This class implements a basic Fuzzy C-Means clustering algorithm.
 */
public class Fuzzy {

	private double[][] indat;

	private int nrow, ncol;

	private int Ierr = 0; // error statement

	// cluster centers
	private DataHolder cMeans;

	private int maxIterations, numClusters;

	// The FCM additional parameters and membership function values.
	private double fuzziness; // "m"

	private double[][] membership;

	// The iteration counter will be global so we can get its value on the
	// middle of the clustering process.
	private int iteration;

	// A metric of clustering "quality", called "j" as in the equations.
	private double j = 1000000;

	// A small value, if the difference of the cluster "quality" does not
	// changes beyond this value, we consider the clustering converged.
	private double epsilon;

	private long position;

	// The cluster centers.
	private double[][] clusterCenters;

	// A big array with all the input data and a small one for a single pixel.
	private double[] aPixel;

	// A big array with the output data (cluster indexes).
	private int[] assignment;

	private String description;

	private Random generator;

	// main initialisation
	public Fuzzy() {

		nrow = SetEnv.NRow;
		ncol = SetEnv.Dim;
		Ierr = 0;
		numClusters = 0;
		description = "Fuzzy C-means clustering";
		indat = new double[nrow][ncol]; // hold data
		aPixel = new double[ncol];
		assignment = new int[nrow];

		// Input array, values to be read in successively, float
		for (int i = 0; i < nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			for (int i2 = 0; i2 < ncol; i2++)
				indat[i][i2] = dp.getAttribute(i2);
		}

	}

	/**
	 * @param numClusters
	 *            Set the desired number of clusters.
	 */
	// set clusters
	public void setClusters(int N) {

		if (N > this.nrow) {
			System.out.println("Too many clusters! Set to number of points");
			N = this.nrow;
		}
		this.numClusters = N;
	}

	
	
	/**
	 *   Get number of clusters.
	 */
	public int  getClusters() {
		
		return this.numClusters;
	}
	
	
	
	/**
	 * GetMembeship
	 */
	// set clusters
	public double[][] Membeship() {

		return this.membership;

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
		this.fuzziness = fuzziness;
		this.epsilon = epsilon;
	}

	// set epsilon, fuzeness
	public void Delete() {

		this.numClusters = 0;
		this.maxIterations = 0;
		this.fuzziness = 00;
		this.epsilon = 0;
		this.nrow = 0;
		this.ncol = 0;
		this.indat = null;
		this.clusterCenters = null;
		this.membership = null;
		this.generator = null;
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
	 * Returns the number of points in each cluster
	 * 
	 * @return int[] getNumberPoints
	 */
	public int[] getNumberPoints() {

		int[] NN = new int[numClusters];

		return NN;
	} // end

	// run in the best cluster mode
	public void runBest() {

		int iter = 1 + (int) (nrow / 2);
		
//		iter=4;
		
		double[] selec = new double[iter];
		for (int j = 0; j < iter; j++)
			selec[j] = Double.MAX_VALUE;

		int[] clus = new int[iter];

		int N = 0;
		for (int j = 0; j < iter; j++) {
			int nclus = 2 + j;
			setClusters(nclus);
			run();
			selec[j] = getCompactness();
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
		this.numClusters=clus[ibest];
		this.description = "Fuzzy C-means clustering: Best estimate";
		// System.out.println("Best clusters=" + this.numClusters);
		// now run
		run();
	}

	/**
	 * Classic Fuzzy C-Means clustering algorithm: Calculate the cluster
	 * centers. Update the membership function. Calculate statistics and repeat
	 * from 1 if needed.
	 */
	public void run() {

		iteration = 0;
		clusterCenters = new double[this.numClusters][this.ncol];
		membership = new double[this.nrow][this.numClusters];
		// Initialize the membership functions randomly.
		generator = new Random(); // easier to debug if a seed is used
		// For each data point (in the membership function table)
		for (int h = 0; h < this.nrow; h++) {
			// For each cluster's membership assign a random value.
			double sum = 0f;
			for (int c = 0; c < this.numClusters; c++) {
				membership[h][c] = 0.01f + generator.nextDouble();
				sum += membership[h][c];
			}
			// Normalize so the sum of MFs for a particular data point will be
			// equal to 1.
			for (int c = 0; c < this.numClusters; c++)
				membership[h][c] /= sum;
		}
		// Initialize the global position value.
		position = 0;
		double lastJ;

		// Calculate the initial objective function just for kicks.
		lastJ = calculateObjectiveFunction();
		// Do all required iterations (until the clustering converges)
		for (iteration = 0; iteration < maxIterations; iteration++) {
			// Calculate cluster centers from MFs.
			calculateClusterCentersFromMFs();
			// Then calculate the MFs from the cluster centers !
			calculateMFsFromClusterCenters();
			// Then see how our objective function is going.
			j = calculateObjectiveFunction();
			if (Math.abs(lastJ - j) < epsilon)
				break;
			lastJ = j;
		} // end of the iterations loop.
		// Means that all calculations are done, too.
		position = getSize();

		// VEC.printMatrix(nrow,numClusters,membership,5,5);
		// VEC.printMatrix(numClusters,ncol,clusterCenters,5,5);

		for (int h = 0; h < this.nrow; h++)
			assignment[h] = -1;
		// assume that a cluster has 68% probability
		for (int c = 0; c < this.numClusters; c++) {
			for (int h = 0; h < this.nrow; h++) {
				if (membership[h][c] > Global.ShowProbab)
					assignment[h] = c;
			}
		}

		// System.out.println("Global.ShowProbab="+Global.ShowProbab);
		// System.out.println("clusters=" + this.numClusters);

		// fill this
		for (int i = 0; i < nrow; i++) {
			DataPoint dp = SetEnv.DATA.getRaw(i);
			dp.assignToCluster(assignment[i]);
			// dp.assignToCluster( 0 );
	//		System.out
	//				.println("point =" + i + " assigned to =" + assignment[i]);
		}

		// VEC.printMatrix(nrow,numClusters,membership,5,5);
	}

	/**
	 * This method calculates the cluster centers from the membership functions.
	 */
	private void calculateClusterCentersFromMFs() {
		double top, bottom;
		// For each band and cluster
		for (int b = 0; b < this.ncol; b++)
			for (int c = 0; c < this.numClusters; c++) {
				// For all data points calculate the top and bottom parts of the
				// equation.
				top = bottom = 0;
				for (int h = 0; h < this.nrow; h++) {
					// Index will help locate the pixel data position.
					top += Math.pow(membership[h][c], fuzziness) * indat[h][b];
					bottom += Math.pow(membership[h][c], fuzziness);
				}
				// Calculate the cluster center.
				clusterCenters[c][b] = top / bottom;
				// Upgrade the position vector (batch).
				// position += width*height;
			}
	}

	/**
	 * This method calculates the membership functions from the cluster centers.
	 */
	private void calculateMFsFromClusterCenters() {
		double sumTerms;
		// For each cluster and data point
		for (int c = 0; c < this.numClusters; c++)
			for (int h = 0; h < this.nrow; h++) {
				// Get a pixel (as a single array).
				for (int b = 0; b < this.ncol; b++)
					aPixel[b] = this.indat[h][b];
				// Top is the distance of this data point to the cluster being
				// read.
				double top = Get.calcDistance(aPixel, clusterCenters[c]);
				// Bottom is the sum of distances from this data point to all
				// clusters.
				sumTerms = 0f;
				for (int ck = 0; ck < numClusters; ck++) {
					double thisDistance = Get.calcDistance(aPixel,
							clusterCenters[ck]);
					sumTerms += Math.pow(top / thisDistance,
							(2f / (fuzziness - 1f)));
				}
				// Then the MF can be calculated as...
				this.membership[h][c] = (double) (1f / sumTerms);
				// Upgrade the position vector (batch).
				position += (ncol + numClusters);
			}
	}

	/*
	 * This method calculates the objective function ("j") which reflects the
	 * quality of the clustering.
	 */
	private double calculateObjectiveFunction() {
		double j = 0;
		// For all data values and clusters
		for (int h = 0; h < nrow; h++)
			for (int c = 0; c < numClusters; c++) {
				// Get the current pixel data.
				for (int b = 0; b < ncol; b++)
					aPixel[b] = indat[h][b];
				// Calculate the distance between a pixel and a cluster center.
				double distancePixelToCluster = Get.calcDistance(aPixel,
						clusterCenters[c]);
				j += distancePixelToCluster
						* Math.pow(membership[h][c], fuzziness);
				// Upgrade the position vector (batch).
				position += (2 * ncol);
			}
		return j;
	}

	/**
	 * This method returns the estimated size (steps) for this task. The value
	 * is, of course, an approximation, just so we will be able to give the user
	 * a feedback on the processing time. In this case, the value is calculated
	 * as the number of loops in the run() method.
	 */
	public long getSize() {
		/*
		 * // Return the estimated size for this task: return
		 * (long)maxIterations* // The maximum number of iterations times (
		 * (numClusters*width*height*(2*ncol))+ // Step 0 of method run()
		 * (width*height*ncol*numClusters)+ // Step 1 of method run()
		 * (numClusters*width*height*(ncol+numClusters))+ // Step 2 of run()
		 * (numClusters*width*height*(2*ncol)) // Step 3 of method run() );
		 */

		return (long) maxIterations;
	}

	/**
	 * This method returns a measure of the progress of the algorithm.
	 */
	public long getPosition() {
		return position;
	}

	/**
	 * This method returns true if the clustering has finished.
	 */
	public boolean isFinished() {
		return (position == getSize());
	}

	/**
	 * This method returns the Partition Coefficient measure of cluster validity
	 * (see Fuzzy Algorithms With Applications to Image Processing and Pattern
	 * Recognition, Zheru Chi, Hong Yan, Tuan Pham, World Scientific, pp. 91)
	 */
	public double getPartitionCoefficient() {
		double pc = 0;
		// For all data values and clusters
		for (int h = 0; h < this.nrow; h++)
			for (int c = 0; c < this.numClusters; c++)
				pc += membership[h][c] * membership[h][c];
		pc = pc / this.nrow;
		return pc;
	}

	/**
	 * This method returns the Partition Entropy measure of cluster validity
	 * (see Fuzzy Algorithms With Applications to Image Processing and Pattern
	 * Recognition, Zheru Chi, Hong Yan, Tuan Pham, World Scientific, pp. 91)
	 */
	public double getPartitionEntropy() {
		double pe = 0;
		// For all data values and clusters
		for (int h = 0; h < this.nrow; h++)
			for (int c = 0; c < this.numClusters; c++)
				pe += membership[h][c] * Math.log(membership[h][c]);
		pe = -pe / this.nrow;
		return pe;
	}

	// get error statement: 0 - looks good
	public int GetError() {
		return this.Ierr;
	}

	/**
	 * This method returns the Compactness and Separation measure of cluster
	 * validity (see Fuzzy Algorithms With Applications to Image Processing and
	 * Pattern Recognition, Zheru Chi, Hong Yan, Tuan Pham, World Scientific,
	 * pp. 93)
	 */
	public double getCompactness() {
		double cs = 0;
		// For all data values and clusters
		for (int h = 0; h < this.nrow; h++) {
			// Get the current pixel data.
			for (int b = 0; b < this.ncol; b++)
				aPixel[b] = this.indat[h][b];
			for (int c = 0; c < this.numClusters; c++) {
				// Calculate the distance between a pixel and a cluster center.
				double distancePixelToCluster = Get.calcSquaredDistance(aPixel,
						clusterCenters[c]);
				cs += membership[h][c] * membership[h][c]
						* distancePixelToCluster * distancePixelToCluster;
			}
		}
		cs /= (nrow);
		// Calculate minimum distance between ALL clusters
		double minDist = Double.MAX_VALUE;
		for (int c1 = 0; c1 < this.numClusters - 1; c1++)
			for (int c2 = c1 + 1; c2 < this.numClusters; c2++) {
				double distance = Get.calcSquaredDistance(clusterCenters[c1],
						clusterCenters[c2]);
				minDist = Math.min(minDist, distance);
			}
		cs = cs / (minDist * minDist);
		return cs;
	}

	/**
	 * 
	 * 
	 * Get description
	 */

	public String getName() {
		return this.description;
	}

	/*
	 * // The main method contains the body of the program public static void
	 * main(String[] argv) { PrintStream out = System.out;
	 * 
	 * try{ if (argv.length == 0) { System.out.println(" Syntax: java HCL
	 * infile.dat "); System.out.println(" Input file format: ");
	 * System.out.println(" Line 1: integer no. rows, no. cols.");
	 * System.out.println(" Successive lines: matrix values, doubleing");
	 * System.out.println(" Read in row-wise"); System.exit (1); } String
	 * filname = argv[0]; System.out.println (" Input file name: " + filname);
	 *  // Open the matrix file FileInputStream is = new
	 * FileInputStream(filname); BufferedReader bis = new BufferedReader(new
	 * InputStreamReader(is)); StreamTokenizer st = new StreamTokenizer(bis);
	 *  // Row and column sizes, read in first st.nextToken(); nrow =
	 * (int)st.nval; st.nextToken(); ncol = (int)st.nval;
	 * 
	 * System.out.println(" No. of rows, nrow = " + nrow); System.out.println("
	 * No. of cols, ncol = " + ncol);
	 *  // Input array, values to be read in successively, double indat = new
	 * double[nrow][ncol]; double inval;
	 * 
	 *  // New read in input array values, successively System.out.println ("
	 * Input data sample follows as a check, first 4 values."); for (int i = 0;
	 * i < nrow; i++) { for (int j = 0; j < ncol; j++) { st.nextToken(); inval =
	 * (double)st.nval; indat[i][j] = inval; if (i < 2 && j < 2) {
	 * System.out.println(" value = " + inval); } } } System.out.println(); //
	 * VEC.printMatrix(nrow,ncol,indat,5,5); }
	 * 
	 * catch (IOException e) { out.println("error: " + e); System.exit(1); }
	 * 
	 * int numClusters=3; int maxIterations=200; double fuzziness=1.8; double
	 * epsilon=0.0001; Fuzzy km= new Fuzzy(numClusters,maxIterations,
	 * fuzziness,epsilon);
	 *  // run km.run(); VEC.printMatrix(nrow,numClusters,membership,5,5);
	 * VEC.printMatrix(numClusters,ncol,clusterCenters,5,5);
	 * 
	 * 
	 * 
	 *  } // End of main
	 */

}
