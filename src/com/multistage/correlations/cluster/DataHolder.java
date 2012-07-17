package com.multistage.correlations.cluster;

import java.io.*;
import java.util.*;

/**
 * Represents an abstraction for a data holder in many dimensional space. 
 * All cluster operations are done using this holder
 * 
 * @author H.Geissler
 */

public class DataHolder {

	/** Array of data points */
	private ArrayList<DataPoint> Points;

	/** Min value of the data */
	private DataPoint Min;

	/** Max value of the data */
	private DataPoint Max;

	/** Attributes */
	private String names[];

	/** Dimension of Attributes */
	private int Dim;

	/** Relation name */
	private String relation;
	
	/** initialisation is true */
	private static boolean first;
	
	

	// internal
	private double[] xmin;

	private double[] xmax;

	private int[] in_xmin;

	private int[] in_xmax;

	/**
	 * Creates a new empty instance of data holder
	 * 
	 */

	public DataHolder() {
		Points = new ArrayList<DataPoint>();
		Dim = 0;
		relation = "DataHolder";
		first=true;
	} // end of empty holder

	/**
	 * Creates a new empty instance of data point
	 * 
	 * @param dim
	 *            dimension
	 */

	public DataHolder(int dim) {
		Points = new ArrayList<DataPoint>();
		Dim = dim;
		relation = "DataHolder";
		first=true;
		names = new String[Dim];
		for (int j = 0; j < Dim; j++) 
			names[j]=Integer.toString(j+1);
		
	} // end of empty holder

	/**
	 * Creates a new empty instance of data point
	 * 
	 * @param name
	 *            Name of this data holder
	 */

	public DataHolder(String name) {
		Points = new ArrayList<DataPoint>();
		Dim = 0;
		first=true;
		relation = name;
	} // end of empty holder

	/**
	 * Creates data holder with random number Input:
	 * 
	 * @param xMin
	 *            min number of DataPoint
	 * @param xMax
	 *            max number of DataPoint
	 * @param Dim
	 *            integer dimension of DataPoint
	 * @param Size
	 *            number of rows
	 */
	public DataHolder(DataPoint xMin, DataPoint xMax, int Dim, int Size) {

		Points = new ArrayList<DataPoint>();
		this.Dim = Dim;
		relation = "Random";
		for (int m = 0; m < Dim; m++)
			this.names[m] = "random_" + Integer.toString(m+1);
		Min = xMin;
		Min = xMax;
		first=true;
        
		Random generator = new Random();
		double[] a = new double[Dim];

		for (int j = 0; j < Size; j++) { // loop over clusters
			for (int m = 0; m < Dim; m++) { // loop over dimensions
				a[m] = xMin.getAttribute(m) + generator.nextDouble()
						* (xMax.getAttribute(m) - xMin.getAttribute(m));

			}
			DataPoint Seed = new DataPoint(a, Dim);
			Points.add(Seed);
		}

	} // end holder with random numbers

	/**
	 * Fill an existing data holder with random numbers
	 * 
	 * @param xMin
	 *            Min DataPoint
	 * @param xMax
	 *            Max DataPoint
	 * @param Dim
	 *            Dimension of DataPoint
	 * @param Size
	 *            Number of rows
	 * 
	 * 
	 */
	public void fillRandom(DataPoint xMin, DataPoint xMax, int Dim, int Size) {

		Points.clear();
		this.Dim = Dim;
		relation = "Random";
		names = new String[Dim];
		for (int m = 0; m < Dim; m++)
			 names[m] = "random_" + String.valueOf(m+1);
		Min = xMin;
		Min = xMax;

		// System.out.println("Fire random generator"+Dim+" size="+Size);
		// xMin.showAttributes();
		// xMax.showAttributes();

		Random generator = new Random();
		double[] a = new double[Dim];

		for (int j = 0; j < Size; j++) { // loop over clusters
			for (int m = 0; m < Dim; m++) { // loop over dimensions
				a[m] = xMin.getAttribute(m) + generator.nextDouble()
						* (xMax.getAttribute(m) - xMin.getAttribute(m));
				// a[m] = generator.nextDouble();

				// double dd= xMax.getAttribute(m) - xMin.getAttribute(m);
				// a[m] = xMin.getAttribute(m) + generator.nextDouble();

				// System.out.println( xMin.getAttribute(m) );
			}
			DataPoint Seed = new DataPoint(a, Dim);
			this.Points.add(Seed);
		}

	} // end holder with random numbers

	/**
	 * Get all elements of the data holder in form of an ArrayList
	 * 
	 * @return ArrayList
	 */
	public ArrayList getArrayList() {
		return Points;

	} // end

