package com.algo.test;


import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * This class uses a web browser to display Slashdot's home page
 */
public class IframeGplot {
  /**
   * Runs the application
   */
  public void run() {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("Slashdot");
    createContents(shell);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  /**
   * Creates the main window's contents
   * 
   * @param shell the main window
   */
  private void createContents(Shell shell) {
    shell.setLayout(new FillLayout());

    // Create a web browser
    Browser browser = new Browser(shell, SWT.NONE);

    // Navigate to Slashdot
    browser.setUrl("http://localhost/qt/canvas/index.html");
  }

  /**
   * The application entry point
   * 
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new IframeGplot().run();
  }
}

