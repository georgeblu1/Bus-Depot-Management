/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * @author GeorgeWong
 */


public class Cbay extends Bay {

    Busdepot depot;
    Waitingbay wbay;
    int servicingtime;
    List < Cleaner > cleanerlist;
    double maximum = 0;
    double count = 0;
    double average = 0;
    double minimum = 0;


    public Cbay(int servicingtime, Busdepot depot, Waitingbay wbay) {
        this.wbay = wbay;
        this.depot = depot;

        this.servicingtime = servicingtime;
        cleanerlist = new LinkedList < > ();

    }

    public void cleanbus() throws InterruptedException {
        Cleaner cleaner;
        synchronized(wbay.gocleanlist) {
            if(depot.TotalBusList.isEmpty()) return;
            while (cleanerlist.isEmpty() || wbay.gocleanlist.isEmpty()) {
                try {
                    System.out.println("Cleaner Waiting");
                    wbay.gocleanlist.wait();
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            }

            cleaner = (Cleaner)((LinkedList < ? > ) cleanerlist).poll();
            bus = (Bus)((LinkedList < ? > ) wbay.gocleanlist).poll();
            System.out.println(bus.getCondition() + bus.getName() + " " + "visit Cleaning Bay ");
            System.out.println(cleaner.getcleanname() + " found " + bus.getCondition() + bus.getName() + " on the way!.");

            long duration = 0;
            try {
                System.out.println("Cleaning will start on " + bus.getCondition() + bus.getName());
                duration = (long)(servicingtime);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }

            System.out.println(cleaner.getcleanname() + " completed cleaning for: " + bus.getCondition() + bus.getName() + " in " + duration + " seconds.");
            bus.setCondition("Cleaned");
            count = count + duration;
            if(duration > maximum) maximum = duration;
            if (duration < minimum) minimum = duration;
            System.out.println(bus.getCondition() + bus.getName() + " exits Cleaning Bay");
            ((LinkedList < Cleaner > ) cleanerlist).offer(cleaner);
            synchronized(depot.waitingbay) {
                ((LinkedList < Bus > ) depot.waitingbay).offerFirst(bus);
                System.out.println("Offered to Waiting Bay from Cbay");
                depot.waitingbay.notifyAll();
            }
        }
            

        


    }



}