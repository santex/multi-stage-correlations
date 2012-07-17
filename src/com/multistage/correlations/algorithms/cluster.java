package com.multistage.correlations.algorithms;

import com.multistage.correlations.cluster.DataPoint;


/*
 * Represents an abstraction for a cluster of data points in two dimensional space
 *
 * Manas Somaiya
 * Computer and Information Science and Engineering
 * University of Florida
 *
 * Created: October 29, 2003
 * Last updated: October 30, 2003
 *
 */



/**
 * Represents an abstraction for a cluster of data points in many dimensional space
 * @author H.Geissler 
 */
 class cluster {


	/** Cluster Number */
	private int clusterNumber;


	/** Mean data point of this cluster */
	private DataPoint mean;


	/**
	 * Returns a new instance of cluster
	 *
	 * @param	_clusterNumber	the cluster number of this cluster
	 */
	public cluster(int _clusterNumber) {

		this.clusterNumber = _clusterNumber;

	} // end of cluster()


	/**
	 * Sets the mean data point of this cluster
	 *
	 * @param	meanDataPoint	the new mean data point for this cluster
	 */
	public void setMean(DataPoint meanDataPoint) {

		this.mean = meanDataPoint;

	} // end of setMean()


	/**
	 * Returns the mean data point of this cluster
	 *
	 * @return	the mean data point of this cluster
	 */
	public DataPoint getMean() {

		return this.mean;

	} // end of getMean()


	/**
	 * Returns the cluster number of this cluster
	 *
	 * @return	the cluster number of this cluster
	 */
	public int getClusterNumber() {

		return this.clusterNumber;

	} // end of getClusterNumber()


	/**
	 * Main method -- to test the cluster class
	 *
	 * @param	args	command line arguments
	 */
	public static void main(String[] args) {

		cluster c1 = new cluster(1);

                double [] a1= new double[3];
                a1[0]=2.;
                a1[1]=3.;
                a1[2]=1.;
		c1.setMean(new DataPoint(a1,3));
		System.out.println(c1.getMean());


	} // end of main()

} // end of class