	/**
	 * Get the data in form of Array
	 * 
	 * @return DataPoint[]
	 */
	public DataPoint[] getArray() {

		int nn = Points.size();
		DataPoint[] d = new DataPoint[nn];

		for (int m = 0; m < nn; m++) {
			DataPoint dp = getRaw(m);
			d[m] = dp;
		}

		return d;

	} // end

	/**
	 * Clear current data holder
	 * 
	 */
	public void clear() {
		Points.clear();
		relation = "";
		Dim = 0;
		names = null;
	} // end of DataPoint()

	
	
	/**
	 * Add a data point to the holder
	 * 
	 * @param p New point
	 */
	public void add(DataPoint p) {

		Points.add(p);
		Dim = p.getDimension();
		if (first) {
			first=false;
			names = new String[Dim];
			for (int j = 0; j < Dim; j++) 
				names[j]=Integer.toString(j+1);
			
		}
		
		
	} // end of DataPoint()

	/**
	 * Set relation (name) of the data holder
	 * 
	 * @param s
	 *            name to be set
	 */

	public void setRelation(String s) {
		this.relation = s;
	} // end of DataPoint()

	/**
	 * Add a new instance to data holder in form of array
	 * 
	 * @param xx
	 *            array
	 */
	public void add(double[] xx) {
		Dim = xx.length;
		double[] a = new double[xx.length];
		for (int i = 0; i < xx.length; i++) a[i] = xx[i];
		DataPoint dp = new DataPoint(a, Dim);
		Points.add(dp);
		
		if (first) {
			first=false;
			names = new String[Dim];
			for (int j = 0; j < Dim; j++) 
				names[j]=Integer.toString(j+1);
		}
		
		
	} // end of DataPoint()

