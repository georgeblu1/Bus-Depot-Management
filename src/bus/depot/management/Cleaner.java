/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author GeorgeWong
 */
class Cleaner implements Runnable {

    String cleanid;
    Cbay cbay;
    Bus bus;
    Waitingbay wbay;
    public boolean closingTime = false;
    

    Cleaner(Cbay cbay) {
        this.cbay = cbay;

    }

    public void setcleanername(String cn) {
        this.cleanid = cn;
    }

    public String getcleanname() {
        return cleanid;
    }


    public void run() {
        while(true){
            try {
                cbay.cleanbus();
            } catch (InterruptedException ex) {
            }
            
        
        }       
    }

     public synchronized void setclosingTime() {
        closingTime = true;
        System.out.println("Bay closing now!");
    }
   
}