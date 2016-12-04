/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

import java.util.ArrayList;
import java.util.List;

public class FrequencyScaler {
    private List<Float> availableFreq = new ArrayList<Float>();
    private float currentFreq = 1f; //in MHz

    public FrequencyScaler(List<Float> aF, float cF){
        availableFreq = aF;
        currentFreq = cF;
    }

    public FrequencyScaler(float cF){
        currentFreq = cF;
        availableFreq.add(currentFreq);
    }

    /**
     * Getters
     */
    public List<Float> getAvailableFreq(){
        return availableFreq;
    }

    public float getCurrentFreq(){
        return currentFreq;
    }

    /**
     * Setters
     */
    public void addAvailableFreq(float f){
        availableFreq.add(f);
    }

    public void setCurrentFreq(float f){
        currentFreq = f;
        if(!availableFreq.contains(f))
            availableFreq.add(f);
    }
}