	/**
	 * Reads the input data from the ARFF file and stores in the data holder
	 * 
	 */
	public void read(String file) {

		String[] tmpholder = new String[100];
		int nn = 0;

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = "";
			int Nlines = 0;
			while ((line = in.readLine()) != null) {

				line = line.trim();
				// System.out.println(line);

				// read attributes
				if (line.startsWith("@")) {
					String[] word = line.split(" ");

					word[0] = word[0].toLowerCase();
					// System.out.println(word[0]);
					if (word[0].equals("@relation")) {
						relation = word[1];
						continue;
					}

					if (word[0].equals("@attribute")) {
						if (nn < 100) {
							tmpholder[nn] = word[1];
							nn++;
						} else {
							System.out
									.println("cannot read more than 100 attributes!");
						}
						continue;

					}
				} // end reading attribues

				// data statement
				if (line.startsWith("@")) {

					String[] word = line.split(" ");
					word[0] = word[0].toLowerCase();
					if (word[0].equals("@data")) {
						this.Dim = nn;
						this.names = new String[nn];
						xmin = new double[nn];
						xmax = new double[nn];
						in_xmin = new int[nn];
						in_xmax = new int[nn];

						for (int i = 0; i < nn; i++)
							names[i] = tmpholder[i];

						if (this.Dim == 0) {
							System.out
									.println("You did not define @ATTRIBUTE statements");
							System.exit(0);
						} else {
							// System.out.println("File contains:" + this.Dim
							// + " attributes");
							continue;
						}
					}
				}

				// read data
				line = line.trim();
				if (!line.startsWith("#") && !line.startsWith("%")
						&& !line.startsWith("*") && !line.startsWith("@")) {

					StringTokenizer st = new StringTokenizer(line, " \t\n\r\f,");
					int ncount = st.countTokens(); // number of words
					if (ncount != this.Dim) {
						System.out
								.println("Wrong number of attributes at line="
										+ Nlines);
						System.exit(0);
					}

					double[] antrub = new double[ncount];

					int m = 0;
					while (st.hasMoreTokens()) { // make sure there is stuff
						// to get
						String tmp = st.nextToken();
						antrub[m] = Double.parseDouble(tmp);
						m++;
					}
					DataPoint dp = new DataPoint(antrub, ncount);
					this.Points.add(dp);

					// initiate min/max calculation
					if (Nlines == 0) {
						for (int j = 0; j < ncount; j++) {
							xmin[j] = antrub[j];
							xmax[j] = antrub[j];
						}
					}
					for (int j = 0; j < ncount; j++) {
						if (antrub[j] < xmin[j]) {
							xmin[j] = antrub[j];
							in_xmin[j] = Nlines;
						}
						if (antrub[j] > xmax[j]) {
							xmax[j] = antrub[j];
							in_xmax[j] = Nlines;
						}
					}

					Nlines++;

				} // end if
			} // end while

			this.Min = new DataPoint(xmin, this.Dim);
			this.Max = new DataPoint(xmax, this.Dim);
			in.close();

		} catch (Exception e) {
			System.out.println("Error in read of =" + file);
			System.err.println(e);
			System.exit(-1);

		}

	} // end of readData()

	/**
	 * Get the dimension of the data
	 * 
	 * @return dimension (int)
	 */
	public int getDimention() {
		return Dim;

	} // end

	/**
	 * Set the dimension of the data
	 * 
	 * @param Dim
	 *            dimension
	 */
	public void setDimention(int Dim) {
		this.Dim = Dim;

	} // end

	/**
	 * Analyse the data holder by calculating min and max. You can access these
	 * values by calling GetMin() anf GetMax()
	 * 
	 */
	public void analyseSet() {
		
		try{

		double[] antrub = new double[this.Dim];
		xmin = new double[this.Dim];
		xmax = new double[this.Dim];

		if (Points == null) {
			System.out.println("DataHolder not filled");
			System.exit(1);

		}

		DataPoint first = Points.get(0);

		for (int j = 0; j < Dim; j++) {
			antrub[j] = first.getAttribute(j);
			xmin[j] = antrub[j];
			xmax[j] = antrub[j];
		}

		for (int i = 1; i < Points.size(); i++) {
			DataPoint dp = Points.get(i);

			for (int j = 0; j < Dim; j++) {
				antrub[j] = dp.getAttribute(j);
				if (antrub[j] < xmin[j])
					xmin[j] = antrub[j];
				if (antrub[j] > xmax[j])
					xmax[j] = antrub[j];

			}
		}

		// System.out.println("Min and Max:");
		Min = new DataPoint(xmin, Dim);
		Max = new DataPoint(xmax, Dim);
		// Min.showAttributes();
		// Max.showAttributes();
		
		}catch(Exception e){
			
		}

	}

	/**
	 * Get Min values of the data holder
	 * 
	 * @return DataPoint
	 */
	public DataPoint getMin() {

		return Min;

	} // end

	/**
	 * Get Max values of the data
	 * 
	 * @return DataPoint
	 * 
	 */
	public DataPoint getMax() {

		return Max;

	} // end

	/**
	 * Get size of the data holder
	 * 
	 * @return Size of the data holder (int)
	 * 
	 */
	public int getSize() {
		
		return Points.size();

	} // end of assignToCluster()

	/**
	 * Get name of the data holder (i.e. relation)
	 * 
	 * @return name of the data holder
	 */
	public String getRelation() {

		return relation;

	} // end of

	/**
	 * Get name of the i-th attribute
	 * 
	 * @return Name of the attribute
	 */
	public String getName(int i) {

		if (i<getDimention()) return names[i];
  
		return null;
		
	} // end of


	/**
	 * Set the title for ith component
	 * @param i  Index of a component
	 * @param title New title
	 */
	
	public void setName(int i, String title ) {
		names = new String[getDimention() ];
		if (i<getDimention()) names[i]=title;

	} // end of
	
	
	
	/**
	 * Get array of elements at position n of the data holder
	 * 
	 * @return array with the data at a position n (double[])
	 */

	public double[] getElement(int n) {

		double[] ele = new double[this.Dim];

		int m = 0;
		Iterator i = this.Points.iterator();
		while (i.hasNext()) {
			DataPoint dp = (DataPoint) (i.next());
			ele[m] = dp.getAttribute(n);
			m++;
		}

		return ele;
	}

	/**
	 * Get DataPoint at the position n
	 * 
	 * @return DataPoint
	 */

	public DataPoint getRaw(int n) {

		DataPoint xx = null;

		if (n <Points.size()) {
			xx = Points.get(n);
		} else {
			System.out.println("Requested raw=" + n
					+ " but data have only the size=" + Points.size());
		}

		return xx;
	}

	/**
	 * Print all entries of the data holder
	 */

	public void print() {

		// System.out.println("Print elements of DataHolder:" + this.relation);
		// for (int i=0; i<this.Dim; i++) {
		// if (this.names[i] != null) System.out.println( this.names[i]+" ");
		// }

		if (Points != null) {
			Iterator i = Points.iterator();
			while (i.hasNext()) {
				DataPoint dp = (DataPoint) (i.next());
				dp.showAttributes();
			}
		}
	}

	/**
	 * Main method -- to test the DataPoint class
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		DataHolder d = new DataHolder();
		d.read("data/userOutFileDefault.msc");
		// d.Print();

		DataPoint dp = d.getRaw(3);
		dp.showAttributes();

		System.out.println("Min");
		DataPoint dmin = d.getMin();
		dmin.showAttributes();

		System.out.println("Max");
		DataPoint dmax = d.getMax();
		dmax.showAttributes();

		// make random hoder
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
		// random numbers
		DataHolder rand = new DataHolder(dp1, dp2, 3, 10);
		rand.print();

	} // end of main()

} // end of class
