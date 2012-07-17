package com.multistage.correlations.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class URLMonitorPanel extends JPanel implements URLPingTask.URLUpdate {

  public static HashMap monitor = new HashMap(); 
  public static URLMonitorPanel[] monitors;
  public static String[] symbols;
  
  Timer timer;

  TimerTask timerUp;

  URL url;

  URLPingTask task;

  JPanel status;
private static String[] row=null;

  public static JButton startButton, stopButton;

public static JLabel startLabel;

public static JLabel stopLabel;

  public URLMonitorPanel(String symbol, Timer t,String urli) throws MalformedURLException {
    setLayout(new BorderLayout());
    timer = t;
    this.url = new URL(urli);
    add(new JLabel(symbol), BorderLayout.CENTER);
    JPanel temp = new JPanel();
    status = new JPanel();
    status.setSize(20, 20);
    temp.add(status);
    
    	
    
    startLabel = new JLabel(SetEnv.getUpdate(symbol,"time"));
    stopLabel = new JLabel(SetEnv.getUpdate(symbol,"pct"));
    
    startButton = new JButton("");
    startButton.setEnabled(false);
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        makeTask();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
      }
    });
    stopButton = new JButton("");
    stopButton.setEnabled(true);
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        task.cancel();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
      }
    });
    

    temp.add(startLabel);
    temp.add(stopLabel);
    temp.add(startButton);
    temp.add(stopButton);
    add(temp, BorderLayout.EAST);
    makeTask();
    

    
    
  }
    
  

  public URLMonitorPanel() {
	// TODO Auto-generated constructor stub
}

private void makeTask() {
    task = new URLPingTask(url, this,startLabel,stopLabel);
    task.setLabels(startLabel,stopLabel);
    timer.schedule(task, 0L,60000L);
    
  }

public void isAlive(final boolean b) {
  SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      status.setBackground(b ? Color.GREEN : Color.RED);
      status.repaint();
    }
  });
}
  public static void setData(String[] irow) {
	 row=irow;
	 
  }

  public static JFrame getFrame() throws Exception {
    JFrame frame = new JFrame("URL Monitor");
    symbols = "COMP,OEX_I,MID,SPX_I,OOI".split(",");
    
    
    monitor = new HashMap();
    Container c = frame.getContentPane();
    c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
    Timer t = new Timer();
    
    monitors = new URLMonitorPanel[symbols.length];
    
    
    for (int i = 0; i < symbols.length; i++) {
      monitors[i]  =new URLMonitorPanel(symbols[i], t,"http://marketdata.nyse.com/JTic?app=QUOT&id="+symbols[i]+"&rf=JS&type=NYQUOTE");	
      monitor.put(symbols[i], null);
      c.add(monitors[i]);
    }
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
  //  frame.pack();
  //  frame.show();
    return frame;
  }
}

class URLPingTask extends TimerTask {

  public interface URLUpdate {
    public void isAlive(boolean b);
  }

  URL url;

  URLUpdate updater;
  
  String response = "";

  public URLPingTask(URL url) {
    this(url, null);
  }

  public void setLabels(JLabel startLabel, JLabel stopLabel) {

  }

public URLPingTask(URL url, URLUpdate uu) {
    this.url = url;
    updater = uu;
  }

  public URLPingTask(URL url2, URLMonitorPanel urlMonitorPanel,
		JLabel startLabel, JLabel c) {
	    this.url = url2;
	    updater = urlMonitorPanel;

}

public void run() {
    //if (System.currentTimeMillis() - scheduledExecutionTime() < 55000) {
      // Let the next task do it
      //return;
    //}
    try {
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
      huc.setConnectTimeout(1000);
      huc.setReadTimeout(1000);
      int code = huc.getResponseCode();
      
      System.out.println(code);
      
      
      byte data[] = new byte[1024*16];
      long total = 0;
		String str;
		StringBuilder buf=new StringBuilder();
      // downlod the file
      InputStream input = new BufferedInputStream((InputStream) huc.getContent());
      InputStreamReader tmp=new InputStreamReader(input);
		BufferedReader reader=new BufferedReader(tmp);
		boolean start = false;
		while ((str = reader.readLine()) != null) {
			//if(str.contains("NYSE MarkeTrac")){
			String[] row = str.replaceAll("na","0").replaceAll("\"","").split(",");
			int i=0;
			for(String s:row){
				
				System.out.println(i+" "+s);
				i++;
			}

			SetEnv.setUpdate(row[6],"time",row[10]);
			SetEnv.setUpdate(row[6],"pct",row[39]);
			start = true;
		//	}
			if(start){
				buf.append(str+"\n");
			}
		}

		System.out.println(buf.toString().replaceAll("na","0"));
		
      if (updater != null)
        updater.isAlive(true);
    } catch (Exception e) {
      if (updater != null)
        updater.isAlive(false);
    }
  }
}


           
         