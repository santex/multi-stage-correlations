package com.multistage.correlations.gui;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Infox extends JFrame{
 JLabel label1;
 
 Infox(){
    
	 setTitle("");
    setLayout(null);
    label1 = new JLabel();
    label1.setText("please wait!");
    label1.setBounds(5,10,100,20);
    add(label1);
    setAlwaysOnTop(true);
    setLocation(500,250);
   setVisible(true);
   setSize(224,80);
  }

   public static void main(String arg[]){
	   new Infox();
}
}

