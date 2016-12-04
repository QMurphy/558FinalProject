package edu.iastate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public class Scheduler {
    private float targetMissRate;
    private IAsyncTaskServer taskServer;
    private ArrayList<Task> accepted;
    private ArrayList<History> hist;

    public Scheduler(IAsyncTaskServer server) {
        taskServer = server;
        accepted = new ArrayList<>();
        hist = new ArrayList<>();
    }

    public void addTask(Task t) {
        if (!t.getSync()) {
            taskServer.addTask(t);
        }
        else {
            accepted.add(t);
        }
    }

    public int schedule(int curTime) {
        if (accepted.size() > 0) {
            // Check for missed deadlines
            for (int i = 0; i < accepted.size(); i++) {
                Task t = accepted.get(i);
                if (t.getDeadline() - t.getCompTime() < curTime) {
                    accepted.remove(t);
                    i--;
                    hist.add(new History(curTime, t, false));
                }
            }

            // Find next schedulable task
            Task scheduled = accepted.get(0);
            for (Task t : accepted) {
                if (scheduled.getDeadline() > t.getDeadline()) {
                    scheduled = t;
                }
            }
            accepted.remove(scheduled);
            hist.add(new History(curTime, scheduled, true));
            return curTime + scheduled.getCompTime();
        }
        return curTime;
    }

    public List<History> getHistory() {
        return hist;
    }
}
