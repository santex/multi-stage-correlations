package com.multistage.correlations.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.algo.test.focus;
public class ProgressBarDemo extends JPanel implements ActionListener,
    PropertyChangeListener {
  private static Main m;

  private JProgressBar progressBar;
  private JButton startButton;
  private JTextArea taskOutput;
  private Task task;
  public static JFrame frame;
  public ThreadRefresh up;

  class Task extends SwingWorker<Void, Void> {
	  Thread t=null;

@Override
    public Void doInBackground() {
      Random random = new Random();
      int progress = 0;
      // Initialize progress property.
      setProgress(0);
      Thread t = new Thread(new Runnable() {
          public void run() {
              try {
            	if(m==null) {
            		m = new Main();
            	
            		new focus();
            		m.frame.m_ClControl.go();
            		m.frame.repaint();
            		//up = new ThreadRefresh(0,m.frame);
            		//UpdateTimer task = new UpdateTimer(new URL("http://www.google.com"),up);
      			    //task.timer.schedule(task, 0L, 1000L);
            	}else {
            		m.frame.m_ClControl.go();
            		m.frame.repaint();
            	}
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          }
      });
	  t.start();
      
      while (progress < 100) {
        // Sleep for up to one second.
    	try {
    		  
  	    if(t.isAlive()){
  	    	Thread.sleep(random.nextInt(10000));
  	    }
        
        } catch (InterruptedException ignore) {
        } catch (Exception ignore) {
        }
        // Make random progress.
        progress += random.nextInt(10);
        setProgress(Math.min(progress, 100));
      }
      return null;
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
      Toolkit.getDefaultToolkit().beep();
      startButton.setEnabled(true);
      setCursor(null); // turn off the wait cursor
      taskOutput.append("Done!\n");
      
      
	  javax.swing.SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	    	  
	    	  frame.disable();
	    	  frame.dispose();
	    	  frame=null;
	    	  ////m.frame.m_ClControl.go();
	    	  //.exec(new String[]{"lib/dataFile.msc"});
	
	    	  
	      }
	    });
      
    }
  }

  public ProgressBarDemo() {
    super(new BorderLayout());

    // Create the demo's UI.
    startButton = new JButton("Start");
    startButton.setActionCommand("start");
    startButton.addActionListener(this);

    progressBar = new JProgressBar(0, 100);
    progressBar.setValue(0);
    progressBar.setStringPainted(true);

    taskOutput = new JTextArea(5, 20);
    taskOutput.setMargin(new Insets(5, 5, 5, 5));
    taskOutput.setEditable(false);

    JPanel panel = new JPanel();
    //panel.add(startButton);
    panel.add(progressBar);

    add(panel, BorderLayout.PAGE_START);
    add(new JScrollPane(taskOutput), BorderLayout.CENTER);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    // Instances of javax.swing.SwingWorker are not reusuable, so
    // we create new instances as needed.
    task = new Task();
    task.addPropertyChangeListener(this);
    task.execute();

   
    
  }

  /**
   * Invoked when the user presses the start button.
   */
  public void actionPerformed(ActionEvent evt) {
    startButton.setEnabled(false);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    // Instances of javax.swing.SwingWorker are not reusuable, so
    // we create new instances as needed.
    task = new Task();
    task.addPropertyChangeListener(this);
    task.execute();
  }

  /**
   * Invoked when task's progress property changes.
   */
  public void propertyChange(PropertyChangeEvent evt) {
    if ("progress" == evt.getPropertyName()) {
      int progress = (Integer) evt.getNewValue();
      progressBar.setValue(progress);
      taskOutput.append(String.format("Completed %d%% of task.\n", task
          .getProgress()));
    }
  }

  /**
   * Create the GUI and show it. As with all GUI code, this must run on the
   * event-dispatching thread.
   */
  private static void createAndShowGUI() {
    // Create and set up the window.
    frame  = new JFrame("Starting....");
    
    // Create and set up the content pane.
    JComponent newContentPane = new ProgressBarDemo();
    newContentPane.setOpaque(true); // content panes must be opaque
    frame.setContentPane(newContentPane);

    // Display the window.
    frame.pack();
    frame.setSize(new Dimension(400,300));
    frame.setVisible(true);
    frame.setAlwaysOnTop(true);
    
  }

  public static void main(String[] args){
	  
	  
	  javax.swing.SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        createAndShowGUI();
	      }
	    });
  }
}