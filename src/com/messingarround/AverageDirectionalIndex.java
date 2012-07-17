package com.messingarround;



public class AverageDirectionalIndex implements Indicator {

	private int periods;
	private int pointCount;
	private double storedValue;
	private ExponentialMovingAverage posDM;
	private ExponentialMovingAverage negDM;
	private CandleDataPoint prevValue;
	private CandleDataPoint value;
	private AverageTrueRange atr;

	public AverageDirectionalIndex(int periods) {
		atr = new AverageTrueRange(periods);
		this.periods = periods;
		this.pointCount = 0;
	}

	public void add(CandleDataPoint newValue) {

		pointCount++;

		prevValue = value;
		value = newValue;

		atr.add(newValue);

		double upMove = value.getHigh() - prevValue.getHigh();
		double downMove = prevValue.getLow() - value.getLow();

		double positiveDirectionalMovement = 0.0;
		if (upMove > downMove && upMove > 0) {
			positiveDirectionalMovement = upMove;
		}
		posDM.add(positiveDirectionalMovement);

		double negativeDirectionalMovement = 0.0;
		if (downMove > upMove && downMove > 0) {
			negativeDirectionalMovement = upMove;
		}
		negDM.add(negativeDirectionalMovement);

		// no value unless there is a complete data set
		if (pointCount < periods) {
			storedValue = Double.NaN;
		}

		double atrValue = atr.value();
		double posDI = posDM.value() / atrValue;
		double negDI = negDM.value() / atrValue;

		double adx = Math.abs(100.0 * (posDI - negDI) / (posDI + negDI));

		storedValue = adx;
	}

	public double value() {
		return storedValue;
	}
}
