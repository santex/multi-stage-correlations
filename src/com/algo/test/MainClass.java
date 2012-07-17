package com.algo.test;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.multistage.correlations.gui.models.MarketDataModel;

public class MainClass extends JFrame {

  public MainClass() {
    super("Dynamic Data Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JTable jt = new JTable(new MarketDataModel(5));
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    MainClass mt = new MainClass();
    mt.setVisible(true);
  }
}

