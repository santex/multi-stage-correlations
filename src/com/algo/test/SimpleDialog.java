package com.algo.test;

import java.awt.Dimension;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.multistage.correlations.gui.Parallelizer;
import com.multistage.correlations.gui.SetEnv;

class AboutDialog extends JDialog {

    public AboutDialog() {

        initUI();
    }

    public final void initUI() {

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));

        ImageIcon icon = new ImageIcon("/tmp/notes.png");
        JLabel label = new JLabel(icon);
        label.setAlignmentX(0.5f);
        add(label);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel name = new JLabel("Notes, 1.23");
        name.setFont(new Font("Serif", Font.BOLD, 13));
        name.setAlignmentX(0.5f);
        add(name);

        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        close.setAlignmentX(0.5f);
        add(close);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle(SetEnv.MARKET+"About Notes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
    }
}

public class SimpleDialog extends JFrame {

    public SimpleDialog() {

        initUI();
    }

    public final void initUI() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

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
    }
    
    

    public static void main(String[] args) {

    	SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                SimpleDialog sd = new SimpleDialog();
                sd.setVisible(true);
            }
        });
    	
    	long startTime = System.currentTimeMillis();
    	 for (int i=0; i<10; i++){
    		 System.out.println("Hello World " + i);
    	 }
    	 long endTime = System.currentTimeMillis();
    	 
         //System.out.println("done in"+(startTime-endTime));
    	
         startTime = System.currentTimeMillis();
    		  Parallelizer parallelizer = new Parallelizer();
    		  for (int i=0; i<10; i++){
    		       final int j = i;
    		      parallelizer.run(
    		          new Runnable(){

    		                  public void run() {
    		              System.out.println("Hello World " + j);
    		                  }
    		         }
    		      );
    		  }
    		  endTime = System.currentTimeMillis();
    		  System.out.println("done in"+(startTime-endTime));
    		  
    		  try {
				parallelizer.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		  System.out.println("done");
    		 
    }
}
