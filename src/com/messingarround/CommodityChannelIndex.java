package com.messingarround;


public class CommodityChannelIndex extends FixedSizeNumberQueue implements Indicator {

	private static final long serialVersionUID = -3438030867529382721L;

	private CandleValueModel valueModel = CandleValueModel.Typical;

	public CommodityChannelIndex(int capacity) {
		super(capacity);
	}

	public void add(CandleDataPoint data) {
		CandleDataPoint value = data.evaluate(valueModel);
		add(value);
	}


	@Override
	public double value() {
		
		double avg = average();
		
		Double[] values = toArray(new Double[size()]);
		
		double md = 0.0;
		for(int i=0; i<values.length; i++) {
			md += Math.abs(avg - values[i]);
		}
		md /= size();
		
		double val = (values[values.length-1] - avg) / (0.015 * md);
		return val;
	}

	private int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	private Double[] toArray(Double[] doubles) {
		// TODO Auto-generated method stub
		return null;
	}

	private double average() {
		// TODO Auto-generated method stub
		return 0;
	}

	public CandleValueModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(CandleValueModel valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public String toString() {
		return String.format("CCI(%d) = %.6f", capacity(), value());
	}
}
