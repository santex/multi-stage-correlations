package com.algo.test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;



public class LoanCalculatorFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoanCalculatorFrame()
	{
		setTitle("Loan Calculator");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen:" + d.width + " by " + d.height + "pixels");
		int height = 200;
		int width = 267;
		setBounds((d.width-width)/2, (d.height-height)/2, width, height);
		setResizable(false);
		addWindowListener(new WindowAdapter()
							{
								public void windowClosing(WindowEvent e)
								{
									System.exit(0);
								}
							});
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		contentPane.add(panel);


	}
	
	
}