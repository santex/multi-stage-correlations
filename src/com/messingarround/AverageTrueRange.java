package com.messingarround;


public class AverageTrueRange implements Indicator {

	private FixedSizeNumberQueue ranges;

	public AverageTrueRange(int periods) {
		ranges = new ExponentialMovingAverage(periods);
	}

	public void add(CandleDataPoint data) {
		double range = data.getClose() - data.getOpen();
		ranges.add(range);
	}

	public double value() {
		return ranges.value();
	}
}
