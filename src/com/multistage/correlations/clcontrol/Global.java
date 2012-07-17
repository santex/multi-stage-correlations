package com.multistage.correlations.clcontrol;

import java.text.*;

import javax.swing.table.*;

import com.multistage.correlations.cluster.*;
import com.multistage.correlations.gui.SetEnv;
import com.multistage.correlations.utils.*;


/**
 * Main global parameters for clustering
 * 
 * @author H.Geissler 18 Aug 2006
 * 
 */
public class Global {

	// file with the settings
	public static String CPUtime;

	public static double Compactness;
	
	public static boolean doneAnalysis=false;

	public static TableResults jInfo;

	public static TableOptions jShowOption;
	
	public static TableOptions jFinanceOption;

	public static TableSettings jSetting;

	public static String Description;

	public static String summary;

	public static MyTableModel model;

	public static MyTableModel model2;

	public static int Ncluster;

	public static int Niterations;

	public static double Fuzzines;

	public static double Eps;

	public static boolean ShowSeeds;

	public static boolean ShowCenters;
	
	public static boolean UseOne;

	public static boolean UseTwo;

	public static double ShowProbab;

	public static double[][] mem = null;

	public static DataPoint MinData;

	public static DataPoint MaxData;

	public static int DimData;

	public static DataHolder Data; // original data

	public static DataHolder CC; // centers

	public static DataHolder SS; // seeds

	public static int SizeData;

	public static double PartitionCoefficient;

	public static int[] nPoints; // number of points in cluster

	public static String[] matrixTitles;

	public static String matrixName;


	public static void init() {

		Description = "summary is not filled yet";
		CPUtime = "";
		Compactness = 0;
		Fuzzines = 1.7;
		Niterations = 200;
		Ncluster = 30;
		Eps = 0.001;
		ShowSeeds = false;
		ShowCenters = true;
		
		UseOne = false;
		UseTwo = true;
		
		PartitionCoefficient = 0;
		ShowProbab = 0.68;
		doneAnalysis=false;
		
		jShowOption = new TableOptions();
		jShowOption.setOpaque(true); // content panes must be opaque
		
		jFinanceOption = new TableOptions();
		jFinanceOption.setOpaque(true); // content panes must be opaque
		

		jSetting = new TableSettings();
		jSetting.setOpaque(true); // content panes must be opaque

		jInfo = new TableResults();
		jInfo.setOpaque(true); // content panes must be opaque

		SetOptions();
		SetFinance();
	}

	/*
	 * // load the data for private use public static void LoadData() {
	 * 
	 * Data=SetEnv.DATA; MinData=Data.GetMin(); MaxData=Data.GetMax();
	 * SizeData=Data.GetSize(); DimData=Data.GetDimention();
	 * CC=SetEnv.ClusCenter; // empty
	 *  }
	 */

	private static void SetFinance() {

		AbstractTableModel s = (AbstractTableModel) jFinanceOption.table
				.getModel();

		s.setValueAt(new String("Use 1"), 1, 1);
		s.setValueAt(new Boolean(UseOne), 1, 1);
		s.setValueAt(new String("Use 2"), 1, 1);
		s.setValueAt(new Boolean(UseTwo), 1, 1);
		
	}

	public static void SetOptions() {

		AbstractTableModel s = (AbstractTableModel) jShowOption.table
				.getModel();

		s.setValueAt(new String("Show Centers"), 0, 0);
		s.setValueAt(new Boolean(ShowCenters), 0, 1);
		s.setValueAt(new String("Show Seeds"), 1, 0);
		s.setValueAt(new Boolean(ShowSeeds), 1, 1);

	}

	public static void GetFinance() {

		AbstractTableModel s = (AbstractTableModel) jFinanceOption.table
				.getModel();

		Object s1 = s.getValueAt(0, 1);
		String h1 = s1.toString();
		UseOne = false;
		if (h1.equals("true"))
			UseOne = true;

		s1 = s.getValueAt(1, 1);
		h1 = s1.toString();
		UseTwo = false;
		if (h1.equals("true"))
			UseTwo = true;

		// / boolean sss=s1.booleanValue();
		// System.out.println( "Get Seeds="+ h1 );
		// System.out.println( "Get Centers="+ h2 );

	}
	public static void GetOptions() {

		AbstractTableModel s = (AbstractTableModel) jShowOption.table
				.getModel();

		Object s1 = s.getValueAt(0, 1);
		String h1 = s1.toString();
		ShowCenters = false;
		if (h1.equals("true"))
			ShowCenters = true;

		s1 = s.getValueAt(1, 1);
		h1 = s1.toString();
		ShowSeeds = false;
		if (h1.equals("true"))
			ShowSeeds = true;

		// / boolean sss=s1.booleanValue();
		// System.out.println( "Get Seeds="+ h1 );
		// System.out.println( "Get Centers="+ h2 );

	}

