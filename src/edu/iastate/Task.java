/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

public class Task {

    private int id;
    private boolean sync; //true = synchronous
    private int compTime;
    private int period;
    private int deadline;
    private int arrivalTime;

    public Task(int id, int cT, int p){
        this.id = id;
        sync = true;
        compTime = cT;
        period = p;
    }

    public Task(int id, int cT, int d, int aT){
        this.id = id;
        sync = false;
        compTime = cT;
        deadline = d;
        arrivalTime = aT;
    }

    public int calcNewTime(FrequencyScaler freqScaler){
        return 0;
    }

    public int calcEnergyUsed(VoltageScaler voltScaler, FrequencyScaler freqScaler){
        return 0;
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