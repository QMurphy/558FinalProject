package edu.iastate;

/**
 * Created by Quinn on 12/4/2016.
 */
public class History {
    public enum Event {
        HIT,
        MISS,
        PREEMPTION
    }

    public int time;
    public Task task;
    public Event event;

    public History(int time, Task task, Event event) {
        this.time = time;
        this.task = task;
        this.event = event;
    }

    public static String eventToString(Event e) {
        switch (e) {
            case HIT: return "Hit";
            case MISS: return "Miss";
            case PREEMPTION: return "Preemption";
            default: return "";
        }
    }
}
