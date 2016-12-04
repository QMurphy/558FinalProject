package edu.iastate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public class Scheduler {
    // TODO Determine Preemption and log it in history

    private float targetMissRate;
    private ArrayList<Task> accepted;
    private ArrayList<History> hist;

    public Scheduler() {
        accepted = new ArrayList<>();
        hist = new ArrayList<>();
    }

    public void addTask(Task t) {
        accepted.add(t);
    }

    public void addTasks(List<Task> t) {
        accepted.addAll(t);
    }

    public void schedule(int curTime) {
        // TODO Add preemption
        // Check for missed deadlines
        for (int i = 0; i < accepted.size(); i++) {
            Task t = accepted.get(i);
            if ((t.getDeadline() + t.getArrivalTime()) - t.getCompTime() < curTime) {
                accepted.remove(t);
                i--;
                hist.add(new History(curTime, t, History.Event.MISS));
            }
        }

        if (accepted.size() > 0) {
            // Find next schedulable task
            Task scheduled = accepted.get(0);
            for (Task t : accepted) {
                if (scheduled.getDeadline() > t.getDeadline()) {
                    scheduled = t;
                }
            }

            // Work on the task and remove it if it finishes
            if (scheduled.work()) {
                accepted.remove(scheduled);
                hist.add(new History(curTime, scheduled, History.Event.HIT));
            }
        }
    }

    public List<History> getHistory() {
        return hist;
    }
}
