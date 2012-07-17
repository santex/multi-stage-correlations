package com.messingarround;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NextPage extends JFrame
{
  NextPage(String st)
   {
      setLayout(null);
     setDefaultCloseOperation(javax.swing. WindowConstants.DISPOSE_ON_CLOSE);
     setTitle("Welcome");
     JLabel lab=new JLabel("Welcome  "+st);

     lab.setBounds(10,10,500,20);
     add(lab);

       setSize(1024, 768);
      }
 }