package com.multistage.correlations.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.algo.test.FileIO;
import com.multistage.correlations.gui.models.MarketDataModel;
import com.multistage.correlations.gui.models.MyTableModel;

/* ListDemo.java requires no other files. */
public class ListDemo extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String hireString = "Hire";
    private static final String loadString = "load";
    private JButton loadButton;
    private JTextField employeeName;
	private HashMap map = new HashMap();
	private String symbols = "";
	public JTextArea textArea = null;
	private JTextField[] textFields;
	private JLabel[] labels;
	private JLabel action;
	private JTable table;
	public MyTableModel  xmodel = new MyTableModel();
    public ListDemo() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton hireButton = new JButton(hireString);
        HireListener hireListener = new HireListener(hireButton);
        hireButton.setActionCommand(hireString);
        hireButton.addActionListener(hireListener);
        hireButton.setEnabled(false);

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new loadListener());

        employeeName = new JTextField(10);
        employeeName.addActionListener(hireListener);
        employeeName.getDocument().addDocumentListener(hireListener);
        String name;
        if(listModel.size()>0){
        name = listModel.getElementAt(
                              list.getSelectedIndex()).toString();

        }
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                                           BoxLayout.LINE_AXIS));
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(employeeName);
        buttonPane.add(hireButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    class loadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
        	
            int index = list.getSelectedIndex();
            
            if(index==-1){
            	index=0;
            }
            //System.out.print(map.get(list.getModel().getElementAt(index)));
            Object RAW = map.get(list.getModel().getElementAt(index));
            
            if(textArea!=null){

            	if(checkDimOk()){
            	
            	String[] rows = RAW.toString().split("\n");
            	SetEnv.RAW=rows[2].replaceAll("symbols=","");
            	textArea.setText(SetEnv.RAW.toString());
            	String[] symbols=rows[2].replace("symbols=","").split(",");
            	SetEnv.MARKET=rows[0].replace("market=","");
            	SetEnv.MARKET+=" marketcap>="+SetEnv.MARKETCAP +" mio USD";
            	SetEnv.DESC=SetEnv.DESC+" "+rows[1];
            	
            	SetEnv.FILE=list.getSelectedValue().toString();
            	textFields[0].setText(rows[0]);
            	textFields[1].setText(rows[1].replaceAll("description=",""));
            	if(symbols!=null){
            	action.setText(symbols.length+" symbols");
            	}

            	
            	FileIO.createConfig();
            	
            	}else{
            		
            	}
            }
            //listModel.remove(index);
            

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                loadButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class HireListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = employeeName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                employeeName.requestFocusInWindow();
                employeeName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(employeeName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            employeeName.requestFocusInWindow();
            employeeName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection, disable load button.
                loadButton.setEnabled(false);

            } else {
            //Selection, enable the load button.
                loadButton.setEnabled(true);
            }
        }
    }

    public boolean checkDimOk() {
    	Object[][] l =  SetEnv.names;
    	
    	for(Object[] o:l){
	    	//for(Object o1:o){
	    		
	      	  if(o[1].equals(new Boolean(true))){
	      		SetEnv.maxdim++;
	      		System.out.println(">>>>"+o[0]);
	      	  }	
	      	  
	    
	      	  
	    	//}
    	}  	
    	
    	if(SetEnv.maxdim>2)
	      		  return true;
    	
    	return false;
	}

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListDemo();
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	public void setMap(HashMap map, JTextArea textArea, JTextField[] textFields, JLabel[] labels, JLabel actionLabel) {
		this.map=map;
		this.textArea =textArea;
		this.textFields = textFields;
		 this.labels= labels;
		 this.table= table;
		 this.action=actionLabel;
		
		int index = 0;
		for(Object o :map.keySet().toArray()){
			listModel.add(index,o.toString());
			index++;
		}
	}
}
