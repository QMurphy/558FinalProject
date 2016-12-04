package edu.iastate;

/**
 * Created by Quinn on 12/4/2016.
 */
public class History {
    public int time;
    public Task task;
    public boolean success;

    public History(int time, Task task, boolean success) {
        this.time = time;
        this.task = task;
        this.success = success;
    }
}
