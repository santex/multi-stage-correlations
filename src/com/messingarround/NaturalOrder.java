package com.messingarround;


public class NaturalOrder {

	private SimpleMovingAverage[] indicators;

	public NaturalOrder(SimpleMovingAverage[] smas) {
		this.indicators = smas;
	}

	public boolean isNaturalOrderRising() {

		for (int i = 0; i < indicators.length - 1; i++) {
			if (!(indicators[i].value() > indicators[i + 1].value())) {
				return false;
			}
		}

		return true;
	}

	public boolean isNaturalOrderFalling() {

		for (int i = 0; i < indicators.length - 1; i++) {
			if (!(indicators[i].value() < indicators[i + 1].value())) {
				return false;
			}
		}

		return true;
	}

	public void add(CandleDataPoint val) {
		for (Indicator indicator : indicators) {
			indicator.add(val);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (FixedSizeNumberQueue sma : indicators) {
			sb.append(String.format("%s(%d)=%.4f ", sma.getClass().getSimpleName(), sma.capacity(), sma.value()));
		}

		return sb.toString();
	}

	public static void main(String[] args) {

		NaturalOrder nos = new NaturalOrder(new SimpleMovingAverage[] { new SimpleMovingAverage(10), new SimpleMovingAverage(20), new SimpleMovingAverage(50) });
		NaturalOrder now = new NaturalOrder(new SimpleMovingAverage[] { new WeightedMovingAverage(10), new WeightedMovingAverage(20), new WeightedMovingAverage(50) });
		NaturalOrder noe = new NaturalOrder(new SimpleMovingAverage[] { new ExponentialMovingAverage(10), new ExponentialMovingAverage(20), new ExponentialMovingAverage(50) });

		for (int i = 0; i < 200; i++) {

			CandleDataPoint cdp = new CandleDataPoint(Math.random());
			nos.add(cdp);
			now.add(cdp);
			noe.add(cdp);

			String smaOrder = nos.isNaturalOrderRising() ? "up" : nos.isNaturalOrderFalling() ? "down" : "none";
			String wmaOrder = now.isNaturalOrderRising() ? "up" : now.isNaturalOrderFalling() ? "down" : "none";
			String emaOrder = noe.isNaturalOrderRising() ? "up" : now.isNaturalOrderFalling() ? "down" : "none";

			System.out.printf("===| %.4f %s/%s/%s %n", cdp.getBuyOpen(), smaOrder, wmaOrder, emaOrder);
			System.out.println(" " + nos);
			System.out.println(" " + now);
			System.out.println(" " + noe);
		}
	}
}
