/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

public class Task {

    private int id;
    private boolean sync; //true = synchronous
    private int originalCompTime; //assuming default 1MHz frequency
    private int compTime;
    private int period;
    private int deadline;
    private int arrivalTime;
    private int compTimeLeft;

    public Task(int id, int cT, int p){
        this.id = id;
        sync = true;
        originalCompTime = cT;
        period = p;
        compTime = cT;
        compTimeLeft = compTime;
    }

    public void reset() {
        compTimeLeft = compTime;
    }

    public boolean work() {
        if (compTimeLeft > 0) {
            compTimeLeft--;
        }
        return compTimeLeft == 0;
    }

    public Task(int id, int cT, int d, int aT){
        this.id = id;
        sync = false;
        originalCompTime = cT;
        deadline = d;
        arrivalTime = aT;
        compTime = cT;
        compTimeLeft = compTime;
    }

    //calculate new computation time
    public int calcNewTime(FrequencyScaler freqScaler){
        compTime = (int)Math.ceil(originalCompTime/freqScaler.getCurrentFreq());
        compTimeLeft = (int)Math.ceil(compTimeLeft/freqScaler.getCurrentFreq());
        return compTime;
    }

    public double calcEnergyUsed(VoltageScaler voltScaler, FrequencyScaler freqScaler){
        double energy = (Math.sqrt(voltScaler.getCurrentVolt()) * freqScaler.getCurrentFreq()) * compTime;
        return energy;
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

    public int getCompTime(){
        return compTime;
    }

    public int getCompTimeLeft(){
        return compTimeLeft;
    }

    public int getPeriod(){
        return period;
    }

    public int getDeadline(){
        return deadline;
    }

    public int getArrivalTime(){
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

    public void setCompTime(int cT){
        compTime = cT;
    }

    public void setPeriod(int p){
        period = p;
    }

    public void setDeadline(int d){
        deadline = d;
    }

    public void setArrivalTime(int aT){
        arrivalTime = aT;
    }
}