/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.Scanner;

/*
 * @author GeorgeWong
 */


public class BusDepotManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Bus Depot! ");
        System.out.println("Number of cleaners: ");
        int cleanernum = scan.nextInt();
        System.out.println("Number of Buses: ");
        int busesnum = scan.nextInt();
        System.out.println("Time for Mechanic tasks: ");
        int mechtasktime = scan.nextInt();
        System.out.println("Time for Cleaner tasks: ");
        int cleantasktime = scan.nextInt();  
        
        Busdepot depot = new Busdepot(); //create depot
        Waitingbay wb = new Waitingbay(5,depot);
        Bay bay = new Bay(wb,depot);                
        Cbay cbay = new Cbay(cleantasktime,depot,wb);
        Mbay mbay = new Mbay(mechtasktime,depot,wb);
        BusGenerator bg = new BusGenerator(busesnum,depot); //generate bus to depot
        WorkerGenerator wg = new WorkerGenerator(cleanernum,cbay,mbay);
        Statistics stat = new Statistics(bay, mbay, cbay, depot);
        
        Thread thbg = new Thread(bg);
        Thread thwg = new Thread(wg);
        Thread thbay = new Thread(bay);
        Thread thstat = new Thread(stat);
        
        thbg.start();
        thbay.start();
        thwg.start();
        thstat.start();
        
        Clock clock = new Clock(bg, bay, stat);
        clock.run();
        
       
        
        
        //Clock clock = new Clock(bg,mech1);
        //clock.run();
        // TODO code application logic here
    }
    
}
