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
public class Clock implements Runnable {

    Bay bay;
    BusGenerator bg;
    boolean ClockTime = false;
    Statistics stat;
    WorkerGenerator wg;
    public Clock(BusGenerator bg, Bay bay, Statistics stat) {

        this.bay = bay;
        this.bg = bg;
        this.stat = stat;
    }

    public void run() {
        try {
            Thread.sleep(28800);
            notifyclosingTime(); //INITIALIZE

        } catch (InterruptedException iex) {
            iex.printStackTrace();
        } 
    }

    public void notifyclosingTime() {
        System.out.println("Its closing time!");
        ClockTime = true;
        bg.setclosingTime();
        bay.setclosingTime();
        stat.setclosingTime();

    }

}