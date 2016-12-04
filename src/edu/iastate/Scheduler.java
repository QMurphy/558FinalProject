package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public class Scheduler {
    private float targetMissRate;
    private VoltageScaler volt;
    private FrequencyScaler freq;
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
            Task scheduled = accepted.get(0);
            for (Task t : accepted) {
                if (scheduled.getDeadline() > t.getDeadline()) {
                    scheduled = t;
                }
            }
            accepted.remove(scheduled);
            hist.add(new History(curTime, scheduled));
            return curTime + scheduled.getCompTime();
        }
        return curTime;
    }

    private float pidController(float curMissRate) {
        float cpuUtil = 0.0f;
        return cpuUtil;
    }

    public List<History> getHistory() {
        return hist;
    }
}
