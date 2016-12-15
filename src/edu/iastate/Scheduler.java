package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public class Scheduler {
    private ArrayList<Task> accepted;
    private ArrayList<Integer> utilHistory;
    private ArrayList<History> hist;
    private int lastID;
    private boolean lastFinished;

    public Scheduler() {
        accepted = new ArrayList<>();
        hist = new ArrayList<>();
        lastID = -1;
        lastFinished = true;
        utilHistory = new ArrayList<>();
    }

    public void addTask(Task t) {
        accepted.add(t);
    }

    public void addTasks(List<Task> t) {
        accepted.addAll(t);
    }

    public int schedule(int curTime) {
        // Return if work is being done or not
        int missedDeadlines = 0;

        // Check for missed deadlines
        for (int i = 0; i < accepted.size(); i++) {
            Task t = accepted.get(i);
            if ((t.getDeadline()) - t.getCompTimeLeft() < curTime) {
                accepted.remove(t);
                missedDeadlines++;
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
                hist.add(new History(curTime, scheduled, History.Event.COMPLETE));
            }

            // Update preemption detection variables
            if (scheduled.getCompTimeLeft() == 0) {
                lastFinished = true;
            }
            else {
                lastFinished = false;
            }
            lastID = scheduled.getID();

            utilHistory.add(1);
        }
        else {
            utilHistory.add(0);
        }
        if (utilHistory.size() > 50) {
            utilHistory.remove(0);
        }

        return missedDeadlines;
    }

    public List<History> getHistory() {
        return hist;
    }

    // TODO
    public float getCPUUtil() {
        if (utilHistory.size() == 0) {
            return 0.0f;
        }
        int sum = 0;
        for (Integer i : utilHistory) {
            sum += i;
        }
        return (1.0f * sum) / utilHistory.size();
    }
}
