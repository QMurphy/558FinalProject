/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
public class Task {

    private int id;
    private boolean sync; //true = synchronous
    private double compTime;
    private double period;
    private double deadline;
    private double arrivalTime;

    public Task(int id, double cT, double p){
        this.id = id;
        sync = true;
        compTime = cT;
        period = p;
    }

    public Task(int id, double cT, double d, double aT){
        this.id = id;
        sync = false;
        compTime = cT;
        deadline = d;
        arrivalTime = aT;
    }

    public double calcNewTime(FrequencyScaler freqScaler){

    }

    public double calcEnergyUsed(VoltageScaler voltScaler, FrequencyScaler freqScaler){

    }

    /**
     * Getters
     */
    public int getID(){
        return id;
    }

    public boolean getSync(){
        return sync;
    }

    public double getCompTime(){
        return compTime;
    }

    public double getPeriod(){
        return period;
    }

    public double getDeadline(){
        return deadline;
    }

    public double getArrivalTime(){
        return arrivalTime;
    }

    /**
     * Setters
     */
    public void setID(int id){
        this.id = id;
    }

    public void setSync(boolean s){
        sync = s;
    }

    public void setCompTime(double cT){
        compTime = cT;
    }

    public void setPeriod(double p){
        period = p;
    }

    public void setDeadline(double d){
        deadline = d;
    }

    public void setArrivalTime(double aT){
        arrivalTime = aT;
    }
}