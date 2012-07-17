package com.messingarround;


public class SimpleMovingAverage extends FixedSizeNumberQueue implements Indicator {

	private static final long serialVersionUID = -3438030867529382721L;

	private CandleValueModel valueModel = CandleValueModel.AverageOfOHLC;

	public SimpleMovingAverage(int capacity) {
		super(capacity);
	}

	public void add(CandleDataPoint data) {
		add(data.evaluate(valueModel));
	}

	@Override
	public double value() {
		return isFull() ? super.value() : Double.NaN;
	}

	private boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public CandleValueModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(CandleValueModel valueModel) {
		this.valueModel = valueModel;
	}
}