	// set settings to the table
	public static void SetSettings(int N1, double N2, int N3, double N4) {

		jSetting.table.setValueAt(new Integer(N1), 0, 1);
		jSetting.table.setValueAt(new Double(N2), 1, 1);
		jSetting.table.setValueAt(new Integer(N3), 2, 1);
		jSetting.table.setValueAt(new Double(N4), 3, 1);
	}

	public static void GetSettings() {

		// int hmax=jSetting.table.getRowCount();

		Object tmp1 = jSetting.table.getValueAt(0, 1);
		Object tmp2 = jSetting.table.getValueAt(1, 1);
		Object tmp3 = jSetting.table.getValueAt(2, 1);
		Object tmp4 = jSetting.table.getValueAt(3, 1);
		Object tmp5 = jSetting.table.getValueAt(4, 1);

		String s1 = "1";
		String s2 = "1";
		String s3 = "1";
		String s4 = "1";
		String s5 = "0.68";
		if (tmp1 != null)
			s1 = tmp1.toString();
		if (tmp2 != null)
			s2 = tmp2.toString();
		if (tmp3 != null)
			s3 = tmp3.toString();
		if (tmp4 != null)
			s4 = tmp4.toString();
		if (tmp5 != null)
			s5 = tmp5.toString();
		// System.out.println( "Get Clusters="+ s1 );
		// System.out.println( "Get Interations="+ s2 );

		try {
			Ncluster = Integer.parseInt(s1.trim());
		} catch (NumberFormatException e) {
		}

		try {
			Fuzzines = Double.parseDouble(s2.trim());
		} catch (NumberFormatException e) {
		}

		try {
			Niterations = Integer.parseInt(s3.trim());
		} catch (NumberFormatException e) {
		}

		try {
			Eps = Double.parseDouble(s4.trim());
		} catch (NumberFormatException e) {
		}

		try {
			ShowProbab = Double.parseDouble(s5.trim());
		} catch (NumberFormatException e) {
		}

		// protection
		if (Ncluster <= 0)
			Ncluster = 2;
		if (Fuzzines <= 1)
			Fuzzines = 1.1;
		if (Niterations <= 0)
			Niterations = 1;
		if (Eps <= 0)
			Eps = 0.0000001;
		if (ShowProbab <= 0)
			ShowProbab = 0.0;
		if (ShowProbab >= 1)
			ShowProbab = 1.0;
	}

	public static void SetInfoTable() {

		NumberFormat formatter = new DecimalFormat("0.000E00");

		if (jInfo.model.getRowCount() > 0)
			jInfo.model.setRowCount(0);

		jInfo.model.addRow(new Object[] { new String("CPU"),
				new String(CPUtime) });

		jInfo.model.addRow(new Object[] { new String("No of clusters"),
				new String(Integer.toString(Ncluster)) });

		String ss = formatter.format(Compactness);
		jInfo.model.addRow(new Object[] { new String("Compactness"),
				new String(ss) });

		ss = formatter.format(PartitionCoefficient);
		jInfo.model.addRow(new Object[] { new String("Partition"),
				new String(ss) });

	} // end cluster

	/**
	 * @author H.Geissler generate summary
	 */

	public static String Summary() {

		String sum = Header();
		
		NumberFormat form1 = new DecimalFormat("0.00");
		NumberFormat FormInt = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		int d = 3;
		int w = 4;
		FormInt.setMaximumIntegerDigits(d);
		int dim = SetEnv.Dim;
		sum  += Footer(FormInt,dim,w,fp);

		return sum;

	}
	
