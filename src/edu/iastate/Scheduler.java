package edu.iastate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public class Scheduler {
    private float targetMissRate;
    private ArrayList<Task> accepted;
    private ArrayList<History> hist;
    private int lastID;
    private boolean lastFinished;

    public Scheduler() {
        accepted = new ArrayList<>();
        hist = new ArrayList<>();
        lastID = -1;
        lastFinished = true;
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

            // Check if we're preempting
            // If the last task is different from this one and the last task didn't finish its run, there was a preemption
            if (scheduled.getID() != lastID && lastFinished == false) {
                hist.add(new History(curTime, scheduled, History.Event.PREEMPTION));
            }

            // Work on the task and remove it if it finishes
            if (scheduled.work()) {
                accepted.remove(scheduled);
                hist.add(new History(curTime, scheduled, History.Event.HIT));
            }

            // Update preemption detection variables
            if (scheduled.getCompTimeLeft() == 0) {
                lastFinished = true;
            }
            else {
                lastFinished = false;
            }
            lastID = scheduled.getID();
        }
    }

    public List<History> getHistory() {
        return hist;
    }
}
