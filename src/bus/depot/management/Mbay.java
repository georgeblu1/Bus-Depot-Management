/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author GeorgeWong
 */

class Mbay extends Bay {

    Busdepot depot;
    Mechanic mech;
    Waitingbay wbay;
    int servicingtime;
    double maximum = 0;
    double count = 0;
    double average = 0;
    double minimum = 0;
    int countbus = 0;
    List < Mechanic > mechaniclist;

    public Mbay(int servicingtime, Busdepot depot, Waitingbay wbay) {
        this.wbay = wbay;
        this.depot = depot;
        this.servicingtime = servicingtime;
        mechaniclist = new LinkedList < > ();

    }

    public void changebus() throws InterruptedException 
    {
        Mechanic mechanic;

        synchronized(wbay.gochangelist) 
        {
            if(depot.TotalBusList.isEmpty()) return;
            while (mechaniclist.isEmpty() || wbay.gochangelist.isEmpty()) 
            {
                try 
                {
                    System.out.println("Mechanic Waiting");
                    wbay.gochangelist.wait();
                } catch (InterruptedException iex) 
                {
                    iex.printStackTrace();
                }
            }


            mechanic = (Mechanic)((LinkedList < ? > ) mechaniclist).poll();
            bus = (Bus)((LinkedList < ? > ) wbay.gochangelist).poll();

            System.out.println(bus.getCondition() + bus.getName() + " visit Mechanic Bay ");
            System.out.println(mechanic.getmechname() + " found " + bus.getCondition() + bus.getName() + " on the way!.");


            long duration = 0;
            if (bus.getCondition().equals("Servicing Needed")) 
            {
                try 
                {
                    System.out.println(mechanic.getmechname() + " wil start changing oil for " + bus.getCondition() + bus.getName());
                    duration = (long)(servicingtime);
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException iex) {
                }

            }
            if (bus.getCondition().equals("Mechanical Malfunctioned")) 
            {
                try {
                    System.out.println(mechanic.getmechname() + " wil start fixing " + bus.getCondition() + bus.getName());
                    duration = (long)(Math.random() * servicingtime + 2);
                    TimeUnit.SECONDS.sleep(duration);
                    } catch (InterruptedException iex) 
                        {
                    iex.printStackTrace();
                        }
             }

            if (bus.getCondition().equals("Fuel Shortaged")) 
            {
                try 
                {
                    System.out.println(mechanic.getmechname() + " wil fix fuel shortage for  " + bus.getCondition() + bus.getName());
                    duration = (long)(Math.random() * servicingtime + 3);
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException iex) 
                {
                    iex.printStackTrace();
                }
                
            }
            if (bus.getCondition().equals("Full Fix Required")) {
                try {
                    System.out.println(mechanic.getmechname() + " wil do a complete overhaul on " + bus.getCondition() + bus.getName());
                    duration = (long)(Math.random() * servicingtime + 4);
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            } else if (bus.getCondition().equals("Servicing and Cleaning Needed")) {
                try {
                    System.out.println(mechanic.getmechname() + " wil start changing oil for " + bus.getCondition() + bus.getName() + " first");
                    duration = (long)(servicingtime);
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
                System.out.println(mechanic.getmechname() + " completed servicing for: " + bus.getCondition() + bus.getName() + " in " + duration + " seconds.");
                count = count + duration;
                if(duration > maximum) maximum = duration;
                if (duration < minimum) minimum = duration;
                bus.setCondition("Serviced but Dirty");
                System.out.println(bus.getCondition() + bus.getName() + " exits Mechanic Bay");
                ((LinkedList < Mechanic > ) mechaniclist).offer(mechanic);
                synchronized(depot.waitingbay) {
                ((LinkedList < Bus > ) depot.waitingbay).offerFirst(bus);
                System.out.println("Offered to Waitingbay from Mbay");
                depot.waitingbay.notify();
            }
                
                
                return;
            }

            System.out.println(mechanic.getmechname() + " completed servicing for: " + bus.getCondition() + bus.getName() + " in " + duration + " seconds.");
            bus.setCondition("Complete Serviced");
            count = count + duration;
            if(duration > maximum) maximum = duration;
            if (duration < minimum) minimum = duration;
            System.out.println(bus.getCondition() + bus.getName() + " exits Mechanic Bay");
            ((LinkedList < Mechanic > ) mechaniclist).offer(mechanic);
            synchronized(depot.waitingbay) {
                ((LinkedList < Bus > ) depot.waitingbay).offerFirst(bus);
                System.out.println("Offered to Waitingbay from Mbay");
                depot.waitingbay.notify();
            }
        }
        
        




    }


}