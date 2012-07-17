package com.multistage.correlations.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Rule extends JPanel
                             implements ActionListener {
	static String[] question = new String[0];
	
    

    JLabel picture;

    public Rule() {
        super(new BorderLayout());

        question =SetEnv.RULES;
        

      
        JPanel radioPanel = new JPanel(new GridLayout(question.length,3));
        int ident=0;
        Dimension d = new Dimension(400,300);
        radioPanel.setPreferredSize(d);
        for(String q:question){
        	  //Create the radio buttons.
            JRadioButton yes = new JRadioButton("yes");
            yes.setActionCommand("yes "+ident);
            

            JRadioButton  no = new JRadioButton("no");
            no.setActionCommand("no "+ident);
            no.setSelected(true);
            JLabel la = new JLabel(q);
            
            
            	
        	  //
        	  ButtonGroup group = new ButtonGroup();
        	  
              group.add(yes);
              group.add(no);
              yes.addActionListener(this);
              no.addActionListener(this);
              yes.setSelected(SetEnv.RUNOPTIONS[ident]);
              no.setSelected(!SetEnv.RUNOPTIONS[ident]);
              radioPanel.add(la);
              radioPanel.add(yes);
              radioPanel.add(no);
              
              ident++;
              
              
        }

        //Group the radio buttons.
      
        //Set up the picture label.
        
        //Put the radio buttons in a column in a panel.
        

        add(radioPanel, BorderLayout.CENTER);
        
    }

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {

    	String[] cmd=e.getActionCommand().split(" ");
    	
    	System.out.println(cmd[0]+" "+cmd[1]);
    	if(cmd[0].contains("yes")){
    		SetEnv.RUNOPTIONS[Integer.parseInt(cmd[1])]=true;
    		    		
    	}else{
    		SetEnv.RUNOPTIONS[Integer.parseInt(cmd[1])]=false;
    		
    	}
    	
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Rule.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @return 
     */
    public static JComponent createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Data Integrety");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Rule();
        return newContentPane;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              //  createAndShowGUI();
            }
        });
    }
}
