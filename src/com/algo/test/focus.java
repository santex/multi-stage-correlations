package com.algo.test;
import java.awt.Dimension;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.multistage.correlations.gui.models.MarketDataModel;



public class focus extends JFrame implements KeyListener {

	
    public HashMap dataFinance = new HashMap();

	public focus() {
        JDesktopPane desktop = new JDesktopPane();
        setContentPane(desktop);

		try {

	        URLConnectionReader urlConn = new URLConnectionReader();
			urlConn.prepareAll(null);
			dataFinance = urlConn.exec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        ClusterImplement cluster = new ClusterImplement(dataFinance);
        JScrollPane xf = cluster.prepareMain();
        
        JTable jt = new JTable(new MarketDataModel(5));
        JScrollPane jsp = new JScrollPane(jt);

        initUI();
        addWindow(this, jsp,"Update");        
        addWindow(this, xf,"All Performance Cluster and there symbols");
//        addWindow(this,addPane(cluster.getInformation()),"Performance Cluster");
        
	     
	    // addPane(this, "two");
        //addPane(this, "three");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(330, 250);

        addKeyListener(this);
    }
    

    public final void initUI() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        JMenuItem foi = new JMenuItem("About");
        file.add(foi);

        JMenuItem about = new JMenuItem("About");
        help.add(about);

        about.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                AboutDialog ad = new AboutDialog();
                ad.setVisible(true);
            }
        });

        menubar.add(file);
        menubar.add(help);
        setJMenuBar(menubar);

        setTitle("Simple Dialog");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    
    private Scatter addPane(Information information) {
        

  	  
    	ArrayList l = new ArrayList();
    	
    	for(Object o :dataFinance.keySet().toArray())
    	{
    		l.add(o.toString());
    	}
         Scatter content = new Scatter(l, dataFinance,information);

        content.setSize(600, 580);
        content.setLocation(300,300);
        return content;
		
	}

	public static void main(String[] args) {
        focus t = new focus();
    }

    private void addPane(JFrame frame, String name) {
        JTextArea textArea = new JTextArea();
        textArea.setName(name);
        textArea.setEditable(false);

        addWindow(frame, textArea, name);
    }

    private JInternalFrame addWindow(JFrame frame, JComponent component,
            String name) {
        JScrollPane scrollablePane = new JScrollPane(component);

        JInternalFrame iframe = new JInternalFrame(name + " ", true, true,
                true, true);

        iframe.setSize(300, 280);
        iframe.setLocation((int) (300 * 0),
                (int) (100 * 0));
        iframe.setVisible(true);
        iframe.getContentPane().add(scrollablePane);
        frame.getContentPane().add(iframe);

        return iframe;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.err.println(e.getKeyChar());
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
	  // This inner class avoids a really obscure race condition.
	  // See http://java.sun.com/developer/JDCTechTips/2003/tt1208.html#1
	  private static class FrameShower implements Runnable {

	    private final Frame frame;

	    FrameShower(Frame frame) {
	      this.frame = frame;
	    }

	    public void run() {
	     frame.setVisible(true);
	    }

	  }
	  
}
