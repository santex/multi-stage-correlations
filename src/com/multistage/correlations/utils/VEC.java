package com.multistage.correlations.utils;

import java.text.*;

// Util class for vectors  
public class VEC {

	// -------------------------------------------------------------------
	// Little method for helping in output formating
	public static String getSpaces(int n) {

		StringBuffer sb = new StringBuffer(n);
		for (int i = 0; i < n; i++)
			sb.append(' ');
		return sb.toString();
	} // getSpaces

	// -------------------------------------------------------------------
	/**
	 * Method for printing a matrix <br>
	 * Based on ER Harold, "Java I/O", O'Reilly, around p. 473.
	 * 
	 * @param n1
	 *            row dimension of matrix
	 * @param n2
	 *            column dimension of matrix
	 * @param m
	 *            input matrix values
	 * @param d
	 *            display precision, number of decimal places
	 * @param w
	 *            display precision, total width of floating value
	 */
	public static void printMatrix(int n1, int n2, double[][] m, int d, int w) {
		// Some definitions for handling output formating
		NumberFormat myFormat = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		myFormat.setMaximumIntegerDigits(d);
		myFormat.setMaximumFractionDigits(d);
		myFormat.setMinimumFractionDigits(d);
		for (int i = 0; i < n1; i++) {
			// Print each row, elements separated by spaces
			for (int j = 0; j < n2; j++)
			// Following unfortunately doesn't format at all
			// System.out.print(m[i][j] + " ");
			{
				String valString = myFormat.format(m[i][j], new StringBuffer(),
						fp).toString();
				valString = getSpaces(w - fp.getEndIndex()) + valString;
				System.out.print(valString);
			}
			// Start a new line at the end of a row
			System.out.println();
		}
		// Leave a gap after the entire matrix
		System.out.println();
	} // printMatrix

	// -------------------------------------------------------------------
	/**
	 * Method for filling string with a matrix <br>
	 * Based on ER Harold, "Java I/O", O'Reilly, around p. 473.
	 * 
	 * @param n1
	 *            row dimension of matrix
	 * @param n2
	 *            column dimension of matrix
	 * @param m
	 *            input matrix values
	 * @param d
	 *            display precision, number of decimal places
	 * @param w
	 *            display precision, total width of floating value
	 */
	public static String getMatrix(int n1, int n2, double[][] m, int d, int w) {
		String s = "\n";
		// Some definitions for handling output formating
		NumberFormat myFormat = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		myFormat.setMaximumIntegerDigits(d);
		myFormat.setMaximumFractionDigits(d);
		myFormat.setMinimumFractionDigits(d);
		for (int i = 0; i < n1; i++) {
			// Print each row, elements separated by spaces
			for (int j = 0; j < n2; j++)
			// Following unfortunately doesn't format at all
			// System.out.print(m[i][j] + " ");
			{
				String valString = myFormat.format(m[i][j], new StringBuffer(),
						fp).toString();
				valString = getSpaces(w - fp.getEndIndex()) + valString;
				s = s + valString;
			}
			// Start a new line at the end of a row
			s = s + "\n";
		}
		// Leave a gap after the entire matrix
		s = s + "\n";
		return s;
	} // printMatrix

	/**
	 * Method for printing an integer matrix <br>
	 * Based on ER Harold, "Java I/O", O'Reilly, around p. 473.
	 * 
	 * @param n1
	 *            row dimension of matrix
	 * @param n2
	 *            column dimension of matrix
	 * @param m
	 *            input matrix values
	 * @param d
	 *            display precision, number of decimal places
	 * @param w
	 *            display precision, total width of floating value
	 */
	public static void printMatrix(int n1, int n2, int[][] m, int d, int w) {
		// Some definitions for handling output formating
		NumberFormat myFormat = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		myFormat.setMaximumIntegerDigits(d);
		for (int i = 0; i < n1; i++) {
			// Print each row, elements separated by spaces
			for (int j = 0; j < n2; j++) {
				String valString = myFormat.format(m[i][j], new StringBuffer(),
						fp).toString();
				// 4 character locations per integer number
				valString = getSpaces(w - fp.getEndIndex()) + valString;
				System.out.print(valString);
			}
			// Start a new line at the end of a row
			System.out.println();
		}
		// Leave a gap after the entire matrix
		System.out.println();
	} // printMatrix

	// -------------------------------------------------------------------
	/**
	 * Method printVect for printing a vector <br>
	 * Based on ER Harold, "Java I/O", O'Reilly, around p. 473.
	 * 
	 * @param m
	 *            input vector of length m.length
	 * @param d
	 *            display precision, number of decimal places
	 * @param w
	 *            display precision, total width of floating value
	 */
	public static void printVect(double[] m, int d, int w) {
		// Some definitions for handling output formating
		NumberFormat myFormat = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		myFormat.setMaximumIntegerDigits(d);
		myFormat.setMaximumFractionDigits(d);
		myFormat.setMinimumFractionDigits(d);
		int len = m.length;
		for (int i = 0; i < len; i++) {
			// Following would be nice, but doesn't format adequately
			// System.out.print(m[i] + " ");
			String valString = myFormat.format(m[i], new StringBuffer(), fp)
					.toString();
			valString = getSpaces(w - fp.getEndIndex()) + valString;
			System.out.print(valString);
		}
		// Start a new line at the row end
		System.out.println();
		// Leave a gap after the entire vector
		System.out.println();
	} // printVect

