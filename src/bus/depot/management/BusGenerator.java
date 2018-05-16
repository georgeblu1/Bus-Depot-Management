/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

/*
 * @author GeorgeWong
 */


import java.util.Date;
import java.util.Random;

import java.util.concurrent.TimeUnit;

public class BusGenerator extends Thread {
    Busdepot depot;
    public boolean closingTime = false;
    int numberofbus;


    public BusGenerator(int _bus, Busdepot depot) {
        this.numberofbus = _bus;
        this.depot = depot;
    }


    public void run() {
        int counter = 1;
        while (!closingTime) {
            if(counter <= numberofbus){
                Bus bus = new Bus(depot);
                bus.setInTime(new Date());
                Thread thbus = new Thread(bus);
                Random rand = new Random();
                int n = rand.nextInt(20) + 1;

                if (n % 2 != 0) {
                    bus.setCondition("Servicing Needed");
                }
                if (n == 15) {
                    bus.setCondition("Servicing and Cleaning Needed");
                }
                if (n == 5) {
                    bus.setCondition("Mechanical Malfunctioned");
                }
                if (n == 9) {
                    bus.setCondition("Fuel Shortaged");
                }

                if (n % 2 == 0) {
                    bus.setCondition("Dirty");
                }

                if (n == 12 || n == 18) {
                    bus.setCondition("Full Fix Required");
                }

                bus.setName(" Bus " + counter); //SETUP CLOCK FOR THE BUS DEPOT REMEMBER

                counter++;
                thbus.start();

                try {
                    TimeUnit.SECONDS.sleep((long)(Math.random() * 5));
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }

                //        }
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
        System.out.println("Last Buses will be entertained");
    }
}