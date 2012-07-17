package com.messingarround;

import static java.lang.Math.abs;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class CandleAnalyser 
{
	
}	/*
	public static void main(String[] args) {
		try {
		    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
		    CandleAnalyser app = (CandleAnalyser)ctx.getBean("candleAnalyser");
		    app.run();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }
	
	public void run() throws Exception {
		
		CandleCollection candles = dm.loadCandles(new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 04, 28), Period.FifteenMin)).getCandles();
		
		Map<String,Integer> counts = new HashMap<String, Integer>();
		
//		CandleDataPoint[] candleArray = candles.toArray(new CandleDataPoint[candles.size()]);
//		for(int i=1; i < candleArray.length; i++) {
//			
//			String key = generateFingerprint(candleArray[i-1]) + generateFingerprint(candleArray[i]);
//			
//			if(counts.get(key) == null)
//			{
//				counts.put(key, 1);
//			}
//			else
//			{
//				counts.put(key, counts.get(key) + 1);
//			}
//		}
		
		StringBuffer buf = new StringBuffer();
		for(String key : counts.keySet())
		{
			buf.append(key.substring(0,1) + "\t" + key.substring(1,4) + "\t" + key.substring(4,5) + "\t" + key.substring(5) + "\t" + counts.get(key) + "\n");
		}
		
		try
		{
			FileOutputStream fos = new FileOutputStream(new File("/mnt/sata1500b/dev/forex/candleAnalysis/analysis.csv"));
			fos.write(buf.toString().getBytes());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private String generateFingerprint(CandleDataPoint candle)
	{
		CandleType type = (candle.getBuyClose() > candle.getBuyOpen()) ? CandleType.Bull : CandleType.Bear;
		double head = (candle.getBuyHigh() - max(candle.getBuyOpen(), candle.getBuyClose())) * 10000.0;
		double body = abs(candle.getBuyClose() - candle.getBuyOpen()) * 10000.0;
		double tail = (min(candle.getBuyOpen(), candle.getBuyClose()) - candle.getBuyLow()) * 10000.0;
		
		return ((type==CandleType.Bear)?"R":"L") + encodeHeight(head) + encodeHeight(body) + encodeHeight(tail);
	}
	
	private String encodeHeight(double size)
	{
		if(size <= 1.0) return "A";
		if(size <= 4.0) return "B";
		if(size <= 9.0) return "C";
		if(size <= 20.0) return "D";
		if(size <= 40.0) return "E";
		return "F";
	}
}

enum CandleType
{
	Bull, Bear
}*/