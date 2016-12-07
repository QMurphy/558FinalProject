package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public abstract class AsyncTaskServer {
    protected int lastRefresh;
    protected int period;
    protected int runTime;
    protected int timeUsed;
    protected ArrayList<Task> tasks;

    public abstract List<Task> run(int curTime);

    public void refresh() {
        timeUsed = 0;
    }

    public boolean canRun() {
        return timeUsed < runTime;
    }

    public boolean shouldRefresh(int curTime) {
        if (lastRefresh / period < curTime / period) {
            lastRefresh = curTime;
            return true;
        }
        return false;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }
}
