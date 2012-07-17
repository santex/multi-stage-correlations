package com.algo.test;

//Print membership probability for C-mean cluster algorithm

import com.multistage.correlations.algorithms.*;
import com.multistage.correlations.cluster.*;
import com.multistage.correlations.gui.*;
import com.multistage.correlations.utils.*;



public class FuzzyImplement {

	public static void main(String argv[]) {

		DataHolder data = new DataHolder("Example");
// fill some data 
		double[] a = new double[3];
		a[0] = 27.;
		a[1] = 14.;
		a[2] = 1.;
		data.add(new DataPoint(a));
		a[0] = 30.;
		a[1] = 15.;
		a[2] = 0.;
		data.add(new DataPoint(a));
		a[0] = 30.;
		a[1] = 13.;
		a[2] = 2.;
		data.add(new DataPoint(a));
		a[0] = 29.;
		a[1] = 17.;
		a[2] = 3.;
		data.add(new DataPoint(a));
		a[0] = 26.;
		a[1] = 21.;
		a[2] = 2.;
		data.add(new DataPoint(a));
		a[0] = 90.;
		a[1] = 50.;
		a[2] = 37.;
		data.add(new DataPoint(a));
		a[0] = 92.;
		a[1] = 61.;
		a[2] = 40.;
		data.add(new DataPoint(a));
		a[0] = 85.;
		a[1] = 62.;
		a[2] = 27.;
		data.add(new DataPoint(a));
		a[0] = 87.;
		a[1] = 64.;
		a[2] = 34.;
		data.add(new DataPoint(a));
		a[0] = 30.;
		a[1] = 190.;
		a[2] = 83.;
		data.add(new DataPoint(a));
		a[0] = 25.;
		a[1] = 187.;
		a[2] = 90.;
		data.add(new DataPoint(a));
		a[0] = 19.;
		a[1] = 201;
		a[2] = 89.;
		data.add(new DataPoint(a));
		a[0] = 21.;
		a[1] = 192.;
		a[2] = 97.;
		data.add(new DataPoint(a));

		System.out.println("Dimension=" + data.getDimention());
		System.out.println("No rows=" + data.getSize());

// check loaded data (print!)
		data.print();

                SetEnv.DATA=data;
                SetEnv.NRow=data.getSize();
                SetEnv.Dim=data.getDimention();  
                Fuzzy fuzzy = new Fuzzy();
                // set number of clusters
                fuzzy.setClusters(3); 
                fuzzy.run();
                fuzzy.runBest();
	} // end of main
}