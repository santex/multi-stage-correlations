package com.messingarround;


public class MACD implements Indicator {

	private ExponentialMovingAverage emaShort;
	private ExponentialMovingAverage emaLong;
	private int ema1;
	private int ema2;

	public MACD() {
		this(12, 26);
	}

	public MACD(int ema1, int ema2) {
		emaShort = new ExponentialMovingAverage(ema1);
		emaLong = new ExponentialMovingAverage(ema2);
		
		emaShort.setValueModel(CandleValueModel.SellOpen);
		emaLong.setValueModel(CandleValueModel.SellOpen);
		
		this.ema1 = ema1;
		this.ema2 = ema2;
	}

	public void add(CandleDataPoint val) {
		emaShort.add(val);
		emaLong.add(val);
	}

	public double value() {
		return emaShort.value() - emaLong.value();
	}
	
	@Override
	public String toString() {
		return String.format("MACD(%d,%d) = %.5f", ema1, ema2, value());
	}
}
