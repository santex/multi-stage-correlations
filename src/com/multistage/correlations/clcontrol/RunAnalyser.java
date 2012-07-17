package com.multistage.correlations.clcontrol;
import com.multistage.correlations.algorithms.Analyse;
import com.multistage.correlations.cluster.*;




// analyse
class RunAnalyser implements Runnable {

 private Thread t=null;
 private String mess;


 RunAnalyser(String s1) {
       mess=s1;
 }


 public boolean  Alive(){

      boolean tt=false;
      if (t != null) {
          if (t.isAlive())  tt=true;
       }
      return tt;
 }


public boolean  Joint(){

      boolean tt=false;
      try {
        t.join();
        return true; // finished

       } catch (InterruptedException e) {
        // Thread was interrupted
        }

        return tt;
  }


 public void Start(){

     t= new Thread (this,mess);
     t.start();

  }


  public void Stop() { t = null; }



// runs k-means
  public void run() {

    Analyse.Run(1);
   }

 } // end Thread1