	public static String Header() {
		
		String sum =  "\n<h3>########MATRIX########\n";
		
		
		//sum = sum + "\n" +SetEnv.title;
		sum = sum +"\n"+Description + "\n"+"matrix:"+SetEnv.matrix+"\n"+matrixName;
		sum = sum + "\n######################</h3>\n" + "CPU time=" + CPUtime;
		sum = sum + "\n" + "Data had dimension: " + SetEnv.Dim
				+ "  with number of rows: " + SetEnv.NRow;
		sum = sum + "\n" + "Number of final clusters="
				+ Integer.toString(Global.Ncluster);
		sum = sum + "\n" + "Compactness=" + Double.toString(Global.Compactness);
		sum = sum + "\n\n" + "Cluster-center and  Cluster symbols:";
		
		//NumberFormat form1 = new DecimalFormat("0.000E00");
		// NumberFormat form2 = new DecimalFormat("000");
		NumberFormat form1 = new DecimalFormat("0.00");
		NumberFormat FormInt = NumberFormat.getNumberInstance();
		FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
		int d = 3;
		int w = 4;
		FormInt.setMaximumIntegerDigits(d);

		int dim = SetEnv.Dim;
		
		if(CC!=null){
			if (Global.mem != null) {
				String symbols = getLocations(-1);
				
				sum+="\n\n\nTRANSITION DATA ID[-1] / MEMBERSHIP BASED\n";
				
				sum+="cluster:  -1 "+symbols;
				
				sum+="\n";
			}
			
			
		for (int j = 0; j < CC.getSize(); j++) {
			DataPoint dp = CC.getRaw(j);

			String symbols = getLocations(j); 
			
			String valString = FormInt.format(j, new StringBuffer(), fp)
					.toString();
			String s = "cluster:" +VEC.getSpaces(w - fp.getEndIndex()) + valString
					+ "  (";

			for (int i = 0; i < dim; i++) {
				String ss = form1.format(dp.getAttribute(i));
				if (i < dim - 1)
					s = s + ss + ", ";
				if (i == dim - 1)
					s = s + ss;
			}
			s = s + ")";
			sum = sum + "\n" + s +symbols;
		} // end centers
		
		//sum = sum + "\n" + "matrix-elements:"+SetEnv.title;
		
		//SetEnv.title += SetEnv.matrixTitles[SetEnv.matrix]+"\n";
//		sum = sum + "\n\n" + "matrix:"+SetEnv.title;
		
		
		
		
		}

		return sum;
	}

	private static String Footer(NumberFormat formInt, int dim, int w, FieldPosition fp) {
		String sum = "\n\n" + "All Symbols and there position Cluster assignment:";
		for (int j = 0; j < SetEnv.NRow; j++) {
			DataPoint dp = SetEnv.DATA.getRaw(j);

			String valString = formInt.format(j, new StringBuffer(), fp)
					.toString();

			String s = dp.getLabel()+"  " + VEC.getSpaces(w - fp.getEndIndex())
					+ valString + "  (";
			for (int i = 0; i < dim; i++) {
				String ss = formInt.format(dp.getAttribute(i));
				if (i < dim - 1)
					s = s + ss + ", ";
				if (i == dim - 1)
					s = s + ss;
			}
			s = s + ")   for  " + dp.getClusterNumber();

			sum = sum + "\n" + s;
		} // end centers

		if (Global.mem != null) {
			sum = sum + "\n\n" + "Fuzzy membership coefficients:";
			sum = sum
					+ VEC.getMatrix(SetEnv.NRow, Global.Ncluster, Global.mem,
							5, 5);
			// double x =dp.getAttribute( SetEnv.JboxX );
			// double y =dp.getAttribute( SetEnv.JboxY );
		}
		
		return sum;
	}

	public static String getLocations(int cluster) {
		String result = "";
		for (int j = 0; j < SetEnv.NRow; j++) {
			DataPoint dp = SetEnv.DATA.getRaw(j);
			
			if(dp.getClusterNumber()==cluster){
				result += " "+dp.getLabel();
			}
			
		}
		
		return result;
	}

	public static String getLocations(int i, int show) {
		String result = "";
		for (int j = 0; j < SetEnv.NRow; j++) {
			DataPoint dp = SetEnv.DATA.getRaw(j);
			
			if(dp.getClusterNumber()==i){
				if(show>0){
				result += " "+dp.getLabel()+"+"+(j-show);
				
				}
				show--;
			}
			
		}
		
		return result;
		
	}

}
