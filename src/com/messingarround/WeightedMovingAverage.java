package com.messingarround;


//import org.apache.log4j.Logger;


public class WeightedMovingAverage extends SimpleMovingAverage {

	private static final long serialVersionUID = -1710408609792814518L;
	//private static Logger log = Logger.getLogger(WeightedMovingAverage.class);

	public WeightedMovingAverage(int capacity) {
		super(capacity);
	}

	@Override
	public double value() {
		
		Double[] values = toArray(new Double[size()]);

		double sumOfValues = 0.0;
		double sumOfWeights = 0.0;

		for (int i = 0; i < values.length; i++) {
			double weight = i + 1;
			sumOfValues += weight * values[i];
			sumOfWeights += weight;
		}

		return (sumOfValues / sumOfWeights);
	}

	private int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	private Double[] toArray(Double[] doubles) {
		// TODO Auto-generated method stub
		return null;
	}
}
