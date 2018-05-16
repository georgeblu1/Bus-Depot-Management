/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

/*
 * @author GeorgeWong
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


class Busdepot {

    private int nBusin = 0; //blue
    private int nBusout = 0; //red
    private boolean BusInturn = true;
    public List < Bus > listBus;
    public List < Bus > waitingbay;
    public List < Bus > TotalBusList;
    public int buscount = 0;
    public List < Bus > waitingarea;

    public Busdepot() {
        TotalBusList = new LinkedList < Bus > ();
        listBus = new LinkedList < Bus > (); // LOOK AT THIS, MAYBE CAN USE ANOTHER ARRAY THATS THREAD SPECIFIC
        waitingbay = new LinkedList < Bus > ();
        waitingarea = new LinkedList < Bus > ();

    }



    /*************************Ramp Monitor*******************************/

    synchronized void BusOutIntoRamp() throws InterruptedException {
        while (nBusin > 0 || BusInturn) wait();
        nBusout++;
    } //Code extracts from Mr. Zailan's Bridge.java

    synchronized void BusOutOutRamp() {
        nBusout--;
        BusInturn = true;
        if (nBusout == 0) notifyAll();
    }

    synchronized void BusInIntoRamp() throws InterruptedException {
        while (nBusout > 0 || !BusInturn) wait();
        nBusin++;
    }

    synchronized void BusInOutRamp() {
        nBusin--;
        BusInturn = true;
        if (nBusin == 0) notifyAll();
    }

    public void Startjob() throws InterruptedException {
        Bus bus;
        synchronized(listBus) {
            while (listBus.size() == 0) {
                System.out.println("Ramp available. Next bus please!");
                try {
                    listBus.wait();
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            }
            bus = (Bus)((LinkedList < ? > ) listBus).poll();
        }
        System.out.println(bus.getCondition() + bus.getName() + " proceed to waiting bay");
        buscount++;
    }
 
    public void Godepot(Bus bus) throws InterruptedException { //add the bus into the depot

        System.out.println(bus.getCondition() + bus.getName() + " requesting to use Ramp at " + bus.gettime());

        if (bus.getCondition().equals("Cleaned") || bus.getCondition().equals("Complete Serviced")) {
            synchronized(listBus) {
                BusInturn = false;
                BusOutIntoRamp();
                ((LinkedList < Bus > ) listBus).offerFirst(bus);
                System.out.println(bus.getCondition() + bus.getName() + " acquired Ramp at " + bus.gettime());
                long duration = 0;
                try {
                    duration = (long)(Math.random() * 3);
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException iex) {}
                System.out.println(bus.getCondition() + bus.getName() + " crossed the ramp in " + duration + " seconds");
                bus = (Bus)((LinkedList < ? > ) listBus).pollFirst();
                for (Iterator < Bus > iterator = TotalBusList.iterator(); iterator.hasNext();) {
                    Bus bus1 = iterator.next();
                    if (bus1.equals(bus)) {
                        // Remove the current element from the iterator and the list.
                        iterator.remove();
                    }
                } //Iterator, @author Bill K, 2008 from Stackoverflow

                System.out.println(bus.getCondition() + bus.getName() + " exits.......");
                BusOutOutRamp();
                return;
            }
        }
        
        synchronized(listBus) {

            if (listBus.size() == 3) {
                System.out.println("Ramp and Waiting Bay is fully occupied!");
                System.out.println(bus.getCondition() + bus.getName() + " exits");
                return;
            }
            long duration = 0;

            ((LinkedList < Bus > ) TotalBusList).offer(bus);
            ((LinkedList < Bus > ) listBus).offer(bus);
            System.out.println(bus.getCondition() + bus.getName() + " acquired Ramp at " + bus.gettime());
            BusInIntoRamp();
            try {
                duration = (long)(Math.random() * 5);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException iex) {}
            System.out.println(bus.getCondition() + bus.getName() + " crossed the ramp in " + duration + " seconds");
            BusInOutRamp();
            System.out.println(bus.getCondition() + bus.getName() + " you are clear to proceed to depot waiting bay.");
            

            if (listBus.size() == 1) listBus.notify();
        }
        synchronized(waitingbay){
            ((LinkedList < Bus > ) waitingbay).offer(bus);
            waitingbay.notifyAll();
        }


    }

}
