package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 * This server has a set amount of time that it can assign async tasks to per call
 */
public class PollingServer implements IAsyncTaskServer {
    private int runTime;
    private int lastRefresh;
    private int period;
    private int timeUsed;
    private ArrayList<Task> tasks;

    public PollingServer(int runTime, int period) {
        this.runTime = runTime;
        tasks = new ArrayList<>();
        lastRefresh = 0;
        this.period = period;
    }

    @Override
    public void refresh() {
        timeUsed = 0;
    }

    @Override
    public boolean canRun() {
        return timeUsed < runTime;
    }

    @Override
    public boolean shouldRefresh(int curTime) {
        if (lastRefresh / period < curTime / period) {
            lastRefresh = curTime;
            return true;
        }
        return false;
    }

    @Override
    public List<Task> run(int curTime) {
        // Check for missed deadlines
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDeadline() - t.getCompTime() < curTime) {
                tasks.remove(t);
                i--;
                //hist.add(new History(curTime, t, false));
            }
        }

        ArrayList<Task> ret = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getCompTime() + timeUsed < runTime) {
                ret.add(t);
                tasks.remove(t);
                i--;
                timeUsed += t.getCompTime();
            }
            if (timeUsed == runTime) {
                break;
            }
        }
        timeUsed = runTime; // Make it so that it can't run again
        return ret;
    }

    @Override
    public void addTask(Task t) {
        tasks.add(t);
    }
}
