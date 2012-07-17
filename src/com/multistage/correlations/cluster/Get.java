/**
 * 
 */
package com.multistage.correlations.cluster;

import com.multistage.correlations.gui.*;

/**
 * Some useful methods related to the clustering procedure 
 * @author H.Geissler 
 * 
 */
public class Get {

	/*	  *//**
			 * This method returns the cluster compactness
			 * 
			 * @param indat[][]
			 *            data matrix
			 * @param numClusters
			 *            number of clusters
			 * @param clusterCenters[][]
			 *            cluster centers
			 * @return compactness
			 */

	public static double compactness(double indat[][], int[] assignment,
			int numClusters, double clusterCenters[][]) {

		int nrow = SetEnv.NRow;
		int ncol = SetEnv.Dim;
		double[] aPixel = new double[ncol];

		double[] dsize = new double[numClusters];
		double[] isize = new double[numClusters];
		for (int j = 0; j < numClusters; j++) {
			dsize[j] = 0;
			isize[j] = 0;
		}

		double cs = 0;
		double maxSize = 0;
		// For all data values and clusters
		for (int h = 0; h < nrow; h++) {
			int current = assignment[h];

			if (current >= 0) {
				// Get the current pixel data.
				for (int b = 0; b < ncol; b++) aPixel[b] = indat[h][b];
					double ddd=calcDistance(aPixel, clusterCenters[current]);					
					dsize[current] = dsize[current]+ddd;
					maxSize= Math.max(maxSize, ddd );
					isize[current]++;
			}
		}

		
		// average size
		double csumm=0;
		for (int j = 0; j < numClusters; j++) {
			csumm = csumm + dsize[j] / (double) isize[j];
		}
		csumm=csumm/numClusters;
		
		/*
	//	double csumm = 0;
		double maxSize = 0;
		for (int j = 0; j < numClusters; j++) {
			// csumm = csumm + dsize[j] / (double) isize[j];
			double ddd =dsize[j]; //  / (double) isize[j];
            maxSize= Math.max(maxSize, ddd );
		}
          */  
            
         // average size
		//   csumm = csumm / numClusters;

		


		// Calculate minimum distance between ALL clusters
		double minDist = Double.MAX_VALUE;
		for (int c1 = 0; c1 < numClusters - 1; c1++)
			for (int c2 = c1 + 1; c2 < numClusters; c2++) {
				double distance = calcDistance(clusterCenters[c1],
						clusterCenters[c2]);
				minDist = Math.min(minDist, distance);
			}

		cs = maxSize / minDist;

 // System.out.println(" maxSize="+maxSize+ " min DIS="+minDist
//		 +" average size="+csumm);
 
		return cs;
	}

	/**
	 * This method calculates the squared distance between two N-dimesional
	 * vectors.
	 * 
	 * @param a1
	 *            the first data vector.
	 * @param a2
	 *            the second data vector.
	 * @return the squared distance between those vectors.
	 */
	public static double calcSquaredDistance(double[] a1, double[] a2) {
		double distance = 0f;
		for (int e = 0; e < a1.length; e++)
			distance += (a1[e] - a2[e]) * (a1[e] - a2[e]);
		return (double) distance;
	}

	/**
	 * This method calculates the Euclidean distance between two N-ncolsional
	 * vectors.
	 * 
	 * @param a1
	 *            the first data vector.
	 * @param a2
	 *            the second data vector.
	 * @return the Euclidean distance between those vectors.
	 */
	public static double calcDistance(double[] a1, double[] a2) {
		double distance = 0f;
		for (int e = 0; e < a1.length; e++)
			distance += (a1[e] - a2[e]) * (a1[e] - a2[e]);
		return (double) Math.sqrt(distance);
	}

}
