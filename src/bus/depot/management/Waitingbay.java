/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

/**
 *
 * @author George
 */


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Waitingbay {
    private int nBusin = 0; //blue
    private int nBusout = 0; //red
    private boolean BusInturn = false;
    Busdepot depot;
    public List < Bus > gocleanlist;
    public List < Bus > gochangelist;
    private boolean closingTime = false;

    int Waitingbayspace;
    int Waitingbaycapacity;

    public Waitingbay(int n, Busdepot depot) {
        Waitingbaycapacity = Waitingbayspace  = n;
        gocleanlist = new LinkedList < > ();
        gochangelist = new LinkedList < > ();
        this.depot = depot;

    }


    synchronized void arriveWB() throws InterruptedException {
        while (Waitingbayspace == 0) wait();
        --Waitingbayspace;
        notifyAll();
    }

    synchronized void departWB() throws InterruptedException {
        while (Waitingbayspace == Waitingbaycapacity) wait();
        ++Waitingbayspace;
        notifyAll();
    }

    public void Enterbay() throws InterruptedException {
        Bus bus;
            synchronized(depot.waitingbay) {
                if(depot.TotalBusList.isEmpty()){
                    return;
                }
                while(depot.waitingbay.isEmpty()) {
                    try {
                        System.out.println("Confirm waiting status");
                        
                        depot.waitingbay.wait();
                        

                    } catch (InterruptedException iex) {

                    }
                }

                bus = (Bus)((LinkedList < ? > ) depot.waitingbay).poll();
            }
            
            long duration;
            try {
                duration = (long)(Math.random() * 3);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }

            arriveWB();
            System.out.println("WAITING BAY SLOT AVAILABLE: " + Waitingbayspace);
            System.out.println("Arrived at Waiting Bay");
            if (bus.getCondition().equals("Cleaned") || bus.getCondition().equals("Complete Serviced")) {
                System.out.println("ENTERED");
                departWB();
                depot.Godepot(bus);
                return;
            }

            System.out.println(bus.getCondition() + bus.bus_name + " will be entering the appropriate bay soon");            
            if (bus.getCondition().equals("Dirty") || bus.getCondition().equals("Serviced but Dirty")) {
                synchronized(gocleanlist) {
                    ((LinkedList < Bus > ) gocleanlist).offer(bus);
                    gocleanlist.notifyAll();
                    departWB();
                }
                 } 
            else {
                synchronized(gochangelist) {
                    ((LinkedList < Bus > ) gochangelist).offer(bus);
                    gochangelist.notifyAll();
                    departWB();
                }
                
            }
            
            
    }

}