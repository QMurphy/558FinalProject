package edu.iastate;

/**
 * Created by qmurp on 12/7/2016.
 */
public class PIDController {
    private float kp, ki, kd;
    private float targetMissRate;
    private float accError;
    private float prevError;
    private float lastInput;
    private float lastOutput;

    public PIDController(float kp, float ki, float kd, float targetMissRate) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.targetMissRate = targetMissRate;
        prevError = 0;
    }

    float update(float actualMissRate) {
        float error = actualMissRate - targetMissRate;
        accError += error;

        // Calculate the correction value
        float cpuUtil = kp * error + ki * accError + kd * (error - prevError);

        prevError = error;
        return cpuUtil;
    }
}
