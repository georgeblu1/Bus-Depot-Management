/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.depot.management;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

/*
 * @author GeorgeWong
 */

class Bay implements Runnable {

    Bus bus;
    Busdepot depot;
    int mechid;
    int cleanid;
    int n = 0;
    Waitingbay bay;
    public boolean closingTime = false;


    public Bay(Waitingbay bay, Busdepot depot) {
        this.bay = bay;
        this.depot = depot;

    }
    
    public void setN(int N){
        this.n = N;
        
    }
    public int getN(){
        return n;
    }

    Bay() {}

    @Override
    public void run() {
        
        System.out.println("Cleaners + Mechanics started....");
        while (!closingTime) {

            try {
                depot.Startjob();
                bay.Enterbay();
            } catch (InterruptedException ex) {

            }
        }
        

        if (closingTime) {
            try {
                Thread.sleep(5000);
                while(!depot.TotalBusList.isEmpty() || !bay.gochangelist.isEmpty() || !bay.gocleanlist.isEmpty()) {
                    bay.Enterbay();
                }
                
                if (depot.TotalBusList.isEmpty()) {
                    System.out.println("End of program!");
                    setN(1);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    
    //   

    public synchronized void setclosingTime() {
        closingTime = true;
        System.out.println("Bay closing now!");
    }
}