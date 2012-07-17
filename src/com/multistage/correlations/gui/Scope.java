
package com.multistage.correlations.gui;


import javax.swing.*;
import javax.swing.text.*;

import com.algo.test.FileIO;
import com.multistage.correlations.gui.models.MarketDataModel;
import com.multistage.correlations.utils.ResourceLoader;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events

import java.net.URL;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Scope extends JPanel
                             implements ActionListener {
    private String newline = "\n";
    protected static final String name = "Name";
    protected static final String password = "Authenticate";
    protected static final String raw = "date";
    protected static final String button = "date";
    static String userhome = System.getProperty("user.home");    
    Converter conv0;
    protected JLabel actionLabel;
    ListDemo list;
    
    
    
    public String getResult(String location){
    	java.net.URL helpURL =  ResourceLoader.getResourceURL(location);
    	  InputStreamReader isr;
	      BufferedReader br;
	      String line;
	      StringBuffer buff = new StringBuffer();
	      
	      try {
	    	  
	    	isr = new InputStreamReader(helpURL.openStream());
	    	br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				  
				  buff.append(line).append("\n");
				  
			    
			}
			br.close();
		    isr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		
		return buff.toString();
    	
    }
    public Scope() {
        setLayout(new BorderLayout());

        Dimension d = new Dimension(120,20);
    
        //Create a regular text field.
        JTextField textField = new JTextField(10);
        textField.setPreferredSize(d);
        textField.setMaximumSize(d);
        textField.setActionCommand(name);
        textField.addActionListener(this);
        
        //Create a password field.
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setPreferredSize(d);
        passwordField.setMaximumSize(d);
        passwordField.setActionCommand(password);
        passwordField.addActionListener(this);

        //Create a formatted text field.
        JFormattedTextField ftf = new JFormattedTextField(
                java.util.Calendar.getInstance().getTime());
        ftf.setPreferredSize(d);
        ftf.setMaximumSize(d);
        ftf.setActionCommand(name);
        ftf.addActionListener(this);

        //Create some labels for the fields.
        JLabel textFieldLabel = new JLabel(name + ": ");
        textFieldLabel.setLabelFor(textField);
        JLabel passwordFieldLabel = new JLabel(password + ": ");
        passwordFieldLabel.setLabelFor(passwordField);
        JLabel ftfLabel = new JLabel(raw + ": ");
        ftfLabel.setLabelFor(ftf);

        //Create a label to put messages during an action event.
        actionLabel = new JLabel("add symbols to new portfolio series");
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        //Lay out the text controls and the labels.
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        textControlsPane.setLayout(gridbag);

        JLabel[] labels = {textFieldLabel, passwordFieldLabel, ftfLabel};
        JTextField[] textFields = {textField, new JTextField(), ftf};
        addLabelTextRows(labels, textFields, gridbag, textControlsPane);

        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        
        textControlsPane.add(actionLabel, c);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Portfolio"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));

        
        
        
        //Create a text area.
       
        File xlist = new File(userhome+File.separator+"mscorrelation","data");
        
        HashMap map = new HashMap();
    	try {
			
	        for(File f:xlist.listFiles()){
	        	System.out.println(f.getName());	
	        	if(f.getName().contains("symbols")){
	        	  System.out.println(f.getAbsolutePath());
	        	  BufferedReader br;
			      br = FileIO.openFile(f.getAbsolutePath());
			
				  map.put(f.getName(),FileIO.readerToString(br));
				}
	        	
	        }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTextArea textArea = new JTextArea();
        list = new ListDemo();
        list.setMap(map,textArea,textFields,labels,actionLabel);

	    
	    Object[] all = new Object[map.size()];
       
        String allx="";
        int o=0;
        for(Object p:map.keySet().toArray()){
        	try {
        		
        		  
        		    // AWT Thread created - must exit
        		if(p.toString().contains(".msc")){
				System.out.println(p.toString());
        		}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	o++;
        }

        textArea.setText(SetEnv.RAW.toString());
     //  textArea.setFont(new Font("Mono", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 350));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("file contains"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));

        
        //Create an editor pane.
        JEditorPane editorPane = createEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setMinimumSize(new Dimension(10, 10));

        //Create a text pane.
        JTextPane textPane = createTextPane();
        JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        paneScrollPane.setMinimumSize(new Dimension(10, 10));

        //Put the editor pane and the text pane in a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              editorScrollPane,
                                              paneScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        
        JPanel rightPane = new JPanel(new GridLayout(5,2));
        rightPane.setPreferredSize(new Dimension(380, 750));
        //splitPane
        
        IndicatorSelect xindi = new IndicatorSelect();
        
        //Container xui = xindi.createAndShowGUI().getContentPane();
        //xindi.setMinimumSize(new Dimension(350, 400));
        //xindi.setPreferredSize(new Dimension(350, 400));
        //xui.setMinimumSize(new Dimension(50, 750));
        
        //rightPane.add(splitPane);
        //rightPane.setPreferredSize(new Dimension(550, 750));
        JPanel tPanel = new JPanel();
        
        rightPane.add(xindi);
        conv0 = new Converter();
        conv0.setI(0);
        Converter conv1 = new Converter();
        conv1.setI(1);
        conv1.render();
        conv0.render();
        
		JPanel lowerPanel = new JPanel();
		//lowerPanel.setPreferredSize(new Dimension(300, 350));

		
		JButton loadButton = new JButton();
		loadButton.setText("reset cap");
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				conv0.resetMaxValues(true);
			        
			}
		});
		conv0.mainPane.add(loadButton, java.awt.BorderLayout.EAST);
		lowerPanel.add(conv0.mainPane);
		
		rightPane.add(lowerPanel);
		rightPane.add(new Rule().createAndShowGUI());
        
/*
 * 
   

        
        

 * */
        //Put everything together.
        JPanel leftPane = new JPanel(new BorderLayout());
        
        leftPane.add(textControlsPane, 
        		BorderLayout.PAGE_START);
        leftPane.add(list,BorderLayout.LINE_END);
        leftPane.add(areaScrollPane,
                     BorderLayout.CENTER);

        add(leftPane, BorderLayout.LINE_START);
        add(rightPane, BorderLayout.LINE_END);
    }

    private void addLabelTextRows(JLabel[] labels,
                                  JTextField[] textFields,
                                  GridBagLayout gridbag,
                                  Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String prefix = "You typed \"";
        if (name.equals(e.getActionCommand())) {
            JTextField source = (JTextField)e.getSource();
            actionLabel.setText(prefix + source.getText() + "\"");
        } else if (password.equals(e.getActionCommand())) {
            JPasswordField source = (JPasswordField)e.getSource();
            actionLabel.setText(prefix + new String(source.getPassword())
                                + "\"");
        } else if (button.equals(e.getActionCommand())) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private JEditorPane createEditorPane() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        
        java.net.URL helpURL =  ResourceLoader.getResourceURL("com/multistage/correlations/resources/data/symbolsNYSE.msc");
        if (helpURL != null) {
            try {
            	System.err.println("Attempted to read a bad URL: " + helpURL);
                editorPane.setPage("");
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: TextSampleDemoHelp.html");
        }

        return editorPane;
    }

    private JTextPane createTextPane() {
        String[] initString = {};

        String[] initStyles =
                { "regular", "italic", "bold", "small", "large",
                  "regular", "button", "regular", "icon",
                  "regular"
                };

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument(doc);

        try {
            for (int i=0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i],
                                 doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }

        return textPane;
    }

    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        
        s = doc.addStyle("button", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        
        JButton bt = new JButton();
        bt.setText("BEEP");
        bt.setCursor(Cursor.getDefaultCursor());
        bt.setMargin(new Insets(0,0,0,0));
        bt.setActionCommand(button);
        bt.addActionListener(this);
        StyleConstants.setComponent(s, bt);
    }


  
}
