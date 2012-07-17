package com.messingarround;



public class HighLowFinder {

	private int countSinceLastHigh = 0;
	private double currentHigh = Double.MIN_VALUE;
	
	public void add(CandleDataPoint candle) {
		
		if(candle.getSellHigh() > currentHigh) {
			currentHigh = candle.getSellHigh();
			countSinceLastHigh = 0;
		}

		countSinceLastHigh ++;
		
		if(countSinceLastHigh > 21) {
			System.out.println(candle.getDate() + "\t" + candle.evaluate(CandleValueModel.Typical) + "\t" + countSinceLastHigh);
			countSinceLastHigh = 0;
			currentHigh = Double.MIN_VALUE;
		}
	}
}
