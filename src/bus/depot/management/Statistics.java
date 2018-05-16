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
 class Statistics implements Runnable{
    Mbay mbay;
    Cbay cbay;
    Busdepot depot;
    Bay bay;
    boolean closingTime = false;
    
    public Statistics(Bay bay, Mbay mbay, Cbay cbay, Busdepot depot){
        this.cbay  = cbay;
        this.mbay = mbay;
        this.depot = depot;
       this.bay = bay;
    }
    
    
    public void run()
    {
        while(closingTime && bay.getN() == 1){
            PrintStatistics();
        }
    }    
    public void PrintStatistics(){
        
        System.out.println("Bus Depot Statistics");
        System.out.println(" ");
        System.out.println("Total number of buses served: " + depot.buscount);
        System.out.println(" ");
        System.out.println("Mechanic Statistics");
        System.out.println("Maximum: " + mbay.maximum);
        System.out.println("Average: " + mbay.maximum / mbay.count);
        System.out.println("Minimum: " + mbay.minimum);
        System.out.println(" ");
        System.out.println("Cleaner Statistics");
        System.out.println("Maximum: " + cbay.minimum);
        System.out.println("Average: " + cbay.maximum / cbay.count);
        System.out.println("Minimum: " + cbay.minimum); 
    }
    
    
     public synchronized void setclosingTime() {
        closingTime = true;
    }
    
    
}
