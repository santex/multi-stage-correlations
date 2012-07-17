package com.multistage.correlations.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.algo.test.URLConnectionFlex;

public class NewData extends JPanel
                             implements ActionListener {
    public final static int ONE_SECOND = 1000;

	public static String[] arguments;

    private JProgressBar progressBar;
    private Timer timer;
    private JButton startButton;
    private LongTask task;
    private static JTextArea taskOutput;
    private String symbolFile = "";
    private String newline = "\n";

    public NewData() {
        super(new BorderLayout());
        task = new LongTask();

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        setTaskOutput(new JTextArea(5, 20));
        getTaskOutput().setMargin(new Insets(5,5,5,5));
        getTaskOutput().setEditable(false);
        getTaskOutput().setCursor(null); //inherit the panel's cursor
                                    //see bug 4851758

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(getTaskOutput()), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                String s = task.getMessage();
                if (s != null) {
                    getTaskOutput().append(s + newline);
                    getTaskOutput().setCaretPosition(
                            getTaskOutput().getDocument().getLength());
                }
                if (task.isDone()) {
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    startButton.setEnabled(true);
                    setCursor(null); //turn off the wait cursor
                    progressBar.setValue(progressBar.getMinimum());
                }
            }
        });
    }

    /**
     * Called when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task.go();
        timer.start();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(arguments[0]);
        

        //Create and set up the content pane.
        JComponent newContentPane = new NewData();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setSize(new Dimension(400,300));
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
      
    }

    public static void main(String[] args) {
    	
    	if(args.length!=1){
    		
    	}else{
    		if(args[0]!=null){
	    		arguments=args;
		        //Schedule a job for the event-dispatching thread:
		        //creating and showing this application's GUI.
		        javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                createAndShowGUI();
		            }
		        });
    		}
    	}
    }

	public void setTaskOutput(JTextArea taskOutput) {
		this.taskOutput = taskOutput;
	}

	public static JTextArea getTaskOutput() {
		return taskOutput;
	}
}
class LongTask {
    private int lengthOfTask;
    private int current = 0;
    private boolean done = false;
    private boolean canceled = false;
    private String statMessage;

    public LongTask() {
        //Compute length of task...
        //In a real program, this would figure out
        //the number of bytes to read or whatever.
        lengthOfTask = 1000;
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
            	new URLConnectionFlex().main(NewData.arguments);
                current = 0;
                done = false;
                canceled = false;
                statMessage = null;
                NewData.getTaskOutput().setText(SetEnv.DATALOG.toString());
                return new ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        canceled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }

    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            //Fake a long task,
            //making a random amount of progress every second.
            while (!canceled && !done) {
                try {
                    Thread.sleep(1000); //sleep for a second
                    current += Math.random() * 100; //make some progress
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                    statMessage = "Completed " + current +
                                  " out of " + lengthOfTask + ".";
                } catch (InterruptedException e) {
                    System.out.println("ActualTask interrupted");
                }
            }
        }
    }
}
/**
 * This is the 3rd version of SwingWorker (also known as
 * SwingWorker 3), an abstract class that you subclass to
 * perform GUI-related work in a dedicated thread.  For
 * instructions on and examples of using this class, see:
 * 
 * http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 *
 * Note that the API changed slightly in the 3rd version:
 * You must now invoke start() on the SwingWorker after
 * creating it.
 */
abstract class SwingWorker {
    private Object value;  // see getValue(), setValue()

    /** 
     * Class to maintain reference to current worker thread
     * under separate synchronization control.
     */
    private static class ThreadVar {
        private Thread thread;
        ThreadVar(Thread t) { thread = t; }
        synchronized Thread get() { return thread; }
        synchronized void clear() { thread = null; }
    }

    private ThreadVar threadVar;

    /** 
     * Get the value produced by the worker thread, or null if it 
     * hasn't been constructed yet.
     */
    protected synchronized Object getValue() { 
        return value; 
    }

    /** 
     * Set the value produced by worker thread 
     */
    private synchronized void setValue(Object x) { 
        value = x; 
    }

    /** 
     * Compute the value to be returned by the <code>get</code> method. 
     */
    public abstract Object construct();

    /**
     * Called on the event dispatching thread (not on the worker thread)
     * after the <code>construct</code> method has returned.
     */
    public void finished() {
    }

    /**
     * A new method that interrupts the worker thread.  Call this method
     * to force the worker to stop what it's doing.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }

    /**
     * Return the value created by the <code>construct</code> method.  
     * Returns null if either the constructing thread or the current
     * thread was interrupted before a value was produced.
     * 
     * @return the value created by the <code>construct</code> method
     */
    public Object get() {
        while (true) {  
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // propagate
                return null;
            }
        }
    }


    /**
     * Start a thread that will call the <code>construct</code> method
     * and then exit.
     */
    public SwingWorker() {
        final Runnable doFinished = new Runnable() {
           public void run() { finished(); }
        };

        Runnable doConstruct = new Runnable() { 
            public void run() {
                try {
                    setValue(construct());
                }
                finally {
                    threadVar.clear();
                }

                SwingUtilities.invokeLater(doFinished);
            }
        };

        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }

    /**
     * Start the worker thread.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
}


           