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

public class Mechanic implements Runnable {


    String Mechname;
    Mbay bay;
    public boolean closingTime = false;

    Mechanic(Mbay bay) {

        this.bay = bay;

    }


    public void setmechname(String mn) {
        this.Mechname = mn;
    }

    public String getmechname() {
        return Mechname;
    }


    public void run() {
        while(true){
        try {
            bay.changebus();
        } catch (InterruptedException ex) {}
    }
            
        
    }




    }

   
