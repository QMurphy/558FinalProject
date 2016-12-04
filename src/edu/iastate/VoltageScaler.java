/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

import java.util.ArrayList;
import java.util.List;

public class VoltageScaler {
     private List<Float> availableVolt = new ArrayList<Float>();
    private float currentVolt = 0f;

    public VoltageScaler(List<Float> aV, float cV){
        availableVolt = aV;
        currentVolt = cV;
    }

    public VoltageScaler(float cV){
        currentVolt = cV;
        availableVolt.add(currentVolt);
    }

    /**
     * Getters
     */
    public List<Float> getAvailableVolt(){
        return availableVolt;
    }

    public float getCurrentVolt(){
        return currentVolt;
    }

    /**
     * Setters
     */
    public void addAvailableVolt(float v){
        availableVolt.add(v);
    }

    public void setCurrentVolt(float v){
        currentVolt = v;
        if(!availableVolt.contains(v))
            availableVolt.add(v);
    }
}