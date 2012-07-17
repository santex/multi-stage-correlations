package com.multistage.correlations.cluster;

/*
 * Represents an abstraction for a data point in two dimensional space
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
 * Represents an abstraction for a data point in many-dimensional space
 * 
 * @author H.Geissler
 */
public class DataPoint {

	/** Attributes */
	private double a[];

	/** Dimension of Attributes */
	private int Dimena;

	/** Assigned cluster */
	private int clusterNumber;
	
	private String label = "";

	/**
	 * Creates a new instance of a data point
	 * 
	 * @param xx
	 *            array of dimension dim
	 * @param dim
	 *            dimension
	 */
	public DataPoint(double xx[], int dim) {

		a = new double[dim];
		for (int i = 0; i < dim; i++)
			this.a[i] = xx[i];
		this.Dimena = dim;
		this.clusterNumber = 0;
	} // end of DataPoint()

	/**
	 * Creates a new instance of a data point
	 * 
	 * @param xx
	 *            array
	 */
	public DataPoint(double xx[]) {

		int dim = xx.length;
		a = new double[dim];
		for (int i = 0; i < dim; i++)
			this.a[i] = xx[i];
		this.Dimena = dim;
		this.clusterNumber = 0;
	} // end of DataPoint()

	/**
	 * Get dimension of the point
	 * 
	 * @return dimension (int)
	 */
	public int getDimension() {

		return this.Dimena;
	} // end of DataPoint()

	/**
	 * Get dimension of the point
	 * 
	 * @return dimension (int)
	 */
	public String getLabel() {

		return this.label;
	} // end of DataPoint()

	/**
	 * Get dimension of the point
	 * 
	 * @return dimension (int)
	 */
	public void setLabel(String _label) {

		this.label = _label;
	} // end of DataPoint()

	/**
	 * Assigns the data point to a cluster
	 * 
	 * @param _clusterNumber
	 *            the cluster to which this data point is to be assigned
	 */
	public void assignToCluster(int _clusterNumber) {

		this.clusterNumber = _clusterNumber;

	} // end of assignToCluster()

	/**
	 * Returns the cluster to which the data point belongs
	 * 
	 * @return the cluster number to which the data point belongs
	 */
	public int getClusterNumber() {

		return this.clusterNumber;

	} // end of getClusterNumber()

	/**
	 * Returns the attribute of data point
	 * 
	 * @param index  current idex
	 * @return  the value in many dimensions
	 */
	public double getAttribute(int index) {

		if (index < this.Dimena) {
			return this.a[index];
		} else {
			return -999999.;
		}

	} // end of getX()

	/**
	 * Print all attributes of a data point
	 * 
	 */
	public void showAttributes() {
		String s = "(";
		for (int i = 0; i < this.Dimena; i++) {
			if (i < this.Dimena - 1)
				s = s + this.getAttribute(i) + ",";
			if (i == this.Dimena - 1)
				s = s + this.getAttribute(i);
		}
		s = s + ")";
		System.out.println(s);

	} // end of getX()

	/**
	 * Returns the distance between two data points
	 * 
	 * @param dp1
	 *            the first data point
	 * @param dp2
	 *            the second data point
	 * @return the distance between the two data points (double)
	 */
	public static double distance(DataPoint dp1, DataPoint dp2) {

		double result = 0;

		for (int i = 0; i < dp1.Dimena; i++) {
			double x1 = dp1.getAttribute(i);
			double x2 = dp2.getAttribute(i);
			result = result + (x1 - x2) * (x1 - x2);
		}

		result = Math.sqrt(result);
		return result;

	} // end of distance()

	/**
	 * Returns the squared distance between two data points
	 * 
	 * @param dp1
	 *            the first data point
	 * @param dp2
	 *            the second data point
	 * @return the distance between the two data points
	 */
	public static double distanceSqrt(DataPoint dp1, DataPoint dp2) {

		double result = 0;

		for (int i = 0; i < dp1.Dimena; i++) {
			double x1 = dp1.getAttribute(i);
			double x2 = dp2.getAttribute(i);
			result = result + (x1 - x2) * (x1 - x2);
		}
		return result;

	} // end of distance()

	/**
	 * Returns a string representation of this DataPoint
	 * 
	 * @return a string representation of this data point
	 */
	public String toString() {

		String s = "(";

		for (int i = 0; i < this.Dimena; i++) {
			if (i < this.Dimena - 1)
				s = s + this.getAttribute(i) + ",";
			if (i == this.Dimena - 1)
				s = s + this.getAttribute(i);
		}

		return s + ")[" + this.clusterNumber + "]";

	} // end of toString()

	/**
	 * Main method -- to test the DataPoint class
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		// DataPoint dp1 = new DataPoint(-3.0,-4.0);
		// DataPoint dp2 = new DataPoint(0.0,4.0);

		double[] a1 = new double[3];
		double[] a2 = new double[3];
		a1[0] = 2.;
		a1[1] = 3.;
		a1[2] = 1.;

		a2[0] = 13.;
		a2[1] = 14.;
		a2[2] = 17.;

		DataPoint dp1 = new DataPoint(a1, 3);
		DataPoint dp2 = new DataPoint(a2, 3);

		System.out.println(DataPoint.distance(dp1, dp2));
		// System.out.println(dp1.getAttribute(0) );
		// System.out.println(dp2.getAttribute(0) );
		dp1.showAttributes();
		dp2.showAttributes();

		dp1.assignToCluster(7);
		// System.out.println(dp1.getClusterNumber());
		dp1.assignToCluster(17);
		// System.out.println(dp1.getClusterNumber());
		// System.out.println(dp2.getClusterNumber());
		// System.out.println(dp1);

	} // end of main()

} // end of class