	// -------------------------------------------------------------------
	/**
	 * Method printVect for printing a vector <br>
	 * Based on ER Harold, "Java I/O", O'Reilly, around p. 473.
	 * 
	 * @param m
	 *            input vector of length m.length
	 * @param d
	 *            display precision, number of decimal places
	 * @param w
	 *            display precision, total width of floating value
	 */
	public static void printVect(int[] m, int d, int w) {
		// Some definitions for handling output formating
		NumberFormat myFormat = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		myFormat.setMaximumIntegerDigits(d);

		int len = m.length;
		for (int i = 0; i < len; i++) {
			// Following would be nice, but doesn't format adequately
			// System.out.print(m[i] + " ");
			String valString = myFormat.format(m[i], new StringBuffer(), fp)
					.toString();
			valString = getSpaces(w - fp.getEndIndex()) + valString;
			System.out.print(valString);
		}
		// Start a new line at the row end
		System.out.println();
		// Leave a gap after the entire vector
		System.out.println();
	} // printVect

	// -------------------------------------------------------------------
	/**
	 * Method for standardizing the input data
	 * <p>
	 * Note the formalas used (since these vary between implementations):<br>
	 * reduction: (vect - meanvect)/sqrt(nrow)*colstdev <br>
	 * colstdev: sum_cols ((vect - meanvect)^2/nrow) <br>
	 * if colstdev is close to 0, then set it to 1.
	 * 
	 * @param nrow
	 *            number of rows in input matrix
	 * @param ncol
	 *            number of columns in input matrix
	 * @param A
	 *            input matrix values
	 */
	public static double[][] Standardize(int nrow, int ncol, double[][] A) {
		double[] colmeans = new double[ncol];
		double[] colstdevs = new double[ncol];
		// Adat will contain the standardized data and will be returned
		double[][] Adat = new double[nrow][ncol];
		double[] tempcol = new double[nrow];
		double tot;

		// Determine means and standard deviations of variables/columns
		for (int j = 0; j < ncol; j++) {
			tot = 0.0;
			for (int i = 0; i < nrow; i++) {
				tempcol[i] = A[i][j];
				tot += tempcol[i];
			}

			// For this col, det mean
			colmeans[j] = tot / (double) nrow;
			for (int i = 0; i < nrow; i++) {
				colstdevs[j] += Math.pow(tempcol[i] - colmeans[j], 2.0);
			}
			colstdevs[j] = Math.sqrt(colstdevs[j] / ((double) nrow));
			if (colstdevs[j] < 0.0001) {
				colstdevs[j] = 1.0;
			}
		}

		// System.out.println(" Variable means:");
		// printVect(colmeans, 4, 8);
		// System.out.println(" Variable standard deviations:");
		// printVect(colstdevs, 4, 8);

		// Now ceter to zero mean, and reduce to unit standard deviation
		for (int j = 0; j < ncol; j++) {
			for (int i = 0; i < nrow; i++) {
				Adat[i][j] = (A[i][j] - colmeans[j])
						/ (Math.sqrt((double) nrow) * colstdevs[j]);
			}
		}
		return Adat;
	} // Standardize

	// -------------------------------------------------------------------

	// get data info
	// output: [ncol][0] - means
	// [ncol][1] - standard deviations
	// [ncol][2,3] -Min and Max

	public static double[][] GetSummaryData(int nrow, int ncol, double[][] A) {

		double[] tempcol = new double[nrow];
		double[][] summary = new double[ncol][4];
		double tot;

		// Determine means and standard deviations of variables/columns
		for (int j = 0; j < ncol; j++) {
			tot = 0.0;
			for (int i = 0; i < nrow; i++) {
				tempcol[i] = A[i][j];
				tot += tempcol[i];
				int itmp = 0;
				itmp = ArrayOps.findLargest(tempcol);
				summary[j][2] = tempcol[itmp];
				itmp = ArrayOps.findSmallest(tempcol);
				summary[j][3] = tempcol[itmp];
			}

			// For this col, det mean
			summary[j][0] = tot / (double) nrow;
			for (int i = 0; i < nrow; i++) {
				summary[j][1] += Math.pow(tempcol[i] - summary[j][0], 2.0);
			}
			summary[j][1] = Math.sqrt(summary[j][1] / ((double) nrow));
			if (summary[j][1] < 0.0001) {
				summary[j][1] = 1.0;
			}
		}

		return summary;

	}

	// -------------------------------------------------------------------
	/**
	 * Method to sort a double vector, by inefficient straight insertion See
	 * section 8.1, p. 243, of Numerical Recipes in C. Two corrections, FM,
	 * 2003/11/14
	 * 
	 * @param invect
	 *            input data to be sorted, sorted in place
	 */
	public static void inSort(double[] invect) {
		double a;
		int i;

		// for (int j = 2; j < invect.length; j++) { CORRECTED!
		for (int j = 1; j < invect.length; j++) {
			a = invect[j];
			i = j - 1;
			// while (i > 0 && invect[i] > a) { CORRECTED!
			while (i >= 0 && invect[i] > a) {
				invect[i + 1] = invect[i];
				i--;
			}
			invect[i + 1] = a;
		}
		// Return type void
	} // inSort

}
