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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class URLMonitorPanelSet extends JPanel implements URLPingTaskSet.URLUpdate {

  
  
  Timer timer;

  URL url;

  URLPingTaskSet task;

  JPanel status;

  JButton startButton, stopButton;

  public URLMonitorPanelSet(String symbol, Timer t,String urli) throws MalformedURLException {
    setLayout(new BorderLayout());
    timer = t;
    this.url = new URL(urli);
    add(new JLabel(symbol), BorderLayout.CENTER);
    JPanel temp = new JPanel();
    status = new JPanel();
    status.setSize(20, 20);
    temp.add(status);
    startButton = new JButton("Start");
    startButton.setEnabled(false);
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        makeTask();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
      }
    });
    stopButton = new JButton("Stop");
    stopButton.setEnabled(true);
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        task.cancel();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
      }
    });
    temp.add(startButton);
    temp.add(stopButton);
    add(temp, BorderLayout.EAST);
    makeTask();
  }

  public URLMonitorPanelSet() {
	// TODO Auto-generated constructor stub
}

private void makeTask() {
    task = new URLPingTaskSet(url, this);
    timer.schedule(task, 0L, 180000L);
  }

  public void isAlive(final boolean b) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        status.setBackground(b ? Color.GREEN : Color.RED);
        status.repaint();
      }
    });
  }

  public static void main(String[] args) throws Exception {
    JFrame frame = new JFrame("URL Monitor");
    
    String[] symbols = "APAC,ARIA,AXP,BAS,BIDU,BPI,CAT,CHA,COP,DCM,DD,ELN,FCX,GLBC,GLNG,GMCR,GSVC,HDB,HK,JVA,KFT,KONA,MMM,MPEL,NVO,OXY,REDF,SLB,SODA,SUG,UTX,VHI,VRUS,ZAGG".split(",");
    //Container c = frame.getContentPane();
    //c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
    Timer t = new Timer();
    
    
    for (int i = 0; i < symbols.length; i++) {
    	new URLMonitorPanelSet(symbols[i], t,"http://marketdata.nyse.com/JTic?app=QUOT&id="+symbols[i]+"&rf=JS&type=NYQUOTE");
    }
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    //frame.pack();
    //frame.show();
    
    
  }
}

class URLPingTaskSet extends TimerTask {

  public interface URLUpdate {
    public void isAlive(boolean b);
  }

  URL url;

  URLUpdate updater;
  
  String response = "";

  public URLPingTaskSet(URL url) {
    this(url, null);
  }

  public URLPingTaskSet(URL url, URLUpdate uu) {
    this.url = url;
    updater = uu;
  }

  public void run() {
    if (System.currentTimeMillis() - scheduledExecutionTime() > 180000) {
      // Let the next task do it
      return;
    }
    try {
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
      huc.setConnectTimeout(2000);
      huc.setReadTimeout(2000);
      int code = huc.getResponseCode();
      
      //System.out.println();
      
      
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
			String[] row = str.split("\",\"");
				start = true;
		//	}
				
			System.out.println("="+row[6]+"="+row[14]);
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


           
         