package com.algo.test;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class tester extends JPanel {

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    
    double ew = 10 / 2;
    double eh = 10 / 2;
    Ellipse2D.Double circle, circle1;

    circle = new Ellipse2D.Double(ew - 16, eh - 29, 50.0, 50.0);

    g2.setColor(Color.green);
    g2.fill(circle);

    g2.setColor(Color.red);
    circle1 = new Ellipse2D.Double(ew, eh, 50.0, 50.0);
    g2.fill(circle1);
    
    Area area1 = new Area(circle);
    Area area2 = new Area(circle1);
    
    g2.setColor(Color.BLUE);
    area1.intersect(area2);
    g2.fill(area1);

    
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new tester());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(200, 200);
    frame.setVisible(true);
  }
}
     