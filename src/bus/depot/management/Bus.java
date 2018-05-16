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
import java.util.logging.Level;
import java.util.logging.Logger;

class Bus implements Runnable
{
    protected String bus_name;
    protected String buscondition;
    public Date time;
    public Busdepot depot;
    public Mbay mbay;
    public Cbay cbay;
    public String bussize;
    
 
   
    public Bus(Busdepot depot)
    {
        this.depot = depot;
    }
    
    
    public Bus(){
        
    }
 
    public String getName() {
        return bus_name;
    }
    public void setName(String bus_name) {
        this.bus_name = bus_name;
    }
    public String getBussize(){
        return bussize;
    }
    public void setBussize(String bussize){
        this.bussize = bussize;
    }
    public String getCondition(){
        return buscondition;
    }
    public void setCondition(String bc){
        this.buscondition = bc;
    }
 
    public Date gettime() {
        return time;
    }
 
    public void setInTime(Date time) {
        this.time = time;
    }
 
    public  void run()
    {
        
        try {
            EnterRamp();  //run this thread so it can enter the ramp 
        } catch (InterruptedException ex) {}
        
    }
    
    
    
    private void EnterRamp() throws InterruptedException 
    {
        depot.Godepot(this); //Go to depot
    }
    
}
