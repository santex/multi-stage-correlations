package com.multistage.correlations.gui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.multistage.correlations.gui.models.CheckListRenderer;
import com.multistage.correlations.gui.models.CheckableItem;
import com.multistage.correlations.utils.BaseUtil;

/**
 * @version 1.0 04/26/99
 */
public class FileUpload extends JFrame {

	 private static int flag = 0;  
	 public int indentLevel = -1;
	 static String userhome = System.getProperty("user.home");


	  public String[] listPath(File path) {
	      File files[]; 
	      String[] ret;
	      ArrayList retList = new ArrayList();
	      indentLevel++; 

	      files = path.listFiles();

	      Arrays.sort(files);
	      for (int i = 0, n = files.length; i < n; i++) {
	        for (int indent = 0; indent < indentLevel; indent++) {
	          System.out.print("  ");
	        }

	        if(files[i].getName().contains(".msc")){
	        System.out.println(files[i].toString());
	        retList.add(files[i].getName().replaceAll(".msc",""));
	        }
	        
	        if (files[i].isDirectory()) {

	       
	          listPath(files[i]);
	        }
	      }
	      ret=new String[retList.size()];
	      for (int i = 0, n = retList.size(); i < n; i++) {
	    	  ret[i]= retList.get(i).toString();
	      }
	      indentLevel--; 
	      
	      return ret;
	  	}

  public FileUpload() {

    String[] strs = {};
    
    
    strs = listPath(new File(userhome+"/mscorrelation/user/"));

    final JList list = new JList(createData(strs));

    list.setCellRenderer(new CheckListRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setBorder(new EmptyBorder(0, 4, 0, 0));
    list.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int index = list.locationToIndex(e.getPoint());
        
        //if(flag != index){
	        
	        CheckableItem item = (CheckableItem) list.getModel()
	            .getElementAt(index);
	        item.setSelected(!item.isSelected());
	        Rectangle rect = list.getCellBounds(index, index);
	        list.repaint(rect);
	        if(item.isSelected()){
	        BaseUtil.changeSymbols(item.toString());
	        }
        }
      //}
    });
    
    
    
    JScrollPane sp = new JScrollPane(list);

    final JTextArea textArea = new JTextArea(3, 10);
    JScrollPane textPanel = new JScrollPane(textArea);
    JButton printButton = new JButton("import");
    printButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ListModel model = list.getModel();
        int n = model.getSize();
        for (int i = 0; i < n; i++) {
          CheckableItem item = (CheckableItem) model.getElementAt(i);
          if (item.isSelected()) {
        	  
	        item.setSelected(!item.isSelected());
	        
            textArea.append(item.toString());
            textArea.append(System.getProperty("line.separator"));
            System.out.println(item.toString());
            
           // return;
          }else{
        	  //item.setSelected(true);
          }
        }
      }
    });
    JButton clearButton = new JButton("load");
    clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textArea.setText("");
      }
    });
    
    JButton changeButton = new JButton("change");
    clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textArea.setText("");
      }
    });
    
    
    

    
    
    JPanel panel = new JPanel(new GridLayout(1, 3));
    
    panel.add(printButton);
    panel.add(clearButton);
    panel.add(changeButton);
    
    
    getContentPane().add(panel, BorderLayout.NORTH);
    getContentPane().add(sp, BorderLayout.CENTER);
    getContentPane().add(textPanel, BorderLayout.PAGE_END);
  }

  private CheckableItem[] createData(String[] strs) {
    int n = strs.length;
    CheckableItem[] items = new CheckableItem[n];
    for (int i = 0; i < n; i++) {
      items[i] = new CheckableItem(strs[i]);
    }
    return items;
  }


  public static void main(String args[]) {
    try {
    	UIManager.setLookAndFeel(UIManager
				.getCrossPlatformLookAndFeelClassName());

    } catch (Exception evt) {}
  
    FileUpload frame = new FileUpload();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    

	frame.setVisible(true);
  }

}

           
         