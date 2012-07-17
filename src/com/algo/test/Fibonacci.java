package com.algo.test;

import javax.swing.text.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;

public class Fibonacci {

 public static void main(String[] args) {

     StringBuffer result =
      new StringBuffer("<html><body><h1>Fibonacci Sequence</h1><ol>");

     long f1 = 0;
     long f2 = 1;

     for (int i = 0; i < 50; i++) {
       result.append("<li>");
       result.append(f1);
       long temp = f2;
       f2 = f1 + f2;
       f1 = temp;
     }

     result.append("</ol></body></html>");

     JEditorPane jep = new JEditorPane("text/html", result.toString());
     jep.setEditable(false);

     
   //  new FibonocciRectangles().execute();
     JScrollPane scrollPane = new JScrollPane(jep);
     JFrame f = new JFrame("Fibonacci Sequence");
     f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     f.setContentPane(scrollPane);
     f.setSize(512, 342);
     EventQueue.invokeLater(new FrameShower(f));

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

 