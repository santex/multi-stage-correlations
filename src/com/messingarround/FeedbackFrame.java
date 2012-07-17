package com.messingarround;


import java.lang.reflect.*;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;



public class FeedbackFrame extends JFrame implements Runnable {



    private Object stt;

    private Thread t;

    private JLabel label;

    private int state;



    static String[] stateMessages = {

        "Connecting to server...",

        "Logging into server...",

        "Waiting for data...",

        "Complete"

    };



    public FeedbackFrame(Object stt) {

        this.stt = stt;

        setupFrame( );

        t = new Thread(this);

        t.start( );

        pack( );

        show( );

    }



    private void setupFrame( ) {

        label = new JLabel( );

        label.setPreferredSize(new Dimension(200, 200));

        Container c = getContentPane( );

        JButton stopButton = new JButton("Stop");

        stopButton.addActionListener(new ActionListener( ) {

            public void actionPerformed(ActionEvent ae) {

                error( );

            }

        });

        c.add(label, BorderLayout.NORTH);

        c.add(stopButton, BorderLayout.SOUTH);

    }



    private void setText(final String s) {

        try {

            SwingUtilities.invokeAndWait(new Runnable( ) {

                public void run( ) {

                    label.setText(s);

                }

            });

        } catch (InterruptedException ie) {

            error( );

        } catch (InvocationTargetException ite) {

            error( );

        }

    }



    private void error( ) {

        t.interrupt( );

        if (SwingUtilities.isEventDispatchThread( ))

            closeDown( );

        else SwingUtilities.invokeLater(new Runnable( ) {

            public void run( ) {

                closeDown( );

            }

        });

    }



    private void closeDown( ) {

      
        hide( );

        dispose( );

    }



    public void run( ) {

        // Simulate connecting to server

        for (int i = 0; i < stateMessages.length; i++) {

            setText(stateMessages[i]);

            try {

                Thread.sleep(5 * 1000);

            } catch (InterruptedException ie) {}

            if (Thread.currentThread( ).isInterrupted( ))

                return;

         }

        SwingUtilities.invokeLater(new Runnable( ) {

            public void run( ) {

                

                hide( );

                dispose( );

            }

        });

    }

}