package com.multistage.correlations.gui.models;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;


public class CheckableItem {
    private String str;

    private boolean isSelected;

    public CheckableItem(String str) {
      this.str = str;
      isSelected = false;
    }

    public void setSelected(boolean b) {
      isSelected = b;
    }

    public boolean isSelected() {
      return isSelected;
    }

    public String toString() {
      return str;
    }
  }
  
