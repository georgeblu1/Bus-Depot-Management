/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author George
 */
public class WorkerGenerator extends Thread {

    String cleanid;
    Mbay mbay;
    Cbay cbay;
    int numberofcleaner;
    Cleaner clean;
    Mechanic mech;
    Bus bus;

    public boolean closingTime = false;

    public WorkerGenerator(int _numberofcleaner, Cbay cbay, Mbay mbay) {
        this.numberofcleaner = _numberofcleaner;
        this.cbay = cbay;
        this.mbay = mbay;

    }

    public void run() {
        if(!closingTime){

        for (int n = 1; n <= numberofcleaner; n++) {
            if (n <= 2) {
                Mechanic mech1 = new Mechanic(mbay);
                Thread thmech = new Thread(mech1);
                mech1.setmechname("Mechanic " + n);
                System.out.println("Generated " + mech1.getmechname());
                ((LinkedList < Mechanic > ) mbay.mechaniclist).offer(mech1);
                thmech.start();
            }
            Cleaner clean1 = new Cleaner(cbay);
            Thread thclean = new Thread(clean1);
            clean1.setcleanername("Cleaner " + n);
            System.out.println("Generated " + clean1.getcleanname());

            ((LinkedList < Cleaner > ) cbay.cleanerlist).offer(clean1);
            thclean.start();
        }
        }
         if (closingTime) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
        }
    }
    
    
    
    
    public synchronized void setclosingTime() {
        closingTime = true;
        System.out.println("Work is going to go home soon");
    }

}